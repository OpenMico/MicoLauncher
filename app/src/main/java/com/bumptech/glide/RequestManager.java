package com.bumptech.glide;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.ConnectivityMonitor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.manager.TargetTracker;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class RequestManager implements ComponentCallbacks2, LifecycleListener {
    private static final RequestOptions b = RequestOptions.decodeTypeOf(Bitmap.class).lock();
    private static final RequestOptions c = RequestOptions.decodeTypeOf(GifDrawable.class).lock();
    private static final RequestOptions d = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA).priority(Priority.LOW).skipMemoryCache(true);
    final Lifecycle a;
    protected final Context context;
    @GuardedBy("this")
    private final RequestTracker e;
    @GuardedBy("this")
    private final RequestManagerTreeNode f;
    @GuardedBy("this")
    private final TargetTracker g;
    protected final Glide glide;
    private final Runnable h;
    private final ConnectivityMonitor i;
    private final CopyOnWriteArrayList<RequestListener<Object>> j;
    @GuardedBy("this")
    private RequestOptions k;
    private boolean l;

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    public RequestManager(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context) {
        this(glide, lifecycle, requestManagerTreeNode, new RequestTracker(), glide.a(), context);
    }

    RequestManager(Glide glide, Lifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode, RequestTracker requestTracker, ConnectivityMonitorFactory connectivityMonitorFactory, Context context) {
        this.g = new TargetTracker();
        this.h = new Runnable() { // from class: com.bumptech.glide.RequestManager.1
            @Override // java.lang.Runnable
            public void run() {
                RequestManager.this.a.addListener(RequestManager.this);
            }
        };
        this.glide = glide;
        this.a = lifecycle;
        this.f = requestManagerTreeNode;
        this.e = requestTracker;
        this.context = context;
        this.i = connectivityMonitorFactory.build(context.getApplicationContext(), new b(requestTracker));
        if (Util.isOnBackgroundThread()) {
            Util.postOnUiThread(this.h);
        } else {
            lifecycle.addListener(this);
        }
        lifecycle.addListener(this.i);
        this.j = new CopyOnWriteArrayList<>(glide.b().getDefaultRequestListeners());
        setRequestOptions(glide.b().getDefaultRequestOptions());
        glide.a(this);
    }

    public synchronized void setRequestOptions(@NonNull RequestOptions requestOptions) {
        this.k = requestOptions.clone().autoClone();
    }

    private synchronized void a(@NonNull RequestOptions requestOptions) {
        this.k = this.k.apply(requestOptions);
    }

    @NonNull
    public synchronized RequestManager applyDefaultRequestOptions(@NonNull RequestOptions requestOptions) {
        a(requestOptions);
        return this;
    }

    @NonNull
    public synchronized RequestManager setDefaultRequestOptions(@NonNull RequestOptions requestOptions) {
        setRequestOptions(requestOptions);
        return this;
    }

    public RequestManager addDefaultRequestListener(RequestListener<Object> requestListener) {
        this.j.add(requestListener);
        return this;
    }

    public void setPauseAllRequestsOnTrimMemoryModerate(boolean z) {
        this.l = z;
    }

    public synchronized boolean isPaused() {
        return this.e.isPaused();
    }

    public synchronized void pauseRequests() {
        this.e.pauseRequests();
    }

    public synchronized void pauseAllRequests() {
        this.e.pauseAllRequests();
    }

    public synchronized void pauseAllRequestsRecursive() {
        pauseAllRequests();
        for (RequestManager requestManager : this.f.getDescendants()) {
            requestManager.pauseAllRequests();
        }
    }

    public synchronized void pauseRequestsRecursive() {
        pauseRequests();
        for (RequestManager requestManager : this.f.getDescendants()) {
            requestManager.pauseRequests();
        }
    }

    public synchronized void resumeRequests() {
        this.e.resumeRequests();
    }

    public synchronized void resumeRequestsRecursive() {
        Util.assertMainThread();
        resumeRequests();
        for (RequestManager requestManager : this.f.getDescendants()) {
            requestManager.resumeRequests();
        }
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public synchronized void onStart() {
        resumeRequests();
        this.g.onStart();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public synchronized void onStop() {
        pauseRequests();
        this.g.onStop();
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public synchronized void onDestroy() {
        this.g.onDestroy();
        for (Target<?> target : this.g.getAll()) {
            clear(target);
        }
        this.g.clear();
        this.e.clearRequests();
        this.a.removeListener(this);
        this.a.removeListener(this.i);
        Util.removeCallbacksOnUiThread(this.h);
        this.glide.b(this);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Bitmap> asBitmap() {
        return as(Bitmap.class).apply((BaseRequestOptions<?>) b);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<GifDrawable> asGif() {
        return as(GifDrawable.class).apply((BaseRequestOptions<?>) c);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> asDrawable() {
        return as(Drawable.class);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Bitmap bitmap) {
        return asDrawable().load(bitmap);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Drawable drawable) {
        return asDrawable().load(drawable);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable String str) {
        return asDrawable().load(str);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Uri uri) {
        return asDrawable().load(uri);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable File file) {
        return asDrawable().load(file);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable @DrawableRes @RawRes Integer num) {
        return asDrawable().load(num);
    }

    @CheckResult
    @Deprecated
    public RequestBuilder<Drawable> load(@Nullable URL url) {
        return asDrawable().load(url);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable byte[] bArr) {
        return asDrawable().load(bArr);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<Drawable> load(@Nullable Object obj) {
        return asDrawable().load(obj);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<File> downloadOnly() {
        return as(File.class).apply((BaseRequestOptions<?>) d);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<File> download(@Nullable Object obj) {
        return downloadOnly().load(obj);
    }

    @NonNull
    @CheckResult
    public RequestBuilder<File> asFile() {
        return as(File.class).apply((BaseRequestOptions<?>) RequestOptions.skipMemoryCacheOf(true));
    }

    @NonNull
    @CheckResult
    public <ResourceType> RequestBuilder<ResourceType> as(@NonNull Class<ResourceType> cls) {
        return new RequestBuilder<>(this.glide, this, cls, this.context);
    }

    public void clear(@NonNull View view) {
        clear(new a(view));
    }

    public void clear(@Nullable Target<?> target) {
        if (target != null) {
            b(target);
        }
    }

    private void b(@NonNull Target<?> target) {
        boolean a2 = a(target);
        Request request = target.getRequest();
        if (!a2 && !this.glide.a(target) && request != null) {
            target.setRequest(null);
            request.clear();
        }
    }

    public synchronized boolean a(@NonNull Target<?> target) {
        Request request = target.getRequest();
        if (request == null) {
            return true;
        }
        if (!this.e.clearAndRemove(request)) {
            return false;
        }
        this.g.untrack(target);
        target.setRequest(null);
        return true;
    }

    public synchronized void a(@NonNull Target<?> target, @NonNull Request request) {
        this.g.track(target);
        this.e.runRequest(request);
    }

    public List<RequestListener<Object>> a() {
        return this.j;
    }

    public synchronized RequestOptions b() {
        return this.k;
    }

    @NonNull
    public <T> TransitionOptions<?, T> a(Class<T> cls) {
        return this.glide.b().getDefaultTransitionOptions(cls);
    }

    public synchronized String toString() {
        return super.toString() + "{tracker=" + this.e + ", treeNode=" + this.f + "}";
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        if (i == 60 && this.l) {
            pauseAllRequestsRecursive();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class b implements ConnectivityMonitor.ConnectivityListener {
        @GuardedBy("RequestManager.this")
        private final RequestTracker b;

        b(RequestTracker requestTracker) {
            RequestManager.this = r1;
            this.b = requestTracker;
        }

        @Override // com.bumptech.glide.manager.ConnectivityMonitor.ConnectivityListener
        public void onConnectivityChanged(boolean z) {
            if (z) {
                synchronized (RequestManager.this) {
                    this.b.restartRequests();
                }
            }
        }
    }

    /* loaded from: classes.dex */
    private static class a extends CustomViewTarget<View, Object> {
        @Override // com.bumptech.glide.request.target.Target
        public void onLoadFailed(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.CustomViewTarget
        protected void onResourceCleared(@Nullable Drawable drawable) {
        }

        @Override // com.bumptech.glide.request.target.Target
        public void onResourceReady(@NonNull Object obj, @Nullable Transition<? super Object> transition) {
        }

        a(@NonNull View view) {
            super(view);
        }
    }
}
