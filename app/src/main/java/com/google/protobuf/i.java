package com.google.protobuf;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ExtensionRegistryFactory.java */
/* loaded from: classes2.dex */
public final class i {
    static final Class<?> a = a();

    static Class<?> a() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static ExtensionRegistryLite b() {
        ExtensionRegistryLite a2 = a("newInstance");
        return a2 != null ? a2 : new ExtensionRegistryLite();
    }

    public static ExtensionRegistryLite c() {
        ExtensionRegistryLite a2 = a("getEmptyRegistry");
        return a2 != null ? a2 : ExtensionRegistryLite.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(ExtensionRegistryLite extensionRegistryLite) {
        Class<?> cls = a;
        return cls != null && cls.isAssignableFrom(extensionRegistryLite.getClass());
    }

    private static final ExtensionRegistryLite a(String str) {
        Class<?> cls = a;
        if (cls == null) {
            return null;
        }
        try {
            return (ExtensionRegistryLite) cls.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
