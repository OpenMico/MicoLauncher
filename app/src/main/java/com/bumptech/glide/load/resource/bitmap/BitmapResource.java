package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

/* loaded from: classes.dex */
public class BitmapResource implements Initializable, Resource<Bitmap> {
    private final Bitmap a;
    private final BitmapPool b;

    @Nullable
    public static BitmapResource obtain(@Nullable Bitmap bitmap, @NonNull BitmapPool bitmapPool) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapResource(bitmap, bitmapPool);
    }

    public BitmapResource(@NonNull Bitmap bitmap, @NonNull BitmapPool bitmapPool) {
        this.a = (Bitmap) Preconditions.checkNotNull(bitmap, "Bitmap must not be null");
        this.b = (BitmapPool) Preconditions.checkNotNull(bitmapPool, "BitmapPool must not be null");
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<Bitmap> getResourceClass() {
        return Bitmap.class;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Bitmap get() {
        return this.a;
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return Util.getBitmapByteSize(this.a);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
        this.b.put(this.a);
    }

    @Override // com.bumptech.glide.load.engine.Initializable
    public void initialize() {
        this.a.prepareToDraw();
    }
}
