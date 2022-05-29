package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;
import org.apache.commons.lang3.Validate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.DistExecutor;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.data.ParticleProvider;
import xyz.apex.forge.utility.registrator.entry.ParticleEntry;
import xyz.apex.forge.utility.registrator.factory.ParticleFactory;
import xyz.apex.java.utility.nullness.NonnullConsumer;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

public final class ParticleBuilder<OWNER extends AbstractRegistrator<OWNER>, PARTICLE extends IParticleData, PARENT> extends RegistratorBuilder<OWNER, ParticleType<?>, ParticleType<PARTICLE>, PARENT, ParticleBuilder<OWNER, PARTICLE, PARENT>, ParticleEntry<PARTICLE>>
{
	private final ParticleFactory<PARTICLE> particleFactory;

	@Nullable private NonnullSupplier<IParticleFactory<PARTICLE>> factory = null;
	@Nullable private NonnullSupplier<ParticleManager.IParticleMetaFactory<PARTICLE>> metaFactory = null;
	private NonnullConsumer<ParticleProvider.ParticleDefinition> particleDefinitionConsumer = NonnullConsumer.noop();

	public ParticleBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, ParticleFactory<PARTICLE> particleFactory)
	{
		super(owner, parent, registryName, callback, ParticleType.class, ParticleEntry::new, ParticleEntry::cast);

		this.particleFactory = particleFactory;
		onRegister(particleType -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> owner.getModBus().addListener(EventPriority.NORMAL, false, ParticleFactoryRegisterEvent.class, event -> onRegisterParticleFactories(particleType))));
		setDataGenerator(AbstractRegistrator.PARTICLE_PROVIDER, (ctx, provider) -> particleDefinitionConsumer.accept(provider.definition(ctx.get())));
	}

	@Override
	protected @NonnullType ParticleType<PARTICLE> createEntry()
	{
		return particleFactory.create();
	}

	public ParticleBuilder<OWNER, PARTICLE, PARENT> definition(NonnullConsumer<ParticleProvider.ParticleDefinition> particleDefinitionConsumer)
	{
		this.particleDefinitionConsumer = particleDefinitionConsumer.andThen(particleDefinitionConsumer);
		return this;
	}

	public ParticleBuilder<OWNER, PARTICLE, PARENT> factory(NonnullSupplier<IParticleFactory<PARTICLE>> particleFactory)
	{
		Validate.isTrue(metaFactory == null, "Can only specify a IParticleFactory or IParticleMetaFactory never BOTH!!");
		Validate.isTrue(factory == null, "IParticleFactory has already been set!");

		factory = particleFactory;

		return this;
	}

	public ParticleBuilder<OWNER, PARTICLE, PARENT> metaFactory(NonnullSupplier<ParticleManager.IParticleMetaFactory<PARTICLE>> particleFactory)
	{
		Validate.isTrue(factory == null, "Can only specify a IParticleFactory or IParticleMetaFactory never BOTH!!");
		Validate.isTrue(metaFactory == null, "IParticleMetaFactory has already been set!");

		metaFactory = particleFactory;

		return this;
	}

	@OnlyIn(Dist.CLIENT)
	private void onRegisterParticleFactories(ParticleType<PARTICLE> particleType)
	{
		ParticleManager particleEngine = Minecraft.getInstance().particleEngine;

		if(factory != null)
			particleEngine.register(particleType, factory.get());
		if(metaFactory != null)
			particleEngine.register(particleType, metaFactory.get());
	}
}
