package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes5.dex */
public @interface ServletSecurity {

    /* loaded from: classes5.dex */
    public enum EmptyRoleSemantic {
        PERMIT,
        DENY
    }

    /* loaded from: classes5.dex */
    public enum TransportGuarantee {
        NONE,
        CONFIDENTIAL
    }

    HttpMethodConstraint[] httpMethodConstraints() default {};

    HttpConstraint value() default @HttpConstraint;
}
