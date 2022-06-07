package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class hp implements is<hp, Object>, Serializable, Cloneable {
    private static final ji b = new ji("DataCollectionItem");
    private static final ja c = new ja("", (byte) 10, 1);
    private static final ja d = new ja("", (byte) 8, 2);
    private static final ja e = new ja("", (byte) 11, 3);
    public long a;

    /* renamed from: a */
    public hj f61a;

    /* renamed from: a */
    public String f62a;

    /* renamed from: a */
    private BitSet f63a = new BitSet(1);

    /* renamed from: a */
    public int compareTo(hp hpVar) {
        int a;
        int a2;
        int a3;
        if (!getClass().equals(hpVar.getClass())) {
            return getClass().getName().compareTo(hpVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m967a()).compareTo(Boolean.valueOf(hpVar.m967a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m967a() && (a3 = it.a(this.a, hpVar.a)) != 0) {
            return a3;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(hpVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a2 = it.a(this.f61a, hpVar.f61a)) != 0) {
            return a2;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(hpVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (!c() || (a = it.a(this.f62a, hpVar.f62a)) == 0) {
            return 0;
        }
        return a;
    }

    public hp a(long j) {
        this.a = j;
        a(true);
        return this;
    }

    public hp a(hj hjVar) {
        this.f61a = hjVar;
        return this;
    }

    public hp a(String str) {
        this.f62a = str;
        return this;
    }

    public String a() {
        return this.f62a;
    }

    /* renamed from: a */
    public void m966a() {
        if (this.f61a == null) {
            throw new je("Required field 'collectionType' was not present! Struct: " + toString());
        } else if (this.f62a == null) {
            throw new je("Required field 'content' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (m967a()) {
                    m966a();
                    return;
                }
                throw new je("Required field 'collectedAt' was not found in serialized data! Struct: " + toString());
            }
            switch (a.f180a) {
                case 1:
                    if (a.a == 10) {
                        this.a = jdVar.m1094a();
                        a(true);
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 8) {
                        this.f61a = hj.a(jdVar.m1093a());
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f62a = jdVar.m1100a();
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
        this.f63a.set(0, z);
    }

    /* renamed from: a */
    public boolean m967a() {
        return this.f63a.get(0);
    }

    /* renamed from: a */
    public boolean m968a(hp hpVar) {
        if (hpVar == null || this.a != hpVar.a) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = hpVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.f61a.equals(hpVar.f61a))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = hpVar.c();
        if (c2 || c3) {
            return c2 && c3 && this.f62a.equals(hpVar.f62a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m966a();
        jdVar.a(b);
        jdVar.a(c);
        jdVar.a(this.a);
        jdVar.b();
        if (this.f61a != null) {
            jdVar.a(d);
            jdVar.mo1091a(this.f61a.a());
            jdVar.b();
        }
        if (this.f62a != null) {
            jdVar.a(e);
            jdVar.a(this.f62a);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean b() {
        return this.f61a != null;
    }

    public boolean c() {
        return this.f62a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hp)) {
            return m968a((hp) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataCollectionItem(");
        sb.append("collectedAt:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("collectionType:");
        hj hjVar = this.f61a;
        if (hjVar == null) {
            sb.append("null");
        } else {
            sb.append(hjVar);
        }
        sb.append(", ");
        sb.append("content:");
        String str = this.f62a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        sb.append(")");
        return sb.toString();
    }
}
