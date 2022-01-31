package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.inventory.MenuType;

import xyz.apex.forge.utility.registrator.entry.similar.ContainerTypeLike;

@Mixin(MenuType.class)
public class ContainerTypeMixin implements ContainerTypeLike
{
	@Override
	public MenuType<?> asContainerType()
	{
		return (MenuType<?>) (Object) this;
	}
}
