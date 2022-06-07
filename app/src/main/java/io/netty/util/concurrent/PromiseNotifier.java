package io.netty.util.concurrent;

import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public class PromiseNotifier<V, F extends Future<V>> implements GenericFutureListener<F> {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(PromiseNotifier.class);
    private final Promise<? super V>[] b;

    @SafeVarargs
    public PromiseNotifier(Promise<? super V>... promiseArr) {
        ObjectUtil.checkNotNull(promiseArr, "promises");
        for (Promise<? super V> promise : promiseArr) {
            if (promise == null) {
                throw new IllegalArgumentException("promises contains null Promise");
            }
        }
        this.b = (Promise[]) promiseArr.clone();
    }

    @Override // io.netty.util.concurrent.GenericFutureListener
    public void operationComplete(F f) throws Exception {
        int i = 0;
        if (f.isSuccess()) {
            Object obj = f.get();
            Promise<? super V>[] promiseArr = this.b;
            int length = promiseArr.length;
            while (i < length) {
                Promise<? super V> promise = promiseArr[i];
                if (!promise.trySuccess(obj)) {
                    a.warn("Failed to mark a promise as success because it is done already: {}", promise);
                }
                i++;
            }
        } else if (f.isCancelled()) {
            Promise<? super V>[] promiseArr2 = this.b;
            for (Promise<? super V> promise2 : promiseArr2) {
                if (!promise2.cancel(false)) {
                    a.warn("Failed to cancel a promise because it is done already: {}", promise2);
                }
            }
        } else {
            Throwable cause = f.cause();
            Promise<? super V>[] promiseArr3 = this.b;
            int length2 = promiseArr3.length;
            while (i < length2) {
                Promise<? super V> promise3 = promiseArr3[i];
                if (!promise3.tryFailure(cause)) {
                    a.warn("Failed to mark a promise as failure because it's done already: {}", promise3, cause);
                }
                i++;
            }
        }
    }
}
