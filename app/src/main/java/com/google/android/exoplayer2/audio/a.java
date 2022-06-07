package com.google.android.exoplayer2.audio;

import android.annotation.TargetApi;
import android.media.AudioTimestamp;
import android.media.AudioTrack;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;

/* compiled from: AudioTimestampPoller.java */
/* loaded from: classes.dex */
final class a {
    @Nullable
    private final C0057a a;
    private int b;
    private long c;
    private long d;
    private long e;
    private long f;

    public a(AudioTrack audioTrack) {
        if (Util.SDK_INT >= 19) {
            this.a = new C0057a(audioTrack);
            d();
            return;
        }
        this.a = null;
        a(3);
    }

    @TargetApi(19)
    public boolean a(long j) {
        C0057a aVar = this.a;
        if (aVar == null || j - this.e < this.d) {
            return false;
        }
        this.e = j;
        boolean a = aVar.a();
        switch (this.b) {
            case 0:
                if (a) {
                    if (this.a.b() < this.c) {
                        return false;
                    }
                    this.f = this.a.c();
                    a(1);
                    return a;
                } else if (j - this.c <= 500000) {
                    return a;
                } else {
                    a(3);
                    return a;
                }
            case 1:
                if (!a) {
                    d();
                    return a;
                } else if (this.a.c() <= this.f) {
                    return a;
                } else {
                    a(2);
                    return a;
                }
            case 2:
                if (a) {
                    return a;
                }
                d();
                return a;
            case 3:
                if (!a) {
                    return a;
                }
                d();
                return a;
            case 4:
                return a;
            default:
                throw new IllegalStateException();
        }
    }

    public void a() {
        a(4);
    }

    public void b() {
        if (this.b == 4) {
            d();
        }
    }

    public boolean c() {
        return this.b == 2;
    }

    public void d() {
        if (this.a != null) {
            a(0);
        }
    }

    @TargetApi(19)
    public long e() {
        C0057a aVar = this.a;
        return aVar != null ? aVar.b() : C.TIME_UNSET;
    }

    @TargetApi(19)
    public long f() {
        C0057a aVar = this.a;
        if (aVar != null) {
            return aVar.c();
        }
        return -1L;
    }

    private void a(int i) {
        this.b = i;
        switch (i) {
            case 0:
                this.e = 0L;
                this.f = -1L;
                this.c = System.nanoTime() / 1000;
                this.d = 10000L;
                return;
            case 1:
                this.d = 10000L;
                return;
            case 2:
            case 3:
                this.d = 10000000L;
                return;
            case 4:
                this.d = 500000L;
                return;
            default:
                throw new IllegalStateException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AudioTimestampPoller.java */
    @RequiresApi(19)
    /* renamed from: com.google.android.exoplayer2.audio.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static final class C0057a {
        private final AudioTrack a;
        private final AudioTimestamp b = new AudioTimestamp();
        private long c;
        private long d;
        private long e;

        public C0057a(AudioTrack audioTrack) {
            this.a = audioTrack;
        }

        public boolean a() {
            boolean timestamp = this.a.getTimestamp(this.b);
            if (timestamp) {
                long j = this.b.framePosition;
                if (this.d > j) {
                    this.c++;
                }
                this.d = j;
                this.e = j + (this.c << 32);
            }
            return timestamp;
        }

        public long b() {
            return this.b.nanoTime / 1000;
        }

        public long c() {
            return this.e;
        }
    }
}
