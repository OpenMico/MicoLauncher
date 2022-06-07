package com.bumptech.glide.load.resource.transcode;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.LazyBitmapDrawableResource;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public class BitmapDrawableTranscoder implements ResourceTranscoder<Bitmap, BitmapDrawable> {
    private final Resources a;

    public BitmapDrawableTranscoder(@NonNull Context context) {
        this(context.getResources());
    }

    @Deprecated
    public BitmapDrawableTranscoder(@NonNull Resources resources, BitmapPool bitmapPool) {
        this(resources);
    }

    public BitmapDrawableTranscoder(@NonNull Resources resources) {
        this.a = (Resources) Preconditions.checkNotNull(resources);
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    @Nullable
    public Resource<BitmapDrawable> transcode(@NonNull Resource<Bitmap> resource, @NonNull Options options) {
        return LazyBitmapDrawableResource.obtain(this.a, resource);
    }
}
