package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.tags.ITag;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.LazyRegistryEntry;
import xyz.apex.forge.utility.registrator.entry.RegistryEntry;
import xyz.apex.java.utility.nullness.NonnullBiConsumer;
import xyz.apex.java.utility.nullness.NonnullConsumer;
import xyz.apex.java.utility.nullness.NonnullFunction;
import xyz.apex.java.utility.nullness.NonnullSupplier;

public abstract class LegacyRegistratorBuilder<
		OWNER extends AbstractRegistrator<OWNER>,
		BASE extends IForgeRegistryEntry<BASE>,
		TYPE extends BASE,
		PARENT,
		BUILDER extends LegacyRegistratorBuilder<OWNER, BASE, TYPE, PARENT, BUILDER, ENTRY>,
		ENTRY extends RegistryEntry<TYPE>
> extends AbstractBuilder<BASE, TYPE, PARENT, BUILDER>
{
	protected final OWNER owner;
	private final NonnullFunction<com.tterrag.registrate.util.entry.RegistryEntry<TYPE>, ENTRY> registryEntryCastor;
	private final LazyRegistryEntry<TYPE> safeSupplier = new LazyRegistryEntry<TYPE>(() -> (RegistryEntry<TYPE>) get());

	public LegacyRegistratorBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, Class<? super BASE> registryType, NonnullFunction<com.tterrag.registrate.util.entry.RegistryEntry<TYPE>, ENTRY> registryEntryCastor)
	{
		super(owner.backend, parent, registryName, callback, registryType);

		this.owner = owner;
		this.registryEntryCastor = registryEntryCastor;
	}

	@Override
	protected abstract ENTRY createEntryWrapper(RegistryObject<TYPE> delegate);

	@Override
	public final ENTRY register()
	{
		return registryEntryCastor.apply(super.register());
	}

	public final String getRegistryNameFull()
	{
		return owner.idString(getName());
	}

	@SafeVarargs
	public final BUILDER removeTags(ProviderType<? extends RegistrateTagsProvider<BASE>> providerType, ITag.INamedTag<BASE>... tags)
	{
		return removeTag((ProviderType<RegistrateTagsProvider<BASE>>) providerType, tags);
	}

	// region: RegistrateBuilder
	public final OWNER getRegistrator()
	{
		return owner;
	}

	public final String getRegistryName()
	{
		return getName();
	}

	public final <PROVIDER extends RegistrateProvider> BUILDER setDataGenerator(ProviderType<? extends PROVIDER> providerType, NonnullBiConsumer<DataGenContext<BASE, TYPE>, PROVIDER> consumer)
	{
		return setData(providerType, consumer::accept);
	}

	public final <PROVIDER extends RegistrateProvider> BUILDER clearDataGenerator(ProviderType<? extends PROVIDER> providerType)
	{
		return setDataGenerator(providerType, NonnullBiConsumer.noop());
	}

	public final <PROVIDER extends RegistrateProvider> BUILDER addMiscDataGenerator(ProviderType<? extends PROVIDER> providerType, NonnullConsumer<? super PROVIDER> consumer)
	{
		return addMiscData(providerType, consumer::accept);
	}

	public final <OWNER_T extends AbstractRegistrator<OWNER_T>, BASE_T extends IForgeRegistryEntry<BASE_T>, TYPE_T extends BASE_T, PARENT_T, BUILDER_T extends LegacyRegistratorBuilder<OWNER_T, BASE_T, TYPE_T, PARENT_T, BUILDER_T, ENTRY_T>, ENTRY_T extends RegistryEntry<TYPE_T>> BUILDER_T transformer(NonnullFunction<BUILDER, BUILDER_T> transformer)
	{
		return transform(transformer::apply);
	}

	public final BUILDER lang(String languageKey, NonnullFunction<TYPE, String> translationKeyProvider, String localizedValue)
	{
		return setDataGenerator(AbstractRegistrator.LANG_EXT_PROVIDER, (ctx, provider) -> provider.add(languageKey, translationKeyProvider.apply(ctx.get()), localizedValue));
	}
	// endregion

	// region: Finalize Methods
	@Override
	protected final BuilderCallback getCallback()
	{
		return super.getCallback();
	}

	@Override
	public final String getName()
	{
		return super.getName();
	}

	@Deprecated
	@Override
	public final AbstractRegistrate<?> getOwner()
	{
		return super.getOwner();
	}

	@Override
	public final PARENT getParent()
	{
		return super.getParent();
	}

	@Override
	public final Class<? super BASE> getRegistryType()
	{
		return super.getRegistryType();
	}

	@Deprecated
	@Override
	public final NonNullSupplier<TYPE> asSupplier()
	{
		return safeSupplier;
	}

	public final NonnullSupplier<TYPE> toSupplier()
	{
		return safeSupplier;
	}

	@Override
	public final BUILDER lang(net.minecraftforge.common.util.NonNullFunction<TYPE, String> langKeyProvider)
	{
		return super.lang( langKeyProvider);
	}

	@Override
	public final BUILDER lang(net.minecraftforge.common.util.NonNullFunction<TYPE, String> langKeyProvider, String name)
	{
		return super.lang(langKeyProvider, name);
	}

	@Override
	public final com.tterrag.registrate.util.entry.RegistryEntry<TYPE> get()
	{
		return owner.<BASE, TYPE> get(getName(), getRegistryType());
	}

	@Override
	public final TYPE getEntry()
	{
		return super.getEntry();
	}

	@Override
	public final <PROVIDER extends RegistrateProvider> BUILDER setData(ProviderType<? extends PROVIDER> providerType, NonNullBiConsumer<DataGenContext<BASE, TYPE>, PROVIDER> consumer)
	{
		return super.setData(providerType, consumer);
	}

	@Override
	public final <PROVIDER extends RegistrateProvider> BUILDER addMiscData(ProviderType<? extends PROVIDER> providerType, NonNullConsumer<? extends PROVIDER> consumer)
	{
		return super.addMiscData(providerType, consumer);
	}

	@Override
	public final BUILDER onRegister(NonNullConsumer<? super TYPE> callback)
	{
		return super.onRegister(callback);
	}

	@Override
	public final <OR extends IForgeRegistryEntry<OR>> BUILDER onRegisterAfter(Class<? super OR> dependencyType, NonNullConsumer<? super TYPE> callback)
	{
		return super.onRegisterAfter(dependencyType, callback);
	}

	@Deprecated
	@Override
	public final <BASE_T extends IForgeRegistryEntry<BASE_T>, TYPE_T extends BASE_T, PARENT_T, BUILDER_T extends Builder<BASE_T, TYPE_T, PARENT_T, BUILDER_T>> BUILDER_T transform(NonNullFunction<BUILDER, BUILDER_T> transformer)
	{
		return super.transform(transformer);
	}

	@Override
	public final PARENT build()
	{
		return super.build();
	}
	// endregion
}
