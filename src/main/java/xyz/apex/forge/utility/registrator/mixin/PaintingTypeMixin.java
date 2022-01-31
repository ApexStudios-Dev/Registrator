package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.decoration.Motive;

import xyz.apex.forge.utility.registrator.entry.similar.PaintingLike;

@Mixin(Motive.class)
public class PaintingTypeMixin implements PaintingLike
{
	@Override
	public Motive asPaintingType()
	{
		return (Motive) (Object) this;
	}
}
