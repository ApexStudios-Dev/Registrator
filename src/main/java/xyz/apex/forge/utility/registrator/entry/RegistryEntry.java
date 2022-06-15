package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

@SuppressWarnings({ "ConstantConditions", "deprecation", "unchecked", "unused" })
public class RegistryEntry<T> extends com.tterrag.registrate.util.entry.RegistryEntry<T> implements NonnullSupplier<T>
{
	public static final com.tterrag.registrate.util.entry.RegistryEntry<?> EMPTY;

	static
	{
		EMPTY = ObfuscationReflectionHelper.getPrivateValue(com.tterrag.registrate.util.entry.RegistryEntry.class, null, "EMPTY");
	}

	protected final AbstractRegistrator<?> registrator;

	public RegistryEntry(AbstractRegistrator<?> registrator, RegistryObject<T> delegate)
	{
		super(registrator.backend, delegate);

		this.registrator = registrator;
	}

	public final AbstractRegistrator<?> getRegistrator()
	{
		return registrator;
	}

	public final <R, E extends R> com.tterrag.registrate.util.entry.RegistryEntry<E> getSibling(ResourceKey<? extends Registry<R>> registryType, String registryNameSuffix)
	{
		return this == EMPTY ? empty() : registrator.get(getId().getPath() + registryNameSuffix, registryType);
	}

	public final <R, E extends R>com.tterrag.registrate.util.entry.RegistryEntry<E> getSibling(IForgeRegistry<R> registryType, String registryNameSuffix)
	{
		return getSibling(registryType.getRegistryKey(), registryNameSuffix);
	}

	public static <E extends RegistryEntry<?>> E cast(Class<? extends Registry<E>> registryEntryClass, RegistryEntry<?> registryEntry)
	{
		if(registryEntryClass.isInstance(registryEntry))
			return (E) registryEntry;
		throw new IllegalArgumentException("Could not convert RegistryEntry: expecting " + registryEntryClass + ", found " + registryEntry.getClass());
	}

	public static <E extends RegistryEntry<?>> E cast0(Class<? extends Registry<E>> registryEntryClass, com.tterrag.registrate.util.entry.RegistryEntry<?> registryEntry)
	{
		if(registryEntryClass.isInstance(registryEntry))
			return (E) registryEntry;
		throw new IllegalArgumentException("Could not convert RegistryEntry: expecting " + registryEntryClass + ", found " + registryEntry.getClass());
	}
}
