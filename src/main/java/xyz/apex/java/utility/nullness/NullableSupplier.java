package xyz.apex.java.utility.nullness;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Extends {@link com.tterrag.registrate.util.nullness.NullableSupplier} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NullableSupplier<@NullableType T> extends Supplier<T>, com.tterrag.registrate.util.nullness.NullableSupplier<T>
{
	@Override
	T get();

	default T getNonnull()
	{
		return getNonnull(() -> "Unexpected null value from supplier");
	}

	default T getNonnull(NonnullSupplier<String> errorMessage)
	{
		return Objects.requireNonNull(get(), errorMessage);
	}

	default NonnullSupplier<T> asNonnull()
	{
		return this::getNonnull;
	}

	default NonnullSupplier<T> asNonnull(NonnullSupplier<String> errorMessage)
	{
		return () -> getNonnull(errorMessage);
	}
}
