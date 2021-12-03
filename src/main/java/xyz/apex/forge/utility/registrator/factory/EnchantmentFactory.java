package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public interface EnchantmentFactory<ENCHANTMENT extends Enchantment>
{
	EnchantmentFactory<Enchantment> DEFAULT = Default::new;

	ENCHANTMENT create(Enchantment.Rarity enchantmentRarity, EnchantmentType enchantmentType, EquipmentSlotType[] slotTypes);

	// because constructor is protected,
	// and I see no way for access transformers
	// to make a constructor public
	class Default extends Enchantment
	{
		public Default(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType[] slotTypes)
		{
			super(rarity, enchantmentType, slotTypes);
		}
	}
}
