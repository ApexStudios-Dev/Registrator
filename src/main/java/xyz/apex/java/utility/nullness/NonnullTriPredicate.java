package xyz.apex.java.utility.nullness;

import java.util.Objects;

@FunctionalInterface
public interface NonnullTriPredicate<@NonnullType A, @NonnullType B, @NonnullType C> extends NonnullTriFunction<A, B, C, Boolean>
{
	boolean test(A a, B b, C c);

	@Override
	default Boolean apply(A a, B b, C c)
	{
		return test(a, b, c);
	}

	default NonnullTriPredicate<A, B, C> and(NonnullTriPredicate<? super A, ? super B, ? super C> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c) -> test(a, b, c) && other.test(a, b, c);
	}

	default NonnullTriPredicate<A, B, C> negate()
	{
		return (a, b, c) -> !test(a, b, c);
	}

	default NonnullTriPredicate<A, B, C> or(NonnullTriPredicate<? super A, ? super B, ? super C> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c) -> test(a, b, c) || other.test(a, b, c);
	}
}
