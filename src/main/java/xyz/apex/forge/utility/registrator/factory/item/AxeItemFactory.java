package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;

@FunctionalInterface
public interface AxeItemFactory<ITEM extends AxeItem>
{
	AxeItemFactory<AxeItem> DEFAULT = AxeItem::new;

	ITEM create(IItemTier itemTier, float attackDamage, float attackSpeed, Item.Properties properties);
}
