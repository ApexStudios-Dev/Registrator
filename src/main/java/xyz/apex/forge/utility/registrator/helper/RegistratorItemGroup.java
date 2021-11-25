package xyz.apex.forge.utility.registrator.helper;

import com.google.common.collect.Sets;
import com.tterrag.registrate.util.entry.RegistryEntry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.ItemProviderEntry;
import xyz.apex.java.utility.Lazy;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.Stack;

@SuppressWarnings({ "deprecation", "UnusedReturnValue" })
public class RegistratorItemGroup extends ItemGroup implements Lazy<ItemStack>
{
	protected final AbstractRegistrator<?> registrator;
	private int cycleTime = 0;
	private final int maxCycleTime;
	private final Set<ItemStack> allIcons = Sets.newHashSet();
	private final Stack<ItemStack> icons = new Stack<>();

	protected RegistratorItemGroup(AbstractRegistrator<?> registrator, @Nullable String labelSuffix, int maxCycleTime)
	{
		super(buildLabel(registrator, labelSuffix));

		this.registrator = registrator;
		this.maxCycleTime = maxCycleTime;

		registrator.backend.addRegisterCallback(Item.class, this::initialize);
	}

	@Override
	public ItemStack get()
	{
		if(icons.isEmpty())
			return Items.STONE.getDefaultInstance();
		return icons.peek();
	}

	@Override
	public void invalidate()
	{
		icons.clear();
		icons.addAll(allIcons);
		Collections.shuffle(icons);
		cycleTime = 0;
	}

	private void initialize()
	{
		for(RegistryEntry<?> registryEntry : registrator.getAll(Item.class))
		{
			if(registryEntry instanceof ItemProviderEntry)
				allIcons.add(((ItemProviderEntry<?>) registryEntry).asItemStack());
			else if(registryEntry instanceof com.tterrag.registrate.util.entry.ItemProviderEntry)
				allIcons.add(((com.tterrag.registrate.util.entry.ItemProviderEntry<?>) registryEntry).asStack());
		}

		invalidate();
	}

	@Override
	public ItemStack getIconItem()
	{
		cycleTime++;

		if(cycleTime >= maxCycleTime)
		{
			cycleTime = 0;

			ItemStack iconItemStack = icons.pop();
			setIconItemStack(this, iconItemStack);

			if(icons.isEmpty())
				invalidate();
		}

		return super.getIconItem();
	}

	@Override
	public ItemStack makeIcon()
	{
		return get();
	}

	private static String buildLabel(AbstractRegistrator<?> registrator, @Nullable String labelSuffix)
	{
		if(StringUtils.isEmpty(labelSuffix) || StringUtils.isBlank(labelSuffix))
			return registrator.getModId();
		return registrator.idString(labelSuffix).replace(':', '.');
	}

	public static ItemGroup create(AbstractRegistrator<?> registrator, String labelSuffix, int maxCycleTime)
	{
		return new RegistratorItemGroup(registrator, labelSuffix, maxCycleTime);
	}

	public static ItemGroup create(AbstractRegistrator<?> registrator, String labelSuffix)
	{
		return create(registrator, labelSuffix, 75);
	}

	public static ItemGroup create(AbstractRegistrator<?> registrator, int maxCycleTime)
	{
		return new RegistratorItemGroup(registrator, null, maxCycleTime);
	}

	public static ItemGroup create(AbstractRegistrator<?> registrator)
	{
		return create(registrator, 75);
	}

	public static ItemStack getIconItemStack(ItemGroup itemGroup)
	{
		// ItemStack iconItemStack = ObfuscationReflectionHelper.getPrivateValue(ItemGroup.class, itemGroup, "field_151245_t");
		ItemStack iconItemStack = itemGroup.iconItemStack;
		Validate.notNull(iconItemStack);
		return iconItemStack;
	}

	public static ItemStack setIconItemStack(ItemGroup itemGroup, ItemStack iconItemStack)
	{
		ItemStack oldItemIconStack = getIconItemStack(itemGroup);
		// ObfuscationReflectionHelper.setPrivateValue(ItemGroup.class, itemGroup, iconItemStack, "field_151245_t");
		itemGroup.iconItemStack = iconItemStack;
		return oldItemIconStack;
	}
}
