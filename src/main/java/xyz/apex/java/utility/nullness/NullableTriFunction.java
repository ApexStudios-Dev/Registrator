package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.TriFunction;

import java.util.Objects;
import java.util.function.Function;

@NullableType
public interface NullableTriFunction<@NullableType A, @NullableType B, @NullableType C, @NullableType R> extends TriFunction<A, B, C, R>
{
	@Override
	R apply(A a, B b, C c);

	default <@NullableType V> NullableTriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return (a, b, c) -> after.apply(apply(a, b, c));
	}
}
