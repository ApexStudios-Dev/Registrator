package xyz.apex.forge.utility.registrator.entry;

import com.google.common.collect.ImmutableSet;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;
import java.util.function.Predicate;

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

	public Predicate<Holder<PoiType>> getHeldJobSite()
	{
		return asVillagerProfession().heldJobSite();
	}

	public Predicate<Holder<PoiType>> getAcquirableJobSite()
	{
		return asVillagerProfession().acquirableJobSite();
	}

	public ImmutableSet<Item> getRequiredItems()
	{
		return asVillagerProfession().requestedItems();
	}

	public ImmutableSet<Block> getSecondaryPoi()
	{
		return asVillagerProfession().secondaryPoi();
	}

	@Nullable
	public SoundEvent getWorkSound()
	{
		return asVillagerProfession().workSound();
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
