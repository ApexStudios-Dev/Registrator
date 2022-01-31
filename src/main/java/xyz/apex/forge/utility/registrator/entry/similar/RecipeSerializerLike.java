package xyz.apex.forge.utility.registrator.entry.similar;

import net.minecraft.world.item.crafting.RecipeSerializer;

@SuppressWarnings("unused")
@FunctionalInterface
public interface RecipeSerializerLike
{
	RecipeSerializer<?> asRecipeSerializer();
}
