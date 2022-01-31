package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.item.crafting.RecipeType;

@SuppressWarnings("unused")
@FunctionalInterface
public interface RecipeTypeLike
{
	RecipeType<?> asRecipeType();
}
