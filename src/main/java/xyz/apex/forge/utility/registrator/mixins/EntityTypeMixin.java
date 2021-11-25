package xyz.apex.forge.utility.registrator.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;

import xyz.apex.forge.utility.registrator.entry.similar.EntityTypeLike;

@Mixin(EntityType.class)
public class EntityTypeMixin implements EntityTypeLike
{
	@Override
	public EntityType<?> asEntityType()
	{
		return (EntityType<?>) (Object) this;
	}
}
