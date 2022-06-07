package com.xiaomi.micolauncher.api.converter.handler;

import com.xiaomi.micolauncher.api.MinaResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface ResponseHandler {
    Class cls() default MinaResponse.class;

    Class handler() default MinaHandler.class;
}
