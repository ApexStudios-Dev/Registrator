package xyz.apex.forge.utility.registrator.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.merchant.villager.VillagerProfession;

import xyz.apex.forge.utility.registrator.entry.similar.VillagerProfessionLike;

@Mixin(VillagerProfession.class)
public class VillagerProfessionMixin implements VillagerProfessionLike
{
	@Override
	public VillagerProfession asVillagerProfession()
	{
		return (VillagerProfession) (Object) this;
	}
}
