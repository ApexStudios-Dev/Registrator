package xyz.apex.java.utility.nullness;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Nonnull
@TypeQualifierDefault({ ElementType.PARAMETER, ElementType.TYPE_PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ParametersAreNonnullByDefault
{ }
