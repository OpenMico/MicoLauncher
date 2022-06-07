package com.google.protobuf;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ExtensionSchemas.java */
/* loaded from: classes2.dex */
public final class l {
    private static final j<?> a = new k();
    private static final j<?> b = c();

    private static j<?> c() {
        try {
            return (j) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static j<?> a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static j<?> b() {
        j<?> jVar = b;
        if (jVar != null) {
            return jVar;
        }
        throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
    }
}
