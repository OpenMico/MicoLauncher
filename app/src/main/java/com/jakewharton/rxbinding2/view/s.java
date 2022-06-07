package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewLayoutChangeEventObservable.java */
/* loaded from: classes2.dex */
final class s extends Observable<ViewLayoutChangeEvent> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super ViewLayoutChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.addOnLayoutChangeListener(aVar);
        }
    }

    /* compiled from: ViewLayoutChangeEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnLayoutChangeListener {
        private final View a;
        private final Observer<? super ViewLayoutChangeEvent> b;

        a(View view, Observer<? super ViewLayoutChangeEvent> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (!isDisposed()) {
                this.b.onNext(ViewLayoutChangeEvent.create(view, i, i2, i3, i4, i5, i6, i7, i8));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeOnLayoutChangeListener(this);
        }
    }
}
