package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewAttachesObservable.java */
/* loaded from: classes2.dex */
final class l extends Observable<Object> {
    private final boolean a;
    private final View b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(View view, boolean z) {
        this.b = view;
        this.a = z;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.b, this.a, observer);
            observer.onSubscribe(aVar);
            this.b.addOnAttachStateChangeListener(aVar);
        }
    }

    /* compiled from: ViewAttachesObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        private final View a;
        private final boolean b;
        private final Observer<? super Object> c;

        a(View view, boolean z, Observer<? super Object> observer) {
            this.a = view;
            this.b = z;
            this.c = observer;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            if (this.b && !isDisposed()) {
                this.c.onNext(Notification.INSTANCE);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (!this.b && !isDisposed()) {
                this.c.onNext(Notification.INSTANCE);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeOnAttachStateChangeListener(this);
        }
    }
}
