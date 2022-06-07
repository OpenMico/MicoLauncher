package com.google.android.exoplayer2;

import android.os.SystemClock;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Longs;

/* loaded from: classes.dex */
public final class DefaultLivePlaybackSpeedControl implements LivePlaybackSpeedControl {
    public static final float DEFAULT_FALLBACK_MAX_PLAYBACK_SPEED = 1.03f;
    public static final float DEFAULT_FALLBACK_MIN_PLAYBACK_SPEED = 0.97f;
    public static final long DEFAULT_MAX_LIVE_OFFSET_ERROR_MS_FOR_UNIT_SPEED = 20;
    public static final float DEFAULT_MIN_POSSIBLE_LIVE_OFFSET_SMOOTHING_FACTOR = 0.999f;
    public static final long DEFAULT_MIN_UPDATE_INTERVAL_MS = 1000;
    public static final float DEFAULT_PROPORTIONAL_CONTROL_FACTOR = 0.1f;
    public static final long DEFAULT_TARGET_LIVE_OFFSET_INCREMENT_ON_REBUFFER_MS = 500;
    private final float a;
    private final float b;
    private final long c;
    private final float d;
    private final long e;
    private final long f;
    private final float g;
    private long h;
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private float n;
    private float o;
    private float p;
    private long q;
    private long r;
    private long s;

    private static long a(long j, long j2, float f) {
        return (((float) j) * f) + ((1.0f - f) * ((float) j2));
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private float a = 0.97f;
        private float b = 1.03f;
        private long c = 1000;
        private float d = 1.0E-7f;
        private long e = C.msToUs(20);
        private long f = C.msToUs(500);
        private float g = 0.999f;

        public Builder setFallbackMinPlaybackSpeed(float f) {
            Assertions.checkArgument(0.0f < f && f <= 1.0f);
            this.a = f;
            return this;
        }

        public Builder setFallbackMaxPlaybackSpeed(float f) {
            Assertions.checkArgument(f >= 1.0f);
            this.b = f;
            return this;
        }

        public Builder setMinUpdateIntervalMs(long j) {
            Assertions.checkArgument(j > 0);
            this.c = j;
            return this;
        }

        public Builder setProportionalControlFactor(float f) {
            Assertions.checkArgument(f > 0.0f);
            this.d = f / 1000000.0f;
            return this;
        }

        public Builder setMaxLiveOffsetErrorMsForUnitSpeed(long j) {
            Assertions.checkArgument(j > 0);
            this.e = C.msToUs(j);
            return this;
        }

        public Builder setTargetLiveOffsetIncrementOnRebufferMs(long j) {
            Assertions.checkArgument(j >= 0);
            this.f = C.msToUs(j);
            return this;
        }

        public Builder setMinPossibleLiveOffsetSmoothingFactor(float f) {
            Assertions.checkArgument(f >= 0.0f && f < 1.0f);
            this.g = f;
            return this;
        }

        public DefaultLivePlaybackSpeedControl build() {
            return new DefaultLivePlaybackSpeedControl(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
        }
    }

    private DefaultLivePlaybackSpeedControl(float f, float f2, long j, float f3, long j2, long j3, float f4) {
        this.a = f;
        this.b = f2;
        this.c = j;
        this.d = f3;
        this.e = j2;
        this.f = j3;
        this.g = f4;
        this.h = C.TIME_UNSET;
        this.i = C.TIME_UNSET;
        this.k = C.TIME_UNSET;
        this.l = C.TIME_UNSET;
        this.o = f;
        this.n = f2;
        this.p = 1.0f;
        this.q = C.TIME_UNSET;
        this.j = C.TIME_UNSET;
        this.m = C.TIME_UNSET;
        this.r = C.TIME_UNSET;
        this.s = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public void setLiveConfiguration(MediaItem.LiveConfiguration liveConfiguration) {
        float f;
        float f2;
        this.h = C.msToUs(liveConfiguration.targetOffsetMs);
        this.k = C.msToUs(liveConfiguration.minOffsetMs);
        this.l = C.msToUs(liveConfiguration.maxOffsetMs);
        if (liveConfiguration.minPlaybackSpeed != -3.4028235E38f) {
            f = liveConfiguration.minPlaybackSpeed;
        } else {
            f = this.a;
        }
        this.o = f;
        if (liveConfiguration.maxPlaybackSpeed != -3.4028235E38f) {
            f2 = liveConfiguration.maxPlaybackSpeed;
        } else {
            f2 = this.b;
        }
        this.n = f2;
        a();
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public void setTargetLiveOffsetOverrideUs(long j) {
        this.i = j;
        a();
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public void notifyRebuffer() {
        long j = this.m;
        if (j != C.TIME_UNSET) {
            this.m = j + this.f;
            long j2 = this.l;
            if (j2 != C.TIME_UNSET && this.m > j2) {
                this.m = j2;
            }
            this.q = C.TIME_UNSET;
        }
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public float getAdjustedPlaybackSpeed(long j, long j2) {
        if (this.h == C.TIME_UNSET) {
            return 1.0f;
        }
        a(j, j2);
        if (this.q != C.TIME_UNSET && SystemClock.elapsedRealtime() - this.q < this.c) {
            return this.p;
        }
        this.q = SystemClock.elapsedRealtime();
        a(j);
        long j3 = j - this.m;
        if (Math.abs(j3) < this.e) {
            this.p = 1.0f;
        } else {
            this.p = Util.constrainValue((this.d * ((float) j3)) + 1.0f, this.o, this.n);
        }
        return this.p;
    }

    @Override // com.google.android.exoplayer2.LivePlaybackSpeedControl
    public long getTargetLiveOffsetUs() {
        return this.m;
    }

    private void a() {
        long j = this.h;
        if (j != C.TIME_UNSET) {
            long j2 = this.i;
            if (j2 != C.TIME_UNSET) {
                j = j2;
            }
            long j3 = this.k;
            if (j3 != C.TIME_UNSET && j < j3) {
                j = j3;
            }
            long j4 = this.l;
            if (j4 != C.TIME_UNSET && j > j4) {
                j = j4;
            }
        } else {
            j = -9223372036854775807L;
        }
        if (this.j != j) {
            this.j = j;
            this.m = j;
            this.r = C.TIME_UNSET;
            this.s = C.TIME_UNSET;
            this.q = C.TIME_UNSET;
        }
    }

    private void a(long j, long j2) {
        long j3 = j - j2;
        long j4 = this.r;
        if (j4 == C.TIME_UNSET) {
            this.r = j3;
            this.s = 0L;
            return;
        }
        this.r = Math.max(j3, a(j4, j3, this.g));
        this.s = a(this.s, Math.abs(j3 - this.r), this.g);
    }

    private void a(long j) {
        long j2 = this.r + (this.s * 3);
        if (this.m > j2) {
            float msToUs = (float) C.msToUs(this.c);
            this.m = Longs.max(j2, this.j, this.m - (((this.p - 1.0f) * msToUs) + ((this.n - 1.0f) * msToUs)));
            return;
        }
        this.m = Util.constrainValue(j - (Math.max(0.0f, this.p - 1.0f) / this.d), this.m, j2);
        long j3 = this.l;
        if (j3 != C.TIME_UNSET && this.m > j3) {
            this.m = j3;
        }
    }
}
