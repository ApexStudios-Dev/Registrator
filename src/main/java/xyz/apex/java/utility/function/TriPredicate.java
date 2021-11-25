package xyz.apex.java.utility.function;

import java.util.Objects;

@FunctionalInterface
public interface TriPredicate<A, B, C> extends TriFunction<A, B, C, Boolean>, net.minecraftforge.common.util.TriPredicate<A, B, C>
{
	boolean test(A a, B b, C c);

	@Override
	default Boolean apply(A a, B b, C c)
	{
		return test(a, b, c);
	}

	default TriPredicate<A, B, C> and(TriPredicate<? super A, ? super B, ? super C> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c) -> test(a, b, c) && other.test(a, b, c);
	}

	@Override
	default TriPredicate<A, B, C> negate()
	{
		return (a, b, c) -> !test(a, b, c);
	}

	default TriPredicate<A, B, C> or(TriPredicate<? super A, ? super B, ? super C> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c) -> test(a, b, c) || other.test(a, b, c);
	}
}
