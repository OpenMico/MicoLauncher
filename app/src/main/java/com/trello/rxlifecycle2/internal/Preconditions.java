package com.trello.rxlifecycle2.internal;

/* loaded from: classes2.dex */
public final class Preconditions {
    public static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    private Preconditions() {
        throw new AssertionError("No instances.");
    }
}
