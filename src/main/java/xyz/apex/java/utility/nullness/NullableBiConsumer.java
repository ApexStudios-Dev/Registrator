package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface NullableBiConsumer<@NullableType T, @NullableType U> extends BiConsumer<T, U>
{
	@Override
	void accept(T t, U u);

	default NullableBiConsumer<T, U> andThen(NullableBiConsumer<? super T, ? super U> after)
	{
		Objects.requireNonNull(after);
		return (t, u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}

	static <@NullableType T, @NullableType U> NullableBiConsumer<T, U> noop()
	{
		return (t, u) -> { };
	}
}
