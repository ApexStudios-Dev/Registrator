package xyz.apex.java.utility.nullness;

import com.tterrag.registrate.util.nullness.NonNullConsumer;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Extends {@link net.minecraftforge.common.util.NonNullConsumer} to allow better compatibility with MinecraftForge<br>
 * Extends {@link NonNullConsumer} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NonnullConsumer<@NonnullType T> extends Consumer<T>, NonNullConsumer<T>, net.minecraftforge.common.util.NonNullConsumer<T>
{
	@Override
	void accept(T t);

	default NonnullConsumer<T> andThen(NonnullConsumer<? super T> after)
	{
		Objects.requireNonNull(after);
		return t -> {
			accept(t);
			after.accept(t);
		};
	}

	static <@NonnullType T> NonnullConsumer<T> noop()
	{
		return t -> { };
	}
}
