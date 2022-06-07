package com.bumptech.glide.request;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableTransformation;
import com.bumptech.glide.load.resource.gif.GifOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.signature.EmptySignature;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class BaseRequestOptions<T extends BaseRequestOptions<T>> implements Cloneable {
    private int a;
    @Nullable
    private Drawable e;
    private int f;
    @Nullable
    private Drawable g;
    private int h;
    private boolean m;
    @Nullable
    private Drawable o;
    private int p;
    private boolean t;
    @Nullable
    private Resources.Theme u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean z;
    private float b = 1.0f;
    @NonNull
    private DiskCacheStrategy c = DiskCacheStrategy.AUTOMATIC;
    @NonNull
    private Priority d = Priority.NORMAL;
    private boolean i = true;
    private int j = -1;
    private int k = -1;
    @NonNull
    private Key l = EmptySignature.obtain();
    private boolean n = true;
    @NonNull
    private Options q = new Options();
    @NonNull
    private Map<Class<?>, Transformation<?>> r = new CachedHashCodeArrayMap();
    @NonNull
    private Class<?> s = Object.class;
    private boolean y = true;

    private static boolean a(int i, int i2) {
        return (i & i2) != 0;
    }

    private T b() {
        return this;
    }

    @NonNull
    @CheckResult
    public T sizeMultiplier(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (this.v) {
            return (T) clone().sizeMultiplier(f);
        }
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.b = f;
        this.a |= 2;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T useUnlimitedSourceGeneratorsPool(boolean z) {
        if (this.v) {
            return (T) clone().useUnlimitedSourceGeneratorsPool(z);
        }
        this.w = z;
        this.a |= 262144;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T useAnimationPool(boolean z) {
        if (this.v) {
            return (T) clone().useAnimationPool(z);
        }
        this.z = z;
        this.a |= 1048576;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T onlyRetrieveFromCache(boolean z) {
        if (this.v) {
            return (T) clone().onlyRetrieveFromCache(z);
        }
        this.x = z;
        this.a |= 524288;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        if (this.v) {
            return (T) clone().diskCacheStrategy(diskCacheStrategy);
        }
        this.c = (DiskCacheStrategy) Preconditions.checkNotNull(diskCacheStrategy);
        this.a |= 4;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T priority(@NonNull Priority priority) {
        if (this.v) {
            return (T) clone().priority(priority);
        }
        this.d = (Priority) Preconditions.checkNotNull(priority);
        this.a |= 8;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T placeholder(@Nullable Drawable drawable) {
        if (this.v) {
            return (T) clone().placeholder(drawable);
        }
        this.g = drawable;
        this.a |= 64;
        this.h = 0;
        this.a &= -129;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T placeholder(@DrawableRes int i) {
        if (this.v) {
            return (T) clone().placeholder(i);
        }
        this.h = i;
        this.a |= 128;
        this.g = null;
        this.a &= -65;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T fallback(@Nullable Drawable drawable) {
        if (this.v) {
            return (T) clone().fallback(drawable);
        }
        this.o = drawable;
        this.a |= 8192;
        this.p = 0;
        this.a &= -16385;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T fallback(@DrawableRes int i) {
        if (this.v) {
            return (T) clone().fallback(i);
        }
        this.p = i;
        this.a |= 16384;
        this.o = null;
        this.a &= -8193;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T error(@Nullable Drawable drawable) {
        if (this.v) {
            return (T) clone().error(drawable);
        }
        this.e = drawable;
        this.a |= 16;
        this.f = 0;
        this.a &= -33;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T error(@DrawableRes int i) {
        if (this.v) {
            return (T) clone().error(i);
        }
        this.f = i;
        this.a |= 32;
        this.e = null;
        this.a &= -17;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T theme(@Nullable Resources.Theme theme) {
        if (this.v) {
            return (T) clone().theme(theme);
        }
        this.u = theme;
        this.a |= 32768;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T skipMemoryCache(boolean z) {
        if (this.v) {
            return (T) clone().skipMemoryCache(true);
        }
        this.i = !z;
        this.a |= 256;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T override(int i, int i2) {
        if (this.v) {
            return (T) clone().override(i, i2);
        }
        this.k = i;
        this.j = i2;
        this.a |= 512;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T override(int i) {
        return override(i, i);
    }

    @NonNull
    @CheckResult
    public T signature(@NonNull Key key) {
        if (this.v) {
            return (T) clone().signature(key);
        }
        this.l = (Key) Preconditions.checkNotNull(key);
        this.a |= 1024;
        return selfOrThrowIfLocked();
    }

    @CheckResult
    public T clone() {
        try {
            T t = (T) ((BaseRequestOptions) super.clone());
            t.q = new Options();
            t.q.putAll(this.q);
            t.r = new CachedHashCodeArrayMap();
            t.r.putAll(this.r);
            t.t = false;
            t.v = false;
            return t;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @CheckResult
    public <Y> T set(@NonNull Option<Y> option, @NonNull Y y) {
        if (this.v) {
            return (T) clone().set(option, y);
        }
        Preconditions.checkNotNull(option);
        Preconditions.checkNotNull(y);
        this.q.set(option, y);
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T decode(@NonNull Class<?> cls) {
        if (this.v) {
            return (T) clone().decode(cls);
        }
        this.s = (Class) Preconditions.checkNotNull(cls);
        this.a |= 4096;
        return selfOrThrowIfLocked();
    }

    public final boolean isTransformationAllowed() {
        return this.n;
    }

    public final boolean isTransformationSet() {
        return a(2048);
    }

    public final boolean isLocked() {
        return this.t;
    }

    @NonNull
    @CheckResult
    public T encodeFormat(@NonNull Bitmap.CompressFormat compressFormat) {
        return set(BitmapEncoder.COMPRESSION_FORMAT, Preconditions.checkNotNull(compressFormat));
    }

    @NonNull
    @CheckResult
    public T encodeQuality(@IntRange(from = 0, to = 100) int i) {
        return set(BitmapEncoder.COMPRESSION_QUALITY, Integer.valueOf(i));
    }

    @NonNull
    @CheckResult
    public T frame(@IntRange(from = 0) long j) {
        return set(VideoDecoder.TARGET_FRAME, Long.valueOf(j));
    }

    @NonNull
    @CheckResult
    public T format(@NonNull DecodeFormat decodeFormat) {
        Preconditions.checkNotNull(decodeFormat);
        return (T) set(Downsampler.DECODE_FORMAT, decodeFormat).set(GifOptions.DECODE_FORMAT, decodeFormat);
    }

    @NonNull
    @CheckResult
    public T disallowHardwareConfig() {
        return set(Downsampler.ALLOW_HARDWARE_CONFIG, false);
    }

    @NonNull
    @CheckResult
    public T downsample(@NonNull DownsampleStrategy downsampleStrategy) {
        return set(DownsampleStrategy.OPTION, Preconditions.checkNotNull(downsampleStrategy));
    }

    @NonNull
    @CheckResult
    public T timeout(@IntRange(from = 0) int i) {
        return set(HttpGlideUrlLoader.TIMEOUT, Integer.valueOf(i));
    }

    @NonNull
    @CheckResult
    public T optionalCenterCrop() {
        return a(DownsampleStrategy.CENTER_OUTSIDE, new CenterCrop());
    }

    @NonNull
    @CheckResult
    public T centerCrop() {
        return b(DownsampleStrategy.CENTER_OUTSIDE, new CenterCrop());
    }

    @NonNull
    @CheckResult
    public T optionalFitCenter() {
        return d(DownsampleStrategy.FIT_CENTER, new FitCenter());
    }

    @NonNull
    @CheckResult
    public T fitCenter() {
        return c(DownsampleStrategy.FIT_CENTER, new FitCenter());
    }

    @NonNull
    @CheckResult
    public T optionalCenterInside() {
        return d(DownsampleStrategy.CENTER_INSIDE, new CenterInside());
    }

    @NonNull
    @CheckResult
    public T centerInside() {
        return c(DownsampleStrategy.CENTER_INSIDE, new CenterInside());
    }

    @NonNull
    @CheckResult
    public T optionalCircleCrop() {
        return a(DownsampleStrategy.CENTER_OUTSIDE, new CircleCrop());
    }

    @NonNull
    @CheckResult
    public T circleCrop() {
        return b(DownsampleStrategy.CENTER_INSIDE, new CircleCrop());
    }

    @NonNull
    final T a(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        if (this.v) {
            return (T) clone().a(downsampleStrategy, transformation);
        }
        downsample(downsampleStrategy);
        return a(transformation, false);
    }

    @NonNull
    @CheckResult
    final T b(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        if (this.v) {
            return (T) clone().b(downsampleStrategy, transformation);
        }
        downsample(downsampleStrategy);
        return transform(transformation);
    }

    @NonNull
    private T c(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        return a(downsampleStrategy, transformation, true);
    }

    @NonNull
    private T d(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation) {
        return a(downsampleStrategy, transformation, false);
    }

    @NonNull
    private T a(@NonNull DownsampleStrategy downsampleStrategy, @NonNull Transformation<Bitmap> transformation, boolean z) {
        T t;
        if (z) {
            t = b(downsampleStrategy, transformation);
        } else {
            t = a(downsampleStrategy, transformation);
        }
        t.y = true;
        return t;
    }

    @NonNull
    @CheckResult
    public T transform(@NonNull Transformation<Bitmap> transformation) {
        return a(transformation, true);
    }

    @NonNull
    @CheckResult
    public T transform(@NonNull Transformation<Bitmap>... transformationArr) {
        if (transformationArr.length > 1) {
            return a((Transformation<Bitmap>) new MultiTransformation(transformationArr), true);
        }
        if (transformationArr.length == 1) {
            return transform(transformationArr[0]);
        }
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    @Deprecated
    public T transforms(@NonNull Transformation<Bitmap>... transformationArr) {
        return a((Transformation<Bitmap>) new MultiTransformation(transformationArr), true);
    }

    @NonNull
    @CheckResult
    public T optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return a(transformation, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NonNull
    T a(@NonNull Transformation<Bitmap> transformation, boolean z) {
        if (this.v) {
            return (T) clone().a(transformation, z);
        }
        DrawableTransformation drawableTransformation = new DrawableTransformation(transformation, z);
        a(Bitmap.class, transformation, z);
        a(Drawable.class, drawableTransformation, z);
        a(BitmapDrawable.class, drawableTransformation.asBitmapDrawable(), z);
        a(GifDrawable.class, new GifDrawableTransformation(transformation), z);
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public <Y> T optionalTransform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return a((Class) cls, (Transformation) transformation, false);
    }

    @NonNull
    <Y> T a(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation, boolean z) {
        if (this.v) {
            return (T) clone().a(cls, transformation, z);
        }
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(transformation);
        this.r.put(cls, transformation);
        this.a |= 2048;
        this.n = true;
        this.a |= 65536;
        this.y = false;
        if (z) {
            this.a |= 131072;
            this.m = true;
        }
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public <Y> T transform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return a((Class) cls, (Transformation) transformation, true);
    }

    @NonNull
    @CheckResult
    public T dontTransform() {
        if (this.v) {
            return (T) clone().dontTransform();
        }
        this.r.clear();
        this.a &= -2049;
        this.m = false;
        this.a &= -131073;
        this.n = false;
        this.a |= 65536;
        this.y = true;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public T dontAnimate() {
        return set(GifOptions.DISABLE_ANIMATION, true);
    }

    @NonNull
    @CheckResult
    public T apply(@NonNull BaseRequestOptions<?> baseRequestOptions) {
        if (this.v) {
            return (T) clone().apply(baseRequestOptions);
        }
        if (a(baseRequestOptions.a, 2)) {
            this.b = baseRequestOptions.b;
        }
        if (a(baseRequestOptions.a, 262144)) {
            this.w = baseRequestOptions.w;
        }
        if (a(baseRequestOptions.a, 1048576)) {
            this.z = baseRequestOptions.z;
        }
        if (a(baseRequestOptions.a, 4)) {
            this.c = baseRequestOptions.c;
        }
        if (a(baseRequestOptions.a, 8)) {
            this.d = baseRequestOptions.d;
        }
        if (a(baseRequestOptions.a, 16)) {
            this.e = baseRequestOptions.e;
            this.f = 0;
            this.a &= -33;
        }
        if (a(baseRequestOptions.a, 32)) {
            this.f = baseRequestOptions.f;
            this.e = null;
            this.a &= -17;
        }
        if (a(baseRequestOptions.a, 64)) {
            this.g = baseRequestOptions.g;
            this.h = 0;
            this.a &= -129;
        }
        if (a(baseRequestOptions.a, 128)) {
            this.h = baseRequestOptions.h;
            this.g = null;
            this.a &= -65;
        }
        if (a(baseRequestOptions.a, 256)) {
            this.i = baseRequestOptions.i;
        }
        if (a(baseRequestOptions.a, 512)) {
            this.k = baseRequestOptions.k;
            this.j = baseRequestOptions.j;
        }
        if (a(baseRequestOptions.a, 1024)) {
            this.l = baseRequestOptions.l;
        }
        if (a(baseRequestOptions.a, 4096)) {
            this.s = baseRequestOptions.s;
        }
        if (a(baseRequestOptions.a, 8192)) {
            this.o = baseRequestOptions.o;
            this.p = 0;
            this.a &= -16385;
        }
        if (a(baseRequestOptions.a, 16384)) {
            this.p = baseRequestOptions.p;
            this.o = null;
            this.a &= -8193;
        }
        if (a(baseRequestOptions.a, 32768)) {
            this.u = baseRequestOptions.u;
        }
        if (a(baseRequestOptions.a, 65536)) {
            this.n = baseRequestOptions.n;
        }
        if (a(baseRequestOptions.a, 131072)) {
            this.m = baseRequestOptions.m;
        }
        if (a(baseRequestOptions.a, 2048)) {
            this.r.putAll(baseRequestOptions.r);
            this.y = baseRequestOptions.y;
        }
        if (a(baseRequestOptions.a, 524288)) {
            this.x = baseRequestOptions.x;
        }
        if (!this.n) {
            this.r.clear();
            this.a &= -2049;
            this.m = false;
            this.a &= -131073;
            this.y = true;
        }
        this.a |= baseRequestOptions.a;
        this.q.putAll(baseRequestOptions.q);
        return selfOrThrowIfLocked();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BaseRequestOptions)) {
            return false;
        }
        BaseRequestOptions baseRequestOptions = (BaseRequestOptions) obj;
        return Float.compare(baseRequestOptions.b, this.b) == 0 && this.f == baseRequestOptions.f && Util.bothNullOrEqual(this.e, baseRequestOptions.e) && this.h == baseRequestOptions.h && Util.bothNullOrEqual(this.g, baseRequestOptions.g) && this.p == baseRequestOptions.p && Util.bothNullOrEqual(this.o, baseRequestOptions.o) && this.i == baseRequestOptions.i && this.j == baseRequestOptions.j && this.k == baseRequestOptions.k && this.m == baseRequestOptions.m && this.n == baseRequestOptions.n && this.w == baseRequestOptions.w && this.x == baseRequestOptions.x && this.c.equals(baseRequestOptions.c) && this.d == baseRequestOptions.d && this.q.equals(baseRequestOptions.q) && this.r.equals(baseRequestOptions.r) && this.s.equals(baseRequestOptions.s) && Util.bothNullOrEqual(this.l, baseRequestOptions.l) && Util.bothNullOrEqual(this.u, baseRequestOptions.u);
    }

    public int hashCode() {
        return Util.hashCode(this.u, Util.hashCode(this.l, Util.hashCode(this.s, Util.hashCode(this.r, Util.hashCode(this.q, Util.hashCode(this.d, Util.hashCode(this.c, Util.hashCode(this.x, Util.hashCode(this.w, Util.hashCode(this.n, Util.hashCode(this.m, Util.hashCode(this.k, Util.hashCode(this.j, Util.hashCode(this.i, Util.hashCode(this.o, Util.hashCode(this.p, Util.hashCode(this.g, Util.hashCode(this.h, Util.hashCode(this.e, Util.hashCode(this.f, Util.hashCode(this.b)))))))))))))))))))));
    }

    @NonNull
    public T lock() {
        this.t = true;
        return b();
    }

    @NonNull
    public T autoClone() {
        if (!this.t || this.v) {
            this.v = true;
            return lock();
        }
        throw new IllegalStateException("You cannot auto lock an already locked options object, try clone() first");
    }

    @NonNull
    protected final T selfOrThrowIfLocked() {
        if (!this.t) {
            return b();
        }
        throw new IllegalStateException("You cannot modify locked T, consider clone()");
    }

    protected final boolean isAutoCloneEnabled() {
        return this.v;
    }

    public final boolean isDiskCacheStrategySet() {
        return a(4);
    }

    public final boolean isSkipMemoryCacheSet() {
        return a(256);
    }

    @NonNull
    public final Map<Class<?>, Transformation<?>> getTransformations() {
        return this.r;
    }

    public final boolean isTransformationRequired() {
        return this.m;
    }

    @NonNull
    public final Options getOptions() {
        return this.q;
    }

    @NonNull
    public final Class<?> getResourceClass() {
        return this.s;
    }

    @NonNull
    public final DiskCacheStrategy getDiskCacheStrategy() {
        return this.c;
    }

    @Nullable
    public final Drawable getErrorPlaceholder() {
        return this.e;
    }

    public final int getErrorId() {
        return this.f;
    }

    public final int getPlaceholderId() {
        return this.h;
    }

    @Nullable
    public final Drawable getPlaceholderDrawable() {
        return this.g;
    }

    public final int getFallbackId() {
        return this.p;
    }

    @Nullable
    public final Drawable getFallbackDrawable() {
        return this.o;
    }

    @Nullable
    public final Resources.Theme getTheme() {
        return this.u;
    }

    public final boolean isMemoryCacheable() {
        return this.i;
    }

    @NonNull
    public final Key getSignature() {
        return this.l;
    }

    public final boolean isPrioritySet() {
        return a(8);
    }

    @NonNull
    public final Priority getPriority() {
        return this.d;
    }

    public final int getOverrideWidth() {
        return this.k;
    }

    public final boolean isValidOverride() {
        return Util.isValidDimensions(this.k, this.j);
    }

    public final int getOverrideHeight() {
        return this.j;
    }

    public final float getSizeMultiplier() {
        return this.b;
    }

    public boolean a() {
        return this.y;
    }

    private boolean a(int i) {
        return a(this.a, i);
    }

    public final boolean getUseUnlimitedSourceGeneratorsPool() {
        return this.w;
    }

    public final boolean getUseAnimationPool() {
        return this.z;
    }

    public final boolean getOnlyRetrieveFromCache() {
        return this.x;
    }
}
