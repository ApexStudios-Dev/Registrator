package xyz.apex.forge.utility.registrator.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.crafting.IRecipeSerializer;

import xyz.apex.forge.utility.registrator.entry.similar.RecipeSerializerLike;

@Mixin(IRecipeSerializer.class)
public interface IRecipeSerializerMixin extends RecipeSerializerLike
{
	@Override
	default IRecipeSerializer<?> asRecipeSerializer()
	{
		return (IRecipeSerializer<?>) this;
	}
}
