package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.entity.EntityType;

@FunctionalInterface
public interface EntityTypeLike
{
	EntityType<?> asEntityType();
}
