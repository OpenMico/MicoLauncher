package com.google.protobuf;

/* compiled from: NewInstanceSchemas.java */
/* loaded from: classes2.dex */
final class ad {
    private static final ab a = c();
    private static final ab b = new ac();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ab a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ab b() {
        return b;
    }

    private static ab c() {
        try {
            return (ab) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
