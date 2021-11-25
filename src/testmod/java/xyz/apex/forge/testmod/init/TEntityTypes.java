package xyz.apex.forge.testmod.init;

import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.world.gen.Heightmap;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;
import xyz.apex.forge.utility.registrator.entry.EntityEntry;

public final class TEntityTypes
{
	private static final TRegistry REGISTRY = TRegistry.getRegistry();

	public static final EntityEntry<PigEntity> TEST_ENTITY = REGISTRY
			.entity("test_entity", EntityClassification.CREATURE, PigEntity::new)
				.attributes(PigEntity::createAttributes)
				.renderer(() -> PigRenderer::new)
				.sized(.9F, .9F)
				.clientTrackingRange(10)
				.spawnPlacement(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules)
				.loot((lootTables, entityType) -> lootTables.add(entityType, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.PORKCHOP).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))).apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, AbstractRegistrator.ENTITY_ON_FIRE))).apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))))))

				.simpleSpawnEgg(15771042, 14377823)
			.register();

	static void bootstrap() { }
}
