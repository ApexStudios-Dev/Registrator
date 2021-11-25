package xyz.apex.forge.testmod.init;

import net.minecraft.util.SoundEvents;

import xyz.apex.forge.utility.registrator.entry.VillagerProfessionEntry;

public class TVillagerProfessions
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final VillagerProfessionEntry TEST = REGISTRY
			.villagerProfession("test_villager_profession")
				.requestItems(TItems.COPPER_INGOT, TItems.COPPER_BLOCK)
				.secondaryPoi(TBlocks.COPPER_BLOCK)
				.workSound(() -> SoundEvents.VILLAGER_WORK_TOOLSMITH)

				.poi()
					.matchingBlock(TBlocks.COPPER_BLOCK)
				.build()
			.register();

	static void bootstrap() { }
}
