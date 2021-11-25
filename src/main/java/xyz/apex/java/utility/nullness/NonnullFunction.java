package xyz.apex.java.utility.nullness;

import com.tterrag.registrate.util.nullness.NonNullFunction;

import java.util.Objects;
import java.util.function.Function;

/**
 * Extends {@link net.minecraftforge.common.util.NonNullFunction} to allow better compatibility with MinecraftForge<br>
 * Extends {@link NonNullFunction} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NonnullFunction<@NonnullType T, @NonnullType R> extends Function<T, R>, NonNullFunction<T, R>, net.minecraftforge.common.util.NonNullFunction<T, R>
{
	@Override
	R apply(T t);

	default <@NonnullType V> NonnullFunction<V, R> compose(NonnullFunction<? super V, ? extends T> before)
	{
		Objects.requireNonNull(before);
		return v -> apply(before.apply(v));
	}

	default <@NonnullType V> NonnullFunction<T, V> andThen(NonnullFunction<? super R, ? extends V> after)
	{
		Objects.requireNonNull(after);
		return t -> after.apply(apply(t));
	}

	static <@NonnullType T> NonnullFunction<T, T> identity()
	{
		return t -> t;
	}
}
