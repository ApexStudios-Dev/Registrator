package xyz.apex.forge.testmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants;

public class TestBlockEntity extends TileEntity
{
	private int interactionCounter = 0;

	public TestBlockEntity(TileEntityType<TestBlockEntity> blockEntityType)
	{
		super(blockEntityType);
	}

	public void onInteract(PlayerEntity player)
	{
		interactionCounter++;

		if(!player.level.isClientSide)
			player.displayClientMessage(new StringTextComponent("This block has now been interacted with " + interactionCounter + " time(s)"), true);
	}

	@Override
	public void load(BlockState blockState, CompoundNBT tileTag)
	{
		super.load(blockState, tileTag);

		if(tileTag.contains("InteractionCounter", Constants.NBT.TAG_INT))
			interactionCounter = tileTag.getInt("InteractionCounter");
		else
			interactionCounter = 0;
	}

	@Override
	public CompoundNBT save(CompoundNBT tileTag)
	{
		tileTag.putInt("InteractionCounter", interactionCounter);
		return super.save(tileTag);
	}
}
