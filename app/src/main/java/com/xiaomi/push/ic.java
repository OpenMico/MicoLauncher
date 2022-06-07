package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/* loaded from: classes4.dex */
public class ic implements is<ic, Object>, Serializable, Cloneable {
    private static final ji g = new ji("XmPushActionCommandResult");
    private static final ja h = new ja("", (byte) 12, 2);
    private static final ja i = new ja("", (byte) 11, 3);
    private static final ja j = new ja("", (byte) 11, 4);
    private static final ja k = new ja("", (byte) 11, 5);
    private static final ja l = new ja("", (byte) 10, 7);
    private static final ja m = new ja("", (byte) 11, 8);
    private static final ja n = new ja("", (byte) 11, 9);
    private static final ja o = new ja("", (byte) 15, 10);
    private static final ja p = new ja("", (byte) 11, 12);
    private static final ja q = new ja("", (byte) 2, 13);
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hw f113a;

    /* renamed from: a  reason: collision with other field name */
    public String f114a;

    /* renamed from: a  reason: collision with other field name */
    public List<String> f116a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f115a = new BitSet(2);

    /* renamed from: a  reason: collision with other field name */
    public boolean f117a = true;

    /* renamed from: a */
    public int compareTo(ic icVar) {
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
        if (!getClass().equals(icVar.getClass())) {
            return getClass().getName().compareTo(icVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1018a()).compareTo(Boolean.valueOf(icVar.m1018a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1018a() && (a10 = it.a(this.f113a, icVar.f113a)) != 0) {
            return a10;
        }
        int compareTo2 = Boolean.valueOf(m1020b()).compareTo(Boolean.valueOf(icVar.m1020b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m1020b() && (a9 = it.a(this.f114a, icVar.f114a)) != 0) {
            return a9;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(icVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a8 = it.a(this.b, icVar.b)) != 0) {
            return a8;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(icVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a7 = it.a(this.c, icVar.c)) != 0) {
            return a7;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(icVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a6 = it.a(this.a, icVar.a)) != 0) {
            return a6;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(icVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a5 = it.a(this.d, icVar.d)) != 0) {
            return a5;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(icVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a4 = it.a(this.e, icVar.e)) != 0) {
            return a4;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(icVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a3 = it.a(this.f116a, icVar.f116a)) != 0) {
            return a3;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(icVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a2 = it.a(this.f, icVar.f)) != 0) {
            return a2;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(icVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (!j() || (a = it.a(this.f117a, icVar.f117a)) == 0) {
            return 0;
        }
        return a;
    }

    public String a() {
        return this.c;
    }

    /* renamed from: a  reason: collision with other method in class */
    public List<String> m1016a() {
        return this.f116a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1017a() {
        if (this.f114a == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.b == null) {
            throw new je("Required field 'appId' was not present! Struct: " + toString());
        } else if (this.c == null) {
            throw new je("Required field 'cmdName' was not present! Struct: " + toString());
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
                    m1017a();
                    return;
                }
                throw new je("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (a.f180a) {
                case 2:
                    if (a.a == 12) {
                        this.f113a = new hw();
                        this.f113a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f114a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 11) {
                        this.b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 5:
                    if (a.a == 11) {
                        this.c = jdVar.m1100a();
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
                        this.d = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 9:
                    if (a.a == 11) {
                        this.e = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 15) {
                        jb a2 = jdVar.m1096a();
                        this.f116a = new ArrayList(a2.f181a);
                        for (int i2 = 0; i2 < a2.f181a; i2++) {
                            this.f116a.add(jdVar.m1100a());
                        }
                        jdVar.i();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 12:
                    if (a.a == 11) {
                        this.f = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 13:
                    if (a.a == 2) {
                        this.f117a = jdVar.m1104a();
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
        this.f115a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1018a() {
        return this.f113a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1019a(ic icVar) {
        if (icVar == null) {
            return false;
        }
        boolean a = m1018a();
        boolean a2 = icVar.m1018a();
        if ((a || a2) && (!a || !a2 || !this.f113a.m1000a(icVar.f113a))) {
            return false;
        }
        boolean b = m1020b();
        boolean b2 = icVar.m1020b();
        if ((b || b2) && (!b || !b2 || !this.f114a.equals(icVar.f114a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = icVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(icVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = icVar.d();
        if (((d || d2) && (!d || !d2 || !this.c.equals(icVar.c))) || this.a != icVar.a) {
            return false;
        }
        boolean f = f();
        boolean f2 = icVar.f();
        if ((f || f2) && (!f || !f2 || !this.d.equals(icVar.d))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = icVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.e.equals(icVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = icVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.f116a.equals(icVar.f116a))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = icVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.f.equals(icVar.f))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = icVar.j();
        if (j2 || j3) {
            return j2 && j3 && this.f117a == icVar.f117a;
        }
        return true;
    }

    public String b() {
        return this.f;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1017a();
        jdVar.a(g);
        if (this.f113a != null && m1018a()) {
            jdVar.a(h);
            this.f113a.b(jdVar);
            jdVar.b();
        }
        if (this.f114a != null) {
            jdVar.a(i);
            jdVar.a(this.f114a);
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
        jdVar.a(l);
        jdVar.a(this.a);
        jdVar.b();
        if (this.d != null && f()) {
            jdVar.a(m);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && g()) {
            jdVar.a(n);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f116a != null && h()) {
            jdVar.a(o);
            jdVar.a(new jb((byte) 11, this.f116a.size()));
            for (String str : this.f116a) {
                jdVar.a(str);
            }
            jdVar.e();
            jdVar.b();
        }
        if (this.f != null && i()) {
            jdVar.a(p);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (j()) {
            jdVar.a(q);
            jdVar.a(this.f117a);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f115a.set(1, z);
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m1020b() {
        return this.f114a != null;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.f115a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ic)) {
            return m1019a((ic) obj);
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
        return this.f116a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f != null;
    }

    public boolean j() {
        return this.f115a.get(1);
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionCommandResult(");
        if (m1018a()) {
            sb.append("target:");
            hw hwVar = this.f113a;
            if (hwVar == null) {
                sb.append("null");
            } else {
                sb.append(hwVar);
            }
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        String str = this.f114a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        sb.append(", ");
        sb.append("appId:");
        String str2 = this.b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", ");
        sb.append("cmdName:");
        String str3 = this.c;
        if (str3 == null) {
            str3 = "null";
        }
        sb.append(str3);
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.a);
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
            sb.append("packageName:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            List<String> list = this.f116a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("category:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (j()) {
            sb.append(", ");
            sb.append("response2Client:");
            sb.append(this.f117a);
        }
        sb.append(")");
        return sb.toString();
    }
}
