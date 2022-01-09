package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.block.Block;

import xyz.apex.forge.utility.registrator.entry.similar.BlockLike;

@Mixin(Block.class)
public class BlockMixin implements BlockLike
{
	@Override
	public Block asBlock()
	{
		return (Block) (Object) this;
	}
}
