package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.RegistryEntry;
import xyz.apex.java.utility.nullness.NonnullBiFunction;
import xyz.apex.java.utility.nullness.NonnullFunction;

@SuppressWarnings("unused")
public abstract class RegistratorBuilder<
		OWNER extends AbstractRegistrator<OWNER>,
		BASE,
		TYPE extends BASE,
		PARENT,
		BUILDER extends RegistratorBuilder<OWNER, BASE, TYPE, PARENT, BUILDER, ENTRY>,
		ENTRY extends RegistryEntry<TYPE>
> extends LegacyRegistratorBuilder<OWNER, BASE, TYPE, PARENT, BUILDER, ENTRY>
{
	private final NonnullBiFunction<OWNER, RegistryObject<TYPE>, ENTRY> registryEntryFactory;

	public RegistratorBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, ResourceKey<? extends Registry<BASE>> registryType, IForgeRegistry<BASE> forgeRegistry, NonnullBiFunction<OWNER, RegistryObject<TYPE>, ENTRY> registryEntryFactory, NonnullFunction<com.tterrag.registrate.util.entry.RegistryEntry<TYPE>, ENTRY> registryEntryCastor)
	{
		super(owner, parent, registryName, callback, registryType, forgeRegistry, registryEntryCastor);

		this.registryEntryFactory = registryEntryFactory;
	}

	@Override
	protected final ENTRY createEntryWrapper(RegistryObject<TYPE> delegate)
	{
		return registryEntryFactory.apply(owner, delegate);
	}
}
