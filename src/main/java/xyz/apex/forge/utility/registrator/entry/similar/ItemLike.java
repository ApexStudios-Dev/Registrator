package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.item.Item;

@FunctionalInterface
public interface ItemLike extends net.minecraft.world.level.ItemLike
{
	@Override
	Item asItem();
}
