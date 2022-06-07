package com.xiaomi.push;

import com.xiaomi.push.iz;
import java.io.ByteArrayOutputStream;

/* loaded from: classes4.dex */
public class iy {
    private final ByteArrayOutputStream a;
    private final jk b;
    private jd c;

    public iy() {
        this(new iz.a());
    }

    public iy(jf jfVar) {
        this.a = new ByteArrayOutputStream();
        this.b = new jk(this.a);
        this.c = jfVar.a(this.b);
    }

    public byte[] a(is isVar) {
        this.a.reset();
        isVar.b(this.c);
        return this.a.toByteArray();
    }
}
