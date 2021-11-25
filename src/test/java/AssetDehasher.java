import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AssetDehasher
{
	public static void main(String[] args)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		Logger logger = LogManager.getLogger();

		Path gameDir = Paths.get(System.getenv("APPDATA"), ".minecraft");
		Path assetsDir = gameDir.resolve("assets");
		Path indexesDir = assetsDir.resolve("indexes");
		Path objectsDir = assetsDir.resolve("objects");

		Path outputDir = Paths.get("./output");

		if(Files.notExists(gameDir) || Files.notExists(assetsDir) || Files.notExists(indexesDir) || Files.notExists(objectsDir))
		{
			logger.error("Minecraft directory does not exist (.minecraft/assets/indexes/objects), ensure you have launched the game once.");
			return;
		}

		List<Path> indexFiles;

		try
		{
			indexFiles = Files.walk(indexesDir).filter(path -> StringUtils.endsWithIgnoreCase(path.getFileName().toString(), ".json")).collect(Collectors.toList());
		}
		catch(IOException e)
		{
			logger.error("Error occurred while searching for asset index files", e);
			return;
		}

		if(indexFiles.isEmpty())
		{
			logger.error("Asset index files found, ensure you have launched the game once.");
			return;
		}

		List<Pair<Path, Long>> validationList = Lists.newArrayList();

		for(Path index : indexFiles)
		{
			String minecraftVersion = index.getFileName().toString();
			minecraftVersion = FilenameUtils.removeExtension(minecraftVersion);

			logger.info("Processing index file '{}.json'", minecraftVersion);

			try
			{
				try(BufferedReader reader = Files.newBufferedReader(index))
				{
					JsonObject root = gson.fromJson(reader, JsonObject.class);
					JsonObject objects = root.getAsJsonObject("objects");

					for(Map.Entry<String, JsonElement> entry : objects.entrySet())
					{
						String name = entry.getKey();
						logger.info("Processing element '{}/{}'", minecraftVersion, name);
						JsonObject element = entry.getValue().getAsJsonObject();

						String hash = element.get("hash").getAsString();
						long size = element.get("size").getAsLong();

						String folderName = hash.substring(0, 2);

						Path hashDir = objectsDir.resolve(folderName);
						Path hashFile = hashDir.resolve(hash);

						if(Files.notExists(hashDir) || Files.notExists(hashFile))
						{
							logger.warn("Skipping hash file '{}', directory or file does not exist ('{}')", hash, objectsDir.relativize(hashFile));
							continue;
						}

						Path outputFile = outputDir.resolve(Paths.get(minecraftVersion, name));
						validationList.add(Pair.of(outputFile, size));
						Files.deleteIfExists(outputFile);
						Files.createDirectories(outputFile.getParent());
						Files.copy(hashFile, outputFile);
					}
				}
			}
			catch(IOException e)
			{
				logger.warn("Error occurred while processing asset index file '{}'", objectsDir.relativize(index), e);
			}
		}

		logger.info("Processed {} files", validationList.size());

		try
		{
			Thread.sleep(1500);
		}
		catch(InterruptedException e)
		{
			logger.catching(e);
		}

		logger.info("Validating file sizes");
		int counter = 0;

		for(Pair<Path, Long> pair : validationList)
		{
			Path outputFile = pair.getKey();
			long sizeFromIndex = pair.getRight();

			try
			{
				long sizeOnDisk = Files.size(outputFile);

				if(sizeOnDisk != sizeFromIndex)
				{
					counter++;
					logger.warn("Asset '{}' failed validation, file sizes differ {} != {} (sizeFromIndex, sizeOnDisk)", outputDir.relativize(outputFile), sizeFromIndex, sizeOnDisk);
				}
			}
			catch(IOException e)
			{
				logger.error("Error occurred while validating asset '{}'", outputDir.relativize(outputFile), e);
			}
		}

		if(counter > 0)
			logger.warn("{} assets failed validation", counter);
		else
			logger.info("Successfully validated all {} assets", validationList.size());
	}
}
