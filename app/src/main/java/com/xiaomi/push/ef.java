package com.xiaomi.push;

/* loaded from: classes4.dex */
public final class ef {

    /* loaded from: classes4.dex */
    public static final class a extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private boolean i;
        private boolean k;
        private boolean m;
        private boolean o;
        private boolean q;
        private boolean s;
        private boolean u;
        private int b = 0;
        private long d = 0;
        private String f = "";
        private String h = "";
        private String j = "";
        private String l = "";
        private String n = "";
        private int p = 1;
        private int r = 0;
        private int t = 0;
        private String v = "";
        private int w = -1;

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.w < 0) {
                mo890b();
            }
            return this.w;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public long mo888a() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public a mo888a() {
            this.k = false;
            this.l = "";
            return this;
        }

        public a a(int i) {
            this.a = true;
            this.b = i;
            return this;
        }

        public a a(long j) {
            this.c = true;
            this.d = j;
            return this;
        }

        @Override // com.xiaomi.push.e
        public a a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                switch (a) {
                    case 0:
                        return this;
                    case 8:
                        a(bVar.b());
                        break;
                    case 16:
                        a(bVar.m773b());
                        break;
                    case 26:
                        a(bVar.m767a());
                        break;
                    case 34:
                        b(bVar.m767a());
                        break;
                    case 42:
                        c(bVar.m767a());
                        break;
                    case 50:
                        d(bVar.m767a());
                        break;
                    case 58:
                        e(bVar.m767a());
                        break;
                    case 64:
                        b(bVar.b());
                        break;
                    case 72:
                        c(bVar.b());
                        break;
                    case 80:
                        d(bVar.b());
                        break;
                    case 90:
                        f(bVar.m767a());
                        break;
                    default:
                        if (a(bVar, a)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        public a a(String str) {
            this.e = true;
            this.f = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.f;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m788a(1, c());
            }
            if (mo890b()) {
                cVar.m802b(2, mo888a());
            }
            if (m844c()) {
                cVar.m792a(3, mo888a());
            }
            if (m846d()) {
                cVar.m792a(4, mo890b());
            }
            if (m848e()) {
                cVar.m792a(5, m843c());
            }
            if (m850f()) {
                cVar.m792a(6, m845d());
            }
            if (g()) {
                cVar.m792a(7, m847e());
            }
            if (h()) {
                cVar.m788a(8, d());
            }
            if (i()) {
                cVar.m788a(9, e());
            }
            if (j()) {
                cVar.m788a(10, f());
            }
            if (k()) {
                cVar.m792a(11, m849f());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, c());
            }
            if (mo890b()) {
                i += c.b(2, mo888a());
            }
            if (m844c()) {
                i += c.a(3, mo888a());
            }
            if (m846d()) {
                i += c.a(4, mo890b());
            }
            if (m848e()) {
                i += c.a(5, m843c());
            }
            if (m850f()) {
                i += c.a(6, m845d());
            }
            if (g()) {
                i += c.a(7, m847e());
            }
            if (h()) {
                i += c.a(8, d());
            }
            if (i()) {
                i += c.a(9, e());
            }
            if (j()) {
                i += c.a(10, f());
            }
            if (k()) {
                i += c.a(11, m849f());
            }
            this.w = i;
            return i;
        }

        public a b(int i) {
            this.o = true;
            this.p = i;
            return this;
        }

        public a b(String str) {
            this.g = true;
            this.h = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.h;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public int c() {
            return this.b;
        }

        public a c(int i) {
            this.q = true;
            this.r = i;
            return this;
        }

        public a c(String str) {
            this.i = true;
            this.j = str;
            return this;
        }

        /* renamed from: c */
        public String m843c() {
            return this.j;
        }

        /* renamed from: c */
        public boolean m844c() {
            return this.e;
        }

        public int d() {
            return this.p;
        }

        public a d(int i) {
            this.s = true;
            this.t = i;
            return this;
        }

        public a d(String str) {
            this.k = true;
            this.l = str;
            return this;
        }

        /* renamed from: d */
        public String m845d() {
            return this.l;
        }

        /* renamed from: d */
        public boolean m846d() {
            return this.g;
        }

        public int e() {
            return this.r;
        }

        public a e(String str) {
            this.m = true;
            this.n = str;
            return this;
        }

        /* renamed from: e */
        public String m847e() {
            return this.n;
        }

        /* renamed from: e */
        public boolean m848e() {
            return this.i;
        }

        public int f() {
            return this.t;
        }

        public a f(String str) {
            this.u = true;
            this.v = str;
            return this;
        }

        /* renamed from: f */
        public String m849f() {
            return this.v;
        }

        /* renamed from: f */
        public boolean m850f() {
            return this.k;
        }

        public boolean g() {
            return this.m;
        }

        public boolean h() {
            return this.o;
        }

        public boolean i() {
            return this.q;
        }

        public boolean j() {
            return this.s;
        }

        public boolean k() {
            return this.u;
        }
    }

    /* loaded from: classes4.dex */
    public static final class b extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private boolean b = false;
        private int d = 0;
        private int f = 0;
        private int h = 0;
        private int i = -1;

        public static b a(byte[] bArr) {
            return (b) new b().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.i < 0) {
                mo890b();
            }
            return this.i;
        }

        public b a(int i) {
            this.c = true;
            this.d = i;
            return this;
        }

        @Override // com.xiaomi.push.e
        public b a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 8) {
                    a(bVar.m770a());
                } else if (a == 24) {
                    a(bVar.b());
                } else if (a == 32) {
                    b(bVar.b());
                } else if (a == 40) {
                    c(bVar.b());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public b a(boolean z) {
            this.a = true;
            this.b = z;
            return this;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo890b()) {
                cVar.m793a(1, mo888a());
            }
            if (m853c()) {
                cVar.m788a(3, c());
            }
            if (m854d()) {
                cVar.m788a(4, d());
            }
            if (m855e()) {
                cVar.m788a(5, e());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo890b()) {
                i = 0 + c.a(1, mo888a());
            }
            if (m853c()) {
                i += c.a(3, c());
            }
            if (m854d()) {
                i += c.a(4, d());
            }
            if (m855e()) {
                i += c.a(5, e());
            }
            this.i = i;
            return i;
        }

        public b b(int i) {
            this.e = true;
            this.f = i;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.a;
        }

        public int c() {
            return this.d;
        }

        public b c(int i) {
            this.g = true;
            this.h = i;
            return this;
        }

        /* renamed from: c */
        public boolean m853c() {
            return this.c;
        }

        public int d() {
            return this.f;
        }

        /* renamed from: d */
        public boolean m854d() {
            return this.e;
        }

        public int e() {
            return this.h;
        }

        /* renamed from: e */
        public boolean m855e() {
            return this.g;
        }
    }

    /* loaded from: classes4.dex */
    public static final class c extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private boolean i;
        private boolean k;
        private String b = "";
        private String d = "";
        private String f = "";
        private String h = "";
        private String j = "";
        private String l = "";
        private int m = -1;

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.m < 0) {
                mo890b();
            }
            return this.m;
        }

        @Override // com.xiaomi.push.e
        public c a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 10) {
                    a(bVar.m767a());
                } else if (a == 18) {
                    b(bVar.m767a());
                } else if (a == 26) {
                    c(bVar.m767a());
                } else if (a == 34) {
                    d(bVar.m767a());
                } else if (a == 42) {
                    e(bVar.m767a());
                } else if (a == 50) {
                    f(bVar.m767a());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public c a(String str) {
            this.a = true;
            this.b = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m792a(1, mo888a());
            }
            if (mo890b()) {
                cVar.m792a(2, mo890b());
            }
            if (m858c()) {
                cVar.m792a(3, c());
            }
            if (m859d()) {
                cVar.m792a(4, d());
            }
            if (m860e()) {
                cVar.m792a(5, e());
            }
            if (m861f()) {
                cVar.m792a(6, f());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, mo888a());
            }
            if (mo890b()) {
                i += c.a(2, mo890b());
            }
            if (m858c()) {
                i += c.a(3, c());
            }
            if (m859d()) {
                i += c.a(4, d());
            }
            if (m860e()) {
                i += c.a(5, e());
            }
            if (m861f()) {
                i += c.a(6, f());
            }
            this.m = i;
            return i;
        }

        public c b(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public c c(String str) {
            this.e = true;
            this.f = str;
            return this;
        }

        public String c() {
            return this.f;
        }

        /* renamed from: c */
        public boolean m858c() {
            return this.e;
        }

        public c d(String str) {
            this.g = true;
            this.h = str;
            return this;
        }

        public String d() {
            return this.h;
        }

        /* renamed from: d */
        public boolean m859d() {
            return this.g;
        }

        public c e(String str) {
            this.i = true;
            this.j = str;
            return this;
        }

        public String e() {
            return this.j;
        }

        /* renamed from: e */
        public boolean m860e() {
            return this.i;
        }

        public c f(String str) {
            this.k = true;
            this.l = str;
            return this;
        }

        public String f() {
            return this.l;
        }

        /* renamed from: f */
        public boolean m861f() {
            return this.k;
        }
    }

    /* loaded from: classes4.dex */
    public static final class d extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private boolean b = false;
        private String d = "";
        private String f = "";
        private String h = "";
        private int i = -1;

        public static d a(byte[] bArr) {
            return (d) new d().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.i < 0) {
                mo890b();
            }
            return this.i;
        }

        @Override // com.xiaomi.push.e
        public d a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 8) {
                    a(bVar.m770a());
                } else if (a == 18) {
                    a(bVar.m767a());
                } else if (a == 26) {
                    b(bVar.m767a());
                } else if (a == 34) {
                    c(bVar.m767a());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public d a(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        public d a(boolean z) {
            this.a = true;
            this.b = z;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo890b()) {
                cVar.m793a(1, mo888a());
            }
            if (m864c()) {
                cVar.m792a(2, mo888a());
            }
            if (d()) {
                cVar.m792a(3, mo890b());
            }
            if (e()) {
                cVar.m792a(4, c());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo890b()) {
                i = 0 + c.a(1, mo888a());
            }
            if (m864c()) {
                i += c.a(2, mo888a());
            }
            if (d()) {
                i += c.a(3, mo890b());
            }
            if (e()) {
                i += c.a(4, c());
            }
            this.i = i;
            return i;
        }

        public d b(String str) {
            this.e = true;
            this.f = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.f;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.a;
        }

        public d c(String str) {
            this.g = true;
            this.h = str;
            return this;
        }

        public String c() {
            return this.h;
        }

        /* renamed from: c */
        public boolean m864c() {
            return this.c;
        }

        public boolean d() {
            return this.e;
        }

        public boolean e() {
            return this.g;
        }
    }

    /* loaded from: classes4.dex */
    public static final class e extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private boolean i;
        private boolean k;
        private boolean m;
        private boolean o;
        private boolean q;
        private boolean s;
        private int b = 0;
        private String d = "";
        private String f = "";
        private String h = "";
        private int j = 0;
        private String l = "";
        private String n = "";
        private String p = "";
        private b r = null;
        private int t = 0;
        private int u = -1;

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.u < 0) {
                mo890b();
            }
            return this.u;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public b mo888a() {
            return this.r;
        }

        public e a(int i) {
            this.a = true;
            this.b = i;
            return this;
        }

        @Override // com.xiaomi.push.e
        public e a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                switch (a) {
                    case 0:
                        return this;
                    case 8:
                        a(bVar.c());
                        break;
                    case 18:
                        a(bVar.m767a());
                        break;
                    case 26:
                        b(bVar.m767a());
                        break;
                    case 34:
                        c(bVar.m767a());
                        break;
                    case 40:
                        b(bVar.b());
                        break;
                    case 50:
                        d(bVar.m767a());
                        break;
                    case 58:
                        e(bVar.m767a());
                        break;
                    case 66:
                        f(bVar.m767a());
                        break;
                    case 74:
                        b bVar2 = new b();
                        bVar.a(bVar2);
                        a(bVar2);
                        break;
                    case 80:
                        c(bVar.b());
                        break;
                    default:
                        if (a(bVar, a)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }

        public e a(b bVar) {
            if (bVar != null) {
                this.q = true;
                this.r = bVar;
                return this;
            }
            throw new NullPointerException();
        }

        public e a(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m801b(1, c());
            }
            if (mo890b()) {
                cVar.m792a(2, mo888a());
            }
            if (m869c()) {
                cVar.m792a(3, mo890b());
            }
            if (m871d()) {
                cVar.m792a(4, m868c());
            }
            if (m873e()) {
                cVar.m788a(5, d());
            }
            if (m874f()) {
                cVar.m792a(6, m870d());
            }
            if (g()) {
                cVar.m792a(7, m872e());
            }
            if (h()) {
                cVar.m792a(8, f());
            }
            if (i()) {
                cVar.m791a(9, (e) mo888a());
            }
            if (j()) {
                cVar.m788a(10, e());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.b(1, c());
            }
            if (mo890b()) {
                i += c.a(2, mo888a());
            }
            if (m869c()) {
                i += c.a(3, mo890b());
            }
            if (m871d()) {
                i += c.a(4, m868c());
            }
            if (m873e()) {
                i += c.a(5, d());
            }
            if (m874f()) {
                i += c.a(6, m870d());
            }
            if (g()) {
                i += c.a(7, m872e());
            }
            if (h()) {
                i += c.a(8, f());
            }
            if (i()) {
                i += c.a(9, (e) mo888a());
            }
            if (j()) {
                i += c.a(10, e());
            }
            this.u = i;
            return i;
        }

        public e b(int i) {
            this.i = true;
            this.j = i;
            return this;
        }

        public e b(String str) {
            this.e = true;
            this.f = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.f;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public int c() {
            return this.b;
        }

        public e c(int i) {
            this.s = true;
            this.t = i;
            return this;
        }

        public e c(String str) {
            this.g = true;
            this.h = str;
            return this;
        }

        /* renamed from: c */
        public String m868c() {
            return this.h;
        }

        /* renamed from: c */
        public boolean m869c() {
            return this.e;
        }

        public int d() {
            return this.j;
        }

        public e d(String str) {
            this.k = true;
            this.l = str;
            return this;
        }

        /* renamed from: d */
        public String m870d() {
            return this.l;
        }

        /* renamed from: d */
        public boolean m871d() {
            return this.g;
        }

        public int e() {
            return this.t;
        }

        public e e(String str) {
            this.m = true;
            this.n = str;
            return this;
        }

        /* renamed from: e */
        public String m872e() {
            return this.n;
        }

        /* renamed from: e */
        public boolean m873e() {
            return this.i;
        }

        public e f(String str) {
            this.o = true;
            this.p = str;
            return this;
        }

        public String f() {
            return this.p;
        }

        /* renamed from: f */
        public boolean m874f() {
            return this.k;
        }

        public boolean g() {
            return this.m;
        }

        public boolean h() {
            return this.o;
        }

        public boolean i() {
            return this.q;
        }

        public boolean j() {
            return this.s;
        }
    }

    /* loaded from: classes4.dex */
    public static final class f extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private String b = "";
        private String d = "";
        private b f = null;
        private int g = -1;

        public static f a(byte[] bArr) {
            return (f) new f().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.g < 0) {
                mo890b();
            }
            return this.g;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public b mo888a() {
            return this.f;
        }

        @Override // com.xiaomi.push.e
        public f a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 10) {
                    a(bVar.m767a());
                } else if (a == 18) {
                    b(bVar.m767a());
                } else if (a == 26) {
                    b bVar2 = new b();
                    bVar.a(bVar2);
                    a(bVar2);
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public f a(b bVar) {
            if (bVar != null) {
                this.e = true;
                this.f = bVar;
                return this;
            }
            throw new NullPointerException();
        }

        public f a(String str) {
            this.a = true;
            this.b = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m792a(1, mo888a());
            }
            if (mo890b()) {
                cVar.m792a(2, mo890b());
            }
            if (c()) {
                cVar.m791a(3, (e) mo888a());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, mo888a());
            }
            if (mo890b()) {
                i += c.a(2, mo890b());
            }
            if (c()) {
                i += c.a(3, (e) mo888a());
            }
            this.g = i;
            return i;
        }

        public f b(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public boolean c() {
            return this.e;
        }
    }

    /* loaded from: classes4.dex */
    public static final class g extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private String b = "";
        private String d = "";
        private String f = "";
        private int g = -1;

        public static g a(byte[] bArr) {
            return (g) new g().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.g < 0) {
                mo890b();
            }
            return this.g;
        }

        @Override // com.xiaomi.push.e
        public g a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 10) {
                    a(bVar.m767a());
                } else if (a == 18) {
                    b(bVar.m767a());
                } else if (a == 26) {
                    c(bVar.m767a());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public g a(String str) {
            this.a = true;
            this.b = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m792a(1, mo888a());
            }
            if (mo890b()) {
                cVar.m792a(2, mo890b());
            }
            if (m880c()) {
                cVar.m792a(3, c());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, mo888a());
            }
            if (mo890b()) {
                i += c.a(2, mo890b());
            }
            if (m880c()) {
                i += c.a(3, c());
            }
            this.g = i;
            return i;
        }

        public g b(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public g c(String str) {
            this.e = true;
            this.f = str;
            return this;
        }

        public String c() {
            return this.f;
        }

        /* renamed from: c */
        public boolean m880c() {
            return this.e;
        }
    }

    /* loaded from: classes4.dex */
    public static final class h extends e {
        private boolean a;
        private boolean c;
        private int b = 0;
        private String d = "";
        private int e = -1;

        public static h a(byte[] bArr) {
            return (h) new h().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.e < 0) {
                mo890b();
            }
            return this.e;
        }

        public h a(int i) {
            this.a = true;
            this.b = i;
            return this;
        }

        @Override // com.xiaomi.push.e
        public h a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 8) {
                    a(bVar.b());
                } else if (a == 18) {
                    a(bVar.m767a());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public h a(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m788a(1, c());
            }
            if (mo890b()) {
                cVar.m792a(2, mo888a());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, c());
            }
            if (mo890b()) {
                i += c.a(2, mo888a());
            }
            this.e = i;
            return i;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public int c() {
            return this.b;
        }
    }

    /* loaded from: classes4.dex */
    public static final class i extends e {
        private boolean a;
        private a b = a.a;
        private int c = -1;

        public static i a(byte[] bArr) {
            return (i) new i().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.c < 0) {
                mo890b();
            }
            return this.c;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public a mo888a() {
            return this.b;
        }

        public i a(a aVar) {
            this.a = true;
            this.b = aVar;
            return this;
        }

        @Override // com.xiaomi.push.e
        public i a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 10) {
                    a(bVar.m766a());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m790a(1, mo888a());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, mo888a());
            }
            this.c = i;
            return i;
        }
    }

    /* loaded from: classes4.dex */
    public static final class j extends e {
        private boolean a;
        private boolean c;
        private a b = a.a;
        private b d = null;
        private int e = -1;

        public static j a(byte[] bArr) {
            return (j) new j().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.e < 0) {
                mo890b();
            }
            return this.e;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public a mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public b mo888a() {
            return this.d;
        }

        public j a(a aVar) {
            this.a = true;
            this.b = aVar;
            return this;
        }

        @Override // com.xiaomi.push.e
        public j a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 10) {
                    a(bVar.m766a());
                } else if (a == 18) {
                    b bVar2 = new b();
                    bVar.a(bVar2);
                    a(bVar2);
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public j a(b bVar) {
            if (bVar != null) {
                this.c = true;
                this.d = bVar;
                return this;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m790a(1, mo888a());
            }
            if (mo890b()) {
                cVar.m791a(2, (e) mo888a());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, mo888a());
            }
            if (mo890b()) {
                i += c.a(2, (e) mo888a());
            }
            this.e = i;
            return i;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }
    }

    /* loaded from: classes4.dex */
    public static final class k extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private boolean i;
        private boolean k;
        private String b = "";
        private String d = "";
        private long f = 0;
        private long h = 0;
        private boolean j = false;
        private int l = 0;
        private int m = -1;

        public static k a(byte[] bArr) {
            return (k) new k().a(bArr);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.m < 0) {
                mo890b();
            }
            return this.m;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public long mo888a() {
            return this.f;
        }

        public k a(int i) {
            this.k = true;
            this.l = i;
            return this;
        }

        public k a(long j) {
            this.e = true;
            this.f = j;
            return this;
        }

        @Override // com.xiaomi.push.e
        public k a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 10) {
                    a(bVar.m767a());
                } else if (a == 18) {
                    b(bVar.m767a());
                } else if (a == 24) {
                    a(bVar.m765a());
                } else if (a == 32) {
                    b(bVar.m765a());
                } else if (a == 40) {
                    a(bVar.m770a());
                } else if (a == 48) {
                    a(bVar.b());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public k a(String str) {
            this.a = true;
            this.b = str;
            return this;
        }

        public k a(boolean z) {
            this.i = true;
            this.j = z;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public String mo888a() {
            return this.b;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m792a(1, mo888a());
            }
            if (mo890b()) {
                cVar.m792a(2, mo890b());
            }
            if (m891c()) {
                cVar.m789a(3, mo888a());
            }
            if (d()) {
                cVar.m789a(4, mo890b());
            }
            if (f()) {
                cVar.m793a(5, e());
            }
            if (g()) {
                cVar.m788a(6, c());
            }
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public boolean mo888a() {
            return this.a;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public int mo890b() {
            int i = 0;
            if (mo888a()) {
                i = 0 + c.a(1, mo888a());
            }
            if (mo890b()) {
                i += c.a(2, mo890b());
            }
            if (m891c()) {
                i += c.a(3, mo888a());
            }
            if (d()) {
                i += c.a(4, mo890b());
            }
            if (f()) {
                i += c.a(5, e());
            }
            if (g()) {
                i += c.a(6, c());
            }
            this.m = i;
            return i;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public long mo890b() {
            return this.h;
        }

        public k b(long j) {
            this.g = true;
            this.h = j;
            return this;
        }

        public k b(String str) {
            this.c = true;
            this.d = str;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public String mo890b() {
            return this.d;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b */
        public boolean mo890b() {
            return this.c;
        }

        public int c() {
            return this.l;
        }

        /* renamed from: c */
        public boolean m891c() {
            return this.e;
        }

        public boolean d() {
            return this.g;
        }

        public boolean e() {
            return this.j;
        }

        public boolean f() {
            return this.i;
        }

        public boolean g() {
            return this.k;
        }
    }
}
