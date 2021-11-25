package xyz.apex.forge.utility.registrator.factory.item;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

import xyz.apex.java.utility.nullness.NonnullSupplier;

@FunctionalInterface
public interface MusicDiscItemFactory<ITEM extends MusicDiscItem>
{
	MusicDiscItemFactory<MusicDiscItem> DEFAULT = MusicDiscItem::new;

	ITEM create(int comparatorValue, NonnullSupplier<SoundEvent> sound, Item.Properties properties);
}
