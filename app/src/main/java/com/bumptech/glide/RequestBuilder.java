package com.bumptech.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.ErrorRequestCoordinator;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestCoordinator;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.SingleRequest;
import com.bumptech.glide.request.ThumbnailRequestCoordinator;
import com.bumptech.glide.request.target.PreloadTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.signature.AndroidResourceSignature;
import com.bumptech.glide.util.Executors;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class RequestBuilder<TranscodeType> extends BaseRequestOptions<RequestBuilder<TranscodeType>> implements Cloneable {
    protected static final RequestOptions DOWNLOAD_ONLY_OPTIONS = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).priority(Priority.LOW).skipMemoryCache(true);
    private final Context a;
    private final RequestManager b;
    private final Class<TranscodeType> c;
    private final Glide d;
    private final GlideContext e;
    @NonNull
    private TransitionOptions<?, ? super TranscodeType> f;
    @Nullable
    private Object g;
    @Nullable
    private List<RequestListener<TranscodeType>> h;
    @Nullable
    private RequestBuilder<TranscodeType> i;
    @Nullable
    private RequestBuilder<TranscodeType> j;
    @Nullable
    private Float k;
    private boolean l;
    private boolean m;
    private boolean n;

    @SuppressLint({"CheckResult"})
    public RequestBuilder(@NonNull Glide glide, RequestManager requestManager, Class<TranscodeType> cls, Context context) {
        this.l = true;
        this.d = glide;
        this.b = requestManager;
        this.c = cls;
        this.a = context;
        this.f = requestManager.a(cls);
        this.e = glide.b();
        a(requestManager.a());
        apply((BaseRequestOptions<?>) requestManager.b());
    }

    @SuppressLint({"CheckResult"})
    public RequestBuilder(Class<TranscodeType> cls, RequestBuilder<?> requestBuilder) {
        this(requestBuilder.d, requestBuilder.b, cls, requestBuilder.a);
        this.g = requestBuilder.g;
        this.m = requestBuilder.m;
        apply((BaseRequestOptions<?>) requestBuilder);
    }

    @SuppressLint({"CheckResult"})
    private void a(List<RequestListener<Object>> list) {
        for (RequestListener<Object> requestListener : list) {
            addListener(requestListener);
        }
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> apply(@NonNull BaseRequestOptions<?> baseRequestOptions) {
        Preconditions.checkNotNull(baseRequestOptions);
        return (RequestBuilder) super.apply(baseRequestOptions);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> transition(@NonNull TransitionOptions<?, ? super TranscodeType> transitionOptions) {
        if (isAutoCloneEnabled()) {
            return clone().transition(transitionOptions);
        }
        this.f = (TransitionOptions) Preconditions.checkNotNull(transitionOptions);
        this.l = false;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> listener(@Nullable RequestListener<TranscodeType> requestListener) {
        if (isAutoCloneEnabled()) {
            return clone().listener(requestListener);
        }
        this.h = null;
        return addListener(requestListener);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> addListener(@Nullable RequestListener<TranscodeType> requestListener) {
        if (isAutoCloneEnabled()) {
            return clone().addListener(requestListener);
        }
        if (requestListener != null) {
            if (this.h == null) {
                this.h = new ArrayList();
            }
            this.h.add(requestListener);
        }
        return selfOrThrowIfLocked();
    }

    @NonNull
    public RequestBuilder<TranscodeType> error(@Nullable RequestBuilder<TranscodeType> requestBuilder) {
        if (isAutoCloneEnabled()) {
            return clone().error((RequestBuilder) requestBuilder);
        }
        this.j = requestBuilder;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> error(Object obj) {
        if (obj == null) {
            return error((RequestBuilder) null);
        }
        return error((RequestBuilder) b().load(obj));
    }

    private RequestBuilder<TranscodeType> b() {
        RequestBuilder<TranscodeType> requestBuilder = null;
        return clone().error((RequestBuilder) requestBuilder).thumbnail(requestBuilder);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> thumbnail(@Nullable RequestBuilder<TranscodeType> requestBuilder) {
        if (isAutoCloneEnabled()) {
            return clone().thumbnail(requestBuilder);
        }
        this.i = requestBuilder;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> thumbnail(@Nullable RequestBuilder<TranscodeType>... requestBuilderArr) {
        if (requestBuilderArr == null || requestBuilderArr.length == 0) {
            return thumbnail((RequestBuilder) null);
        }
        return thumbnail(Arrays.asList(requestBuilderArr));
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> thumbnail(@Nullable List<RequestBuilder<TranscodeType>> list) {
        RequestBuilder<TranscodeType> requestBuilder = null;
        if (list == null || list.isEmpty()) {
            return thumbnail((RequestBuilder) null);
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            RequestBuilder<TranscodeType> requestBuilder2 = list.get(size);
            if (requestBuilder2 != null) {
                requestBuilder = requestBuilder == null ? requestBuilder2 : requestBuilder2.thumbnail(requestBuilder);
            }
        }
        return thumbnail(requestBuilder);
    }

    @NonNull
    @CheckResult
    @Deprecated
    public RequestBuilder<TranscodeType> thumbnail(float f) {
        if (isAutoCloneEnabled()) {
            return clone().thumbnail(f);
        }
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("sizeMultiplier must be between 0 and 1");
        }
        this.k = Float.valueOf(f);
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable Object obj) {
        return a(obj);
    }

    @NonNull
    private RequestBuilder<TranscodeType> a(@Nullable Object obj) {
        if (isAutoCloneEnabled()) {
            return clone().a(obj);
        }
        this.g = obj;
        this.m = true;
        return selfOrThrowIfLocked();
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable Bitmap bitmap) {
        return a(bitmap).apply((BaseRequestOptions<?>) RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE));
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable Drawable drawable) {
        return a(drawable).apply((BaseRequestOptions<?>) RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE));
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable String str) {
        return a(str);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable Uri uri) {
        return a(uri);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable File file) {
        return a(file);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable @DrawableRes @RawRes Integer num) {
        return a(num).apply((BaseRequestOptions<?>) RequestOptions.signatureOf(AndroidResourceSignature.obtain(this.a)));
    }

    @CheckResult
    @Deprecated
    public RequestBuilder<TranscodeType> load(@Nullable URL url) {
        return a(url);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<TranscodeType> load(@Nullable byte[] bArr) {
        RequestBuilder<TranscodeType> a = a(bArr);
        if (!a.isDiskCacheStrategySet()) {
            a = a.apply((BaseRequestOptions<?>) RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE));
        }
        return !a.isSkipMemoryCacheSet() ? a.apply((BaseRequestOptions<?>) RequestOptions.skipMemoryCacheOf(true)) : a;
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    public RequestBuilder<TranscodeType> clone() {
        RequestBuilder<TranscodeType> requestBuilder = (RequestBuilder) super.clone();
        requestBuilder.f = requestBuilder.f.clone();
        List<RequestListener<TranscodeType>> list = requestBuilder.h;
        if (list != null) {
            requestBuilder.h = new ArrayList(list);
        }
        RequestBuilder<TranscodeType> requestBuilder2 = requestBuilder.i;
        if (requestBuilder2 != null) {
            requestBuilder.i = requestBuilder2.clone();
        }
        RequestBuilder<TranscodeType> requestBuilder3 = requestBuilder.j;
        if (requestBuilder3 != null) {
            requestBuilder.j = requestBuilder3.clone();
        }
        return requestBuilder;
    }

    @NonNull
    public <Y extends Target<TranscodeType>> Y into(@NonNull Y y) {
        return (Y) a((RequestBuilder<TranscodeType>) y, (RequestListener) null, Executors.mainThreadExecutor());
    }

    @NonNull
    <Y extends Target<TranscodeType>> Y a(@NonNull Y y, @Nullable RequestListener<TranscodeType> requestListener, Executor executor) {
        return (Y) a(y, requestListener, this, executor);
    }

    private <Y extends Target<TranscodeType>> Y a(@NonNull Y y, @Nullable RequestListener<TranscodeType> requestListener, BaseRequestOptions<?> baseRequestOptions, Executor executor) {
        Preconditions.checkNotNull(y);
        if (this.m) {
            Request b = b(y, requestListener, baseRequestOptions, executor);
            Request request = y.getRequest();
            if (!b.isEquivalentTo(request) || a(baseRequestOptions, request)) {
                this.b.clear((Target<?>) y);
                y.setRequest(b);
                this.b.a(y, b);
                return y;
            }
            if (!((Request) Preconditions.checkNotNull(request)).isRunning()) {
                request.begin();
            }
            return y;
        }
        throw new IllegalArgumentException("You must call #load() before calling #into()");
    }

    private boolean a(BaseRequestOptions<?> baseRequestOptions, Request request) {
        return !baseRequestOptions.isMemoryCacheable() && request.isComplete();
    }

    @NonNull
    public ViewTarget<ImageView, TranscodeType> into(@NonNull ImageView imageView) {
        RequestBuilder<TranscodeType> requestBuilder;
        Util.assertMainThread();
        Preconditions.checkNotNull(imageView);
        if (!isTransformationSet() && isTransformationAllowed() && imageView.getScaleType() != null) {
            switch (AnonymousClass1.a[imageView.getScaleType().ordinal()]) {
                case 1:
                    requestBuilder = clone().optionalCenterCrop();
                    break;
                case 2:
                    requestBuilder = clone().optionalCenterInside();
                    break;
                case 3:
                case 4:
                case 5:
                    requestBuilder = clone().optionalFitCenter();
                    break;
                case 6:
                    requestBuilder = clone().optionalCenterInside();
                    break;
            }
            return (ViewTarget) a(this.e.buildImageViewTarget(imageView, this.c), null, requestBuilder, Executors.mainThreadExecutor());
        }
        requestBuilder = this;
        return (ViewTarget) a(this.e.buildImageViewTarget(imageView, this.c), null, requestBuilder, Executors.mainThreadExecutor());
    }

    @Deprecated
    public FutureTarget<TranscodeType> into(int i, int i2) {
        return submit(i, i2);
    }

    @NonNull
    public FutureTarget<TranscodeType> submit() {
        return submit(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @NonNull
    public FutureTarget<TranscodeType> submit(int i, int i2) {
        RequestFutureTarget requestFutureTarget = new RequestFutureTarget(i, i2);
        return (FutureTarget) a((RequestBuilder<TranscodeType>) requestFutureTarget, requestFutureTarget, Executors.directExecutor());
    }

    @NonNull
    public Target<TranscodeType> preload(int i, int i2) {
        return into((RequestBuilder<TranscodeType>) PreloadTarget.obtain(this.b, i, i2));
    }

    @NonNull
    public Target<TranscodeType> preload() {
        return preload(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @CheckResult
    @Deprecated
    public <Y extends Target<File>> Y downloadOnly(@NonNull Y y) {
        return (Y) getDownloadOnlyRequest().into((RequestBuilder<File>) y);
    }

    @CheckResult
    @Deprecated
    public FutureTarget<File> downloadOnly(int i, int i2) {
        return getDownloadOnlyRequest().submit(i, i2);
    }

    @NonNull
    @CheckResult
    protected RequestBuilder<File> getDownloadOnlyRequest() {
        return new RequestBuilder(File.class, this).apply((BaseRequestOptions<?>) DOWNLOAD_ONLY_OPTIONS);
    }

    /* renamed from: com.bumptech.glide.RequestBuilder$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;

        static {
            try {
                b[Priority.LOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[Priority.NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[Priority.HIGH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[Priority.IMMEDIATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            a = new int[ImageView.ScaleType.values().length];
            try {
                a[ImageView.ScaleType.CENTER_CROP.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[ImageView.ScaleType.FIT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[ImageView.ScaleType.FIT_START.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[ImageView.ScaleType.FIT_END.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[ImageView.ScaleType.FIT_XY.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[ImageView.ScaleType.CENTER.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[ImageView.ScaleType.MATRIX.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    @NonNull
    private Priority a(@NonNull Priority priority) {
        switch (priority) {
            case LOW:
                return Priority.NORMAL;
            case NORMAL:
                return Priority.HIGH;
            case HIGH:
            case IMMEDIATE:
                return Priority.IMMEDIATE;
            default:
                throw new IllegalArgumentException("unknown priority: " + getPriority());
        }
    }

    private Request b(Target<TranscodeType> target, @Nullable RequestListener<TranscodeType> requestListener, BaseRequestOptions<?> baseRequestOptions, Executor executor) {
        return a(new Object(), target, requestListener, (RequestCoordinator) null, this.f, baseRequestOptions.getPriority(), baseRequestOptions.getOverrideWidth(), baseRequestOptions.getOverrideHeight(), baseRequestOptions, executor);
    }

    private Request a(Object obj, Target<TranscodeType> target, @Nullable RequestListener<TranscodeType> requestListener, @Nullable RequestCoordinator requestCoordinator, TransitionOptions<?, ? super TranscodeType> transitionOptions, Priority priority, int i, int i2, BaseRequestOptions<?> baseRequestOptions, Executor executor) {
        ErrorRequestCoordinator errorRequestCoordinator;
        ErrorRequestCoordinator errorRequestCoordinator2;
        if (this.j != null) {
            errorRequestCoordinator2 = new ErrorRequestCoordinator(obj, requestCoordinator);
            errorRequestCoordinator = errorRequestCoordinator2;
        } else {
            errorRequestCoordinator = null;
            errorRequestCoordinator2 = requestCoordinator;
        }
        Request b = b(obj, target, requestListener, errorRequestCoordinator2, transitionOptions, priority, i, i2, baseRequestOptions, executor);
        if (errorRequestCoordinator == null) {
            return b;
        }
        int i3 = this.j.getOverrideWidth();
        int i4 = this.j.getOverrideHeight();
        if (Util.isValidDimensions(i, i2) && !this.j.isValidOverride()) {
            i3 = baseRequestOptions.getOverrideWidth();
            i4 = baseRequestOptions.getOverrideHeight();
        }
        RequestBuilder<TranscodeType> requestBuilder = this.j;
        errorRequestCoordinator.setRequests(b, requestBuilder.a(obj, target, requestListener, errorRequestCoordinator, requestBuilder.f, requestBuilder.getPriority(), i3, i4, this.j, executor));
        return errorRequestCoordinator;
    }

    private Request b(Object obj, Target<TranscodeType> target, RequestListener<TranscodeType> requestListener, @Nullable RequestCoordinator requestCoordinator, TransitionOptions<?, ? super TranscodeType> transitionOptions, Priority priority, int i, int i2, BaseRequestOptions<?> baseRequestOptions, Executor executor) {
        Priority priority2;
        RequestBuilder<TranscodeType> requestBuilder = this.i;
        if (requestBuilder != null) {
            if (!this.n) {
                TransitionOptions<?, ? super TranscodeType> transitionOptions2 = requestBuilder.l ? transitionOptions : requestBuilder.f;
                if (this.i.isPrioritySet()) {
                    priority2 = this.i.getPriority();
                } else {
                    priority2 = a(priority);
                }
                int i3 = this.i.getOverrideWidth();
                int i4 = this.i.getOverrideHeight();
                if (Util.isValidDimensions(i, i2) && !this.i.isValidOverride()) {
                    i3 = baseRequestOptions.getOverrideWidth();
                    i4 = baseRequestOptions.getOverrideHeight();
                }
                ThumbnailRequestCoordinator thumbnailRequestCoordinator = new ThumbnailRequestCoordinator(obj, requestCoordinator);
                Request a = a(obj, target, requestListener, baseRequestOptions, thumbnailRequestCoordinator, transitionOptions, priority, i, i2, executor);
                this.n = true;
                RequestBuilder<TranscodeType> requestBuilder2 = this.i;
                Request a2 = requestBuilder2.a(obj, target, requestListener, thumbnailRequestCoordinator, transitionOptions2, priority2, i3, i4, requestBuilder2, executor);
                this.n = false;
                thumbnailRequestCoordinator.setRequests(a, a2);
                return thumbnailRequestCoordinator;
            }
            throw new IllegalStateException("You cannot use a request as both the main request and a thumbnail, consider using clone() on the request(s) passed to thumbnail()");
        } else if (this.k == null) {
            return a(obj, target, requestListener, baseRequestOptions, requestCoordinator, transitionOptions, priority, i, i2, executor);
        } else {
            ThumbnailRequestCoordinator thumbnailRequestCoordinator2 = new ThumbnailRequestCoordinator(obj, requestCoordinator);
            thumbnailRequestCoordinator2.setRequests(a(obj, target, requestListener, baseRequestOptions, thumbnailRequestCoordinator2, transitionOptions, priority, i, i2, executor), a(obj, target, requestListener, baseRequestOptions.clone().sizeMultiplier(this.k.floatValue()), thumbnailRequestCoordinator2, transitionOptions, a(priority), i, i2, executor));
            return thumbnailRequestCoordinator2;
        }
    }

    private Request a(Object obj, Target<TranscodeType> target, RequestListener<TranscodeType> requestListener, BaseRequestOptions<?> baseRequestOptions, RequestCoordinator requestCoordinator, TransitionOptions<?, ? super TranscodeType> transitionOptions, Priority priority, int i, int i2, Executor executor) {
        Context context = this.a;
        GlideContext glideContext = this.e;
        return SingleRequest.obtain(context, glideContext, obj, this.g, this.c, baseRequestOptions, i, i2, priority, target, requestListener, this.h, requestCoordinator, glideContext.getEngine(), transitionOptions.a(), executor);
    }
}
