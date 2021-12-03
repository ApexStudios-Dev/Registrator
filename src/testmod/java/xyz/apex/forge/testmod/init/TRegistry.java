package xyz.apex.forge.testmod.init;

import xyz.apex.forge.testmod.TestMod;
import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.helper.RegistratorItemGroup;
import xyz.apex.java.utility.Lazy;

public final class TRegistry extends AbstractRegistrator<TRegistry>
{
	private static final Lazy<TRegistry> registry = create(TRegistry::new);
	private static boolean bootstrap = false;

	private TRegistry()
	{
		super(TestMod.ID);

		skipErrors(false).itemGroup(() -> RegistratorItemGroup.create(this), "Test Mod");
	}

	public static void bootstrap()
	{
		if(!bootstrap)
		{
			TSounds.bootstrap();
			TElements.bootstrap();
			TPaintings.bootstrap();

			TItems.bootstrap();
			TBlocks.bootstrap();
			TBlockEntityTypes.bootstrap();
			TContainers.bootstrap();
			TEntityTypes.bootstrap();
			TTags.bootstrap();
			TVillagerProfessions.bootstrap();
			TPointOfInterestTypes.bootstrap();
			TEnchantments.bootstrap();

			TStructures.bootstrap();

			bootstrap = true;
		}
	}

	public static TRegistry getRegistry()
	{
		return registry.get();
	}
}
