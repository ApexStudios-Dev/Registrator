package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.EntityType;

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
