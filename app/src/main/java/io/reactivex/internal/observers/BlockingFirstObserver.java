package io.reactivex.internal.observers;

/* loaded from: classes4.dex */
public final class BlockingFirstObserver<T> extends BlockingBaseObserver<T> {
    @Override // io.reactivex.Observer
    public void onNext(T t) {
        if (this.a == null) {
            this.a = t;
            this.c.dispose();
            countDown();
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        if (this.a == null) {
            this.b = th;
        }
        countDown();
    }
}
