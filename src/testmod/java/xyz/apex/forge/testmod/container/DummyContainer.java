package xyz.apex.forge.testmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;

public class DummyContainer extends Container
{
	public DummyContainer(@Nullable ContainerType<?> containerType, int windowId, PlayerInventory playerInventory, PacketBuffer extraData)
	{
		super(containerType, windowId);
	}

	@Override
	public boolean stillValid(PlayerEntity player)
	{
		return true;
	}
}
