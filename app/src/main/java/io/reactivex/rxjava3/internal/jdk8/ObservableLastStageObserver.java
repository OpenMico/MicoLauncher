package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class ObservableLastStageObserver<T> extends b<T> {
    final boolean a;
    final T b;

    public ObservableLastStageObserver(boolean z, T t) {
        this.a = z;
        this.b = t;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        this.d = t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.reactivex.rxjava3.core.Observer
    public void onComplete() {
        if (!isDone()) {
            Object obj = this.d;
            clear();
            if (obj != null) {
                complete(obj);
            } else if (this.a) {
                complete(this.b);
            } else {
                completeExceptionally(new NoSuchElementException());
            }
        }
    }
}
