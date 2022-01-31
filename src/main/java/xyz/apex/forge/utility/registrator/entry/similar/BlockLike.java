package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
