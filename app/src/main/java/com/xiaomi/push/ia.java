package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ia implements is<ia, Object>, Serializable, Cloneable {
    private static final ji b = new ji("XmPushActionCollectData");
    private static final ja c = new ja("", (byte) 15, 1);
    public List<hp> a;

    /* renamed from: a */
    public int compareTo(ia iaVar) {
        int a;
        if (!getClass().equals(iaVar.getClass())) {
            return getClass().getName().compareTo(iaVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1010a()).compareTo(Boolean.valueOf(iaVar.m1010a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (!m1010a() || (a = it.a(this.a, iaVar.a)) == 0) {
            return 0;
        }
        return a;
    }

    public ia a(List<hp> list) {
        this.a = list;
        return this;
    }

    public void a() {
        if (this.a == null) {
            throw new je("Required field 'dataCollectionItems' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                a();
                return;
            }
            if (a.f180a == 1 && a.a == 15) {
                jb a2 = jdVar.m1096a();
                this.a = new ArrayList(a2.f181a);
                for (int i = 0; i < a2.f181a; i++) {
                    hp hpVar = new hp();
                    hpVar.a(jdVar);
                    this.a.add(hpVar);
                }
                jdVar.i();
            } else {
                jg.a(jdVar, a.a);
            }
            jdVar.g();
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1010a() {
        return this.a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1011a(ia iaVar) {
        if (iaVar == null) {
            return false;
        }
        boolean a = m1010a();
        boolean a2 = iaVar.m1010a();
        if (a || a2) {
            return a && a2 && this.a.equals(iaVar.a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(b);
        if (this.a != null) {
            jdVar.a(c);
            jdVar.a(new jb((byte) 12, this.a.size()));
            for (hp hpVar : this.a) {
                hpVar.b(jdVar);
            }
            jdVar.e();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ia)) {
            return m1011a((ia) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCollectData(");
        sb.append("dataCollectionItems:");
        List<hp> list = this.a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
