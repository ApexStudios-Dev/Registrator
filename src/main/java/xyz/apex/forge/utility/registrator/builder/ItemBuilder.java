package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.NonNullLazyValue;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.tags.ITag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.ItemEntry;
import xyz.apex.forge.utility.registrator.factory.item.ItemFactory;
import xyz.apex.java.utility.nullness.NonnullBiConsumer;
import xyz.apex.java.utility.nullness.NonnullCallable;
import xyz.apex.java.utility.nullness.NonnullSupplier;
import xyz.apex.java.utility.nullness.NonnullUnaryOperator;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public final class ItemBuilder<OWNER extends AbstractRegistrator<OWNER>, ITEM extends Item, PARENT> extends RegistratorBuilder<OWNER, Item, ITEM, PARENT, ItemBuilder<OWNER, ITEM, PARENT>, ItemEntry<ITEM>>
{
	private final ItemFactory<ITEM> itemFactory;
	private NonnullSupplier<Item.Properties> initialProperties = Item.Properties::new;;
	private NonnullUnaryOperator<Item.Properties> propertiesModifier = NonnullUnaryOperator.identity();
	@Nullable private NonnullSupplier<NonnullSupplier<IItemColor>> colorHandler;

	public ItemBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, ItemFactory<ITEM> itemFactory)
	{
		super(owner, parent, registryName, callback, Item.class, ItemEntry::new, ItemEntry::cast);

		this.itemFactory = itemFactory;
		onRegister(this::onRegister);

		// apply registrate defaults
		defaultModel().defaultLang();

		NonNullLazyValue<? extends ItemGroup> currentGroup = ObfuscationReflectionHelper.getPrivateValue(AbstractRegistrate.class, owner.backend, "currentGroup");

		if(currentGroup != null)
			itemGroup(currentGroup::get);
	}

	private void onRegister(ITEM item)
	{
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> OneTimeEventReceiver.addModListener(ColorHandlerEvent.Item.class, event -> registerItemColor(event, item)));
	}

	@OnlyIn(Dist.CLIENT)
	private void registerItemColor(ColorHandlerEvent.Item event, ITEM item)
	{
		if(colorHandler != null)
			event.getItemColors().register(colorHandler.get().get(), item);
	}

	@Override
	protected @NonnullType ITEM createEntry()
	{
		Item.Properties properties = initialProperties.get();
		properties = propertiesModifier.apply(properties);
		return itemFactory.create(properties);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> properties(NonnullUnaryOperator<Item.Properties> propertiesModifier)
	{
		this.propertiesModifier = this.propertiesModifier.andThen(propertiesModifier);
		return this;
	}

	// region: Properties Wrappers
	public ItemBuilder<OWNER, ITEM, PARENT> food(Food food)
	{
		return properties(properties -> properties.food(food));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> stacksTo(int stackSize)
	{
		return properties(properties -> properties.stacksTo(Math.max(stackSize, 1)));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> defaultDurability(int durability)
	{
		return properties(properties -> properties.defaultDurability(durability));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> durability(int durability)
	{
		return properties(properties -> properties.durability(durability));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> itemGroup(NonnullSupplier<ItemGroup> itemGroup)
	{
		return properties(properties -> properties.tab(itemGroup.get()));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> rarity(Rarity rarity)
	{
		return properties(properties -> properties.rarity(rarity));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> fireResistant()
	{
		return properties(Item.Properties::fireResistant);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> noRepair()
	{
		return properties(Item.Properties::setNoRepair);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> addToolType(NonnullSupplier<ToolType> toolType, int toolLevel)
	{
		return properties(properties -> properties.addToolType(toolType.get(), toolLevel));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> setISTER(NonnullSupplier<NonnullCallable<ItemStackTileEntityRenderer>> ister)
	{
		return properties(properties -> properties.setISTER(ister::get));
	}
	// endregion

	public ItemBuilder<OWNER, ITEM, PARENT> initialProperties(NonnullSupplier<Item.Properties> initialProperties)
	{
		this.initialProperties = initialProperties;
		return this;
	}

	public ItemBuilder<OWNER, ITEM, PARENT> color(NonnullSupplier<NonnullSupplier<IItemColor>> colorHandler)
	{
		this.colorHandler = colorHandler;
		return this;
	}

	public ItemBuilder<OWNER, ITEM, PARENT> defaultModel()
	{
		return model((ctx, provider) -> provider.generated(ctx));
	}

	public ItemBuilder<OWNER, ITEM, PARENT> model(NonnullBiConsumer<DataGenContext<Item, ITEM>, RegistrateItemModelProvider> consumer)
	{
		return setDataGenerator(ProviderType.ITEM_MODEL, consumer);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> defaultLang()
	{
		return lang(Item::getDescriptionId);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> lang(String name)
	{
		return lang(Item::getDescriptionId, name);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> recipe(NonnullBiConsumer<DataGenContext<Item, ITEM>, RegistrateRecipeProvider> consumer)
	{
		return setDataGenerator(ProviderType.RECIPE, consumer);
	}

	@SafeVarargs
	public final ItemBuilder<OWNER, ITEM, PARENT> tag(ITag.INamedTag<Item>... tags)
	{
		return tag(ProviderType.ITEM_TAGS, tags);
	}

	@SafeVarargs
	public final ItemBuilder<OWNER, ITEM, PARENT> removeTag(ITag.INamedTag<Item>... tags)
	{
		return removeTags(ProviderType.ITEM_TAGS, tags);
	}

	public ItemBuilder<OWNER, ITEM, PARENT> lang(String languageKey, String localizedValue)
	{
		return lang(languageKey, Item::getDescriptionId, localizedValue);
	}
}
