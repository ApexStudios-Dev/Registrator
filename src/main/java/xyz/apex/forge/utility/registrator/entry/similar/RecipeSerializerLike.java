package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.item.crafting.IRecipeSerializer;

@FunctionalInterface
public interface RecipeSerializerLike
{
	IRecipeSerializer<?> asRecipeSerializer();
}
