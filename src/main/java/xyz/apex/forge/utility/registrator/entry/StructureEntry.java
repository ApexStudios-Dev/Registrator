package xyz.apex.forge.utility.registrator.entry;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraftforge.fmllegacy.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.data.template.TemplatePools;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

public final class StructureEntry<STRUCTURE extends StructureFeature<FEATURE_CONFIG>, FEATURE_CONFIG extends FeatureConfiguration> extends RegistryEntry<STRUCTURE> implements NonnullSupplier<STRUCTURE>
{
	private final TemplatePools templatePool;

	public StructureEntry(AbstractRegistrator<?> registrator, RegistryObject<STRUCTURE> delegate, TemplatePools templatePool)
	{
		super(registrator, delegate);

		this.templatePool = templatePool;
	}

	public TemplatePools getTemplatePool()
	{
		return templatePool;
	}

	@Nullable
	public StructureTemplatePool getJigsawPattern(RegistryAccess registries)
	{
		return registries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(templatePool.getPoolName());
	}

	public StructureFeature<?> asStructure()
	{
		return get();
	}

	public GenerationStep.Decoration step()
	{
		return asStructure().step();
	}

	public Codec<ConfiguredStructureFeature<FEATURE_CONFIG, StructureFeature<FEATURE_CONFIG>>> configuredFeatureCodec()
	{
		return get().configuredStructureCodec();
	}

	public ConfiguredStructureFeature<FEATURE_CONFIG, ? extends StructureFeature<FEATURE_CONFIG>> configured(FEATURE_CONFIG featureConfig)
	{
		return get().configured(featureConfig);
	}

	@Nullable
	public BlockPos getNearestGenerationFeature(LevelReader level, StructureFeatureManager structureManager, BlockPos pos, int searchRadius, boolean skipKnownStructures, long seed, StructureFeatureConfiguration separationSettings)
	{
		return asStructure().getNearestGeneratedFeature(level, structureManager, pos, searchRadius, skipKnownStructures, seed, separationSettings);
	}

	public ChunkPos getPotentialFeatureConfig(StructureFeatureConfiguration separationSettings, long seed, WorldgenRandom rng, int x, int z)
	{
		return asStructure().getPotentialFeatureChunk(separationSettings, seed, rng, x, z);
	}

	public StructureStart<?> generate(RegistryAccess registries, ChunkGenerator generator, BiomeSource biomeProvider, StructureManager templateManager, long seed, ChunkPos chunkPos, Biome biome, int references, WorldgenRandom rng, StructureFeatureConfiguration separationSettings, FEATURE_CONFIG featureConfig, LevelHeightAccessor levelHeightAccessor)
	{
		return get().generate(registries, generator, biomeProvider, templateManager, seed, chunkPos, biome, references, rng, separationSettings, featureConfig, levelHeightAccessor);
	}

	public StructureFeature.StructureStartFactory<?> getStartFactory()
	{
		return asStructure().getStartFactory();
	}

	public String getFeatureName()
	{
		return asStructure().getFeatureName();
	}

	public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialEnemies()
	{
		return asStructure().getSpecialEnemies();
	}

	public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialAnimals()
	{
		return asStructure().getSpecialAnimals();
	}

	public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpawnList(MobCategory mobCategory)
	{
		return asStructure().getSpawnList(mobCategory);
	}

	public static <STRUCTURE extends StructureFeature<FEATURE_CONFIG>, FEATURE_CONFIG extends FeatureConfiguration> StructureEntry<STRUCTURE, FEATURE_CONFIG> cast(RegistryEntry<STRUCTURE> registryEntry)
	{
		return cast(StructureEntry.class, registryEntry);
	}

	public static <STRUCTURE extends StructureFeature<FEATURE_CONFIG>, FEATURE_CONFIG extends FeatureConfiguration> StructureEntry<STRUCTURE, FEATURE_CONFIG> cast(com.tterrag.registrate.util.entry.RegistryEntry<STRUCTURE> registryEntry)
	{
		return cast(StructureEntry.class, registryEntry);
	}
}
