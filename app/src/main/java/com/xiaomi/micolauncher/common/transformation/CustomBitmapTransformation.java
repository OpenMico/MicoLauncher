package com.xiaomi.micolauncher.common.transformation;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;
import com.xiaomi.mico.router.proxy.Proxies;
import com.xiaomi.micolauncher.module.homepage.manager.BlurManager;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes3.dex */
public class CustomBitmapTransformation extends BitmapTransformation {
    private static final byte[] a = "com.xiaomi.micolauncher.module.homepage.view.CustomBitmapTransformation".getBytes(CHARSET);
    private final int b;
    private int c;
    private final int d;

    public CustomBitmapTransformation(int i, int i2) {
        this.c = 10;
        this.b = i;
        this.d = i2;
    }

    public CustomBitmapTransformation(int i, int i2, int i3) {
        this.c = 10;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        return BlurManager.getManager().blur(Proxies.getApp(), bitmapPool, bitmap, this.b, this.c, this.d);
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(a);
        messageDigest.update(ByteBuffer.allocate(12).putInt(this.b).putInt(this.c).putInt(this.d).array());
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof CustomBitmapTransformation)) {
            return false;
        }
        CustomBitmapTransformation customBitmapTransformation = (CustomBitmapTransformation) obj;
        return this.b == customBitmapTransformation.b && this.c == customBitmapTransformation.c && this.d == customBitmapTransformation.d;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode(Util.hashCode(Util.hashCode("com.xiaomi.micolauncher.module.homepage.view.CustomBitmapTransformation".hashCode(), Util.hashCode(this.b)), this.c), this.d);
    }
}
