package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class hy implements is<hy, Object>, Serializable, Cloneable {
    private static final ji h = new ji("XmPushActionAckNotification");
    private static final ja i = new ja("", (byte) 11, 1);
    private static final ja j = new ja("", (byte) 12, 2);
    private static final ja k = new ja("", (byte) 11, 3);
    private static final ja l = new ja("", (byte) 11, 4);
    private static final ja m = new ja("", (byte) 11, 5);
    private static final ja n = new ja("", (byte) 10, 7);
    private static final ja o = new ja("", (byte) 11, 8);
    private static final ja p = new ja("", (byte) 13, 9);
    private static final ja q = new ja("", (byte) 11, 10);
    private static final ja r = new ja("", (byte) 11, 11);

    /* renamed from: a  reason: collision with other field name */
    public hw f102a;

    /* renamed from: a  reason: collision with other field name */
    public String f103a;

    /* renamed from: a  reason: collision with other field name */
    public Map<String, String> f105a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f104a = new BitSet(1);
    public long a = 0;

    /* renamed from: a */
    public int compareTo(hy hyVar) {
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
        if (!getClass().equals(hyVar.getClass())) {
            return getClass().getName().compareTo(hyVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1005a()).compareTo(Boolean.valueOf(hyVar.m1005a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1005a() && (a10 = it.a(this.f103a, hyVar.f103a)) != 0) {
            return a10;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hyVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a9 = it.a(this.f102a, hyVar.f102a)) != 0) {
            return a9;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(hyVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a8 = it.a(this.b, hyVar.b)) != 0) {
            return a8;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(hyVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a7 = it.a(this.c, hyVar.c)) != 0) {
            return a7;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(hyVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a6 = it.a(this.d, hyVar.d)) != 0) {
            return a6;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(hyVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a5 = it.a(this.a, hyVar.a)) != 0) {
            return a5;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(hyVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a4 = it.a(this.e, hyVar.e)) != 0) {
            return a4;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(hyVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a3 = it.a(this.f105a, hyVar.f105a)) != 0) {
            return a3;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(hyVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a2 = it.a(this.f, hyVar.f)) != 0) {
            return a2;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(hyVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (!j() || (a = it.a(this.g, hyVar.g)) == 0) {
            return 0;
        }
        return a;
    }

    public String a() {
        return this.b;
    }

    /* renamed from: a  reason: collision with other method in class */
    public Map<String, String> m1003a() {
        return this.f105a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1004a() {
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
                m1004a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f103a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f102a = new hw();
                        this.f102a.a(jdVar);
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
                case 7:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
                        a(true);
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
                    if (a.a == 13) {
                        jc a2 = jdVar.m1097a();
                        this.f105a = new HashMap(a2.f182a * 2);
                        for (int i2 = 0; i2 < a2.f182a; i2++) {
                            this.f105a.put(jdVar.m1100a(), jdVar.m1100a());
                        }
                        jdVar.h();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 11) {
                        this.f = jdVar.m1100a();
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

    public void a(boolean z) {
        this.f104a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1005a() {
        return this.f103a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1006a(hy hyVar) {
        if (hyVar == null) {
            return false;
        }
        boolean a = m1005a();
        boolean a2 = hyVar.m1005a();
        if ((a || a2) && (!a || !a2 || !this.f103a.equals(hyVar.f103a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = hyVar.b();
        if ((b || b2) && (!b || !b2 || !this.f102a.m1000a(hyVar.f102a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = hyVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(hyVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = hyVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(hyVar.c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = hyVar.e();
        if ((e || e2) && (!e || !e2 || !this.d.equals(hyVar.d))) {
            return false;
        }
        boolean f = f();
        boolean f2 = hyVar.f();
        if ((f || f2) && (!f || !f2 || this.a != hyVar.a)) {
            return false;
        }
        boolean g = g();
        boolean g2 = hyVar.g();
        if ((g || g2) && (!g || !g2 || !this.e.equals(hyVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = hyVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.f105a.equals(hyVar.f105a))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = hyVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.f.equals(hyVar.f))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = hyVar.j();
        if (j2 || j3) {
            return j2 && j3 && this.g.equals(hyVar.g);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1004a();
        jdVar.a(h);
        if (this.f103a != null && m1005a()) {
            jdVar.a(i);
            jdVar.a(this.f103a);
            jdVar.b();
        }
        if (this.f102a != null && b()) {
            jdVar.a(j);
            this.f102a.b(jdVar);
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
        if (this.d != null && e()) {
            jdVar.a(m);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (f()) {
            jdVar.a(n);
            jdVar.a(this.a);
            jdVar.b();
        }
        if (this.e != null && g()) {
            jdVar.a(o);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f105a != null && h()) {
            jdVar.a(p);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f105a.size()));
            for (Map.Entry<String, String> entry : this.f105a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (this.f != null && i()) {
            jdVar.a(q);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && j()) {
            jdVar.a(r);
            jdVar.a(this.g);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean b() {
        return this.f102a != null;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hy)) {
            return m1006a((hy) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f104a.get(0);
    }

    public boolean g() {
        return this.e != null;
    }

    public boolean h() {
        return this.f105a != null;
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

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionAckNotification(");
        if (m1005a()) {
            sb.append("debug:");
            String str = this.f103a;
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
            hw hwVar = this.f102a;
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
            sb.append("type:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.a);
        }
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f105a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
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
        sb.append(")");
        return sb.toString();
    }
}
