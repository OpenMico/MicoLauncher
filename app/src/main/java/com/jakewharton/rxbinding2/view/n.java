package com.jakewharton.rxbinding2.view;

import android.view.DragEvent;
import android.view.View;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: ViewDragObservable.java */
/* loaded from: classes2.dex */
final class n extends Observable<DragEvent> {
    private final View a;
    private final Predicate<? super DragEvent> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(View view, Predicate<? super DragEvent> predicate) {
        this.a = view;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super DragEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnDragListener(aVar);
        }
    }

    /* compiled from: ViewDragObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnDragListener {
        private final View a;
        private final Predicate<? super DragEvent> b;
        private final Observer<? super DragEvent> c;

        a(View view, Predicate<? super DragEvent> predicate, Observer<? super DragEvent> observer) {
            this.a = view;
            this.b = predicate;
            this.c = observer;
        }

        @Override // android.view.View.OnDragListener
        public boolean onDrag(View view, DragEvent dragEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.b.test(dragEvent)) {
                    return false;
                }
                this.c.onNext(dragEvent);
                return true;
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnDragListener(null);
        }
    }
}
