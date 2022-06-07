package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.Resource;

/* compiled from: NonOwnedDrawableResource.java */
/* loaded from: classes.dex */
public final class a extends DrawableResource<Drawable> {
    @Override // com.bumptech.glide.load.engine.Resource
    public void recycle() {
    }

    @Nullable
    public static Resource<Drawable> a(@Nullable Drawable drawable) {
        if (drawable != null) {
            return new a(drawable);
        }
        return null;
    }

    private a(Drawable drawable) {
        super(drawable);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    @NonNull
    public Class<Drawable> getResourceClass() {
        return this.drawable.getClass();
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public int getSize() {
        return Math.max(1, this.drawable.getIntrinsicWidth() * this.drawable.getIntrinsicHeight() * 4);
    }
}
