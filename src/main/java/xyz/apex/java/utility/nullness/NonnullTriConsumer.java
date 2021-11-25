package xyz.apex.java.utility.nullness;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.Objects;

@FunctionalInterface
public interface NonnullTriConsumer<@NonnullType A, @NonnullType B, @NonnullType C> extends TriConsumer<A, B, C>
{
	@Override
	void accept(A a, B b, C c);

	default NonnullTriConsumer<A, B, C> andThen(NonnullTriConsumer<? super A, ? super B, ? super C> after)
	{
		Objects.requireNonNull(after);

		return (a, b, c) -> {
			accept(a, b, c);
			after.accept(a, b, c);
		};
	}

	static <@NonnullType A, @NonnullType B, @NonnullType C> NonnullTriConsumer<A, B, C> noop()
	{
		return (a, b, c) -> { };
	}
}
