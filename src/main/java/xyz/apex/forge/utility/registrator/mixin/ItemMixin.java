package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.Item;

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
