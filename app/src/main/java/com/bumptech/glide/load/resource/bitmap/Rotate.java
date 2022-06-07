package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class Rotate extends BitmapTransformation {
    private static final byte[] a = "com.bumptech.glide.load.resource.bitmap.Rotate".getBytes(CHARSET);
    private final int b;

    public Rotate(int i) {
        this.b = i;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        return TransformationUtils.rotateImage(bitmap, this.b);
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof Rotate) && this.b == ((Rotate) obj).b;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode("com.bumptech.glide.load.resource.bitmap.Rotate".hashCode(), Util.hashCode(this.b));
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(a);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.b).array());
    }
}
