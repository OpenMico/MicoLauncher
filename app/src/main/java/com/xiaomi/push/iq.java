package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class iq implements is<iq, Object>, Serializable, Cloneable {
    private static final ji h = new ji("XmPushActionUnSubscriptionResult");
    private static final ja i = new ja("", (byte) 11, 1);
    private static final ja j = new ja("", (byte) 12, 2);
    private static final ja k = new ja("", (byte) 11, 3);
    private static final ja l = new ja("", (byte) 11, 4);
    private static final ja m = new ja("", (byte) 10, 6);
    private static final ja n = new ja("", (byte) 11, 7);
    private static final ja o = new ja("", (byte) 11, 8);
    private static final ja p = new ja("", (byte) 11, 9);
    private static final ja q = new ja("", (byte) 11, 10);
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hw f174a;

    /* renamed from: a  reason: collision with other field name */
    public String f175a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f176a = new BitSet(1);
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    /* renamed from: a */
    public int compareTo(iq iqVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        int a8;
        int a9;
        if (!getClass().equals(iqVar.getClass())) {
            return getClass().getName().compareTo(iqVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1079a()).compareTo(Boolean.valueOf(iqVar.m1079a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1079a() && (a9 = it.a(this.f175a, iqVar.f175a)) != 0) {
            return a9;
        }
        int compareTo2 = Boolean.valueOf(m1081b()).compareTo(Boolean.valueOf(iqVar.m1081b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m1081b() && (a8 = it.a(this.f174a, iqVar.f174a)) != 0) {
            return a8;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(iqVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a7 = it.a(this.b, iqVar.b)) != 0) {
            return a7;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(iqVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a6 = it.a(this.c, iqVar.c)) != 0) {
            return a6;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(iqVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a5 = it.a(this.a, iqVar.a)) != 0) {
            return a5;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(iqVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a4 = it.a(this.d, iqVar.d)) != 0) {
            return a4;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(iqVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a3 = it.a(this.e, iqVar.e)) != 0) {
            return a3;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(iqVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a2 = it.a(this.f, iqVar.f)) != 0) {
            return a2;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(iqVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (!i() || (a = it.a(this.g, iqVar.g)) == 0) {
            return 0;
        }
        return a;
    }

    public String a() {
        return this.e;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1078a() {
        if (this.b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m1078a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f175a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f174a = new hw();
                        this.f174a.a(jdVar);
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
                case 6:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
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
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    public void a(boolean z) {
        this.f176a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1079a() {
        return this.f175a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1080a(iq iqVar) {
        if (iqVar == null) {
            return false;
        }
        boolean a = m1079a();
        boolean a2 = iqVar.m1079a();
        if ((a || a2) && (!a || !a2 || !this.f175a.equals(iqVar.f175a))) {
            return false;
        }
        boolean b = m1081b();
        boolean b2 = iqVar.m1081b();
        if ((b || b2) && (!b || !b2 || !this.f174a.m1000a(iqVar.f174a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = iqVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(iqVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = iqVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(iqVar.c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = iqVar.e();
        if ((e || e2) && (!e || !e2 || this.a != iqVar.a)) {
            return false;
        }
        boolean f = f();
        boolean f2 = iqVar.f();
        if ((f || f2) && (!f || !f2 || !this.d.equals(iqVar.d))) {
            return false;
        }
        boolean g = g();
        boolean g2 = iqVar.g();
        if ((g || g2) && (!g || !g2 || !this.e.equals(iqVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = iqVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.f.equals(iqVar.f))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = iqVar.i();
        if (i2 || i3) {
            return i2 && i3 && this.g.equals(iqVar.g);
        }
        return true;
    }

    public String b() {
        return this.g;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1078a();
        jdVar.a(h);
        if (this.f175a != null && m1079a()) {
            jdVar.a(i);
            jdVar.a(this.f175a);
            jdVar.b();
        }
        if (this.f174a != null && m1081b()) {
            jdVar.a(j);
            this.f174a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(k);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null && d()) {
            jdVar.a(l);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (e()) {
            jdVar.a(m);
            jdVar.a(this.a);
            jdVar.b();
        }
        if (this.d != null && f()) {
            jdVar.a(n);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && g()) {
            jdVar.a(o);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && h()) {
            jdVar.a(p);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && i()) {
            jdVar.a(q);
            jdVar.a(this.g);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m1081b() {
        return this.f174a != null;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.f176a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof iq)) {
            return m1080a((iq) obj);
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

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscriptionResult(");
        if (m1079a()) {
            sb.append("debug:");
            String str = this.f175a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z = false;
        } else {
            z = true;
        }
        if (m1081b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f174a;
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
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.c;
            if (str3 == null) {
                str3 = "null";
            }
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.a);
        }
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
            sb.append("topic:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        sb.append(")");
        return sb.toString();
    }
}
