package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewSystemUiVisibilityChangeObservable.java */
/* loaded from: classes2.dex */
final class w extends Observable<Integer> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnSystemUiVisibilityChangeListener(aVar);
        }
    }

    /* compiled from: ViewSystemUiVisibilityChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnSystemUiVisibilityChangeListener {
        private final View a;
        private final Observer<? super Integer> b;

        a(View view, Observer<? super Integer> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnSystemUiVisibilityChangeListener
        public void onSystemUiVisibilityChange(int i) {
            if (!isDisposed()) {
                this.b.onNext(Integer.valueOf(i));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnSystemUiVisibilityChangeListener(null);
        }
    }
}
