package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.crafting.IRecipeType;

import xyz.apex.forge.utility.registrator.entry.similar.RecipeTypeLike;

@Mixin(IRecipeType.class)
public interface IRecipeTypeMixin extends RecipeTypeLike
{
	@Override
	default IRecipeType<?> asRecipeType()
	{
		return (IRecipeType<?>) this;
	}
}
