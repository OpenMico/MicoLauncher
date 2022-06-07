package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class SimpleNameResolver<T> implements NameResolver<T> {
    private final EventExecutor a;

    @Override // io.netty.resolver.NameResolver, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    protected abstract void doResolve(String str, Promise<T> promise) throws Exception;

    protected abstract void doResolveAll(String str, Promise<List<T>> promise) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleNameResolver(EventExecutor eventExecutor) {
        this.a = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    protected EventExecutor executor() {
        return this.a;
    }

    @Override // io.netty.resolver.NameResolver
    public final Future<T> resolve(String str) {
        return resolve(str, executor().newPromise());
    }

    @Override // io.netty.resolver.NameResolver
    public Future<T> resolve(String str, Promise<T> promise) {
        ObjectUtil.checkNotNull(str, "inetHost");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            doResolve(str, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    @Override // io.netty.resolver.NameResolver
    public final Future<List<T>> resolveAll(String str) {
        return resolveAll(str, executor().newPromise());
    }

    @Override // io.netty.resolver.NameResolver
    public Future<List<T>> resolveAll(String str, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(str, "inetHost");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            doResolveAll(str, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }
}
