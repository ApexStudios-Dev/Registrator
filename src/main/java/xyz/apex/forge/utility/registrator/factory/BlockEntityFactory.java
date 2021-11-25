package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public interface BlockEntityFactory<BLOCK_ENTITY extends TileEntity>
{
	BLOCK_ENTITY create(TileEntityType<BLOCK_ENTITY> blockEntityType);
}
