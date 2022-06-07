package io.reactivex.rxjava3.internal.jdk8;

import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public final class ObservableSingleStageObserver<T> extends b<T> {
    final boolean a;
    final T b;

    public ObservableSingleStageObserver(boolean z, T t) {
        this.a = z;
        this.b = t;
    }

    @Override // io.reactivex.rxjava3.core.Observer
    public void onNext(T t) {
        if (this.d != null) {
            this.d = null;
            completeExceptionally(new IllegalArgumentException("Sequence contains more than one element!"));
            return;
        }
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
