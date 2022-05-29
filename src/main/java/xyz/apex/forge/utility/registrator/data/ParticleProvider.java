package xyz.apex.forge.utility.registrator.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.particles.ParticleType;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class ParticleProvider implements IDataProvider
{
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Logger LOGGER = LogManager.getLogger();

	protected static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(ResourcePackType.CLIENT_RESOURCES, ".png", "textures");

	protected final DataGenerator generator;
	protected final ExistingFileHelper fileHelper;

	private final Map<ResourceLocation, ParticleDefinition> particleDefinitions = Maps.newHashMap();

	protected ParticleProvider(DataGenerator generator, ExistingFileHelper fileHelper)
	{
		this.generator = generator;
		this.fileHelper = fileHelper;
	}

	protected abstract void registerParticleDefs();

	@Override
	public final void run(DirectoryCache cache) throws IOException
	{
		particleDefinitions.clear();
		registerParticleDefs();
		validateParticleDefs();
		Path dataPath = generator.getOutputFolder().resolve("assets");
		particleDefinitions.forEach((particleName, def) -> saveParticleDefinition(cache, particleName, def, dataPath));
	}

	@Override
	public String getName()
	{
		return "Particles";
	}

	private void validateParticleDefs()
	{
		particleDefinitions.forEach((particleName, definition) -> {
			for(ResourceLocation texture : definition.textures)
			{
				ResourceLocation relativePath = new ResourceLocation(texture.getNamespace(), "particle/" + texture.getPath());
				Preconditions.checkArgument(fileHelper.exists(relativePath, TEXTURE), "Texture %s does not exist in any known resource pack", relativePath);
			}
		});
	}

	public static void saveParticleDefinition(DirectoryCache cache, ResourceLocation particleName, ParticleDefinition definition, Path dataPath)
	{
		try
		{
			Path particlePath = dataPath.resolve(Paths.get(particleName.getNamespace(), "particles", particleName.getPath() + ".json"));
			JsonElement serialized = serializeParticleDefinition(definition);
			IDataProvider.save(GSON, cache, serialized, particlePath);
		}
		catch(IOException e)
		{
			LOGGER.error("Couldn't save ParticleDefinition {}", particleName);
		}
	}

	private static JsonElement serializeParticleDefinition(ParticleDefinition definition)
	{
		JsonObject json = new JsonObject();

		JsonArray texturesJson = new JsonArray();
		definition.textures.stream().map(Object::toString).forEach(texturesJson::add);
		json.add("textures", texturesJson);

		return json;
	}

	/*
		Note: All texture paths are relative to `particle` folder under your namespace/textures path
		Full relative path looks like so `assets/<namespace>/textures/particle/<particle_texture_path>`

		Do not include the file extension in the path, it gets appended automatically by the game at runtime
		and is assumed to be .PNG

		Example Registration:
			build.texture("my_namespace", "my_particle")

		This would result in the following path
			`assets/my_namespace/textures/particle/my_particle`
	 */
	public ParticleDefinition definition(ParticleType<?> particleType)
	{
		ResourceLocation name = Objects.requireNonNull(particleType.getRegistryName());
		return particleDefinitions.computeIfAbsent(name, $ -> new ParticleDefinition());
	}

	public final class ParticleDefinition
	{
		private final Set<ResourceLocation> textures = Sets.newHashSet();

		private ParticleDefinition()
		{
		}

		public ParticleDefinition texture(ResourceLocation texture)
		{
			textures.add(texture);
			return this;
		}

		public ParticleDefinition textures(ResourceLocation... textures)
		{
			Collections.addAll(this.textures, textures);
			return this;
		}

		public ParticleDefinition texture(String namespace, String texturePath)
		{
			return texture(new ResourceLocation(namespace, texturePath));
		}
	}
}
