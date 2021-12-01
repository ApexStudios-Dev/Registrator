package xyz.apex.forge.testmod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import xyz.apex.forge.testmod.init.TContainers;

public class DummyContainerItem extends Item
{
	public DummyContainerItem(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand)
	{
		ItemStack stack = player.getItemInHand(hand);

		if(player instanceof ServerPlayerEntity)
		{
			TContainers.DUMMY.open((ServerPlayerEntity) player, stack.getDisplayName());
			return ActionResult.pass(stack);
		}

		return ActionResult.sidedSuccess(stack, level.isClientSide);
	}
}
