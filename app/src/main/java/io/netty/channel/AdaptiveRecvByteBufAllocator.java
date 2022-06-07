package io.netty.channel;

import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class AdaptiveRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
    @Deprecated
    public static final AdaptiveRecvByteBufAllocator DEFAULT;
    private static final int[] a;
    private final int b;
    private final int c;
    private final int d;

    static {
        int i;
        ArrayList arrayList = new ArrayList();
        int i2 = 16;
        while (true) {
            if (i2 < 512) {
                arrayList.add(Integer.valueOf(i2));
                i2 += 16;
            }
        }
        for (i = 512; i > 0; i <<= 1) {
            arrayList.add(Integer.valueOf(i));
        }
        a = new int[arrayList.size()];
        int i3 = 0;
        while (true) {
            int[] iArr = a;
            if (i3 < iArr.length) {
                iArr[i3] = ((Integer) arrayList.get(i3)).intValue();
                i3++;
            } else {
                DEFAULT = new AdaptiveRecvByteBufAllocator();
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i) {
        int length = a.length - 1;
        int i2 = 0;
        while (length >= i2) {
            if (length == i2) {
                return length;
            }
            int i3 = (i2 + length) >>> 1;
            int[] iArr = a;
            int i4 = iArr[i3];
            int i5 = i3 + 1;
            if (i > iArr[i5]) {
                i2 = i5;
            } else if (i >= i4) {
                return i == i4 ? i3 : i5;
            } else {
                length = i3 - 1;
            }
        }
        return i2;
    }

    /* loaded from: classes4.dex */
    private final class a extends DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle {
        private final int c;
        private final int d;
        private int e;
        private int f;
        private boolean g;

        public a(int i, int i2, int i3) {
            super();
            this.c = i;
            this.d = i2;
            this.e = AdaptiveRecvByteBufAllocator.b(i3);
            this.f = AdaptiveRecvByteBufAllocator.a[this.e];
        }

        @Override // io.netty.channel.RecvByteBufAllocator.Handle
        public int guess() {
            return this.f;
        }

        private void a(int i) {
            if (i <= AdaptiveRecvByteBufAllocator.a[Math.max(0, (this.e - 1) - 1)]) {
                if (this.g) {
                    this.e = Math.max(this.e - 1, this.c);
                    this.f = AdaptiveRecvByteBufAllocator.a[this.e];
                    this.g = false;
                    return;
                }
                this.g = true;
            } else if (i >= this.f) {
                this.e = Math.min(this.e + 4, this.d);
                this.f = AdaptiveRecvByteBufAllocator.a[this.e];
                this.g = false;
            }
        }

        @Override // io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle, io.netty.channel.RecvByteBufAllocator.Handle
        public void readComplete() {
            a(totalBytesRead());
        }
    }

    public AdaptiveRecvByteBufAllocator() {
        this(64, 1024, 65536);
    }

    public AdaptiveRecvByteBufAllocator(int i, int i2, int i3) {
        if (i <= 0) {
            throw new IllegalArgumentException("minimum: " + i);
        } else if (i2 < i) {
            throw new IllegalArgumentException("initial: " + i2);
        } else if (i3 >= i2) {
            int b = b(i);
            if (a[b] < i) {
                this.b = b + 1;
            } else {
                this.b = b;
            }
            int b2 = b(i3);
            if (a[b2] > i3) {
                this.c = b2 - 1;
            } else {
                this.c = b2;
            }
            this.d = i2;
        } else {
            throw new IllegalArgumentException("maximum: " + i3);
        }
    }

    @Override // io.netty.channel.RecvByteBufAllocator
    public RecvByteBufAllocator.Handle newHandle() {
        return new a(this.b, this.c, this.d);
    }
}
