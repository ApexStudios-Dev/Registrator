package xyz.apex.java.utility.nullness;

import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Extends {@link NonNullBiConsumer} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NonnullBiConsumer<@NonnullType T, @NonnullType U> extends BiConsumer<T, U>, NonNullBiConsumer<T, U>
{
	@Override
	void accept(T t, U u);

	default NonnullBiConsumer<T, U> andThen(NonnullBiConsumer<? super T, ? super U> after)
	{
		Objects.requireNonNull(after);
		return (t, u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}

	static <@NonnullType T, @NonnullType U> NonnullBiConsumer<T, U> noop()
	{
		return (t, u) -> { };
	}
}
