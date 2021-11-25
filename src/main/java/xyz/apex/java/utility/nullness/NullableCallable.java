package xyz.apex.java.utility.nullness;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface NullableCallable<@NullableType T> extends Callable<T>
{
}
