package com.xiaomi.push;

import com.xiaomi.push.iz;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class jj extends iz {
    private static int d = 10000;
    private static int e = 10000;
    private static int f = 10000;
    private static int g = 10485760;
    private static int h = 104857600;

    /* loaded from: classes4.dex */
    public static class a extends iz.a {
        public a() {
            super(false, true);
        }

        public a(boolean z, boolean z2, int i) {
            super(z, z2, i);
        }

        @Override // com.xiaomi.push.iz.a, com.xiaomi.push.jf
        public jd a(jn jnVar) {
            jj jjVar = new jj(jnVar, this.f178a, this.b);
            if (this.a != 0) {
                jjVar.b(this.a);
            }
            return jjVar;
        }
    }

    public jj(jn jnVar, boolean z, boolean z2) {
        super(jnVar, z, z2);
    }

    @Override // com.xiaomi.push.iz, com.xiaomi.push.jd
    /* renamed from: a */
    public jb mo1106a() {
        byte a2 = mo1106a();
        int a3 = mo1106a();
        if (a3 <= e) {
            return new jb(a2, a3);
        }
        throw new je(3, "Thrift list size " + a3 + " out of range!");
    }

    @Override // com.xiaomi.push.iz, com.xiaomi.push.jd
    /* renamed from: a  reason: collision with other method in class */
    public jc mo1106a() {
        byte a2 = mo1106a();
        byte a3 = mo1106a();
        int a4 = mo1106a();
        if (a4 <= d) {
            return new jc(a2, a3, a4);
        }
        throw new je(3, "Thrift map size " + a4 + " out of range!");
    }

    @Override // com.xiaomi.push.iz, com.xiaomi.push.jd
    /* renamed from: a */
    public jh mo1106a() {
        byte a2 = mo1106a();
        int a3 = mo1106a();
        if (a3 <= f) {
            return new jh(a2, a3);
        }
        throw new je(3, "Thrift set size " + a3 + " out of range!");
    }

    @Override // com.xiaomi.push.iz, com.xiaomi.push.jd
    /* renamed from: a  reason: collision with other method in class */
    public String mo1106a() {
        int a2 = mo1106a();
        if (a2 > g) {
            throw new je(3, "Thrift string size " + a2 + " out of range!");
        } else if (this.a.b() < a2) {
            return mo1091a(a2);
        } else {
            try {
                String str = new String(this.a.a(), this.a.mo1110a(), a2, "UTF-8");
                this.a.a(a2);
                return str;
            } catch (UnsupportedEncodingException unused) {
                throw new ix("JVM DOES NOT SUPPORT UTF-8");
            }
        }
    }

    @Override // com.xiaomi.push.iz, com.xiaomi.push.jd
    /* renamed from: a */
    public ByteBuffer mo1106a() {
        int a2 = mo1106a();
        if (a2 <= h) {
            c(a2);
            if (this.a.b() >= a2) {
                ByteBuffer wrap = ByteBuffer.wrap(this.a.a(), this.a.mo1110a(), a2);
                this.a.a(a2);
                return wrap;
            }
            byte[] bArr = new byte[a2];
            this.a.b(bArr, 0, a2);
            return ByteBuffer.wrap(bArr);
        }
        throw new je(3, "Thrift binary size " + a2 + " out of range!");
    }
}
