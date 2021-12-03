package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.similar.EnchantmentLike;

import java.util.Map;

public final class EnchantmentEntry<ENCHANTMENT extends Enchantment> extends RegistryEntry<ENCHANTMENT> implements EnchantmentLike
{
	public EnchantmentEntry(AbstractRegistrator<?> registrator, RegistryObject<ENCHANTMENT> delegate)
	{
		super(registrator, delegate);
	}

	@Override
	public ENCHANTMENT asEnchantment()
	{
		return get();
	}

	public boolean isInEnchantmentTag(ITag<Enchantment> tag)
	{
		return asEnchantment().isIn(tag);
	}

	public boolean isEnchantment(Enchantment enchantment)
	{
		return asEnchantment() == enchantment;
	}

	public boolean isEnchantment(EnchantmentLike enchantment)
	{
		return isEnchantment(enchantment.asEnchantment());
	}

	public Map<EquipmentSlotType, ItemStack> getSlotItems(LivingEntity entity)
	{
		return asEnchantment().getSlotItems(entity);
	}

	public Enchantment.Rarity getRarity()
	{
		return asEnchantment().getRarity();
	}

	public int getMinLevel()
	{
		return asEnchantment().getMinLevel();
	}

	public int getMaxLevel()
	{
		return asEnchantment().getMaxLevel();
	}

	public int getMinCost(int level)
	{
		return asEnchantment().getMinCost(level);
	}

	public  int getMaxCost(int level)
	{
		return asEnchantment().getMaxCost(level);
	}

	public int getDamageProtection(int level, DamageSource damageSource)
	{
		return asEnchantment().getDamageProtection(level, damageSource);
	}

	public float getDamageBonus(int level, CreatureAttribute creatureAttribute)
	{
		return asEnchantment().getDamageBonus(level, creatureAttribute);
	}

	public boolean isCompatibleWith(Enchantment enchantment)
	{
		return asEnchantment().isCompatibleWith(enchantment);
	}

	public boolean isCompatibleWith(EnchantmentLike enchantment)
	{
		return isCompatibleWith(enchantment.asEnchantment());
	}

	public ITextComponent getFullName(int level)
	{
		return asEnchantment().getFullname(level);
	}

	public boolean canEnchant(ItemStack stack)
	{
		return asEnchantment().canEnchant(stack);
	}

	public boolean isTreasureOnly()
	{
		return asEnchantment().isTreasureOnly();
	}

	public boolean isCurse()
	{
		return asEnchantment().isCurse();
	}

	public boolean isDiscoverable()
	{
		return asEnchantment().isDiscoverable();
	}

	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return asEnchantment().canApplyAtEnchantingTable(stack);
	}

	public boolean isAllowedOnBooks()
	{
		return asEnchantment().isAllowedOnBooks();
	}

	public static <ENCHANTMENT extends Enchantment> EnchantmentEntry<ENCHANTMENT> cast(RegistryEntry<ENCHANTMENT> registryEntry)
	{
		return cast(EnchantmentEntry.class, registryEntry);
	}

	public static <ENCHANTMENT extends Enchantment> EnchantmentEntry<ENCHANTMENT> cast(com.tterrag.registrate.util.entry.RegistryEntry<ENCHANTMENT> registryEntry)
	{
		return cast(EnchantmentEntry.class, registryEntry);
	}
}
