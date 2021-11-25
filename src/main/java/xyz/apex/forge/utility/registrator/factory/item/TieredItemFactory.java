package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.TieredItem;

@FunctionalInterface
public interface TieredItemFactory<ITEM extends TieredItem>
{
	TieredItemFactory<TieredItem> DEFAULT = TieredItem::new;

	ITEM create(IItemTier itemTier, Item.Properties properties);
}
