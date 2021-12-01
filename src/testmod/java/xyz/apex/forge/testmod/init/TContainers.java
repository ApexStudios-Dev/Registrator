package xyz.apex.forge.testmod.init;

import xyz.apex.forge.testmod.client.screen.DummyContainerScreen;
import xyz.apex.forge.testmod.container.DummyContainer;
import xyz.apex.forge.utility.registrator.entry.ContainerEntry;

public final class TContainers
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final ContainerEntry<DummyContainer> DUMMY = REGISTRY
			.container(
					"dummy",
					DummyContainer::new,
					() -> DummyContainerScreen::new
			).register();

	static void bootstrap() { }
}
