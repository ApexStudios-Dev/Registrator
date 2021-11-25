package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

@FunctionalInterface
public interface SwordItemFactory<ITEM extends SwordItem>
{
	SwordItemFactory<SwordItem> DEFAULT = SwordItem::new;

	ITEM create(IItemTier itemTier, int attackDamage, float attackSpeed, Item.Properties properties);
}
