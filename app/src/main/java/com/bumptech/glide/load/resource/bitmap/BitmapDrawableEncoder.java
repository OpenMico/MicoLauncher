package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.io.File;

/* loaded from: classes.dex */
public class BitmapDrawableEncoder implements ResourceEncoder<BitmapDrawable> {
    private final BitmapPool a;
    private final ResourceEncoder<Bitmap> b;

    public BitmapDrawableEncoder(BitmapPool bitmapPool, ResourceEncoder<Bitmap> resourceEncoder) {
        this.a = bitmapPool;
        this.b = resourceEncoder;
    }

    public boolean encode(@NonNull Resource<BitmapDrawable> resource, @NonNull File file, @NonNull Options options) {
        return this.b.encode(new BitmapResource(resource.get().getBitmap(), this.a), file, options);
    }

    @Override // com.bumptech.glide.load.ResourceEncoder
    @NonNull
    public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
        return this.b.getEncodeStrategy(options);
    }
}
