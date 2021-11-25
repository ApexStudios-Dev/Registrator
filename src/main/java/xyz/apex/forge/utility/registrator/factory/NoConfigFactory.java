package xyz.apex.forge.utility.registrator.factory;

@FunctionalInterface
public interface NoConfigFactory<TYPE>
{
	TYPE create();
}
