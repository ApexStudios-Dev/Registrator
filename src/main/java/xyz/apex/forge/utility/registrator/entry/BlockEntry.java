package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.ITag;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.similar.BlockLike;
import xyz.apex.java.utility.nullness.NonnullSupplier;

@SuppressWarnings("unused")
public final class BlockEntry<BLOCK extends Block> extends ItemProviderEntry<BLOCK> implements BlockLike, NonnullSupplier<BLOCK>
{
	public BlockEntry(AbstractRegistrator<?> registrator, RegistryObject<BLOCK> delegate)
	{
		super(registrator, delegate);
	}

	public BlockState defaultBlockState()
	{
		return asBlock().defaultBlockState();
	}

	public boolean hasBlockState(BlockState blockState)
	{
		return isBlock(blockState.getBlock());
	}

	public boolean isInBlockTag(ITag<Block> tag)
	{
		return asBlock().is(tag);
	}

	public boolean isBlock(Block block)
	{
		return asBlock().is(block);
	}

	public boolean isBlock(BlockLike block)
	{
		return isBlock(block.asBlock());
	}

	@Override
	public BLOCK asBlock()
	{
		return get();
	}

	public static <BLOCK extends Block> BlockEntry<BLOCK> cast(RegistryEntry<BLOCK> registryEntry)
	{
		return cast(BlockEntry.class, registryEntry);
	}

	public static <BLOCK extends Block> BlockEntry<BLOCK> cast(com.tterrag.registrate.util.entry.RegistryEntry<BLOCK> registryEntry)
	{
		return cast(BlockEntry.class, registryEntry);
	}
}
