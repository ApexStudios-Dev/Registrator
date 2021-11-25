package xyz.apex.forge.testmod.init;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;

import net.minecraft.block.Block;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.*;
import net.minecraftforge.common.Tags;

import xyz.apex.forge.utility.registrator.builder.EntityBuilder;
import xyz.apex.forge.utility.registrator.entry.BlockEntry;
import xyz.apex.forge.utility.registrator.entry.EntityEntry;
import xyz.apex.forge.utility.registrator.entry.ItemEntry;
import xyz.apex.forge.utility.registrator.helper.ForgeSpawnEggItem;
import xyz.apex.forge.utility.registrator.provider.RegistrateLangExtProvider;

public final class TItems
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	// region: Copper Pickaxe
	public static final ItemEntry<PickaxeItem> COPPER_PICKAXE = REGISTRY
			.pickaxeItem("copper_pickaxe", TElements.COPPER_ITEM_TIER, 1, -2.8F)
				.lang("Copper Pickaxe")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Pickaxe")
				.model((ctx, provider) -> provider.handheld(ctx))
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('#', Tags.Items.RODS_WOODEN)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("XXX")
						.pattern(" # ")
						.pattern(" # ")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Axe
	public static final ItemEntry<AxeItem> COPPER_AXE = REGISTRY
			.axeItem("copper_axe", TElements.COPPER_ITEM_TIER, 6F, -3.1F)
				.lang("Copper Axe")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Axe")
				.model((ctx, provider) -> provider.handheld(ctx))
				.recipe((ctx, provider) -> ShapedRecipeBuilder
					.shaped(ctx::getEntry)
					.define('#', Tags.Items.RODS_WOODEN)
					.define('X', TTags.Items.INGOTS_COPPER)
					.pattern("XX")
					.pattern("X#")
					.pattern(" #")
					.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
					.save(provider)
			)
			.register();
	// endregion

	// region: Copper Shovel
	public static final ItemEntry<ShovelItem> COPPER_SHOVEL = REGISTRY
			.shovelItem("copper_shovel", TElements.COPPER_ITEM_TIER, 1.5F, -3F)
				.lang("Copper Shovel")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Shovel")
				.model((ctx, provider) -> provider.handheld(ctx))
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('#', Tags.Items.RODS_WOODEN)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("X")
						.pattern("#")
						.pattern("#")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Hoe
	public static final ItemEntry<HoeItem> COPPER_HOE = REGISTRY
			.hoeItem("copper_hoe", TElements.COPPER_ITEM_TIER, -2, -1F)
				.lang("Copper Hoe")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Hoe")
				.model((ctx, provider) -> provider.handheld(ctx))
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('#', Tags.Items.RODS_WOODEN)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("XX")
						.pattern(" #")
						.pattern(" #")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Sword
	public static final ItemEntry<SwordItem> COPPER_SWORD = REGISTRY
			.swordItem("copper_sword", TElements.COPPER_ITEM_TIER, 3, -2.4F)
				.lang("Copper Sword")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Sword")
				.model((ctx, provider) -> provider.handheld(ctx))
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('#', Tags.Items.RODS_WOODEN)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("X")
						.pattern("X")
						.pattern("#")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Helmet
	public static final ItemEntry<ArmorItem> COPPER_HELMET = REGISTRY
			.helmetArmorItem("copper_helmet", TElements.COPPER_ARMOR_MATERIAL)
				.lang("Copper Helmet")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Helmet")
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("XXX")
						.pattern("X X")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Chestplate
	public static final ItemEntry<ArmorItem> COPPER_CHESTPLATE = REGISTRY
			.chestplateArmorItem("copper_chestplate", TElements.COPPER_ARMOR_MATERIAL)
				.lang("Copper Chestplate")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Chestplate")
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("X X")
						.pattern("XXX")
						.pattern("XXX")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Leggings
	public static final ItemEntry<ArmorItem> COPPER_LEGGINGS = REGISTRY
			.leggingsArmorItem("copper_leggings", TElements.COPPER_ARMOR_MATERIAL)
				.lang("Copper Leggings")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Leggings")
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("XXX")
						.pattern("X X")
						.pattern("X X")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Boots
	public static final ItemEntry<ArmorItem> COPPER_BOOTS = REGISTRY
			.bootsArmorItem("copper_boots", TElements.COPPER_ARMOR_MATERIAL)
				.lang("Copper Boots")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Boots")
				.recipe((ctx, provider) -> ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('X', TTags.Items.INGOTS_COPPER)
						.pattern("X X")
						.pattern("X X")
						.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
						.save(provider)
				)
			.register();
	// endregion

	// region: Copper Horse Armor
	public static final ItemEntry<HorseArmorItem> COPPER_HORSE_ARMOR = REGISTRY
			.horseArmorItem("copper_horse_armor", 5, "copper")
				.lang("Copper Horse Armor")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Horse Armor")
				.stacksTo(1)
			.register();
	// endregion

	// region: Copper Ingot
	public static final ItemEntry<Item> COPPER_INGOT = REGISTRY
			.item("copper_ingot")
			.lang("Copper Ingot")
			.lang(RegistrateLangExtProvider.EN_GB, "Copper Ingot")
			.defaultModel()
			.tag(TTags.Items.INGOTS_COPPER)
			.recipe((ctx, provider) -> {
				ShapedRecipeBuilder
						.shaped(ctx::getEntry)
						.define('#', TTags.Items.NUGGETS_COPPER)
						.pattern("###")
						.pattern("###")
						.pattern("###")
						.group("copper_ingot")
						.unlockedBy("has_copper_nugget", RegistrateRecipeProvider.hasItem(TTags.Items.NUGGETS_COPPER))
						.save(provider, "copper_ingot_from_copper_nugget")
				;

				ShapelessRecipeBuilder
						.shapeless(ctx::getEntry, 9)
						.requires(TTags.Items.STORAGE_BLOCKS_COPPER)
						.group("copper_ingot")
						.unlockedBy("has_copper_block", RegistrateRecipeProvider.hasItem(TTags.Items.STORAGE_BLOCKS_COPPER))
						.save(provider, "copper_ingot_from_copper_block")
				;
			})
			.register();
	// endregion

	// region: Copper Nugget
	public static final ItemEntry<Item> COPPER_NUGGET = REGISTRY
			.item("copper_nugget")
				.lang("Copper Nugget")
				.lang(RegistrateLangExtProvider.EN_GB, "Copper Nugget")
				.defaultModel()
				.tag(TTags.Items.NUGGETS_COPPER)
				.recipe((ctx, provider) -> {
					ShapelessRecipeBuilder
							.shapeless(ctx::getEntry, 9)
							.requires(TTags.Items.INGOTS_COPPER)
							.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
							.save(provider);

					provider.smeltingAndBlasting(DataIngredient.items(COPPER_PICKAXE, COPPER_SHOVEL, COPPER_AXE, COPPER_HOE, COPPER_SWORD, COPPER_HELMET, COPPER_CHESTPLATE, COPPER_LEGGINGS, COPPER_BOOTS, COPPER_HORSE_ARMOR), ctx, .1F);
				})
			.register();
	// endregion

	// region: Blocks
	public static final ItemEntry<BlockItem> COPPER_ORE = block(TBlocks.COPPER_ORE);
	public static final ItemEntry<BlockItem> COPPER_BLOCK = block(TBlocks.COPPER_BLOCK);
	public static final ItemEntry<BlockItem> TEST_BLOCK = block(TBlocks.TEST_BLOCK);
	// endregion

	// region: SpawnEggs
	public static final ItemEntry<ForgeSpawnEggItem<PigEntity>> TEST_ENTITY_SPAWN_EGG = spawnEgg(TEntityTypes.TEST_ENTITY);
	// endregion

	private static <BLOCK extends Block, BLOCK_ITEM extends BlockItem> ItemEntry<BLOCK_ITEM> block(BlockEntry<BLOCK> blockEntry)
	{
		return ItemEntry.cast(blockEntry.getSibling(Item.class));
	}

	private static <ENTITY extends Entity, SPAWN_EGG extends ForgeSpawnEggItem<ENTITY>> ItemEntry<SPAWN_EGG> spawnEgg(EntityEntry<ENTITY> entityEntry)
	{
		return ItemEntry.cast(entityEntry.getSibling(Item.class, EntityBuilder.SPAWN_EGG_SUFFIX));
	}

	static void bootstrap()
	{
		REGISTRY.addDataGenerator(ProviderType.RECIPE, provider ->  ShapedRecipeBuilder
				.shaped(COPPER_BLOCK::get)
				.define('#', TTags.Items.INGOTS_COPPER)
				.pattern("###")
				.pattern("###")
				.pattern("###")
				.unlockedBy("has_copper_ingot", RegistrateRecipeProvider.hasItem(TTags.Items.INGOTS_COPPER))
				.save(provider)
		);
	}
}
