package com.bumptech.glide.load.resource.transcode;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.gif.GifDrawable;

/* loaded from: classes.dex */
public final class DrawableBytesTranscoder implements ResourceTranscoder<Drawable, byte[]> {
    private final BitmapPool a;
    private final ResourceTranscoder<Bitmap, byte[]> b;
    private final ResourceTranscoder<GifDrawable, byte[]> c;

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    private static Resource<GifDrawable> a(@NonNull Resource<Drawable> resource) {
        return resource;
    }

    public DrawableBytesTranscoder(@NonNull BitmapPool bitmapPool, @NonNull ResourceTranscoder<Bitmap, byte[]> resourceTranscoder, @NonNull ResourceTranscoder<GifDrawable, byte[]> resourceTranscoder2) {
        this.a = bitmapPool;
        this.b = resourceTranscoder;
        this.c = resourceTranscoder2;
    }

    @Override // com.bumptech.glide.load.resource.transcode.ResourceTranscoder
    @Nullable
    public Resource<byte[]> transcode(@NonNull Resource<Drawable> resource, @NonNull Options options) {
        Drawable drawable = resource.get();
        if (drawable instanceof BitmapDrawable) {
            return this.b.transcode(BitmapResource.obtain(((BitmapDrawable) drawable).getBitmap(), this.a), options);
        }
        if (drawable instanceof GifDrawable) {
            return this.c.transcode(a(resource), options);
        }
        return null;
    }
}
