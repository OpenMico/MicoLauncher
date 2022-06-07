package com.xiaomi.push;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ig implements is<ig, Object>, Serializable, Cloneable {
    private static final ji j = new ji("XmPushActionNotification");
    private static final ja k = new ja("", (byte) 11, 1);
    private static final ja l = new ja("", (byte) 12, 2);
    private static final ja m = new ja("", (byte) 11, 3);
    private static final ja n = new ja("", (byte) 11, 4);
    private static final ja o = new ja("", (byte) 11, 5);
    private static final ja p = new ja("", (byte) 2, 6);
    private static final ja q = new ja("", (byte) 11, 7);
    private static final ja r = new ja("", (byte) 13, 8);
    private static final ja s = new ja("", (byte) 11, 9);
    private static final ja t = new ja("", (byte) 11, 10);
    private static final ja u = new ja("", (byte) 11, 12);
    private static final ja v = new ja("", (byte) 11, 13);
    private static final ja w = new ja("", (byte) 11, 14);
    private static final ja x = new ja("", (byte) 10, 15);
    private static final ja y = new ja("", (byte) 2, 20);
    public long a;

    /* renamed from: a */
    public hw f125a;

    /* renamed from: a */
    public String f126a;

    /* renamed from: a */
    public ByteBuffer f127a;

    /* renamed from: a */
    private BitSet f128a;

    /* renamed from: a */
    public Map<String, String> f129a;

    /* renamed from: a */
    public boolean f130a;
    public String b;

    /* renamed from: b */
    public boolean f131b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;

    public ig() {
        this.f128a = new BitSet(3);
        this.f130a = true;
        this.f131b = false;
    }

    public ig(String str, boolean z) {
        this();
        this.b = str;
        this.f130a = z;
        m1038a(true);
    }

    /* renamed from: a */
    public int compareTo(ig igVar) {
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
        int a11;
        int a12;
        int a13;
        int a14;
        int a15;
        if (!getClass().equals(igVar.getClass())) {
            return getClass().getName().compareTo(igVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1039a()).compareTo(Boolean.valueOf(igVar.m1039a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1039a() && (a15 = it.a(this.f126a, igVar.f126a)) != 0) {
            return a15;
        }
        int compareTo2 = Boolean.valueOf(m1042b()).compareTo(Boolean.valueOf(igVar.m1042b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m1042b() && (a14 = it.a(this.f125a, igVar.f125a)) != 0) {
            return a14;
        }
        int compareTo3 = Boolean.valueOf(m1043c()).compareTo(Boolean.valueOf(igVar.m1043c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m1043c() && (a13 = it.a(this.b, igVar.b)) != 0) {
            return a13;
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(igVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d() && (a12 = it.a(this.c, igVar.c)) != 0) {
            return a12;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(igVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a11 = it.a(this.d, igVar.d)) != 0) {
            return a11;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(igVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a10 = it.a(this.f130a, igVar.f130a)) != 0) {
            return a10;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(igVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a9 = it.a(this.e, igVar.e)) != 0) {
            return a9;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(igVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a8 = it.a(this.f129a, igVar.f129a)) != 0) {
            return a8;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(igVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a7 = it.a(this.f, igVar.f)) != 0) {
            return a7;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(igVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a6 = it.a(this.g, igVar.g)) != 0) {
            return a6;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(igVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a5 = it.a(this.h, igVar.h)) != 0) {
            return a5;
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(igVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (l() && (a4 = it.a(this.i, igVar.i)) != 0) {
            return a4;
        }
        int compareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(igVar.m()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (m() && (a3 = it.a(this.f127a, igVar.f127a)) != 0) {
            return a3;
        }
        int compareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(igVar.n()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (n() && (a2 = it.a(this.a, igVar.a)) != 0) {
            return a2;
        }
        int compareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(igVar.o()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (!o() || (a = it.a(this.f131b, igVar.f131b)) == 0) {
            return 0;
        }
        return a;
    }

    public ig a(String str) {
        this.b = str;
        return this;
    }

    public ig a(ByteBuffer byteBuffer) {
        this.f127a = byteBuffer;
        return this;
    }

    public ig a(Map<String, String> map) {
        this.f129a = map;
        return this;
    }

    public ig a(boolean z) {
        this.f130a = z;
        m1038a(true);
        return this;
    }

    public ig a(byte[] bArr) {
        a(ByteBuffer.wrap(bArr));
        return this;
    }

    public String a() {
        return this.b;
    }

    /* renamed from: a */
    public Map<String, String> m1036a() {
        return this.f129a;
    }

    /* renamed from: a */
    public void m1037a() {
        if (this.b == null) {
            throw new je("Required field 'id' was not present! Struct: " + toString());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (f()) {
                    m1037a();
                    return;
                }
                throw new je("Required field 'requireAck' was not found in serialized data! Struct: " + toString());
            }
            short s2 = a.f180a;
            if (s2 != 20) {
                switch (s2) {
                    case 1:
                        if (a.a == 11) {
                            this.f126a = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 2:
                        if (a.a == 12) {
                            this.f125a = new hw();
                            this.f125a.a(jdVar);
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 3:
                        if (a.a == 11) {
                            this.b = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 4:
                        if (a.a == 11) {
                            this.c = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 5:
                        if (a.a == 11) {
                            this.d = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 6:
                        if (a.a == 2) {
                            this.f130a = jdVar.m1104a();
                            m1038a(true);
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 7:
                        if (a.a == 11) {
                            this.e = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 8:
                        if (a.a == 13) {
                            jc a2 = jdVar.m1097a();
                            this.f129a = new HashMap(a2.f182a * 2);
                            for (int i = 0; i < a2.f182a; i++) {
                                this.f129a.put(jdVar.m1100a(), jdVar.m1100a());
                            }
                            jdVar.h();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 9:
                        if (a.a == 11) {
                            this.f = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    case 10:
                        if (a.a == 11) {
                            this.g = jdVar.m1100a();
                            break;
                        }
                        jg.a(jdVar, a.a);
                        break;
                    default:
                        switch (s2) {
                            case 12:
                                if (a.a == 11) {
                                    this.h = jdVar.m1100a();
                                    break;
                                }
                                jg.a(jdVar, a.a);
                                break;
                            case 13:
                                if (a.a == 11) {
                                    this.i = jdVar.m1100a();
                                    break;
                                }
                                jg.a(jdVar, a.a);
                                break;
                            case 14:
                                if (a.a == 11) {
                                    this.f127a = jdVar.m1101a();
                                    break;
                                }
                                jg.a(jdVar, a.a);
                                break;
                            case 15:
                                if (a.a == 10) {
                                    this.a = jdVar.m1094a();
                                    b(true);
                                    break;
                                }
                                jg.a(jdVar, a.a);
                                break;
                            default:
                                jg.a(jdVar, a.a);
                                break;
                        }
                }
                jdVar.g();
            } else {
                if (a.a == 2) {
                    this.f131b = jdVar.m1104a();
                    c(true);
                    jdVar.g();
                }
                jg.a(jdVar, a.a);
                jdVar.g();
            }
        }
    }

    public void a(String str, String str2) {
        if (this.f129a == null) {
            this.f129a = new HashMap();
        }
        this.f129a.put(str, str2);
    }

    /* renamed from: a */
    public void m1038a(boolean z) {
        this.f128a.set(0, z);
    }

    /* renamed from: a */
    public boolean m1039a() {
        return this.f126a != null;
    }

    /* renamed from: a */
    public boolean m1040a(ig igVar) {
        if (igVar == null) {
            return false;
        }
        boolean a = m1039a();
        boolean a2 = igVar.m1039a();
        if ((a || a2) && (!a || !a2 || !this.f126a.equals(igVar.f126a))) {
            return false;
        }
        boolean b = m1042b();
        boolean b2 = igVar.m1042b();
        if ((b || b2) && (!b || !b2 || !this.f125a.m1000a(igVar.f125a))) {
            return false;
        }
        boolean c = m1043c();
        boolean c2 = igVar.m1043c();
        if ((c || c2) && (!c || !c2 || !this.b.equals(igVar.b))) {
            return false;
        }
        boolean d = d();
        boolean d2 = igVar.d();
        if ((d || d2) && (!d || !d2 || !this.c.equals(igVar.c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = igVar.e();
        if (((e || e2) && (!e || !e2 || !this.d.equals(igVar.d))) || this.f130a != igVar.f130a) {
            return false;
        }
        boolean g = g();
        boolean g2 = igVar.g();
        if ((g || g2) && (!g || !g2 || !this.e.equals(igVar.e))) {
            return false;
        }
        boolean h = h();
        boolean h2 = igVar.h();
        if ((h || h2) && (!h || !h2 || !this.f129a.equals(igVar.f129a))) {
            return false;
        }
        boolean i = i();
        boolean i2 = igVar.i();
        if ((i || i2) && (!i || !i2 || !this.f.equals(igVar.f))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = igVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.g.equals(igVar.g))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = igVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.h.equals(igVar.h))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = igVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.i.equals(igVar.i))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = igVar.m();
        if ((m2 || m3) && (!m2 || !m3 || !this.f127a.equals(igVar.f127a))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = igVar.n();
        if ((n2 || n3) && (!n2 || !n3 || this.a != igVar.a)) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = igVar.o();
        if (o2 || o3) {
            return o2 && o3 && this.f131b == igVar.f131b;
        }
        return true;
    }

    /* renamed from: a */
    public byte[] m1041a() {
        a(it.a(this.f127a));
        return this.f127a.array();
    }

    public ig b(String str) {
        this.c = str;
        return this;
    }

    public String b() {
        return this.c;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1037a();
        jdVar.a(j);
        if (this.f126a != null && m1039a()) {
            jdVar.a(k);
            jdVar.a(this.f126a);
            jdVar.b();
        }
        if (this.f125a != null && m1042b()) {
            jdVar.a(l);
            this.f125a.b(jdVar);
            jdVar.b();
        }
        if (this.b != null) {
            jdVar.a(m);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.c != null && d()) {
            jdVar.a(n);
            jdVar.a(this.c);
            jdVar.b();
        }
        if (this.d != null && e()) {
            jdVar.a(o);
            jdVar.a(this.d);
            jdVar.b();
        }
        jdVar.a(p);
        jdVar.a(this.f130a);
        jdVar.b();
        if (this.e != null && g()) {
            jdVar.a(q);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (this.f129a != null && h()) {
            jdVar.a(r);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f129a.size()));
            for (Map.Entry<String, String> entry : this.f129a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (this.f != null && i()) {
            jdVar.a(s);
            jdVar.a(this.f);
            jdVar.b();
        }
        if (this.g != null && j()) {
            jdVar.a(t);
            jdVar.a(this.g);
            jdVar.b();
        }
        if (this.h != null && k()) {
            jdVar.a(u);
            jdVar.a(this.h);
            jdVar.b();
        }
        if (this.i != null && l()) {
            jdVar.a(v);
            jdVar.a(this.i);
            jdVar.b();
        }
        if (this.f127a != null && m()) {
            jdVar.a(w);
            jdVar.a(this.f127a);
            jdVar.b();
        }
        if (n()) {
            jdVar.a(x);
            jdVar.a(this.a);
            jdVar.b();
        }
        if (o()) {
            jdVar.a(y);
            jdVar.a(this.f131b);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(boolean z) {
        this.f128a.set(1, z);
    }

    /* renamed from: b */
    public boolean m1042b() {
        return this.f125a != null;
    }

    public ig c(String str) {
        this.d = str;
        return this;
    }

    public String c() {
        return this.f;
    }

    public void c(boolean z) {
        this.f128a.set(2, z);
    }

    /* renamed from: c */
    public boolean m1043c() {
        return this.b != null;
    }

    public ig d(String str) {
        this.f = str;
        return this;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ig)) {
            return m1040a((ig) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f128a.get(0);
    }

    public boolean g() {
        return this.e != null;
    }

    public boolean h() {
        return this.f129a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f != null;
    }

    public boolean j() {
        return this.g != null;
    }

    public boolean k() {
        return this.h != null;
    }

    public boolean l() {
        return this.i != null;
    }

    public boolean m() {
        return this.f127a != null;
    }

    public boolean n() {
        return this.f128a.get(1);
    }

    public boolean o() {
        return this.f128a.get(2);
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionNotification(");
        if (m1039a()) {
            sb.append("debug:");
            String str = this.f126a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
            z = false;
        } else {
            z = true;
        }
        if (m1042b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("target:");
            hw hwVar = this.f125a;
            if (hwVar == null) {
                sb.append("null");
            } else {
                sb.append(hwVar);
            }
            z = false;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        String str2 = this.b;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            String str3 = this.c;
            if (str3 == null) {
                str3 = "null";
            }
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("type:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        sb.append(", ");
        sb.append("requireAck:");
        sb.append(this.f130a);
        if (g()) {
            sb.append(", ");
            sb.append("payload:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f129a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            String str6 = this.f;
            if (str6 == null) {
                str6 = "null";
            }
            sb.append(str6);
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            String str7 = this.g;
            if (str7 == null) {
                str7 = "null";
            }
            sb.append(str7);
        }
        if (k()) {
            sb.append(", ");
            sb.append("regId:");
            String str8 = this.h;
            if (str8 == null) {
                str8 = "null";
            }
            sb.append(str8);
        }
        if (l()) {
            sb.append(", ");
            sb.append("aliasName:");
            String str9 = this.i;
            if (str9 == null) {
                str9 = "null";
            }
            sb.append(str9);
        }
        if (m()) {
            sb.append(", ");
            sb.append("binaryExtra:");
            ByteBuffer byteBuffer = this.f127a;
            if (byteBuffer == null) {
                sb.append("null");
            } else {
                it.a(byteBuffer, sb);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("createdTs:");
            sb.append(this.a);
        }
        if (o()) {
            sb.append(", ");
            sb.append("alreadyLogClickInXmq:");
            sb.append(this.f131b);
        }
        sb.append(")");
        return sb.toString();
    }
}
