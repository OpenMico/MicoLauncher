package com.bumptech.glide.request;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.DrawableRes;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableDecoderCompat;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.GlideTrace;
import com.bumptech.glide.util.pool.StateVerifier;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class SingleRequest<R> implements Request, ResourceCallback, SizeReadyCallback {
    private static final boolean a = Log.isLoggable("GlideRequest", 2);
    @Nullable
    @GuardedBy("requestLock")
    private Drawable A;
    @GuardedBy("requestLock")
    private int B;
    @GuardedBy("requestLock")
    private int C;
    @GuardedBy("requestLock")
    private boolean D;
    @Nullable
    private RuntimeException E;
    private int b;
    @Nullable
    private final String c;
    private final StateVerifier d;
    private final Object e;
    @Nullable
    private final RequestListener<R> f;
    private final RequestCoordinator g;
    private final Context h;
    private final GlideContext i;
    @Nullable
    private final Object j;
    private final Class<R> k;
    private final BaseRequestOptions<?> l;
    private final int m;
    private final int n;
    private final Priority o;
    private final Target<R> p;
    @Nullable
    private final List<RequestListener<R>> q;
    private final TransitionFactory<? super R> r;
    private final Executor s;
    @GuardedBy("requestLock")
    private Resource<R> t;
    @GuardedBy("requestLock")
    private Engine.LoadStatus u;
    @GuardedBy("requestLock")
    private long v;
    private volatile Engine w;
    @GuardedBy("requestLock")
    private a x;
    @Nullable
    @GuardedBy("requestLock")
    private Drawable y;
    @Nullable
    @GuardedBy("requestLock")
    private Drawable z;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum a {
        PENDING,
        RUNNING,
        WAITING_FOR_SIZE,
        COMPLETE,
        FAILED,
        CLEARED
    }

    public static <R> SingleRequest<R> obtain(Context context, GlideContext glideContext, Object obj, Object obj2, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i, int i2, Priority priority, Target<R> target, RequestListener<R> requestListener, @Nullable List<RequestListener<R>> list, RequestCoordinator requestCoordinator, Engine engine, TransitionFactory<? super R> transitionFactory, Executor executor) {
        return new SingleRequest<>(context, glideContext, obj, obj2, cls, baseRequestOptions, i, i2, priority, target, requestListener, list, requestCoordinator, engine, transitionFactory, executor);
    }

    private SingleRequest(Context context, GlideContext glideContext, @NonNull Object obj, @Nullable Object obj2, Class<R> cls, BaseRequestOptions<?> baseRequestOptions, int i, int i2, Priority priority, Target<R> target, @Nullable RequestListener<R> requestListener, @Nullable List<RequestListener<R>> list, RequestCoordinator requestCoordinator, Engine engine, TransitionFactory<? super R> transitionFactory, Executor executor) {
        this.c = a ? String.valueOf(super.hashCode()) : null;
        this.d = StateVerifier.newInstance();
        this.e = obj;
        this.h = context;
        this.i = glideContext;
        this.j = obj2;
        this.k = cls;
        this.l = baseRequestOptions;
        this.m = i;
        this.n = i2;
        this.o = priority;
        this.p = target;
        this.f = requestListener;
        this.q = list;
        this.g = requestCoordinator;
        this.w = engine;
        this.r = transitionFactory;
        this.s = executor;
        this.x = a.PENDING;
        if (this.E == null && glideContext.getExperiments().isEnabled(GlideBuilder.LogRequestOrigins.class)) {
            this.E = new RuntimeException("Glide request origin trace");
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void begin() {
        synchronized (this.e) {
            b();
            this.d.throwIfRecycled();
            this.v = LogTime.getLogTime();
            if (this.j == null) {
                if (Util.isValidDimensions(this.m, this.n)) {
                    this.B = this.m;
                    this.C = this.n;
                }
                a(new GlideException("Received null model"), e() == null ? 5 : 3);
            } else if (this.x == a.RUNNING) {
                throw new IllegalArgumentException("Cannot restart a running request");
            } else if (this.x == a.COMPLETE) {
                onResourceReady(this.t, DataSource.MEMORY_CACHE, false);
            } else {
                a(this.j);
                this.b = GlideTrace.beginSectionAsync("GlideRequest");
                this.x = a.WAITING_FOR_SIZE;
                if (Util.isValidDimensions(this.m, this.n)) {
                    onSizeReady(this.m, this.n);
                } else {
                    this.p.getSize(this);
                }
                if ((this.x == a.RUNNING || this.x == a.WAITING_FOR_SIZE) && i()) {
                    this.p.onLoadStarted(d());
                }
                if (a) {
                    a("finished run method in " + LogTime.getElapsedMillis(this.v));
                }
            }
        }
    }

    private void a(Object obj) {
        List<RequestListener<R>> list = this.q;
        if (list != null) {
            for (RequestListener<R> requestListener : list) {
                if (requestListener instanceof ExperimentalRequestListener) {
                    ((ExperimentalRequestListener) requestListener).onRequestStarted(obj);
                }
            }
        }
    }

    @GuardedBy("requestLock")
    private void a() {
        b();
        this.d.throwIfRecycled();
        this.p.removeCallback(this);
        Engine.LoadStatus loadStatus = this.u;
        if (loadStatus != null) {
            loadStatus.cancel();
            this.u = null;
        }
    }

    @GuardedBy("requestLock")
    private void b() {
        if (this.D) {
            throw new IllegalStateException("You can't start or clear loads in RequestListener or Target callbacks. If you're trying to start a fallback request when a load fails, use RequestBuilder#error(RequestBuilder). Otherwise consider posting your into() or clear() calls to the main thread using a Handler instead.");
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void clear() {
        Resource<R> resource;
        synchronized (this.e) {
            b();
            this.d.throwIfRecycled();
            if (this.x != a.CLEARED) {
                a();
                if (this.t != null) {
                    resource = this.t;
                    this.t = null;
                } else {
                    resource = null;
                }
                if (h()) {
                    this.p.onLoadCleared(d());
                }
                GlideTrace.endSectionAsync("GlideRequest", this.b);
                this.x = a.CLEARED;
                if (resource != null) {
                    this.w.release(resource);
                }
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public void pause() {
        synchronized (this.e) {
            if (isRunning()) {
                clear();
            }
        }
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isRunning() {
        boolean z;
        synchronized (this.e) {
            if (!(this.x == a.RUNNING || this.x == a.WAITING_FOR_SIZE)) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isComplete() {
        boolean z;
        synchronized (this.e) {
            z = this.x == a.COMPLETE;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isCleared() {
        boolean z;
        synchronized (this.e) {
            z = this.x == a.CLEARED;
        }
        return z;
    }

    @Override // com.bumptech.glide.request.Request, com.bumptech.glide.request.RequestCoordinator
    public boolean isAnyResourceSet() {
        boolean z;
        synchronized (this.e) {
            z = this.x == a.COMPLETE;
        }
        return z;
    }

    @GuardedBy("requestLock")
    private Drawable c() {
        if (this.y == null) {
            this.y = this.l.getErrorPlaceholder();
            if (this.y == null && this.l.getErrorId() > 0) {
                this.y = a(this.l.getErrorId());
            }
        }
        return this.y;
    }

    @GuardedBy("requestLock")
    private Drawable d() {
        if (this.z == null) {
            this.z = this.l.getPlaceholderDrawable();
            if (this.z == null && this.l.getPlaceholderId() > 0) {
                this.z = a(this.l.getPlaceholderId());
            }
        }
        return this.z;
    }

    @GuardedBy("requestLock")
    private Drawable e() {
        if (this.A == null) {
            this.A = this.l.getFallbackDrawable();
            if (this.A == null && this.l.getFallbackId() > 0) {
                this.A = a(this.l.getFallbackId());
            }
        }
        return this.A;
    }

    @GuardedBy("requestLock")
    private Drawable a(@DrawableRes int i) {
        return DrawableDecoderCompat.getDrawable(this.i, i, this.l.getTheme() != null ? this.l.getTheme() : this.h.getTheme());
    }

    @GuardedBy("requestLock")
    private void f() {
        if (i()) {
            Drawable drawable = null;
            if (this.j == null) {
                drawable = e();
            }
            if (drawable == null) {
                drawable = c();
            }
            if (drawable == null) {
                drawable = d();
            }
            this.p.onLoadFailed(drawable);
        }
    }

    @Override // com.bumptech.glide.request.target.SizeReadyCallback
    public void onSizeReady(int i, int i2) {
        Object obj;
        this.d.throwIfRecycled();
        Object obj2 = this.e;
        try {
            synchronized (obj2) {
                try {
                    if (a) {
                        a("Got onSizeReady in " + LogTime.getElapsedMillis(this.v));
                    }
                    if (this.x == a.WAITING_FOR_SIZE) {
                        this.x = a.RUNNING;
                        float sizeMultiplier = this.l.getSizeMultiplier();
                        this.B = a(i, sizeMultiplier);
                        this.C = a(i2, sizeMultiplier);
                        if (a) {
                            a("finished setup for calling load in " + LogTime.getElapsedMillis(this.v));
                        }
                        obj = obj2;
                        try {
                            this.u = this.w.load(this.i, this.j, this.l.getSignature(), this.B, this.C, this.l.getResourceClass(), this.k, this.o, this.l.getDiskCacheStrategy(), this.l.getTransformations(), this.l.isTransformationRequired(), this.l.a(), this.l.getOptions(), this.l.isMemoryCacheable(), this.l.getUseUnlimitedSourceGeneratorsPool(), this.l.getUseAnimationPool(), this.l.getOnlyRetrieveFromCache(), this, this.s);
                            if (this.x != a.RUNNING) {
                                this.u = null;
                            }
                            if (a) {
                                a("finished onSizeReady in " + LogTime.getElapsedMillis(this.v));
                            }
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    obj = obj2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static int a(int i, float f) {
        return i == Integer.MIN_VALUE ? i : Math.round(f * i);
    }

    @GuardedBy("requestLock")
    private boolean g() {
        RequestCoordinator requestCoordinator = this.g;
        return requestCoordinator == null || requestCoordinator.canSetImage(this);
    }

    @GuardedBy("requestLock")
    private boolean h() {
        RequestCoordinator requestCoordinator = this.g;
        return requestCoordinator == null || requestCoordinator.canNotifyCleared(this);
    }

    @GuardedBy("requestLock")
    private boolean i() {
        RequestCoordinator requestCoordinator = this.g;
        return requestCoordinator == null || requestCoordinator.canNotifyStatusChanged(this);
    }

    @GuardedBy("requestLock")
    private boolean j() {
        RequestCoordinator requestCoordinator = this.g;
        return requestCoordinator == null || !requestCoordinator.getRoot().isAnyResourceSet();
    }

    @GuardedBy("requestLock")
    private void k() {
        RequestCoordinator requestCoordinator = this.g;
        if (requestCoordinator != null) {
            requestCoordinator.onRequestSuccess(this);
        }
    }

    @GuardedBy("requestLock")
    private void l() {
        RequestCoordinator requestCoordinator = this.g;
        if (requestCoordinator != null) {
            requestCoordinator.onRequestFailed(this);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0056, code lost:
        if (r6 == null) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
        r5.w.release(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005d, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b1, code lost:
        if (r6 == null) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b3, code lost:
        r5.w.release(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b8, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.request.ResourceCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onResourceReady(com.bumptech.glide.load.engine.Resource<?> r6, com.bumptech.glide.load.DataSource r7, boolean r8) {
        /*
            r5 = this;
            com.bumptech.glide.util.pool.StateVerifier r0 = r5.d
            r0.throwIfRecycled()
            r0 = 0
            java.lang.Object r1 = r5.e     // Catch: all -> 0x00c3
            monitor-enter(r1)     // Catch: all -> 0x00c3
            r5.u = r0     // Catch: all -> 0x00b9
            if (r6 != 0) goto L_0x002f
            com.bumptech.glide.load.engine.GlideException r6 = new com.bumptech.glide.load.engine.GlideException     // Catch: all -> 0x00b9
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: all -> 0x00b9
            r7.<init>()     // Catch: all -> 0x00b9
            java.lang.String r8 = "Expected to receive a Resource<R> with an object of "
            r7.append(r8)     // Catch: all -> 0x00b9
            java.lang.Class<R> r8 = r5.k     // Catch: all -> 0x00b9
            r7.append(r8)     // Catch: all -> 0x00b9
            java.lang.String r8 = " inside, but instead got null."
            r7.append(r8)     // Catch: all -> 0x00b9
            java.lang.String r7 = r7.toString()     // Catch: all -> 0x00b9
            r6.<init>(r7)     // Catch: all -> 0x00b9
            r5.onLoadFailed(r6)     // Catch: all -> 0x00b9
            monitor-exit(r1)     // Catch: all -> 0x00b9
            return
        L_0x002f:
            java.lang.Object r2 = r6.get()     // Catch: all -> 0x00b9
            if (r2 == 0) goto L_0x0063
            java.lang.Class<R> r3 = r5.k     // Catch: all -> 0x00b9
            java.lang.Class r4 = r2.getClass()     // Catch: all -> 0x00b9
            boolean r3 = r3.isAssignableFrom(r4)     // Catch: all -> 0x00b9
            if (r3 != 0) goto L_0x0042
            goto L_0x0063
        L_0x0042:
            boolean r3 = r5.g()     // Catch: all -> 0x00b9
            if (r3 != 0) goto L_0x005e
            r5.t = r0     // Catch: all -> 0x00c1
            com.bumptech.glide.request.SingleRequest$a r7 = com.bumptech.glide.request.SingleRequest.a.COMPLETE     // Catch: all -> 0x00c1
            r5.x = r7     // Catch: all -> 0x00c1
            java.lang.String r7 = "GlideRequest"
            int r8 = r5.b     // Catch: all -> 0x00c1
            com.bumptech.glide.util.pool.GlideTrace.endSectionAsync(r7, r8)     // Catch: all -> 0x00c1
            monitor-exit(r1)     // Catch: all -> 0x00c1
            if (r6 == 0) goto L_0x005d
            com.bumptech.glide.load.engine.Engine r7 = r5.w
            r7.release(r6)
        L_0x005d:
            return
        L_0x005e:
            r5.a(r6, r2, r7, r8)     // Catch: all -> 0x00b9
            monitor-exit(r1)     // Catch: all -> 0x00b9
            return
        L_0x0063:
            r5.t = r0     // Catch: all -> 0x00c1
            com.bumptech.glide.load.engine.GlideException r7 = new com.bumptech.glide.load.engine.GlideException     // Catch: all -> 0x00c1
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: all -> 0x00c1
            r8.<init>()     // Catch: all -> 0x00c1
            java.lang.String r0 = "Expected to receive an object of "
            r8.append(r0)     // Catch: all -> 0x00c1
            java.lang.Class<R> r0 = r5.k     // Catch: all -> 0x00c1
            r8.append(r0)     // Catch: all -> 0x00c1
            java.lang.String r0 = " but instead got "
            r8.append(r0)     // Catch: all -> 0x00c1
            if (r2 == 0) goto L_0x0082
            java.lang.Class r0 = r2.getClass()     // Catch: all -> 0x00c1
            goto L_0x0084
        L_0x0082:
            java.lang.String r0 = ""
        L_0x0084:
            r8.append(r0)     // Catch: all -> 0x00c1
            java.lang.String r0 = "{"
            r8.append(r0)     // Catch: all -> 0x00c1
            r8.append(r2)     // Catch: all -> 0x00c1
            java.lang.String r0 = "} inside Resource{"
            r8.append(r0)     // Catch: all -> 0x00c1
            r8.append(r6)     // Catch: all -> 0x00c1
            java.lang.String r0 = "}."
            r8.append(r0)     // Catch: all -> 0x00c1
            if (r2 == 0) goto L_0x00a1
            java.lang.String r0 = ""
            goto L_0x00a3
        L_0x00a1:
            java.lang.String r0 = " To indicate failure return a null Resource object, rather than a Resource object containing null data."
        L_0x00a3:
            r8.append(r0)     // Catch: all -> 0x00c1
            java.lang.String r8 = r8.toString()     // Catch: all -> 0x00c1
            r7.<init>(r8)     // Catch: all -> 0x00c1
            r5.onLoadFailed(r7)     // Catch: all -> 0x00c1
            monitor-exit(r1)     // Catch: all -> 0x00c1
            if (r6 == 0) goto L_0x00b8
            com.bumptech.glide.load.engine.Engine r7 = r5.w
            r7.release(r6)
        L_0x00b8:
            return
        L_0x00b9:
            r6 = move-exception
            r7 = r6
            r6 = r0
        L_0x00bc:
            monitor-exit(r1)     // Catch: all -> 0x00c1
            throw r7     // Catch: all -> 0x00be
        L_0x00be:
            r7 = move-exception
            r0 = r6
            goto L_0x00c4
        L_0x00c1:
            r7 = move-exception
            goto L_0x00bc
        L_0x00c3:
            r7 = move-exception
        L_0x00c4:
            if (r0 == 0) goto L_0x00cb
            com.bumptech.glide.load.engine.Engine r6 = r5.w
            r6.release(r0)
        L_0x00cb:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.request.SingleRequest.onResourceReady(com.bumptech.glide.load.engine.Resource, com.bumptech.glide.load.DataSource, boolean):void");
    }

    /* JADX WARN: Finally extract failed */
    @GuardedBy("requestLock")
    private void a(Resource<R> resource, R r, DataSource dataSource, boolean z) {
        boolean z2;
        boolean j = j();
        this.x = a.COMPLETE;
        this.t = resource;
        if (this.i.getLogLevel() <= 3) {
            Log.d("Glide", "Finished loading " + r.getClass().getSimpleName() + " from " + dataSource + " for " + this.j + " with size [" + this.B + "x" + this.C + "] in " + LogTime.getElapsedMillis(this.v) + " ms");
        }
        boolean z3 = true;
        this.D = true;
        try {
            if (this.q != null) {
                z2 = false;
                for (RequestListener<R> requestListener : this.q) {
                    z2 |= requestListener.onResourceReady(r, this.j, this.p, dataSource, j);
                }
            } else {
                z2 = false;
            }
            if (this.f == null || !this.f.onResourceReady(r, this.j, this.p, dataSource, j)) {
                z3 = false;
            }
            if (!z3 && !z2) {
                this.p.onResourceReady(r, this.r.build(dataSource, j));
            }
            this.D = false;
            k();
            GlideTrace.endSectionAsync("GlideRequest", this.b);
        } catch (Throwable th) {
            this.D = false;
            throw th;
        }
    }

    @Override // com.bumptech.glide.request.ResourceCallback
    public void onLoadFailed(GlideException glideException) {
        a(glideException, 5);
    }

    @Override // com.bumptech.glide.request.ResourceCallback
    public Object getLock() {
        this.d.throwIfRecycled();
        return this.e;
    }

    private void a(GlideException glideException, int i) {
        boolean z;
        this.d.throwIfRecycled();
        synchronized (this.e) {
            glideException.setOrigin(this.E);
            int logLevel = this.i.getLogLevel();
            if (logLevel <= i) {
                Log.w("Glide", "Load failed for " + this.j + " with size [" + this.B + "x" + this.C + "]", glideException);
                if (logLevel <= 4) {
                    glideException.logRootCauses("Glide");
                }
            }
            this.u = null;
            this.x = a.FAILED;
            boolean z2 = true;
            this.D = true;
            if (this.q != null) {
                z = false;
                for (RequestListener<R> requestListener : this.q) {
                    z |= requestListener.onLoadFailed(glideException, this.j, this.p, j());
                }
            } else {
                z = false;
            }
            if (this.f == null || !this.f.onLoadFailed(glideException, this.j, this.p, j())) {
                z2 = false;
            }
            if (!z && !z2) {
                f();
            }
            this.D = false;
            l();
            GlideTrace.endSectionAsync("GlideRequest", this.b);
        }
    }

    @Override // com.bumptech.glide.request.Request
    public boolean isEquivalentTo(Request request) {
        int i;
        int i2;
        Object obj;
        Class<R> cls;
        BaseRequestOptions<?> baseRequestOptions;
        Priority priority;
        int size;
        int i3;
        int i4;
        Object obj2;
        Class<R> cls2;
        BaseRequestOptions<?> baseRequestOptions2;
        Priority priority2;
        int size2;
        if (!(request instanceof SingleRequest)) {
            return false;
        }
        synchronized (this.e) {
            i = this.m;
            i2 = this.n;
            obj = this.j;
            cls = this.k;
            baseRequestOptions = this.l;
            priority = this.o;
            size = this.q != null ? this.q.size() : 0;
        }
        SingleRequest singleRequest = (SingleRequest) request;
        synchronized (singleRequest.e) {
            i3 = singleRequest.m;
            i4 = singleRequest.n;
            obj2 = singleRequest.j;
            cls2 = singleRequest.k;
            baseRequestOptions2 = singleRequest.l;
            priority2 = singleRequest.o;
            size2 = singleRequest.q != null ? singleRequest.q.size() : 0;
        }
        return i == i3 && i2 == i4 && Util.bothModelsNullEquivalentOrEquals(obj, obj2) && cls.equals(cls2) && baseRequestOptions.equals(baseRequestOptions2) && priority == priority2 && size == size2;
    }

    private void a(String str) {
        Log.v("GlideRequest", str + " this: " + this.c);
    }

    public String toString() {
        Object obj;
        Class<R> cls;
        synchronized (this.e) {
            obj = this.j;
            cls = this.k;
        }
        return super.toString() + "[model=" + obj + ", transcodeClass=" + cls + "]";
    }
}
