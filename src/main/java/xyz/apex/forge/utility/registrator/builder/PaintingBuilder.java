package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.ForgeRegistries;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.PaintingEntry;

public final class PaintingBuilder<OWNER extends AbstractRegistrator<OWNER>, PARENT> extends RegistratorBuilder<OWNER, PaintingVariant, PaintingVariant, PARENT, PaintingBuilder<OWNER, PARENT>, PaintingEntry>
{
	private int width = 16;
	private int height = 16;

	public PaintingBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback)
	{
		super(owner, parent, registryName, callback, Registry.PAINTING_VARIANT_REGISTRY, ForgeRegistries.PAINTING_VARIANTS, PaintingEntry::new, PaintingEntry::cast);
	}

	public PaintingBuilder<OWNER, PARENT> width(int width)
	{
		this.width = width;
		return this;
	}

	public PaintingBuilder<OWNER, PARENT> height(int height)
	{
		this.height = height;
		return this;
	}

	public PaintingBuilder<OWNER, PARENT> size(int width, int height)
	{
		return width(width).height(height);
	}

	@Override
	protected @NonnullType PaintingVariant createEntry()
	{
		return new PaintingVariant(width, height);
	}
}
