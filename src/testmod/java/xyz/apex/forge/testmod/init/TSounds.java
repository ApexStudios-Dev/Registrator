package xyz.apex.forge.testmod.init;

import xyz.apex.forge.utility.registrator.entry.SoundEntry;

public class TSounds
{
	private static TRegistry REGISTRY = TRegistry.getRegistry();

	public static final SoundEntry COPPER_BREAK = REGISTRY
			.sound("block.copper.break")
				.withSimpleSound("block/copper/break1")
				.withSimpleSound("block/copper/break2")
				.withSimpleSound("block/copper/break3")
				.withSimpleSound("block/copper/break4")
				.subtitle("subtitles.block.generic.break")
			.register();

	public static final SoundEntry COPPER_STEP = REGISTRY
			.sound("block.copper.step")
				.withSimpleSound("block/copper/step1")
				.withSimpleSound("block/copper/step2")
				.withSimpleSound("block/copper/step3")
				.withSimpleSound("block/copper/step4")
				.withSimpleSound("block/copper/step5")
				.withSimpleSound("block/copper/step6")
				.subtitle("subtitles.block.generic.footsteps")
			.register();

	public static final SoundEntry COPPER_PLACE = REGISTRY
			.sound("block.copper.place")
				.withSimpleSound("block/copper/break1")
				.withSimpleSound("block/copper/break2")
				.withSimpleSound("block/copper/break3")
				.withSimpleSound("block/copper/break4")
				.subtitle("subtitles.block.generic.place")
			.register();

	public static final SoundEntry COPPER_HIT = REGISTRY
			.sound("block.copper.hit")
				.withSimpleSound("block/copper/step1")
				.withSimpleSound("block/copper/step2")
				.withSimpleSound("block/copper/step3")
				.withSimpleSound("block/copper/step4")
				.withSimpleSound("block/copper/step5")
				.withSimpleSound("block/copper/step6")
				.subtitle("subtitles.block.generic.hit")
			.register();

	public static final SoundEntry COPPER_FALL = REGISTRY
			.sound("block.copper.fall")
				.withSimpleSound("block/copper/step1")
				.withSimpleSound("block/copper/step2")
				.withSimpleSound("block/copper/step3")
				.withSimpleSound("block/copper/step4")
				.withSimpleSound("block/copper/step5")
				.withSimpleSound("block/copper/step6")
			.register();

	static void bootstrap() { }
}
