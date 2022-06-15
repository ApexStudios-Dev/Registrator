package xyz.apex.forge.utility.registrator.provider;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.RegistryEntry;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

import xyz.apex.forge.utility.registrator.AbstractRegistrator;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public final class BlockListReporter implements DataProvider
{
	private final AbstractRegistrator<?> registrator;
	private final DataGenerator generator;

	public BlockListReporter(AbstractRegistrator<?> registrator, DataGenerator generator)
	{
		this.registrator = registrator;
		this.generator = generator;
	}

	@Override
	public void run(CachedOutput cache) throws IOException
	{
		var path = generator.getOutputFolder().resolve(Paths.get("reports", registrator.getModId(), "blocks.json"));
		var json = serializeBlocks();
		DataProvider.saveStable(cache, json, path);
	}

	@Override
	public String getName()
	{
		return "Block List: " + registrator.getModId();
	}

	private Iterable<Block> getBlocks()
	{
		return registrator
				.getAll(Registry.BLOCK_REGISTRY)
				.stream()
				.filter(RegistryEntry::isPresent)
				.sorted((a, b) -> a.getId().compareNamespaced(b.getId()))
				.distinct()
				.map(RegistryEntry::get)
				.collect(ImmutableList.toImmutableList())
		;
	}

	private JsonObject serializeBlocks()
	{
		var json = new JsonObject();
		getBlocks().forEach(block -> json.add(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).toString(), serializeBlock(block)));
		return json;
	}

	private JsonObject serializeBlock(Block block)
	{
		var blockJson = new JsonObject();
		blockJson.add("properties", serializeBlockProperties(block));
		blockJson.add("states", serializeBlockStates(block));
		return blockJson;
	}

	private JsonObject serializeBlockProperties(Block block)
	{
		var json = new JsonObject();
		block.getStateDefinition().getProperties().forEach(property -> json.add(property.getName(), serializeBlockPropertyPossibleValues(property)));
		return json;
	}

	private JsonArray serializeBlockPropertyPossibleValues(Property<?> property)
	{
		var json = new JsonArray();
		property.getPossibleValues().stream().map(comparable -> Util.getPropertyName(property, comparable)).forEach(json::add);
		return json;
	}

	private JsonArray serializeBlockStates(Block block)
	{
		var json = new JsonArray();
		block.getStateDefinition().getPossibleStates().stream().map(blockState -> serializeBlockState(block, blockState)).forEach(json::add);
		return json;
	}

	private JsonObject serializeBlockState(Block block, BlockState blockState)
	{
		var json = new JsonObject();
		json.add("properties", serializeBlockStateProperties(block, blockState));
		json.addProperty("id", Block.getId(blockState));
		json.addProperty("default", blockState == block.defaultBlockState());
		return json;
	}

	private JsonObject serializeBlockStateProperties(Block block, BlockState blockState)
	{
		var json = new JsonObject();
		block.getStateDefinition().getProperties().forEach(property -> json.addProperty(property.getName(), Util.getPropertyName(property, blockState.getValue(property))));
		return json;
	}
}
