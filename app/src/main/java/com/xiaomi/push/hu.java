package com.xiaomi.push;

import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class hu implements is<hu, Object>, Serializable, Cloneable {
    private static final ji f = new ji("PushMetaInfo");
    private static final ja g = new ja("", (byte) 11, 1);
    private static final ja h = new ja("", (byte) 10, 2);
    private static final ja i = new ja("", (byte) 11, 3);
    private static final ja j = new ja("", (byte) 11, 4);
    private static final ja k = new ja("", (byte) 11, 5);
    private static final ja l = new ja("", (byte) 8, 6);
    private static final ja m = new ja("", (byte) 11, 7);
    private static final ja n = new ja("", (byte) 8, 8);
    private static final ja o = new ja("", (byte) 8, 9);
    private static final ja p = new ja("", (byte) 13, 10);
    private static final ja q = new ja("", (byte) 13, 11);
    private static final ja r = new ja("", (byte) 2, 12);
    private static final ja s = new ja("", (byte) 13, 13);
    public int a;

    /* renamed from: a */
    public long f80a;

    /* renamed from: a */
    public String f81a;

    /* renamed from: a */
    private BitSet f82a;

    /* renamed from: a */
    public Map<String, String> f83a;

    /* renamed from: a */
    public boolean f84a;
    public int b;

    /* renamed from: b */
    public String f85b;

    /* renamed from: b */
    public Map<String, String> f86b;
    public int c;

    /* renamed from: c */
    public String f87c;

    /* renamed from: c */
    public Map<String, String> f88c;
    public String d;
    public String e;

    public hu() {
        this.f82a = new BitSet(5);
        this.f84a = false;
    }

    public hu(hu huVar) {
        this.f82a = new BitSet(5);
        this.f82a.clear();
        this.f82a.or(huVar.f82a);
        if (huVar.m991a()) {
            this.f81a = huVar.f81a;
        }
        this.f80a = huVar.f80a;
        if (huVar.m997c()) {
            this.f85b = huVar.f85b;
        }
        if (huVar.m998d()) {
            this.f87c = huVar.f87c;
        }
        if (huVar.e()) {
            this.d = huVar.d;
        }
        this.a = huVar.a;
        if (huVar.g()) {
            this.e = huVar.e;
        }
        this.b = huVar.b;
        this.c = huVar.c;
        if (huVar.j()) {
            HashMap hashMap = new HashMap();
            for (Map.Entry<String, String> entry : huVar.f83a.entrySet()) {
                hashMap.put(entry.getKey(), entry.getValue());
            }
            this.f83a = hashMap;
        }
        if (huVar.k()) {
            HashMap hashMap2 = new HashMap();
            for (Map.Entry<String, String> entry2 : huVar.f86b.entrySet()) {
                hashMap2.put(entry2.getKey(), entry2.getValue());
            }
            this.f86b = hashMap2;
        }
        this.f84a = huVar.f84a;
        if (huVar.n()) {
            HashMap hashMap3 = new HashMap();
            for (Map.Entry<String, String> entry3 : huVar.f88c.entrySet()) {
                hashMap3.put(entry3.getKey(), entry3.getValue());
            }
            this.f88c = hashMap3;
        }
    }

    public int a() {
        return this.a;
    }

    /* renamed from: a */
    public int compareTo(hu huVar) {
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
        if (!getClass().equals(huVar.getClass())) {
            return getClass().getName().compareTo(huVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(m991a()).compareTo(Boolean.valueOf(huVar.m991a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (m991a() && (a13 = it.a(this.f81a, huVar.f81a)) != 0) {
            return a13;
        }
        int compareTo2 = Boolean.valueOf(m995b()).compareTo(Boolean.valueOf(huVar.m995b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (m995b() && (a12 = it.a(this.f80a, huVar.f80a)) != 0) {
            return a12;
        }
        int compareTo3 = Boolean.valueOf(m997c()).compareTo(Boolean.valueOf(huVar.m997c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (m997c() && (a11 = it.a(this.f85b, huVar.f85b)) != 0) {
            return a11;
        }
        int compareTo4 = Boolean.valueOf(m998d()).compareTo(Boolean.valueOf(huVar.m998d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (m998d() && (a10 = it.a(this.f87c, huVar.f87c)) != 0) {
            return a10;
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(huVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e() && (a9 = it.a(this.d, huVar.d)) != 0) {
            return a9;
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(huVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f() && (a8 = it.a(this.a, huVar.a)) != 0) {
            return a8;
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(huVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g() && (a7 = it.a(this.e, huVar.e)) != 0) {
            return a7;
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(huVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h() && (a6 = it.a(this.b, huVar.b)) != 0) {
            return a6;
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(huVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i() && (a5 = it.a(this.c, huVar.c)) != 0) {
            return a5;
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(huVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j() && (a4 = it.a(this.f83a, huVar.f83a)) != 0) {
            return a4;
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(huVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k() && (a3 = it.a(this.f86b, huVar.f86b)) != 0) {
            return a3;
        }
        int compareTo12 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(huVar.m()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (m() && (a2 = it.a(this.f84a, huVar.f84a)) != 0) {
            return a2;
        }
        int compareTo13 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(huVar.n()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (!n() || (a = it.a(this.f88c, huVar.f88c)) == 0) {
            return 0;
        }
        return a;
    }

    /* renamed from: a */
    public long m986a() {
        return this.f80a;
    }

    /* renamed from: a */
    public hu m987a() {
        return new hu(this);
    }

    public hu a(int i2) {
        this.a = i2;
        b(true);
        return this;
    }

    public hu a(String str) {
        this.f81a = str;
        return this;
    }

    public hu a(Map<String, String> map) {
        this.f83a = map;
        return this;
    }

    /* renamed from: a */
    public String m988a() {
        return this.f81a;
    }

    /* renamed from: a */
    public Map<String, String> m989a() {
        return this.f83a;
    }

    /* renamed from: a */
    public void m990a() {
        if (this.f81a == null) {
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
                if (m995b()) {
                    m990a();
                    return;
                }
                throw new je("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
            }
            int i2 = 0;
            switch (a.f180a) {
                case 1:
                    if (a.a == 11) {
                        this.f81a = jdVar.m1100a();
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 2:
                    if (a.a == 10) {
                        this.f80a = jdVar.m1094a();
                        a(true);
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 3:
                    if (a.a == 11) {
                        this.f85b = jdVar.m1100a();
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 4:
                    if (a.a == 11) {
                        this.f87c = jdVar.m1100a();
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
                    if (a.a == 8) {
                        this.a = jdVar.m1093a();
                        b(true);
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
                    if (a.a == 8) {
                        this.b = jdVar.m1093a();
                        c(true);
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 9:
                    if (a.a == 8) {
                        this.c = jdVar.m1093a();
                        d(true);
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 10:
                    if (a.a == 13) {
                        jc a2 = jdVar.m1097a();
                        this.f83a = new HashMap(a2.f182a * 2);
                        while (i2 < a2.f182a) {
                            this.f83a.put(jdVar.m1100a(), jdVar.m1100a());
                            i2++;
                        }
                        jdVar.h();
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 11:
                    if (a.a == 13) {
                        jc a3 = jdVar.m1097a();
                        this.f86b = new HashMap(a3.f182a * 2);
                        while (i2 < a3.f182a) {
                            this.f86b.put(jdVar.m1100a(), jdVar.m1100a());
                            i2++;
                        }
                        jdVar.h();
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 12:
                    if (a.a == 2) {
                        this.f84a = jdVar.m1104a();
                        e(true);
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                case 13:
                    if (a.a == 13) {
                        jc a4 = jdVar.m1097a();
                        this.f88c = new HashMap(a4.f182a * 2);
                        while (i2 < a4.f182a) {
                            this.f88c.put(jdVar.m1100a(), jdVar.m1100a());
                            i2++;
                        }
                        jdVar.h();
                        break;
                    }
                    jg.a(jdVar, a.a);
                    break;
                default:
                    jg.a(jdVar, a.a);
                    break;
            }
            jdVar.g();
        }
    }

    public void a(String str, String str2) {
        if (this.f83a == null) {
            this.f83a = new HashMap();
        }
        this.f83a.put(str, str2);
    }

    public void a(boolean z) {
        this.f82a.set(0, z);
    }

    /* renamed from: a */
    public boolean m991a() {
        return this.f81a != null;
    }

    /* renamed from: a */
    public boolean m992a(hu huVar) {
        if (huVar == null) {
            return false;
        }
        boolean a = m991a();
        boolean a2 = huVar.m991a();
        if (((a || a2) && (!a || !a2 || !this.f81a.equals(huVar.f81a))) || this.f80a != huVar.f80a) {
            return false;
        }
        boolean c = m997c();
        boolean c2 = huVar.m997c();
        if ((c || c2) && (!c || !c2 || !this.f85b.equals(huVar.f85b))) {
            return false;
        }
        boolean d = m998d();
        boolean d2 = huVar.m998d();
        if ((d || d2) && (!d || !d2 || !this.f87c.equals(huVar.f87c))) {
            return false;
        }
        boolean e = e();
        boolean e2 = huVar.e();
        if ((e || e2) && (!e || !e2 || !this.d.equals(huVar.d))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = huVar.f();
        if ((f2 || f3) && (!f2 || !f3 || this.a != huVar.a)) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = huVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.e.equals(huVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = huVar.h();
        if ((h2 || h3) && (!h2 || !h3 || this.b != huVar.b)) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = huVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.c != huVar.c)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = huVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.f83a.equals(huVar.f83a))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = huVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.f86b.equals(huVar.f86b))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = huVar.m();
        if ((m2 || m3) && (!m2 || !m3 || this.f84a != huVar.f84a)) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = huVar.n();
        if (n2 || n3) {
            return n2 && n3 && this.f88c.equals(huVar.f88c);
        }
        return true;
    }

    public int b() {
        return this.b;
    }

    public hu b(int i2) {
        this.b = i2;
        c(true);
        return this;
    }

    public hu b(String str) {
        this.f85b = str;
        return this;
    }

    /* renamed from: b */
    public String m993b() {
        return this.f85b;
    }

    /* renamed from: b */
    public Map<String, String> m994b() {
        return this.f86b;
    }

    @Override // com.xiaomi.push.is
    public void b(jd jdVar) {
        m990a();
        jdVar.a(f);
        if (this.f81a != null) {
            jdVar.a(g);
            jdVar.a(this.f81a);
            jdVar.b();
        }
        jdVar.a(h);
        jdVar.a(this.f80a);
        jdVar.b();
        if (this.f85b != null && m997c()) {
            jdVar.a(i);
            jdVar.a(this.f85b);
            jdVar.b();
        }
        if (this.f87c != null && m998d()) {
            jdVar.a(j);
            jdVar.a(this.f87c);
            jdVar.b();
        }
        if (this.d != null && e()) {
            jdVar.a(k);
            jdVar.a(this.d);
            jdVar.b();
        }
        if (f()) {
            jdVar.a(l);
            jdVar.mo1091a(this.a);
            jdVar.b();
        }
        if (this.e != null && g()) {
            jdVar.a(m);
            jdVar.a(this.e);
            jdVar.b();
        }
        if (h()) {
            jdVar.a(n);
            jdVar.mo1091a(this.b);
            jdVar.b();
        }
        if (i()) {
            jdVar.a(o);
            jdVar.mo1091a(this.c);
            jdVar.b();
        }
        if (this.f83a != null && j()) {
            jdVar.a(p);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f83a.size()));
            for (Map.Entry<String, String> entry : this.f83a.entrySet()) {
                jdVar.a(entry.getKey());
                jdVar.a(entry.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (this.f86b != null && k()) {
            jdVar.a(q);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f86b.size()));
            for (Map.Entry<String, String> entry2 : this.f86b.entrySet()) {
                jdVar.a(entry2.getKey());
                jdVar.a(entry2.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        if (m()) {
            jdVar.a(r);
            jdVar.a(this.f84a);
            jdVar.b();
        }
        if (this.f88c != null && n()) {
            jdVar.a(s);
            jdVar.a(new jc((byte) 11, (byte) 11, this.f88c.size()));
            for (Map.Entry<String, String> entry3 : this.f88c.entrySet()) {
                jdVar.a(entry3.getKey());
                jdVar.a(entry3.getValue());
            }
            jdVar.d();
            jdVar.b();
        }
        jdVar.c();
        jdVar.m1103a();
    }

    public void b(String str, String str2) {
        if (this.f86b == null) {
            this.f86b = new HashMap();
        }
        this.f86b.put(str, str2);
    }

    public void b(boolean z) {
        this.f82a.set(1, z);
    }

    /* renamed from: b */
    public boolean m995b() {
        return this.f82a.get(0);
    }

    public int c() {
        return this.c;
    }

    public hu c(int i2) {
        this.c = i2;
        d(true);
        return this;
    }

    public hu c(String str) {
        this.f87c = str;
        return this;
    }

    /* renamed from: c */
    public String m996c() {
        return this.f87c;
    }

    public void c(boolean z) {
        this.f82a.set(2, z);
    }

    /* renamed from: c */
    public boolean m997c() {
        return this.f85b != null;
    }

    public hu d(String str) {
        this.d = str;
        return this;
    }

    public String d() {
        return this.d;
    }

    public void d(boolean z) {
        this.f82a.set(3, z);
    }

    /* renamed from: d */
    public boolean m998d() {
        return this.f87c != null;
    }

    public void e(boolean z) {
        this.f82a.set(4, z);
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof hu)) {
            return m992a((hu) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f82a.get(1);
    }

    public boolean g() {
        return this.e != null;
    }

    public boolean h() {
        return this.f82a.get(2);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f82a.get(3);
    }

    public boolean j() {
        return this.f83a != null;
    }

    public boolean k() {
        return this.f86b != null;
    }

    public boolean l() {
        return this.f84a;
    }

    public boolean m() {
        return this.f82a.get(4);
    }

    public boolean n() {
        return this.f88c != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMetaInfo(");
        sb.append("id:");
        String str = this.f81a;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.f80a);
        if (m997c()) {
            sb.append(", ");
            sb.append("topic:");
            String str2 = this.f85b;
            if (str2 == null) {
                str2 = "null";
            }
            sb.append(str2);
        }
        if (m998d()) {
            sb.append(", ");
            sb.append("title:");
            String str3 = this.f87c;
            if (str3 == null) {
                str3 = "null";
            }
            sb.append(str3);
        }
        if (e()) {
            sb.append(", ");
            sb.append("description:");
            String str4 = this.d;
            if (str4 == null) {
                str4 = "null";
            }
            sb.append(str4);
        }
        if (f()) {
            sb.append(", ");
            sb.append("notifyType:");
            sb.append(this.a);
        }
        if (g()) {
            sb.append(", ");
            sb.append("url:");
            String str5 = this.e;
            if (str5 == null) {
                str5 = "null";
            }
            sb.append(str5);
        }
        if (h()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.b);
        }
        if (i()) {
            sb.append(", ");
            sb.append("notifyId:");
            sb.append(this.c);
        }
        if (j()) {
            sb.append(", ");
            sb.append("extra:");
            Map<String, String> map = this.f83a;
            if (map == null) {
                sb.append("null");
            } else {
                sb.append(map);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("internal:");
            Map<String, String> map2 = this.f86b;
            if (map2 == null) {
                sb.append("null");
            } else {
                sb.append(map2);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("ignoreRegInfo:");
            sb.append(this.f84a);
        }
        if (n()) {
            sb.append(", ");
            sb.append("apsProperFields:");
            Map<String, String> map3 = this.f88c;
            if (map3 == null) {
                sb.append("null");
            } else {
                sb.append(map3);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
