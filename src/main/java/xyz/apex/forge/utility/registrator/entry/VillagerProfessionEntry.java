package xyz.apex.forge.utility.registrator.entry;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public final class VillagerProfessionEntry extends RegistryEntry<VillagerProfession> implements NonnullSupplier<VillagerProfession>
{
	public VillagerProfessionEntry(AbstractRegistrator<?> owner, RegistryObject<VillagerProfession> delegate)
	{
		super(owner, delegate);
	}

	public VillagerProfession asVillagerProfession()
	{
		return get();
	}

	public boolean is(@Nullable VillagerProfession other)
	{
		return other != null && asVillagerProfession() == other;
	}

	public PointOfInterestType getJobPoiType()
	{
		return asVillagerProfession().getJobPoiType();
	}

	public ImmutableSet<Item> getRequiredItems()
	{
		return asVillagerProfession().getRequestedItems();
	}

	public ImmutableSet<Block> getSecondaryPoi()
	{
		return asVillagerProfession().getSecondaryPoi();
	}

	@Nullable
	public SoundEvent getWorkSound()
	{
		return asVillagerProfession().getWorkSound();
	}

	public static VillagerProfessionEntry cast(RegistryEntry<VillagerProfession> registryEntry)
	{
		return cast(VillagerProfessionEntry.class, registryEntry);
	}

	public static VillagerProfessionEntry cast(com.tterrag.registrate.util.entry.RegistryEntry<VillagerProfession> registryEntry)
	{
		return cast(VillagerProfessionEntry.class, registryEntry);
	}
}
