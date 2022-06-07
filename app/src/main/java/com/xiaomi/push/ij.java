package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class ij implements is<ij, Object>, Serializable, Cloneable {
    private static final ji f = new ji("XmPushActionSendFeedbackResult");
    private static final ja g = new ja("", (byte) 11, 1);
    private static final ja h = new ja("", (byte) 12, 2);
    private static final ja i = new ja("", (byte) 11, 3);
    private static final ja j = new ja("", (byte) 11, 4);
    private static final ja k = new ja("", (byte) 10, 6);
    private static final ja l = new ja("", (byte) 11, 7);
    private static final ja m = new ja("", (byte) 11, 8);
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hw f150a;

    /* renamed from: a  reason: collision with other field name */
    public String f151a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f152a = new BitSet(1);
    public String b;
    public String c;
    public String d;
    public String e;

    /* renamed from: a */
    public int compareTo(ij ijVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        if (!getClass().equals(ijVar.getClass())) {
            return getClass().getName().compareTo(ijVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1054a()).compareTo(Boolean.valueOf(ijVar.m1054a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1054a() && (a7 = it.a(this.f151a, ijVar.f151a)) != 0) {
            return a7;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ijVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a6 = it.a(this.f150a, ijVar.f150a)) != 0) {
            return a6;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ijVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a5 = it.a(this.b, ijVar.b)) != 0) {
            return a5;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ijVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a4 = it.a(this.c, ijVar.c)) != 0) {
            return a4;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ijVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a3 = it.a(this.a, ijVar.a)) != 0) {
            return a3;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ijVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a2 = it.a(this.d, ijVar.d)) != 0) {
            return a2;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ijVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (!g() || (a = it.a(this.e, ijVar.e)) == 0) {
            return 0;
        }
        return a;
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
                throw new je("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f151a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 12) {
                        this.f150a = new hw();
                        this.f150a.a(jdVar);
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
            }
            jg.a(jdVar, a.a);
            jdVar.g();
        }
    }

    public void a(boolean z) {
        this.f152a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1054a() {
        return this.f151a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1055a(ij ijVar) {
        if (ijVar == null) {
            return false;
        }
        boolean a = m1054a();
        boolean a2 = ijVar.m1054a();
        if ((a || a2) && (!a || !a2 || !this.f151a.equals(ijVar.f151a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = ijVar.b();
        if ((b || b2) && (!b || !b2 || !this.f150a.m1000a(ijVar.f150a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = ijVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(ijVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = ijVar.d();
        if (((d || d2) && (!d || !d2 || !this.c.equals(ijVar.c))) || this.a != ijVar.a) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = ijVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.d.equals(ijVar.d))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = ijVar.g();
        if (g2 || g3) {
            return g2 && g3 && this.e.equals(ijVar.e);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(f);
        if (this.f151a != null && m1054a()) {
            jdVar.a(g);
            jdVar.a(this.f151a);
            jdVar.b();
        }
        if (this.f150a != null && b()) {
            jdVar.a(h);
            this.f150a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(i);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null) {
            jdVar.a(j);
            jdVar.a(this.c);
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
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean b() {
        return this.f150a != null;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.f152a.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ij)) {
            return m1055a((ij) obj);
        }
        return false;
    }

    public boolean f() {
        return this.d != null;
    }

    public boolean g() {
        return this.e != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionSendFeedbackResult(");
        if (m1054a()) {
            sb.append("debug:");
            String str = this.f151a;
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
            hw hwVar = this.f150a;
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
            sb.append("category:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        sb.append(")");
        return sb.toString();
    }
}
