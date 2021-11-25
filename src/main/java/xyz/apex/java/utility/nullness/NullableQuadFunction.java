package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.QuadFunction;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface NullableQuadFunction<@NullableType A, @NullableType B, @NullableType C, @NullableType D, @NullableType R> extends QuadFunction<A, B, C, D, R>
{
	@Override
	R apply(A a, B b, C c, D d);

	default <@NullableType V> NullableQuadFunction<A, B, C, D, V> andThen(Function<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return (a, b, c, d) -> after.apply(apply(a, b, c, d));
	}
}
