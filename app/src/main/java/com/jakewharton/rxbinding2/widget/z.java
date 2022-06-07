package com.jakewharton.rxbinding2.widget;

import android.view.MenuItem;
import android.widget.PopupMenu;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: PopupMenuItemClickObservable.java */
/* loaded from: classes2.dex */
final class z extends Observable<MenuItem> {
    private final PopupMenu a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public z(PopupMenu popupMenu) {
        this.a = popupMenu;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super MenuItem> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnMenuItemClickListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* compiled from: PopupMenuItemClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements PopupMenu.OnMenuItemClickListener {
        private final PopupMenu a;
        private final Observer<? super MenuItem> b;

        a(PopupMenu popupMenu, Observer<? super MenuItem> observer) {
            this.a = popupMenu;
            this.b = observer;
        }

        @Override // android.widget.PopupMenu.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (isDisposed()) {
                return false;
            }
            this.b.onNext(menuItem);
            return true;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnMenuItemClickListener(null);
        }
    }
}
