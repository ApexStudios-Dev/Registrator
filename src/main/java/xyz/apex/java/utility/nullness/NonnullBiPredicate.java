package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface NonnullBiPredicate<@NonnullType T, @NonnullType U> extends BiPredicate<T, U>, NonnullBiFunction<T, U, Boolean>
{
	@Override
	boolean test(T t, U u);

	@Override
	default Boolean apply(T t, U u)
	{
		return test(t, u);
	}

	default NonnullBiPredicate<T, U> and(NonnullBiPredicate<? super T, ? super U> other)
	{
		Objects.requireNonNull(other);
		return (t, u) -> test(t, u) && other.test(t, u);
	}

	default NonnullBiPredicate<T, U> or(NonnullBiPredicate<? super T, ? super U> other)
	{
		Objects.requireNonNull(other);
		return (t, u) -> test(t, u) || other.test(t, u);
	}
}
