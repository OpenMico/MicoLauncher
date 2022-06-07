package com.xiaomi.micolauncher.common.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.xiaomi.micolauncher.common.utils.UiUtils;

/* loaded from: classes3.dex */
public class MicoTransformUtils {
    private static MultiTransformation<Bitmap> a;
    private static MultiTransformation<Bitmap> b;
    private static final SparseArray<MultiTransformation<Bitmap>> c = new SparseArray<>();
    private static final SparseArray<MultiTransformation<Bitmap>> d = new SparseArray<>();

    public static MultiTransformation<Bitmap> topCornerTransformation(int i) {
        if (a == null) {
            float f = i;
            a = new MultiTransformation<>(new CenterCrop(), new GranularRoundedCorners(f, f, 0.0f, 0.0f));
        }
        return a;
    }

    public static MultiTransformation<Bitmap> getTransformationWithBlurSamplingCorner(Context context, int i) {
        if (b == null) {
            b = new MultiTransformation<>(new VideoBlurTransformation(i, 10, 10), new RoundedCorners(UiUtils.getEntertainmentCornerRadius(context)));
        }
        return b;
    }

    public static MultiTransformation<Bitmap> getTransformationWithBlurCorner(int i, int i2) {
        MultiTransformation<Bitmap> multiTransformation = c.get(i);
        if (multiTransformation != null) {
            return multiTransformation;
        }
        MultiTransformation<Bitmap> multiTransformation2 = new MultiTransformation<>(new CustomBitmapTransformation(i, 10), new RoundedCorners(i2));
        c.put(i, multiTransformation2);
        return multiTransformation2;
    }

    public static MultiTransformation<Bitmap> getTransformationWithBlurCornerCenterCrop(int i, int i2) {
        MultiTransformation<Bitmap> multiTransformation = d.get(i);
        if (multiTransformation != null) {
            return multiTransformation;
        }
        MultiTransformation<Bitmap> multiTransformation2 = new MultiTransformation<>(new CustomBitmapTransformation(i, 10), new CenterCrop(), new RoundedCorners(i2));
        d.put(i, multiTransformation2);
        return multiTransformation2;
    }

    public static Bitmap.Config getNonNullConfig(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    private MicoTransformUtils() {
    }
}
