package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/* loaded from: classes4.dex */
public class hq implements is<hq, Object>, Serializable, Cloneable {
    private static final ji b = new ji("NormalConfig");
    private static final ja c = new ja("", (byte) 8, 1);
    private static final ja d = new ja("", (byte) 15, 2);
    private static final ja e = new ja("", (byte) 8, 3);
    public int a;

    /* renamed from: a */
    public hn f64a;

    /* renamed from: a */
    private BitSet f65a = new BitSet(1);

    /* renamed from: a */
    public List<hs> f66a;

    public int a() {
        return this.a;
    }

    /* renamed from: a */
    public int compareTo(hq hqVar) {
        int a;
        int a2;
        int a3;
        if (!getClass().equals(hqVar.getClass())) {
            return getClass().getName().compareTo(hqVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m971a()).compareTo(Boolean.valueOf(hqVar.m971a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m971a() && (a3 = it.a(this.a, hqVar.a)) != 0) {
            return a3;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hqVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a2 = it.a(this.f66a, hqVar.f66a)) != 0) {
            return a2;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(hqVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (!c() || (a = it.a(this.f64a, hqVar.f64a)) == 0) {
            return 0;
        }
        return a;
    }

    /* renamed from: a */
    public hn m969a() {
        return this.f64a;
    }

    /* renamed from: a */
    public void m970a() {
        if (this.f66a == null) {
            throw new je("Required field 'configItems' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (m971a()) {
                    m970a();
                    return;
                }
                throw new je("Required field 'version' was not found in serialized data! Struct: " + toString());
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
                    if (a.a == 15) {
                        jb a2 = jdVar.m1096a();
                        this.f66a = new ArrayList(a2.f181a);
                        for (int i = 0; i < a2.f181a; i++) {
                            hs hsVar = new hs();
                            hsVar.a(jdVar);
                            this.f66a.add(hsVar);
                        }
                        jdVar.i();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 8) {
                        this.f64a = hn.a(jdVar.m1093a());
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
        this.f65a.set(0, z);
    }

    /* renamed from: a */
    public boolean m971a() {
        return this.f65a.get(0);
    }

    /* renamed from: a */
    public boolean m972a(hq hqVar) {
        if (hqVar == null || this.a != hqVar.a) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = hqVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.f66a.equals(hqVar.f66a))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = hqVar.c();
        if (c2 || c3) {
            return c2 && c3 && this.f64a.equals(hqVar.f64a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m970a();
        jdVar.a(b);
        jdVar.a(c);
        jdVar.mo1091a(this.a);
        jdVar.b();
        if (this.f66a != null) {
            jdVar.a(d);
            jdVar.a(new jb((byte) 12, this.f66a.size()));
            for (hs hsVar : this.f66a) {
                hsVar.b(jdVar);
            }
            jdVar.e();
            jdVar.b();
        }
        if (this.f64a != null && c()) {
            jdVar.a(e);
            jdVar.mo1091a(this.f64a.a());
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean b() {
        return this.f66a != null;
    }

    public boolean c() {
        return this.f64a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hq)) {
            return m972a((hq) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NormalConfig(");
        sb.append("version:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("configItems:");
        List<hs> list = this.f66a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        if (c()) {
            sb.append(", ");
            sb.append("type:");
            hn hnVar = this.f64a;
            if (hnVar == null) {
                sb.append("null");
            } else {
                sb.append(hnVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
