package org.fourthline.cling.registry.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface After {
}
