package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

@FunctionalInterface
public interface ItemLike extends IItemProvider
{
	@Override Item asItem();
}
