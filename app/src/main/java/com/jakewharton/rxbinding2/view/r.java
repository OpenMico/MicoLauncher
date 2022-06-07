package com.jakewharton.rxbinding2.view;

import android.view.KeyEvent;
import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: ViewKeyObservable.java */
/* loaded from: classes2.dex */
final class r extends Observable<KeyEvent> {
    private final View a;
    private final Predicate<? super KeyEvent> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public r(View view, Predicate<? super KeyEvent> predicate) {
        this.a = view;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super KeyEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnKeyListener(aVar);
        }
    }

    /* compiled from: ViewKeyObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnKeyListener {
        private final View a;
        private final Predicate<? super KeyEvent> b;
        private final Observer<? super KeyEvent> c;

        a(View view, Predicate<? super KeyEvent> predicate, Observer<? super KeyEvent> observer) {
            this.a = view;
            this.b = predicate;
            this.c = observer;
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.b.test(keyEvent)) {
                    return false;
                }
                this.c.onNext(keyEvent);
                return true;
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnKeyListener(null);
        }
    }
}
