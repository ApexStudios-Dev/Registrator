package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.sounds.SoundEvent;

@FunctionalInterface
public interface SoundLike
{
	SoundEvent asSoundEvent();
}
