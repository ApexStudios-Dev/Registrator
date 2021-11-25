package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.inventory.container.ContainerType;

@FunctionalInterface
public interface ContainerTypeLike
{
	ContainerType<?> asContainerType();
}
