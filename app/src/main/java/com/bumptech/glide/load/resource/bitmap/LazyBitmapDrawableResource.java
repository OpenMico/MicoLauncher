package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public final class LazyBitmapDrawableResource implements Initializable, Resource<BitmapDrawable> {
    private final Resources a;
    private final Resource<Bitmap> b;

    @Deprecated
    public static LazyBitmapDrawableResource obtain(Context context, Bitmap bitmap) {
        return (LazyBitmapDrawableResource) obtain(context.getResources(), BitmapResource.obtain(bitmap, Glide.get(context).getBitmapPool()));
    }

    @Deprecated
    public static LazyBitmapDrawableResource obtain(Resources resources, BitmapPool bitmapPool, Bitmap bitmap) {
        return (LazyBitmapDrawableResource) obtain(resources, BitmapResource.obtain(bitmap, bitmapPool));
    }

    @Nullable
    public static Resource<BitmapDrawable> obtain(@NonNull Resources resources, @Nullable Resource<Bitmap> resource) {
        if (resource == null) {
            return null;
        }
        return new LazyBitmapDrawableResource(resources, resource);
    }

    private LazyBitmapDrawableResource(@NonNull Resources resources, @NonNull Resource<Bitmap> resource) {
        this.a = (Resources) Preconditions.checkNotNull(resources);
        this.b = (Resource) Preconditions.checkNotNull(resource);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<BitmapDrawable> getResourceClass() {
        return BitmapDrawable.class;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public BitmapDrawable get() {
        return new BitmapDrawable(this.a, this.b.get());
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return this.b.getSize();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
        this.b.recycle();
    }

    @Override // com.bumptech.glide.load.engine.Initializable
    public void initialize() {
        Resource<Bitmap> resource = this.b;
        if (resource instanceof Initializable) {
            ((Initializable) resource).initialize();
        }
    }
}
