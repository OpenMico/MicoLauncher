package org.apache.commons.lang3;

import com.fasterxml.jackson.core.JsonPointer;
import com.umeng.analytics.pro.c;

/* loaded from: classes5.dex */
public class ClassPathUtils {
    public static String toFullyQualifiedName(Class<?> cls, String str) {
        Validate.notNull(cls, "Parameter '%s' must not be null!", c.R);
        Validate.notNull(str, "Parameter '%s' must not be null!", "resourceName");
        return toFullyQualifiedName(cls.getPackage(), str);
    }

    public static String toFullyQualifiedName(Package r5, String str) {
        Validate.notNull(r5, "Parameter '%s' must not be null!", c.R);
        Validate.notNull(str, "Parameter '%s' must not be null!", "resourceName");
        return r5.getName() + "." + str;
    }

    public static String toFullyQualifiedPath(Class<?> cls, String str) {
        Validate.notNull(cls, "Parameter '%s' must not be null!", c.R);
        Validate.notNull(str, "Parameter '%s' must not be null!", "resourceName");
        return toFullyQualifiedPath(cls.getPackage(), str);
    }

    public static String toFullyQualifiedPath(Package r5, String str) {
        Validate.notNull(r5, "Parameter '%s' must not be null!", c.R);
        Validate.notNull(str, "Parameter '%s' must not be null!", "resourceName");
        return r5.getName().replace('.', JsonPointer.SEPARATOR) + "/" + str;
    }
}
