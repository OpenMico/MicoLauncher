package com.xiaomi.push;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class iz extends jd {
    private static final ji d = new ji();
    protected int a;

    /* renamed from: a */
    protected boolean f177a;
    protected boolean b;
    protected boolean c = false;
    private byte[] e = new byte[1];
    private byte[] f = new byte[2];
    private byte[] g = new byte[4];
    private byte[] h = new byte[8];
    private byte[] i = new byte[1];
    private byte[] j = new byte[2];
    private byte[] k = new byte[4];
    private byte[] l = new byte[8];

    /* loaded from: classes4.dex */
    public static class a implements jf {
        protected int a;

        /* renamed from: a */
        protected boolean f178a;
        protected boolean b;

        public a() {
            this(false, true);
        }

        public a(boolean z, boolean z2) {
            this(z, z2, 0);
        }

        public a(boolean z, boolean z2, int i) {
            this.f178a = false;
            this.b = true;
            this.f178a = z;
            this.b = z2;
            this.a = i;
        }

        @Override // com.xiaomi.push.jf
        public jd a(jn jnVar) {
            iz izVar = new iz(jnVar, this.f178a, this.b);
            int i = this.a;
            if (i != 0) {
                izVar.b(i);
            }
            return izVar;
        }
    }

    public iz(jn jnVar, boolean z, boolean z2) {
        super(jnVar);
        this.f177a = false;
        this.b = true;
        this.f177a = z;
        this.b = z2;
    }

    private int a(byte[] bArr, int i, int i2) {
        c(i2);
        return this.a.b(bArr, i, i2);
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public byte mo1106a() {
        if (this.a.b() >= 1) {
            byte b = this.a.a()[this.a.mo1110a()];
            this.a.a(1);
            return b;
        }
        a(this.i, 0, 1);
        return this.i[0];
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public double mo1106a() {
        return Double.longBitsToDouble(mo1106a());
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public int mo1106a() {
        byte[] bArr = this.k;
        int i = 0;
        if (this.a.b() >= 4) {
            bArr = this.a.a();
            i = this.a.mo1110a();
            this.a.a(4);
        } else {
            a(this.k, 0, 4);
        }
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public long mo1106a() {
        byte[] bArr = this.l;
        int i = 0;
        if (this.a.b() >= 8) {
            bArr = this.a.a();
            i = this.a.mo1110a();
            this.a.a(8);
        } else {
            a(this.l, 0, 8);
        }
        return (bArr[i + 7] & 255) | ((bArr[i] & 255) << 56) | ((bArr[i + 1] & 255) << 48) | ((bArr[i + 2] & 255) << 40) | ((bArr[i + 3] & 255) << 32) | ((bArr[i + 4] & 255) << 24) | ((bArr[i + 5] & 255) << 16) | ((bArr[i + 6] & 255) << 8);
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public ja mo1106a() {
        byte a2 = mo1106a();
        return new ja("", a2, a2 == 0 ? (short) 0 : mo1106a());
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public jb mo1106a() {
        return new jb(mo1106a(), mo1106a());
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public jc mo1106a() {
        return new jc(mo1106a(), mo1106a(), mo1106a());
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public jh mo1106a() {
        return new jh(mo1106a(), mo1106a());
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public ji mo1106a() {
        return d;
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public String mo1106a() {
        int a2 = mo1106a();
        if (this.a.b() < a2) {
            return mo1091a(a2);
        }
        try {
            String str = new String(this.a.a(), this.a.mo1110a(), a2, "UTF-8");
            this.a.a(a2);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new ix("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public String mo1091a(int i) {
        try {
            c(i);
            byte[] bArr = new byte[i];
            this.a.b(bArr, 0, i);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new ix("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public ByteBuffer mo1106a() {
        int a2 = mo1106a();
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

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public short mo1106a() {
        byte[] bArr = this.j;
        int i = 0;
        if (this.a.b() >= 2) {
            bArr = this.a.a();
            i = this.a.mo1110a();
            this.a.a(2);
        } else {
            a(this.j, 0, 2);
        }
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public void mo1106a() {
    }

    @Override // com.xiaomi.push.jd
    public void a(byte b) {
        this.e[0] = b;
        this.a.a(this.e, 0, 1);
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public void mo1091a(int i) {
        byte[] bArr = this.g;
        bArr[0] = (byte) ((i >> 24) & 255);
        bArr[1] = (byte) ((i >> 16) & 255);
        bArr[2] = (byte) ((i >> 8) & 255);
        bArr[3] = (byte) (i & 255);
        this.a.a(this.g, 0, 4);
    }

    @Override // com.xiaomi.push.jd
    public void a(long j) {
        byte[] bArr = this.h;
        bArr[0] = (byte) ((j >> 56) & 255);
        bArr[1] = (byte) ((j >> 48) & 255);
        bArr[2] = (byte) ((j >> 40) & 255);
        bArr[3] = (byte) ((j >> 32) & 255);
        bArr[4] = (byte) ((j >> 24) & 255);
        bArr[5] = (byte) ((j >> 16) & 255);
        bArr[6] = (byte) ((j >> 8) & 255);
        bArr[7] = (byte) (j & 255);
        this.a.a(this.h, 0, 8);
    }

    @Override // com.xiaomi.push.jd
    public void a(ja jaVar) {
        a(jaVar.a);
        a(jaVar.f180a);
    }

    @Override // com.xiaomi.push.jd
    public void a(jb jbVar) {
        a(jbVar.a);
        mo1091a(jbVar.f181a);
    }

    @Override // com.xiaomi.push.jd
    public void a(jc jcVar) {
        a(jcVar.a);
        a(jcVar.b);
        mo1091a(jcVar.f182a);
    }

    @Override // com.xiaomi.push.jd
    public void a(ji jiVar) {
    }

    @Override // com.xiaomi.push.jd
    public void a(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            mo1091a(bytes.length);
            this.a.a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new ix("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.jd
    public void a(ByteBuffer byteBuffer) {
        int limit = (byteBuffer.limit() - byteBuffer.position()) - byteBuffer.arrayOffset();
        mo1091a(limit);
        this.a.a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), limit);
    }

    @Override // com.xiaomi.push.jd
    public void a(short s) {
        byte[] bArr = this.f;
        bArr[0] = (byte) ((s >> 8) & 255);
        bArr[1] = (byte) (s & 255);
        this.a.a(this.f, 0, 2);
    }

    @Override // com.xiaomi.push.jd
    public void a(boolean z) {
        a(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.xiaomi.push.jd
    /* renamed from: a */
    public boolean mo1106a() {
        return mo1106a() == 1;
    }

    @Override // com.xiaomi.push.jd
    public void b() {
    }

    public void b(int i) {
        this.a = i;
        this.c = true;
    }

    @Override // com.xiaomi.push.jd
    public void c() {
        a((byte) 0);
    }

    protected void c(int i) {
        if (i < 0) {
            throw new ix("Negative length: " + i);
        } else if (this.c) {
            this.a -= i;
            if (this.a < 0) {
                throw new ix("Message length exceeded: " + i);
            }
        }
    }

    @Override // com.xiaomi.push.jd
    public void d() {
    }

    @Override // com.xiaomi.push.jd
    public void e() {
    }

    @Override // com.xiaomi.push.jd
    public void f() {
    }

    @Override // com.xiaomi.push.jd
    public void g() {
    }

    @Override // com.xiaomi.push.jd
    public void h() {
    }

    @Override // com.xiaomi.push.jd
    public void i() {
    }

    @Override // com.xiaomi.push.jd
    public void j() {
    }
}
