package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.gen.feature.structure.Structure;

import xyz.apex.forge.utility.registrator.entry.similar.StructureLike;

@Mixin(Structure.class)
public class StructureMixin implements StructureLike
{
	@Override
	public Structure<?> asStructure()
	{
		return (Structure<?>) (Object) this;
	}
}
