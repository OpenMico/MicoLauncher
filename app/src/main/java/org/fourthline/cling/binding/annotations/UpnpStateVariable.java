package org.fourthline.cling.binding.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface UpnpStateVariable {
    long allowedValueMaximum() default 0;

    long allowedValueMinimum() default 0;

    Class allowedValueProvider() default void.class;

    Class allowedValueRangeProvider() default void.class;

    long allowedValueStep() default 1;

    String[] allowedValues() default {};

    Class allowedValuesEnum() default void.class;

    String datatype() default "";

    String defaultValue() default "";

    int eventMaximumRateMilliseconds() default 0;

    int eventMinimumDelta() default 0;

    String name() default "";

    boolean sendEvents() default true;
}
