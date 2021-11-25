package xyz.apex.forge.utility.registrator.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.item.PaintingType;

import xyz.apex.forge.utility.registrator.entry.similar.PaintingLike;

@Mixin(PaintingType.class)
public class PaintingTypeMixin implements PaintingLike
{
	@Override
	public PaintingType asPaintingType()
	{
		return (PaintingType) (Object) this;
	}
}
