package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

@SuppressWarnings("unused")
@FunctionalInterface
public interface ContainerFactory<CONTAINER extends Container>
{
	CONTAINER create(ContainerType<CONTAINER> containerType, int windowId, PlayerInventory playerInventory, PacketBuffer buffer);

	static <CONTAINER extends Container> ContainerFactory<CONTAINER> fromVanilla(VanillaFactory<CONTAINER> containerFactory)
	{
		return (containerType, windowId, playerInventory, buffer) -> containerFactory.create(containerType, windowId, playerInventory);
	}

	static <CONTAINER extends Container> ContainerFactory<CONTAINER> fromVanilla(ContainerType.IFactory<CONTAINER> factory)
	{
		return fromVanilla(VanillaFactory.fromVanilla(factory));
	}

	@FunctionalInterface
	interface VanillaFactory<CONTAINER extends Container>
	{
		CONTAINER create(ContainerType<CONTAINER> containerType, int windowId, PlayerInventory playerInventory);

		static <CONTAINER extends Container> VanillaFactory<CONTAINER> fromVanilla(ContainerType.IFactory<CONTAINER> factory)
		{
			return (containerType, windowId, playerInventory) -> factory.create(windowId, playerInventory);
		}
	}

	@FunctionalInterface
	interface ScreenFactory<CONTAINER extends Container, SCREEN extends Screen & IHasContainer<CONTAINER>>
	{
		SCREEN create(CONTAINER container, PlayerInventory playerInventory, ITextComponent titleComponent);
	}
}
