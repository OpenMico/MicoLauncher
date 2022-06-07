package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewTreeObserverDrawObservable.java */
@RequiresApi(16)
/* loaded from: classes2.dex */
final class y extends Observable<Object> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public y(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.getViewTreeObserver().addOnDrawListener(aVar);
        }
    }

    /* compiled from: ViewTreeObserverDrawObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements ViewTreeObserver.OnDrawListener {
        private final View a;
        private final Observer<? super Object> b;

        a(View view, Observer<? super Object> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.ViewTreeObserver.OnDrawListener
        public void onDraw() {
            if (!isDisposed()) {
                this.b.onNext(Notification.INSTANCE);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.getViewTreeObserver().removeOnDrawListener(this);
        }
    }
}
