package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.Util;

/* loaded from: classes.dex */
public final class UnitBitmapDecoder implements ResourceDecoder<Bitmap, Bitmap> {
    public boolean handles(@NonNull Bitmap bitmap, @NonNull Options options) {
        return true;
    }

    public Resource<Bitmap> decode(@NonNull Bitmap bitmap, int i, int i2, @NonNull Options options) {
        return new a(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a implements Resource<Bitmap> {
        private final Bitmap a;

        @Override // com.bumptech.glide.load.engine.Resource
        public void recycle() {
        }

        a(@NonNull Bitmap bitmap) {
            this.a = bitmap;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        @NonNull
        public Class<Bitmap> getResourceClass() {
            return Bitmap.class;
        }

        @NonNull
        /* renamed from: a */
        public Bitmap get() {
            return this.a;
        }

        @Override // com.bumptech.glide.load.engine.Resource
        public int getSize() {
            return Util.getBitmapByteSize(this.a);
        }
    }
}
