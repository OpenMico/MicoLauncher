package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class hk implements is<hk, Object>, Serializable, Cloneable {
    private static final ji b = new ji("ClientUploadData");
    private static final ja c = new ja("", (byte) 15, 1);
    public List<hl> a;

    public int a() {
        List<hl> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* renamed from: a */
    public int compareTo(hk hkVar) {
        int a;
        if (!getClass().equals(hkVar.getClass())) {
            return getClass().getName().compareTo(hkVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m955a()).compareTo(Boolean.valueOf(hkVar.m955a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (!m955a() || (a = it.a(this.a, hkVar.a)) == 0) {
            return 0;
        }
        return a;
    }

    /* renamed from: a  reason: collision with other method in class */
    public void m954a() {
        if (this.a == null) {
            throw new je("Required field 'uploadDataItems' was not present! Struct: " + toString());
        }
    }

    public void a(hl hlVar) {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.add(hlVar);
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                m954a();
                return;
            }
            if (a.f180a == 1 && a.a == 15) {
                jb a2 = jdVar.m1096a();
                this.a = new ArrayList(a2.f181a);
                for (int i = 0; i < a2.f181a; i++) {
                    hl hlVar = new hl();
                    hlVar.a(jdVar);
                    this.a.add(hlVar);
                }
                jdVar.i();
            } else {
                jg.a(jdVar, a.a);
            }
            jdVar.g();
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m955a() {
        return this.a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m956a(hk hkVar) {
        if (hkVar == null) {
            return false;
        }
        boolean a = m955a();
        boolean a2 = hkVar.m955a();
        if (a || a2) {
            return a && a2 && this.a.equals(hkVar.a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m954a();
        jdVar.a(b);
        if (this.a != null) {
            jdVar.a(c);
            jdVar.a(new jb((byte) 12, this.a.size()));
            for (hl hlVar : this.a) {
                hlVar.b(jdVar);
            }
            jdVar.e();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hk)) {
            return m956a((hk) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ClientUploadData(");
        sb.append("uploadDataItems:");
        List<hl> list = this.a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
