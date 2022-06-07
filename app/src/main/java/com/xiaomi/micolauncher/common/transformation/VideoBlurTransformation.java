package com.xiaomi.micolauncher.common.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.util.Util;
import com.xiaomi.mico.router.proxy.Proxies;
import com.xiaomi.micolauncher.module.homepage.manager.BlurManager;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes3.dex */
public class VideoBlurTransformation extends BitmapTransformation {
    private static final byte[] a = "com.xiaomi.micolauncher.common.transformation.VideoBlurTransformation".getBytes(CHARSET);
    private final int b;
    private final int c;
    private final int d;

    public VideoBlurTransformation(int i, int i2, int i3) {
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    protected Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap bitmap, int i, int i2) {
        Throwable th;
        Exception e;
        Bitmap centerCrop = TransformationUtils.centerCrop(bitmapPool, bitmap, i, i2 - this.b);
        Bitmap bitmap2 = null;
        try {
            try {
                bitmap2 = bitmapPool.get(i, i2, MicoTransformUtils.getNonNullConfig(centerCrop));
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            Canvas canvas = new Canvas(bitmap2);
            Paint paint = new Paint();
            paint.setFlags(2);
            canvas.drawBitmap(centerCrop, 0.0f, 0.0f, paint);
            canvas.drawBitmap(centerCrop, new Rect(0, centerCrop.getHeight() - this.b, i, centerCrop.getHeight()), new Rect(0, i2 - this.b, i, i2), (Paint) null);
            Bitmap blur = BlurManager.getManager().blur(Proxies.getApp(), bitmapPool, bitmap2, this.b, this.c, this.d);
            if (!bitmap.equals(centerCrop)) {
                bitmapPool.put(centerCrop);
            }
            if (bitmap2 != null) {
                bitmapPool.put(bitmap2);
            }
            return blur;
        } catch (Exception e3) {
            e = e3;
            bitmap2 = bitmap2;
            e.printStackTrace();
            if (!bitmap.equals(centerCrop)) {
                bitmapPool.put(centerCrop);
            }
            if (bitmap2 == null) {
                return bitmap;
            }
            bitmapPool.put(bitmap2);
            return bitmap;
        } catch (Throwable th3) {
            th = th3;
            if (!bitmap.equals(centerCrop)) {
                bitmapPool.put(centerCrop);
            }
            if (bitmap2 != null) {
                bitmapPool.put(bitmap2);
            }
            throw th;
        }
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(a);
        messageDigest.update(ByteBuffer.allocate(12).putInt(this.b).putInt(this.c).putInt(this.d).array());
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof VideoBlurTransformation)) {
            return false;
        }
        VideoBlurTransformation videoBlurTransformation = (VideoBlurTransformation) obj;
        return this.b == videoBlurTransformation.b && this.c == videoBlurTransformation.c && this.d == videoBlurTransformation.d;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return Util.hashCode(Util.hashCode(Util.hashCode("com.xiaomi.micolauncher.common.transformation.VideoBlurTransformation".hashCode(), Util.hashCode(this.b)), this.c), this.d);
    }
}
