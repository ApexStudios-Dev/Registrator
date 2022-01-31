package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.crafting.RecipeSerializer;

import xyz.apex.forge.utility.registrator.entry.similar.RecipeSerializerLike;

@Mixin(RecipeSerializer.class)
public interface IRecipeSerializerMixin extends RecipeSerializerLike
{
	@Override
	default RecipeSerializer<?> asRecipeSerializer()
	{
		return (RecipeSerializer<?>) this;
	}
}
