package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class ht implements is<ht, Object>, Serializable, Cloneable {
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hu f73a;

    /* renamed from: a  reason: collision with other field name */
    public hw f74a;

    /* renamed from: a  reason: collision with other field name */
    public String f75a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f76a = new BitSet(4);

    /* renamed from: a  reason: collision with other field name */
    public boolean f77a = false;
    public long b;

    /* renamed from: b  reason: collision with other field name */
    public String f78b;
    public long c;

    /* renamed from: c  reason: collision with other field name */
    public String f79c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    private static final ji m = new ji("PushMessage");
    private static final ja n = new ja("", (byte) 12, 1);
    private static final ja o = new ja("", (byte) 11, 2);
    private static final ja p = new ja("", (byte) 11, 3);
    private static final ja q = new ja("", (byte) 11, 4);
    private static final ja r = new ja("", (byte) 10, 5);
    private static final ja s = new ja("", (byte) 10, 6);
    private static final ja t = new ja("", (byte) 11, 7);
    private static final ja u = new ja("", (byte) 11, 8);
    private static final ja v = new ja("", (byte) 11, 9);
    private static final ja w = new ja("", (byte) 11, 10);
    private static final ja x = new ja("", (byte) 11, 11);
    private static final ja y = new ja("", (byte) 12, 12);
    private static final ja z = new ja("", (byte) 11, 13);
    private static final ja A = new ja("", (byte) 2, 14);
    private static final ja B = new ja("", (byte) 11, 15);
    private static final ja C = new ja("", (byte) 10, 16);
    private static final ja D = new ja("", (byte) 11, 20);
    private static final ja E = new ja("", (byte) 11, 21);

    /* renamed from: a */
    public int compareTo(ht htVar) {
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
        if (!getClass().equals(htVar.getClass())) {
            return getClass().getName().compareTo(htVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m982a()).compareTo(Boolean.valueOf(htVar.m982a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m982a() && (a18 = it.a(this.f74a, htVar.f74a)) != 0) {
            return a18;
        }
        int compareTo2 = Boolean.valueOf(m984b()).compareTo(Boolean.valueOf(htVar.m984b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m984b() && (a17 = it.a(this.f75a, htVar.f75a)) != 0) {
            return a17;
        }
        int compareTo3 = Boolean.valueOf(m985c()).compareTo(Boolean.valueOf(htVar.m985c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m985c() && (a16 = it.a(this.f78b, htVar.f78b)) != 0) {
            return a16;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(htVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a15 = it.a(this.f79c, htVar.f79c)) != 0) {
            return a15;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(htVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a14 = it.a(this.a, htVar.a)) != 0) {
            return a14;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(htVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a13 = it.a(this.b, htVar.b)) != 0) {
            return a13;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(htVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a12 = it.a(this.d, htVar.d)) != 0) {
            return a12;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(htVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a11 = it.a(this.e, htVar.e)) != 0) {
            return a11;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(htVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a10 = it.a(this.f, htVar.f)) != 0) {
            return a10;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(htVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a9 = it.a(this.g, htVar.g)) != 0) {
            return a9;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(htVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a8 = it.a(this.h, htVar.h)) != 0) {
            return a8;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(htVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (l() && (a7 = it.a(this.f73a, htVar.f73a)) != 0) {
            return a7;
        }
        int compareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(htVar.m()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (m() && (a6 = it.a(this.i, htVar.i)) != 0) {
            return a6;
        }
        int compareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(htVar.n()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (n() && (a5 = it.a(this.f77a, htVar.f77a)) != 0) {
            return a5;
        }
        int compareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(htVar.o()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (o() && (a4 = it.a(this.j, htVar.j)) != 0) {
            return a4;
        }
        int compareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(htVar.p()));
        if (compareTo16 != 0) {
            return compareTo16;
        }
        if (p() && (a3 = it.a(this.c, htVar.c)) != 0) {
            return a3;
        }
        int compareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(htVar.q()));
        if (compareTo17 != 0) {
            return compareTo17;
        }
        if (q() && (a2 = it.a(this.k, htVar.k)) != 0) {
            return a2;
        }
        int compareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(htVar.r()));
        if (compareTo18 != 0) {
            return compareTo18;
        }
        if (!r() || (a = it.a(this.l, htVar.l)) == 0) {
            return 0;
        }
        return a;
    }

    public long a() {
        return this.a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public String m980a() {
        return this.f75a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m981a() {
        if (this.f75a == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.f78b == null) {
            throw new je("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.f79c == null) {
            throw new je("Required field 'payload' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m981a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 12) {
                        this.f74a = new hw();
                        this.f74a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 11) {
                        this.f75a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f78b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 11) {
                        this.f79c = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 5:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 6:
                    if (a.a == 10) {
                        this.b = jdVar.m1094a();
                        b(true);
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
                    if (a.a == 11) {
                        this.h = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 12:
                    if (a.a == 12) {
                        this.f73a = new hu();
                        this.f73a.a(jdVar);
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
                    if (a.a == 2) {
                        this.f77a = jdVar.m1104a();
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
                    if (a.a == 10) {
                        this.c = jdVar.m1094a();
                        d(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 20:
                    if (a.a == 11) {
                        this.k = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 21:
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
        this.f76a.set(0, z2);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m982a() {
        return this.f74a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m983a(ht htVar) {
        if (htVar == null) {
            return false;
        }
        boolean a = m982a();
        boolean a2 = htVar.m982a();
        if ((a || a2) && (!a || !a2 || !this.f74a.m1000a(htVar.f74a))) {
            return false;
        }
        boolean b = m984b();
        boolean b2 = htVar.m984b();
        if ((b || b2) && (!b || !b2 || !this.f75a.equals(htVar.f75a))) {
            return false;
        }
        boolean c = m985c();
        boolean c2 = htVar.m985c();
        if ((c || c2) && (!c || !c2 || !this.f78b.equals(htVar.f78b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = htVar.d();
        if ((d || d2) && (!d || !d2 || !this.f79c.equals(htVar.f79c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = htVar.e();
        if ((e || e2) && (!e || !e2 || this.a != htVar.a)) {
            return false;
        }
        boolean f = f();
        boolean f2 = htVar.f();
        if ((f || f2) && (!f || !f2 || this.b != htVar.b)) {
            return false;
        }
        boolean g = g();
        boolean g2 = htVar.g();
        if ((g || g2) && (!g || !g2 || !this.d.equals(htVar.d))) {
            return false;
        }
        boolean h = h();
        boolean h2 = htVar.h();
        if ((h || h2) && (!h || !h2 || !this.e.equals(htVar.e))) {
            return false;
        }
        boolean i = i();
        boolean i2 = htVar.i();
        if ((i || i2) && (!i || !i2 || !this.f.equals(htVar.f))) {
            return false;
        }
        boolean j = j();
        boolean j2 = htVar.j();
        if ((j || j2) && (!j || !j2 || !this.g.equals(htVar.g))) {
            return false;
        }
        boolean k = k();
        boolean k2 = htVar.k();
        if ((k || k2) && (!k || !k2 || !this.h.equals(htVar.h))) {
            return false;
        }
        boolean l = l();
        boolean l2 = htVar.l();
        if ((l || l2) && (!l || !l2 || !this.f73a.m992a(htVar.f73a))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = htVar.m();
        if ((m2 || m3) && (!m2 || !m3 || !this.i.equals(htVar.i))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = htVar.n();
        if ((n2 || n3) && (!n2 || !n3 || this.f77a != htVar.f77a)) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = htVar.o();
        if ((o2 || o3) && (!o2 || !o3 || !this.j.equals(htVar.j))) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = htVar.p();
        if ((p2 || p3) && (!p2 || !p3 || this.c != htVar.c)) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = htVar.q();
        if ((q2 || q3) && (!q2 || !q3 || !this.k.equals(htVar.k))) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = htVar.r();
        if (r2 || r3) {
            return r2 && r3 && this.l.equals(htVar.l);
        }
        return true;
    }

    public String b() {
        return this.f78b;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m981a();
        jdVar.a(m);
        if (this.f74a != null && m982a()) {
            jdVar.a(n);
            this.f74a.b(jdVar);
            jdVar.b();
        }
        if (this.f75a != null) {
            jdVar.a(o);
            jdVar.a(this.f75a);
            jdVar.b();
        }
        if (this.f78b != null) {
            jdVar.a(p);
            jdVar.a(this.f78b);
            jdVar.b();
        }
        if (this.f79c != null) {
            jdVar.a(q);
            jdVar.a(this.f79c);
            jdVar.b();
        }
        if (e()) {
            jdVar.a(r);
            jdVar.a(this.a);
            jdVar.b();
        }
        if (f()) {
            jdVar.a(s);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.d != null && g()) {
            jdVar.a(t);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && h()) {
            jdVar.a(u);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && i()) {
            jdVar.a(v);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && j()) {
            jdVar.a(w);
            jdVar.a(this.g);
            jdVar.b();
        }
        if (this.h != null && k()) {
            jdVar.a(x);
            jdVar.a(this.h);
            jdVar.b();
        }
        if (this.f73a != null && l()) {
            jdVar.a(y);
            this.f73a.b(jdVar);
            jdVar.b();
        }
        if (this.i != null && m()) {
            jdVar.a(z);
            jdVar.a(this.i);
            jdVar.b();
        }
        if (n()) {
            jdVar.a(A);
            jdVar.a(this.f77a);
            jdVar.b();
        }
        if (this.j != null && o()) {
            jdVar.a(B);
            jdVar.a(this.j);
            jdVar.b();
        }
        if (p()) {
            jdVar.a(C);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (this.k != null && q()) {
            jdVar.a(D);
            jdVar.a(this.k);
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
        this.f76a.set(1, z2);
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m984b() {
        return this.f75a != null;
    }

    public String c() {
        return this.f79c;
    }

    public void c(boolean z2) {
        this.f76a.set(2, z2);
    }

    /* renamed from: c  reason: collision with other method in class */
    public boolean m985c() {
        return this.f78b != null;
    }

    public void d(boolean z2) {
        this.f76a.set(3, z2);
    }

    public boolean d() {
        return this.f79c != null;
    }

    public boolean e() {
        return this.f76a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ht)) {
            return m983a((ht) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f76a.get(1);
    }

    public boolean g() {
        return this.d != null;
    }

    public boolean h() {
        return this.e != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f != null;
    }

    public boolean j() {
        return this.g != null;
    }

    public boolean k() {
        return this.h != null;
    }

    public boolean l() {
        return this.f73a != null;
    }

    public boolean m() {
        return this.i != null;
    }

    public boolean n() {
        return this.f76a.get(2);
    }

    public boolean o() {
        return this.j != null;
    }

    public boolean p() {
        return this.f76a.get(3);
    }

    public boolean q() {
        return this.k != null;
    }

    public boolean r() {
        return this.l != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("PushMessage(");
        if (m982a()) {
            sb.append("to:");
            hw hwVar = this.f74a;
            if (hwVar == null) {
                sb.append("null");
            } else {
                sb.append(hwVar);
            }
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f75a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.f78b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", ");
        sb.append("payload:");
        String str3 = this.f79c;
        if (str3 == null) {
            str3 = "null";
        }
        sb.append(str3);
        if (e()) {
            sb.append(", ");
            sb.append("createAt:");
            sb.append(this.a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("ttl:");
            sb.append(this.b);
        }
        if (g()) {
            sb.append(", ");
            sb.append("collapseKey:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (i()) {
            sb.append(", ");
            sb.append("regId:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        if (k()) {
            sb.append(", ");
            sb.append("topic:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        if (l()) {
            sb.append(", ");
            sb.append("metaInfo:");
            hu huVar = this.f73a;
            if (huVar == null) {
                sb.append("null");
            } else {
                sb.append(huVar);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.i;
            if (str9 == null) {
                str9 = "null";
            }
            sb.append(str9);
        }
        if (n()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.f77a);
        }
        if (o()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str10 = this.j;
            if (str10 == null) {
                str10 = "null";
            }
            sb.append(str10);
        }
        if (p()) {
            sb.append(", ");
            sb.append("miid:");
            sb.append(this.c);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            String str11 = this.k;
            if (str11 == null) {
                str11 = "null";
            }
            sb.append(str11);
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
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
