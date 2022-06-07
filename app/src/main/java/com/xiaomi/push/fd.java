package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class fd implements is<fd, Object>, Serializable, Cloneable {
    private static final ji f = new ji("StatsEvent");
    private static final ja g = new ja("", (byte) 3, 1);
    private static final ja h = new ja("", (byte) 8, 2);
    private static final ja i = new ja("", (byte) 8, 3);
    private static final ja j = new ja("", (byte) 11, 4);
    private static final ja k = new ja("", (byte) 11, 5);
    private static final ja l = new ja("", (byte) 8, 6);
    private static final ja m = new ja("", (byte) 11, 7);
    private static final ja n = new ja("", (byte) 11, 8);
    private static final ja o = new ja("", (byte) 8, 9);
    private static final ja p = new ja("", (byte) 8, 10);
    public byte a;

    /* renamed from: a */
    public int f28a;

    /* renamed from: a */
    public String f29a;

    /* renamed from: a */
    private BitSet f30a = new BitSet(6);
    public int b;

    /* renamed from: b */
    public String f31b;
    public int c;

    /* renamed from: c */
    public String f32c;
    public int d;

    /* renamed from: d */
    public String f33d;
    public int e;

    /* renamed from: a */
    public int compareTo(fd fdVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        int a8;
        int a9;
        int a10;
        if (!getClass().equals(fdVar.getClass())) {
            return getClass().getName().compareTo(fdVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m902a()).compareTo(Boolean.valueOf(fdVar.m902a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m902a() && (a10 = it.a(this.a, fdVar.a)) != 0) {
            return a10;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(fdVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a9 = it.a(this.f28a, fdVar.f28a)) != 0) {
            return a9;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(fdVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a8 = it.a(this.b, fdVar.b)) != 0) {
            return a8;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(fdVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a7 = it.a(this.f29a, fdVar.f29a)) != 0) {
            return a7;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(fdVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a6 = it.a(this.f31b, fdVar.f31b)) != 0) {
            return a6;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(fdVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a5 = it.a(this.c, fdVar.c)) != 0) {
            return a5;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(fdVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a4 = it.a(this.f32c, fdVar.f32c)) != 0) {
            return a4;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(fdVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a3 = it.a(this.f33d, fdVar.f33d)) != 0) {
            return a3;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(fdVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a2 = it.a(this.d, fdVar.d)) != 0) {
            return a2;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(fdVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (!j() || (a = it.a(this.e, fdVar.e)) == 0) {
            return 0;
        }
        return a;
    }

    public fd a(byte b) {
        this.a = b;
        a(true);
        return this;
    }

    public fd a(int i2) {
        this.f28a = i2;
        b(true);
        return this;
    }

    public fd a(String str) {
        this.f29a = str;
        return this;
    }

    public void a() {
        if (this.f29a == null) {
            throw new je("Required field 'connpt' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (!m902a()) {
                    throw new je("Required field 'chid' was not found in serialized data! Struct: " + toString());
                } else if (!b()) {
                    throw new je("Required field 'type' was not found in serialized data! Struct: " + toString());
                } else if (c()) {
                    a();
                    return;
                } else {
                    throw new je("Required field 'value' was not found in serialized data! Struct: " + toString());
                }
            } else {
                switch (a.f180a) {
                    case 1:
                        if (a.a == 3) {
                            this.a = jdVar.mo1106a();
                            a(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 2:
                        if (a.a == 8) {
                            this.f28a = jdVar.m1093a();
                            b(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 3:
                        if (a.a == 8) {
                            this.b = jdVar.m1093a();
                            c(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 4:
                        if (a.a == 11) {
                            this.f29a = jdVar.m1100a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 5:
                        if (a.a == 11) {
                            this.f31b = jdVar.m1100a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 6:
                        if (a.a == 8) {
                            this.c = jdVar.m1093a();
                            d(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 7:
                        if (a.a == 11) {
                            this.f32c = jdVar.m1100a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 8:
                        if (a.a == 11) {
                            this.f33d = jdVar.m1100a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 9:
                        if (a.a == 8) {
                            this.d = jdVar.m1093a();
                            e(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 10:
                        if (a.a == 8) {
                            this.e = jdVar.m1093a();
                            f(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                }
                jg.a(jdVar, a.a);
                jdVar.g();
            }
        }
    }

    public void a(boolean z) {
        this.f30a.set(0, z);
    }

    /* renamed from: a */
    public boolean m902a() {
        return this.f30a.get(0);
    }

    /* renamed from: a */
    public boolean m903a(fd fdVar) {
        if (fdVar == null || this.a != fdVar.a || this.f28a != fdVar.f28a || this.b != fdVar.b) {
            return false;
        }
        boolean d = d();
        boolean d2 = fdVar.d();
        if ((d || d2) && (!d || !d2 || !this.f29a.equals(fdVar.f29a))) {
            return false;
        }
        boolean e = e();
        boolean e2 = fdVar.e();
        if ((e || e2) && (!e || !e2 || !this.f31b.equals(fdVar.f31b))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = fdVar.f();
        if ((f2 || f3) && (!f2 || !f3 || this.c != fdVar.c)) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = fdVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.f32c.equals(fdVar.f32c))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = fdVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.f33d.equals(fdVar.f33d))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = fdVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.d != fdVar.d)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = fdVar.j();
        if (j2 || j3) {
            return j2 && j3 && this.e == fdVar.e;
        }
        return true;
    }

    public fd b(int i2) {
        this.b = i2;
        c(true);
        return this;
    }

    public fd b(String str) {
        this.f31b = str;
        return this;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(f);
        jdVar.a(g);
        jdVar.a(this.a);
        jdVar.b();
        jdVar.a(h);
        jdVar.mo1091a(this.f28a);
        jdVar.b();
        jdVar.a(i);
        jdVar.mo1091a(this.b);
        jdVar.b();
        if (this.f29a != null) {
            jdVar.a(j);
            jdVar.a(this.f29a);
            jdVar.b();
        }
        if (this.f31b != null && e()) {
            jdVar.a(k);
            jdVar.a(this.f31b);
            jdVar.b();
        }
        if (f()) {
            jdVar.a(l);
            jdVar.mo1091a(this.c);
            jdVar.b();
        }
        if (this.f32c != null && g()) {
            jdVar.a(m);
            jdVar.a(this.f32c);
            jdVar.b();
        }
        if (this.f33d != null && h()) {
            jdVar.a(n);
            jdVar.a(this.f33d);
            jdVar.b();
        }
        if (i()) {
            jdVar.a(o);
            jdVar.mo1091a(this.d);
            jdVar.b();
        }
        if (j()) {
            jdVar.a(p);
            jdVar.mo1091a(this.e);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f30a.set(1, z);
    }

    public boolean b() {
        return this.f30a.get(1);
    }

    public fd c(int i2) {
        this.c = i2;
        d(true);
        return this;
    }

    public fd c(String str) {
        this.f32c = str;
        return this;
    }

    public void c(boolean z) {
        this.f30a.set(2, z);
    }

    public boolean c() {
        return this.f30a.get(2);
    }

    public fd d(int i2) {
        this.d = i2;
        e(true);
        return this;
    }

    public fd d(String str) {
        this.f33d = str;
        return this;
    }

    public void d(boolean z) {
        this.f30a.set(3, z);
    }

    public boolean d() {
        return this.f29a != null;
    }

    public void e(boolean z) {
        this.f30a.set(4, z);
    }

    public boolean e() {
        return this.f31b != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof fd)) {
            return m903a((fd) obj);
        }
        return false;
    }

    public void f(boolean z) {
        this.f30a.set(5, z);
    }

    public boolean f() {
        return this.f30a.get(3);
    }

    public boolean g() {
        return this.f32c != null;
    }

    public boolean h() {
        return this.f33d != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f30a.get(4);
    }

    public boolean j() {
        return this.f30a.get(5);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvent(");
        sb.append("chid:");
        sb.append((int) this.a);
        sb.append(", ");
        sb.append("type:");
        sb.append(this.f28a);
        sb.append(", ");
        sb.append("value:");
        sb.append(this.b);
        sb.append(", ");
        sb.append("connpt:");
        String str = this.f29a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        if (e()) {
            sb.append(", ");
            sb.append("host:");
            String str2 = this.f31b;
            if (str2 == null) {
                str2 = "null";
            }
            sb.append(str2);
        }
        if (f()) {
            sb.append(", ");
            sb.append("subvalue:");
            sb.append(this.c);
        }
        if (g()) {
            sb.append(", ");
            sb.append("annotation:");
            String str3 = this.f32c;
            if (str3 == null) {
                str3 = "null";
            }
            sb.append(str3);
        }
        if (h()) {
            sb.append(", ");
            sb.append("user:");
            String str4 = this.f33d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (i()) {
            sb.append(", ");
            sb.append("time:");
            sb.append(this.d);
        }
        if (j()) {
            sb.append(", ");
            sb.append("clientIp:");
            sb.append(this.e);
        }
        sb.append(")");
        return sb.toString();
    }
}
