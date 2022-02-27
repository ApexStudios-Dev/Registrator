package xyz.apex.forge.utility.registrator.entry;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import java.util.function.Predicate;

@SuppressWarnings("unused")
public final class PointOfInterestEntry extends RegistryEntry<PointOfInterestType> implements NonnullSupplier<PointOfInterestType>
{
	public PointOfInterestEntry(AbstractRegistrator<?> owner, RegistryObject<PointOfInterestType> delegate)
	{
		super(owner, delegate);
	}

	public PointOfInterestType asPointOfInterestType()
	{
		return get();
	}

	public int getMaxTickets()
	{
		return asPointOfInterestType().getMaxTickets();
	}

	public Predicate<PointOfInterestType> getPredicate()
	{
		return asPointOfInterestType().getPredicate();
	}

	public int getValidRange()
	{
		return asPointOfInterestType().getValidRange();
	}

	public ImmutableSet<BlockState> getBlockStates()
	{
		return asPointOfInterestType().getBlockStates();
	}

	public static PointOfInterestEntry cast(RegistryEntry<PointOfInterestType> registryEntry)
	{
		return cast(PointOfInterestEntry.class, registryEntry);
	}

	public static PointOfInterestEntry cast(com.tterrag.registrate.util.entry.RegistryEntry<PointOfInterestType> registryEntry)
	{
		return cast(PointOfInterestEntry.class, registryEntry);
	}
}
