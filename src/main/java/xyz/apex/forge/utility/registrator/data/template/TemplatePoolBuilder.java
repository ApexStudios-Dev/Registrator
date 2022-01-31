package xyz.apex.forge.utility.registrator.data.template;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import org.apache.commons.lang3.Validate;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElementType;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class TemplatePoolBuilder
{
	private final TemplatePools pool;
	private final LinkedList<ElementBuilder> elements = Lists.newLinkedList();
	@Nullable private TemplatePools fallbackPool = TemplatePools.EMPTY;

	private TemplatePoolBuilder(TemplatePools pool)
	{
		this.pool = pool;
	}

	public TemplatePoolBuilder fallback(@Nullable TemplatePools fallbackPool)
	{
		this.fallbackPool = fallbackPool;
		return this;
	}

	public ElementBuilder element()
	{
		var element = new ElementBuilder(this);
		elements.add(element);
		return element;
	}

	public ElementBuilder element(int elementIndex)
	{
		return elements.get(elementIndex);
	}

	public ElementBuilder firstElement()
	{
		return elements.getFirst();
	}

	public ElementBuilder lastElement()
	{
		return elements.getLast();
	}

	public TemplatePools getPool()
	{
		return pool;
	}

	public ResourceLocation getPoolName()
	{
		return getPool().getPoolName();
	}

	public TemplatePools getFallbackPool()
	{
		return fallbackPool == null ? TemplatePools.EMPTY : fallbackPool;
	}

	public ResourceLocation getFallbackPoolName()
	{
		return getFallbackPool().getPoolName();
	}

	public JsonObject serialize()
	{
		var json = new JsonObject();
		json.addProperty("name", getPoolName().toString());
		json.addProperty("fallback", getFallbackPoolName().toString());

		var elementsJson = new JsonArray();
		elements.stream().map(ElementBuilder::serialize).forEach(elementsJson::add);
		json.add("elements", elementsJson);

		return json;
	}

	public static TemplatePoolBuilder builder(TemplatePools pool)
	{
		return new TemplatePoolBuilder(pool);
	}

	public static final class ElementBuilder
	{
		private final TemplatePoolBuilder poolBuilder;
		private int weight = 100;
		private Supplier<StructureFeature<?>> structureSupplier = () -> null;
		private NonNullSupplier<StructureProcessorList> processorSupplier = () -> ProcessorLists.EMPTY;
		private StructureTemplatePool.Projection projection = StructureTemplatePool.Projection.RIGID;
		private NonNullSupplier<StructurePoolElementType<?>> elementTypeSupplier = () -> StructurePoolElementType.SINGLE;

		private ElementBuilder(TemplatePoolBuilder poolBuilder)
		{
			this.poolBuilder = poolBuilder;
		}

		public ElementBuilder weight(int weight)
		{
			this.weight = weight;
			return this;
		}

		public ElementBuilder location(NonNullSupplier<StructureFeature<?>> structureSupplier)
		{
			this.structureSupplier = structureSupplier;
			return this;
		}

		public ElementBuilder processor(NonNullSupplier<StructureProcessorList> processorSupplier)
		{
			this.processorSupplier = processorSupplier;
			return this;
		}

		public ElementBuilder projection(StructureTemplatePool.Projection projection)
		{
			this.projection = projection;
			return this;
		}

		public ElementBuilder elementType(NonNullSupplier<StructurePoolElementType<?>> elementTypeSupplier)
		{
			this.elementTypeSupplier = elementTypeSupplier;
			return this;
		}

		public int getWeight()
		{
			return Mth.clamp(weight, 0, 100);
		}

		public StructureFeature<?> getStructure()
		{
			return Validate.notNull(structureSupplier.get());
		}

		public ResourceLocation getStructureName()
		{
			return Validate.notNull(getStructure().getRegistryName());
		}

		public StructureProcessorList getProcessor()
		{
			return processorSupplier.get();
		}

		public ResourceLocation getProcessorName()
		{
			return Validate.notNull(BuiltinRegistries.PROCESSOR_LIST.getKey(getProcessor()));
		}

		public StructureTemplatePool.Projection getProjection()
		{
			return projection;
		}

		public String getProjectionName()
		{
			return getProjection().getSerializedName();
		}

		public StructurePoolElementType<?> getElementType()
		{
			return elementTypeSupplier.get();
		}

		public ResourceLocation getElementTypeName()
		{
			return Validate.notNull(Registry.STRUCTURE_POOL_ELEMENT.getKey(getElementType()));
		}

		public JsonObject serialize()
		{
			var element = new JsonObject();
			element.addProperty("location", getStructureName().toString());
			element.addProperty("processors", getProcessorName().toString());
			element.addProperty("projection", getProjectionName());
			element.addProperty("element_type", getElementTypeName().toString());

			var json = new JsonObject();
			json.addProperty("weight", getWeight());
			json.add("element", element);
			return json;
		}

		public TemplatePoolBuilder end()
		{
			return poolBuilder;
		}
	}
}
