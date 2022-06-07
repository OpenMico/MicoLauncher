package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.Subject;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ObservableWindowSubscribeIntercept.java */
/* loaded from: classes5.dex */
public final class b<T> extends Observable<T> {
    final Subject<T> a;
    final AtomicBoolean b = new AtomicBoolean();

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Subject<T> subject) {
        this.a = subject;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(observer);
        this.b.set(true);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return !this.b.get() && this.b.compareAndSet(false, true);
    }
}
