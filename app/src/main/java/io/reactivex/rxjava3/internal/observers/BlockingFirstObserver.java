package io.reactivex.rxjava3.internal.observers;

/* loaded from: classes4.dex */
public final class BlockingFirstObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        if (this.a == null) {
            this.a = t;
            this.c.dispose();
            countDown();
        }
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onError(Throwable th) {
        if (this.a == null) {
            this.b = th;
        }
        countDown();
    }
}
