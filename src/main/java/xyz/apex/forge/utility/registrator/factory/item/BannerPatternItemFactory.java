package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPattern;

@FunctionalInterface
public interface BannerPatternItemFactory<ITEM extends BannerPatternItem>
{
	BannerPatternItemFactory<BannerPatternItem> DEFAULT = BannerPatternItem::new;

	ITEM create(TagKey<BannerPattern> bannerPattern, Item.Properties properties);
}
