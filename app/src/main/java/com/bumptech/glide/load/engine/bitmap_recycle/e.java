package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;

/* compiled from: LruPoolStrategy.java */
/* loaded from: classes.dex */
interface e {
    @Nullable
    Bitmap get(int i, int i2, Bitmap.Config config);

    int getSize(Bitmap bitmap);

    String logBitmap(int i, int i2, Bitmap.Config config);

    String logBitmap(Bitmap bitmap);

    void put(Bitmap bitmap);

    @Nullable
    Bitmap removeLast();
}
