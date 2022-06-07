package io.netty.resolver.dns;

import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/* compiled from: InflightNameResolver.java */
/* loaded from: classes4.dex */
final class e<T> implements NameResolver<T> {
    private final EventExecutor a;
    private final NameResolver<T> b;
    private final ConcurrentMap<String, Promise<T>> c;
    private final ConcurrentMap<String, Promise<List<T>>> d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(EventExecutor eventExecutor, NameResolver<T> nameResolver, ConcurrentMap<String, Promise<T>> concurrentMap, ConcurrentMap<String, Promise<List<T>>> concurrentMap2) {
        this.a = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.b = (NameResolver) ObjectUtil.checkNotNull(nameResolver, "delegate");
        this.c = (ConcurrentMap) ObjectUtil.checkNotNull(concurrentMap, "resolvesInProgress");
        this.d = (ConcurrentMap) ObjectUtil.checkNotNull(concurrentMap2, "resolveAllsInProgress");
    }

    @Override // io.netty.resolver.NameResolver
    public Future<T> resolve(String str) {
        return resolve(str, this.a.newPromise());
    }

    @Override // io.netty.resolver.NameResolver
    public Future<List<T>> resolveAll(String str) {
        return resolveAll(str, this.a.newPromise());
    }

    @Override // io.netty.resolver.NameResolver, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.b.close();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    public Promise<T> resolve(String str, Promise<T> promise) {
        return (Promise<T>) a((ConcurrentMap<String, Promise<T>>) this.c, str, promise, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: b */
    public Promise<List<T>> resolveAll(String str, Promise<List<T>> promise) {
        return (Promise<List<T>>) a((ConcurrentMap<String, Promise<List<T>>>) this.d, str, promise, true);
    }

    private <U> Promise<U> a(final ConcurrentMap<String, Promise<U>> concurrentMap, final String str, final Promise<U> promise, boolean z) {
        Promise<U> putIfAbsent = concurrentMap.putIfAbsent(str, promise);
        if (putIfAbsent == null) {
            try {
                if (z) {
                    this.b.resolveAll(str, promise);
                } else {
                    this.b.resolve(str, promise);
                }
                if (promise.isDone()) {
                    concurrentMap.remove(str);
                } else {
                    promise.addListener((GenericFutureListener<? extends Future<? super U>>) new FutureListener<U>() { // from class: io.netty.resolver.dns.e.2
                        @Override // io.netty.util.concurrent.GenericFutureListener
                        public void operationComplete(Future<U> future) throws Exception {
                            concurrentMap.remove(str);
                        }
                    });
                }
            } catch (Throwable th) {
                if (promise.isDone()) {
                    concurrentMap.remove(str);
                } else {
                    promise.addListener((GenericFutureListener<? extends Future<? super U>>) new FutureListener<U>() { // from class: io.netty.resolver.dns.e.2
                        @Override // io.netty.util.concurrent.GenericFutureListener
                        public void operationComplete(Future<U> future) throws Exception {
                            concurrentMap.remove(str);
                        }
                    });
                }
                throw th;
            }
        } else if (putIfAbsent.isDone()) {
            b(putIfAbsent, promise);
        } else {
            putIfAbsent.addListener((GenericFutureListener<? extends Future<? super U>>) new FutureListener<U>() { // from class: io.netty.resolver.dns.e.1
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<U> future) throws Exception {
                    e.b(future, promise);
                }
            });
        }
        return promise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> void b(Future<T> future, Promise<T> promise) {
        if (future.isSuccess()) {
            promise.trySuccess(future.getNow());
        } else {
            promise.tryFailure(future.cause());
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.b + ')';
    }
}
