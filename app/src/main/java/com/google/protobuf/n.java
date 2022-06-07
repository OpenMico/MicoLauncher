package com.google.protobuf;

/* compiled from: GeneratedMessageInfoFactory.java */
/* loaded from: classes2.dex */
class n implements x {
    private static final n a = new n();

    private n() {
    }

    public static n a() {
        return a;
    }

    @Override // com.google.protobuf.x
    public boolean a(Class<?> cls) {
        return GeneratedMessageLite.class.isAssignableFrom(cls);
    }

    @Override // com.google.protobuf.x
    public w b(Class<?> cls) {
        if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
            try {
                return (w) GeneratedMessageLite.a((Class<GeneratedMessageLite>) cls.asSubclass(GeneratedMessageLite.class)).d();
            } catch (Exception e) {
                throw new RuntimeException("Unable to get message info for " + cls.getName(), e);
            }
        } else {
            throw new IllegalArgumentException("Unsupported message type: " + cls.getName());
        }
    }
}
