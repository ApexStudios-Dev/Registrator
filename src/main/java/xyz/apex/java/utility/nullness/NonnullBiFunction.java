package xyz.apex.java.utility.nullness;

import com.tterrag.registrate.util.nullness.NonNullBiFunction;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Extends {@link NonNullBiFunction} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NonnullBiFunction<@NonnullType T, @NonnullType U, @NonnullType R> extends BiFunction<T, U, R>, NonNullBiFunction<T, U, R>
{
	@Override
	R apply(T t, U u);

	default <@NonnullType V> NonnullBiFunction<T, U, V> andThen(NonnullFunction<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return (t, u) -> after.apply(apply(t, u));
	}
}
