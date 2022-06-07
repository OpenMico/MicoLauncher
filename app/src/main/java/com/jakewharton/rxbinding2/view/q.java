package com.jakewharton.rxbinding2.view;

import android.view.MotionEvent;
import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: ViewHoverObservable.java */
/* loaded from: classes2.dex */
final class q extends Observable<MotionEvent> {
    private final View a;
    private final Predicate<? super MotionEvent> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(View view, Predicate<? super MotionEvent> predicate) {
        this.a = view;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super MotionEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnHoverListener(aVar);
        }
    }

    /* compiled from: ViewHoverObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnHoverListener {
        private final View a;
        private final Predicate<? super MotionEvent> b;
        private final Observer<? super MotionEvent> c;

        a(View view, Predicate<? super MotionEvent> predicate, Observer<? super MotionEvent> observer) {
            this.a = view;
            this.b = predicate;
            this.c = observer;
        }

        @Override // android.view.View.OnHoverListener
        public boolean onHover(View view, MotionEvent motionEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.b.test(motionEvent)) {
                    return false;
                }
                this.c.onNext(motionEvent);
                return true;
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnHoverListener(null);
        }
    }
}
