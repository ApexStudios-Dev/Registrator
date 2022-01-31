package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.ai.village.poi.PoiType;

import xyz.apex.forge.utility.registrator.entry.similar.PointOfInterestLike;

@Mixin(PoiType.class)
public class PointOfInterestTypeMixin implements PointOfInterestLike
{
	@Override
	public PoiType asPointOfInterestType()
	{
		return (PoiType) (Object) this;
	}
}
