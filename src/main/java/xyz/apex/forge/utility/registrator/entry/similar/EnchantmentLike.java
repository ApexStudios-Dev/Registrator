package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.item.enchantment.Enchantment;

@FunctionalInterface
public interface EnchantmentLike
{
	Enchantment asEnchantment();
}
