package xyz.apex.java.utility.nullness;

import java.util.Objects;

@FunctionalInterface
public interface NonnullQuadPredicate<@NonnullType A, @NonnullType B, @NonnullType C, @NonnullType D> extends NonnullQuadFunction<A, B, C, D, Boolean>
{
	boolean test(A a, B b, C c, D d);

	@Override
	default Boolean apply(A a, B b, C c, D d)
	{
		return test(a, b, c, d);
	}

	default NonnullQuadPredicate<A, B, C, D> and(NonnullQuadPredicate<? super A, ? super B, ? super C, ? super D> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c, d) -> test(a, b, c, d) && other.test(a, b, c, d);
	}

	default NonnullQuadPredicate<A, B, C, D> negate()
	{
		return (a, b, c, d) -> !test(a, b, c, d);
	}

	default NonnullQuadPredicate<A, B, C, D> or(NonnullQuadPredicate<? super A, ? super B, ? super C, ? super D> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c, d) -> test(a, b, c, d) || other.test(a, b, c, d);
	}
}
