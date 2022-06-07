package com.xiaomi.push;

import com.xiaomi.push.iz;

/* loaded from: classes4.dex */
public class iw {
    private final jd a;
    private final jm b;

    public iw() {
        this(new iz.a());
    }

    public iw(jf jfVar) {
        this.b = new jm();
        this.a = jfVar.a(this.b);
    }

    public void a(is isVar, byte[] bArr) {
        try {
            this.b.a(bArr);
            isVar.a(this.a);
        } finally {
            this.a.k();
        }
    }
}
