package com.xiaomi.micolauncher.common.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.RSRuntimeException;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.xiaomi.mico.base.utils.BitmapUtil;
import java.security.MessageDigest;

/* loaded from: classes3.dex */
public class BlurTransformation extends BitmapTransformation {
    private static int a = 1;
    private static int b = 25;
    private Context c;
    private int d;
    private int e;

    public BlurTransformation(Context context, int i) {
        this(context, i, b);
    }

    public BlurTransformation(Context context, int i, int i2) {
        this.c = context.getApplicationContext();
        this.d = i;
        this.e = i2;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        try {
            return BitmapUtil.blur(this.c, bitmap, this.d, this.e);
        } catch (RSRuntimeException unused) {
            return bitmap;
        }
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(("" + this.d + this.e).getBytes(CHARSET));
    }
}
