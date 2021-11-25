package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.similar.SoundLike;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

public final class SoundEntry extends RegistryEntry<SoundEvent> implements SoundLike, NonnullSupplier<SoundEvent>
{
	public SoundEntry(AbstractRegistrator<?> owner, RegistryObject<SoundEvent> delegate)
	{
		super(owner, delegate);
	}

	public void play(World level, @Nullable PlayerEntity player, BlockPos pos, SoundCategory category, float pitch, float volume)
	{
		level.playSound(player, pos, asSoundEvent(), category, pitch, volume);
	}

	public void play(World level, @Nullable PlayerEntity player, BlockPos pos, SoundCategory category, float pitch)
	{
		play(level, player, pos, category, pitch, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, BlockPos pos, SoundCategory category)
	{
		play(level, player, pos, category, 1F);
	}

	public void play(World level, BlockPos pos, SoundCategory category, float pitch, float volume)
	{
		play(level, null, pos, category, pitch, volume);
	}

	public void play(World level, BlockPos pos, SoundCategory category, float pitch)
	{
		play(level, pos, category, pitch, 1F);
	}

	public void play(World level, BlockPos pos, SoundCategory category)
	{
		play(level, pos, category, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, double x, double y, double z, SoundCategory category, float pitch, float volume)
	{
		level.playSound(player, x, y, z, asSoundEvent(), category, pitch, volume);
	}

	public void play(World level, @Nullable PlayerEntity player, double x, double y, double z, SoundCategory category, float pitch)
	{
		play(level, player, x, y, z, category, pitch, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, double x, double y, double z, SoundCategory category)
	{
		play(level, player, x, y, z, category, 1F);
	}

	public void play(World level, double x, double y, double z, SoundCategory category, float pitch, float volume)
	{
		play(level, null, x, y, z, category, pitch, volume);
	}

	public void play(World level, double x, double y, double z, SoundCategory category, float pitch)
	{
		play(level, x, y, z, category, pitch, 1F);
	}

	public void play(World level, double x, double y, double z, SoundCategory category)
	{
		play(level, x, y, z, category, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, Entity entity, SoundCategory category, float pitch, float volume)
	{
		level.playSound(player, entity, asSoundEvent(), category, pitch, volume);
	}

	public void play(World level, @Nullable PlayerEntity player, Entity entity, SoundCategory category, float pitch)
	{
		play(level, player, entity, category, pitch, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, Entity entity, SoundCategory category)
	{
		play(level, player, entity, category, 1F);
	}

	public void play(World level, Entity entity, SoundCategory category, float pitch, float volume)
	{
		play(level, null, entity, category, pitch, volume);
	}

	public void play(World level, Entity entity, SoundCategory category, float pitch)
	{
		play(level, entity, category, pitch, 1F);
	}

	public void play(World level, Entity entity, SoundCategory category)
	{
		play(level, entity, category, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, Entity entity, float pitch, float volume)
	{
		play(level, player, entity, entity.getSoundSource(), pitch, volume);
	}

	public void play(World level, @Nullable PlayerEntity player, Entity entity, float pitch)
	{
		play(level, player, entity, pitch, 1F);
	}

	public void play(World level, @Nullable PlayerEntity player, Entity entity)
	{
		play(level, player, entity, 1F);
	}

	public void play(World level, Entity entity, float pitch, float volume)
	{
		play(level, null, entity, pitch, volume);
	}

	public void play(World level, Entity entity, float pitch)
	{
		play(level, entity, pitch, 1F);
	}

	public void play(World level, Entity entity)
	{
		play(level, entity, 1F);
	}

	public void play(Entity entity, float pitch, float volume)
	{
		entity.playSound(asSoundEvent(), pitch, volume);
	}

	public void play(Entity entity, float pitch)
	{
		play(entity, pitch, 1F);
	}

	public void play(Entity entity)
	{
		play(entity, 1F);
	}

	@Override
	public SoundEvent asSoundEvent()
	{
		return get();
	}

	public ResourceLocation getSoundLocation()
	{
		return asSoundEvent().getLocation();
	}

	public static SoundEntry cast(RegistryEntry<SoundEvent> registryEntry)
	{
		return cast(SoundEntry.class, registryEntry);
	}

	public static SoundEntry cast(com.tterrag.registrate.util.entry.RegistryEntry<SoundEvent> registryEntry)
	{
		return cast(SoundEntry.class, registryEntry);
	}
}
