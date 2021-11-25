package xyz.apex.java.utility.nullness;

import net.minecraftforge.common.util.NonNullPredicate;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Extends {@link net.minecraftforge.common.util.NonNullPredicate} to allow better compatibility with MinecraftForge
 */
@FunctionalInterface
public interface NonnullPredicate<@NonnullType T> extends Predicate<T>, NonnullFunction<T, Boolean>, NonNullPredicate<T>
{
	@Override
	boolean test(T t);

	@Override
	default Boolean apply(T t)
	{
		return test(t);
	}

	default NonnullPredicate<T> and(NonnullPredicate<? super T> other)
	{
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}

	default NonnullPredicate<T> or(NonnullPredicate<? super T> other)
	{
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}
}
