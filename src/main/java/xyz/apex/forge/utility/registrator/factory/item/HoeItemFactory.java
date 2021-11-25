package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;

@FunctionalInterface
public interface HoeItemFactory<ITEM extends HoeItem>
{
	HoeItemFactory<HoeItem> DEFAULT = HoeItem::new;

	ITEM create(IItemTier itemTier, int attackDamage, float attackSpeed, Item.Properties properties);
}
