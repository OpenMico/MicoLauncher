package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ih implements is<ih, Object>, Serializable, Cloneable {
    public int a;

    /* renamed from: a  reason: collision with other field name */
    public long f132a;

    /* renamed from: a  reason: collision with other field name */
    public hv f133a;

    /* renamed from: a  reason: collision with other field name */
    public hw f134a;

    /* renamed from: a  reason: collision with other field name */
    public String f135a;

    /* renamed from: a  reason: collision with other field name */
    public Map<String, String> f137a;
    public int b;

    /* renamed from: b  reason: collision with other field name */
    public long f139b;

    /* renamed from: b  reason: collision with other field name */
    public String f140b;
    public int c;

    /* renamed from: c  reason: collision with other field name */
    public String f142c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q;
    public String r;
    private static final ji s = new ji("XmPushActionRegistration");
    private static final ja t = new ja("", (byte) 11, 1);
    private static final ja u = new ja("", (byte) 12, 2);
    private static final ja v = new ja("", (byte) 11, 3);
    private static final ja w = new ja("", (byte) 11, 4);
    private static final ja x = new ja("", (byte) 11, 5);
    private static final ja y = new ja("", (byte) 11, 6);
    private static final ja z = new ja("", (byte) 11, 7);
    private static final ja A = new ja("", (byte) 11, 8);
    private static final ja B = new ja("", (byte) 11, 9);
    private static final ja C = new ja("", (byte) 11, 10);
    private static final ja D = new ja("", (byte) 11, 11);
    private static final ja E = new ja("", (byte) 11, 12);
    private static final ja F = new ja("", (byte) 8, 13);
    private static final ja G = new ja("", (byte) 8, 14);
    private static final ja H = new ja("", (byte) 11, 15);
    private static final ja I = new ja("", (byte) 11, 16);
    private static final ja J = new ja("", (byte) 11, 17);
    private static final ja K = new ja("", (byte) 11, 18);
    private static final ja L = new ja("", (byte) 8, 19);
    private static final ja M = new ja("", (byte) 8, 20);
    private static final ja N = new ja("", (byte) 2, 21);
    private static final ja O = new ja("", (byte) 10, 22);
    private static final ja P = new ja("", (byte) 10, 23);
    private static final ja Q = new ja("", (byte) 11, 24);
    private static final ja R = new ja("", (byte) 11, 25);
    private static final ja S = new ja("", (byte) 13, 100);
    private static final ja T = new ja("", (byte) 2, 101);
    private static final ja U = new ja("", (byte) 11, 102);

    /* renamed from: a  reason: collision with other field name */
    private BitSet f136a = new BitSet(7);

    /* renamed from: a  reason: collision with other field name */
    public boolean f138a = true;

    /* renamed from: b  reason: collision with other field name */
    public boolean f141b = false;

    public boolean A() {
        return this.f136a.get(6);
    }

    public boolean B() {
        return this.r != null;
    }

    /* renamed from: a */
    public int compareTo(ih ihVar) {
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
        int a11;
        int a12;
        int a13;
        int a14;
        int a15;
        int a16;
        int a17;
        int a18;
        int a19;
        int a20;
        int a21;
        int a22;
        int a23;
        int a24;
        int a25;
        int a26;
        int a27;
        int a28;
        if (!getClass().equals(ihVar.getClass())) {
            return getClass().getName().compareTo(ihVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1045a()).compareTo(Boolean.valueOf(ihVar.m1045a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1045a() && (a28 = it.a(this.f135a, ihVar.f135a)) != 0) {
            return a28;
        }
        int compareTo2 = Boolean.valueOf(m1047b()).compareTo(Boolean.valueOf(ihVar.m1047b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m1047b() && (a27 = it.a(this.f134a, ihVar.f134a)) != 0) {
            return a27;
        }
        int compareTo3 = Boolean.valueOf(m1048c()).compareTo(Boolean.valueOf(ihVar.m1048c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m1048c() && (a26 = it.a(this.f140b, ihVar.f140b)) != 0) {
            return a26;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ihVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a25 = it.a(this.f142c, ihVar.f142c)) != 0) {
            return a25;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ihVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a24 = it.a(this.d, ihVar.d)) != 0) {
            return a24;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ihVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a23 = it.a(this.e, ihVar.e)) != 0) {
            return a23;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ihVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a22 = it.a(this.f, ihVar.f)) != 0) {
            return a22;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ihVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a21 = it.a(this.g, ihVar.g)) != 0) {
            return a21;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ihVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a20 = it.a(this.h, ihVar.h)) != 0) {
            return a20;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ihVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a19 = it.a(this.i, ihVar.i)) != 0) {
            return a19;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ihVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a18 = it.a(this.j, ihVar.j)) != 0) {
            return a18;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(ihVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (l() && (a17 = it.a(this.k, ihVar.k)) != 0) {
            return a17;
        }
        int compareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(ihVar.m()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (m() && (a16 = it.a(this.a, ihVar.a)) != 0) {
            return a16;
        }
        int compareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(ihVar.n()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (n() && (a15 = it.a(this.b, ihVar.b)) != 0) {
            return a15;
        }
        int compareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(ihVar.o()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (o() && (a14 = it.a(this.l, ihVar.l)) != 0) {
            return a14;
        }
        int compareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(ihVar.p()));
        if (compareTo16 != 0) {
            return compareTo16;
        }
        if (p() && (a13 = it.a(this.m, ihVar.m)) != 0) {
            return a13;
        }
        int compareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(ihVar.q()));
        if (compareTo17 != 0) {
            return compareTo17;
        }
        if (q() && (a12 = it.a(this.n, ihVar.n)) != 0) {
            return a12;
        }
        int compareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(ihVar.r()));
        if (compareTo18 != 0) {
            return compareTo18;
        }
        if (r() && (a11 = it.a(this.o, ihVar.o)) != 0) {
            return a11;
        }
        int compareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(ihVar.s()));
        if (compareTo19 != 0) {
            return compareTo19;
        }
        if (s() && (a10 = it.a(this.c, ihVar.c)) != 0) {
            return a10;
        }
        int compareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(ihVar.t()));
        if (compareTo20 != 0) {
            return compareTo20;
        }
        if (t() && (a9 = it.a(this.f133a, ihVar.f133a)) != 0) {
            return a9;
        }
        int compareTo21 = Boolean.valueOf(u()).compareTo(Boolean.valueOf(ihVar.u()));
        if (compareTo21 != 0) {
            return compareTo21;
        }
        if (u() && (a8 = it.a(this.f138a, ihVar.f138a)) != 0) {
            return a8;
        }
        int compareTo22 = Boolean.valueOf(v()).compareTo(Boolean.valueOf(ihVar.v()));
        if (compareTo22 != 0) {
            return compareTo22;
        }
        if (v() && (a7 = it.a(this.f132a, ihVar.f132a)) != 0) {
            return a7;
        }
        int compareTo23 = Boolean.valueOf(w()).compareTo(Boolean.valueOf(ihVar.w()));
        if (compareTo23 != 0) {
            return compareTo23;
        }
        if (w() && (a6 = it.a(this.f139b, ihVar.f139b)) != 0) {
            return a6;
        }
        int compareTo24 = Boolean.valueOf(x()).compareTo(Boolean.valueOf(ihVar.x()));
        if (compareTo24 != 0) {
            return compareTo24;
        }
        if (x() && (a5 = it.a(this.p, ihVar.p)) != 0) {
            return a5;
        }
        int compareTo25 = Boolean.valueOf(y()).compareTo(Boolean.valueOf(ihVar.y()));
        if (compareTo25 != 0) {
            return compareTo25;
        }
        if (y() && (a4 = it.a(this.q, ihVar.q)) != 0) {
            return a4;
        }
        int compareTo26 = Boolean.valueOf(z()).compareTo(Boolean.valueOf(ihVar.z()));
        if (compareTo26 != 0) {
            return compareTo26;
        }
        if (z() && (a3 = it.a(this.f137a, ihVar.f137a)) != 0) {
            return a3;
        }
        int compareTo27 = Boolean.valueOf(A()).compareTo(Boolean.valueOf(ihVar.A()));
        if (compareTo27 != 0) {
            return compareTo27;
        }
        if (A() && (a2 = it.a(this.f141b, ihVar.f141b)) != 0) {
            return a2;
        }
        int compareTo28 = Boolean.valueOf(B()).compareTo(Boolean.valueOf(ihVar.B()));
        if (compareTo28 != 0) {
            return compareTo28;
        }
        if (!B() || (a = it.a(this.r, ihVar.r)) == 0) {
            return 0;
        }
        return a;
    }

    public ih a(int i) {
        this.a = i;
        a(true);
        return this;
    }

    public ih a(hv hvVar) {
        this.f133a = hvVar;
        return this;
    }

    public ih a(String str) {
        this.f140b = str;
        return this;
    }

    public String a() {
        return this.f140b;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1044a() {
        if (this.f140b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.f142c == null) {
            throw new je("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.f == null) {
            throw new je("Required field 'token' was not present! Struct: " + toString());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m1044a();
                return;
            }
            short s2 = a.f180a;
            switch (s2) {
                case 1:
                    if (a.a == 11) {
                        this.f135a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f134a = new hw();
                        this.f134a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f140b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 11) {
                        this.f142c = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 5:
                    if (a.a == 11) {
                        this.d = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 6:
                    if (a.a == 11) {
                        this.e = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 7:
                    if (a.a == 11) {
                        this.f = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 8:
                    if (a.a == 11) {
                        this.g = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 9:
                    if (a.a == 11) {
                        this.h = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 11) {
                        this.i = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 11:
                    if (a.a == 11) {
                        this.j = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 12:
                    if (a.a == 11) {
                        this.k = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 13:
                    if (a.a == 8) {
                        this.a = jdVar.m1093a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 14:
                    if (a.a == 8) {
                        this.b = jdVar.m1093a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 15:
                    if (a.a == 11) {
                        this.l = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 16:
                    if (a.a == 11) {
                        this.m = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 17:
                    if (a.a == 11) {
                        this.n = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 18:
                    if (a.a == 11) {
                        this.o = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 19:
                    if (a.a == 8) {
                        this.c = jdVar.m1093a();
                        c(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 20:
                    if (a.a == 8) {
                        this.f133a = hv.a(jdVar.m1093a());
                        continue;
                        jdVar.g();
                    }
                    break;
                case 21:
                    if (a.a == 2) {
                        this.f138a = jdVar.m1104a();
                        d(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 22:
                    if (a.a == 10) {
                        this.f132a = jdVar.m1094a();
                        e(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 23:
                    if (a.a == 10) {
                        this.f139b = jdVar.m1094a();
                        f(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 24:
                    if (a.a == 11) {
                        this.p = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 25:
                    if (a.a == 11) {
                        this.q = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                default:
                    switch (s2) {
                        case 100:
                            if (a.a == 13) {
                                jc a2 = jdVar.m1097a();
                                this.f137a = new HashMap(a2.f182a * 2);
                                for (int i = 0; i < a2.f182a; i++) {
                                    this.f137a.put(jdVar.m1100a(), jdVar.m1100a());
                                }
                                jdVar.h();
                                break;
                            }
                            break;
                        case 101:
                            if (a.a == 2) {
                                this.f141b = jdVar.m1104a();
                                g(true);
                                break;
                            }
                            break;
                        case 102:
                            if (a.a == 11) {
                                this.r = jdVar.m1100a();
                                continue;
                            }
                            break;
                    }
                    jdVar.g();
                    break;
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    public void a(boolean z2) {
        this.f136a.set(0, z2);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1045a() {
        return this.f135a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1046a(ih ihVar) {
        if (ihVar == null) {
            return false;
        }
        boolean a = m1045a();
        boolean a2 = ihVar.m1045a();
        if ((a || a2) && (!a || !a2 || !this.f135a.equals(ihVar.f135a))) {
            return false;
        }
        boolean b = m1047b();
        boolean b2 = ihVar.m1047b();
        if ((b || b2) && (!b || !b2 || !this.f134a.m1000a(ihVar.f134a))) {
            return false;
        }
        boolean c = m1048c();
        boolean c2 = ihVar.m1048c();
        if ((c || c2) && (!c || !c2 || !this.f140b.equals(ihVar.f140b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = ihVar.d();
        if ((d || d2) && (!d || !d2 || !this.f142c.equals(ihVar.f142c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = ihVar.e();
        if ((e || e2) && (!e || !e2 || !this.d.equals(ihVar.d))) {
            return false;
        }
        boolean f = f();
        boolean f2 = ihVar.f();
        if ((f || f2) && (!f || !f2 || !this.e.equals(ihVar.e))) {
            return false;
        }
        boolean g = g();
        boolean g2 = ihVar.g();
        if ((g || g2) && (!g || !g2 || !this.f.equals(ihVar.f))) {
            return false;
        }
        boolean h = h();
        boolean h2 = ihVar.h();
        if ((h || h2) && (!h || !h2 || !this.g.equals(ihVar.g))) {
            return false;
        }
        boolean i = i();
        boolean i2 = ihVar.i();
        if ((i || i2) && (!i || !i2 || !this.h.equals(ihVar.h))) {
            return false;
        }
        boolean j = j();
        boolean j2 = ihVar.j();
        if ((j || j2) && (!j || !j2 || !this.i.equals(ihVar.i))) {
            return false;
        }
        boolean k = k();
        boolean k2 = ihVar.k();
        if ((k || k2) && (!k || !k2 || !this.j.equals(ihVar.j))) {
            return false;
        }
        boolean l = l();
        boolean l2 = ihVar.l();
        if ((l || l2) && (!l || !l2 || !this.k.equals(ihVar.k))) {
            return false;
        }
        boolean m = m();
        boolean m2 = ihVar.m();
        if ((m || m2) && (!m || !m2 || this.a != ihVar.a)) {
            return false;
        }
        boolean n = n();
        boolean n2 = ihVar.n();
        if ((n || n2) && (!n || !n2 || this.b != ihVar.b)) {
            return false;
        }
        boolean o = o();
        boolean o2 = ihVar.o();
        if ((o || o2) && (!o || !o2 || !this.l.equals(ihVar.l))) {
            return false;
        }
        boolean p = p();
        boolean p2 = ihVar.p();
        if ((p || p2) && (!p || !p2 || !this.m.equals(ihVar.m))) {
            return false;
        }
        boolean q = q();
        boolean q2 = ihVar.q();
        if ((q || q2) && (!q || !q2 || !this.n.equals(ihVar.n))) {
            return false;
        }
        boolean r = r();
        boolean r2 = ihVar.r();
        if ((r || r2) && (!r || !r2 || !this.o.equals(ihVar.o))) {
            return false;
        }
        boolean s2 = s();
        boolean s3 = ihVar.s();
        if ((s2 || s3) && (!s2 || !s3 || this.c != ihVar.c)) {
            return false;
        }
        boolean t2 = t();
        boolean t3 = ihVar.t();
        if ((t2 || t3) && (!t2 || !t3 || !this.f133a.equals(ihVar.f133a))) {
            return false;
        }
        boolean u2 = u();
        boolean u3 = ihVar.u();
        if ((u2 || u3) && (!u2 || !u3 || this.f138a != ihVar.f138a)) {
            return false;
        }
        boolean v2 = v();
        boolean v3 = ihVar.v();
        if ((v2 || v3) && (!v2 || !v3 || this.f132a != ihVar.f132a)) {
            return false;
        }
        boolean w2 = w();
        boolean w3 = ihVar.w();
        if ((w2 || w3) && (!w2 || !w3 || this.f139b != ihVar.f139b)) {
            return false;
        }
        boolean x2 = x();
        boolean x3 = ihVar.x();
        if ((x2 || x3) && (!x2 || !x3 || !this.p.equals(ihVar.p))) {
            return false;
        }
        boolean y2 = y();
        boolean y3 = ihVar.y();
        if ((y2 || y3) && (!y2 || !y3 || !this.q.equals(ihVar.q))) {
            return false;
        }
        boolean z2 = z();
        boolean z3 = ihVar.z();
        if ((z2 || z3) && (!z2 || !z3 || !this.f137a.equals(ihVar.f137a))) {
            return false;
        }
        boolean A2 = A();
        boolean A3 = ihVar.A();
        if ((A2 || A3) && (!A2 || !A3 || this.f141b != ihVar.f141b)) {
            return false;
        }
        boolean B2 = B();
        boolean B3 = ihVar.B();
        if (B2 || B3) {
            return B2 && B3 && this.r.equals(ihVar.r);
        }
        return true;
    }

    public ih b(int i) {
        this.b = i;
        b(true);
        return this;
    }

    public ih b(String str) {
        this.f142c = str;
        return this;
    }

    public String b() {
        return this.f142c;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1044a();
        jdVar.a(s);
        if (this.f135a != null && m1045a()) {
            jdVar.a(t);
            jdVar.a(this.f135a);
            jdVar.b();
        }
        if (this.f134a != null && m1047b()) {
            jdVar.a(u);
            this.f134a.b(jdVar);
            jdVar.b();
        }
        if (this.f140b != null) {
            jdVar.a(v);
            jdVar.a(this.f140b);
            jdVar.b();
        }
        if (this.f142c != null) {
            jdVar.a(w);
            jdVar.a(this.f142c);
            jdVar.b();
        }
        if (this.d != null && e()) {
            jdVar.a(x);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && f()) {
            jdVar.a(y);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null) {
            jdVar.a(z);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && h()) {
            jdVar.a(A);
            jdVar.a(this.g);
            jdVar.b();
        }
        if (this.h != null && i()) {
            jdVar.a(B);
            jdVar.a(this.h);
            jdVar.b();
        }
        if (this.i != null && j()) {
            jdVar.a(C);
            jdVar.a(this.i);
            jdVar.b();
        }
        if (this.j != null && k()) {
            jdVar.a(D);
            jdVar.a(this.j);
            jdVar.b();
        }
        if (this.k != null && l()) {
            jdVar.a(E);
            jdVar.a(this.k);
            jdVar.b();
        }
        if (m()) {
            jdVar.a(F);
            jdVar.mo1091a(this.a);
            jdVar.b();
        }
        if (n()) {
            jdVar.a(G);
            jdVar.mo1091a(this.b);
            jdVar.b();
        }
        if (this.l != null && o()) {
            jdVar.a(H);
            jdVar.a(this.l);
            jdVar.b();
        }
        if (this.m != null && p()) {
            jdVar.a(I);
            jdVar.a(this.m);
            jdVar.b();
        }
        if (this.n != null && q()) {
            jdVar.a(J);
            jdVar.a(this.n);
            jdVar.b();
        }
        if (this.o != null && r()) {
            jdVar.a(K);
            jdVar.a(this.o);
            jdVar.b();
        }
        if (s()) {
            jdVar.a(L);
            jdVar.mo1091a(this.c);
            jdVar.b();
        }
        if (this.f133a != null && t()) {
            jdVar.a(M);
            jdVar.mo1091a(this.f133a.a());
            jdVar.b();
        }
        if (u()) {
            jdVar.a(N);
            jdVar.a(this.f138a);
            jdVar.b();
        }
        if (v()) {
            jdVar.a(O);
            jdVar.a(this.f132a);
            jdVar.b();
        }
        if (w()) {
            jdVar.a(P);
            jdVar.a(this.f139b);
            jdVar.b();
        }
        if (this.p != null && x()) {
            jdVar.a(Q);
            jdVar.a(this.p);
            jdVar.b();
        }
        if (this.q != null && y()) {
            jdVar.a(R);
            jdVar.a(this.q);
            jdVar.b();
        }
        if (this.f137a != null && z()) {
            jdVar.a(S);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f137a.size()));
            for (Map.Entry<String, String> entry : this.f137a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (A()) {
            jdVar.a(T);
            jdVar.a(this.f141b);
            jdVar.b();
        }
        if (this.r != null && B()) {
            jdVar.a(U);
            jdVar.a(this.r);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z2) {
        this.f136a.set(1, z2);
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m1047b() {
        return this.f134a != null;
    }

    public ih c(int i) {
        this.c = i;
        c(true);
        return this;
    }

    public ih c(String str) {
        this.d = str;
        return this;
    }

    public String c() {
        return this.f;
    }

    public void c(boolean z2) {
        this.f136a.set(2, z2);
    }

    /* renamed from: c  reason: collision with other method in class */
    public boolean m1048c() {
        return this.f140b != null;
    }

    public ih d(String str) {
        this.e = str;
        return this;
    }

    public void d(boolean z2) {
        this.f136a.set(3, z2);
    }

    public boolean d() {
        return this.f142c != null;
    }

    public ih e(String str) {
        this.f = str;
        return this;
    }

    public void e(boolean z2) {
        this.f136a.set(4, z2);
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ih)) {
            return m1046a((ih) obj);
        }
        return false;
    }

    public ih f(String str) {
        this.g = str;
        return this;
    }

    public void f(boolean z2) {
        this.f136a.set(5, z2);
    }

    public boolean f() {
        return this.e != null;
    }

    public ih g(String str) {
        this.k = str;
        return this;
    }

    public void g(boolean z2) {
        this.f136a.set(6, z2);
    }

    public boolean g() {
        return this.f != null;
    }

    public ih h(String str) {
        this.l = str;
        return this;
    }

    public boolean h() {
        return this.g != null;
    }

    public int hashCode() {
        return 0;
    }

    public ih i(String str) {
        this.m = str;
        return this;
    }

    public boolean i() {
        return this.h != null;
    }

    public ih j(String str) {
        this.n = str;
        return this;
    }

    public boolean j() {
        return this.i != null;
    }

    public ih k(String str) {
        this.o = str;
        return this;
    }

    public boolean k() {
        return this.j != null;
    }

    public boolean l() {
        return this.k != null;
    }

    public boolean m() {
        return this.f136a.get(0);
    }

    public boolean n() {
        return this.f136a.get(1);
    }

    public boolean o() {
        return this.l != null;
    }

    public boolean p() {
        return this.m != null;
    }

    public boolean q() {
        return this.n != null;
    }

    public boolean r() {
        return this.o != null;
    }

    public boolean s() {
        return this.f136a.get(2);
    }

    public boolean t() {
        return this.f133a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistration(");
        if (m1045a()) {
            sb.append("debug:");
            String str = this.f135a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z2 = false;
        } else {
            z2 = true;
        }
        if (m1047b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f134a;
            if (hwVar == null) {
                sb.append("null");
            } else {
                sb.append(hwVar);
            }
            z2 = false;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("id:");
        String str2 = this.f140b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f142c;
        if (str3 == null) {
            str3 = "null";
        }
        sb.append(str3);
        if (e()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        sb.append(", ");
        sb.append("token:");
        String str6 = this.f;
        if (str6 == null) {
            str6 = "null";
        }
        sb.append(str6);
        if (h()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        if (i()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        if (j()) {
            sb.append(", ");
            sb.append("sdkVersion:");
            String str9 = this.i;
            if (str9 == null) {
                str9 = "null";
            }
            sb.append(str9);
        }
        if (k()) {
            sb.append(", ");
            sb.append("regId:");
            String str10 = this.j;
            if (str10 == null) {
                str10 = "null";
            }
            sb.append(str10);
        }
        if (l()) {
            sb.append(", ");
            sb.append("pushSdkVersionName:");
            String str11 = this.k;
            if (str11 == null) {
                str11 = "null";
            }
            sb.append(str11);
        }
        if (m()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.a);
        }
        if (n()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.b);
        }
        if (o()) {
            sb.append(", ");
            sb.append("androidId:");
            String str12 = this.l;
            if (str12 == null) {
                str12 = "null";
            }
            sb.append(str12);
        }
        if (p()) {
            sb.append(", ");
            sb.append("imei:");
            String str13 = this.m;
            if (str13 == null) {
                str13 = "null";
            }
            sb.append(str13);
        }
        if (q()) {
            sb.append(", ");
            sb.append("serial:");
            String str14 = this.n;
            if (str14 == null) {
                str14 = "null";
            }
            sb.append(str14);
        }
        if (r()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str15 = this.o;
            if (str15 == null) {
                str15 = "null";
            }
            sb.append(str15);
        }
        if (s()) {
            sb.append(", ");
            sb.append("spaceId:");
            sb.append(this.c);
        }
        if (t()) {
            sb.append(", ");
            sb.append("reason:");
            hv hvVar = this.f133a;
            if (hvVar == null) {
                sb.append("null");
            } else {
                sb.append(hvVar);
            }
        }
        if (u()) {
            sb.append(", ");
            sb.append("validateToken:");
            sb.append(this.f138a);
        }
        if (v()) {
            sb.append(", ");
            sb.append("miid:");
            sb.append(this.f132a);
        }
        if (w()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.f139b);
        }
        if (x()) {
            sb.append(", ");
            sb.append("subImei:");
            String str16 = this.p;
            if (str16 == null) {
                str16 = "null";
            }
            sb.append(str16);
        }
        if (y()) {
            sb.append(", ");
            sb.append("subImeiMd5:");
            String str17 = this.q;
            if (str17 == null) {
                str17 = "null";
            }
            sb.append(str17);
        }
        if (z()) {
            sb.append(", ");
            sb.append("connectionAttrs:");
            Map<String, String> map = this.f137a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (A()) {
            sb.append(", ");
            sb.append("cleanOldRegInfo:");
            sb.append(this.f141b);
        }
        if (B()) {
            sb.append(", ");
            sb.append("oldRegId:");
            String str18 = this.r;
            if (str18 == null) {
                str18 = "null";
            }
            sb.append(str18);
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean u() {
        return this.f136a.get(3);
    }

    public boolean v() {
        return this.f136a.get(4);
    }

    public boolean w() {
        return this.f136a.get(5);
    }

    public boolean x() {
        return this.p != null;
    }

    public boolean y() {
        return this.q != null;
    }

    public boolean z() {
        return this.f137a != null;
    }
}
