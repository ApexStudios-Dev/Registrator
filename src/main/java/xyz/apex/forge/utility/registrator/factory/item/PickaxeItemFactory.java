package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

@FunctionalInterface
public interface PickaxeItemFactory<ITEM extends PickaxeItem>
{
	PickaxeItemFactory<PickaxeItem> DEFAULT = PickaxeItem::new;

	ITEM create(IItemTier itemTier, int attackDamage, float attackSpeed, Item.Properties properties);
}
