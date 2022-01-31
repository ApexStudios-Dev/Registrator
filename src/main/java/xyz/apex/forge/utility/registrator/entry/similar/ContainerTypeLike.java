package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.inventory.MenuType;

@FunctionalInterface
public interface ContainerTypeLike
{
	MenuType<?> asContainerType();
}
