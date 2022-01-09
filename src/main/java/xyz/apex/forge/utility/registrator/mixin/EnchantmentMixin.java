package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.enchantment.Enchantment;

import xyz.apex.forge.utility.registrator.entry.similar.EnchantmentLike;

@Mixin(Enchantment.class)
public class EnchantmentMixin implements EnchantmentLike
{
	@Override
	public Enchantment asEnchantment()
	{
		return (Enchantment) (Object) this;
	}
}
