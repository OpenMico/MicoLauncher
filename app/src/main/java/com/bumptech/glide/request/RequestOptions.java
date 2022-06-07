package com.bumptech.glide.request;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

/* loaded from: classes.dex */
public class RequestOptions extends BaseRequestOptions<RequestOptions> {
    @Nullable
    private static RequestOptions a;
    @Nullable
    private static RequestOptions b;
    @Nullable
    private static RequestOptions c;
    @Nullable
    private static RequestOptions d;
    @Nullable
    private static RequestOptions e;
    @Nullable
    private static RequestOptions f;
    @Nullable
    private static RequestOptions g;
    @Nullable
    private static RequestOptions h;

    @NonNull
    @CheckResult
    public static RequestOptions sizeMultiplierOf(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return new RequestOptions().sizeMultiplier(f2);
    }

    @NonNull
    @CheckResult
    public static RequestOptions diskCacheStrategyOf(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return new RequestOptions().diskCacheStrategy(diskCacheStrategy);
    }

    @NonNull
    @CheckResult
    public static RequestOptions priorityOf(@NonNull Priority priority) {
        return new RequestOptions().priority(priority);
    }

    @NonNull
    @CheckResult
    public static RequestOptions placeholderOf(@Nullable Drawable drawable) {
        return new RequestOptions().placeholder(drawable);
    }

    @NonNull
    @CheckResult
    public static RequestOptions placeholderOf(@DrawableRes int i) {
        return new RequestOptions().placeholder(i);
    }

    @NonNull
    @CheckResult
    public static RequestOptions errorOf(@Nullable Drawable drawable) {
        return new RequestOptions().error(drawable);
    }

    @NonNull
    @CheckResult
    public static RequestOptions errorOf(@DrawableRes int i) {
        return new RequestOptions().error(i);
    }

    @NonNull
    @CheckResult
    public static RequestOptions skipMemoryCacheOf(boolean z) {
        if (z) {
            if (a == null) {
                a = new RequestOptions().skipMemoryCache(true).autoClone();
            }
            return a;
        }
        if (b == null) {
            b = new RequestOptions().skipMemoryCache(false).autoClone();
        }
        return b;
    }

    @NonNull
    @CheckResult
    public static RequestOptions overrideOf(int i, int i2) {
        return new RequestOptions().override(i, i2);
    }

    @NonNull
    @CheckResult
    public static RequestOptions overrideOf(int i) {
        return overrideOf(i, i);
    }

    @NonNull
    @CheckResult
    public static RequestOptions signatureOf(@NonNull Key key) {
        return new RequestOptions().signature(key);
    }

    @NonNull
    @CheckResult
    public static RequestOptions fitCenterTransform() {
        if (c == null) {
            c = new RequestOptions().fitCenter().autoClone();
        }
        return c;
    }

    @NonNull
    @CheckResult
    public static RequestOptions centerInsideTransform() {
        if (d == null) {
            d = new RequestOptions().centerInside().autoClone();
        }
        return d;
    }

    @NonNull
    @CheckResult
    public static RequestOptions centerCropTransform() {
        if (e == null) {
            e = new RequestOptions().centerCrop().autoClone();
        }
        return e;
    }

    @NonNull
    @CheckResult
    public static RequestOptions circleCropTransform() {
        if (f == null) {
            f = new RequestOptions().circleCrop().autoClone();
        }
        return f;
    }

    @NonNull
    @CheckResult
    public static RequestOptions bitmapTransform(@NonNull Transformation<Bitmap> transformation) {
        return new RequestOptions().transform(transformation);
    }

    @NonNull
    @CheckResult
    public static RequestOptions noTransformation() {
        if (g == null) {
            g = new RequestOptions().dontTransform().autoClone();
        }
        return g;
    }

    @NonNull
    @CheckResult
    public static <T> RequestOptions option(@NonNull Option<T> option, @NonNull T t) {
        return new RequestOptions().set(option, t);
    }

    @NonNull
    @CheckResult
    public static RequestOptions decodeTypeOf(@NonNull Class<?> cls) {
        return new RequestOptions().decode(cls);
    }

    @NonNull
    @CheckResult
    public static RequestOptions formatOf(@NonNull DecodeFormat decodeFormat) {
        return new RequestOptions().format(decodeFormat);
    }

    @NonNull
    @CheckResult
    public static RequestOptions frameOf(@IntRange(from = 0) long j) {
        return new RequestOptions().frame(j);
    }

    @NonNull
    @CheckResult
    public static RequestOptions downsampleOf(@NonNull DownsampleStrategy downsampleStrategy) {
        return new RequestOptions().downsample(downsampleStrategy);
    }

    @NonNull
    @CheckResult
    public static RequestOptions timeoutOf(@IntRange(from = 0) int i) {
        return new RequestOptions().timeout(i);
    }

    @NonNull
    @CheckResult
    public static RequestOptions encodeQualityOf(@IntRange(from = 0, to = 100) int i) {
        return new RequestOptions().encodeQuality(i);
    }

    @NonNull
    @CheckResult
    public static RequestOptions encodeFormatOf(@NonNull Bitmap.CompressFormat compressFormat) {
        return new RequestOptions().encodeFormat(compressFormat);
    }

    @NonNull
    @CheckResult
    public static RequestOptions noAnimation() {
        if (h == null) {
            h = new RequestOptions().dontAnimate().autoClone();
        }
        return h;
    }
}
