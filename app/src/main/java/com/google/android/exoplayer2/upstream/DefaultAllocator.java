package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class DefaultAllocator implements Allocator {
    private final boolean a;
    private final int b;
    @Nullable
    private final byte[] c;
    private final Allocation[] d;
    private int e;
    private int f;
    private int g;
    private Allocation[] h;

    public DefaultAllocator(boolean z, int i) {
        this(z, i, 0);
    }

    public DefaultAllocator(boolean z, int i, int i2) {
        Assertions.checkArgument(i > 0);
        Assertions.checkArgument(i2 >= 0);
        this.a = z;
        this.b = i;
        this.g = i2;
        this.h = new Allocation[i2 + 100];
        if (i2 > 0) {
            this.c = new byte[i2 * i];
            for (int i3 = 0; i3 < i2; i3++) {
                this.h[i3] = new Allocation(this.c, i3 * i);
            }
        } else {
            this.c = null;
        }
        this.d = new Allocation[1];
    }

    public synchronized void reset() {
        if (this.a) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int i) {
        boolean z = i < this.e;
        this.e = i;
        if (z) {
            trim();
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized Allocation allocate() {
        Allocation allocation;
        this.f++;
        if (this.g > 0) {
            Allocation[] allocationArr = this.h;
            int i = this.g - 1;
            this.g = i;
            allocation = (Allocation) Assertions.checkNotNull(allocationArr[i]);
            this.h[this.g] = null;
        } else {
            allocation = new Allocation(new byte[this.b], 0);
        }
        return allocation;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void release(Allocation allocation) {
        this.d[0] = allocation;
        release(this.d);
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void release(Allocation[] allocationArr) {
        if (this.g + allocationArr.length >= this.h.length) {
            this.h = (Allocation[]) Arrays.copyOf(this.h, Math.max(this.h.length * 2, this.g + allocationArr.length));
        }
        for (Allocation allocation : allocationArr) {
            Allocation[] allocationArr2 = this.h;
            int i = this.g;
            this.g = i + 1;
            allocationArr2[i] = allocation;
        }
        this.f -= allocationArr.length;
        notifyAll();
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void trim() {
        int i = 0;
        int max = Math.max(0, Util.ceilDivide(this.e, this.b) - this.f);
        if (max < this.g) {
            if (this.c != null) {
                int i2 = this.g - 1;
                while (i <= i2) {
                    Allocation allocation = (Allocation) Assertions.checkNotNull(this.h[i]);
                    if (allocation.data == this.c) {
                        i++;
                    } else {
                        Allocation allocation2 = (Allocation) Assertions.checkNotNull(this.h[i2]);
                        if (allocation2.data != this.c) {
                            i2--;
                        } else {
                            i++;
                            this.h[i] = allocation2;
                            i2--;
                            this.h[i2] = allocation;
                        }
                    }
                }
                max = Math.max(max, i);
                if (max >= this.g) {
                    return;
                }
            }
            Arrays.fill(this.h, max, this.g, (Object) null);
            this.g = max;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized int getTotalBytesAllocated() {
        return this.f * this.b;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public int getIndividualAllocationLength() {
        return this.b;
    }
}
