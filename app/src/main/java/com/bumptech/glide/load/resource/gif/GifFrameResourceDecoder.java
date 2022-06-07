package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/* loaded from: classes.dex */
public final class GifFrameResourceDecoder implements ResourceDecoder<GifDecoder, Bitmap> {
    private final BitmapPool a;

    public boolean handles(@NonNull GifDecoder gifDecoder, @NonNull Options options) {
        return true;
    }

    public GifFrameResourceDecoder(BitmapPool bitmapPool) {
        this.a = bitmapPool;
    }

    public Resource<Bitmap> decode(@NonNull GifDecoder gifDecoder, int i, int i2, @NonNull Options options) {
        return BitmapResource.obtain(gifDecoder.getNextFrame(), this.a);
    }
}
