package xyz.apex.forge.utility.registrator.helper;

import org.apache.logging.log4j.LogManager;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import xyz.apex.java.utility.nullness.NonnullSupplier;

import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class ForgeSpawnEggItem<ENTITY extends Entity> extends SpawnEggItem
{
	private static final IDispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) -> {
		Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
		EntityType<?> entityType = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());

		try
		{
			entityType.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
		}
		catch(Exception e)
		{
			LogManager.getLogger().error("Error while dispensing spawn egg from dispenser at {}", source.getPos(), e);
			return ItemStack.EMPTY;
		}

		stack.shrink(1);
		// source.getLevel().gameEvent(GameEvent.ENTITY_PLACE, source.getPos());
		return stack;
	};

	private boolean registered = false;
	private final NonnullSupplier<EntityType<ENTITY>> entityTypeSupplier;

	public ForgeSpawnEggItem(NonnullSupplier<EntityType<ENTITY>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties properties)
	{
		super(null, backgroundColor, highlightColor, properties);

		this.entityTypeSupplier = entityTypeSupplier;
	}

	@Override
	public EntityType<?> getType(@Nullable CompoundNBT tag)
	{
		EntityType<?> entityType = super.getType(tag);
		return entityType == null ? entityTypeSupplier.get() : entityType;
	}

	@Nullable
	protected IDispenseItemBehavior getDispenserBehavior()
	{
		return DEFAULT_DISPENSE_BEHAVIOR;
	}

	@Deprecated
	public final void registerSpawnEggProperties()
	{
		if(registered)
			return;

		BY_ID.put(entityTypeSupplier.get(), this);
		registered = true;

		IDispenseItemBehavior dispenserBehavior = getDispenserBehavior();

		if(dispenserBehavior != null)
			DispenserBlock.registerBehavior(this, dispenserBehavior);
	}

	@OnlyIn(Dist.CLIENT)
	public static int getSpawnEggColor(ItemStack stack, int tintIndex)
	{
		Item item = stack.getItem();
		return item instanceof SpawnEggItem ? ((SpawnEggItem) item).getColor(tintIndex) : 0;
	}
}
