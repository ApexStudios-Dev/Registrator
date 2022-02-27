package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;
import java.util.Optional;

@SuppressWarnings("unused")
public final class BlockEntityEntry<BLOCK_ENTITY extends TileEntity> extends RegistryEntry<TileEntityType<BLOCK_ENTITY>> implements NonnullSupplier<TileEntityType<BLOCK_ENTITY>>
{
	public BlockEntityEntry(AbstractRegistrator<?> registrator, RegistryObject<TileEntityType<BLOCK_ENTITY>> delegate)
	{
		super(registrator, delegate);
	}

	public boolean isInBlockEntityTypeTag(ITag<TileEntityType<?>> tag)
	{
		return asBlockEntityType().isIn(tag);
	}

	public boolean isBlockEntityType(TileEntityType<?> blockEntityType)
	{
		return asBlockEntityType() == blockEntityType;
	}

	@Nullable
	public BLOCK_ENTITY createBlockEntity()
	{
		return asBlockEntityType().create();
	}

	@Nullable
	public BLOCK_ENTITY getBlockEntity(IBlockReader level, BlockPos pos)
	{
		return asBlockEntityType().getBlockEntity(level, pos);
	}

	public Optional<BLOCK_ENTITY> getBlockEntityOptional(IBlockReader level, BlockPos pos)
	{
		return Optional.ofNullable(getBlockEntity(level, pos));
	}

	public boolean isValidBlock(Block block)
	{
		return asBlockEntityType().isValid(block);
	}

	public TileEntityType<BLOCK_ENTITY> asBlockEntityType()
	{
		return get();
	}

	public static <BLOCK_ENTITY extends TileEntity> BlockEntityEntry<BLOCK_ENTITY> cast(RegistryEntry<TileEntityType<BLOCK_ENTITY>> registryEntry)
	{
		return cast(BlockEntityEntry.class, registryEntry);
	}

	public static <BLOCK_ENTITY extends TileEntity> BlockEntityEntry<BLOCK_ENTITY> cast(com.tterrag.registrate.util.entry.RegistryEntry<TileEntityType<BLOCK_ENTITY>> registryEntry)
	{
		return cast(BlockEntityEntry.class, registryEntry);
	}
}
