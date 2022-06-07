package com.xiaomi.push;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class fe implements is<fe, Object>, Serializable, Cloneable {
    private static final ji c = new ji("StatsEvents");
    private static final ja d = new ja("", (byte) 11, 1);
    private static final ja e = new ja("", (byte) 11, 2);
    private static final ja f = new ja("", (byte) 15, 3);
    public String a;

    /* renamed from: a  reason: collision with other field name */
    public List<fd> f34a;
    public String b;

    public fe() {
    }

    public fe(String str, List<fd> list) {
        this();
        this.a = str;
        this.f34a = list;
    }

    /* renamed from: a */
    public int compareTo(fe feVar) {
        int a;
        int a2;
        int a3;
        if (!getClass().equals(feVar.getClass())) {
            return getClass().getName().compareTo(feVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m904a()).compareTo(Boolean.valueOf(feVar.m904a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m904a() && (a3 = it.a(this.a, feVar.a)) != 0) {
            return a3;
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(feVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b() && (a2 = it.a(this.b, feVar.b)) != 0) {
            return a2;
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(feVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (!c() || (a = it.a(this.f34a, feVar.f34a)) == 0) {
            return 0;
        }
        return a;
    }

    public fe a(String str) {
        this.b = str;
        return this;
    }

    public void a() {
        if (this.a == null) {
            throw new je("Required field 'uuid' was not present! Struct: " + toString());
        } else if (this.f34a == null) {
            throw new je("Required field 'events' was not present! Struct: " + toString());
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
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.a = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 2:
                    if (a.a == 11) {
                        this.b = jdVar.m1100a();
                        continue;
                        jdVar.g();
                    }
                    break;
                case 3:
                    if (a.a == 15) {
                        jb a2 = jdVar.m1096a();
                        this.f34a = new ArrayList(a2.f181a);
                        for (int i = 0; i < a2.f181a; i++) {
                            fd fdVar = new fd();
                            fdVar.a(jdVar);
                            this.f34a.add(fdVar);
                        }
                        jdVar.i();
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
    public boolean m904a() {
        return this.a != null;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m905a(fe feVar) {
        if (feVar == null) {
            return false;
        }
        boolean a = m904a();
        boolean a2 = feVar.m904a();
        if ((a || a2) && (!a || !a2 || !this.a.equals(feVar.a))) {
            return false;
        }
        boolean b = b();
        boolean b2 = feVar.b();
        if ((b || b2) && (!b || !b2 || !this.b.equals(feVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = feVar.c();
        if (c2 || c3) {
            return c2 && c3 && this.f34a.equals(feVar.f34a);
        }
        return true;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        a();
        jdVar.a(c);
        if (this.a != null) {
            jdVar.a(d);
            jdVar.a(this.a);
            jdVar.b();
        }
        if (this.b != null && b()) {
            jdVar.a(e);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.f34a != null) {
            jdVar.a(f);
            jdVar.a(new jb((byte) 12, this.f34a.size()));
            for (fd fdVar : this.f34a) {
                fdVar.b(jdVar);
            }
            jdVar.e();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.f34a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof fe)) {
            return m905a((fe) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvents(");
        sb.append("uuid:");
        String str = this.a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        if (b()) {
            sb.append(", ");
            sb.append("operator:");
            String str2 = this.b;
            if (str2 == null) {
                str2 = "null";
            }
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("events:");
        List<fd> list = this.f34a;
        if (list == null) {
            sb.append("null");
        } else {
            sb.append(list);
        }
        sb.append(")");
        return sb.toString();
    }
}
