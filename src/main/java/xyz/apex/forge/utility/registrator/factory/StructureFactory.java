package xyz.apex.forge.utility.registrator.factory;

import com.mojang.serialization.Codec;

import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

@FunctionalInterface
public interface StructureFactory<STRUCTURE extends Structure<FEATURE_CONFIG>, FEATURE_CONFIG extends IFeatureConfig>
{
	STRUCTURE create(Codec<FEATURE_CONFIG> codec);
}
