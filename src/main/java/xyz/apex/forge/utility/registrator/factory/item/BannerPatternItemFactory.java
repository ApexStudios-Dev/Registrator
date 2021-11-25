package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.BannerPattern;

@FunctionalInterface
public interface BannerPatternItemFactory<ITEM extends BannerPatternItem>
{
	BannerPatternItemFactory<BannerPatternItem> DEFAULT = BannerPatternItem::new;

	ITEM create(BannerPattern bannerPattern, Item.Properties properties);
}
