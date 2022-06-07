package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public final class GranularRoundedCorners extends BitmapTransformation {
    private static final byte[] a = "com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners".getBytes(CHARSET);
    private final float b;
    private final float c;
    private final float d;
    private final float e;

    public GranularRoundedCorners(float f, float f2, float f3, float f4) {
        this.b = f;
        this.c = f2;
        this.d = f3;
        this.e = f4;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        return TransformationUtils.roundedCorners(bitmapPool, bitmap, this.b, this.c, this.d, this.e);
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof GranularRoundedCorners)) {
            return false;
        }
        GranularRoundedCorners granularRoundedCorners = (GranularRoundedCorners) obj;
        return this.b == granularRoundedCorners.b && this.c == granularRoundedCorners.c && this.d == granularRoundedCorners.d && this.e == granularRoundedCorners.e;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode(this.e, Util.hashCode(this.d, Util.hashCode(this.c, Util.hashCode("com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners".hashCode(), Util.hashCode(this.b)))));
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(a);
        messageDigest.update(ByteBuffer.allocate(16).putFloat(this.b).putFloat(this.c).putFloat(this.d).putFloat(this.e).array());
    }
}
