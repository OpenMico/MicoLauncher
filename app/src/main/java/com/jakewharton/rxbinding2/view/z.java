package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewTreeObserver;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewTreeObserverGlobalLayoutObservable.java */
/* loaded from: classes2.dex */
final class z extends Observable<Object> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public z(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.getViewTreeObserver().addOnGlobalLayoutListener(aVar);
        }
    }

    /* compiled from: ViewTreeObserverGlobalLayoutObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements ViewTreeObserver.OnGlobalLayoutListener {
        private final View a;
        private final Observer<? super Object> b;

        a(View view, Observer<? super Object> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (!isDisposed()) {
                this.b.onNext(Notification.INSTANCE);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
