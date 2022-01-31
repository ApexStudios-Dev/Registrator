package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import xyz.apex.forge.utility.registrator.entry.similar.BlockEntityTypeLike;

@Mixin(BlockEntity.class)
public class BlockEntityTypeMixin implements BlockEntityTypeLike
{
	@Override
	public BlockEntityType<?> asBlockEntityType()
	{
		return (BlockEntityType<?>) (Object) this;
	}
}
