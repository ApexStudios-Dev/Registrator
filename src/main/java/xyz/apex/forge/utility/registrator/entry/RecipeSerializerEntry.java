package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

public final class RecipeSerializerEntry<RECIPE_TYPE extends IRecipeSerializer<RECIPE>, RECIPE extends IRecipe<?>> extends RegistryEntry<RECIPE_TYPE> implements NonnullSupplier<RECIPE_TYPE>
{
	private final IRecipeType<RECIPE> recipeType;

	public RecipeSerializerEntry(AbstractRegistrator<?> owner, RegistryObject<RECIPE_TYPE> delegate)
	{
		super(owner, delegate);

		recipeType = IRecipeType.register(delegate.getId().toString());
	}

	public IRecipeSerializer<RECIPE> asRecipeSerializer()
	{
		return get();
	}

	public IRecipeType<RECIPE> asRecipeType()
	{
		return recipeType;
	}

	public static <RECIPE_TYPE extends IRecipeSerializer<RECIPE>, RECIPE extends IRecipe<?>> RecipeSerializerEntry<RECIPE_TYPE, RECIPE> cast(RegistryEntry<RECIPE_TYPE> registryEntry)
	{
		return cast(RecipeSerializerEntry.class, registryEntry);
	}

	public static <RECIPE_TYPE extends IRecipeSerializer<RECIPE>, RECIPE extends IRecipe<?>> RecipeSerializerEntry<RECIPE_TYPE, RECIPE> cast(com.tterrag.registrate.util.entry.RegistryEntry<RECIPE_TYPE> registryEntry)
	{
		return cast(RecipeSerializerEntry.class, registryEntry);
	}
}
