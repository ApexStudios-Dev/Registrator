package xyz.apex.forge.utility.registrator.builder;

import com.google.common.collect.Sets;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.BlockEntityEntry;
import xyz.apex.forge.utility.registrator.factory.BlockEntityFactory;
import xyz.apex.java.utility.nullness.NonnullFunction;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

@SuppressWarnings({ "deprecation", "ConstantConditions", "unused" })
public final class BlockEntityBuilder<OWNER extends AbstractRegistrator<OWNER>, BLOCK_ENTITY extends TileEntity, PARENT> extends RegistratorBuilder<OWNER, TileEntityType<?>, TileEntityType<BLOCK_ENTITY>, PARENT, BlockEntityBuilder<OWNER, BLOCK_ENTITY, PARENT>, BlockEntityEntry<BLOCK_ENTITY>>
{
	private final BlockEntityFactory<BLOCK_ENTITY> blockEntityFactory;
	private final Set<NonnullSupplier<? extends Block>> validBlocks = Sets.newHashSet();
	@Nullable private NonnullSupplier<NonnullFunction<TileEntityRendererDispatcher, TileEntityRenderer<? super BLOCK_ENTITY>>> renderer = null;

	public BlockEntityBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, BlockEntityFactory<BLOCK_ENTITY> blockEntityFactory)
	{
		super(owner, parent, registryName, callback, TileEntityType.class, BlockEntityEntry::new, BlockEntityEntry::cast);

		this.blockEntityFactory = blockEntityFactory;
		onRegister(this::onRegister);
	}

	private void onRegister(TileEntityType<BLOCK_ENTITY> blockEntityType)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> OneTimeEventReceiver.addModListener(FMLClientSetupEvent.class, event -> event.enqueueWork(() -> registerRenderer(blockEntityType))));
	}

	@OnlyIn(Dist.CLIENT)
	private void registerRenderer(TileEntityType<BLOCK_ENTITY> blockEntityType)
	{
		if(renderer != null)
			ClientRegistry.bindTileEntityRenderer(blockEntityType, renderer.get());
	}

	@Override
	protected @NonnullType TileEntityType<BLOCK_ENTITY> createEntry()
	{
		NonnullSupplier<TileEntityType<BLOCK_ENTITY>> supplier = asSupplier();
		Block[] validBlocks = this.validBlocks.stream().map(NonnullSupplier::get).toArray(Block[]::new);
		TileEntityType.Builder<BLOCK_ENTITY> builder = TileEntityType.Builder.of(() -> blockEntityFactory.create(supplier.get()), validBlocks);
		return builder.build(Util.fetchChoiceType(TypeReferences.BLOCK_ENTITY, getRegistryNameFull()));

	}

	public BlockEntityBuilder<OWNER, BLOCK_ENTITY, PARENT> validBlock(NonnullSupplier<? extends Block> block)
	{
		validBlocks.add(block);
		return this;
	}

	@SafeVarargs
	public final BlockEntityBuilder<OWNER, BLOCK_ENTITY, PARENT> validBlocks(NonnullSupplier<? extends Block>... blocks)
	{
		Collections.addAll(validBlocks, blocks);
		return this;
	}

	public BlockEntityBuilder<OWNER, BLOCK_ENTITY, PARENT> renderer(NonnullSupplier<NonnullFunction<TileEntityRendererDispatcher, TileEntityRenderer<? super BLOCK_ENTITY>>> renderer)
	{
		this.renderer = renderer;
		return this;
	}

	@SafeVarargs
	public final BlockEntityBuilder<OWNER, BLOCK_ENTITY, PARENT> tag(ITag.INamedTag<TileEntityType<?>>... tags)
	{
		return tag(AbstractRegistrator.BLOCK_ENTITY_TAGS_PROVIDER, tags);
	}

	@SafeVarargs
	public final BlockEntityBuilder<OWNER, BLOCK_ENTITY, PARENT> removeTag(ITag.INamedTag<TileEntityType<?>>... tags)
	{
		return removeTag(AbstractRegistrator.BLOCK_ENTITY_TAGS_PROVIDER, tags);
	}
}
