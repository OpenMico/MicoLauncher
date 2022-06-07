package com.xiaomi.micolauncher.application;

import android.content.res.Resources;
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
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

/* loaded from: classes3.dex */
public final class GlideOptions extends RequestOptions implements Cloneable {
    private static GlideOptions a;
    private static GlideOptions b;
    private static GlideOptions c;
    private static GlideOptions d;
    private static GlideOptions e;
    private static GlideOptions f;

    @NonNull
    @CheckResult
    public static GlideOptions sizeMultiplierOf(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return new GlideOptions().sizeMultiplier(f2);
    }

    @NonNull
    @CheckResult
    public static GlideOptions diskCacheStrategyOf(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return new GlideOptions().diskCacheStrategy(diskCacheStrategy);
    }

    @NonNull
    @CheckResult
    public static GlideOptions priorityOf(@NonNull Priority priority) {
        return new GlideOptions().priority(priority);
    }

    @NonNull
    @CheckResult
    public static GlideOptions placeholderOf(@Nullable Drawable drawable) {
        return new GlideOptions().placeholder(drawable);
    }

    @NonNull
    @CheckResult
    public static GlideOptions placeholderOf(@DrawableRes int i) {
        return new GlideOptions().placeholder(i);
    }

    @NonNull
    @CheckResult
    public static GlideOptions errorOf(@Nullable Drawable drawable) {
        return new GlideOptions().error(drawable);
    }

    @NonNull
    @CheckResult
    public static GlideOptions errorOf(@DrawableRes int i) {
        return new GlideOptions().error(i);
    }

    @NonNull
    @CheckResult
    public static GlideOptions skipMemoryCacheOf(boolean z) {
        return new GlideOptions().skipMemoryCache(z);
    }

    @NonNull
    @CheckResult
    public static GlideOptions overrideOf(int i, int i2) {
        return new GlideOptions().override(i, i2);
    }

    @NonNull
    @CheckResult
    public static GlideOptions overrideOf(int i) {
        return new GlideOptions().override(i);
    }

    @NonNull
    @CheckResult
    public static GlideOptions signatureOf(@NonNull Key key) {
        return new GlideOptions().signature(key);
    }

    @NonNull
    @CheckResult
    public static GlideOptions fitCenterTransform() {
        if (a == null) {
            a = new GlideOptions().fitCenter().autoClone();
        }
        return a;
    }

    @NonNull
    @CheckResult
    public static GlideOptions centerInsideTransform() {
        if (b == null) {
            b = new GlideOptions().centerInside().autoClone();
        }
        return b;
    }

    @NonNull
    @CheckResult
    public static GlideOptions centerCropTransform() {
        if (c == null) {
            c = new GlideOptions().centerCrop().autoClone();
        }
        return c;
    }

    @NonNull
    @CheckResult
    public static GlideOptions circleCropTransform() {
        if (d == null) {
            d = new GlideOptions().circleCrop().autoClone();
        }
        return d;
    }

    @NonNull
    @CheckResult
    public static GlideOptions bitmapTransform(@NonNull Transformation<Bitmap> transformation) {
        return new GlideOptions().transform2(transformation);
    }

    @NonNull
    @CheckResult
    public static GlideOptions noTransformation() {
        if (e == null) {
            e = new GlideOptions().dontTransform().autoClone();
        }
        return e;
    }

    @NonNull
    @CheckResult
    public static <T> GlideOptions option(@NonNull Option<T> option, @NonNull T t) {
        return new GlideOptions().set2((Option<Option<T>>) option, (Option<T>) t);
    }

    @NonNull
    @CheckResult
    public static GlideOptions decodeTypeOf(@NonNull Class<?> cls) {
        return new GlideOptions().decode2(cls);
    }

    @NonNull
    @CheckResult
    public static GlideOptions formatOf(@NonNull DecodeFormat decodeFormat) {
        return new GlideOptions().format(decodeFormat);
    }

    @NonNull
    @CheckResult
    public static GlideOptions frameOf(@IntRange(from = 0) long j) {
        return new GlideOptions().frame(j);
    }

    @NonNull
    @CheckResult
    public static GlideOptions downsampleOf(@NonNull DownsampleStrategy downsampleStrategy) {
        return new GlideOptions().downsample(downsampleStrategy);
    }

    @NonNull
    @CheckResult
    public static GlideOptions timeoutOf(@IntRange(from = 0) int i) {
        return new GlideOptions().timeout(i);
    }

    @NonNull
    @CheckResult
    public static GlideOptions encodeQualityOf(@IntRange(from = 0, to = 100) int i) {
        return new GlideOptions().encodeQuality(i);
    }

    @NonNull
    @CheckResult
    public static GlideOptions encodeFormatOf(@NonNull Bitmap.CompressFormat compressFormat) {
        return new GlideOptions().encodeFormat(compressFormat);
    }

    @NonNull
    @CheckResult
    public static GlideOptions noAnimation() {
        if (f == null) {
            f = new GlideOptions().dontAnimate().autoClone();
        }
        return f;
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions sizeMultiplier(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        return (GlideOptions) super.sizeMultiplier(f2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions useUnlimitedSourceGeneratorsPool(boolean z) {
        return (GlideOptions) super.useUnlimitedSourceGeneratorsPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions useAnimationPool(boolean z) {
        return (GlideOptions) super.useAnimationPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions onlyRetrieveFromCache(boolean z) {
        return (GlideOptions) super.onlyRetrieveFromCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return (GlideOptions) super.diskCacheStrategy(diskCacheStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions priority(@NonNull Priority priority) {
        return (GlideOptions) super.priority(priority);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions placeholder(@Nullable Drawable drawable) {
        return (GlideOptions) super.placeholder(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions placeholder(@DrawableRes int i) {
        return (GlideOptions) super.placeholder(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions fallback(@Nullable Drawable drawable) {
        return (GlideOptions) super.fallback(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions fallback(@DrawableRes int i) {
        return (GlideOptions) super.fallback(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions error(@Nullable Drawable drawable) {
        return (GlideOptions) super.error(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions error(@DrawableRes int i) {
        return (GlideOptions) super.error(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions theme(@Nullable Resources.Theme theme) {
        return (GlideOptions) super.theme(theme);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions skipMemoryCache(boolean z) {
        return (GlideOptions) super.skipMemoryCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions override(int i, int i2) {
        return (GlideOptions) super.override(i, i2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions override(int i) {
        return (GlideOptions) super.override(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions signature(@NonNull Key key) {
        return (GlideOptions) super.signature(key);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    public RequestOptions clone() {
        return (GlideOptions) super.clone();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    /* renamed from: set  reason: avoid collision after fix types in other method */
    public <Y> RequestOptions set2(@NonNull Option<Y> option, @NonNull Y y) {
        return (GlideOptions) super.set((Option<Option<Y>>) option, (Option<Y>) y);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    /* renamed from: decode  reason: avoid collision after fix types in other method */
    public RequestOptions decode2(@NonNull Class<?> cls) {
        return (GlideOptions) super.decode(cls);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions encodeFormat(@NonNull Bitmap.CompressFormat compressFormat) {
        return (GlideOptions) super.encodeFormat(compressFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions encodeQuality(@IntRange(from = 0, to = 100) int i) {
        return (GlideOptions) super.encodeQuality(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions frame(@IntRange(from = 0) long j) {
        return (GlideOptions) super.frame(j);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions format(@NonNull DecodeFormat decodeFormat) {
        return (GlideOptions) super.format(decodeFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions disallowHardwareConfig() {
        return (GlideOptions) super.disallowHardwareConfig();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions downsample(@NonNull DownsampleStrategy downsampleStrategy) {
        return (GlideOptions) super.downsample(downsampleStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions timeout(@IntRange(from = 0) int i) {
        return (GlideOptions) super.timeout(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions optionalCenterCrop() {
        return (GlideOptions) super.optionalCenterCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions centerCrop() {
        return (GlideOptions) super.centerCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions optionalFitCenter() {
        return (GlideOptions) super.optionalFitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions fitCenter() {
        return (GlideOptions) super.fitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions optionalCenterInside() {
        return (GlideOptions) super.optionalCenterInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions centerInside() {
        return (GlideOptions) super.centerInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions optionalCircleCrop() {
        return (GlideOptions) super.optionalCircleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions circleCrop() {
        return (GlideOptions) super.circleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    /* renamed from: transform  reason: avoid collision after fix types in other method */
    public RequestOptions transform2(@NonNull Transformation<Bitmap> transformation) {
        return (GlideOptions) super.transform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @SafeVarargs
    @CheckResult
    /* renamed from: transform  reason: avoid collision after fix types in other method */
    public final RequestOptions transform2(@NonNull Transformation<Bitmap>... transformationArr) {
        return (GlideOptions) super.transform(transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @Deprecated
    @SafeVarargs
    @CheckResult
    /* renamed from: transforms  reason: avoid collision after fix types in other method */
    public final RequestOptions transforms2(@NonNull Transformation<Bitmap>... transformationArr) {
        return (GlideOptions) super.transforms(transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    /* renamed from: optionalTransform  reason: avoid collision after fix types in other method */
    public RequestOptions optionalTransform2(@NonNull Transformation<Bitmap> transformation) {
        return (GlideOptions) super.optionalTransform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> RequestOptions optionalTransform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (GlideOptions) super.optionalTransform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public <Y> RequestOptions transform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (GlideOptions) super.transform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions dontTransform() {
        return (GlideOptions) super.dontTransform();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestOptions dontAnimate() {
        return (GlideOptions) super.dontAnimate();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    /* renamed from: apply  reason: avoid collision after fix types in other method */
    public RequestOptions apply2(@NonNull BaseRequestOptions<?> baseRequestOptions) {
        return (GlideOptions) super.apply(baseRequestOptions);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public RequestOptions lock() {
        return (GlideOptions) super.lock();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public RequestOptions autoClone() {
        return (GlideOptions) super.autoClone();
    }
}
