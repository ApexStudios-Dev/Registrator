package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.similar.EntityTypeLike;
import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("unused")
public final class EntityEntry<ENTITY extends Entity> extends RegistryEntry<EntityType<ENTITY>> implements EntityTypeLike, NonnullSupplier<EntityType<ENTITY>>
{
	public EntityEntry(AbstractRegistrator<?> registrator, RegistryObject<EntityType<ENTITY>> delegate)
	{
		super(registrator, delegate);
	}

	public boolean isInEntityTypeTag(ITag<EntityType<?>> tag)
	{
		return asEntityType().is(tag);
	}

	public boolean isEntityType(EntityType<?> entityType)
	{
		return asEntityType() == entityType;
	}

	@Nullable
	public ENTITY spawn(ServerWorld level, @Nullable ItemStack stack, @Nullable PlayerEntity player, BlockPos pos, SpawnReason spawnReason, boolean unk1, boolean unk2)
	{
		return spawn(level, stack == null ? null : stack.getTag(), stack != null && stack.hasCustomHoverName() ? stack.getHoverName() : null, player, pos, spawnReason, unk1, unk2);
	}

	@Nullable
	public ENTITY spawn(ServerWorld level, @Nullable CompoundNBT entityTag, @Nullable ITextComponent displayName, @Nullable PlayerEntity player, BlockPos pos, SpawnReason spawnReason, boolean unk1, boolean unk2)
	{
		return asEntityType().spawn(level, entityTag, displayName, player, pos, spawnReason, unk1, unk2);
	}

	@Nullable
	public ENTITY create(ServerWorld level, @Nullable CompoundNBT entityTag, @Nullable ITextComponent displayName, @Nullable PlayerEntity player, BlockPos pos, SpawnReason spawnReason, boolean unk1, boolean unk2)
	{
		return asEntityType().create(level, entityTag, displayName, player, pos, spawnReason, unk1, unk2);
	}

	public EntityClassification getCategory()
	{
		return asEntityType().getCategory();
	}

	public ResourceLocation getDefaultLootTable()
	{
		return asEntityType().getDefaultLootTable();
	}

	public float getWidth()
	{
		return asEntityType().getWidth();
	}

	public float getHeight()
	{
		return asEntityType().getHeight();
	}

	public EntitySize getDimensions()
	{
		return asEntityType().getDimensions();
	}

	@Override
	public EntityType<ENTITY> asEntityType()
	{
		return get();
	}

	public boolean hasType(Entity entity)
	{
		return isEntityType(entity.getType());
	}

	public static <ENTITY extends Entity> EntityEntry<ENTITY> cast(RegistryEntry<EntityType<ENTITY>> registryEntry)
	{
		return cast(EntityEntry.class, registryEntry);
	}

	public static <ENTITY extends Entity> EntityEntry<ENTITY> cast(com.tterrag.registrate.util.entry.RegistryEntry<EntityType<ENTITY>> registryEntry)
	{
		return cast(EntityEntry.class, registryEntry);
	}
}
