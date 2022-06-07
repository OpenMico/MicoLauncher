package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class hw implements is<hw, Object>, Serializable, Cloneable {
    private static final ji e = new ji("Target");
    private static final ja f = new ja("", (byte) 10, 1);
    private static final ja g = new ja("", (byte) 11, 2);
    private static final ja h = new ja("", (byte) 11, 3);
    private static final ja i = new ja("", (byte) 11, 4);
    private static final ja j = new ja("", (byte) 2, 5);
    private static final ja k = new ja("", (byte) 11, 7);

    /* renamed from: a  reason: collision with other field name */
    public String f90a;
    public String d;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f91a = new BitSet(2);
    public long a = 5;
    public String b = "xiaomi.com";
    public String c = "";

    /* renamed from: a  reason: collision with other field name */
    public boolean f92a = false;

    /* renamed from: a */
    public int compareTo(hw hwVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        if (!getClass().equals(hwVar.getClass())) {
            return getClass().getName().compareTo(hwVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m999a()).compareTo(Boolean.valueOf(hwVar.m999a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m999a() && (a6 = it.a(this.a, hwVar.a)) != 0) {
            return a6;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hwVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a5 = it.a(this.f90a, hwVar.f90a)) != 0) {
            return a5;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(hwVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a4 = it.a(this.b, hwVar.b)) != 0) {
            return a4;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(hwVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a3 = it.a(this.c, hwVar.c)) != 0) {
            return a3;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(hwVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a2 = it.a(this.f92a, hwVar.f92a)) != 0) {
            return a2;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(hwVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (!f() || (a = it.a(this.d, hwVar.d)) == 0) {
            return 0;
        }
        return a;
    }

    public void a() {
        if (this.f90a == null) {
            throw new je("Required field 'userId' was not present! Struct: " + toString());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (m999a()) {
                    a();
                    return;
                }
                throw new je("Required field 'channelId' was not found in serialized data! Struct: " + toString());
            }
            short s = a.f180a;
            if (s != 7) {
                switch (s) {
                    case 1:
                        if (a.a == 10) {
                            this.a = jdVar.m1094a();
                            a(true);
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 2:
                        if (a.a == 11) {
                            this.f90a = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 3:
                        if (a.a == 11) {
                            this.b = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 4:
                        if (a.a == 11) {
                            this.c = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 5:
                        if (a.a == 2) {
                            this.f92a = jdVar.m1104a();
                            b(true);
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    default:
                        jg.a(jdVar, a.a);
                        break;
                }
                jdVar.g();
            } else {
                if (a.a == 11) {
                    this.d = jdVar.m1100a();
                    jdVar.g();
                }
                jg.a(jdVar, a.a);
                jdVar.g();
            }
        }
    }

    public void a(boolean z) {
        this.f91a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m999a() {
        return this.f91a.get(0);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1000a(hw hwVar) {
        if (hwVar == null || this.a != hwVar.a) {
            return false;
        }
        boolean b = b();
        boolean b2 = hwVar.b();
        if ((b || b2) && (!b || !b2 || !this.f90a.equals(hwVar.f90a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = hwVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(hwVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = hwVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(hwVar.c))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = hwVar.e();
        if ((e2 || e3) && (!e2 || !e3 || this.f92a != hwVar.f92a)) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = hwVar.f();
        if (f2 || f3) {
            return f2 && f3 && this.d.equals(hwVar.d);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(e);
        jdVar.a(f);
        jdVar.a(this.a);
        jdVar.b();
        if (this.f90a != null) {
            jdVar.a(g);
            jdVar.a(this.f90a);
            jdVar.b();
        }
        if (this.b != null && c()) {
            jdVar.a(h);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null && d()) {
            jdVar.a(i);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (e()) {
            jdVar.a(j);
            jdVar.a(this.f92a);
            jdVar.b();
        }
        if (this.d != null && f()) {
            jdVar.a(k);
            jdVar.a(this.d);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f91a.set(1, z);
    }

    public boolean b() {
        return this.f90a != null;
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.f91a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hw)) {
            return m1000a((hw) obj);
        }
        return false;
    }

    public boolean f() {
        return this.d != null;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Target(");
        sb.append("channelId:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("userId:");
        String str = this.f90a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        if (c()) {
            sb.append(", ");
            sb.append("server:");
            String str2 = this.b;
            if (str2 == null) {
                str2 = "null";
            }
            sb.append(str2);
        }
        if (d()) {
            sb.append(", ");
            sb.append("resource:");
            String str3 = this.c;
            if (str3 == null) {
                str3 = "null";
            }
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("isPreview:");
            sb.append(this.f92a);
        }
        if (f()) {
            sb.append(", ");
            sb.append("token:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        sb.append(")");
        return sb.toString();
    }
}
