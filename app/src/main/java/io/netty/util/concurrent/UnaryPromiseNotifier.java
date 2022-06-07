package io.netty.util.concurrent;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public final class UnaryPromiseNotifier<T> implements FutureListener<T> {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(UnaryPromiseNotifier.class);
    private final Promise<? super T> b;

    public UnaryPromiseNotifier(Promise<? super T> promise) {
        this.b = (Promise) ObjectUtil.checkNotNull(promise, "promise");
    }

    @Override // io.netty.util.concurrent.GenericFutureListener
    public void operationComplete(Future<T> future) throws Exception {
        cascadeTo(future, this.b);
    }

    public static <X> void cascadeTo(Future<X> future, Promise<? super X> promise) {
        if (future.isSuccess()) {
            if (!promise.trySuccess(future.getNow())) {
                a.warn("Failed to mark a promise as success because it is done already: {}", promise);
            }
        } else if (future.isCancelled()) {
            if (!promise.cancel(false)) {
                a.warn("Failed to cancel a promise because it is done already: {}", promise);
            }
        } else if (!promise.tryFailure(future.cause())) {
            a.warn("Failed to mark a promise as failure because it's done already: {}", promise, future.cause());
        }
    }
}
