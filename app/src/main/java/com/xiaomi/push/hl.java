package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class hl implements is<hl, Object>, Serializable, Cloneable {
    private static final ji h = new ji("ClientUploadDataItem");
    private static final ja i = new ja("", (byte) 11, 1);
    private static final ja j = new ja("", (byte) 11, 2);
    private static final ja k = new ja("", (byte) 11, 3);
    private static final ja l = new ja("", (byte) 10, 4);
    private static final ja m = new ja("", (byte) 10, 5);
    private static final ja n = new ja("", (byte) 2, 6);
    private static final ja o = new ja("", (byte) 11, 7);
    private static final ja p = new ja("", (byte) 11, 8);
    private static final ja q = new ja("", (byte) 11, 9);
    private static final ja r = new ja("", (byte) 13, 10);
    private static final ja s = new ja("", (byte) 11, 11);
    public long a;

    /* renamed from: a */
    public String f53a;

    /* renamed from: a */
    private BitSet f54a = new BitSet(3);

    /* renamed from: a */
    public Map<String, String> f55a;

    /* renamed from: a */
    public boolean f56a;
    public long b;

    /* renamed from: b */
    public String f57b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    /* renamed from: a */
    public int compareTo(hl hlVar) {
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
        if (!getClass().equals(hlVar.getClass())) {
            return getClass().getName().compareTo(hlVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m960a()).compareTo(Boolean.valueOf(hlVar.m960a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m960a() && (a11 = it.a(this.f53a, hlVar.f53a)) != 0) {
            return a11;
        }
        int compareTo2 = Boolean.valueOf(m962b()).compareTo(Boolean.valueOf(hlVar.m962b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m962b() && (a10 = it.a(this.f57b, hlVar.f57b)) != 0) {
            return a10;
        }
        int compareTo3 = Boolean.valueOf(m963c()).compareTo(Boolean.valueOf(hlVar.m963c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m963c() && (a9 = it.a(this.c, hlVar.c)) != 0) {
            return a9;
        }
        int compareTo4 = Boolean.valueOf(m964d()).compareTo(Boolean.valueOf(hlVar.m964d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (m964d() && (a8 = it.a(this.a, hlVar.a)) != 0) {
            return a8;
        }
        int compareTo5 = Boolean.valueOf(m965e()).compareTo(Boolean.valueOf(hlVar.m965e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (m965e() && (a7 = it.a(this.b, hlVar.b)) != 0) {
            return a7;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(hlVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a6 = it.a(this.f56a, hlVar.f56a)) != 0) {
            return a6;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(hlVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a5 = it.a(this.d, hlVar.d)) != 0) {
            return a5;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(hlVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a4 = it.a(this.e, hlVar.e)) != 0) {
            return a4;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(hlVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a3 = it.a(this.f, hlVar.f)) != 0) {
            return a3;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(hlVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a2 = it.a(this.f55a, hlVar.f55a)) != 0) {
            return a2;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(hlVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (!k() || (a = it.a(this.g, hlVar.g)) == 0) {
            return 0;
        }
        return a;
    }

    public long a() {
        return this.b;
    }

    public hl a(long j2) {
        this.a = j2;
        m959a(true);
        return this;
    }

    public hl a(String str) {
        this.f53a = str;
        return this;
    }

    public hl a(boolean z) {
        this.f56a = z;
        c(true);
        return this;
    }

    /* renamed from: a */
    public String m957a() {
        return this.f53a;
    }

    /* renamed from: a */
    public void m958a() {
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m958a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f53a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 11) {
                        this.f57b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.c = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
                        m959a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 5:
                    if (a.a == 10) {
                        this.b = jdVar.m1094a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 6:
                    if (a.a == 2) {
                        this.f56a = jdVar.m1104a();
                        c(true);
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
                    if (a.a == 13) {
                        jc a2 = jdVar.m1097a();
                        this.f55a = new HashMap(a2.f182a * 2);
                        for (int i2 = 0; i2 < a2.f182a; i2++) {
                            this.f55a.put(jdVar.m1100a(), jdVar.m1100a());
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
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    /* renamed from: a */
    public void m959a(boolean z) {
        this.f54a.set(0, z);
    }

    /* renamed from: a */
    public boolean m960a() {
        return this.f53a != null;
    }

    /* renamed from: a */
    public boolean m961a(hl hlVar) {
        if (hlVar == null) {
            return false;
        }
        boolean a = m960a();
        boolean a2 = hlVar.m960a();
        if ((a || a2) && (!a || !a2 || !this.f53a.equals(hlVar.f53a))) {
            return false;
        }
        boolean b = m962b();
        boolean b2 = hlVar.m962b();
        if ((b || b2) && (!b || !b2 || !this.f57b.equals(hlVar.f57b))) {
            return false;
        }
        boolean c = m963c();
        boolean c2 = hlVar.m963c();
        if ((c || c2) && (!c || !c2 || !this.c.equals(hlVar.c))) {
            return false;
        }
        boolean d = m964d();
        boolean d2 = hlVar.m964d();
        if ((d || d2) && (!d || !d2 || this.a != hlVar.a)) {
            return false;
        }
        boolean e = m965e();
        boolean e2 = hlVar.m965e();
        if ((e || e2) && (!e || !e2 || this.b != hlVar.b)) {
            return false;
        }
        boolean f = f();
        boolean f2 = hlVar.f();
        if ((f || f2) && (!f || !f2 || this.f56a != hlVar.f56a)) {
            return false;
        }
        boolean g = g();
        boolean g2 = hlVar.g();
        if ((g || g2) && (!g || !g2 || !this.d.equals(hlVar.d))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = hlVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.e.equals(hlVar.e))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = hlVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.f.equals(hlVar.f))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = hlVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.f55a.equals(hlVar.f55a))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = hlVar.k();
        if (k2 || k3) {
            return k2 && k3 && this.g.equals(hlVar.g);
        }
        return true;
    }

    public hl b(long j2) {
        this.b = j2;
        b(true);
        return this;
    }

    public hl b(String str) {
        this.f57b = str;
        return this;
    }

    public String b() {
        return this.c;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m958a();
        jdVar.a(h);
        if (this.f53a != null && m960a()) {
            jdVar.a(i);
            jdVar.a(this.f53a);
            jdVar.b();
        }
        if (this.f57b != null && m962b()) {
            jdVar.a(j);
            jdVar.a(this.f57b);
            jdVar.b();
        }
        if (this.c != null && m963c()) {
            jdVar.a(k);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (m964d()) {
            jdVar.a(l);
            jdVar.a(this.a);
            jdVar.b();
        }
        if (m965e()) {
            jdVar.a(m);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (f()) {
            jdVar.a(n);
            jdVar.a(this.f56a);
            jdVar.b();
        }
        if (this.d != null && g()) {
            jdVar.a(o);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && h()) {
            jdVar.a(p);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f != null && i()) {
            jdVar.a(q);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.f55a != null && j()) {
            jdVar.a(r);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f55a.size()));
            for (Map.Entry<String, String> entry : this.f55a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (this.g != null && k()) {
            jdVar.a(s);
            jdVar.a(this.g);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f54a.set(1, z);
    }

    /* renamed from: b */
    public boolean m962b() {
        return this.f57b != null;
    }

    public hl c(String str) {
        this.c = str;
        return this;
    }

    public String c() {
        return this.e;
    }

    public void c(boolean z) {
        this.f54a.set(2, z);
    }

    /* renamed from: c */
    public boolean m963c() {
        return this.c != null;
    }

    public hl d(String str) {
        this.d = str;
        return this;
    }

    public String d() {
        return this.f;
    }

    /* renamed from: d */
    public boolean m964d() {
        return this.f54a.get(0);
    }

    public hl e(String str) {
        this.e = str;
        return this;
    }

    public String e() {
        return this.g;
    }

    /* renamed from: e */
    public boolean m965e() {
        return this.f54a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hl)) {
            return m961a((hl) obj);
        }
        return false;
    }

    public hl f(String str) {
        this.f = str;
        return this;
    }

    public boolean f() {
        return this.f54a.get(2);
    }

    public hl g(String str) {
        this.g = str;
        return this;
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
        return this.f55a != null;
    }

    public boolean k() {
        return this.g != null;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("ClientUploadDataItem(");
        if (m960a()) {
            sb.append("channel:");
            String str = this.f53a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z = false;
        } else {
            z = true;
        }
        if (m962b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("data:");
            String str2 = this.f57b;
            if (str2 == null) {
                str2 = "null";
            }
            sb.append(str2);
            z = false;
        }
        if (m963c()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("name:");
            String str3 = this.c;
            if (str3 == null) {
                str3 = "null";
            }
            sb.append(str3);
            z = false;
        }
        if (m964d()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("counter:");
            sb.append(this.a);
            z = false;
        }
        if (m965e()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("timestamp:");
            sb.append(this.b);
            z = false;
        }
        if (f()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("fromSdk:");
            sb.append(this.f56a);
            z = false;
        }
        if (g()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("category:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
            z = false;
        }
        if (h()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("sourcePackage:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
            z = false;
        }
        if (i()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("id:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
            z = false;
        }
        if (j()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("extra:");
            Map<String, String> map = this.f55a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
            z = false;
        }
        if (k()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("pkgName:");
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
