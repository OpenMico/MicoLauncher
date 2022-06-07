package com.google.protobuf;

/* compiled from: Android.java */
/* loaded from: classes2.dex */
final class b {
    private static final Class<?> a = a("libcore.io.Memory");
    private static final boolean b;

    static {
        b = a("org.robolectric.Robolectric") != null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        return a != null && !b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Class<?> b() {
        return a;
    }

    private static <T> Class<T> a(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
