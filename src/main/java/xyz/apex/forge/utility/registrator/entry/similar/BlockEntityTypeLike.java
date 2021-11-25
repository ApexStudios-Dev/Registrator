package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.tileentity.TileEntityType;

@FunctionalInterface
public interface BlockEntityTypeLike
{
	TileEntityType<?> asBlockEntityType();
}
