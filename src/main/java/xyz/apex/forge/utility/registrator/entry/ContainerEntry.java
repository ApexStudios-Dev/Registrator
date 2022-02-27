package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullConsumer;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public final class ContainerEntry<CONTAINER extends Container> extends RegistryEntry<ContainerType<CONTAINER>> implements IContainerProvider, NonnullSupplier<ContainerType<CONTAINER>>
{
	public ContainerEntry(AbstractRegistrator<?> registrator, RegistryObject<ContainerType<CONTAINER>> delegate)
	{
		super(registrator, delegate);
	}

	public ContainerType<CONTAINER> asContainerType()
	{
		return get();
	}

	public CONTAINER create(int windowId, PlayerInventory playerInventory, @Nullable PacketBuffer extraData)
	{
		return asContainerType().create(windowId, playerInventory, extraData);
	}

	// wrapper for lambda method references
	public CONTAINER create(int windowId, PlayerInventory playerInventory, PlayerEntity player)
	{
		return create(windowId, playerInventory, (PacketBuffer) null);
	}

	public CONTAINER create(int windowId, PlayerInventory playerInventory)
	{
		return create(windowId, playerInventory, (PacketBuffer) null);
	}

	public INamedContainerProvider asNamedProvider(ITextComponent titleComponent)
	{
		return new SimpleNamedContainerProvider(this, titleComponent);
	}

	public void open(ServerPlayerEntity player, INamedContainerProvider containerProvider, NonnullConsumer<PacketBuffer> extraData)
	{
		NetworkHooks.openGui(player, containerProvider, extraData);
	}

	public void open(ServerPlayerEntity player, INamedContainerProvider containerProvider)
	{
		open(player, containerProvider, NonnullConsumer.noop());
	}

	public void open(ServerPlayerEntity player, ITextComponent titleComponent, NonnullConsumer<PacketBuffer> extraData)
	{
		open(player, asNamedProvider(titleComponent), extraData);
	}

	public void open(ServerPlayerEntity player, ITextComponent titleComponent)
	{
		open(player, asNamedProvider(titleComponent), NonnullConsumer.noop());
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity player)
	{
		return create(windowId, playerInventory, player);
	}

	public static <CONTAINER extends Container> ContainerEntry<CONTAINER> cast(RegistryEntry<ContainerType<CONTAINER>> registryEntry)
	{
		return cast(ContainerEntry.class, registryEntry);
	}

	public static <CONTAINER extends Container> ContainerEntry<CONTAINER> cast(com.tterrag.registrate.util.entry.RegistryEntry<ContainerType<CONTAINER>> registryEntry)
	{
		return cast(ContainerEntry.class, registryEntry);
	}
}
