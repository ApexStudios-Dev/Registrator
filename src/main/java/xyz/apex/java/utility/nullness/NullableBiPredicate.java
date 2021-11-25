package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface NullableBiPredicate<@NullableType T, @NullableType U> extends BiPredicate<T, U>, NullableBiFunction<T, U, Boolean>
{
	@Override
	boolean test(T t, U u);

	@Override
	default Boolean apply(T t, U u)
	{
		return test(t, u);
	}

	default NullableBiPredicate<T, U> and(NullableBiPredicate<? super T, ? super U> other)
	{
		Objects.requireNonNull(other);
		return (t, u) -> test(t, u) && other.test(t, u);
	}

	default NullableBiPredicate<T, U> or(NullableBiPredicate<? super T, ? super U> other)
	{
		Objects.requireNonNull(other);
		return (t, u) -> test(t, u) || other.test(t, u);
	}
}
