package xyz.apex.forge.utility.registrator.builder;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({ "unchecked", "ResultOfMethodCallIgnored" })
public final class StructureBuilder<
		OWNER extends AbstractRegistrator<OWNER>,
		STRUCTURE extends Structure<FEATURE_CONFIG>,
		FEATURE_CONFIG extends IFeatureConfig,
		PARENT
> extends LegacyRegistratorBuilder<OWNER, Structure<?>, STRUCTURE, PARENT, StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT>, StructureEntry<STRUCTURE, FEATURE_CONFIG>>
{
	private static final Method getCodec_Method = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");

	private final StructureFactory<STRUCTURE, FEATURE_CONFIG> structureFactory;
	private final Lazy<Codec<FEATURE_CONFIG>> structureCodec;
	private final Lazy<FEATURE_CONFIG> featureConfig;

	private Supplier<StructureSeparationSettings> separationSettingsSupplier = () -> null;
	private boolean terraformTerrain = false;
	private GenerationStage.Decoration generationStage = GenerationStage.Decoration.SURFACE_STRUCTURES;
	private NonnullFunction<ServerWorld, Boolean> canDimensionGenerateStructure = level -> true;
	private Function<Biome, Boolean> canBiomeGenerate = biome -> true;
	private NonnullTriFunction<Biome.Category, BiomeAmbience, Biome.Climate, Boolean> canBiomeDataGenerate = (category, effects, climate) -> true;
	private NonnullUnaryOperator<TemplatePoolBuilder.ElementBuilder> templateElementModifier = NonnullUnaryOperator.identity();
	private final Lazy<TemplatePools> templatePool;
	private NonnullSupplier<TemplatePools> templatePoolFactory = () -> TemplatePools.of(owner.getModId(), getRegistryName());

	public StructureBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, StructureFactory<STRUCTURE, FEATURE_CONFIG> structureFactory, NonnullSupplier<Codec<FEATURE_CONFIG>> structureCodecSupplier, NonnullSupplier<FEATURE_CONFIG> featureConfigSupplier)
	{
		super(owner, parent, registryName, callback, Structure.class, StructureEntry::cast);

		this.structureFactory = structureFactory;

		structureCodec = Lazy.of(structureCodecSupplier, true);
		featureConfig = Lazy.of(featureConfigSupplier, true);
		templatePool = Lazy.of(() -> templatePoolFactory.get(), true);

		setDataGenerator(AbstractRegistrator.TEMPLATE_POOL_PROVIDER, (ctx, provider) -> templateElementModifier.apply(provider.pool(templatePool.get()).element().location(ctx::get)).location(ctx::get).end());
		onRegister(this::onRegister);
	}

	private void onRegister(STRUCTURE structure)
	{
		Structure.STRUCTURES_REGISTRY.put(getRegistryNameFull(), structure);

		if(terraformTerrain)
		{
			List<Structure<?>> noiseAffectingFeatures = Apex.copyIfImmutableList(Structure.NOISE_AFFECTING_FEATURES);
			noiseAffectingFeatures.add(structure);
			Structure.NOISE_AFFECTING_FEATURES = noiseAffectingFeatures;
		}

		StructureSeparationSettings separationSettings = separationSettingsSupplier.get();

		if(separationSettings != null)
		{
			Map<Structure<?>, StructureSeparationSettings> defaults = Apex.copyIfImmutableMap(DimensionStructuresSettings.DEFAULTS);
			defaults.put(structure, separationSettings);
			DimensionStructuresSettings.DEFAULTS = ImmutableMap.copyOf(defaults);

			for(Map.Entry<RegistryKey<DimensionSettings>, DimensionSettings> entry : WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet())
			{
				DimensionStructuresSettings dimensionSettings = entry.getValue().structureSettings();
				Map<Structure<?>, StructureSeparationSettings> structureSettingsMap = Apex.copyIfImmutableMap(dimensionSettings.structureConfig());
				structureSettingsMap.put(structure, separationSettings);
				dimensionSettings.structureConfig = structureSettingsMap;
			}
		}

		Structure.STEP.put(structure, generationStage);

		OneTimeEventReceiver.addModListener(EventPriority.HIGH, FMLCommonSetupEvent.class, event -> {
			StructureFeature<FEATURE_CONFIG, ? extends Structure<FEATURE_CONFIG>> configuredFeature = structure.configured(featureConfig.get());
			Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, owner.id("configured_" + getRegistryName()), configuredFeature);
			FlatGenerationSettings.STRUCTURE_FEATURES.put(structure, configuredFeature);
		});

		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, WorldEvent.Load.class, event -> onLevelLoad(event, structure));
		MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, false, BiomeLoadingEvent.class, event -> onBiomeLoading(event, structure));
	}

	private void onLevelLoad(WorldEvent.Load event, STRUCTURE structure)
	{
		IWorld world = event.getWorld();

		if(world instanceof ServerWorld)
		{
			ServerWorld level = (ServerWorld) world;
			ChunkGenerator generator = level.getChunkSource().getGenerator();

			if(isTerraForged(level))
				return;
			if(generator instanceof FlatChunkGenerator && level.dimension() == World.OVERWORLD)
				return;

			DimensionStructuresSettings dimensionStructuresSettings = generator.getSettings();
			Map<Structure<?>, StructureSeparationSettings> structureSeparationSettingsMap = dimensionStructuresSettings.structureConfig();
			dimensionStructuresSettings.structureConfig = Apex.copyIfImmutableMap(structureSeparationSettingsMap);

			if(canDimensionGenerateStructure.apply(level))
				structureSeparationSettingsMap.putIfAbsent(structure, DimensionStructuresSettings.DEFAULTS.get(structure));
			else
				structureSeparationSettingsMap.remove(structure);
		}
	}

	private void onBiomeLoading(BiomeLoadingEvent event, STRUCTURE structure)
	{
		Biome.Category category = event.getCategory();
		BiomeAmbience effects = event.getEffects();
		Biome.Climate climate = event.getClimate();
		ResourceLocation biomeName = event.getName();
		Biome biome = null;

		if(biomeName != null)
			biome = WorldGenRegistries.BIOME.get(biomeName);
		if(biome != null && !canBiomeGenerate.apply(biome))
			return;
		if(!canBiomeDataGenerate.apply(category, effects, climate))
			return;

		FEATURE_CONFIG featureConfig = this.featureConfig.get();
		StructureFeature<FEATURE_CONFIG, ? extends Structure<FEATURE_CONFIG>> configured = structure.configured(featureConfig);
		event.getGeneration().getStructures().add(() -> configured);
	}

	@Override
	protected StructureEntry<STRUCTURE, FEATURE_CONFIG> createEntryWrapper(RegistryObject<STRUCTURE> delegate)
	{
		return new StructureEntry<>(owner, delegate, templatePool.get());
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> separationSettings(int spacing, int separation, int salt)
	{
		return separationSettings(() -> new StructureSeparationSettings(spacing, separation, salt));
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> separationSettings(NonnullSupplier<StructureSeparationSettings> separationSettingsSupplier)
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

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> generationStage(GenerationStage.Decoration generationStage)
	{
		this.generationStage = generationStage;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> canDimensionGenerate(NonnullFunction<ServerWorld, Boolean> canDimensionGenerateStructure)
	{
		this.canDimensionGenerateStructure = canDimensionGenerateStructure;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> canBiomeGenerate(NonnullFunction<Biome, Boolean> canBiomeGenerate)
	{
		this.canBiomeGenerate = canBiomeGenerate;
		return this;
	}

	public StructureBuilder<OWNER, STRUCTURE, FEATURE_CONFIG, PARENT> canBiomeGenerate(NonnullTriFunction<Biome.Category, BiomeAmbience, Biome.Climate, Boolean> canBiomeDataGenerate)
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

	private static boolean isTerraForged(ServerWorld level)
	{
		try
		{
			ChunkGenerator generator = level.getChunkSource().getGenerator();
			Codec<? extends ChunkGenerator> codec = (Codec<? extends ChunkGenerator>) getCodec_Method.invoke(generator);
			ResourceLocation generatorName = Registry.CHUNK_GENERATOR.getKey(codec);
			return generatorName != null && generatorName.getNamespace().equalsIgnoreCase("terraforged");
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
