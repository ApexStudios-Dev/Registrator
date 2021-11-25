package xyz.apex.forge.utility.registrator.provider;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.RegistrateProvider;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.LogicalSide;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.data.template.TemplatePoolProvider;

public final class RegistrateTemplatePoolProvider extends TemplatePoolProvider implements RegistrateProvider
{
	private final AbstractRegistrate<?> registrate;

	public RegistrateTemplatePoolProvider(AbstractRegistrate<?> registrate, DataGenerator generator)
	{
		super(generator);

		this.registrate = registrate;
	}

	@Override
	protected void registerPools()
	{
		registrate.genData(AbstractRegistrator.TEMPLATE_POOL_PROVIDER, this);
	}

	@Override
	public LogicalSide getSide()
	{
		return LogicalSide.SERVER;
	}
}
