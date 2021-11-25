package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

@FunctionalInterface
public interface BlockLike extends ItemLike
{
	Block asBlock();

	@Override
	default Item asItem()
	{
		return asBlock().asItem();
	}
}
