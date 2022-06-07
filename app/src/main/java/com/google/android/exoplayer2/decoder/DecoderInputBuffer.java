package com.google.android.exoplayer2.decoder;

import androidx.annotation.Nullable;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* loaded from: classes.dex */
public class DecoderInputBuffer extends Buffer {
    public static final int BUFFER_REPLACEMENT_MODE_DIRECT = 2;
    public static final int BUFFER_REPLACEMENT_MODE_DISABLED = 0;
    public static final int BUFFER_REPLACEMENT_MODE_NORMAL = 1;
    private final int a;
    private final int b;
    public final CryptoInfo cryptoInfo;
    @Nullable
    public ByteBuffer data;
    @Nullable
    public ByteBuffer supplementalData;
    public long timeUs;
    public boolean waitingForKeys;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface BufferReplacementMode {
    }

    /* loaded from: classes.dex */
    public static final class InsufficientCapacityException extends IllegalStateException {
        public final int currentCapacity;
        public final int requiredCapacity;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public InsufficientCapacityException(int r3, int r4) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 44
                r0.<init>(r1)
                java.lang.String r1 = "Buffer too small ("
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = " < "
                r0.append(r1)
                r0.append(r4)
                java.lang.String r1 = ")"
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                r2.currentCapacity = r3
                r2.requiredCapacity = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.decoder.DecoderInputBuffer.InsufficientCapacityException.<init>(int, int):void");
        }
    }

    public static DecoderInputBuffer newNoDataInstance() {
        return new DecoderInputBuffer(0);
    }

    public DecoderInputBuffer(int i) {
        this(i, 0);
    }

    public DecoderInputBuffer(int i, int i2) {
        this.cryptoInfo = new CryptoInfo();
        this.a = i;
        this.b = i2;
    }

    @EnsuresNonNull({"supplementalData"})
    public void resetSupplementalData(int i) {
        ByteBuffer byteBuffer = this.supplementalData;
        if (byteBuffer == null || byteBuffer.capacity() < i) {
            this.supplementalData = ByteBuffer.allocate(i);
        } else {
            this.supplementalData.clear();
        }
    }

    @EnsuresNonNull({"data"})
    public void ensureSpaceForWrite(int i) {
        int i2 = i + this.b;
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer == null) {
            this.data = a(i2);
            return;
        }
        int capacity = byteBuffer.capacity();
        int position = byteBuffer.position();
        int i3 = i2 + position;
        if (capacity >= i3) {
            this.data = byteBuffer;
            return;
        }
        ByteBuffer a = a(i3);
        a.order(byteBuffer.order());
        if (position > 0) {
            byteBuffer.flip();
            a.put(byteBuffer);
        }
        this.data = a;
    }

    public final boolean isEncrypted() {
        return getFlag(1073741824);
    }

    public final void flip() {
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.flip();
        }
        ByteBuffer byteBuffer2 = this.supplementalData;
        if (byteBuffer2 != null) {
            byteBuffer2.flip();
        }
    }

    @Override // com.google.android.exoplayer2.decoder.Buffer
    public void clear() {
        super.clear();
        ByteBuffer byteBuffer = this.data;
        if (byteBuffer != null) {
            byteBuffer.clear();
        }
        ByteBuffer byteBuffer2 = this.supplementalData;
        if (byteBuffer2 != null) {
            byteBuffer2.clear();
        }
        this.waitingForKeys = false;
    }

    private ByteBuffer a(int i) {
        int i2 = this.a;
        if (i2 == 1) {
            return ByteBuffer.allocate(i);
        }
        if (i2 == 2) {
            return ByteBuffer.allocateDirect(i);
        }
        ByteBuffer byteBuffer = this.data;
        throw new InsufficientCapacityException(byteBuffer == null ? 0 : byteBuffer.capacity(), i);
    }
}
