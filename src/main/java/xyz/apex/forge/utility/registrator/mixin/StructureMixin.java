package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.level.levelgen.feature.StructureFeature;

import xyz.apex.forge.utility.registrator.entry.similar.StructureLike;

@Mixin(StructureFeature.class)
public class StructureMixin implements StructureLike
{
	@Override
	public StructureFeature<?> asStructure()
	{
		return (StructureFeature<?>) (Object) this;
	}
}
