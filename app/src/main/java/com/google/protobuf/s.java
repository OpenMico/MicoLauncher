package com.google.protobuf;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ManifestSchemaFactory.java */
/* loaded from: classes2.dex */
public final class s implements an {
    private static final x b = new x() { // from class: com.google.protobuf.s.1
        @Override // com.google.protobuf.x
        public boolean a(Class<?> cls) {
            return false;
        }

        @Override // com.google.protobuf.x
        public w b(Class<?> cls) {
            throw new IllegalStateException("This should never be called.");
        }
    };
    private final x a;

    public s() {
        this(a());
    }

    private s(x xVar) {
        this.a = (x) Internal.a(xVar, "messageInfoFactory");
    }

    @Override // com.google.protobuf.an
    public <T> am<T> a(Class<T> cls) {
        ao.a((Class<?>) cls);
        w b2 = this.a.b(cls);
        if (!b2.b()) {
            return a(cls, b2);
        }
        if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
            return aa.a(ao.c(), l.a(), b2.c());
        }
        return aa.a(ao.a(), l.b(), b2.c());
    }

    private static <T> am<T> a(Class<T> cls, w wVar) {
        if (GeneratedMessageLite.class.isAssignableFrom(cls)) {
            if (a(wVar)) {
                return z.a(cls, wVar, ad.b(), q.b(), ao.c(), l.a(), v.b());
            }
            return z.a(cls, wVar, ad.b(), q.b(), ao.c(), (j<?>) null, v.b());
        } else if (a(wVar)) {
            return z.a(cls, wVar, ad.a(), q.a(), ao.a(), l.b(), v.a());
        } else {
            return z.a(cls, wVar, ad.a(), q.a(), ao.b(), (j<?>) null, v.a());
        }
    }

    private static boolean a(w wVar) {
        return wVar.a() == ProtoSyntax.PROTO2;
    }

    private static x a() {
        return new a(n.a(), b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ManifestSchemaFactory.java */
    /* loaded from: classes2.dex */
    public static class a implements x {
        private x[] a;

        a(x... xVarArr) {
            this.a = xVarArr;
        }

        @Override // com.google.protobuf.x
        public boolean a(Class<?> cls) {
            for (x xVar : this.a) {
                if (xVar.a(cls)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.protobuf.x
        public w b(Class<?> cls) {
            x[] xVarArr = this.a;
            for (x xVar : xVarArr) {
                if (xVar.a(cls)) {
                    return xVar.b(cls);
                }
            }
            throw new UnsupportedOperationException("No factory is available for message type: " + cls.getName());
        }
    }

    private static x b() {
        try {
            return (x) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return b;
        }
    }
}
