package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.village.PointOfInterestType;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.PointOfInterestEntry;
import xyz.apex.java.utility.nullness.NonnullBiPredicate;
import xyz.apex.java.utility.nullness.NonnullPredicate;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public final class PointOfInterestBuilder<OWNER extends AbstractRegistrator<OWNER>, PARENT> extends RegistratorBuilder<OWNER, PointOfInterestType, PointOfInterestType, PARENT, PointOfInterestBuilder<OWNER, PARENT>, PointOfInterestEntry>
{
	private NonnullSupplier<? extends Block> block = () -> Blocks.AIR;
	private int maxTickets = 1;
	private Predicate predicate = (testType, thisType) -> testType == thisType;
	private int validRange = 1;

	public PointOfInterestBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback)
	{
		super(owner, parent, registryName, callback, PointOfInterestType.class, PointOfInterestEntry::new, PointOfInterestEntry::cast);
	}

	public PointOfInterestBuilder<OWNER, PARENT> matchingBlock(NonnullSupplier<? extends Block> block)
	{
		this.block = block;
		return this;
	}

	public PointOfInterestBuilder<OWNER, PARENT> maxTickets(int maxTickets)
	{
		this.maxTickets = maxTickets;
		return this;
	}

	public PointOfInterestBuilder<OWNER, PARENT> predicate(Predicate predicate)
	{
		this.predicate = predicate;
		return this;
	}

	public PointOfInterestBuilder<OWNER, PARENT> validRange(int validRange)
	{
		this.validRange = validRange;
		return this;
	}

	@Override
	protected @NonnullType PointOfInterestType createEntry()
	{
		AtomicReference<PointOfInterestType> result = new AtomicReference<>();
		NonnullPredicate<PointOfInterestType> predicate = poiType -> this.predicate.apply(poiType, result.get());
		Set<BlockState> matchingBlockStates = PointOfInterestType.getBlockStates(block.get());
		String registryName = getRegistryNameFull();
		PointOfInterestType poiType = new PointOfInterestType(registryName, matchingBlockStates, maxTickets, predicate, validRange);
		result.set(poiType);
		return poiType;
	}

	@FunctionalInterface
	public interface Predicate extends NonnullBiPredicate<PointOfInterestType, PointOfInterestType>
	{
		@Override
		boolean test(PointOfInterestType testType, PointOfInterestType yourType);
	}
}
