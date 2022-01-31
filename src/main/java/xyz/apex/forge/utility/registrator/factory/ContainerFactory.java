package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

@SuppressWarnings("unused")
@FunctionalInterface
public interface ContainerFactory<CONTAINER extends AbstractContainerMenu>
{
	CONTAINER create(MenuType<CONTAINER> containerType, int windowId, Inventory playerInventory, FriendlyByteBuf buffer);

	static <CONTAINER extends AbstractContainerMenu> ContainerFactory<CONTAINER> fromVanilla(VanillaFactory<CONTAINER> containerFactory)
	{
		return (containerType, windowId, playerInventory, buffer) -> containerFactory.create(containerType, windowId, playerInventory);
	}

	static <CONTAINER extends AbstractContainerMenu> ContainerFactory<CONTAINER> fromVanilla(MenuType.MenuSupplier<CONTAINER> factory)
	{
		return fromVanilla(VanillaFactory.fromVanilla(factory));
	}

	@FunctionalInterface
	interface VanillaFactory<CONTAINER extends AbstractContainerMenu>
	{
		CONTAINER create(MenuType<CONTAINER> containerType, int windowId, Inventory playerInventory);

		static <CONTAINER extends AbstractContainerMenu> VanillaFactory<CONTAINER> fromVanilla(MenuType.MenuSupplier<CONTAINER> factory)
		{
			return (containerType, windowId, playerInventory) -> factory.create(windowId, playerInventory);
		}
	}

	@FunctionalInterface
	interface ScreenFactory<CONTAINER extends AbstractContainerMenu, SCREEN extends Screen & MenuAccess<CONTAINER>>
	{
		SCREEN create(CONTAINER container, Inventory playerInventory, Component titleComponent);
	}
}
