package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.item.crafting.IRecipeType;

@SuppressWarnings("unused")
@FunctionalInterface
public interface RecipeTypeLike
{
	IRecipeType<?> asRecipeType();
}
