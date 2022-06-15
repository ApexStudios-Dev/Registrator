package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

@SuppressWarnings("unused")
public final class PaintingEntry extends RegistryEntry<PaintingVariant> implements NonnullSupplier<PaintingVariant>
{
	public PaintingEntry(AbstractRegistrator<?> owner, RegistryObject<PaintingVariant> delegate)
	{
		super(owner, delegate);
	}

	public PaintingVariant asPaintingType()
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

	public static PaintingEntry cast(RegistryEntry<PaintingVariant> registryEntry)
	{
		return cast(PaintingEntry.class, registryEntry);
	}

	public static PaintingEntry cast(com.tterrag.registrate.util.entry.RegistryEntry<PaintingVariant> registryEntry)
	{
		return cast(PaintingEntry.class, registryEntry);
	}
}
