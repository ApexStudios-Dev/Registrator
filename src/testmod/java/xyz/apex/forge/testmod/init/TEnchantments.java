package xyz.apex.forge.testmod.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

import xyz.apex.forge.utility.registrator.entry.EnchantmentEntry;
import xyz.apex.forge.utility.registrator.provider.RegistrateLangExtProvider;

public class TEnchantments
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final EnchantmentEntry<InstaKillEnchantment> INSTA_KILL = REGISTRY
			.enchantment("insta_kill", EnchantmentType.WEAPON, InstaKillEnchantment::new)
				.lang("Insta-Kill")
				.lang(RegistrateLangExtProvider.EN_GB, "Insta-Kill")
				.slotType(EquipmentSlotType.MAINHAND)
			.register();

	static void bootstrap() { }

	public static final class InstaKillEnchantment extends Enchantment
	{
		private InstaKillEnchantment(Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType[] slotTypes)
		{
			super(rarity, enchantmentType, slotTypes);
		}

		@Override
		public void doPostAttack(LivingEntity attacker, Entity attacked, int level)
		{
			if(attacked instanceof LivingEntity)
			{
				LivingEntity living = (LivingEntity) attacked;
				living.hurt(DamageSource.GENERIC, living.getMaxHealth() + 1000F);
			}
		}
	}
}
