package xyz.apex.forge.testmod.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import xyz.apex.forge.testmod.block.entity.TestBlockEntity;
import xyz.apex.forge.utility.registrator.entry.BlockEntityEntry;
import xyz.apex.forge.utility.registrator.entry.BlockEntry;

@SuppressWarnings({ "unused", "SameParameterValue" })
public final class TBlockEntityTypes
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final BlockEntityEntry<TestBlockEntity> TEST_BLOCK_ENTITY = blockEntity(TBlocks.TEST_BLOCK);

	private static <BLOCK_ENTITY extends TileEntity, BLOCK extends Block> BlockEntityEntry<BLOCK_ENTITY> blockEntity(BlockEntry<BLOCK> blockEntry)
	{
		return BlockEntityEntry.cast(blockEntry.getSibling(TileEntityType.class));
	}

	static void bootstrap() { }
}
