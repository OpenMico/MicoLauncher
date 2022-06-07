package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ip implements is<ip, Object>, Serializable, Cloneable {
    private static final ji g = new ji("XmPushActionUnSubscription");
    private static final ja h = new ja("", (byte) 11, 1);
    private static final ja i = new ja("", (byte) 12, 2);
    private static final ja j = new ja("", (byte) 11, 3);
    private static final ja k = new ja("", (byte) 11, 4);
    private static final ja l = new ja("", (byte) 11, 5);
    private static final ja m = new ja("", (byte) 11, 6);
    private static final ja n = new ja("", (byte) 11, 7);
    private static final ja o = new ja("", (byte) 15, 8);
    public hw a;

    /* renamed from: a  reason: collision with other field name */
    public String f172a;

    /* renamed from: a  reason: collision with other field name */
    public List<String> f173a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    /* renamed from: a */
    public int compareTo(ip ipVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        int a8;
        if (!getClass().equals(ipVar.getClass())) {
            return getClass().getName().compareTo(ipVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1076a()).compareTo(Boolean.valueOf(ipVar.m1076a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1076a() && (a8 = it.a(this.f172a, ipVar.f172a)) != 0) {
            return a8;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ipVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a7 = it.a(this.a, ipVar.a)) != 0) {
            return a7;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ipVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a6 = it.a(this.b, ipVar.b)) != 0) {
            return a6;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ipVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a5 = it.a(this.c, ipVar.c)) != 0) {
            return a5;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ipVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a4 = it.a(this.d, ipVar.d)) != 0) {
            return a4;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ipVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a3 = it.a(this.e, ipVar.e)) != 0) {
            return a3;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ipVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a2 = it.a(this.f, ipVar.f)) != 0) {
            return a2;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ipVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (!h() || (a = it.a(this.f173a, ipVar.f173a)) == 0) {
            return 0;
        }
        return a;
    }

    public ip a(String str) {
        this.b = str;
        return this;
    }

    public void a() {
        if (this.b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new je("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.d == null) {
            throw new je("Required field 'topic' was not present! Struct: " + toString());
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
                        this.f172a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.a = new hw();
                        this.a.a(jdVar);
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
                    if (a.a == 15) {
                        jb a2 = jdVar.m1096a();
                        this.f173a = new ArrayList(a2.f181a);
                        for (int i2 = 0; i2 < a2.f181a; i2++) {
                            this.f173a.add(jdVar.m1100a());
                        }
                        jdVar.i();
                        continue;
                        jdVar.g();
                    }
                    break;
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1076a() {
        return this.f172a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1077a(ip ipVar) {
        if (ipVar == null) {
            return false;
        }
        boolean a = m1076a();
        boolean a2 = ipVar.m1076a();
        if ((a || a2) && (!a || !a2 || !this.f172a.equals(ipVar.f172a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = ipVar.b();
        if ((b || b2) && (!b || !b2 || !this.a.m1000a(ipVar.a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = ipVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(ipVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = ipVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(ipVar.c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = ipVar.e();
        if ((e || e2) && (!e || !e2 || !this.d.equals(ipVar.d))) {
            return false;
        }
        boolean f = f();
        boolean f2 = ipVar.f();
        if ((f || f2) && (!f || !f2 || !this.e.equals(ipVar.e))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = ipVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.f.equals(ipVar.f))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = ipVar.h();
        if (h2 || h3) {
            return h2 && h3 && this.f173a.equals(ipVar.f173a);
        }
        return true;
    }

    public ip b(String str) {
        this.c = str;
        return this;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(g);
        if (this.f172a != null && m1076a()) {
            jdVar.a(h);
            jdVar.a(this.f172a);
            jdVar.b();
        }
        if (this.a != null && b()) {
            jdVar.a(i);
            this.a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(j);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null) {
            jdVar.a(k);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (this.d != null) {
            jdVar.a(l);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && f()) {
            jdVar.a(m);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && g()) {
            jdVar.a(n);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.f173a != null && h()) {
            jdVar.a(o);
            jdVar.a(new jb((byte) 11, this.f173a.size()));
            for (String str : this.f173a) {
                jdVar.a(str);
            }
            jdVar.e();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean b() {
        return this.a != null;
    }

    public ip c(String str) {
        this.d = str;
        return this;
    }

    public boolean c() {
        return this.b != null;
    }

    public ip d(String str) {
        this.e = str;
        return this;
    }

    public boolean d() {
        return this.c != null;
    }

    public ip e(String str) {
        this.f = str;
        return this;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ip)) {
            return m1077a((ip) obj);
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
        return this.f173a != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscription(");
        if (m1076a()) {
            sb.append("debug:");
            String str = this.f172a;
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
            hw hwVar = this.a;
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
        sb.append(", ");
        sb.append("topic:");
        String str4 = this.d;
        if (str4 == null) {
            str4 = "null";
        }
        sb.append(str4);
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (h()) {
            sb.append(", ");
            sb.append("aliases:");
            List<String> list = this.f173a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
