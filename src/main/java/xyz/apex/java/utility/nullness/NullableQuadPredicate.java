package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.QuadPredicate;

import java.util.Objects;

@FunctionalInterface
public interface NullableQuadPredicate<@NullableType A, @NullableType B, @NullableType C, @NullableType D> extends NullableQuadFunction<A, B, C, D, Boolean>, QuadPredicate<A, B, C, D>
{
	@Override
	boolean test(A a, B b, C c, D d);

	@Override
	default Boolean apply(A a, B b, C c, D d)
	{
		return test(a, b, c, d);
	}

	default NullableQuadPredicate<A, B, C, D> and(NullableQuadPredicate<? super A, ? super B, ? super C, ? super D> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c, d) -> test(a, b, c, d) && other.test(a, b, c, d);
	}

	default NullableQuadPredicate<A, B, C, D> or(NullableQuadPredicate<? super A, ? super B, ? super C, ? super D> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c, d) -> test(a, b, c, d) || other.test(a, b, c, d);
	}
}
