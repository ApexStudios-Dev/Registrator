package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

@FunctionalInterface
public interface EntityFactory<ENTITY extends Entity>
{
	ENTITY create(EntityType<ENTITY> entityType, World level);
}
