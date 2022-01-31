package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.DistExecutor;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.ContainerEntry;
import xyz.apex.forge.utility.registrator.factory.ContainerFactory;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public final class ContainerBuilder<OWNER extends AbstractRegistrator<OWNER>, CONTAINER extends AbstractContainerMenu, SCREEN extends Screen & MenuAccess<CONTAINER>, PARENT> extends RegistratorBuilder<OWNER, MenuType<?>, MenuType<CONTAINER>, PARENT, ContainerBuilder<OWNER, CONTAINER, SCREEN, PARENT>, ContainerEntry<CONTAINER>>
{
	private final ContainerFactory<CONTAINER> containerFactory;
	@Nullable private final NonnullSupplier<ContainerFactory.ScreenFactory<CONTAINER, SCREEN>> screenFactory;

	public ContainerBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, ContainerFactory<CONTAINER> containerFactory, @Nullable NonnullSupplier<ContainerFactory.ScreenFactory<CONTAINER, SCREEN>> screenFactory)
	{
		super(owner, parent, registryName, callback, MenuType.class, ContainerEntry::new, ContainerEntry::cast);

		this.containerFactory = containerFactory;
		this.screenFactory = screenFactory;

		onRegister(this::registerScreenFactory);
	}

	private void registerScreenFactory(MenuType<CONTAINER> containerType)
	{
		if(screenFactory != null)
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MenuScreens.register(containerType, screenFactory.get()::create));
	}

	@Override
	protected MenuType<CONTAINER> createEntry()
	{
		var supplier = asSupplier();
		return IForgeContainerType.create((windowId, playerInventory, buffer) -> containerFactory.create(supplier.get(), windowId, playerInventory, buffer));
	}
}
