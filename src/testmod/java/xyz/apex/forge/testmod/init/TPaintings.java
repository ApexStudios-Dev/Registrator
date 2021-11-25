package xyz.apex.forge.testmod.init;

import xyz.apex.forge.utility.registrator.entry.PaintingEntry;

public final class TPaintings
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final PaintingEntry APEX_LOGO = REGISTRY.painting("apex_logo", 32, 32);
	public static final PaintingEntry APEX_BANNER = REGISTRY.painting("apex_banner", 64, 32);

	static void bootstrap() { }
}
