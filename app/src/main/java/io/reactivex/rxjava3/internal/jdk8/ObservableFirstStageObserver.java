package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class ObservableFirstStageObserver<T> extends b<T> {
    final boolean a;
    final T b;

    public ObservableFirstStageObserver(boolean z, T t) {
        this.a = z;
        this.b = t;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        complete(t);
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (!isDone()) {
            clear();
            if (this.a) {
                complete(this.b);
            } else {
                completeExceptionally(new NoSuchElementException());
            }
        }
    }
}
