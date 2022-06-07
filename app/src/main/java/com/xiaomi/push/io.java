package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class io implements is<io, Object>, Serializable, Cloneable {
    private static final ji f = new ji("XmPushActionUnRegistrationResult");
    private static final ja g = new ja("", (byte) 11, 1);
    private static final ja h = new ja("", (byte) 12, 2);
    private static final ja i = new ja("", (byte) 11, 3);
    private static final ja j = new ja("", (byte) 11, 4);
    private static final ja k = new ja("", (byte) 10, 6);
    private static final ja l = new ja("", (byte) 11, 7);
    private static final ja m = new ja("", (byte) 11, 8);
    private static final ja n = new ja("", (byte) 10, 9);
    private static final ja o = new ja("", (byte) 10, 10);
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hw f167a;

    /* renamed from: a  reason: collision with other field name */
    public String f168a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f169a = new BitSet(3);
    public long b;

    /* renamed from: b  reason: collision with other field name */
    public String f170b;
    public long c;

    /* renamed from: c  reason: collision with other field name */
    public String f171c;
    public String d;
    public String e;

    /* renamed from: a */
    public int compareTo(io ioVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        int a8;
        int a9;
        if (!getClass().equals(ioVar.getClass())) {
            return getClass().getName().compareTo(ioVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1074a()).compareTo(Boolean.valueOf(ioVar.m1074a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1074a() && (a9 = it.a(this.f168a, ioVar.f168a)) != 0) {
            return a9;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ioVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a8 = it.a(this.f167a, ioVar.f167a)) != 0) {
            return a8;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ioVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a7 = it.a(this.f170b, ioVar.f170b)) != 0) {
            return a7;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ioVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a6 = it.a(this.f171c, ioVar.f171c)) != 0) {
            return a6;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ioVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a5 = it.a(this.a, ioVar.a)) != 0) {
            return a5;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ioVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a4 = it.a(this.d, ioVar.d)) != 0) {
            return a4;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ioVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a3 = it.a(this.e, ioVar.e)) != 0) {
            return a3;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ioVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a2 = it.a(this.b, ioVar.b)) != 0) {
            return a2;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ioVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (!i() || (a = it.a(this.c, ioVar.c)) == 0) {
            return 0;
        }
        return a;
    }

    public String a() {
        return this.e;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1073a() {
        if (this.f170b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        } else if (this.f171c == null) {
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
                    m1073a();
                    return;
                }
                throw new je("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f168a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f167a = new hw();
                        this.f167a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f170b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 11) {
                        this.f171c = jdVar.m1100a();
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
                    if (a.a == 10) {
                        this.b = jdVar.m1094a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 10) {
                        this.c = jdVar.m1094a();
                        c(true);
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
        this.f169a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1074a() {
        return this.f168a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1075a(io ioVar) {
        if (ioVar == null) {
            return false;
        }
        boolean a = m1074a();
        boolean a2 = ioVar.m1074a();
        if ((a || a2) && (!a || !a2 || !this.f168a.equals(ioVar.f168a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = ioVar.b();
        if ((b || b2) && (!b || !b2 || !this.f167a.m1000a(ioVar.f167a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = ioVar.c();
        if ((c || c2) && (!c || !c2 || !this.f170b.equals(ioVar.f170b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = ioVar.d();
        if (((d || d2) && (!d || !d2 || !this.f171c.equals(ioVar.f171c))) || this.a != ioVar.a) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = ioVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.d.equals(ioVar.d))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = ioVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.e.equals(ioVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = ioVar.h();
        if ((h2 || h3) && (!h2 || !h3 || this.b != ioVar.b)) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = ioVar.i();
        if (i2 || i3) {
            return i2 && i3 && this.c == ioVar.c;
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1073a();
        jdVar.a(f);
        if (this.f168a != null && m1074a()) {
            jdVar.a(g);
            jdVar.a(this.f168a);
            jdVar.b();
        }
        if (this.f167a != null && b()) {
            jdVar.a(h);
            this.f167a.b(jdVar);
            jdVar.b();
        }
        if (this.f170b != null) {
            jdVar.a(i);
            jdVar.a(this.f170b);
            jdVar.b();
        }
        if (this.f171c != null) {
            jdVar.a(j);
            jdVar.a(this.f171c);
            jdVar.b();
        }
        jdVar.a(k);
        jdVar.a(this.a);
        jdVar.b();
        if (this.d != null && f()) {
            jdVar.a(l);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (this.e != null && g()) {
            jdVar.a(m);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (h()) {
            jdVar.a(n);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (i()) {
            jdVar.a(o);
            jdVar.a(this.c);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f169a.set(1, z);
    }

    public boolean b() {
        return this.f167a != null;
    }

    public void c(boolean z) {
        this.f169a.set(2, z);
    }

    public boolean c() {
        return this.f170b != null;
    }

    public boolean d() {
        return this.f171c != null;
    }

    public boolean e() {
        return this.f169a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof io)) {
            return m1075a((io) obj);
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
        return this.f169a.get(1);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f169a.get(2);
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistrationResult(");
        if (m1074a()) {
            sb.append("debug:");
            String str = this.f168a;
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
            hw hwVar = this.f167a;
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
        String str2 = this.f170b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", ");
        sb.append("appId:");
        String str3 = this.f171c;
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
            sb.append("unRegisteredAt:");
            sb.append(this.b);
        }
        if (i()) {
            sb.append(", ");
            sb.append("costTime:");
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
