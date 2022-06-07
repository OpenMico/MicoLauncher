package io.netty.util.internal;

import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Promise;

/* loaded from: classes4.dex */
public final class PendingWrite {
    private static final Recycler<PendingWrite> a = new Recycler<PendingWrite>() { // from class: io.netty.util.internal.PendingWrite.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public PendingWrite newObject(Recycler.Handle<PendingWrite> handle) {
            return new PendingWrite(handle);
        }
    };
    private final Recycler.Handle<PendingWrite> b;
    private Object c;
    private Promise<Void> d;

    public static PendingWrite newInstance(Object obj, Promise<Void> promise) {
        PendingWrite pendingWrite = a.get();
        pendingWrite.c = obj;
        pendingWrite.d = promise;
        return pendingWrite;
    }

    private PendingWrite(Recycler.Handle<PendingWrite> handle) {
        this.b = handle;
    }

    public boolean recycle() {
        this.c = null;
        this.d = null;
        this.b.recycle(this);
        return true;
    }

    public boolean failAndRecycle(Throwable th) {
        ReferenceCountUtil.release(this.c);
        Promise<Void> promise = this.d;
        if (promise != null) {
            promise.setFailure(th);
        }
        return recycle();
    }

    public boolean successAndRecycle() {
        Promise<Void> promise = this.d;
        if (promise != null) {
            promise.setSuccess(null);
        }
        return recycle();
    }

    public Object msg() {
        return this.c;
    }

    public Promise<Void> promise() {
        return this.d;
    }

    public Promise<Void> recycleAndGet() {
        Promise<Void> promise = this.d;
        recycle();
        return promise;
    }
}
