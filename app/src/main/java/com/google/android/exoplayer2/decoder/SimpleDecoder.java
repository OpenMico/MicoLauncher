package com.google.android.exoplayer2.decoder;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.decoder.OutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayDeque;

/* loaded from: classes.dex */
public abstract class SimpleDecoder<I extends DecoderInputBuffer, O extends OutputBuffer, E extends DecoderException> implements Decoder<I, O, E> {
    private final Thread a;
    private final Object b = new Object();
    private final ArrayDeque<I> c = new ArrayDeque<>();
    private final ArrayDeque<O> d = new ArrayDeque<>();
    private final I[] e;
    private final O[] f;
    private int g;
    private int h;
    private I i;
    private E j;
    private boolean k;
    private boolean l;
    private int m;

    protected abstract I createInputBuffer();

    protected abstract O createOutputBuffer();

    protected abstract E createUnexpectedDecodeException(Throwable th);

    @Nullable
    protected abstract E decode(I i, O o, boolean z);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.exoplayer2.decoder.Decoder
    public /* bridge */ /* synthetic */ void queueInputBuffer(Object obj) throws DecoderException {
        queueInputBuffer((SimpleDecoder<I, O, E>) ((DecoderInputBuffer) obj));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleDecoder(I[] iArr, O[] oArr) {
        this.e = iArr;
        this.g = iArr.length;
        for (int i = 0; i < this.g; i++) {
            this.e[i] = createInputBuffer();
        }
        this.f = oArr;
        this.h = oArr.length;
        for (int i2 = 0; i2 < this.h; i2++) {
            this.f[i2] = createOutputBuffer();
        }
        this.a = new Thread("ExoPlayer:SimpleDecoder") { // from class: com.google.android.exoplayer2.decoder.SimpleDecoder.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SimpleDecoder.this.c();
            }
        };
        this.a.start();
    }

    protected final void setInitialInputBufferSize(int i) {
        Assertions.checkState(this.g == this.e.length);
        for (I i2 : this.e) {
            i2.ensureSpaceForWrite(i);
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public final I dequeueInputBuffer() throws DecoderException {
        I i;
        I i2;
        synchronized (this.b) {
            a();
            Assertions.checkState(this.i == null);
            if (this.g == 0) {
                i = null;
            } else {
                I[] iArr = this.e;
                int i3 = this.g - 1;
                this.g = i3;
                i = iArr[i3];
            }
            this.i = i;
            i2 = this.i;
        }
        return i2;
    }

    public final void queueInputBuffer(I i) throws DecoderException {
        synchronized (this.b) {
            a();
            Assertions.checkArgument(i == this.i);
            this.c.addLast(i);
            b();
            this.i = null;
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public final O dequeueOutputBuffer() throws DecoderException {
        synchronized (this.b) {
            a();
            if (this.d.isEmpty()) {
                return null;
            }
            return this.d.removeFirst();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @CallSuper
    public void releaseOutputBuffer(O o) {
        synchronized (this.b) {
            a((SimpleDecoder<I, O, E>) o);
            b();
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    public final void flush() {
        synchronized (this.b) {
            this.k = true;
            this.m = 0;
            if (this.i != null) {
                a((SimpleDecoder<I, O, E>) this.i);
                this.i = null;
            }
            while (!this.c.isEmpty()) {
                a((SimpleDecoder<I, O, E>) this.c.removeFirst());
            }
            while (!this.d.isEmpty()) {
                this.d.removeFirst().release();
            }
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Decoder
    @CallSuper
    public void release() {
        synchronized (this.b) {
            this.l = true;
            this.b.notify();
        }
        try {
            this.a.join();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    private void a() throws DecoderException {
        E e = this.j;
        if (e != null) {
            throw e;
        }
    }

    private void b() {
        if (e()) {
            this.b.notify();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        do {
            try {
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        } while (d());
    }

    private boolean d() throws InterruptedException {
        E e;
        synchronized (this.b) {
            while (!this.l && !e()) {
                this.b.wait();
            }
            if (this.l) {
                return false;
            }
            I removeFirst = this.c.removeFirst();
            O[] oArr = this.f;
            int i = this.h - 1;
            this.h = i;
            O o = oArr[i];
            boolean z = this.k;
            this.k = false;
            if (removeFirst.isEndOfStream()) {
                o.addFlag(4);
            } else {
                if (removeFirst.isDecodeOnly()) {
                    o.addFlag(Integer.MIN_VALUE);
                }
                try {
                    e = decode(removeFirst, o, z);
                } catch (OutOfMemoryError e2) {
                    e = createUnexpectedDecodeException(e2);
                } catch (RuntimeException e3) {
                    e = createUnexpectedDecodeException(e3);
                }
                if (e != null) {
                    synchronized (this.b) {
                        this.j = e;
                    }
                    return false;
                }
            }
            synchronized (this.b) {
                if (this.k) {
                    o.release();
                } else if (o.isDecodeOnly()) {
                    this.m++;
                    o.release();
                } else {
                    o.skippedOutputBufferCount = this.m;
                    this.m = 0;
                    this.d.addLast(o);
                }
                a((SimpleDecoder<I, O, E>) removeFirst);
            }
            return true;
        }
    }

    private boolean e() {
        return !this.c.isEmpty() && this.h > 0;
    }

    private void a(I i) {
        i.clear();
        I[] iArr = this.e;
        int i2 = this.g;
        this.g = i2 + 1;
        iArr[i2] = i;
    }

    private void a(O o) {
        o.clear();
        O[] oArr = this.f;
        int i = this.h;
        this.h = i + 1;
        oArr[i] = o;
    }
}
