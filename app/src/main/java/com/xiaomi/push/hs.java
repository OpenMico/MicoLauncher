package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class hs implements is<hs, Object>, Serializable, Cloneable {
    private static final ji d = new ji("OnlineConfigItem");
    private static final ja e = new ja("", (byte) 8, 1);
    private static final ja f = new ja("", (byte) 8, 2);
    private static final ja g = new ja("", (byte) 2, 3);
    private static final ja h = new ja("", (byte) 8, 4);
    private static final ja i = new ja("", (byte) 10, 5);
    private static final ja j = new ja("", (byte) 11, 6);
    private static final ja k = new ja("", (byte) 2, 7);
    public int a;

    /* renamed from: a  reason: collision with other field name */
    public long f68a;

    /* renamed from: a  reason: collision with other field name */
    public String f69a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f70a = new BitSet(6);

    /* renamed from: a  reason: collision with other field name */
    public boolean f71a;
    public int b;

    /* renamed from: b  reason: collision with other field name */
    public boolean f72b;
    public int c;

    public int a() {
        return this.a;
    }

    /* renamed from: a */
    public int compareTo(hs hsVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        if (!getClass().equals(hsVar.getClass())) {
            return getClass().getName().compareTo(hsVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m976a()).compareTo(Boolean.valueOf(hsVar.m976a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m976a() && (a7 = it.a(this.a, hsVar.a)) != 0) {
            return a7;
        }
        int compareTo2 = Boolean.valueOf(m978b()).compareTo(Boolean.valueOf(hsVar.m978b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m978b() && (a6 = it.a(this.b, hsVar.b)) != 0) {
            return a6;
        }
        int compareTo3 = Boolean.valueOf(m979c()).compareTo(Boolean.valueOf(hsVar.m979c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m979c() && (a5 = it.a(this.f71a, hsVar.f71a)) != 0) {
            return a5;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(hsVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a4 = it.a(this.c, hsVar.c)) != 0) {
            return a4;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(hsVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a3 = it.a(this.f68a, hsVar.f68a)) != 0) {
            return a3;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(hsVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a2 = it.a(this.f69a, hsVar.f69a)) != 0) {
            return a2;
        }
        int compareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(hsVar.h()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (!h() || (a = it.a(this.f72b, hsVar.f72b)) == 0) {
            return 0;
        }
        return a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public long m973a() {
        return this.f68a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public String m974a() {
        return this.f69a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m975a() {
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m975a();
                return;
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 8) {
                        this.a = jdVar.m1093a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 8) {
                        this.b = jdVar.m1093a();
                        b(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 2) {
                        this.f71a = jdVar.m1104a();
                        c(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 4:
                    if (a.a == 8) {
                        this.c = jdVar.m1093a();
                        d(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 5:
                    if (a.a == 10) {
                        this.f68a = jdVar.m1094a();
                        e(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 6:
                    if (a.a == 11) {
                        this.f69a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 7:
                    if (a.a == 2) {
                        this.f72b = jdVar.m1104a();
                        f(true);
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
        this.f70a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m976a() {
        return this.f70a.get(0);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m977a(hs hsVar) {
        if (hsVar == null) {
            return false;
        }
        boolean a = m976a();
        boolean a2 = hsVar.m976a();
        if ((a || a2) && (!a || !a2 || this.a != hsVar.a)) {
            return false;
        }
        boolean b = m978b();
        boolean b2 = hsVar.m978b();
        if ((b || b2) && (!b || !b2 || this.b != hsVar.b)) {
            return false;
        }
        boolean c = m979c();
        boolean c2 = hsVar.m979c();
        if ((c || c2) && (!c || !c2 || this.f71a != hsVar.f71a)) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = hsVar.d();
        if ((d2 || d3) && (!d2 || !d3 || this.c != hsVar.c)) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = hsVar.e();
        if ((e2 || e3) && (!e2 || !e3 || this.f68a != hsVar.f68a)) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = hsVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.f69a.equals(hsVar.f69a))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = hsVar.h();
        if (h2 || h3) {
            return h2 && h3 && this.f72b == hsVar.f72b;
        }
        return true;
    }

    public int b() {
        return this.b;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m975a();
        jdVar.a(d);
        if (m976a()) {
            jdVar.a(e);
            jdVar.mo1091a(this.a);
            jdVar.b();
        }
        if (m978b()) {
            jdVar.a(f);
            jdVar.mo1091a(this.b);
            jdVar.b();
        }
        if (m979c()) {
            jdVar.a(g);
            jdVar.a(this.f71a);
            jdVar.b();
        }
        if (d()) {
            jdVar.a(h);
            jdVar.mo1091a(this.c);
            jdVar.b();
        }
        if (e()) {
            jdVar.a(i);
            jdVar.a(this.f68a);
            jdVar.b();
        }
        if (this.f69a != null && f()) {
            jdVar.a(j);
            jdVar.a(this.f69a);
            jdVar.b();
        }
        if (h()) {
            jdVar.a(k);
            jdVar.a(this.f72b);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f70a.set(1, z);
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m978b() {
        return this.f70a.get(1);
    }

    public int c() {
        return this.c;
    }

    public void c(boolean z) {
        this.f70a.set(2, z);
    }

    /* renamed from: c  reason: collision with other method in class */
    public boolean m979c() {
        return this.f70a.get(2);
    }

    public void d(boolean z) {
        this.f70a.set(3, z);
    }

    public boolean d() {
        return this.f70a.get(3);
    }

    public void e(boolean z) {
        this.f70a.set(4, z);
    }

    public boolean e() {
        return this.f70a.get(4);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hs)) {
            return m977a((hs) obj);
        }
        return false;
    }

    public void f(boolean z) {
        this.f70a.set(5, z);
    }

    public boolean f() {
        return this.f69a != null;
    }

    public boolean g() {
        return this.f72b;
    }

    public boolean h() {
        return this.f70a.get(5);
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("OnlineConfigItem(");
        if (m976a()) {
            sb.append("key:");
            sb.append(this.a);
            z = false;
        } else {
            z = true;
        }
        if (m978b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("type:");
            sb.append(this.b);
            z = false;
        }
        if (m979c()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("clear:");
            sb.append(this.f71a);
            z = false;
        }
        if (d()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("intValue:");
            sb.append(this.c);
            z = false;
        }
        if (e()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("longValue:");
            sb.append(this.f68a);
            z = false;
        }
        if (f()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("stringValue:");
            String str = this.f69a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z = false;
        }
        if (h()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("boolValue:");
            sb.append(this.f72b);
        }
        sb.append(")");
        return sb.toString();
    }
}
