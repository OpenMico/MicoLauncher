package io.reactivex.internal.observers;

/* loaded from: classes4.dex */
public final class BlockingLastObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.Observer
    public void onNext(T t) {
        this.a = t;
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        this.a = null;
        this.b = th;
        countDown();
    }
}
