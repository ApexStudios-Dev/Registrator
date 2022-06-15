package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

@SuppressWarnings("unused")
public final class BlockEntry<BLOCK extends Block> extends ItemLikeEntry<BLOCK> implements NonnullSupplier<BLOCK>
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

	public boolean isInBlockTag(TagKey<Block> tag)
	{
		var tags = ForgeRegistries.BLOCKS.tags();
		return tags != null && tags.getTag(tag).contains(asBlock());
	}

	public boolean isBlock(Block block)
	{
		return asBlock() == block;
	}

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
