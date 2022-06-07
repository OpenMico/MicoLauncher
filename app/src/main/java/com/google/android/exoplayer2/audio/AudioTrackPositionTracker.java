package com.google.android.exoplayer2.audio;

import android.media.AudioTrack;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
final class AudioTrackPositionTracker {
    private long A;
    private long B;
    private long C;
    private boolean D;
    private long E;
    private long F;
    private final Listener a;
    private final long[] b;
    @Nullable
    private AudioTrack c;
    private int d;
    private int e;
    @Nullable
    private a f;
    private int g;
    private boolean h;
    private long i;
    private float j;
    private boolean k;
    private long l;
    private long m;
    @Nullable
    private Method n;
    private long o;
    private boolean p;
    private boolean q;
    private long r;
    private long s;
    private long t;
    private long u;
    private int v;
    private int w;
    private long x;
    private long y;
    private long z;

    /* loaded from: classes.dex */
    public interface Listener {
        void onInvalidLatency(long j);

        void onPositionAdvancing(long j);

        void onPositionFramesMismatch(long j, long j2, long j3, long j4);

        void onSystemTimeUsMismatch(long j, long j2, long j3, long j4);

        void onUnderrun(int i, long j);
    }

    public AudioTrackPositionTracker(Listener listener) {
        this.a = (Listener) Assertions.checkNotNull(listener);
        if (Util.SDK_INT >= 18) {
            try {
                this.n = AudioTrack.class.getMethod("getLatency", null);
            } catch (NoSuchMethodException unused) {
            }
        }
        this.b = new long[10];
    }

    public void a(AudioTrack audioTrack, boolean z, int i, int i2, int i3) {
        this.c = audioTrack;
        this.d = i2;
        this.e = i3;
        this.f = new a(audioTrack);
        this.g = audioTrack.getSampleRate();
        this.h = z && a(i);
        this.q = Util.isEncodingLinearPcm(i);
        this.i = this.q ? h(i3 / i2) : -9223372036854775807L;
        this.s = 0L;
        this.t = 0L;
        this.u = 0L;
        this.p = false;
        this.x = C.TIME_UNSET;
        this.y = C.TIME_UNSET;
        this.r = 0L;
        this.o = 0L;
        this.j = 1.0f;
    }

    public void a(float f) {
        this.j = f;
        a aVar = this.f;
        if (aVar != null) {
            aVar.d();
        }
    }

    public long a(boolean z) {
        long j;
        if (((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState() == 3) {
            e();
        }
        long nanoTime = System.nanoTime() / 1000;
        a aVar = (a) Assertions.checkNotNull(this.f);
        boolean c = aVar.c();
        if (c) {
            j = h(aVar.f()) + Util.getMediaDurationForPlayoutDuration(nanoTime - aVar.e(), this.j);
        } else {
            if (this.w == 0) {
                j = h();
            } else {
                j = this.l + nanoTime;
            }
            if (!z) {
                j = Math.max(0L, j - this.o);
            }
        }
        if (this.D != c) {
            this.F = this.C;
            this.E = this.B;
        }
        long j2 = nanoTime - this.F;
        if (j2 < 1000000) {
            long j3 = (j2 * 1000) / 1000000;
            j = ((j * j3) + ((1000 - j3) * (this.E + Util.getMediaDurationForPlayoutDuration(j2, this.j)))) / 1000;
        }
        if (!this.k) {
            long j4 = this.B;
            if (j > j4) {
                this.k = true;
                this.a.onPositionAdvancing(System.currentTimeMillis() - C.usToMs(Util.getPlayoutDurationForMediaDuration(C.usToMs(j - j4), this.j)));
            }
        }
        this.C = nanoTime;
        this.B = j;
        this.D = c;
        return j;
    }

    public void a() {
        ((a) Assertions.checkNotNull(this.f)).d();
    }

    public boolean b() {
        return ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState() == 3;
    }

    public boolean a(long j) {
        int playState = ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState();
        if (this.h) {
            if (playState == 2) {
                this.p = false;
                return false;
            } else if (playState == 1 && i() == 0) {
                return false;
            }
        }
        boolean z = this.p;
        this.p = f(j);
        if (z && !this.p && playState != 1) {
            this.a.onUnderrun(this.e, C.usToMs(this.i));
        }
        return true;
    }

    public int b(long j) {
        return this.e - ((int) (j - (i() * this.d)));
    }

    public long c(long j) {
        return C.usToMs(h(j - i()));
    }

    public boolean d(long j) {
        return this.y != C.TIME_UNSET && j > 0 && SystemClock.elapsedRealtime() - this.y >= 200;
    }

    public void e(long j) {
        this.z = i();
        this.x = SystemClock.elapsedRealtime() * 1000;
        this.A = j;
    }

    public boolean f(long j) {
        return j > i() || g();
    }

    public boolean c() {
        f();
        if (this.x != C.TIME_UNSET) {
            return false;
        }
        ((a) Assertions.checkNotNull(this.f)).d();
        return true;
    }

    public void d() {
        f();
        this.c = null;
        this.f = null;
    }

    private void e() {
        long h = h();
        if (h != 0) {
            long nanoTime = System.nanoTime() / 1000;
            if (nanoTime - this.m >= 30000) {
                long[] jArr = this.b;
                int i = this.v;
                jArr[i] = h - nanoTime;
                this.v = (i + 1) % 10;
                int i2 = this.w;
                if (i2 < 10) {
                    this.w = i2 + 1;
                }
                this.m = nanoTime;
                this.l = 0L;
                int i3 = 0;
                while (true) {
                    int i4 = this.w;
                    if (i3 >= i4) {
                        break;
                    }
                    this.l += this.b[i3] / i4;
                    i3++;
                }
            }
            if (!this.h) {
                a(nanoTime, h);
                g(nanoTime);
            }
        }
    }

    private void a(long j, long j2) {
        a aVar = (a) Assertions.checkNotNull(this.f);
        if (aVar.a(j)) {
            long e = aVar.e();
            long f = aVar.f();
            if (Math.abs(e - j) > 5000000) {
                this.a.onSystemTimeUsMismatch(f, e, j, j2);
                aVar.a();
            } else if (Math.abs(h(f) - j2) > 5000000) {
                this.a.onPositionFramesMismatch(f, e, j, j2);
                aVar.a();
            } else {
                aVar.b();
            }
        }
    }

    private void g(long j) {
        Method method;
        if (this.q && (method = this.n) != null && j - this.r >= 500000) {
            try {
                this.o = (((Integer) Util.castNonNull((Integer) method.invoke(Assertions.checkNotNull(this.c), new Object[0]))).intValue() * 1000) - this.i;
                this.o = Math.max(this.o, 0L);
                if (this.o > 5000000) {
                    this.a.onInvalidLatency(this.o);
                    this.o = 0L;
                }
            } catch (Exception unused) {
                this.n = null;
            }
            this.r = j;
        }
    }

    private long h(long j) {
        return (j * 1000000) / this.g;
    }

    private void f() {
        this.l = 0L;
        this.w = 0;
        this.v = 0;
        this.m = 0L;
        this.C = 0L;
        this.F = 0L;
        this.k = false;
    }

    private boolean g() {
        return this.h && ((AudioTrack) Assertions.checkNotNull(this.c)).getPlayState() == 2 && i() == 0;
    }

    private static boolean a(int i) {
        return Util.SDK_INT < 23 && (i == 5 || i == 6);
    }

    private long h() {
        return h(i());
    }

    private long i() {
        AudioTrack audioTrack = (AudioTrack) Assertions.checkNotNull(this.c);
        if (this.x != C.TIME_UNSET) {
            return Math.min(this.A, this.z + ((((SystemClock.elapsedRealtime() * 1000) - this.x) * this.g) / 1000000));
        }
        int playState = audioTrack.getPlayState();
        if (playState == 1) {
            return 0L;
        }
        long playbackHeadPosition = 4294967295L & audioTrack.getPlaybackHeadPosition();
        if (this.h) {
            if (playState == 2 && playbackHeadPosition == 0) {
                this.u = this.s;
            }
            playbackHeadPosition += this.u;
        }
        if (Util.SDK_INT <= 29) {
            if (playbackHeadPosition == 0 && this.s > 0 && playState == 3) {
                if (this.y == C.TIME_UNSET) {
                    this.y = SystemClock.elapsedRealtime();
                }
                return this.s;
            }
            this.y = C.TIME_UNSET;
        }
        if (this.s > playbackHeadPosition) {
            this.t++;
        }
        this.s = playbackHeadPosition;
        return playbackHeadPosition + (this.t << 32);
    }
}
