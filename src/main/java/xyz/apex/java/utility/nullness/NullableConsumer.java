package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface NullableConsumer<@NullableType T> extends Consumer<T>
{
	@Override
	void accept(T t);

	default NullableConsumer<T> andThen(NullableConsumer<? super T> after)
	{
		Objects.requireNonNull(after);
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	static <@NullableType T> NullableConsumer<T> noop()
	{
		return t -> { };
	}
}
