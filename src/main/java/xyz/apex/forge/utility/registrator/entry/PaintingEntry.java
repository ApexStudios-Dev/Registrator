package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.entity.item.PaintingType;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

@SuppressWarnings("unused")
public final class PaintingEntry extends RegistryEntry<PaintingType> implements NonnullSupplier<PaintingType>
{
	public PaintingEntry(AbstractRegistrator<?> owner, RegistryObject<PaintingType> delegate)
	{
		super(owner, delegate);
	}

	public PaintingType asPaintingType()
	{
		return get();
	}

	public int getWidth()
	{
		return asPaintingType().getWidth();
	}

	public int getHeight()
	{
		return asPaintingType().getHeight();
	}

	public static PaintingEntry cast(RegistryEntry<PaintingType> registryEntry)
	{
		return cast(PaintingEntry.class, registryEntry);
	}

	public static PaintingEntry cast(com.tterrag.registrate.util.entry.RegistryEntry<PaintingType> registryEntry)
	{
		return cast(PaintingEntry.class, registryEntry);
	}
}
