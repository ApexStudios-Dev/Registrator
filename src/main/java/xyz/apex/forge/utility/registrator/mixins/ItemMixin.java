package xyz.apex.forge.utility.registrator.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.Item;

import xyz.apex.forge.utility.registrator.entry.similar.ItemLike;

@Mixin(Item.class)
public class ItemMixin implements ItemLike
{
	@Override
	public Item asItem()
	{
		return (Item) (Object) this;
	}
}
