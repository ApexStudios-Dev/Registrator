package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.similar.ContainerTypeLike;
import xyz.apex.java.utility.nullness.NonnullConsumer;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public final class ContainerEntry<CONTAINER extends AbstractContainerMenu> extends RegistryEntry<MenuType<CONTAINER>> implements ContainerTypeLike, MenuConstructor, NonnullSupplier<MenuType<CONTAINER>>
{
	public ContainerEntry(AbstractRegistrator<?> registrator, RegistryObject<MenuType<CONTAINER>> delegate)
	{
		super(registrator, delegate);
	}

	@Override
	public MenuType<CONTAINER> asContainerType()
	{
		return get();
	}

	public CONTAINER create(int windowId, Inventory playerInventory, @Nullable FriendlyByteBuf extraData)
	{
		return asContainerType().create(windowId, playerInventory, extraData);
	}

	// wrapper for lambda method references
	public CONTAINER create(int windowId, Inventory playerInventory, Player player)
	{
		return create(windowId, playerInventory, (FriendlyByteBuf) null);
	}

	public CONTAINER create(int windowId, Inventory playerInventory)
	{
		return create(windowId, playerInventory, (FriendlyByteBuf) null);
	}

	public MenuProvider asNamedProvider(Component titleComponent)
	{
		return new SimpleMenuProvider(this, titleComponent);
	}

	public void open(ServerPlayer player, MenuProvider containerProvider, NonnullConsumer<FriendlyByteBuf> extraData)
	{
		NetworkHooks.openGui(player, containerProvider, extraData);
	}

	public void open(ServerPlayer player, MenuProvider containerProvider)
	{
		open(player, containerProvider, NonnullConsumer.noop());
	}

	public void open(ServerPlayer player, Component titleComponent, NonnullConsumer<FriendlyByteBuf> extraData)
	{
		open(player, asNamedProvider(titleComponent), extraData);
	}

	public void open(ServerPlayer player, Component titleComponent)
	{
		open(player, asNamedProvider(titleComponent), NonnullConsumer.noop());
	}

	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player)
	{
		return create(windowId, playerInventory, player);
	}

	public static <CONTAINER extends AbstractContainerMenu> ContainerEntry<CONTAINER> cast(RegistryEntry<MenuType<CONTAINER>> registryEntry)
	{
		return cast(ContainerEntry.class, registryEntry);
	}

	public static <CONTAINER extends AbstractContainerMenu> ContainerEntry<CONTAINER> cast(com.tterrag.registrate.util.entry.RegistryEntry<MenuType<CONTAINER>> registryEntry)
	{
		return cast(ContainerEntry.class, registryEntry);
	}
}
