package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;

/* loaded from: classes.dex */
public class UnitDrawableDecoder implements ResourceDecoder<Drawable, Drawable> {
    public boolean handles(@NonNull Drawable drawable, @NonNull Options options) {
        return true;
    }

    @Nullable
    public Resource<Drawable> decode(@NonNull Drawable drawable, int i, int i2, @NonNull Options options) {
        return a.a(drawable);
    }
}
