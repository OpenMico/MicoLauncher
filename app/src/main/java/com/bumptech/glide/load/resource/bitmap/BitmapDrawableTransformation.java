package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

@Deprecated
/* loaded from: classes.dex */
public class BitmapDrawableTransformation implements Transformation<BitmapDrawable> {
    private final Transformation<Drawable> a;

    private static Resource<Drawable> b(Resource<BitmapDrawable> resource) {
        return resource;
    }

    public BitmapDrawableTransformation(Transformation<Bitmap> transformation) {
        this.a = (Transformation) Preconditions.checkNotNull(new DrawableTransformation(transformation, false));
    }

    @Override // com.bumptech.glide.load.Transformation
    @NonNull
    public Resource<BitmapDrawable> transform(@NonNull Context context, @NonNull Resource<BitmapDrawable> resource, int i, int i2) {
        return a(this.a.transform(context, b(resource), i, i2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Resource<BitmapDrawable> a(Resource<Drawable> resource) {
        if (resource.get() instanceof BitmapDrawable) {
            return resource;
        }
        throw new IllegalArgumentException("Wrapped transformation unexpectedly returned a non BitmapDrawable resource: " + resource.get());
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof BitmapDrawableTransformation) {
            return this.a.equals(((BitmapDrawableTransformation) obj).a);
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
