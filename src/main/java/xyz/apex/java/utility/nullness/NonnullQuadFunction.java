package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.QuadFunction;

import java.util.Objects;

@FunctionalInterface
public interface NonnullQuadFunction<@NonnullType A, @NonnullType B, @NonnullType C, @NonnullType D, @NonnullType R> extends QuadFunction<A, B, C, D, R>
{
	@Override
	R apply(A a, B b, C c, D d);

	default <@NonnullType V> NonnullQuadFunction<A, B, C, D, V> andThen(NonnullFunction<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return (a, b, c, d) -> after.apply(apply(a, b, c, d));
	}
}
