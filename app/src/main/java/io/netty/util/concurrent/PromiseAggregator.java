package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import java.util.LinkedHashSet;
import java.util.Set;

@Deprecated
/* loaded from: classes4.dex */
public class PromiseAggregator<V, F extends Future<V>> implements GenericFutureListener<F> {
    private final Promise<?> a;
    private final boolean b;
    private Set<Promise<V>> c;

    public PromiseAggregator(Promise<Void> promise, boolean z) {
        if (promise != null) {
            this.a = promise;
            this.b = z;
            return;
        }
        throw new NullPointerException("aggregatePromise");
    }

    public PromiseAggregator(Promise<Void> promise) {
        this(promise, true);
    }

    @SafeVarargs
    public final PromiseAggregator<V, F> add(Promise<V>... promiseArr) {
        if (promiseArr == null) {
            throw new NullPointerException("promises");
        } else if (promiseArr.length == 0) {
            return this;
        } else {
            synchronized (this) {
                if (this.c == null) {
                    this.c = new LinkedHashSet(promiseArr.length > 1 ? promiseArr.length : 2);
                }
                for (Promise<V> promise : promiseArr) {
                    if (promise != null) {
                        this.c.add(promise);
                        promise.addListener((GenericFutureListener) this);
                    }
                }
            }
            return this;
        }
    }

    @Override // io.netty.util.concurrent.GenericFutureListener
    public synchronized void operationComplete(F f) throws Exception {
        if (this.c == null) {
            this.a.setSuccess(null);
        } else {
            this.c.remove(f);
            if (!f.isSuccess()) {
                Throwable cause = f.cause();
                this.a.setFailure(cause);
                if (this.b) {
                    for (Promise<V> promise : this.c) {
                        promise.setFailure(cause);
                    }
                }
            } else if (this.c.isEmpty()) {
                this.a.setSuccess(null);
            }
        }
    }
}
