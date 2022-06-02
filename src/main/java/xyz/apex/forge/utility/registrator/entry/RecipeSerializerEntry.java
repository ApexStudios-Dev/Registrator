package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.Lazy;
import xyz.apex.java.utility.nullness.NonnullSupplier;

public final class RecipeSerializerEntry<RECIPE_TYPE extends RecipeSerializer<RECIPE>, RECIPE extends Recipe<?>> extends RegistryEntry<RECIPE_TYPE> implements NonnullSupplier<RECIPE_TYPE>
{
	private final Lazy<RecipeType<RECIPE>> recipeType;

	public RecipeSerializerEntry(AbstractRegistrator<?> owner, RegistryObject<RECIPE_TYPE> delegate)
	{
		super(owner, delegate);

		recipeType = Lazy.of(() -> RecipeType.register(delegate.getId().toString()));
		owner.addRegisterCallback(RecipeSerializer.class, () -> recipeType.get());
	}

	public RecipeSerializer<RECIPE> asRecipeSerializer()
	{
		return get();
	}

	public RecipeType<RECIPE> asRecipeType()
	{
		return recipeType.get();
	}

	public static <RECIPE_TYPE extends RecipeSerializer<RECIPE>, RECIPE extends Recipe<?>> RecipeSerializerEntry<RECIPE_TYPE, RECIPE> cast(RegistryEntry<RECIPE_TYPE> registryEntry)
	{
		return cast(RecipeSerializerEntry.class, registryEntry);
	}

	public static <RECIPE_TYPE extends RecipeSerializer<RECIPE>, RECIPE extends Recipe<?>> RecipeSerializerEntry<RECIPE_TYPE, RECIPE> cast(com.tterrag.registrate.util.entry.RegistryEntry<RECIPE_TYPE> registryEntry)
	{
		return cast(RecipeSerializerEntry.class, registryEntry);
	}
}
