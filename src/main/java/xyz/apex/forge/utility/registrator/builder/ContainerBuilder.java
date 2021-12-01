package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
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
public final class ContainerBuilder<OWNER extends AbstractRegistrator<OWNER>, CONTAINER extends Container, SCREEN extends Screen & IHasContainer<CONTAINER>, PARENT> extends RegistratorBuilder<OWNER, ContainerType<?>, ContainerType<CONTAINER>, PARENT, ContainerBuilder<OWNER, CONTAINER, SCREEN, PARENT>, ContainerEntry<CONTAINER>>
{
	private final ContainerFactory<CONTAINER> containerFactory;
	@Nullable private final NonnullSupplier<ContainerFactory.ScreenFactory<CONTAINER, SCREEN>> screenFactory;

	public ContainerBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, ContainerFactory<CONTAINER> containerFactory, @Nullable NonnullSupplier<ContainerFactory.ScreenFactory<CONTAINER, SCREEN>> screenFactory)
	{
		super(owner, parent, registryName, callback, ContainerType.class, ContainerEntry::new, ContainerEntry::cast);

		this.containerFactory = containerFactory;
		this.screenFactory = screenFactory;

		onRegister(this::registerScreenFactory);
	}

	private void registerScreenFactory(ContainerType<CONTAINER> containerType)
	{
		if(screenFactory != null)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ScreenManager.register(containerType, screenFactory.get()::create));
	}

	@Override
	protected ContainerType<CONTAINER> createEntry()
	{
		NonNullSupplier<ContainerType<CONTAINER>> supplier = asSupplier();
		return IForgeContainerType.create((windowId, playerInventory, buffer) -> containerFactory.create(supplier.get(), windowId, playerInventory, buffer));
	}
}
