package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public final class PromiseCombiner {
    private int a;
    private int b;
    private boolean c;
    private Promise<Void> d;
    private Throwable e;
    private final GenericFutureListener<Future<?>> f = new GenericFutureListener<Future<?>>() { // from class: io.netty.util.concurrent.PromiseCombiner.1
        @Override // io.netty.util.concurrent.GenericFutureListener
        public void operationComplete(Future<?> future) throws Exception {
            PromiseCombiner.a(PromiseCombiner.this);
            if (!future.isSuccess() && PromiseCombiner.this.e == null) {
                PromiseCombiner.this.e = future.cause();
            }
            if (PromiseCombiner.this.b == PromiseCombiner.this.a && PromiseCombiner.this.c) {
                PromiseCombiner.this.a();
            }
        }
    };

    static /* synthetic */ int a(PromiseCombiner promiseCombiner) {
        int i = promiseCombiner.b + 1;
        promiseCombiner.b = i;
        return i;
    }

    public void add(Promise promise) {
        b();
        this.a++;
        promise.addListener((GenericFutureListener) this.f);
    }

    public void addAll(Promise... promiseArr) {
        b();
        this.a += promiseArr.length;
        for (Promise promise : promiseArr) {
            promise.addListener((GenericFutureListener) this.f);
        }
    }

    public void finish(Promise<Void> promise) {
        if (!this.c) {
            this.c = true;
            this.d = (Promise) ObjectUtil.checkNotNull(promise, "aggregatePromise");
            if (this.b == this.a) {
                a();
                return;
            }
            return;
        }
        throw new IllegalStateException("Already finished");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        Throwable th = this.e;
        return th == null ? this.d.trySuccess(null) : this.d.tryFailure(th);
    }

    private void b() {
        if (this.c) {
            throw new IllegalStateException("Adding promises is not allowed after finished adding");
        }
    }
}
