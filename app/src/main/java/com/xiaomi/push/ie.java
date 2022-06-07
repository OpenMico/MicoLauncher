package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ie implements is<ie, Object>, Serializable, Cloneable {
    private static final ji b = new ji("XmPushActionCustomConfig");
    private static final ja c = new ja("", (byte) 15, 1);
    public List<hs> a;

    /* renamed from: a */
    public int compareTo(ie ieVar) {
        int a;
        if (!getClass().equals(ieVar.getClass())) {
            return getClass().getName().compareTo(ieVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1031a()).compareTo(Boolean.valueOf(ieVar.m1031a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (!m1031a() || (a = it.a(this.a, ieVar.a)) == 0) {
            return 0;
        }
        return a;
    }

    public List<hs> a() {
        return this.a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m1030a() {
        if (this.a == null) {
            throw new je("Required field 'customConfigs' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m1030a();
                return;
            }
            if (a.f180a == 1 && a.a == 15) {
                jb a2 = jdVar.m1096a();
                this.a = new ArrayList(a2.f181a);
                for (int i = 0; i < a2.f181a; i++) {
                    hs hsVar = new hs();
                    hsVar.a(jdVar);
                    this.a.add(hsVar);
                }
                jdVar.i();
            } else {
                jg.a(jdVar, a.a);
            }
            jdVar.g();
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1031a() {
        return this.a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1032a(ie ieVar) {
        if (ieVar == null) {
            return false;
        }
        boolean a = m1031a();
        boolean a2 = ieVar.m1031a();
        if (a || a2) {
            return a && a2 && this.a.equals(ieVar.a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1030a();
        jdVar.a(b);
        if (this.a != null) {
            jdVar.a(c);
            jdVar.a(new jb((byte) 12, this.a.size()));
            for (hs hsVar : this.a) {
                hsVar.b(jdVar);
            }
            jdVar.e();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ie)) {
            return m1032a((ie) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCustomConfig(");
        sb.append("customConfigs:");
        List<hs> list = this.a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
