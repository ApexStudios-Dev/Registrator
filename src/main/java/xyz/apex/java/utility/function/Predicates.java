package xyz.apex.java.utility.function;

import java.util.Objects;
import java.util.function.Predicate;

public final class Predicates
{
	public static <T> Predicate<T> alwaysTrue()
	{
		return t -> true;
	}

	public static <T> Predicate<T> alwaysFalse()
	{
		return t -> false;
	}

	public static <T> Predicate<T> isNonnull()
	{
		return Objects::isNull;
	}

	public static <T> Predicate<T> isNull()
	{
		return Objects::isNull;
	}
}
