package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface NullableUnaryOperator<@NullableType T> extends UnaryOperator<T>, NullableFunction<T, T>
{
	default NullableUnaryOperator<T> andThen(NullableUnaryOperator<T> after)
	{
		Objects.requireNonNull(after);
		return t -> after.apply(apply(t));
	}

	static <@NullableType T> NullableUnaryOperator<T> identity()
	{
		return t -> t;
	}
}
