package com.xiaomi.push;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class ee {

    /* loaded from: classes4.dex */
    public static final class a extends e {
        private boolean a;
        private boolean c;
        private boolean e;
        private boolean g;
        private int b = 0;
        private boolean d = false;
        private int f = 0;
        private boolean h = false;
        private List<String> i = Collections.emptyList();
        private int j = -1;

        public static a a(byte[] bArr) {
            return (a) new a().a(bArr);
        }

        public static a b(b bVar) {
            return new a().a(bVar);
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a */
        public int mo888a() {
            if (this.j < 0) {
                mo890b();
            }
            return this.j;
        }

        public a a(int i) {
            this.a = true;
            this.b = i;
            return this;
        }

        @Override // com.xiaomi.push.e
        public a a(b bVar) {
            while (true) {
                int a = bVar.m764a();
                if (a == 0) {
                    return this;
                }
                if (a == 8) {
                    a(bVar.c());
                } else if (a == 16) {
                    a(bVar.m770a());
                } else if (a == 24) {
                    b(bVar.b());
                } else if (a == 32) {
                    b(bVar.m770a());
                } else if (a == 42) {
                    a(bVar.m767a());
                } else if (!a(bVar, a)) {
                    return this;
                }
            }
        }

        public a a(String str) {
            if (str != null) {
                if (this.i.isEmpty()) {
                    this.i = new ArrayList();
                }
                this.i.add(str);
                return this;
            }
            throw new NullPointerException();
        }

        public a a(boolean z) {
            this.c = true;
            this.d = z;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: a  reason: collision with other method in class */
        public List<String> mo888a() {
            return this.i;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (mo888a()) {
                cVar.m801b(1, c());
            }
            if (m837c()) {
                cVar.m793a(2, mo890b());
            }
            if (m838d()) {
                cVar.m788a(3, d());
            }
            if (f()) {
                cVar.m793a(4, m839e());
            }
            for (String str : mo888a()) {
                cVar.m792a(5, str);
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
            int b = mo888a() ? c.b(1, c()) + 0 : 0;
            if (m837c()) {
                b += c.a(2, mo890b());
            }
            if (m838d()) {
                b += c.a(3, d());
            }
            if (f()) {
                b += c.a(4, m839e());
            }
            for (String str : mo888a()) {
                i += c.a(str);
            }
            int size = b + i + (mo888a().size() * 1);
            this.j = size;
            return size;
        }

        public a b(int i) {
            this.e = true;
            this.f = i;
            return this;
        }

        public a b(boolean z) {
            this.g = true;
            this.h = z;
            return this;
        }

        @Override // com.xiaomi.push.e
        /* renamed from: b  reason: collision with other method in class */
        public boolean mo890b() {
            return this.d;
        }

        public int c() {
            return this.b;
        }

        /* renamed from: c  reason: collision with other method in class */
        public boolean m837c() {
            return this.c;
        }

        public int d() {
            return this.f;
        }

        /* renamed from: d  reason: collision with other method in class */
        public boolean m838d() {
            return this.e;
        }

        public int e() {
            return this.i.size();
        }

        /* renamed from: e  reason: collision with other method in class */
        public boolean m839e() {
            return this.h;
        }

        public boolean f() {
            return this.g;
        }
    }
}
