package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.IntArrayQueue;
import com.google.android.exoplayer2.util.Util;
import com.milink.base.contract.LockContract;
import java.util.ArrayDeque;

/* compiled from: AsynchronousMediaCodecCallback.java */
@RequiresApi(23)
/* loaded from: classes2.dex */
final class b extends MediaCodec.Callback {
    private final HandlerThread b;
    private Handler c;
    @Nullable
    @GuardedBy(LockContract.Matcher.LOCK)
    private MediaFormat h;
    @Nullable
    @GuardedBy(LockContract.Matcher.LOCK)
    private MediaFormat i;
    @Nullable
    @GuardedBy(LockContract.Matcher.LOCK)
    private MediaCodec.CodecException j;
    @GuardedBy(LockContract.Matcher.LOCK)
    private long k;
    @GuardedBy(LockContract.Matcher.LOCK)
    private boolean l;
    @Nullable
    @GuardedBy(LockContract.Matcher.LOCK)
    private IllegalStateException m;
    private final Object a = new Object();
    @GuardedBy(LockContract.Matcher.LOCK)
    private final IntArrayQueue d = new IntArrayQueue();
    @GuardedBy(LockContract.Matcher.LOCK)
    private final IntArrayQueue e = new IntArrayQueue();
    @GuardedBy(LockContract.Matcher.LOCK)
    private final ArrayDeque<MediaCodec.BufferInfo> f = new ArrayDeque<>();
    @GuardedBy(LockContract.Matcher.LOCK)
    private final ArrayDeque<MediaFormat> g = new ArrayDeque<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(HandlerThread handlerThread) {
        this.b = handlerThread;
    }

    public void a(MediaCodec mediaCodec) {
        Assertions.checkState(this.c == null);
        this.b.start();
        Handler handler = new Handler(this.b.getLooper());
        mediaCodec.setCallback(this, handler);
        this.c = handler;
    }

    public void a() {
        synchronized (this.a) {
            this.l = true;
            this.b.quit();
            d();
        }
    }

    public int b() {
        synchronized (this.a) {
            int i = -1;
            if (e()) {
                return -1;
            }
            f();
            if (!this.d.isEmpty()) {
                i = this.d.remove();
            }
            return i;
        }
    }

    public int a(MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.a) {
            if (e()) {
                return -1;
            }
            f();
            if (this.e.isEmpty()) {
                return -1;
            }
            int remove = this.e.remove();
            if (remove >= 0) {
                Assertions.checkStateNotNull(this.h);
                MediaCodec.BufferInfo remove2 = this.f.remove();
                bufferInfo.set(remove2.offset, remove2.size, remove2.presentationTimeUs, remove2.flags);
            } else if (remove == -2) {
                this.h = this.g.remove();
            }
            return remove;
        }
    }

    public MediaFormat c() {
        MediaFormat mediaFormat;
        synchronized (this.a) {
            if (this.h != null) {
                mediaFormat = this.h;
            } else {
                throw new IllegalStateException();
            }
        }
        return mediaFormat;
    }

    public void a(final Runnable runnable) {
        synchronized (this.a) {
            this.k++;
            ((Handler) Util.castNonNull(this.c)).post(new Runnable() { // from class: com.google.android.exoplayer2.mediacodec.-$$Lambda$b$avtsAqvXea-KkF62Me29Q172pG8
                @Override // java.lang.Runnable
                public final void run() {
                    b.this.d(runnable);
                }
            });
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onInputBufferAvailable(@NonNull MediaCodec mediaCodec, int i) {
        synchronized (this.a) {
            this.d.add(i);
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputBufferAvailable(@NonNull MediaCodec mediaCodec, int i, @NonNull MediaCodec.BufferInfo bufferInfo) {
        synchronized (this.a) {
            if (this.i != null) {
                a(this.i);
                this.i = null;
            }
            this.e.add(i);
            this.f.add(bufferInfo);
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onError(@NonNull MediaCodec mediaCodec, @NonNull MediaCodec.CodecException codecException) {
        synchronized (this.a) {
            this.j = codecException;
        }
    }

    @Override // android.media.MediaCodec.Callback
    public void onOutputFormatChanged(@NonNull MediaCodec mediaCodec, @NonNull MediaFormat mediaFormat) {
        synchronized (this.a) {
            a(mediaFormat);
            this.i = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void d(Runnable runnable) {
        synchronized (this.a) {
            c(runnable);
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void c(Runnable runnable) {
        if (!this.l) {
            this.k--;
            long j = this.k;
            if (j <= 0) {
                if (j < 0) {
                    a(new IllegalStateException());
                    return;
                }
                d();
                try {
                    runnable.run();
                } catch (IllegalStateException e) {
                    a(e);
                } catch (Exception e2) {
                    a(new IllegalStateException(e2));
                }
            }
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void d() {
        if (!this.g.isEmpty()) {
            this.i = this.g.getLast();
        }
        this.d.clear();
        this.e.clear();
        this.f.clear();
        this.g.clear();
        this.j = null;
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private boolean e() {
        return this.k > 0 || this.l;
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void a(MediaFormat mediaFormat) {
        this.e.add(-2);
        this.g.add(mediaFormat);
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void f() {
        g();
        h();
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void g() {
        IllegalStateException illegalStateException = this.m;
        if (illegalStateException != null) {
            this.m = null;
            throw illegalStateException;
        }
    }

    @GuardedBy(LockContract.Matcher.LOCK)
    private void h() {
        MediaCodec.CodecException codecException = this.j;
        if (codecException != null) {
            this.j = null;
            throw codecException;
        }
    }

    private void a(IllegalStateException illegalStateException) {
        synchronized (this.a) {
            this.m = illegalStateException;
        }
    }
}
