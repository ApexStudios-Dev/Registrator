package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.entity.ai.village.poi.PoiType;

@FunctionalInterface
public interface PointOfInterestLike
{
	PoiType asPointOfInterestType();
}
