package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class in implements is<in, Object>, Serializable, Cloneable {
    private static final ji j = new ji("XmPushActionUnRegistration");
    private static final ja k = new ja("", (byte) 11, 1);
    private static final ja l = new ja("", (byte) 12, 2);
    private static final ja m = new ja("", (byte) 11, 3);
    private static final ja n = new ja("", (byte) 11, 4);
    private static final ja o = new ja("", (byte) 11, 5);
    private static final ja p = new ja("", (byte) 11, 6);
    private static final ja q = new ja("", (byte) 11, 7);
    private static final ja r = new ja("", (byte) 11, 8);
    private static final ja s = new ja("", (byte) 11, 9);
    private static final ja t = new ja("", (byte) 11, 10);
    private static final ja u = new ja("", (byte) 2, 11);
    private static final ja v = new ja("", (byte) 10, 12);
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hw f163a;

    /* renamed from: a  reason: collision with other field name */
    public String f164a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f165a = new BitSet(2);

    /* renamed from: a  reason: collision with other field name */
    public boolean f166a = true;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;

    /* renamed from: a */
    public int compareTo(in inVar) {
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
        if (!getClass().equals(inVar.getClass())) {
            return getClass().getName().compareTo(inVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1071a()).compareTo(Boolean.valueOf(inVar.m1071a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1071a() && (a12 = it.a(this.f164a, inVar.f164a)) != 0) {
            return a12;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(inVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a11 = it.a(this.f163a, inVar.f163a)) != 0) {
            return a11;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(inVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a10 = it.a(this.b, inVar.b)) != 0) {
            return a10;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(inVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a9 = it.a(this.c, inVar.c)) != 0) {
            return a9;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(inVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a8 = it.a(this.d, inVar.d)) != 0) {
            return a8;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(inVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a7 = it.a(this.e, inVar.e)) != 0) {
            return a7;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(inVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a6 = it.a(this.f, inVar.f)) != 0) {
            return a6;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(inVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a5 = it.a(this.g, inVar.g)) != 0) {
            return a5;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(inVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a4 = it.a(this.h, inVar.h)) != 0) {
            return a4;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(inVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a3 = it.a(this.i, inVar.i)) != 0) {
            return a3;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(inVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a2 = it.a(this.f166a, inVar.f166a)) != 0) {
            return a2;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(inVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (!l() || (a = it.a(this.a, inVar.a)) == 0) {
            return 0;
        }
        return a;
    }

    public in a(String str) {
        this.b = str;
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
                a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f164a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f163a = new hw();
                        this.f163a.a(jdVar);
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
                    if (a.a == 2) {
                        this.f166a = jdVar.m1104a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 12:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    public void a(boolean z) {
        this.f165a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1071a() {
        return this.f164a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1072a(in inVar) {
        if (inVar == null) {
            return false;
        }
        boolean a = m1071a();
        boolean a2 = inVar.m1071a();
        if ((a || a2) && (!a || !a2 || !this.f164a.equals(inVar.f164a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = inVar.b();
        if ((b || b2) && (!b || !b2 || !this.f163a.m1000a(inVar.f163a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = inVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(inVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = inVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(inVar.c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = inVar.e();
        if ((e || e2) && (!e || !e2 || !this.d.equals(inVar.d))) {
            return false;
        }
        boolean f = f();
        boolean f2 = inVar.f();
        if ((f || f2) && (!f || !f2 || !this.e.equals(inVar.e))) {
            return false;
        }
        boolean g = g();
        boolean g2 = inVar.g();
        if ((g || g2) && (!g || !g2 || !this.f.equals(inVar.f))) {
            return false;
        }
        boolean h = h();
        boolean h2 = inVar.h();
        if ((h || h2) && (!h || !h2 || !this.g.equals(inVar.g))) {
            return false;
        }
        boolean i = i();
        boolean i2 = inVar.i();
        if ((i || i2) && (!i || !i2 || !this.h.equals(inVar.h))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = inVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.i.equals(inVar.i))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = inVar.k();
        if ((k2 || k3) && (!k2 || !k3 || this.f166a != inVar.f166a)) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = inVar.l();
        if (l2 || l3) {
            return l2 && l3 && this.a == inVar.a;
        }
        return true;
    }

    public in b(String str) {
        this.c = str;
        return this;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(j);
        if (this.f164a != null && m1071a()) {
            jdVar.a(k);
            jdVar.a(this.f164a);
            jdVar.b();
        }
        if (this.f163a != null && b()) {
            jdVar.a(l);
            this.f163a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(m);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null) {
            jdVar.a(n);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (this.d != null && e()) {
            jdVar.a(o);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && f()) {
            jdVar.a(p);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && g()) {
            jdVar.a(q);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && h()) {
            jdVar.a(r);
            jdVar.a(this.g);
            jdVar.b();
        }
        if (this.h != null && i()) {
            jdVar.a(s);
            jdVar.a(this.h);
            jdVar.b();
        }
        if (this.i != null && j()) {
            jdVar.a(t);
            jdVar.a(this.i);
            jdVar.b();
        }
        if (k()) {
            jdVar.a(u);
            jdVar.a(this.f166a);
            jdVar.b();
        }
        if (l()) {
            jdVar.a(v);
            jdVar.a(this.a);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f165a.set(1, z);
    }

    public boolean b() {
        return this.f163a != null;
    }

    public in c(String str) {
        this.d = str;
        return this;
    }

    public boolean c() {
        return this.b != null;
    }

    public in d(String str) {
        this.f = str;
        return this;
    }

    public boolean d() {
        return this.c != null;
    }

    public in e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof in)) {
            return m1072a((in) obj);
        }
        return false;
    }

    public boolean f() {
        return this.e != null;
    }

    public boolean g() {
        return this.f != null;
    }

    public boolean h() {
        return this.g != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.h != null;
    }

    public boolean j() {
        return this.i != null;
    }

    public boolean k() {
        return this.f165a.get(0);
    }

    public boolean l() {
        return this.f165a.get(1);
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistration(");
        if (m1071a()) {
            sb.append("debug:");
            String str = this.f164a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z = false;
        } else {
            z = true;
        }
        if (b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f163a;
            if (hwVar == null) {
                sb.append("null");
            } else {
                sb.append(hwVar);
            }
            z = false;
        }
        if (!z) {
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
        if (e()) {
            sb.append(", ");
            sb.append("regId:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("appVersion:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (h()) {
            sb.append(", ");
            sb.append("token:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        if (i()) {
            sb.append(", ");
            sb.append("deviceId:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        if (j()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.i;
            if (str9 == null) {
                str9 = "null";
            }
            sb.append(str9);
        }
        if (k()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.f166a);
        }
        if (l()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
