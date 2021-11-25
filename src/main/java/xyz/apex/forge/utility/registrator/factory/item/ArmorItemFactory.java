package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

@FunctionalInterface
public interface ArmorItemFactory<ITEM extends ArmorItem>
{
	ArmorItemFactory<ArmorItem> DEFAULT = ArmorItem::new;
	DyeableFactory<DyeableArmorItem> DYEABLE_DEFAULT = DyeableArmorItem::new;

	ITEM create(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Item.Properties properties);

	interface DyeableFactory<ITEM extends ArmorItem & IDyeableArmorItem> extends ArmorItemFactory<ITEM>
	{
	}
}
