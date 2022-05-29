package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

@FunctionalInterface
public interface ParticleFactory<PARTICLE extends IParticleData>
{
	ParticleType<PARTICLE> create();
}
