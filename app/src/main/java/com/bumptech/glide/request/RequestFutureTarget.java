package com.bumptech.glide.request;

import android.graphics.drawable.Drawable;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes.dex */
public class RequestFutureTarget<R> implements FutureTarget<R>, RequestListener<R> {
    private static final a a = new a();
    private final int b;
    private final int c;
    private final boolean d;
    private final a e;
    @Nullable
    @GuardedBy("this")
    private R f;
    @Nullable
    @GuardedBy("this")
    private Request g;
    @GuardedBy("this")
    private boolean h;
    @GuardedBy("this")
    private boolean i;
    @GuardedBy("this")
    private boolean j;
    @Nullable
    @GuardedBy("this")
    private GlideException k;

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadCleared(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadStarted(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
    }

    @Override // com.bumptech.glide.request.target.Target
    public void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
    }

    public RequestFutureTarget(int i, int i2) {
        this(i, i2, true, a);
    }

    RequestFutureTarget(int i, int i2, boolean z, a aVar) {
        this.b = i;
        this.c = i2;
        this.d = z;
        this.e = aVar;
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        Request request;
        synchronized (this) {
            if (isDone()) {
                return false;
            }
            this.h = true;
            this.e.a(this);
            if (z) {
                request = this.g;
                this.g = null;
            } else {
                request = null;
            }
            if (request != null) {
                request.clear();
            }
            return true;
        }
    }

    @Override // java.util.concurrent.Future
    public synchronized boolean isCancelled() {
        return this.h;
    }

    @Override // java.util.concurrent.Future
    public synchronized boolean isDone() {
        boolean z;
        if (!this.h && !this.i) {
            if (!this.j) {
                z = false;
            }
        }
        z = true;
        return z;
    }

    @Override // java.util.concurrent.Future
    public R get() throws InterruptedException, ExecutionException {
        try {
            return a(null);
        } catch (TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    @Override // java.util.concurrent.Future
    public R get(long j, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return a(Long.valueOf(timeUnit.toMillis(j)));
    }

    @Override // com.bumptech.glide.request.target.Target
    public void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
        sizeReadyCallback.onSizeReady(this.b, this.c);
    }

    @Override // com.bumptech.glide.request.target.Target
    public synchronized void setRequest(@Nullable Request request) {
        this.g = request;
    }

    @Override // com.bumptech.glide.request.target.Target
    @Nullable
    public synchronized Request getRequest() {
        return this.g;
    }

    @Override // com.bumptech.glide.request.target.Target
    public synchronized void onLoadFailed(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.request.target.Target
    public synchronized void onResourceReady(@NonNull R r, @Nullable Transition<? super R> transition) {
    }

    private synchronized R a(Long l) throws ExecutionException, InterruptedException, TimeoutException {
        if (this.d && !isDone()) {
            Util.assertBackgroundThread();
        }
        if (this.h) {
            throw new CancellationException();
        } else if (this.j) {
            throw new ExecutionException(this.k);
        } else if (this.i) {
            return this.f;
        } else {
            if (l == null) {
                this.e.a(this, 0L);
            } else if (l.longValue() > 0) {
                long currentTimeMillis = System.currentTimeMillis();
                long longValue = l.longValue() + currentTimeMillis;
                while (!isDone() && currentTimeMillis < longValue) {
                    this.e.a(this, longValue - currentTimeMillis);
                    currentTimeMillis = System.currentTimeMillis();
                }
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            } else if (this.j) {
                throw new ExecutionException(this.k);
            } else if (this.h) {
                throw new CancellationException();
            } else if (this.i) {
                return this.f;
            } else {
                throw new TimeoutException();
            }
        }
    }

    @Override // com.bumptech.glide.request.RequestListener
    public synchronized boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<R> target, boolean z) {
        this.j = true;
        this.k = glideException;
        this.e.a(this);
        return false;
    }

    @Override // com.bumptech.glide.request.RequestListener
    public synchronized boolean onResourceReady(R r, Object obj, Target<R> target, DataSource dataSource, boolean z) {
        this.i = true;
        this.f = r;
        this.e.a(this);
        return false;
    }

    public String toString() {
        Request request;
        String str;
        String str2 = super.toString() + "[status=";
        synchronized (this) {
            request = null;
            if (this.h) {
                str = "CANCELLED";
            } else if (this.j) {
                str = "FAILURE";
            } else if (this.i) {
                str = "SUCCESS";
            } else {
                str = "PENDING";
                request = this.g;
            }
        }
        if (request != null) {
            return str2 + str + ", request=[" + request + "]]";
        }
        return str2 + str + "]";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* loaded from: classes.dex */
    public static class a {
        a() {
        }

        void a(Object obj, long j) throws InterruptedException {
            obj.wait(j);
        }

        void a(Object obj) {
            obj.notifyAll();
        }
    }
}
