package xyz.apex.forge.testmod.init;

import net.minecraft.village.PointOfInterestType;

import xyz.apex.forge.utility.registrator.entry.PointOfInterestEntry;
import xyz.apex.forge.utility.registrator.entry.VillagerProfessionEntry;

@SuppressWarnings({ "unused", "SameParameterValue" })
public final class TPointOfInterestTypes
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final PointOfInterestEntry TEST_VILLAGER_PROFESSION = villagerProfession(TVillagerProfessions.TEST);

	private static PointOfInterestEntry villagerProfession(VillagerProfessionEntry professionEntry)
	{
		return PointOfInterestEntry.cast(professionEntry.getSibling(PointOfInterestType.class));
	}

	static void bootstrap() { }
}
