package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.decoder.CryptoInfo;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: AsynchronousMediaCodecBufferEnqueuer.java */
@RequiresApi(23)
/* loaded from: classes2.dex */
class a {
    @GuardedBy("MESSAGE_PARAMS_INSTANCE_POOL")
    private static final ArrayDeque<C0064a> a = new ArrayDeque<>();
    private static final Object b = new Object();
    private final MediaCodec c;
    private final HandlerThread d;
    private Handler e;
    private final AtomicReference<RuntimeException> f;
    private final ConditionVariable g;
    private final boolean h;
    private boolean i;

    public a(MediaCodec mediaCodec, HandlerThread handlerThread, boolean z) {
        this(mediaCodec, handlerThread, z, new ConditionVariable());
    }

    @VisibleForTesting
    a(MediaCodec mediaCodec, HandlerThread handlerThread, boolean z, ConditionVariable conditionVariable) {
        this.c = mediaCodec;
        this.d = handlerThread;
        this.g = conditionVariable;
        this.f = new AtomicReference<>();
        this.h = z || i();
    }

    public void a() {
        if (!this.i) {
            this.d.start();
            this.e = new Handler(this.d.getLooper()) { // from class: com.google.android.exoplayer2.mediacodec.a.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    a.this.a(message);
                }
            };
            this.i = true;
        }
    }

    public void a(int i, int i2, int i3, long j, int i4) {
        e();
        C0064a h = h();
        h.a(i, i2, i3, j, i4);
        ((Handler) Util.castNonNull(this.e)).obtainMessage(0, h).sendToTarget();
    }

    public void a(int i, int i2, CryptoInfo cryptoInfo, long j, int i3) {
        e();
        C0064a h = h();
        h.a(i, i2, 0, j, i3);
        a(cryptoInfo, h.d);
        ((Handler) Util.castNonNull(this.e)).obtainMessage(1, h).sendToTarget();
    }

    public void b() {
        if (this.i) {
            try {
                f();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }
    }

    public void c() {
        if (this.i) {
            b();
            this.d.quit();
        }
        this.i = false;
    }

    public void d() throws InterruptedException {
        g();
    }

    private void e() {
        RuntimeException andSet = this.f.getAndSet(null);
        if (andSet != null) {
            throw andSet;
        }
    }

    private void f() throws InterruptedException {
        ((Handler) Util.castNonNull(this.e)).removeCallbacksAndMessages(null);
        g();
        e();
    }

    private void g() throws InterruptedException {
        this.g.close();
        ((Handler) Util.castNonNull(this.e)).obtainMessage(2).sendToTarget();
        this.g.block();
    }

    @VisibleForTesting
    void a(RuntimeException runtimeException) {
        this.f.set(runtimeException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        C0064a aVar;
        switch (message.what) {
            case 0:
                aVar = (C0064a) message.obj;
                b(aVar.a, aVar.b, aVar.c, aVar.e, aVar.f);
                break;
            case 1:
                aVar = (C0064a) message.obj;
                a(aVar.a, aVar.b, aVar.d, aVar.e, aVar.f);
                break;
            case 2:
                this.g.open();
                aVar = null;
                break;
            default:
                a(new IllegalStateException(String.valueOf(message.what)));
                aVar = null;
                break;
        }
        if (aVar != null) {
            a(aVar);
        }
    }

    private void b(int i, int i2, int i3, long j, int i4) {
        try {
            this.c.queueInputBuffer(i, i2, i3, j, i4);
        } catch (RuntimeException e) {
            a(e);
        }
    }

    private void a(int i, int i2, MediaCodec.CryptoInfo cryptoInfo, long j, int i3) {
        try {
            if (this.h) {
                synchronized (b) {
                    this.c.queueSecureInputBuffer(i, i2, cryptoInfo, j, i3);
                }
            } else {
                this.c.queueSecureInputBuffer(i, i2, cryptoInfo, j, i3);
            }
        } catch (RuntimeException e) {
            a(e);
        }
    }

    private static C0064a h() {
        synchronized (a) {
            if (a.isEmpty()) {
                return new C0064a();
            }
            return a.removeFirst();
        }
    }

    private static void a(C0064a aVar) {
        synchronized (a) {
            a.add(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: AsynchronousMediaCodecBufferEnqueuer.java */
    /* renamed from: com.google.android.exoplayer2.mediacodec.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public static class C0064a {
        public int a;
        public int b;
        public int c;
        public final MediaCodec.CryptoInfo d = new MediaCodec.CryptoInfo();
        public long e;
        public int f;

        C0064a() {
        }

        public void a(int i, int i2, int i3, long j, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.e = j;
            this.f = i4;
        }
    }

    private static boolean i() {
        String lowerCase = Ascii.toLowerCase(Util.MANUFACTURER);
        return lowerCase.contains("samsung") || lowerCase.contains("motorola");
    }

    private static void a(CryptoInfo cryptoInfo, MediaCodec.CryptoInfo cryptoInfo2) {
        cryptoInfo2.numSubSamples = cryptoInfo.numSubSamples;
        cryptoInfo2.numBytesOfClearData = a(cryptoInfo.numBytesOfClearData, cryptoInfo2.numBytesOfClearData);
        cryptoInfo2.numBytesOfEncryptedData = a(cryptoInfo.numBytesOfEncryptedData, cryptoInfo2.numBytesOfEncryptedData);
        cryptoInfo2.key = (byte[]) Assertions.checkNotNull(a(cryptoInfo.key, cryptoInfo2.key));
        cryptoInfo2.iv = (byte[]) Assertions.checkNotNull(a(cryptoInfo.iv, cryptoInfo2.iv));
        cryptoInfo2.mode = cryptoInfo.mode;
        if (Util.SDK_INT >= 24) {
            cryptoInfo2.setPattern(new MediaCodec.CryptoInfo.Pattern(cryptoInfo.encryptedBlocks, cryptoInfo.clearBlocks));
        }
    }

    @Nullable
    private static int[] a(@Nullable int[] iArr, @Nullable int[] iArr2) {
        if (iArr == null) {
            return iArr2;
        }
        if (iArr2 == null || iArr2.length < iArr.length) {
            return Arrays.copyOf(iArr, iArr.length);
        }
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    @Nullable
    private static byte[] a(@Nullable byte[] bArr, @Nullable byte[] bArr2) {
        if (bArr == null) {
            return bArr2;
        }
        if (bArr2 == null || bArr2.length < bArr.length) {
            return Arrays.copyOf(bArr, bArr.length);
        }
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }
}
