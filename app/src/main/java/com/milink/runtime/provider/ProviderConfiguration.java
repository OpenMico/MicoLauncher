package com.milink.runtime.provider;

import androidx.annotation.NonNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes2.dex */
public @interface ProviderConfiguration {
    Class<? extends AccessFilter>[] accessFilters() default {};

    @NonNull
    String path();

    @NonNull
    String readPermission() default "";

    @NonNull
    String writePermission() default "";
}
