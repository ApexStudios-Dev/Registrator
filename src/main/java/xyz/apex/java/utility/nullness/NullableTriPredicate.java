package xyz.apex.java.utility.nullness;

import net.minecraftforge.common.util.TriPredicate;

import java.util.Objects;

@FunctionalInterface
public interface NullableTriPredicate<@NullableType A, @NullableType B, @NullableType C> extends NullableTriFunction<A, B, C, Boolean>, TriPredicate<A, B, C>
{
	@Override
	boolean test(A a, B b, C c);

	@Override
	default Boolean apply(A a, B b, C c)
	{
		return test(a, b, c);
	}

	default NullableTriPredicate<A, B, C> and(NullableTriPredicate<? super A, ? super B, ? super C> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c) -> test(a, b, c) && other.test(a, b, c);
	}

	default NullableTriPredicate<A, B, C> or(NullableTriPredicate<? super A, ? super B, ? super C> other)
	{
		Objects.requireNonNull(other);
		return (a, b, c) -> test(a, b, c) || other.test(a, b, c);
	}
}
