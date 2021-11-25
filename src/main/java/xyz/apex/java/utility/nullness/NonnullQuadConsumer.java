package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.QuadConsumer;

import java.util.Objects;

@FunctionalInterface
public interface NonnullQuadConsumer<@NonnullType A, @NonnullType B, @NonnullType C, @NonnullType D> extends QuadConsumer<A, B, C, D>
{
	@Override
	void accept(A a, B b, C c, D d);

	default NonnullQuadConsumer<A, B, C, D> andThen(NonnullQuadConsumer<? super A, ? super B, ? super C, ? super D> after)
	{
		Objects.requireNonNull(after);

		return (a, b, c, d) -> {
			accept(a, b, c, d);
			after.accept(a, b, c, d);
		};
	}

	static <@NonnullType A, @NonnullType B, @NonnullType C, @NonnullType D> NonnullQuadConsumer<A, B, C, D> noop()
	{
		return (a, b, c, d) -> { };
	}
}
