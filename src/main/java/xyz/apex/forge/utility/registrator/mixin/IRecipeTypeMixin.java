package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.crafting.RecipeType;

import xyz.apex.forge.utility.registrator.entry.similar.RecipeTypeLike;

@Mixin(RecipeType.class)
public interface IRecipeTypeMixin extends RecipeTypeLike
{
	@Override
	default RecipeType<?> asRecipeType()
	{
		return (RecipeType<?>) this;
	}
}
