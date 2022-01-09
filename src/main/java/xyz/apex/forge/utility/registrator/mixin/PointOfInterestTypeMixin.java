package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.village.PointOfInterestType;

import xyz.apex.forge.utility.registrator.entry.similar.PointOfInterestLike;

@Mixin(PointOfInterestType.class)
public class PointOfInterestTypeMixin implements PointOfInterestLike
{
	@Override
	public PointOfInterestType asPointOfInterestType()
	{
		return (PointOfInterestType) (Object) this;
	}
}
