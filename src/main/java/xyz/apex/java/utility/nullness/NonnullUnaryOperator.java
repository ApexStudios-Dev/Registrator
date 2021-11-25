package xyz.apex.java.utility.nullness;

import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Extends {@link NonNullUnaryOperator} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NonnullUnaryOperator<@NonnullType T> extends UnaryOperator<T>, NonnullFunction<T, T>, NonNullUnaryOperator<T>
{
	default NonnullUnaryOperator<T> andThen(NonnullUnaryOperator<T> after)
	{
		Objects.requireNonNull(after);
		return t -> after.apply(apply(t));
	}

	static <@NonnullType T> NonnullUnaryOperator<T> identity()
	{
		return t -> t;
	}
}
