package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

@FunctionalInterface
public interface HorseArmorItemFactory<ITEM extends HorseArmorItem>
{
	HorseArmorItemFactory<HorseArmorItem> DEFAULT = HorseArmorItem::new;
	DyeableFactory<DyeableHorseArmorItem> DYEABLE_DEFAULT = DyeableHorseArmorItem::new;

	ITEM create(int protection, ResourceLocation texture, Item.Properties properties);

	interface DyeableFactory<ITEM extends HorseArmorItem & IDyeableArmorItem> extends HorseArmorItemFactory<ITEM>
	{
	}
}
