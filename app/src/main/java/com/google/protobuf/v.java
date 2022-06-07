package com.google.protobuf;

/* compiled from: MapFieldSchemas.java */
/* loaded from: classes2.dex */
final class v {
    private static final t a = c();
    private static final t b = new u();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static t a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static t b() {
        return b;
    }

    private static t c() {
        try {
            return (t) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
