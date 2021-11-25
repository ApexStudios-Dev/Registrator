package xyz.apex.forge.testmod.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

import xyz.apex.forge.testmod.block.TestBlock;
import xyz.apex.forge.testmod.block.entity.TestBlockEntity;
import xyz.apex.forge.utility.registrator.entry.BlockEntry;
import xyz.apex.forge.utility.registrator.provider.RegistrateLangExtProvider;

public class TBlocks
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	// region: Test Block
	public static final BlockEntry<TestBlock> TEST_BLOCK = REGISTRY
			.block("test_block", TestBlock::new)
				.lang("Test Block")
				.lang(RegistrateLangExtProvider.EN_GB, "Test Block")

				.simpleBlockEntity(TestBlockEntity::new)
				.simpleItem()

			.register();
	// endregion

	// region: Copper Ore
	public static final BlockEntry<Block> COPPER_ORE = REGISTRY
			.block("copper_ore", Material.STONE)
				.lang("Copper Ore")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Ore")
				.tag(TTags.Blocks.ORES_COPPER)
				.harvestTool(ToolType.PICKAXE)
				.requiresCorrectToolForDrops()
				.strength(3F, 3F)

				.item()
					.recipe((ctx, provider) -> provider.smeltingAndBlasting(DataIngredient.tag(TTags.Items.ORES_COPPER), TItems.COPPER_INGOT, .7F))
					.tag(TTags.Items.ORES_COPPER)
				.build()
			.register();
	// endregion

	// region: Copper Block
	public static final BlockEntry<Block> COPPER_BLOCK = REGISTRY
			.block("copper_block", Material.METAL, MaterialColor.COLOR_ORANGE)
				.lang("Copper Block")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Block")
				.harvestTool(ToolType.PICKAXE)
				.requiresCorrectToolForDrops()
				.strength(3F, 6F)
				.sound(TElements.COPPER_SOUND_TYPE)
				.tag(TTags.Blocks.STORAGE_BLOCKS_COPPER)
				.clearDataGenerator(ProviderType.BLOCKSTATE)

				.item()
					.tag(TTags.Items.STORAGE_BLOCKS_COPPER)
					.clearDataGenerator(ProviderType.ITEM_MODEL)
				.build()
			.register();
	// endregion

	static void bootstrap() { }
}
