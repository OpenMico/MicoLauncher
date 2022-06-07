package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class ii implements is<ii, Object>, Serializable, Cloneable {
    public int a;

    /* renamed from: a  reason: collision with other field name */
    public long f143a;

    /* renamed from: a  reason: collision with other field name */
    public hw f144a;

    /* renamed from: a  reason: collision with other field name */
    public String f145a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f146a = new BitSet(5);
    public int b;

    /* renamed from: b  reason: collision with other field name */
    public long f147b;

    /* renamed from: b  reason: collision with other field name */
    public String f148b;
    public long c;

    /* renamed from: c  reason: collision with other field name */
    public String f149c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    private static final ji m = new ji("XmPushActionRegistrationResult");
    private static final ja n = new ja("", (byte) 11, 1);
    private static final ja o = new ja("", (byte) 12, 2);
    private static final ja p = new ja("", (byte) 11, 3);
    private static final ja q = new ja("", (byte) 11, 4);
    private static final ja r = new ja("", (byte) 10, 6);
    private static final ja s = new ja("", (byte) 11, 7);
    private static final ja t = new ja("", (byte) 11, 8);
    private static final ja u = new ja("", (byte) 11, 9);
    private static final ja v = new ja("", (byte) 11, 10);
    private static final ja w = new ja("", (byte) 10, 11);
    private static final ja x = new ja("", (byte) 11, 12);
    private static final ja y = new ja("", (byte) 11, 13);
    private static final ja z = new ja("", (byte) 10, 14);
    private static final ja A = new ja("", (byte) 11, 15);
    private static final ja B = new ja("", (byte) 8, 16);
    private static final ja C = new ja("", (byte) 11, 17);
    private static final ja D = new ja("", (byte) 8, 18);
    private static final ja E = new ja("", (byte) 11, 19);

    /* renamed from: a */
    public int compareTo(ii iiVar) {
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
        if (!getClass().equals(iiVar.getClass())) {
            return getClass().getName().compareTo(iiVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1051a()).compareTo(Boolean.valueOf(iiVar.m1051a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1051a() && (a18 = it.a(this.f145a, iiVar.f145a)) != 0) {
            return a18;
        }
        int compareTo2 = Boolean.valueOf(m1053b()).compareTo(Boolean.valueOf(iiVar.m1053b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m1053b() && (a17 = it.a(this.f144a, iiVar.f144a)) != 0) {
            return a17;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(iiVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a16 = it.a(this.f148b, iiVar.f148b)) != 0) {
            return a16;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(iiVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a15 = it.a(this.f149c, iiVar.f149c)) != 0) {
            return a15;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iiVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a14 = it.a(this.f143a, iiVar.f143a)) != 0) {
            return a14;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(iiVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a13 = it.a(this.d, iiVar.d)) != 0) {
            return a13;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(iiVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a12 = it.a(this.e, iiVar.e)) != 0) {
            return a12;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(iiVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a11 = it.a(this.f, iiVar.f)) != 0) {
            return a11;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(iiVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a10 = it.a(this.g, iiVar.g)) != 0) {
            return a10;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(iiVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a9 = it.a(this.f147b, iiVar.f147b)) != 0) {
            return a9;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(iiVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a8 = it.a(this.h, iiVar.h)) != 0) {
            return a8;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(iiVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (l() && (a7 = it.a(this.i, iiVar.i)) != 0) {
            return a7;
        }
        int compareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(iiVar.m()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (m() && (a6 = it.a(this.c, iiVar.c)) != 0) {
            return a6;
        }
        int compareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(iiVar.n()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (n() && (a5 = it.a(this.j, iiVar.j)) != 0) {
            return a5;
        }
        int compareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(iiVar.o()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (o() && (a4 = it.a(this.a, iiVar.a)) != 0) {
            return a4;
        }
        int compareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(iiVar.p()));
        if (compareTo16 != 0) {
            return compareTo16;
        }
        if (p() && (a3 = it.a(this.k, iiVar.k)) != 0) {
            return a3;
        }
        int compareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(iiVar.q()));
        if (compareTo17 != 0) {
            return compareTo17;
        }
        if (q() && (a2 = it.a(this.b, iiVar.b)) != 0) {
            return a2;
        }
        int compareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(iiVar.r()));
        if (compareTo18 != 0) {
            return compareTo18;
        }
        if (!r() || (a = it.a(this.l, iiVar.l)) == 0) {
            return 0;
        }
        return a;
    }

    public long a() {
        return this.f143a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public String m1049a() {
        return this.f148b;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1050a() {
        if (this.f148b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.f149c == null) {
            throw new je("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (e()) {
                    m1050a();
                    return;
                }
                throw new je("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f145a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f144a = new hw();
                        this.f144a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f148b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 11) {
                        this.f149c = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 6:
                    if (a.a == 10) {
                        this.f143a = jdVar.m1094a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 7:
                    if (a.a == 11) {
                        this.d = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 8:
                    if (a.a == 11) {
                        this.e = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 9:
                    if (a.a == 11) {
                        this.f = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 11) {
                        this.g = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 11:
                    if (a.a == 10) {
                        this.f147b = jdVar.m1094a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 12:
                    if (a.a == 11) {
                        this.h = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 13:
                    if (a.a == 11) {
                        this.i = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 14:
                    if (a.a == 10) {
                        this.c = jdVar.m1094a();
                        c(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 15:
                    if (a.a == 11) {
                        this.j = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 16:
                    if (a.a == 8) {
                        this.a = jdVar.m1093a();
                        d(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 17:
                    if (a.a == 11) {
                        this.k = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 18:
                    if (a.a == 8) {
                        this.b = jdVar.m1093a();
                        e(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 19:
                    if (a.a == 11) {
                        this.l = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    public void a(boolean z2) {
        this.f146a.set(0, z2);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1051a() {
        return this.f145a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1052a(ii iiVar) {
        if (iiVar == null) {
            return false;
        }
        boolean a = m1051a();
        boolean a2 = iiVar.m1051a();
        if ((a || a2) && (!a || !a2 || !this.f145a.equals(iiVar.f145a))) {
            return false;
        }
        boolean b = m1053b();
        boolean b2 = iiVar.m1053b();
        if ((b || b2) && (!b || !b2 || !this.f144a.m1000a(iiVar.f144a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = iiVar.c();
        if ((c || c2) && (!c || !c2 || !this.f148b.equals(iiVar.f148b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = iiVar.d();
        if (((d || d2) && (!d || !d2 || !this.f149c.equals(iiVar.f149c))) || this.f143a != iiVar.f143a) {
            return false;
        }
        boolean f = f();
        boolean f2 = iiVar.f();
        if ((f || f2) && (!f || !f2 || !this.d.equals(iiVar.d))) {
            return false;
        }
        boolean g = g();
        boolean g2 = iiVar.g();
        if ((g || g2) && (!g || !g2 || !this.e.equals(iiVar.e))) {
            return false;
        }
        boolean h = h();
        boolean h2 = iiVar.h();
        if ((h || h2) && (!h || !h2 || !this.f.equals(iiVar.f))) {
            return false;
        }
        boolean i = i();
        boolean i2 = iiVar.i();
        if ((i || i2) && (!i || !i2 || !this.g.equals(iiVar.g))) {
            return false;
        }
        boolean j = j();
        boolean j2 = iiVar.j();
        if ((j || j2) && (!j || !j2 || this.f147b != iiVar.f147b)) {
            return false;
        }
        boolean k = k();
        boolean k2 = iiVar.k();
        if ((k || k2) && (!k || !k2 || !this.h.equals(iiVar.h))) {
            return false;
        }
        boolean l = l();
        boolean l2 = iiVar.l();
        if ((l || l2) && (!l || !l2 || !this.i.equals(iiVar.i))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = iiVar.m();
        if ((m2 || m3) && (!m2 || !m3 || this.c != iiVar.c)) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = iiVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.j.equals(iiVar.j))) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = iiVar.o();
        if ((o2 || o3) && (!o2 || !o3 || this.a != iiVar.a)) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = iiVar.p();
        if ((p2 || p3) && (!p2 || !p3 || !this.k.equals(iiVar.k))) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = iiVar.q();
        if ((q2 || q3) && (!q2 || !q3 || this.b != iiVar.b)) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = iiVar.r();
        if (r2 || r3) {
            return r2 && r3 && this.l.equals(iiVar.l);
        }
        return true;
    }

    public String b() {
        return this.g;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1050a();
        jdVar.a(m);
        if (this.f145a != null && m1051a()) {
            jdVar.a(n);
            jdVar.a(this.f145a);
            jdVar.b();
        }
        if (this.f144a != null && m1053b()) {
            jdVar.a(o);
            this.f144a.b(jdVar);
            jdVar.b();
        }
        if (this.f148b != null) {
            jdVar.a(p);
            jdVar.a(this.f148b);
            jdVar.b();
        }
        if (this.f149c != null) {
            jdVar.a(q);
            jdVar.a(this.f149c);
            jdVar.b();
        }
        jdVar.a(r);
        jdVar.a(this.f143a);
        jdVar.b();
        if (this.d != null && f()) {
            jdVar.a(s);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && g()) {
            jdVar.a(t);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && h()) {
            jdVar.a(u);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && i()) {
            jdVar.a(v);
            jdVar.a(this.g);
            jdVar.b();
        }
        if (j()) {
            jdVar.a(w);
            jdVar.a(this.f147b);
            jdVar.b();
        }
        if (this.h != null && k()) {
            jdVar.a(x);
            jdVar.a(this.h);
            jdVar.b();
        }
        if (this.i != null && l()) {
            jdVar.a(y);
            jdVar.a(this.i);
            jdVar.b();
        }
        if (m()) {
            jdVar.a(z);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (this.j != null && n()) {
            jdVar.a(A);
            jdVar.a(this.j);
            jdVar.b();
        }
        if (o()) {
            jdVar.a(B);
            jdVar.mo1091a(this.a);
            jdVar.b();
        }
        if (this.k != null && p()) {
            jdVar.a(C);
            jdVar.a(this.k);
            jdVar.b();
        }
        if (q()) {
            jdVar.a(D);
            jdVar.mo1091a(this.b);
            jdVar.b();
        }
        if (this.l != null && r()) {
            jdVar.a(E);
            jdVar.a(this.l);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z2) {
        this.f146a.set(1, z2);
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m1053b() {
        return this.f144a != null;
    }

    public void c(boolean z2) {
        this.f146a.set(2, z2);
    }

    public boolean c() {
        return this.f148b != null;
    }

    public void d(boolean z2) {
        this.f146a.set(3, z2);
    }

    public boolean d() {
        return this.f149c != null;
    }

    public void e(boolean z2) {
        this.f146a.set(4, z2);
    }

    public boolean e() {
        return this.f146a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ii)) {
            return m1052a((ii) obj);
        }
        return false;
    }

    public boolean f() {
        return this.d != null;
    }

    public boolean g() {
        return this.e != null;
    }

    public boolean h() {
        return this.f != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.g != null;
    }

    public boolean j() {
        return this.f146a.get(1);
    }

    public boolean k() {
        return this.h != null;
    }

    public boolean l() {
        return this.i != null;
    }

    public boolean m() {
        return this.f146a.get(2);
    }

    public boolean n() {
        return this.j != null;
    }

    public boolean o() {
        return this.f146a.get(3);
    }

    public boolean p() {
        return this.k != null;
    }

    public boolean q() {
        return this.f146a.get(4);
    }

    public boolean r() {
        return this.l != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistrationResult(");
        if (m1051a()) {
            sb.append("debug:");
            String str = this.f145a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z2 = false;
        } else {
            z2 = true;
        }
        if (m1053b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f144a;
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
        String str2 = this.f148b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f149c;
        if (str3 == null) {
            str3 = "null";
        }
        sb.append(str3);
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f143a);
        if (f()) {
            sb.append(", ");
            sb.append("reason:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (g()) {
            sb.append(", ");
            sb.append("regId:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("regSecret:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        if (j()) {
            sb.append(", ");
            sb.append("registeredAt:");
            sb.append(this.f147b);
        }
        if (k()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        if (l()) {
            sb.append(", ");
            sb.append("clientId:");
            String str9 = this.i;
            if (str9 == null) {
                str9 = "null";
            }
            sb.append(str9);
        }
        if (m()) {
            sb.append(", ");
            sb.append("costTime:");
            sb.append(this.c);
        }
        if (n()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str10 = this.j;
            if (str10 == null) {
                str10 = "null";
            }
            sb.append(str10);
        }
        if (o()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.a);
        }
        if (p()) {
            sb.append(", ");
            sb.append("hybridPushEndpoint:");
            String str11 = this.k;
            if (str11 == null) {
                str11 = "null";
            }
            sb.append(str11);
        }
        if (q()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.b);
        }
        if (r()) {
            sb.append(", ");
            sb.append("region:");
            String str12 = this.l;
            if (str12 == null) {
                str12 = "null";
            }
            sb.append(str12);
        }
        sb.append(")");
        return sb.toString();
    }
}
