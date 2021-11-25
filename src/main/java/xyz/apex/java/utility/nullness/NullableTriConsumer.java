package xyz.apex.java.utility.nullness;

import xyz.apex.java.utility.function.TriConsumer;

import java.util.Objects;

@FunctionalInterface
public interface NullableTriConsumer<@NullableType A, @NullableType B, @NullableType C> extends TriConsumer<A, B, C>
{
	@Override
	void accept(A a, B b, C c);

	default NullableTriConsumer<A, B, C> andThen(NullableTriConsumer<? super A, ? super B, ? super C> after)
	{
		Objects.requireNonNull(after);

		return (a, b, c) -> {
			accept(a, b, c);
			after.accept(a, b, c);
		};
	}

	static <@NullableType A, @NullableType B, @NullableType C> NullableTriConsumer<A, B, C> noop()
	{
		return (a, b, c) -> { };
	}
}
