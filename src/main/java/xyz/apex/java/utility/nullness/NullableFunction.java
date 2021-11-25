package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface NullableFunction<@NullableType T, @NullableType R> extends Function<T, R>
{
	@Override
	R apply(T t);

	default <@NullableType V> NullableFunction<V, R> compose(NullableFunction<? super V, ? extends T> before)
	{
		Objects.requireNonNull(before);
		return v -> apply(before.apply(v));
	}

	default <@NullableType V> NullableFunction<T, V> andThen(NullableFunction<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return t -> after.apply(apply(t));
	}

	static <@NullableType T> NullableFunction<T, T> identity()
	{
		return t -> t;
	}
}
