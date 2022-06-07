package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewLayoutChangeObservable.java */
/* loaded from: classes2.dex */
final class t extends Observable<Object> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public t(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.addOnLayoutChangeListener(aVar);
        }
    }

    /* compiled from: ViewLayoutChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnLayoutChangeListener {
        private final View a;
        private final Observer<? super Object> b;

        a(View view, Observer<? super Object> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (!isDisposed()) {
                this.b.onNext(Notification.INSTANCE);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeOnLayoutChangeListener(this);
        }
    }
}
