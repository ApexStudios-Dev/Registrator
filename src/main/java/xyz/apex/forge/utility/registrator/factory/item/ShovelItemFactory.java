package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;

@FunctionalInterface
public interface ShovelItemFactory<ITEM extends ShovelItem>
{
	ShovelItemFactory<ShovelItem> DEFAULT = ShovelItem::new;

	ITEM create(IItemTier itemTier, float attackDamage, float attackSpeed, Item.Properties properties);
}
