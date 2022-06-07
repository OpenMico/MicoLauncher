package org.fourthline.cling.binding.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface UpnpService {
    UpnpServiceId serviceId();

    UpnpServiceType serviceType();

    Class[] stringConvertibleTypes() default {};

    boolean supportsQueryStateVariables() default true;
}
