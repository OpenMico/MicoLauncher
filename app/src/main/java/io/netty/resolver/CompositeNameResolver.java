package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public final class CompositeNameResolver<T> extends SimpleNameResolver<T> {
    private final NameResolver<T>[] a;

    public CompositeNameResolver(EventExecutor eventExecutor, NameResolver<T>... nameResolverArr) {
        super(eventExecutor);
        ObjectUtil.checkNotNull(nameResolverArr, "resolvers");
        for (int i = 0; i < nameResolverArr.length; i++) {
            if (nameResolverArr[i] == null) {
                throw new NullPointerException("resolvers[" + i + ']');
            }
        }
        if (nameResolverArr.length >= 2) {
            this.a = (NameResolver[]) nameResolverArr.clone();
            return;
        }
        throw new IllegalArgumentException("resolvers: " + Arrays.asList(nameResolverArr) + " (expected: at least 2 resolvers)");
    }

    @Override // io.netty.resolver.SimpleNameResolver
    protected void doResolve(String str, Promise<T> promise) throws Exception {
        a(str, promise, 0, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final Promise<T> promise, final int i, Throwable th) throws Exception {
        NameResolver<T>[] nameResolverArr = this.a;
        if (i >= nameResolverArr.length) {
            promise.setFailure(th);
        } else {
            nameResolverArr[i].resolve(str).addListener(new FutureListener<T>() { // from class: io.netty.resolver.CompositeNameResolver.1
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<T> future) throws Exception {
                    if (future.isSuccess()) {
                        promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.this.a(str, promise, i + 1, future.cause());
                    }
                }
            });
        }
    }

    @Override // io.netty.resolver.SimpleNameResolver
    protected void doResolveAll(String str, Promise<List<T>> promise) throws Exception {
        b(str, promise, 0, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final String str, final Promise<List<T>> promise, final int i, Throwable th) throws Exception {
        NameResolver<T>[] nameResolverArr = this.a;
        if (i >= nameResolverArr.length) {
            promise.setFailure(th);
        } else {
            nameResolverArr[i].resolveAll(str).addListener(new FutureListener<List<T>>() { // from class: io.netty.resolver.CompositeNameResolver.2
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<List<T>> future) throws Exception {
                    if (future.isSuccess()) {
                        promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.this.b(str, promise, i + 1, future.cause());
                    }
                }
            });
        }
    }
}
