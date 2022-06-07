package miuix.arch.component;

import androidx.annotation.NonNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface ComponentTask {
    @NonNull
    String[] dependency() default {};

    boolean multiProcess() default false;

    @NonNull
    String name() default "";

    int notEarlierThan() default 1;

    int notLaterThan() default 8;

    boolean onMainThread() default false;
}
