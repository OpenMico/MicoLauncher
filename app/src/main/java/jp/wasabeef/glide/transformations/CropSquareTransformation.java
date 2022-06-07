package jp.wasabeef.glide.transformations;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import java.security.MessageDigest;

/* loaded from: classes5.dex */
public class CropSquareTransformation extends BitmapTransformation {
    private int a;

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        this.a = Math.max(i, i2);
        int i3 = this.a;
        return TransformationUtils.centerCrop(bitmapPool, bitmap, i3, i3);
    }

    public String toString() {
        return "CropSquareTransformation(size=" + this.a + ")";
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof CropSquareTransformation) && ((CropSquareTransformation) obj).a == this.a;
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public int hashCode() {
        return "jp.wasabeef.glide.transformations.CropSquareTransformation.1".hashCode() + (this.a * 10);
    }

    @Override // jp.wasabeef.glide.transformations.BitmapTransformation, com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("jp.wasabeef.glide.transformations.CropSquareTransformation.1" + this.a).getBytes(CHARSET));
    }
}
