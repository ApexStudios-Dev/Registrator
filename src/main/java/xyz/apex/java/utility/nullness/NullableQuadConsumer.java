package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.QuadConsumer;

import java.util.Objects;

@FunctionalInterface
public interface NullableQuadConsumer<@NullableType A, @NullableType B, @NullableType C, @NullableType D> extends QuadConsumer<A, B, C, D>
{
	@Override
	void accept(A a, B b, C c, D d);

	default NullableQuadConsumer<A, B, C, D> andThen(NullableQuadConsumer<? super A, ? super B, ? super C, ? super D> after)
	{
		Objects.requireNonNull(after);

		return (a, b, c, d) -> {
			accept(a, b, c, d);
			after.accept(a, b, c, d);
		};
	}

	static <@NullableType A, @NullableType B, @NullableType C, @NullableType D> NullableQuadConsumer<A, B, C, D> noop()
	{
		return (a, b, c, d) -> { };
	}
}
