package xyz.apex.forge.utility.registrator.entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.java.utility.nullness.NonnullSupplier;

@SuppressWarnings("unused")
public abstract class ItemProviderEntry<ITEM extends IForgeRegistryEntry<? super ITEM> & IItemProvider> extends RegistryEntry<ITEM> implements IItemProvider, NonnullSupplier<ITEM>
{
	public ItemProviderEntry(AbstractRegistrator<?> registrator, RegistryObject<ITEM> delegate)
	{
		super(registrator, delegate);
	}

	public final ItemStack asItemStack(int stackSize)
	{
		return new ItemStack(this, stackSize);
	}

	public final ItemStack asItemStack()
	{
		return asItemStack(1);
	}

	public final boolean isInStack(ItemStack stack)
	{
		return isItem(stack.getItem());
	}

	public final boolean isInItemTag(ITag<Item> tag)
	{
		return asItem().is(tag);
	}

	public final boolean isItem(Item item)
	{
		return asItem() == item;
	}

	// vanilla wrapper
	public final boolean isItem(IItemProvider item)
	{
		return isItem(item.asItem());
	}

	@Override
	public Item asItem()
	{
		return get().asItem();
	}
}
