package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class hx implements is<hx, Object>, Serializable, Cloneable {
    public int a;

    /* renamed from: a  reason: collision with other field name */
    public long f93a;

    /* renamed from: a  reason: collision with other field name */
    public hw f94a;

    /* renamed from: a  reason: collision with other field name */
    public ik f95a;

    /* renamed from: a  reason: collision with other field name */
    public String f96a;

    /* renamed from: a  reason: collision with other field name */
    public Map<String, String> f98a;

    /* renamed from: a  reason: collision with other field name */
    public short f99a;
    public String b;

    /* renamed from: b  reason: collision with other field name */
    public short f101b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    private static final ji m = new ji("XmPushActionAckMessage");
    private static final ja n = new ja("", (byte) 11, 1);
    private static final ja o = new ja("", (byte) 12, 2);
    private static final ja p = new ja("", (byte) 11, 3);
    private static final ja q = new ja("", (byte) 11, 4);
    private static final ja r = new ja("", (byte) 10, 5);
    private static final ja s = new ja("", (byte) 11, 6);
    private static final ja t = new ja("", (byte) 11, 7);
    private static final ja u = new ja("", (byte) 12, 8);
    private static final ja v = new ja("", (byte) 11, 9);
    private static final ja w = new ja("", (byte) 11, 10);
    private static final ja x = new ja("", (byte) 2, 11);
    private static final ja y = new ja("", (byte) 11, 12);
    private static final ja z = new ja("", (byte) 11, 13);
    private static final ja A = new ja("", (byte) 11, 14);
    private static final ja B = new ja("", (byte) 6, 15);
    private static final ja C = new ja("", (byte) 6, 16);
    private static final ja D = new ja("", (byte) 11, 20);
    private static final ja E = new ja("", (byte) 11, 21);
    private static final ja F = new ja("", (byte) 8, 22);
    private static final ja G = new ja("", (byte) 13, 23);

    /* renamed from: a  reason: collision with other field name */
    private BitSet f97a = new BitSet(5);

    /* renamed from: a  reason: collision with other field name */
    public boolean f100a = false;

    /* renamed from: a */
    public int compareTo(hx hxVar) {
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
        if (!getClass().equals(hxVar.getClass())) {
            return getClass().getName().compareTo(hxVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1001a()).compareTo(Boolean.valueOf(hxVar.m1001a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1001a() && (a20 = it.a(this.f96a, hxVar.f96a)) != 0) {
            return a20;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hxVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a19 = it.a(this.f94a, hxVar.f94a)) != 0) {
            return a19;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(hxVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a18 = it.a(this.b, hxVar.b)) != 0) {
            return a18;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(hxVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a17 = it.a(this.c, hxVar.c)) != 0) {
            return a17;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(hxVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a16 = it.a(this.f93a, hxVar.f93a)) != 0) {
            return a16;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(hxVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a15 = it.a(this.d, hxVar.d)) != 0) {
            return a15;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(hxVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a14 = it.a(this.e, hxVar.e)) != 0) {
            return a14;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(hxVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a13 = it.a(this.f95a, hxVar.f95a)) != 0) {
            return a13;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(hxVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a12 = it.a(this.f, hxVar.f)) != 0) {
            return a12;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(hxVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a11 = it.a(this.g, hxVar.g)) != 0) {
            return a11;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(hxVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a10 = it.a(this.f100a, hxVar.f100a)) != 0) {
            return a10;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(hxVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (l() && (a9 = it.a(this.h, hxVar.h)) != 0) {
            return a9;
        }
        int compareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(hxVar.m()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (m() && (a8 = it.a(this.i, hxVar.i)) != 0) {
            return a8;
        }
        int compareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(hxVar.n()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (n() && (a7 = it.a(this.j, hxVar.j)) != 0) {
            return a7;
        }
        int compareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(hxVar.o()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (o() && (a6 = it.a(this.f99a, hxVar.f99a)) != 0) {
            return a6;
        }
        int compareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(hxVar.p()));
        if (compareTo16 != 0) {
            return compareTo16;
        }
        if (p() && (a5 = it.a(this.f101b, hxVar.f101b)) != 0) {
            return a5;
        }
        int compareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(hxVar.q()));
        if (compareTo17 != 0) {
            return compareTo17;
        }
        if (q() && (a4 = it.a(this.k, hxVar.k)) != 0) {
            return a4;
        }
        int compareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(hxVar.r()));
        if (compareTo18 != 0) {
            return compareTo18;
        }
        if (r() && (a3 = it.a(this.l, hxVar.l)) != 0) {
            return a3;
        }
        int compareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(hxVar.s()));
        if (compareTo19 != 0) {
            return compareTo19;
        }
        if (s() && (a2 = it.a(this.a, hxVar.a)) != 0) {
            return a2;
        }
        int compareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(hxVar.t()));
        if (compareTo20 != 0) {
            return compareTo20;
        }
        if (!t() || (a = it.a(this.f98a, hxVar.f98a)) == 0) {
            return 0;
        }
        return a;
    }

    public hx a(long j) {
        this.f93a = j;
        a(true);
        return this;
    }

    public hx a(String str) {
        this.b = str;
        return this;
    }

    public hx a(short s2) {
        this.f99a = s2;
        c(true);
        return this;
    }

    public void a() {
        if (this.b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.c == null) {
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
                    a();
                    return;
                }
                throw new je("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f96a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f94a = new hw();
                        this.f94a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 11) {
                        this.c = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 5:
                    if (a.a == 10) {
                        this.f93a = jdVar.m1094a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 6:
                    if (a.a == 11) {
                        this.d = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 7:
                    if (a.a == 11) {
                        this.e = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 8:
                    if (a.a == 12) {
                        this.f95a = new ik();
                        this.f95a.a(jdVar);
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
                    if (a.a == 2) {
                        this.f100a = jdVar.m1104a();
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
                    if (a.a == 11) {
                        this.j = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 15:
                    if (a.a == 6) {
                        this.f99a = jdVar.m1102a();
                        c(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 16:
                    if (a.a == 6) {
                        this.f101b = jdVar.m1102a();
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
                case 22:
                    if (a.a == 8) {
                        this.a = jdVar.m1093a();
                        e(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 23:
                    if (a.a == 13) {
                        jc a2 = jdVar.m1097a();
                        this.f98a = new HashMap(a2.f182a * 2);
                        for (int i = 0; i < a2.f182a; i++) {
                            this.f98a.put(jdVar.m1100a(), jdVar.m1100a());
                        }
                        jdVar.h();
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
        this.f97a.set(0, z2);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1001a() {
        return this.f96a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1002a(hx hxVar) {
        if (hxVar == null) {
            return false;
        }
        boolean a = m1001a();
        boolean a2 = hxVar.m1001a();
        if ((a || a2) && (!a || !a2 || !this.f96a.equals(hxVar.f96a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = hxVar.b();
        if ((b || b2) && (!b || !b2 || !this.f94a.m1000a(hxVar.f94a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = hxVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(hxVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = hxVar.d();
        if (((d || d2) && (!d || !d2 || !this.c.equals(hxVar.c))) || this.f93a != hxVar.f93a) {
            return false;
        }
        boolean f = f();
        boolean f2 = hxVar.f();
        if ((f || f2) && (!f || !f2 || !this.d.equals(hxVar.d))) {
            return false;
        }
        boolean g = g();
        boolean g2 = hxVar.g();
        if ((g || g2) && (!g || !g2 || !this.e.equals(hxVar.e))) {
            return false;
        }
        boolean h = h();
        boolean h2 = hxVar.h();
        if ((h || h2) && (!h || !h2 || !this.f95a.m1059a(hxVar.f95a))) {
            return false;
        }
        boolean i = i();
        boolean i2 = hxVar.i();
        if ((i || i2) && (!i || !i2 || !this.f.equals(hxVar.f))) {
            return false;
        }
        boolean j = j();
        boolean j2 = hxVar.j();
        if ((j || j2) && (!j || !j2 || !this.g.equals(hxVar.g))) {
            return false;
        }
        boolean k = k();
        boolean k2 = hxVar.k();
        if ((k || k2) && (!k || !k2 || this.f100a != hxVar.f100a)) {
            return false;
        }
        boolean l = l();
        boolean l2 = hxVar.l();
        if ((l || l2) && (!l || !l2 || !this.h.equals(hxVar.h))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = hxVar.m();
        if ((m2 || m3) && (!m2 || !m3 || !this.i.equals(hxVar.i))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = hxVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.j.equals(hxVar.j))) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = hxVar.o();
        if ((o2 || o3) && (!o2 || !o3 || this.f99a != hxVar.f99a)) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = hxVar.p();
        if ((p2 || p3) && (!p2 || !p3 || this.f101b != hxVar.f101b)) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = hxVar.q();
        if ((q2 || q3) && (!q2 || !q3 || !this.k.equals(hxVar.k))) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = hxVar.r();
        if ((r2 || r3) && (!r2 || !r3 || !this.l.equals(hxVar.l))) {
            return false;
        }
        boolean s2 = s();
        boolean s3 = hxVar.s();
        if ((s2 || s3) && (!s2 || !s3 || this.a != hxVar.a)) {
            return false;
        }
        boolean t2 = t();
        boolean t3 = hxVar.t();
        if (t2 || t3) {
            return t2 && t3 && this.f98a.equals(hxVar.f98a);
        }
        return true;
    }

    public hx b(String str) {
        this.c = str;
        return this;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(m);
        if (this.f96a != null && m1001a()) {
            jdVar.a(n);
            jdVar.a(this.f96a);
            jdVar.b();
        }
        if (this.f94a != null && b()) {
            jdVar.a(o);
            this.f94a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(p);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null) {
            jdVar.a(q);
            jdVar.a(this.c);
            jdVar.b();
        }
        jdVar.a(r);
        jdVar.a(this.f93a);
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
        if (this.f95a != null && h()) {
            jdVar.a(u);
            this.f95a.b(jdVar);
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
        if (k()) {
            jdVar.a(x);
            jdVar.a(this.f100a);
            jdVar.b();
        }
        if (this.h != null && l()) {
            jdVar.a(y);
            jdVar.a(this.h);
            jdVar.b();
        }
        if (this.i != null && m()) {
            jdVar.a(z);
            jdVar.a(this.i);
            jdVar.b();
        }
        if (this.j != null && n()) {
            jdVar.a(A);
            jdVar.a(this.j);
            jdVar.b();
        }
        if (o()) {
            jdVar.a(B);
            jdVar.a(this.f99a);
            jdVar.b();
        }
        if (p()) {
            jdVar.a(C);
            jdVar.a(this.f101b);
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
        if (s()) {
            jdVar.a(F);
            jdVar.mo1091a(this.a);
            jdVar.b();
        }
        if (this.f98a != null && t()) {
            jdVar.a(G);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f98a.size()));
            for (Map.Entry<String, String> entry : this.f98a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z2) {
        this.f97a.set(1, z2);
    }

    public boolean b() {
        return this.f94a != null;
    }

    public hx c(String str) {
        this.d = str;
        return this;
    }

    public void c(boolean z2) {
        this.f97a.set(2, z2);
    }

    public boolean c() {
        return this.b != null;
    }

    public hx d(String str) {
        this.e = str;
        return this;
    }

    public void d(boolean z2) {
        this.f97a.set(3, z2);
    }

    public boolean d() {
        return this.c != null;
    }

    public void e(boolean z2) {
        this.f97a.set(4, z2);
    }

    public boolean e() {
        return this.f97a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hx)) {
            return m1002a((hx) obj);
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
        return this.f95a != null;
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
        return this.f97a.get(1);
    }

    public boolean l() {
        return this.h != null;
    }

    public boolean m() {
        return this.i != null;
    }

    public boolean n() {
        return this.j != null;
    }

    public boolean o() {
        return this.f97a.get(2);
    }

    public boolean p() {
        return this.f97a.get(3);
    }

    public boolean q() {
        return this.k != null;
    }

    public boolean r() {
        return this.l != null;
    }

    public boolean s() {
        return this.f97a.get(4);
    }

    public boolean t() {
        return this.f98a != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionAckMessage(");
        if (m1001a()) {
            sb.append("debug:");
            String str = this.f96a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z2 = false;
        } else {
            z2 = true;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f94a;
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
        String str2 = this.b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.c;
        if (str3 == null) {
            str3 = "null";
        }
        sb.append(str3);
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.f93a);
        if (f()) {
            sb.append(", ");
            sb.append("topic:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("request:");
            ik ikVar = this.f95a;
            if (ikVar == null) {
                sb.append("null");
            } else {
                sb.append(ikVar);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
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
            sb.append("isOnline:");
            sb.append(this.f100a);
        }
        if (l()) {
            sb.append(", ");
            sb.append("regId:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        if (m()) {
            sb.append(", ");
            sb.append("callbackUrl:");
            String str9 = this.i;
            if (str9 == null) {
                str9 = "null";
            }
            sb.append(str9);
        }
        if (n()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str10 = this.j;
            if (str10 == null) {
                str10 = "null";
            }
            sb.append(str10);
        }
        if (o()) {
            sb.append(", ");
            sb.append("deviceStatus:");
            sb.append((int) this.f99a);
        }
        if (p()) {
            sb.append(", ");
            sb.append("geoMsgStatus:");
            sb.append((int) this.f101b);
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
        if (s()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.a);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f98a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
