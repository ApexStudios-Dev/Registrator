package xyz.apex.forge.testmod.init;

import com.tterrag.registrate.providers.ProviderType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;

public final class TTags
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	static void bootstrap()
	{
		Items.bootstrap();
		Blocks.bootstrap();
	}

	public static final class Items
	{
		public static final ITag.INamedTag<Item> INGOTS_COPPER = REGISTRY.itemTagOptionalForge("ingots/copper");
		public static final ITag.INamedTag<Item> NUGGETS_COPPER = REGISTRY.itemTagOptionalForge("nuggets/copper");
		public static final ITag.INamedTag<Item> ORES_COPPER = REGISTRY.itemTagOptionalForge("ores/copper");
		public static final ITag.INamedTag<Item> STORAGE_BLOCKS_COPPER = REGISTRY.itemTagOptionalForge("storage_blocks/copper");

		@SuppressWarnings("unchecked")
		private static void bootstrap()
		{
			REGISTRY.addDataGenerator(ProviderType.ITEM_TAGS, provider -> {
				provider.tag(Tags.Items.INGOTS).addTags(INGOTS_COPPER);
				provider.tag(Tags.Items.NUGGETS).addTags(NUGGETS_COPPER);
				provider.tag(Tags.Items.ORES).addTags(ORES_COPPER);
				provider.tag(STORAGE_BLOCKS_COPPER).addTags();
				provider.tag(Tags.Items.STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_COPPER);
			});
		}
	}

	public static final class Blocks
	{
		public static final ITag.INamedTag<Block> ORES_COPPER = REGISTRY.blockTagOptionalForge("ores/copper");
		public static final ITag.INamedTag<Block> STORAGE_BLOCKS_COPPER = REGISTRY.blockTagOptionalForge("storage_blocks/copper");

		@SuppressWarnings("unchecked")
		private static void bootstrap()
		{
			REGISTRY.addDataGenerator(ProviderType.BLOCK_TAGS, provider -> {
				provider.tag(Tags.Blocks.ORES).addTags(ORES_COPPER);
				provider.tag(STORAGE_BLOCKS_COPPER).addTags();
				provider.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(STORAGE_BLOCKS_COPPER);
			});
		}
	}
}
