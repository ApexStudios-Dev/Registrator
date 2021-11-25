package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

@FunctionalInterface
public interface RecipeSerializerFactory<RECIPE_TYPE extends IRecipeSerializer<RECIPE>, RECIPE extends IRecipe<INVENTORY>, INVENTORY extends IInventory>
{
	RECIPE_TYPE create();
}
