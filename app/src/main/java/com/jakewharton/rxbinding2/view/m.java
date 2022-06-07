package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ViewClickObservable.java */
/* loaded from: classes2.dex */
public final class m extends Observable<Object> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnClickListener(aVar);
        }
    }

    /* compiled from: ViewClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnClickListener {
        private final View a;
        private final Observer<? super Object> b;

        a(View view, Observer<? super Object> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (!isDisposed()) {
                this.b.onNext(Notification.INSTANCE);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnClickListener(null);
        }
    }
}
