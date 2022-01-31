package xyz.apex.forge.utility.registrator.factory;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

@FunctionalInterface
public interface StructureFactory<STRUCTURE extends StructureFeature<FEATURE_CONFIG>, FEATURE_CONFIG extends FeatureConfiguration>
{
	STRUCTURE create(Codec<FEATURE_CONFIG> codec);
}
