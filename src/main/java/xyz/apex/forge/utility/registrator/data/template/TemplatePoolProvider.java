package xyz.apex.forge.utility.registrator.data.template;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@SuppressWarnings({ "unused", "RedundantThrows", "UnstableApiUsage" })
public abstract class TemplatePoolProvider implements DataProvider
{
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Logger LOGGER = LogManager.getLogger();

	protected final DataGenerator generator;
	private final Map<TemplatePools, TemplatePoolBuilder> poolBuilderMap = Maps.newHashMap();

	protected TemplatePoolProvider(DataGenerator generator)
	{
		this.generator = generator;
	}

	protected abstract void registerPools();

	public TemplatePoolBuilder pool(TemplatePools pool)
	{
		return poolBuilderMap.computeIfAbsent(pool, TemplatePoolBuilder::builder);
	}

	public TemplatePoolBuilder pool(ResourceLocation poolName)
	{
		return pool(TemplatePools.of(poolName));
	}

	public TemplatePoolBuilder pool(String poolNamespace, String poolPath)
	{
		return pool(TemplatePools.of(poolNamespace, poolPath));
	}

	@Override
	public final void run(HashCache cache) throws IOException
	{
		poolBuilderMap.clear();
		registerPools();
		var dataPath = generator.getOutputFolder().resolve("data");
		poolBuilderMap.forEach((poolName, poolBuilder) -> saveTemplatePool(cache, poolBuilder, dataPath));

	}

	@Override
	public String getName()
	{
		return "TemplatePollProvider";
	}

	public static void saveTemplatePool(HashCache cache, TemplatePoolBuilder builder, Path dataPath)
	{
		try
		{
			var poolName = builder.getPoolName();
			var poolPath = dataPath.resolve(Paths.get(poolName.getNamespace(), "worldgen", "template_pool", poolName.getPath() + ".json"));
			var serialized = builder.serialize();
			DataProvider.save(GSON, cache, serialized, poolPath);
		}
		catch(IOException e)
		{
			LOGGER.error("Couldn't save TemplatePool {}", builder.getPoolName(), e);
		}
	}
}
