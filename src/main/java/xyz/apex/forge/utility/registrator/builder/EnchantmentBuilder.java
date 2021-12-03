package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tags.ITag;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.EnchantmentEntry;
import xyz.apex.forge.utility.registrator.factory.EnchantmentFactory;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public final class EnchantmentBuilder<OWNER extends AbstractRegistrator<OWNER>, ENCHANTMENT extends Enchantment, PARENT> extends RegistratorBuilder<OWNER, Enchantment, ENCHANTMENT, PARENT, EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT>, EnchantmentEntry<ENCHANTMENT>>
{
	private final EnchantmentFactory<ENCHANTMENT> enchantmentFactory;
	private final Set<EquipmentSlotType> slotTypes = EnumSet.noneOf(EquipmentSlotType.class);
	private final EnchantmentType enchantmentType;
	private Enchantment.Rarity rarity = Enchantment.Rarity.COMMON;

	public EnchantmentBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, EnchantmentType enchantmentType, EnchantmentFactory<ENCHANTMENT> enchantmentFactory)
	{
		super(owner, parent, registryName, callback, Enchantment.class, EnchantmentEntry::new, EnchantmentEntry::cast);

		this.enchantmentType = enchantmentType;
		this.enchantmentFactory = enchantmentFactory;
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> rarity(Enchantment.Rarity rarity)
	{
		this.rarity = rarity;
		return this;
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> slotType(EquipmentSlotType slotType)
	{
		slotTypes.add(slotType);
		return this;
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> slotTypes(EquipmentSlotType... slotTypes)
	{
		Collections.addAll(this.slotTypes, slotTypes);
		return this;
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> armorSlotTypes()
	{
		return slotTypes(EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET);
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> defaultLang()
	{
		return lang(Enchantment::getDescriptionId);
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> lang(String name)
	{
		return lang(Enchantment::getDescriptionId, name);
	}

	@SafeVarargs
	public final EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> tag(ITag.INamedTag<Enchantment>... tags)
	{
		return tag(AbstractRegistrator.ENCHANTMENT_TAGS_PROVIDER, tags);
	}

	@SafeVarargs
	public final EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> removeTag(ITag.INamedTag<Enchantment>... tags)
	{
		return removeTags(AbstractRegistrator.ENCHANTMENT_TAGS_PROVIDER, tags);
	}

	public EnchantmentBuilder<OWNER, ENCHANTMENT, PARENT> lang(String languageKey, String localizedValue)
	{
		return lang(languageKey, Enchantment::getDescriptionId, localizedValue);
	}

	@Override
	protected @NonnullType ENCHANTMENT createEntry()
	{
		EquipmentSlotType[] slotTypes = this.slotTypes.toArray(new EquipmentSlotType[0]);
		return enchantmentFactory.create(rarity, enchantmentType, slotTypes);
	}
}
