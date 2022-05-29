package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

public final class ParticleEntry<PARTICLE extends IParticleData> extends RegistryEntry<ParticleType<PARTICLE>> implements NonnullSupplier<ParticleType<PARTICLE>>
{
	public ParticleEntry(AbstractRegistrator<?> registrator, RegistryObject<ParticleType<PARTICLE>> delegate)
	{
		super(registrator, delegate);
	}

	public ParticleType<PARTICLE> asParticleType()
	{
		return get();
	}

	public static <PARTICLE extends IParticleData> ParticleEntry<PARTICLE> cast(RegistryEntry<ParticleType<PARTICLE>> registryEntry)
	{
		return cast(ParticleEntry.class, registryEntry);
	}

	public static <PARTICLE extends IParticleData> ParticleEntry<PARTICLE> cast(com.tterrag.registrate.util.entry.RegistryEntry<ParticleType<PARTICLE>> registryEntry)
	{
		return cast(ParticleEntry.class, registryEntry);
	}
}
