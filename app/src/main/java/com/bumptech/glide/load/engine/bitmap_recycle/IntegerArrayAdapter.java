package com.bumptech.glide.load.engine.bitmap_recycle;

/* loaded from: classes.dex */
public final class IntegerArrayAdapter implements a<int[]> {
    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public int getElementSizeInBytes() {
        return 4;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public String getTag() {
        return "IntegerArrayPool";
    }

    public int getArrayLength(int[] iArr) {
        return iArr.length;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public int[] newArray(int i) {
        return new int[i];
    }
}
