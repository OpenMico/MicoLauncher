package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: MenuItemClickOnSubscribe.java */
/* loaded from: classes2.dex */
final class j extends Observable<Object> {
    private final MenuItem a;
    private final Predicate<? super MenuItem> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(MenuItem menuItem, Predicate<? super MenuItem> predicate) {
        this.a = menuItem;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnMenuItemClickListener(aVar);
        }
    }

    /* compiled from: MenuItemClickOnSubscribe.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements MenuItem.OnMenuItemClickListener {
        private final MenuItem a;
        private final Predicate<? super MenuItem> b;
        private final Observer<? super Object> c;

        a(MenuItem menuItem, Predicate<? super MenuItem> predicate, Observer<? super Object> observer) {
            this.a = menuItem;
            this.b = predicate;
            this.c = observer;
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.b.test(this.a)) {
                    return false;
                }
                this.c.onNext(Notification.INSTANCE);
                return true;
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnMenuItemClickListener(null);
        }
    }
}
