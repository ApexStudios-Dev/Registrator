package xyz.apex.forge.testmod.init;

import net.minecraft.block.SoundType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraftforge.common.util.ForgeSoundType;

import xyz.apex.forge.utility.registrator.helper.ArmorMaterial;
import xyz.apex.forge.utility.registrator.helper.ItemTier;

public final class TElements
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final IItemTier COPPER_ITEM_TIER = ItemTier.copy(net.minecraft.item.ItemTier.IRON).build();
	public static final IArmorMaterial COPPER_ARMOR_MATERIAL = ArmorMaterial.copy(REGISTRY.getModId(), "copper", net.minecraft.item.ArmorMaterial.IRON).build();

	public static final SoundType COPPER_SOUND_TYPE = new ForgeSoundType(1F, 1F, TSounds.COPPER_BREAK, TSounds.COPPER_STEP, TSounds.COPPER_PLACE, TSounds.COPPER_HIT, TSounds.COPPER_FALL);

	static void bootstrap() { }
}
