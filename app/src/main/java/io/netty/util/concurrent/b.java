package io.netty.util.concurrent;

import io.netty.util.internal.StringUtil;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/* compiled from: PromiseTask.java */
/* loaded from: classes4.dex */
class b<V> extends DefaultPromise<V> implements RunnableFuture<V> {
    protected final Callable<V> a;

    public final boolean equals(Object obj) {
        return this == obj;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public final boolean tryFailure(Throwable th) {
        return false;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public final boolean trySuccess(V v) {
        return false;
    }

    static <T> Callable<T> a(Runnable runnable, T t) {
        return new a(runnable, t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: PromiseTask.java */
    /* loaded from: classes4.dex */
    public static final class a<T> implements Callable<T> {
        final Runnable a;
        final T b;

        a(Runnable runnable, T t) {
            this.a = runnable;
            this.b = t;
        }

        @Override // java.util.concurrent.Callable
        public T call() {
            this.a.run();
            return this.b;
        }

        public String toString() {
            return "Callable(task: " + this.a + ", result: " + this.b + ')';
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(EventExecutor eventExecutor, Runnable runnable, V v) {
        this(eventExecutor, a(runnable, v));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(EventExecutor eventExecutor, Callable<V> callable) {
        super(eventExecutor);
        this.a = callable;
    }

    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public void run() {
        try {
            if (a()) {
                a((b<V>) this.a.call());
            }
        } catch (Throwable th) {
            a(th);
        }
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.channel.ChannelPromise
    public final Promise<V> setFailure(Throwable th) {
        throw new IllegalStateException();
    }

    protected final Promise<V> a(Throwable th) {
        super.setFailure(th);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise, io.netty.util.concurrent.ProgressivePromise
    public final Promise<V> setSuccess(V v) {
        throw new IllegalStateException();
    }

    protected final Promise<V> a(V v) {
        super.setSuccess(v);
        return this;
    }

    @Override // io.netty.util.concurrent.DefaultPromise, io.netty.util.concurrent.Promise
    public final boolean setUncancellable() {
        throw new IllegalStateException();
    }

    protected final boolean a() {
        return super.setUncancellable();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.util.concurrent.DefaultPromise
    public StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, StringUtil.COMMA);
        stringBuilder.append(" task: ");
        stringBuilder.append(this.a);
        stringBuilder.append(')');
        return stringBuilder;
    }
}
