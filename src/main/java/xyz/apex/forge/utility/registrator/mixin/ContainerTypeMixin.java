package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.inventory.container.ContainerType;

import xyz.apex.forge.utility.registrator.entry.similar.ContainerTypeLike;

@Mixin(ContainerType.class)
public class ContainerTypeMixin implements ContainerTypeLike
{
	@Override
	public ContainerType<?> asContainerType()
	{
		return (ContainerType<?>) (Object) this;
	}
}
