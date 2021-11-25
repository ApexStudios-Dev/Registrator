package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;

import java.util.Set;

@FunctionalInterface
public interface ToolItemFactory<ITEM extends ToolItem>
{
	ToolItemFactory<ToolItem> DEFAULT = ToolItem::new;

	ITEM create(float attackDamage, float attackSpeed, IItemTier itemTier, Set<Block> diggables, Item.Properties properties);
}
