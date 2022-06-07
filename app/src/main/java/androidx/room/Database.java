package androidx.room;

import androidx.annotation.RestrictTo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes.dex */
public @interface Database {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    Class<?>[] autoMigrations() default {};

    Class<?>[] entities();

    boolean exportSchema() default true;

    int version();

    Class<?>[] views() default {};
}
