package xyz.apex.java.utility;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import xyz.apex.java.utility.nullness.NonnullUnaryOperator;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
}
