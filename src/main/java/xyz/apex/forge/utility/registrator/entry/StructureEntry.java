package xyz.apex.forge.utility.registrator.entry;

import com.mojang.serialization.Codec;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.data.template.TemplatePools;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;
import java.util.List;

public final class StructureEntry<STRUCTURE extends Structure<FEATURE_CONFIG>, FEATURE_CONFIG extends IFeatureConfig> extends RegistryEntry<STRUCTURE> implements NonnullSupplier<STRUCTURE>
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
	public JigsawPattern getJigsawPattern(DynamicRegistries registries)
	{
		return registries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(templatePool.getPoolName());
	}

	public Structure<?> asStructure()
	{
		return get();
	}

	public GenerationStage.Decoration step()
	{
		return asStructure().step();
	}

	public Codec<StructureFeature<FEATURE_CONFIG, Structure<FEATURE_CONFIG>>> configuredFeatureCodec()
	{
		return get().configuredStructureCodec();
	}

	public StructureFeature<FEATURE_CONFIG, ? extends Structure<FEATURE_CONFIG>> configured(FEATURE_CONFIG featureConfig)
	{
		return get().configured(featureConfig);
	}

	@Nullable
	public BlockPos getNearestGenerationFeature(IWorldReader level, StructureManager structureManager, BlockPos pos, int searchRadius, boolean skipKnownStructures, long seed, StructureSeparationSettings separationSettings)
	{
		return asStructure().getNearestGeneratedFeature(level, structureManager, pos, searchRadius, skipKnownStructures, seed, separationSettings);
	}

	public ChunkPos getPotentialFeatureConfig(StructureSeparationSettings separationSettings, long seed, SharedSeedRandom rng, int x, int z)
	{
		return asStructure().getPotentialFeatureChunk(separationSettings, seed, rng, x, z);
	}

	public StructureStart<?> generate(DynamicRegistries registries, ChunkGenerator generator, BiomeProvider biomeProvider, TemplateManager templateManager, long seed, ChunkPos chunkPos, Biome biome, int references, SharedSeedRandom rng, StructureSeparationSettings separationSettings, FEATURE_CONFIG featureConfig)
	{
		return get().generate(registries, generator, biomeProvider, templateManager, seed, chunkPos, biome, references, rng, separationSettings, featureConfig);
	}

	public Structure.IStartFactory<?> getStartFactory()
	{
		return asStructure().getStartFactory();
	}

	public String getFeatureName()
	{
		return asStructure().getFeatureName();
	}

	public List<MobSpawnInfo.Spawners> getSpecialEnemies()
	{
		return asStructure().getSpecialEnemies();
	}

	public List<MobSpawnInfo.Spawners> getSpecialAnimals()
	{
		return asStructure().getSpecialAnimals();
	}

	public List<MobSpawnInfo.Spawners> getSpawnList(EntityClassification entityClassification)
	{
		return asStructure().getSpawnList(entityClassification);
	}

	public static <STRUCTURE extends Structure<FEATURE_CONFIG>, FEATURE_CONFIG extends IFeatureConfig> StructureEntry<STRUCTURE, FEATURE_CONFIG> cast(RegistryEntry<STRUCTURE> registryEntry)
	{
		return cast(StructureEntry.class, registryEntry);
	}

	public static <STRUCTURE extends Structure<FEATURE_CONFIG>, FEATURE_CONFIG extends IFeatureConfig> StructureEntry<STRUCTURE, FEATURE_CONFIG> cast(com.tterrag.registrate.util.entry.RegistryEntry<STRUCTURE> registryEntry)
	{
		return cast(StructureEntry.class, registryEntry);
	}
}
