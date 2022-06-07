package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: MenuItemActionViewEventObservable.java */
/* loaded from: classes2.dex */
final class i extends Observable<MenuItemActionViewEvent> {
    private final MenuItem a;
    private final Predicate<? super MenuItemActionViewEvent> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(MenuItem menuItem, Predicate<? super MenuItemActionViewEvent> predicate) {
        this.a = menuItem;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super MenuItemActionViewEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, this.b, observer);
            observer.onSubscribe(aVar);
            this.a.setOnActionExpandListener(aVar);
        }
    }

    /* compiled from: MenuItemActionViewEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements MenuItem.OnActionExpandListener {
        private final MenuItem a;
        private final Predicate<? super MenuItemActionViewEvent> b;
        private final Observer<? super MenuItemActionViewEvent> c;

        a(MenuItem menuItem, Predicate<? super MenuItemActionViewEvent> predicate, Observer<? super MenuItemActionViewEvent> observer) {
            this.a = menuItem;
            this.b = predicate;
            this.c = observer;
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return a(MenuItemActionViewExpandEvent.create(menuItem));
        }

        @Override // android.view.MenuItem.OnActionExpandListener
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return a(MenuItemActionViewCollapseEvent.create(menuItem));
        }

        private boolean a(MenuItemActionViewEvent menuItemActionViewEvent) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.b.test(menuItemActionViewEvent)) {
                    return false;
                }
                this.c.onNext(menuItemActionViewEvent);
                return true;
            } catch (Exception e) {
                this.c.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnActionExpandListener(null);
        }
    }
}
