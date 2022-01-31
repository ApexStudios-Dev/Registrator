package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.level.block.entity.BlockEntityType;

@FunctionalInterface
public interface BlockEntityTypeLike
{
	BlockEntityType<?> asBlockEntityType();
}
