package com.bumptech.glide.request.target;

import androidx.annotation.NonNull;
import com.bumptech.glide.util.Util;

@Deprecated
/* loaded from: classes.dex */
public abstract class SimpleTarget<Z> extends BaseTarget<Z> {
    private final int a;
    private final int b;

    @Override // com.bumptech.glide.request.target.Target
    public void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
    }

    public SimpleTarget() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public SimpleTarget(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
        if (Util.isValidDimensions(this.a, this.b)) {
            sizeReadyCallback.onSizeReady(this.a, this.b);
            return;
        }
        throw new IllegalArgumentException("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: " + this.a + " and height: " + this.b + ", either provide dimensions in the constructor or call override()");
    }
}
