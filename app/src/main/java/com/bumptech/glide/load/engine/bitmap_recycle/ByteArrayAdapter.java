package com.bumptech.glide.load.engine.bitmap_recycle;

/* loaded from: classes.dex */
public final class ByteArrayAdapter implements a<byte[]> {
    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public int getElementSizeInBytes() {
        return 1;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public String getTag() {
        return "ByteArrayPool";
    }

    public int getArrayLength(byte[] bArr) {
        return bArr.length;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.a
    public byte[] newArray(int i) {
        return new byte[i];
    }
}
