package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.tileentity.TileEntityType;

import xyz.apex.forge.utility.registrator.entry.similar.BlockEntityTypeLike;

@Mixin(TileEntityType.class)
public class BlockEntityTypeMixin implements BlockEntityTypeLike
{
	@Override
	public TileEntityType<?> asBlockEntityType()
	{
		return (TileEntityType<?>) (Object) this;
	}
}
