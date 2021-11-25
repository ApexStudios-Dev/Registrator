package xyz.apex.java.utility.nullness;

import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Extends {@link net.minecraftforge.common.util.NonNullSupplier} to allow better compatibility with MinecraftForge<br>
 * Extends {@link NonNullSupplier} to allow better compatibility with Registrate
 */
@FunctionalInterface
public interface NonnullSupplier<@NonnullType T> extends Supplier<T>, NonNullSupplier<T>, net.minecraftforge.common.util.NonNullSupplier<T>
{
	@Override
	T get();

	static <@NullableType T> NonnullSupplier<T> of(Supplier<T> supplier)
	{
		return of(supplier, () -> "Unexpected null value from supplier");
	}

	static <@NullableType T> NonnullSupplier<T> of(Supplier<T> supplier, NonnullSupplier<String> errorMessage)
	{
		return () -> Objects.requireNonNull(supplier.get(), errorMessage);
	}
}
