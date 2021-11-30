package xyz.apex.java.utility;

import com.google.common.collect.*;

import xyz.apex.java.utility.nullness.NonnullFunction;
import xyz.apex.java.utility.nullness.NonnullUnaryOperator;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({ "unused", "NullableProblems" })
public final class Apex
{
	public static <V> List<V> makeImmutableList(NonnullUnaryOperator<ImmutableList.Builder<V>> consumer)
	{
		return consumer.apply(ImmutableList.builder()).build();
	}

	public static <V> List<V> makeImmutableList(List<V> list)
	{
		if(list instanceof ImmutableList)
			return list;
		return makeImmutableList(builder -> builder.addAll(list));
	}

	public static <V> List<V> copyIfImmutableList(List<V> list)
	{
		return copyIfImmutableList(list, Lists::newArrayList);
	}

	public static <V> List<V> copyIfImmutableList(List<V> list, NonnullFunction<ImmutableList<V>, List<V>> copier)
	{
		return list instanceof ImmutableList ? copier.apply((ImmutableList<V>) list) : list;
	}

	public static <V> Set<V> makeImmutableSet(NonnullUnaryOperator<ImmutableSet.Builder<V>> consumer)
	{
		return consumer.apply(ImmutableSet.builder()).build();
	}

	public static <V> Set<V> makeImmutableSet(Set<V> set)
	{
		if(set instanceof ImmutableSet)
			return set;
		return makeImmutableSet(builder -> builder.addAll(set));
	}

	public static <V> Set<V> copyIfImmutableSet(Set<V> set)
	{
		return copyIfImmutableSet(set, Sets::newHashSet);
	}

	public static <V> Set<V> copyIfImmutableSet(Set<V> set, NonnullFunction<ImmutableSet<V>, Set<V>> copier)
	{
		return set instanceof ImmutableSet ? copier.apply((ImmutableSet<V>) set) : set;
	}

	public static <K, V> Map<K, V> makeImmutableMap(NonnullUnaryOperator<ImmutableMap.Builder<K, V>> consumer)
	{
		return consumer.apply(ImmutableMap.builder()).build();
	}

	public static <K, V> Map<K, V> makeImmutableMap(Map<K, V> map)
	{
		if(map instanceof ImmutableMap)
			return map;
		return makeImmutableMap(builder -> builder.putAll(map));
	}

	public static <K, V> Map<K, V> copyIfImmutableMap(Map<K, V> map)
	{
		return copyIfImmutableMap(map, Maps::newHashMap);
	}

	public static <K, V> Map<K, V> copyIfImmutableMap(Map<K, V> map, NonnullFunction<ImmutableMap<K, V>, Map<K, V>> copier)
	{
		return map instanceof ImmutableMap ? copier.apply((ImmutableMap<K, V>) map) : map;
	}
}
