package xyz.apex.forge.testmod.init;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.TemplateManager;

import xyz.apex.forge.utility.registrator.entry.StructureEntry;

public final class TStructures
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final StructureEntry<TestStructure, NoFeatureConfig> TEST_STRUCTURE = REGISTRY
			.structure("test_structure", TestStructure::new)
			.separationSettings(16, 12, 133742069)
			.terraformTerrain()
			.templateElement(element -> element
					.weight(50)
					.projection(JigsawPattern.PlacementBehaviour.RIGID)
					.elementType(() -> IJigsawDeserializer.LEGACY)
					.processor(() -> ProcessorLists.EMPTY)
			)
			.register();

	static void bootstrap() { }

	public static final class TestStructure extends Structure<NoFeatureConfig>
	{
		public TestStructure(Codec<NoFeatureConfig> codec)
		{
			super(codec);
		}

		@Override
		public IStartFactory<NoFeatureConfig> getStartFactory()
		{
			return StartFactory::new;
		}

		public static final class StartFactory extends StructureStart<NoFeatureConfig>
		{
			public StartFactory(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed)
			{
				super(structure, chunkX, chunkZ, boundingBox, references, seed);
			}

			@Override
			public void generatePieces(DynamicRegistries registries, ChunkGenerator generator, TemplateManager templateManager, int chunkX, int chunkZ, Biome biome, NoFeatureConfig featureConfig)
			{
				int x = chunkX * 16;
				int z = chunkZ * 16;
				int landHeight = generator.getFirstFreeHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
				BlockPos centerPos = new BlockPos(x, landHeight, z);

				JigsawManager.addPieces(
						registries,
						new VillageConfig(() -> TEST_STRUCTURE.getJigsawPattern(registries), 10),
						AbstractVillagePiece::new,
						generator,
						templateManager,
						centerPos,
						pieces,
						random,
						false,
						false
				);

				Vector3i structureCenter = pieces.get(0).getBoundingBox().getCenter();
				int xOffset = centerPos.getX() - structureCenter.getX();
				int zOffset = centerPos.getZ() - structureCenter.getZ();
				pieces.forEach(piece -> piece.move(xOffset, 0, zOffset));

				calculateBoundingBox();
			}
		}
	}
}
