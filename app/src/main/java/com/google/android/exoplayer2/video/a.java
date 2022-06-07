package com.google.android.exoplayer2.video;

import com.google.android.exoplayer2.C;
import java.util.Arrays;

/* compiled from: FixedFrameRateEstimator.java */
/* loaded from: classes2.dex */
final class a {
    private boolean c;
    private boolean d;
    private int f;
    private C0074a a = new C0074a();
    private C0074a b = new C0074a();
    private long e = C.TIME_UNSET;

    public void a() {
        this.a.a();
        this.b.a();
        this.c = false;
        this.e = C.TIME_UNSET;
        this.f = 0;
    }

    public void a(long j) {
        this.a.a(j);
        int i = 0;
        if (this.a.b() && !this.d) {
            this.c = false;
        } else if (this.e != C.TIME_UNSET) {
            if (!this.c || this.b.c()) {
                this.b.a();
                this.b.a(this.e);
            }
            this.c = true;
            this.b.a(j);
        }
        if (this.c && this.b.b()) {
            C0074a aVar = this.a;
            this.a = this.b;
            this.b = aVar;
            this.c = false;
            this.d = false;
        }
        this.e = j;
        if (!this.a.b()) {
            i = this.f + 1;
        }
        this.f = i;
    }

    public boolean b() {
        return this.a.b();
    }

    public int c() {
        return this.f;
    }

    public long d() {
        return b() ? this.a.d() : C.TIME_UNSET;
    }

    public long e() {
        return b() ? this.a.e() : C.TIME_UNSET;
    }

    public float f() {
        if (b()) {
            return (float) (1.0E9d / this.a.e());
        }
        return -1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FixedFrameRateEstimator.java */
    /* renamed from: com.google.android.exoplayer2.video.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static final class C0074a {
        private long a;
        private long b;
        private long c;
        private long d;
        private long e;
        private long f;
        private final boolean[] g = new boolean[15];
        private int h;

        public void a() {
            this.d = 0L;
            this.e = 0L;
            this.f = 0L;
            this.h = 0;
            Arrays.fill(this.g, false);
        }

        public boolean b() {
            return this.d > 15 && this.h == 0;
        }

        public boolean c() {
            long j = this.d;
            if (j == 0) {
                return false;
            }
            return this.g[b(j - 1)];
        }

        public long d() {
            return this.f;
        }

        public long e() {
            long j = this.e;
            if (j == 0) {
                return 0L;
            }
            return this.f / j;
        }

        public void a(long j) {
            long j2 = this.d;
            if (j2 == 0) {
                this.a = j;
            } else if (j2 == 1) {
                this.b = j - this.a;
                this.f = this.b;
                this.e = 1L;
            } else {
                long j3 = j - this.c;
                int b = b(j2);
                if (Math.abs(j3 - this.b) <= 1000000) {
                    this.e++;
                    this.f += j3;
                    boolean[] zArr = this.g;
                    if (zArr[b]) {
                        zArr[b] = false;
                        this.h--;
                    }
                } else {
                    boolean[] zArr2 = this.g;
                    if (!zArr2[b]) {
                        zArr2[b] = true;
                        this.h++;
                    }
                }
            }
            this.d++;
            this.c = j;
        }

        private static int b(long j) {
            return (int) (j % 15);
        }
    }
}
