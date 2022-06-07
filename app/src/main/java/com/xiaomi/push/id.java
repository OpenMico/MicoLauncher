package com.xiaomi.push;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class id implements is<id, Object>, Serializable, Cloneable {
    private static final ji c = new ji("XmPushActionContainer");
    private static final ja d = new ja("", (byte) 8, 1);
    private static final ja e = new ja("", (byte) 2, 2);
    private static final ja f = new ja("", (byte) 2, 3);
    private static final ja g = new ja("", (byte) 11, 4);
    private static final ja h = new ja("", (byte) 11, 5);
    private static final ja i = new ja("", (byte) 11, 6);
    private static final ja j = new ja("", (byte) 12, 7);
    private static final ja k = new ja("", (byte) 12, 8);
    public hh a;

    /* renamed from: a */
    public hu f118a;

    /* renamed from: a */
    public hw f119a;

    /* renamed from: a */
    public String f120a;

    /* renamed from: a */
    public ByteBuffer f121a;
    public String b;

    /* renamed from: a */
    private BitSet f122a = new BitSet(2);

    /* renamed from: a */
    public boolean f123a = true;

    /* renamed from: b */
    public boolean f124b = true;

    /* renamed from: a */
    public int compareTo(id idVar) {
        int a;
        int a2;
        int a3;
        int a4;
        int a5;
        int a6;
        int a7;
        int a8;
        if (!getClass().equals(idVar.getClass())) {
            return getClass().getName().compareTo(idVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m1025a()).compareTo(Boolean.valueOf(idVar.m1025a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m1025a() && (a8 = it.a(this.a, idVar.a)) != 0) {
            return a8;
        }
        int compareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(idVar.c()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (c() && (a7 = it.a(this.f123a, idVar.f123a)) != 0) {
            return a7;
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(idVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d() && (a6 = it.a(this.f124b, idVar.f124b)) != 0) {
            return a6;
        }
        int compareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(idVar.e()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (e() && (a5 = it.a(this.f121a, idVar.f121a)) != 0) {
            return a5;
        }
        int compareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(idVar.f()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (f() && (a4 = it.a(this.f120a, idVar.f120a)) != 0) {
            return a4;
        }
        int compareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(idVar.g()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (g() && (a3 = it.a(this.b, idVar.b)) != 0) {
            return a3;
        }
        int compareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(idVar.h()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (h() && (a2 = it.a(this.f119a, idVar.f119a)) != 0) {
            return a2;
        }
        int compareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(idVar.i()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (!i() || (a = it.a(this.f118a, idVar.f118a)) == 0) {
            return 0;
        }
        return a;
    }

    public hh a() {
        return this.a;
    }

    /* renamed from: a */
    public hu m1021a() {
        return this.f118a;
    }

    public id a(hh hhVar) {
        this.a = hhVar;
        return this;
    }

    public id a(hu huVar) {
        this.f118a = huVar;
        return this;
    }

    public id a(hw hwVar) {
        this.f119a = hwVar;
        return this;
    }

    public id a(String str) {
        this.f120a = str;
        return this;
    }

    public id a(ByteBuffer byteBuffer) {
        this.f121a = byteBuffer;
        return this;
    }

    public id a(boolean z) {
        this.f123a = z;
        m1024a(true);
        return this;
    }

    /* renamed from: a */
    public String m1022a() {
        return this.f120a;
    }

    /* renamed from: a */
    public void m1023a() {
        if (this.a == null) {
            throw new je("Required field 'action' was not present! Struct: " + toString());
        } else if (this.f121a == null) {
            throw new je("Required field 'pushAction' was not present! Struct: " + toString());
        } else if (this.f119a == null) {
            throw new je("Required field 'target' was not present! Struct: " + toString());
        }
    }

    @Override // com.xiaomi.push.is
    public void a(jd jdVar) {
        jdVar.m1099a();
        while (true) {
            ja a = jdVar.m1095a();
            if (a.a == 0) {
                jdVar.f();
                if (!c()) {
                    throw new je("Required field 'encryptAction' was not found in serialized data! Struct: " + toString());
                } else if (d()) {
                    m1023a();
                    return;
                } else {
                    throw new je("Required field 'isRequest' was not found in serialized data! Struct: " + toString());
                }
            } else {
                switch (a.f180a) {
                    case 1:
                        if (a.a == 8) {
                            this.a = hh.a(jdVar.m1093a());
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 2:
                        if (a.a == 2) {
                            this.f123a = jdVar.m1104a();
                            m1024a(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 3:
                        if (a.a == 2) {
                            this.f124b = jdVar.m1104a();
                            m1028b(true);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 4:
                        if (a.a == 11) {
                            this.f121a = jdVar.m1101a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 5:
                        if (a.a == 11) {
                            this.f120a = jdVar.m1100a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 6:
                        if (a.a == 11) {
                            this.b = jdVar.m1100a();
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 7:
                        if (a.a == 12) {
                            this.f119a = new hw();
                            this.f119a.a(jdVar);
                            continue;
                            jdVar.g();
                        }
                        break;
                    case 8:
                        if (a.a == 12) {
                            this.f118a = new hu();
                            this.f118a.a(jdVar);
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

    /* renamed from: a */
    public void m1024a(boolean z) {
        this.f122a.set(0, z);
    }

    /* renamed from: a */
    public boolean m1025a() {
        return this.a != null;
    }

    /* renamed from: a */
    public boolean m1026a(id idVar) {
        if (idVar == null) {
            return false;
        }
        boolean a = m1025a();
        boolean a2 = idVar.m1025a();
        if (((a || a2) && (!a || !a2 || !this.a.equals(idVar.a))) || this.f123a != idVar.f123a || this.f124b != idVar.f124b) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = idVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.f121a.equals(idVar.f121a))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = idVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.f120a.equals(idVar.f120a))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = idVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.b.equals(idVar.b))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = idVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.f119a.m1000a(idVar.f119a))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = idVar.i();
        if (i2 || i3) {
            return i2 && i3 && this.f118a.m992a(idVar.f118a);
        }
        return true;
    }

    /* renamed from: a */
    public byte[] m1027a() {
        a(it.a(this.f121a));
        return this.f121a.array();
    }

    public id b(String str) {
        this.b = str;
        return this;
    }

    public id b(boolean z) {
        this.f124b = z;
        m1028b(true);
        return this;
    }

    public String b() {
        return this.b;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m1023a();
        jdVar.a(c);
        if (this.a != null) {
            jdVar.a(d);
            jdVar.mo1091a(this.a.a());
            jdVar.b();
        }
        jdVar.a(e);
        jdVar.a(this.f123a);
        jdVar.b();
        jdVar.a(f);
        jdVar.a(this.f124b);
        jdVar.b();
        if (this.f121a != null) {
            jdVar.a(g);
            jdVar.a(this.f121a);
            jdVar.b();
        }
        if (this.f120a != null && f()) {
            jdVar.a(h);
            jdVar.a(this.f120a);
            jdVar.b();
        }
        if (this.b != null && g()) {
            jdVar.a(i);
            jdVar.a(this.b);
            jdVar.b();
        }
        if (this.f119a != null) {
            jdVar.a(j);
            this.f119a.b(jdVar);
            jdVar.b();
        }
        if (this.f118a != null && i()) {
            jdVar.a(k);
            this.f118a.b(jdVar);
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    /* renamed from: b */
    public void m1028b(boolean z) {
        this.f122a.set(1, z);
    }

    /* renamed from: b */
    public boolean m1029b() {
        return this.f123a;
    }

    public boolean c() {
        return this.f122a.get(0);
    }

    public boolean d() {
        return this.f122a.get(1);
    }

    public boolean e() {
        return this.f121a != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof id)) {
            return m1026a((id) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f120a != null;
    }

    public boolean g() {
        return this.b != null;
    }

    public boolean h() {
        return this.f119a != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f118a != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionContainer(");
        sb.append("action:");
        hh hhVar = this.a;
        if (hhVar == null) {
            sb.append("null");
        } else {
            sb.append(hhVar);
        }
        sb.append(", ");
        sb.append("encryptAction:");
        sb.append(this.f123a);
        sb.append(", ");
        sb.append("isRequest:");
        sb.append(this.f124b);
        sb.append(", ");
        sb.append("pushAction:");
        ByteBuffer byteBuffer = this.f121a;
        if (byteBuffer == null) {
            sb.append("null");
        } else {
            it.a(byteBuffer, sb);
        }
        if (f()) {
            sb.append(", ");
            sb.append("appid:");
            String str = this.f120a;
            if (str == null) {
                str = "null";
            }
            sb.append(str);
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            String str2 = this.b;
            if (str2 == null) {
                str2 = "null";
            }
            sb.append(str2);
        }
        sb.append(", ");
        sb.append("target:");
        hw hwVar = this.f119a;
        if (hwVar == null) {
            sb.append("null");
        } else {
            sb.append(hwVar);
        }
        if (i()) {
            sb.append(", ");
            sb.append("metaInfo:");
            hu huVar = this.f118a;
            if (huVar == null) {
                sb.append("null");
            } else {
                sb.append(huVar);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
