package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/* loaded from: classes4.dex */
public class ib implements is<ib, Object>, Serializable, Cloneable {
    private static final ji f = new ji("XmPushActionCommand");
    private static final ja g = new ja("", (byte) 12, 2);
    private static final ja h = new ja("", (byte) 11, 3);
    private static final ja i = new ja("", (byte) 11, 4);
    private static final ja j = new ja("", (byte) 11, 5);
    private static final ja k = new ja("", (byte) 15, 6);
    private static final ja l = new ja("", (byte) 11, 7);
    private static final ja m = new ja("", (byte) 11, 9);
    private static final ja n = new ja("", (byte) 2, 10);
    private static final ja o = new ja("", (byte) 2, 11);
    private static final ja p = new ja("", (byte) 10, 12);
    public long a;

    /* renamed from: a  reason: collision with other field name */
    public hw f107a;

    /* renamed from: a  reason: collision with other field name */
    public String f108a;

    /* renamed from: a  reason: collision with other field name */
    public List<String> f110a;
    public String b;
    public String c;
    public String d;
    public String e;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f109a = new BitSet(3);

    /* renamed from: a  reason: collision with other field name */
    public boolean f111a = false;

    /* renamed from: b  reason: collision with other field name */
    public boolean f112b = true;

    /* renamed from: a */
    public int compareTo(ib ibVar) {
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
        if (!getClass().equals(ibVar.getClass())) {
            return getClass().getName().compareTo(ibVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1014a()).compareTo(Boolean.valueOf(ibVar.m1014a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1014a() && (a10 = it.a(this.f107a, ibVar.f107a)) != 0) {
            return a10;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ibVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a9 = it.a(this.f108a, ibVar.f108a)) != 0) {
            return a9;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ibVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c() && (a8 = it.a(this.b, ibVar.b)) != 0) {
            return a8;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ibVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a7 = it.a(this.c, ibVar.c)) != 0) {
            return a7;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ibVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a6 = it.a(this.f110a, ibVar.f110a)) != 0) {
            return a6;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ibVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a5 = it.a(this.d, ibVar.d)) != 0) {
            return a5;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ibVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a4 = it.a(this.e, ibVar.e)) != 0) {
            return a4;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ibVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a3 = it.a(this.f111a, ibVar.f111a)) != 0) {
            return a3;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ibVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a2 = it.a(this.f112b, ibVar.f112b)) != 0) {
            return a2;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ibVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (!j() || (a = it.a(this.a, ibVar.a)) == 0) {
            return 0;
        }
        return a;
    }

    public ib a(String str) {
        this.f108a = str;
        return this;
    }

    public ib a(List<String> list) {
        this.f110a = list;
        return this;
    }

    public String a() {
        return this.c;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1012a() {
        if (this.f108a == null) {
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
                m1012a();
                return;
            }
            switch (a.f180a) {
                case 2:
                    if (a.a == 12) {
                        this.f107a = new hw();
                        this.f107a.a(jdVar);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f108a = jdVar.m1100a();
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
                case 6:
                    if (a.a == 15) {
                        jb a2 = jdVar.m1096a();
                        this.f110a = new ArrayList(a2.f181a);
                        for (int i2 = 0; i2 < a2.f181a; i2++) {
                            this.f110a.add(jdVar.m1100a());
                        }
                        jdVar.i();
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
                case 9:
                    if (a.a == 11) {
                        this.e = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 10:
                    if (a.a == 2) {
                        this.f111a = jdVar.m1104a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 11:
                    if (a.a == 2) {
                        this.f112b = jdVar.m1104a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 12:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
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

    /* renamed from: a  reason: collision with other method in class */
    public void m1013a(String str) {
        if (this.f110a == null) {
            this.f110a = new ArrayList();
        }
        this.f110a.add(str);
    }

    public void a(boolean z) {
        this.f109a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1014a() {
        return this.f107a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1015a(ib ibVar) {
        if (ibVar == null) {
            return false;
        }
        boolean a = m1014a();
        boolean a2 = ibVar.m1014a();
        if ((a || a2) && (!a || !a2 || !this.f107a.m1000a(ibVar.f107a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = ibVar.b();
        if ((b || b2) && (!b || !b2 || !this.f108a.equals(ibVar.f108a))) {
            return false;
        }
        boolean c = c();
        boolean c2 = ibVar.c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(ibVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = ibVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(ibVar.c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = ibVar.e();
        if ((e || e2) && (!e || !e2 || !this.f110a.equals(ibVar.f110a))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = ibVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.d.equals(ibVar.d))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = ibVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.e.equals(ibVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = ibVar.h();
        if ((h2 || h3) && (!h2 || !h3 || this.f111a != ibVar.f111a)) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = ibVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.f112b != ibVar.f112b)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = ibVar.j();
        if (j2 || j3) {
            return j2 && j3 && this.a == ibVar.a;
        }
        return true;
    }

    public ib b(String str) {
        this.b = str;
        return this;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1012a();
        jdVar.a(f);
        if (this.f107a != null && m1014a()) {
            jdVar.a(g);
            this.f107a.b(jdVar);
            jdVar.b();
        }
        if (this.f108a != null) {
            jdVar.a(h);
            jdVar.a(this.f108a);
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
        if (this.f110a != null && e()) {
            jdVar.a(k);
            jdVar.a(new jb((byte) 11, this.f110a.size()));
            for (String str : this.f110a) {
                jdVar.a(str);
            }
            jdVar.e();
            jdVar.b();
        }
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
            jdVar.a(this.f111a);
            jdVar.b();
        }
        if (i()) {
            jdVar.a(o);
            jdVar.a(this.f112b);
            jdVar.b();
        }
        if (j()) {
            jdVar.a(p);
            jdVar.a(this.a);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f109a.set(1, z);
    }

    public boolean b() {
        return this.f108a != null;
    }

    public ib c(String str) {
        this.c = str;
        return this;
    }

    public void c(boolean z) {
        this.f109a.set(2, z);
    }

    public boolean c() {
        return this.b != null;
    }

    public ib d(String str) {
        this.d = str;
        return this;
    }

    public boolean d() {
        return this.c != null;
    }

    public ib e(String str) {
        this.e = str;
        return this;
    }

    public boolean e() {
        return this.f110a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ib)) {
            return m1015a((ib) obj);
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
        return this.f109a.get(0);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f109a.get(1);
    }

    public boolean j() {
        return this.f109a.get(2);
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionCommand(");
        if (m1014a()) {
            sb.append("target:");
            hw hwVar = this.f107a;
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
        String str = this.f108a;
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
        if (e()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            List<String> list = this.f110a;
            if (list == null) {
                sb.append("null");
            } else {
                sb.append(list);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
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
        if (h()) {
            sb.append(", ");
            sb.append("updateCache:");
            sb.append(this.f111a);
        }
        if (i()) {
            sb.append(", ");
            sb.append("response2Client:");
            sb.append(this.f112b);
        }
        if (j()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
