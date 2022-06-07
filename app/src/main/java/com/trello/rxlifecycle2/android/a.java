package com.trello.rxlifecycle2.android;

import android.view.View;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewDetachesOnSubscribe.java */
/* loaded from: classes2.dex */
final class a implements ObservableOnSubscribe<Object> {
    static final Object a = new Object();
    final View b;

    public a(View view) {
        this.b = view;
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
        MainThreadDisposable.verifyMainThread();
        View$OnAttachStateChangeListenerC0133a aVar = new View$OnAttachStateChangeListenerC0133a(observableEmitter);
        observableEmitter.setDisposable(aVar);
        this.b.addOnAttachStateChangeListener(aVar);
    }

    /* compiled from: ViewDetachesOnSubscribe.java */
    /* renamed from: com.trello.rxlifecycle2.android.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    class View$OnAttachStateChangeListenerC0133a extends MainThreadDisposable implements View.OnAttachStateChangeListener {
        final ObservableEmitter<Object> a;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        public View$OnAttachStateChangeListenerC0133a(ObservableEmitter<Object> observableEmitter) {
            this.a = observableEmitter;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            this.a.onNext(a.a);
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            a.this.b.removeOnAttachStateChangeListener(this);
        }
    }
}
