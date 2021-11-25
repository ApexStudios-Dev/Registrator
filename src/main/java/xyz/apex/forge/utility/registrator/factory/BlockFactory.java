package xyz.apex.forge.utility.registrator.factory;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

@FunctionalInterface
public interface BlockFactory<BLOCK extends Block>
{
	BlockFactory<Block> DEFAULT = Block::new;

	BLOCK create(AbstractBlock.Properties properties);
}
