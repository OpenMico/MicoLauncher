package com.jakewharton.rxbinding2.widget;

import android.view.MenuItem;
import android.widget.Toolbar;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ToolbarItemClickObservable.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
final class an extends Observable<MenuItem> {
    private final Toolbar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public an(Toolbar toolbar) {
        this.a = toolbar;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super MenuItem> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnMenuItemClickListener(aVar);
        }
    }

    /* compiled from: ToolbarItemClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements Toolbar.OnMenuItemClickListener {
        private final Toolbar a;
        private final Observer<? super MenuItem> b;

        a(Toolbar toolbar, Observer<? super MenuItem> observer) {
            this.a = toolbar;
            this.b = observer;
        }

        @Override // android.widget.Toolbar.OnMenuItemClickListener
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
