package xyz.apex.forge.utility.registrator.builder;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.data.template.TemplatePoolBuilder;
import xyz.apex.forge.utility.registrator.data.template.TemplatePools;
import xyz.apex.forge.utility.registrator.entry.StructureEntry;
import xyz.apex.forge.utility.registrator.factory.StructureFactory;
import xyz.apex.java.utility.Apex;
import xyz.apex.java.utility.Lazy;
import xyz.apex.java.utility.nullness.NonnullFunction;
import xyz.apex.java.utility.nullness.NonnullSupplier;
import xyz.apex.java.utility.nullness.NonnullTriFunction;
import xyz.apex.java.utility.nullness.NonnullUnaryOperator;

import java.lang.reflect.Method;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({ "unchecked", "ResultOfMethodCallIgnored" })
public final class StructureBuilder<
		OWNER extends AbstractRegistrator<OWNER>,
		STRUCTURE extends StructureFeature<FEATURE_CONFIG>,
		FEATURE_CONFIG extends FeatureConfiguration,
		PARENT
> extends LegacyRegistratorBuilder<OWNER, StructureFeature<?>, STRUCTURE, PARENT, StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT>, StructureEntry<STRUCTURE, FEATURE_CONFIG>>
{
	private static final Method getCodec_Method = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");

	private final StructureFactory<STRUCTURE, FEATURE_CONFIG> structureFactory;
	private final Lazy<Codec<FEATURE_CONFIG>> structureCodec;
	private final Lazy<FEATURE_CONFIG> featureConfig;

	private Supplier<StructureFeatureConfiguration> separationSettingsSupplier = () -> null;
	private boolean terraformTerrain = false;
	private GenerationStep.Decoration generationStage = GenerationStep.Decoration.SURFACE_STRUCTURES;
	private NonnullFunction<ServerLevel, Boolean> canDimensionGenerateStructure = level -> true;
	private Function<Biome, Boolean> canBiomeGenerate = biome -> true;
	private NonnullTriFunction<Biome.BiomeCategory, BiomeSpecialEffects, Biome.ClimateSettings, Boolean> canBiomeDataGenerate = (category, effects, climate) -> true;
	private NonnullUnaryOperator<TemplatePoolBuilder.ElementBuilder> templateElementModifier = NonnullUnaryOperator.identity();
	private final Lazy<TemplatePools> templatePool;
	private NonnullSupplier<TemplatePools> templatePoolFactory = () -> TemplatePools.of(owner.getModId(), getRegistryName());

	public StructureBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, StructureFactory<STRUCTURE, FEATURE_CONFIG> structureFactory, NonnullSupplier<Codec<FEATURE_CONFIG>> structureCodecSupplier, NonnullSupplier<FEATURE_CONFIG> featureConfigSupplier)
	{
		super(owner, parent, registryName, callback, StructureFeature.class, StructureEntry::cast);

		this.structureFactory = structureFactory;

		structureCodec = Lazy.of(structureCodecSupplier, true);
		featureConfig = Lazy.of(featureConfigSupplier, true);
		templatePool = Lazy.of(() -> templatePoolFactory.get(), true);

		setDataGenerator(AbstractRegistrator.TEMPLATE_POOL_PROVIDER, (ctx, provider) -> templateElementModifier.apply(provider.pool(templatePool.get()).element().location(ctx::get)).location(ctx::get).end());
		onRegister(this::onRegister);
	}

	private void onRegister(STRUCTURE structure)
	{
		StructureFeature.STRUCTURES_REGISTRY.put(getRegistryNameFull(), structure);

		if(terraformTerrain)
		{
			var noiseAffectingFeatures = Apex.makeMutableList(StructureFeature.NOISE_AFFECTING_FEATURES);
			noiseAffectingFeatures.add(structure);
			StructureFeature.NOISE_AFFECTING_FEATURES = noiseAffectingFeatures;
		}

		var separationSettings = separationSettingsSupplier.get();

		if(separationSettings != null)
		{
			var defaults = Apex.makeMutableMap(StructureSettings.DEFAULTS);
			defaults.put(structure, separationSettings);
			StructureSettings.DEFAULTS = ImmutableMap.copyOf(defaults);

			for(var entry : BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet())
			{
				var dimensionSettings = entry.getValue().structureSettings();
				var structureSettingsMap = Apex.makeMutableMap(dimensionSettings.structureConfig());
				structureSettingsMap.put(structure, separationSettings);
				dimensionSettings.structureConfig = structureSettingsMap;
			}
		}

		StructureFeature.STEP.put(structure, generationStage);

		OneTimeEventReceiver.addModListener(EventPriority.HIGH, FMLCommonSetupEvent.class, event -> {
			var configuredFeature = structure.configured(featureConfig.get());
			Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, owner.id("configured_" + getRegistryName()), configuredFeature);
		});

		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, WorldEvent.Load.class, event -> onLevelLoad(event, structure));
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, false, BiomeLoadingEvent.class, event -> onBiomeLoading(event, structure));
	}

	private void onLevelLoad(WorldEvent.Load event, STRUCTURE structure)
	{
		var world = event.getWorld();

		if(world instanceof ServerLevel level)
		{
			var generator = level.getChunkSource().getGenerator();

			if(isTerraForged(level))
				return;
			if(generator instanceof FlatLevelSource && level.dimension() == Level.OVERWORLD)
				return;

			var dimensionStructuresSettings = generator.getSettings();
			var structureSeparationSettingsMap = dimensionStructuresSettings.structureConfig();
			dimensionStructuresSettings.structureConfig = Apex.makeMutableMap(structureSeparationSettingsMap);

			if(canDimensionGenerateStructure.apply(level))
				structureSeparationSettingsMap.putIfAbsent(structure, StructureSettings.DEFAULTS.get(structure));
			else
				structureSeparationSettingsMap.remove(structure);
		}
	}

	private void onBiomeLoading(BiomeLoadingEvent event, STRUCTURE structure)
	{
		var category = event.getCategory();
		var effects = event.getEffects();
		var climate = event.getClimate();
		var biomeName = event.getName();
		Biome biome = null;

		if(biomeName != null)
			biome = BuiltinRegistries.BIOME.get(biomeName);
		if(biome != null && !canBiomeGenerate.apply(biome))
			return;
		if(!canBiomeDataGenerate.apply(category, effects, climate))
			return;

		var featureConfig = this.featureConfig.get();
		var configured = structure.configured(featureConfig);
		event.getGeneration().getStructures().add(() -> configured);
	}

	@Override
	protected StructureEntry<STRUCTURE, FEATURE_CONFIG> createEntryWrapper(RegistryObject<STRUCTURE> delegate)
	{
		return new StructureEntry<>(owner, delegate, templatePool.get());
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> separationSettings(int spacing, int separation, int salt)
	{
		return separationSettings(() -> new StructureFeatureConfiguration(spacing, separation, salt));
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> separationSettings(NonnullSupplier<StructureFeatureConfiguration> separationSettingsSupplier)
	{
		this.separationSettingsSupplier = separationSettingsSupplier;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> terraformTerrain()
	{
		return terraformTerrain(true);
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> terraformTerrain(boolean terraformTerrain)
	{
		this.terraformTerrain = terraformTerrain;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> generationStage(GenerationStep.Decoration generationStage)
	{
		this.generationStage = generationStage;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> canDimensionGenerate(NonnullFunction<ServerLevel, Boolean> canDimensionGenerateStructure)
	{
		this.canDimensionGenerateStructure = canDimensionGenerateStructure;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> canBiomeGenerate(NonnullFunction<Biome, Boolean> canBiomeGenerate)
	{
		this.canBiomeGenerate = canBiomeGenerate;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> canBiomeGenerate(NonnullTriFunction<Biome.BiomeCategory, BiomeSpecialEffects, Biome.ClimateSettings, Boolean> canBiomeDataGenerate)
	{
		this.canBiomeDataGenerate = canBiomeDataGenerate;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> templatePool(NonnullSupplier<TemplatePools> templatePoolFactory)
	{
		templatePool.invalidate();
		this.templatePoolFactory = templatePoolFactory;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> templateElement(NonnullUnaryOperator<TemplatePoolBuilder.ElementBuilder> templateElementModifier)
	{
		this.templateElementModifier = this.templateElementModifier.andThen(templateElementModifier);
		return this;
	}

	@Override
	protected @NonnullType STRUCTURE createEntry()
	{
		return structureFactory.create(structureCodec.get());
	}

	private static boolean isTerraForged(ServerLevel level)
	{
		try
		{
			var generator = level.getChunkSource().getGenerator();
			var codec = (Codec<? extends ChunkGenerator>) getCodec_Method.invoke(generator);
			var generatorName = Registry.CHUNK_GENERATOR.getKey(codec);
			return generatorName != null && generatorName.getNamespace().equalsIgnoreCase("terraforged");
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
