package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface NullablePredicate<@NullableType T> extends Predicate<T>, NullableFunction<T, Boolean>
{
	@Override
	boolean test(T t);

	@Override
	default Boolean apply(T t)
	{
		return test(t);
	}

	default NullablePredicate<T> and(NullablePredicate<? super T> other)
	{
		Objects.requireNonNull(other);
		return t -> test(t) && other.test(t);
	}

	default NullablePredicate<T> or(NullablePredicate<? super T> other)
	{
		Objects.requireNonNull(other);
		return t -> test(t) || other.test(t);
	}
}
