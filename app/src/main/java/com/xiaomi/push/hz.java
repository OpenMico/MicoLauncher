package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class hz implements is<hz, Object>, Serializable, Cloneable {
    private static final ji c = new ji("XmPushActionCheckClientInfo");
    private static final ja d = new ja("", (byte) 8, 1);
    private static final ja e = new ja("", (byte) 8, 2);
    public int a;

    /* renamed from: a  reason: collision with other field name */
    private BitSet f106a = new BitSet(2);
    public int b;

    /* renamed from: a */
    public int compareTo(hz hzVar) {
        int a;
        int a2;
        if (!getClass().equals(hzVar.getClass())) {
            return getClass().getName().compareTo(hzVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1007a()).compareTo(Boolean.valueOf(hzVar.m1007a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1007a() && (a2 = it.a(this.a, hzVar.a)) != 0) {
            return a2;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hzVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (!b() || (a = it.a(this.b, hzVar.b)) == 0) {
            return 0;
        }
        return a;
    }

    public hz a(int i) {
        this.a = i;
        a(true);
        return this;
    }

    public void a() {
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (!m1007a()) {
                    throw new je("Required field 'miscConfigVersion' was not found in serialized data! Struct: " + toString());
                } else if (b()) {
                    a();
                    return;
                } else {
                    throw new je("Required field 'pluginConfigVersion' was not found in serialized data! Struct: " + toString());
                }
            } else {
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
                }
                jg.a(jdVar, a.a);
                jdVar.g();
            }
        }
    }

    public void a(boolean z) {
        this.f106a.set(0, z);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1007a() {
        return this.f106a.get(0);
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1008a(hz hzVar) {
        return hzVar != null && this.a == hzVar.a && this.b == hzVar.b;
    }

    public hz b(int i) {
        this.b = i;
        b(true);
        return this;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(c);
        jdVar.a(d);
        jdVar.mo1091a(this.a);
        jdVar.b();
        jdVar.a(e);
        jdVar.mo1091a(this.b);
        jdVar.b();
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f106a.set(1, z);
    }

    public boolean b() {
        return this.f106a.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hz)) {
            return m1008a((hz) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "XmPushActionCheckClientInfo(miscConfigVersion:" + this.a + ", pluginConfigVersion:" + this.b + ")";
    }
}
