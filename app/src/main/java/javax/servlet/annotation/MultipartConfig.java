package javax.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface MultipartConfig {
    int fileSizeThreshold() default 0;

    String location() default "";

    long maxFileSize() default -1;

    long maxRequestSize() default -1;
}
