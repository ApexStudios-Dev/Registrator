package xyz.apex.forge.utility.registrator.builder;

import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.loot.RegistrateEntityLootTables;
import com.tterrag.registrate.providers.loot.RegistrateLootTableProvider;
import com.tterrag.registrate.util.OneTimeEventReceiver;
import com.tterrag.registrate.util.nullness.NonnullType;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.tags.ITag;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.FMLPlayMessages;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.EntityEntry;
import xyz.apex.forge.utility.registrator.factory.EntityFactory;
import xyz.apex.forge.utility.registrator.factory.item.SpawnEggItemFactory;
import xyz.apex.forge.utility.registrator.helper.ForgeSpawnEggItem;
import xyz.apex.java.utility.nullness.NonnullBiConsumer;
import xyz.apex.java.utility.nullness.NonnullBiFunction;
import xyz.apex.java.utility.nullness.NonnullSupplier;
import xyz.apex.java.utility.nullness.NonnullUnaryOperator;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public final class EntityBuilder<OWNER extends AbstractRegistrator<OWNER>, ENTITY extends Entity, PARENT> extends RegistratorBuilder<OWNER, EntityType<?>, EntityType<ENTITY>, PARENT, EntityBuilder<OWNER, ENTITY, PARENT>, EntityEntry<ENTITY>>
{
	public static final String SPAWN_EGG_SUFFIX = "_spawn_egg";

	private final EntityClassification entityClassification;
	private final EntityFactory<ENTITY> entityFactory;
	private NonnullUnaryOperator<EntityType.Builder<ENTITY>> propertiesModifier = NonnullUnaryOperator.identity();
	@Nullable private NonnullSupplier<IRenderFactory<? super ENTITY>> renderer = null;
	@Nullable private NonnullSupplier<AttributeModifierMap.MutableAttribute> attributes = null;
	@Nullable private EntitySpawnPlacementRegistry.PlacementType placementType = null;
	@Nullable private Heightmap.Type heightMapType = null;
	@Nullable private EntitySpawnPlacementRegistry.IPlacementPredicate<ENTITY> placementPredicate;

	public EntityBuilder(OWNER owner, PARENT parent, String registryName, BuilderCallback callback, EntityClassification entityClassification, EntityFactory<ENTITY> entityFactory)
	{
		super(owner, parent, registryName, callback, EntityType.class, EntityEntry::new, EntityEntry::cast);

		this.entityClassification = entityClassification;
		this.entityFactory = entityFactory;
		onRegister(this::onRegister);

		// apply registrate defaults
		defaultLang();
	}

	private void onRegister(EntityType<ENTITY> entityType)
	{
		if(renderer != null)
		{
			DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
				OneTimeEventReceiver.addModListener(FMLClientSetupEvent.class, event -> {
					try
					{
						RenderingRegistry.registerEntityRenderingHandler(entityType, renderer.get());
					}
					catch(Exception e)
					{
						throw new IllegalStateException("Failed to register renderer for Entity " + entityType.getRegistryName(), e);
					}
				});
			});
		}

		if(attributes != null)
			OneTimeEventReceiver.addModListener(EntityAttributeCreationEvent.class, event -> event.put((EntityType<? extends LivingEntity>) entityType, attributes.get().build()));

		if(placementType != null && heightMapType != null && placementPredicate != null)
			registerSpawnPlacement(entityType, placementType, heightMapType, placementPredicate);
	}

	@Override
	protected @NonnullType EntityType<ENTITY> createEntry()
	{
		EntityType.Builder<ENTITY> builder = EntityType.Builder.of(entityFactory::create, entityClassification);
		builder = propertiesModifier.apply(builder);
		return builder.build(getRegistryNameFull());
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> properties(NonnullUnaryOperator<EntityType.Builder<ENTITY>> propertiesModifier)
	{
		this.propertiesModifier = this.propertiesModifier.andThen(propertiesModifier);
		return this;
	}

	// region: Properties Wrappers
	public EntityBuilder<OWNER, ENTITY, PARENT> sized(float width, float height)
	{
		return properties(properties -> properties.sized(width, height));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> noSummon()
	{
		return properties(EntityType.Builder::noSummon);
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> noSave()
	{
		return properties(EntityType.Builder::noSave);
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> fireImmune()
	{
		return properties(EntityType.Builder::fireImmune);
	}

	@Deprecated
	public EntityBuilder<OWNER, ENTITY, PARENT> immuneTo(Block... blocks)
	{
		return properties(properties -> properties.immuneTo(blocks));
	}

	@SafeVarargs
	public final EntityBuilder<OWNER, ENTITY, PARENT> immuneTo(NonnullSupplier<? extends Block>... blocks)
	{
		return properties(properties -> properties.immuneTo(Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> canSpawnFarFromPlayer()
	{
		return properties(EntityType.Builder::canSpawnFarFromPlayer);
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> clientTrackingRange(int clientTrackingRange)
	{
		return properties(properties -> properties.clientTrackingRange(clientTrackingRange));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> updateInterval(int updateInterval)
	{
		return properties(properties -> properties.updateInterval(updateInterval));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> setUpdateInterval(int updateInterval)
	{
		return properties(properties -> properties.setUpdateInterval(updateInterval));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> setTrackingRange(int trackingRange)
	{
		return properties(properties -> properties.setTrackingRange(trackingRange));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> setShouldReceiveVelocityUpdates(boolean shouldReceiveVelocityUpdates)
	{
		return properties(properties -> properties.setShouldReceiveVelocityUpdates(shouldReceiveVelocityUpdates));
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> setCustomClientFactory(NonnullBiFunction<FMLPlayMessages.SpawnEntity, World, ENTITY> customClientFactory)
	{
		return properties(properties -> properties.setCustomClientFactory(customClientFactory));
	}
	// endregion

	public EntityBuilder<OWNER, ENTITY, PARENT> renderer(NonnullSupplier<IRenderFactory<? super ENTITY>> renderer)
	{
		this.renderer = renderer;
		return this;
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> attributes(NonnullSupplier<AttributeModifierMap.MutableAttribute> attributes)
	{
		this.attributes = attributes;
		return this;
	}

	public <ITEM extends ForgeSpawnEggItem<ENTITY>> EntityBuilder<OWNER, ENTITY, PARENT> simpleSpawnEgg(int backgroundColor, int highlightColor, SpawnEggItemFactory<ENTITY, ITEM> itemFactory)
	{
		return spawnEgg(backgroundColor, highlightColor, itemFactory).build();
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> simpleSpawnEgg(int backgroundColor, int highlightColor)
	{
		return simpleSpawnEgg(backgroundColor, highlightColor, SpawnEggItemFactory.forEntity());
	}

	public <ITEM extends ForgeSpawnEggItem<ENTITY>> ItemBuilder<OWNER, ITEM, EntityBuilder<OWNER, ENTITY, PARENT>> spawnEgg(int backgroundColor, int highlightColor, SpawnEggItemFactory<ENTITY, ITEM> itemFactory)
	{
		return owner.spawnEggItem(getRegistryName() + SPAWN_EGG_SUFFIX, this, asSupplier(), backgroundColor, highlightColor, itemFactory);
	}

	public ItemBuilder<OWNER, ForgeSpawnEggItem<ENTITY>, EntityBuilder<OWNER, ENTITY, PARENT>> spawnEgg(int backgroundColor, int highlightColor)
	{
		return spawnEgg(backgroundColor, highlightColor, SpawnEggItemFactory.forEntity());
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> spawnPlacement(EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightmapType, EntitySpawnPlacementRegistry.IPlacementPredicate<ENTITY> placementPredicate)
	{
		this.placementType = placementType;
		this.heightMapType = heightmapType;
		this.placementPredicate = placementPredicate;
		return this;
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> defaultLang()
	{
		return lang(EntityType::getDescriptionId);
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> lang(String localizedValue)
	{
		return lang(EntityType::getDescriptionId, localizedValue);
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> lang(String languageKey, String localizedValue)
	{
		return lang(languageKey, EntityType::getDescriptionId, localizedValue);
	}

	public EntityBuilder<OWNER, ENTITY, PARENT> loot(NonnullBiConsumer<RegistrateEntityLootTables, EntityType<ENTITY>> consumer)
	{
		return setDataGenerator(ProviderType.LOOT, (ctx, provider) -> provider.addLootAction(RegistrateLootTableProvider.LootType.ENTITY, lootTables -> consumer.accept(lootTables, ctx.getEntry())));
	}

	@SafeVarargs
	public final EntityBuilder<OWNER, ENTITY, PARENT> tag(ITag.INamedTag<EntityType<?>>... tags)
	{
		return tag(ProviderType.ENTITY_TAGS, tags);
	}

	@SafeVarargs
	public final EntityBuilder<OWNER, ENTITY, PARENT> removeTag(ITag.INamedTag<EntityType<?>>... tags)
	{
		return removeTags(ProviderType.ENTITY_TAGS, tags);
	}

	public static <ENTITY extends Entity> void registerSpawnPlacement(EntityType<ENTITY> entityType, EntitySpawnPlacementRegistry.PlacementType placementType, Heightmap.Type heightMapType, EntitySpawnPlacementRegistry.IPlacementPredicate<ENTITY> placementPredicate)
	{
		EntitySpawnPlacementRegistry.Entry entitySpawnPlacementRegistryEntry = EntitySpawnPlacementRegistry.DATA_BY_TYPE.put(entityType, new EntitySpawnPlacementRegistry.Entry(heightMapType, placementType, placementPredicate));

		if(entitySpawnPlacementRegistryEntry != null)
			throw new IllegalStateException("Duplicate registration for type " + Registry.ENTITY_TYPE.getKey(entityType));
	}
}
