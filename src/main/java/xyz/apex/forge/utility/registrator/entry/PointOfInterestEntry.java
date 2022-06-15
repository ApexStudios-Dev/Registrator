package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullPredicate;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import java.util.Set;

@SuppressWarnings("unused")
public final class PointOfInterestEntry extends RegistryEntry<PoiType> implements NonnullSupplier<PoiType>, NonnullPredicate<BlockState>
{
	public PointOfInterestEntry(AbstractRegistrator<?> owner, RegistryObject<PoiType> delegate)
	{
		super(owner, delegate);
	}

	public PoiType asPointOfInterestType()
	{
		return get();
	}

	public int getMaxTickets()
	{
		return asPointOfInterestType().maxTickets();
	}

	public int getValidRange()
	{
		return asPointOfInterestType().validRange();
	}

	public Set<BlockState> getBlockStates()
	{
		return asPointOfInterestType().matchingStates();
	}

	public boolean matches(BlockState blockState)
	{
		return asPointOfInterestType().is(blockState);
	}

	@Override
	public boolean test(BlockState blockState)
	{
		return matches(blockState);
	}

	public static PointOfInterestEntry cast(RegistryEntry<PoiType> registryEntry)
	{
		return cast(PointOfInterestEntry.class, registryEntry);
	}

	public static PointOfInterestEntry cast(com.tterrag.registrate.util.entry.RegistryEntry<PoiType> registryEntry)
	{
		return cast(PointOfInterestEntry.class, registryEntry);
	}
}
