package com.efs.sdk.base.a.i;

/* loaded from: classes.dex */
public final class f {
    public c a;
    public com.efs.sdk.base.a.d.a b;
    public d c;
    public g d;

    /* synthetic */ f(byte b) {
        this();
    }

    private f() {
        this.a = new c();
        this.c = new d();
        this.d = new g();
    }

    /* loaded from: classes.dex */
    public static class a {
        private static final f a = new f((byte) 0);

        public static /* synthetic */ f a() {
            return a;
        }
    }

    public final void a(String str, String str2, String str3) {
        this.d.a(str, str2, str3);
    }

    public final void a(int i) {
        com.efs.sdk.base.a.d.a aVar = this.b;
        if (aVar != null) {
            aVar.a(a("flow_limit", i));
        }
    }

    public final void a(int i, String str) {
        if (this.b != null || com.efs.sdk.base.a.d.a.a().d) {
            b a2 = a("flow_limit_type", i);
            a2.put("code", str);
            this.b.a(a2);
        }
    }

    public final b a(String str, int i) {
        b bVar = new b("efs_core", str, this.a.c);
        bVar.put("cver", Integer.valueOf(i));
        return bVar;
    }
}
