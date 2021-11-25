package xyz.apex.forge.testmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import xyz.apex.forge.testmod.block.entity.TestBlockEntity;
import xyz.apex.forge.testmod.init.TBlockEntityTypes;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class TestBlock extends Block implements ITileEntityProvider
{
	public TestBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public ActionResultType use(BlockState blockState, World level, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
	{
		TestBlockEntity blockEntity = TBlockEntityTypes.TEST_BLOCK_ENTITY.getBlockEntity(level, pos);

		if(blockEntity != null)
			blockEntity.onInteract(player);

		return ActionResultType.sidedSuccess(level.isClientSide);
	}

	@Nullable
	@Override
	public TileEntity newBlockEntity(IBlockReader level)
	{
		return TBlockEntityTypes.TEST_BLOCK_ENTITY.createBlockEntity();
	}
}
