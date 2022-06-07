package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import java.util.concurrent.Callable;

/* compiled from: ViewLongClickObservable.java */
/* loaded from: classes2.dex */
final class u extends Observable<Object> {
    private final View a;
    private final Callable<Boolean> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public u(View view, Callable<Boolean> callable) {
        this.a = view;
        this.b = callable;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnLongClickListener(aVar);
        }
    }

    /* compiled from: ViewLongClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnLongClickListener {
        private final View a;
        private final Observer<? super Object> b;
        private final Callable<Boolean> c;

        a(View view, Callable<Boolean> callable, Observer<? super Object> observer) {
            this.a = view;
            this.b = observer;
            this.c = callable;
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.c.call().booleanValue()) {
                    return false;
                }
                this.b.onNext(Notification.INSTANCE);
                return true;
            } catch (Exception e) {
                this.b.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnLongClickListener(null);
        }
    }
}
