package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.util.SoundEvent;

@FunctionalInterface
public interface SoundLike
{
	SoundEvent asSoundEvent();
}
