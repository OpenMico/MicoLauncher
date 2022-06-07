package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.xiaomi.push.if */
/* loaded from: classes4.dex */
public class Cif implements is<Cif, Object>, Serializable, Cloneable {
    private static final ji b = new ji("XmPushActionNormalConfig");
    private static final ja c = new ja("", (byte) 15, 1);
    public List<hq> a;

    /* renamed from: a */
    public int compareTo(Cif ifVar) {
        int a;
        if (!getClass().equals(ifVar.getClass())) {
            return getClass().getName().compareTo(ifVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1034a()).compareTo(Boolean.valueOf(ifVar.m1034a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (!m1034a() || (a = it.a(this.a, ifVar.a)) == 0) {
            return 0;
        }
        return a;
    }

    public List<hq> a() {
        return this.a;
    }

    /* renamed from: a */
    public void m1033a() {
        if (this.a == null) {
            throw new je("Required field 'normalConfigs' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m1033a();
                return;
            }
            if (a.f180a == 1 && a.a == 15) {
                jb a2 = jdVar.m1096a();
                this.a = new ArrayList(a2.f181a);
                for (int i = 0; i < a2.f181a; i++) {
                    hq hqVar = new hq();
                    hqVar.a(jdVar);
                    this.a.add(hqVar);
                }
                jdVar.i();
            } else {
                jg.a(jdVar, a.a);
            }
            jdVar.g();
        }
    }

    /* renamed from: a */
    public boolean m1034a() {
        return this.a != null;
    }

    /* renamed from: a */
    public boolean m1035a(Cif ifVar) {
        if (ifVar == null) {
            return false;
        }
        boolean a = m1034a();
        boolean a2 = ifVar.m1034a();
        if (a || a2) {
            return a && a2 && this.a.equals(ifVar.a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1033a();
        jdVar.a(b);
        if (this.a != null) {
            jdVar.a(c);
            jdVar.a(new jb((byte) 12, this.a.size()));
            for (hq hqVar : this.a) {
                hqVar.b(jdVar);
            }
            jdVar.e();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof Cif)) {
            return m1035a((Cif) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionNormalConfig(");
        sb.append("normalConfigs:");
        List<hq> list = this.a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
