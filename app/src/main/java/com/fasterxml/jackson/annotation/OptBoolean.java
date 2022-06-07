package com.fasterxml.jackson.annotation;

/* loaded from: classes.dex */
public enum OptBoolean {
    TRUE,
    FALSE,
    DEFAULT;

    public Boolean asBoolean() {
        if (this == DEFAULT) {
            return null;
        }
        return this == TRUE ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean asPrimitive() {
        return this == TRUE;
    }

    public static OptBoolean fromBoolean(Boolean bool) {
        if (bool == null) {
            return DEFAULT;
        }
        return bool.booleanValue() ? TRUE : FALSE;
    }

    public static boolean equals(Boolean bool, Boolean bool2) {
        if (bool == null) {
            return bool2 == null;
        }
        return bool.equals(bool2);
    }
}
