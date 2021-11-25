package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.item.crafting.IRecipeType;

@FunctionalInterface
public interface RecipeTypeLike
{
	IRecipeType<?> asRecipeType();
}
