package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.TriFunction;

import java.util.Objects;

@FunctionalInterface
public interface NonnullTriFunction<@NonnullType A, @NonnullType B, @NonnullType C, @NonnullType R> extends TriFunction<A, B, C, R>
{
	@Override
	R apply(A a, B b, C c);

	default <@NonnullType V> NonnullTriFunction<A, B, C, V> andThen(NonnullFunction<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return (a, b, c) -> after.apply(apply(a, b, c));
	}
}
