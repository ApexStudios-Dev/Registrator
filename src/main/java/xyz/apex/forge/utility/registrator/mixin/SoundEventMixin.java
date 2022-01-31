package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.sounds.SoundEvent;

import xyz.apex.forge.utility.registrator.entry.similar.SoundLike;

@Mixin(SoundEvent.class)
public class SoundEventMixin implements SoundLike
{
	@Override
	public SoundEvent asSoundEvent()
	{
		return (SoundEvent) (Object) this;
	}
}
