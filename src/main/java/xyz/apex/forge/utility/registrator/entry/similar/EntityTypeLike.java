package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.entity.EntityType;

@FunctionalInterface
public interface EntityTypeLike
{
	EntityType<?> asEntityType();
}
