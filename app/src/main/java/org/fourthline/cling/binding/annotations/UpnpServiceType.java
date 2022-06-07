package org.fourthline.cling.binding.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface UpnpServiceType {
    String namespace() default "schemas-upnp-org";

    String value();

    int version() default 1;
}
