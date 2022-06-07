package com.umeng.commonsdk.statistics.proto;

import com.umeng.analytics.pro.an;
import com.umeng.analytics.pro.aq;
import com.umeng.analytics.pro.aw;
import com.umeng.analytics.pro.ax;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bd;
import com.umeng.analytics.pro.bj;
import com.umeng.analytics.pro.bk;
import com.umeng.analytics.pro.bp;
import com.umeng.analytics.pro.bq;
import com.umeng.analytics.pro.bs;
import com.umeng.analytics.pro.bu;
import com.umeng.analytics.pro.bv;
import com.umeng.analytics.pro.bx;
import com.umeng.analytics.pro.by;
import com.umeng.analytics.pro.bz;
import com.umeng.analytics.pro.ca;
import com.umeng.analytics.pro.cb;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: ImprintValue.java */
/* loaded from: classes2.dex */
public class e implements aq<e, EnumC0149e>, Serializable, Cloneable {
    public static final Map<EnumC0149e, bc> d;
    private static final long e = 7501688097813630241L;
    private static final bu f = new bu("ImprintValue");
    private static final bk g = new bk(com.xiaomi.onetrack.api.b.p, (byte) 11, 1);
    private static final bk h = new bk("ts", (byte) 10, 2);
    private static final bk i = new bk("guid", (byte) 11, 3);
    private static final Map<Class<? extends bx>, by> j = new HashMap();
    private static final int k = 0;
    public String a;
    public long b;
    public String c;
    private byte l;
    private EnumC0149e[] m;

    static {
        j.put(bz.class, new b());
        j.put(ca.class, new d());
        EnumMap enumMap = new EnumMap(EnumC0149e.class);
        enumMap.put((EnumMap) EnumC0149e.VALUE, (EnumC0149e) new bc(com.xiaomi.onetrack.api.b.p, (byte) 2, new bd((byte) 11)));
        enumMap.put((EnumMap) EnumC0149e.TS, (EnumC0149e) new bc("ts", (byte) 1, new bd((byte) 10)));
        enumMap.put((EnumMap) EnumC0149e.GUID, (EnumC0149e) new bc("guid", (byte) 1, new bd((byte) 11)));
        d = Collections.unmodifiableMap(enumMap);
        bc.a(e.class, d);
    }

    /* compiled from: ImprintValue.java */
    /* renamed from: com.umeng.commonsdk.statistics.proto.e$e  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public enum EnumC0149e implements ax {
        VALUE(1, com.xiaomi.onetrack.api.b.p),
        TS(2, "ts"),
        GUID(3, "guid");
        
        private static final Map<String, EnumC0149e> d = new HashMap();
        private final short e;
        private final String f;

        static {
            Iterator it = EnumSet.allOf(EnumC0149e.class).iterator();
            while (it.hasNext()) {
                EnumC0149e eVar = (EnumC0149e) it.next();
                d.put(eVar.b(), eVar);
            }
        }

        public static EnumC0149e a(int i) {
            switch (i) {
                case 1:
                    return VALUE;
                case 2:
                    return TS;
                case 3:
                    return GUID;
                default:
                    return null;
            }
        }

        public static EnumC0149e b(int i) {
            EnumC0149e a = a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static EnumC0149e a(String str) {
            return d.get(str);
        }

        EnumC0149e(short s, String str) {
            this.e = s;
            this.f = str;
        }

        @Override // com.umeng.analytics.pro.ax
        public short a() {
            return this.e;
        }

        @Override // com.umeng.analytics.pro.ax
        public String b() {
            return this.f;
        }
    }

    public e() {
        this.l = (byte) 0;
        this.m = new EnumC0149e[]{EnumC0149e.VALUE};
    }

    public e(long j2, String str) {
        this();
        this.b = j2;
        b(true);
        this.c = str;
    }

    public e(e eVar) {
        this.l = (byte) 0;
        this.m = new EnumC0149e[]{EnumC0149e.VALUE};
        this.l = eVar.l;
        if (eVar.d()) {
            this.a = eVar.a;
        }
        this.b = eVar.b;
        if (eVar.j()) {
            this.c = eVar.c;
        }
    }

    /* renamed from: a */
    public e deepCopy() {
        return new e(this);
    }

    @Override // com.umeng.analytics.pro.aq
    public void clear() {
        this.a = null;
        b(false);
        this.b = 0L;
        this.c = null;
    }

    public String b() {
        return this.a;
    }

    public e a(String str) {
        this.a = str;
        return this;
    }

    public void c() {
        this.a = null;
    }

    public boolean d() {
        return this.a != null;
    }

    public void a(boolean z) {
        if (!z) {
            this.a = null;
        }
    }

    public long e() {
        return this.b;
    }

    public e a(long j2) {
        this.b = j2;
        b(true);
        return this;
    }

    public void f() {
        this.l = an.b(this.l, 0);
    }

    public boolean g() {
        return an.a(this.l, 0);
    }

    public void b(boolean z) {
        this.l = an.a(this.l, 0, z);
    }

    public String h() {
        return this.c;
    }

    public e b(String str) {
        this.c = str;
        return this;
    }

    public void i() {
        this.c = null;
    }

    public boolean j() {
        return this.c != null;
    }

    public void c(boolean z) {
        if (!z) {
            this.c = null;
        }
    }

    /* renamed from: a */
    public EnumC0149e fieldForId(int i2) {
        return EnumC0149e.a(i2);
    }

    @Override // com.umeng.analytics.pro.aq
    public void read(bp bpVar) throws aw {
        j.get(bpVar.D()).b().b(bpVar, this);
    }

    @Override // com.umeng.analytics.pro.aq
    public void write(bp bpVar) throws aw {
        j.get(bpVar.D()).b().a(bpVar, this);
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("ImprintValue(");
        if (d()) {
            sb.append("value:");
            String str = this.a;
            if (str == null) {
                sb.append("null");
            } else {
                sb.append(str);
            }
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("ts:");
        sb.append(this.b);
        sb.append(", ");
        sb.append("guid:");
        String str2 = this.c;
        if (str2 == null) {
            sb.append("null");
        } else {
            sb.append(str2);
        }
        sb.append(")");
        return sb.toString();
    }

    public void k() throws aw {
        if (this.c == null) {
            throw new bq("Required field 'guid' was not present! Struct: " + toString());
        }
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new bj(new cb(objectOutputStream)));
        } catch (aw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.l = (byte) 0;
            read(new bj(new cb(objectInputStream)));
        } catch (aw e2) {
            throw new IOException(e2.getMessage());
        }
    }

    /* compiled from: ImprintValue.java */
    /* loaded from: classes2.dex */
    private static class b implements by {
        private b() {
        }

        /* renamed from: a */
        public a b() {
            return new a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ImprintValue.java */
    /* loaded from: classes2.dex */
    public static class a extends bz<e> {
        private a() {
        }

        /* renamed from: a */
        public void b(bp bpVar, e eVar) throws aw {
            bpVar.j();
            while (true) {
                bk l = bpVar.l();
                if (l.b == 0) {
                    bpVar.k();
                    if (eVar.g()) {
                        eVar.k();
                        return;
                    }
                    throw new bq("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case 1:
                        if (l.b != 11) {
                            bs.a(bpVar, l.b);
                            break;
                        } else {
                            eVar.a = bpVar.z();
                            eVar.a(true);
                            break;
                        }
                    case 2:
                        if (l.b != 10) {
                            bs.a(bpVar, l.b);
                            break;
                        } else {
                            eVar.b = bpVar.x();
                            eVar.b(true);
                            break;
                        }
                    case 3:
                        if (l.b != 11) {
                            bs.a(bpVar, l.b);
                            break;
                        } else {
                            eVar.c = bpVar.z();
                            eVar.c(true);
                            break;
                        }
                    default:
                        bs.a(bpVar, l.b);
                        break;
                }
                bpVar.m();
            }
        }

        /* renamed from: b */
        public void a(bp bpVar, e eVar) throws aw {
            eVar.k();
            bpVar.a(e.f);
            if (eVar.a != null && eVar.d()) {
                bpVar.a(e.g);
                bpVar.a(eVar.a);
                bpVar.c();
            }
            bpVar.a(e.h);
            bpVar.a(eVar.b);
            bpVar.c();
            if (eVar.c != null) {
                bpVar.a(e.i);
                bpVar.a(eVar.c);
                bpVar.c();
            }
            bpVar.d();
            bpVar.b();
        }
    }

    /* compiled from: ImprintValue.java */
    /* loaded from: classes2.dex */
    private static class d implements by {
        private d() {
        }

        /* renamed from: a */
        public c b() {
            return new c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ImprintValue.java */
    /* loaded from: classes2.dex */
    public static class c extends ca<e> {
        private c() {
        }

        public void a(bp bpVar, e eVar) throws aw {
            bv bvVar = (bv) bpVar;
            bvVar.a(eVar.b);
            bvVar.a(eVar.c);
            BitSet bitSet = new BitSet();
            if (eVar.d()) {
                bitSet.set(0);
            }
            bvVar.a(bitSet, 1);
            if (eVar.d()) {
                bvVar.a(eVar.a);
            }
        }

        public void b(bp bpVar, e eVar) throws aw {
            bv bvVar = (bv) bpVar;
            eVar.b = bvVar.x();
            eVar.b(true);
            eVar.c = bvVar.z();
            eVar.c(true);
            if (bvVar.b(1).get(0)) {
                eVar.a = bvVar.z();
                eVar.a(true);
            }
        }
    }
}
