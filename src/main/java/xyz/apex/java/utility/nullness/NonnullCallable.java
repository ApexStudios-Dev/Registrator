package xyz.apex.java.utility.nullness;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface NonnullCallable<@NonnullType T> extends Callable<T>
{
}
