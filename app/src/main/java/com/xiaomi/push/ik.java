package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ik implements is<ik, Object>, Serializable, Cloneable {
    private static final ji i = new ji("XmPushActionSendMessage");
    private static final ja j = new ja("", (byte) 11, 1);
    private static final ja k = new ja("", (byte) 12, 2);
    private static final ja l = new ja("", (byte) 11, 3);
    private static final ja m = new ja("", (byte) 11, 4);
    private static final ja n = new ja("", (byte) 11, 5);
    private static final ja o = new ja("", (byte) 11, 6);
    private static final ja p = new ja("", (byte) 11, 7);
    private static final ja q = new ja("", (byte) 12, 8);
    private static final ja r = new ja("", (byte) 2, 9);
    private static final ja s = new ja("", (byte) 13, 10);
    private static final ja t = new ja("", (byte) 11, 11);
    private static final ja u = new ja("", (byte) 11, 12);
    public ht a;

    /* renamed from: a  reason: collision with other field name */
    public hw f153a;

    /* renamed from: a  reason: collision with other field name */
    public String f154a;

    /* renamed from: a  reason: collision with other field name */
    public Map<String, String> f156a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f155a = new BitSet(1);

    /* renamed from: a  reason: collision with other field name */
    public boolean f157a = true;

    /* renamed from: a */
    public int compareTo(ik ikVar) {
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
        if (!getClass().equals(ikVar.getClass())) {
            return getClass().getName().compareTo(ikVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1058a()).compareTo(Boolean.valueOf(ikVar.m1058a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1058a() && (a12 = it.a(this.f154a, ikVar.f154a)) != 0) {
            return a12;
        }
        int compareTo2 = Boolean.valueOf(m1060b()).compareTo(Boolean.valueOf(ikVar.m1060b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m1060b() && (a11 = it.a(this.f153a, ikVar.f153a)) != 0) {
            return a11;
        }
        int compareTo3 = Boolean.valueOf(m1061c()).compareTo(Boolean.valueOf(ikVar.m1061c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m1061c() && (a10 = it.a(this.b, ikVar.b)) != 0) {
            return a10;
        }
        int compareTo4 = Boolean.valueOf(m1062d()).compareTo(Boolean.valueOf(ikVar.m1062d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (m1062d() && (a9 = it.a(this.c, ikVar.c)) != 0) {
            return a9;
        }
        int compareTo5 = Boolean.valueOf(m1063e()).compareTo(Boolean.valueOf(ikVar.m1063e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (m1063e() && (a8 = it.a(this.d, ikVar.d)) != 0) {
            return a8;
        }
        int compareTo6 = Boolean.valueOf(m1064f()).compareTo(Boolean.valueOf(ikVar.m1064f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (m1064f() && (a7 = it.a(this.e, ikVar.e)) != 0) {
            return a7;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ikVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a6 = it.a(this.f, ikVar.f)) != 0) {
            return a6;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ikVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a5 = it.a(this.a, ikVar.a)) != 0) {
            return a5;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ikVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a4 = it.a(this.f157a, ikVar.f157a)) != 0) {
            return a4;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ikVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a3 = it.a(this.f156a, ikVar.f156a)) != 0) {
            return a3;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ikVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a2 = it.a(this.g, ikVar.g)) != 0) {
            return a2;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(ikVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (!l() || (a = it.a(this.h, ikVar.h)) == 0) {
            return 0;
        }
        return a;
    }

    public ht a() {
        return this.a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public String m1056a() {
        return this.b;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1057a() {
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
                m1057a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f154a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f153a = new hw();
                        this.f153a.a(jdVar);
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
                    if (a.a == 12) {
                        this.a = new ht();
                        this.a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 9:
                    if (a.a == 2) {
                        this.f157a = jdVar.m1104a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 13) {
                        jc a2 = jdVar.m1097a();
                        this.f156a = new HashMap(a2.f182a * 2);
                        for (int i2 = 0; i2 < a2.f182a; i2++) {
                            this.f156a.put(jdVar.m1100a(), jdVar.m1100a());
                        }
                        jdVar.h();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 11:
                    if (a.a == 11) {
                        this.g = jdVar.m1100a();
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
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    public void a(boolean z) {
        this.f155a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1058a() {
        return this.f154a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1059a(ik ikVar) {
        if (ikVar == null) {
            return false;
        }
        boolean a = m1058a();
        boolean a2 = ikVar.m1058a();
        if ((a || a2) && (!a || !a2 || !this.f154a.equals(ikVar.f154a))) {
            return false;
        }
        boolean b = m1060b();
        boolean b2 = ikVar.m1060b();
        if ((b || b2) && (!b || !b2 || !this.f153a.m1000a(ikVar.f153a))) {
            return false;
        }
        boolean c = m1061c();
        boolean c2 = ikVar.m1061c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(ikVar.b))) {
            return false;
        }
        boolean d = m1062d();
        boolean d2 = ikVar.m1062d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(ikVar.c))) {
            return false;
        }
        boolean e = m1063e();
        boolean e2 = ikVar.m1063e();
        if ((e || e2) && (!e || !e2 || !this.d.equals(ikVar.d))) {
            return false;
        }
        boolean f = m1064f();
        boolean f2 = ikVar.m1064f();
        if ((f || f2) && (!f || !f2 || !this.e.equals(ikVar.e))) {
            return false;
        }
        boolean g = g();
        boolean g2 = ikVar.g();
        if ((g || g2) && (!g || !g2 || !this.f.equals(ikVar.f))) {
            return false;
        }
        boolean h = h();
        boolean h2 = ikVar.h();
        if ((h || h2) && (!h || !h2 || !this.a.m983a(ikVar.a))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = ikVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.f157a != ikVar.f157a)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = ikVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.f156a.equals(ikVar.f156a))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = ikVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.g.equals(ikVar.g))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = ikVar.l();
        if (l2 || l3) {
            return l2 && l3 && this.h.equals(ikVar.h);
        }
        return true;
    }

    public String b() {
        return this.c;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1057a();
        jdVar.a(i);
        if (this.f154a != null && m1058a()) {
            jdVar.a(j);
            jdVar.a(this.f154a);
            jdVar.b();
        }
        if (this.f153a != null && m1060b()) {
            jdVar.a(k);
            this.f153a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(l);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null) {
            jdVar.a(m);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (this.d != null && m1063e()) {
            jdVar.a(n);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && m1064f()) {
            jdVar.a(o);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && g()) {
            jdVar.a(p);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.a != null && h()) {
            jdVar.a(q);
            this.a.b(jdVar);
            jdVar.b();
        }
        if (i()) {
            jdVar.a(r);
            jdVar.a(this.f157a);
            jdVar.b();
        }
        if (this.f156a != null && j()) {
            jdVar.a(s);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f156a.size()));
            for (Map.Entry<String, String> entry : this.f156a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (this.g != null && k()) {
            jdVar.a(t);
            jdVar.a(this.g);
            jdVar.b();
        }
        if (this.h != null && l()) {
            jdVar.a(u);
            jdVar.a(this.h);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m1060b() {
        return this.f153a != null;
    }

    public String c() {
        return this.e;
    }

    /* renamed from: c  reason: collision with other method in class */
    public boolean m1061c() {
        return this.b != null;
    }

    public String d() {
        return this.f;
    }

    /* renamed from: d  reason: collision with other method in class */
    public boolean m1062d() {
        return this.c != null;
    }

    public String e() {
        return this.g;
    }

    /* renamed from: e  reason: collision with other method in class */
    public boolean m1063e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ik)) {
            return m1059a((ik) obj);
        }
        return false;
    }

    public String f() {
        return this.h;
    }

    /* renamed from: f  reason: collision with other method in class */
    public boolean m1064f() {
        return this.e != null;
    }

    public boolean g() {
        return this.f != null;
    }

    public boolean h() {
        return this.a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f155a.get(0);
    }

    public boolean j() {
        return this.f156a != null;
    }

    public boolean k() {
        return this.g != null;
    }

    public boolean l() {
        return this.h != null;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionSendMessage(");
        if (m1058a()) {
            sb.append("debug:");
            String str = this.f154a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z = false;
        } else {
            z = true;
        }
        if (m1060b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f153a;
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
        if (m1063e()) {
            sb.append(", ");
            sb.append("packageName:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (m1064f()) {
            sb.append(", ");
            sb.append("topic:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (h()) {
            sb.append(", ");
            sb.append("message:");
            ht htVar = this.a;
            if (htVar == null) {
                sb.append("null");
            } else {
                sb.append(htVar);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.f157a);
        }
        if (j()) {
            sb.append(", ");
            sb.append("params:");
            Map<String, String> map = this.f156a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        if (l()) {
            sb.append(", ");
            sb.append("userAccount:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        sb.append(")");
        return sb.toString();
    }
}
