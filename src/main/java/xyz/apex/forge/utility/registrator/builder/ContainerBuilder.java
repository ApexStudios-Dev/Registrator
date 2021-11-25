package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.DistExecutor;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.ContainerEntry;
import xyz.apex.forge.utility.registrator.factory.ContainerFactory;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public final class ContainerBuilder<OWNER extends AbstractRegistrator<OWNER>, CONTAINER extends Container, PARENT> extends RegistratorBuilder<OWNER, ContainerType<?>, ContainerType<CONTAINER>, PARENT, ContainerBuilder<OWNER, CONTAINER, PARENT>, ContainerEntry<CONTAINER>>
{
	private final ContainerFactory<CONTAINER> containerFactory;
	@Nullable private NonnullSupplier<ContainerFactory.ScreenFactory<CONTAINER, ?>> screenFactory = null;

	public ContainerBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, ContainerFactory<CONTAINER> containerFactory)
	{
		super(owner, parent, registryName, callback, ContainerType.class, ContainerEntry::new, ContainerEntry::cast);

		this.containerFactory = containerFactory;

		onRegister(this::registerScreenFactory);
	}

	private void registerScreenFactory(ContainerType<CONTAINER> containerType)
	{
		if(screenFactory != null)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ScreenManager.register(containerType, screenFactory.get()));
	}

	public ContainerBuilder<OWNER, CONTAINER, PARENT> screen(NonnullSupplier<ContainerFactory.ScreenFactory<CONTAINER, ?>> screenFactory)
	{
		this.screenFactory = screenFactory;
		return this;
	}

	@Override
	protected ContainerType<CONTAINER> createEntry()
	{
		NonNullSupplier<ContainerType<CONTAINER>> supplier = asSupplier();
		return IForgeContainerType.create((windowId, playerInventory, buffer) -> containerFactory.create(supplier.get(), windowId, playerInventory, buffer));
	}
}
