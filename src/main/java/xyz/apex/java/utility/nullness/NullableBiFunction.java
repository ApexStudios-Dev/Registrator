package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.BiFunction;

@FunctionalInterface
public interface NullableBiFunction<@NullableType T, @NullableType U, @NullableType R> extends BiFunction<T, U, R>
{
	@Override
	R apply(T t, U u);

	default <@NullableType V> NullableBiFunction<T, U, V> andThen(NullableFunction<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return (t, u) -> after.apply(apply(t, u));
	}
}
