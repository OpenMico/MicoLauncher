package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class GifDrawableTransformation implements Transformation<GifDrawable> {
    private final Transformation<Bitmap> a;

    public GifDrawableTransformation(Transformation<Bitmap> transformation) {
        this.a = (Transformation) Preconditions.checkNotNull(transformation);
    }

    @Override // com.bumptech.glide.load.Transformation
    @NonNull
    public Resource<GifDrawable> transform(@NonNull Context context, @NonNull Resource<GifDrawable> resource, int i, int i2) {
        GifDrawable gifDrawable = resource.get();
        Resource<Bitmap> bitmapResource = new BitmapResource(gifDrawable.getFirstFrame(), Glide.get(context).getBitmapPool());
        Resource<Bitmap> transform = this.a.transform(context, bitmapResource, i, i2);
        if (!bitmapResource.equals(transform)) {
            bitmapResource.recycle();
        }
        gifDrawable.setFrameTransformation(this.a, transform.get());
        return resource;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof GifDrawableTransformation) {
            return this.a.equals(((GifDrawableTransformation) obj).a);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.a.updateDiskCacheKey(messageDigest);
    }
}
