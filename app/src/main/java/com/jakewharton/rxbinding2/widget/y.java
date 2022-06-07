package com.jakewharton.rxbinding2.widget;

import android.widget.PopupMenu;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: PopupMenuDismissObservable.java */
/* loaded from: classes2.dex */
final class y extends Observable<Object> {
    private final PopupMenu a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public y(PopupMenu popupMenu) {
        this.a = popupMenu;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnDismissListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* compiled from: PopupMenuDismissObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements PopupMenu.OnDismissListener {
        private final PopupMenu a;
        private final Observer<? super Object> b;

        a(PopupMenu popupMenu, Observer<? super Object> observer) {
            this.a = popupMenu;
            this.b = observer;
        }

        @Override // android.widget.PopupMenu.OnDismissListener
        public void onDismiss(PopupMenu popupMenu) {
            if (!isDisposed()) {
                this.b.onNext(Notification.INSTANCE);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnDismissListener(null);
        }
    }
}
