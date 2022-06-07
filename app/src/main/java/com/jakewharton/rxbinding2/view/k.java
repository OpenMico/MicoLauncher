package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewAttachEventObservable.java */
/* loaded from: classes2.dex */
final class k extends Observable<ViewAttachEvent> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super ViewAttachEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.addOnAttachStateChangeListener(aVar);
        }
    }

    /* compiled from: ViewAttachEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        private final View a;
        private final Observer<? super ViewAttachEvent> b;

        a(View view, Observer<? super ViewAttachEvent> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            if (!isDisposed()) {
                this.b.onNext(ViewAttachAttachedEvent.create(this.a));
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (!isDisposed()) {
                this.b.onNext(ViewAttachDetachedEvent.create(this.a));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeOnAttachStateChangeListener(this);
        }
    }
}
