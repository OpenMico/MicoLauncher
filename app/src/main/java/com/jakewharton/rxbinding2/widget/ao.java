package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.Toolbar;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Notification;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ToolbarNavigationClickObservable.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
final class ao extends Observable<Object> {
    private final Toolbar a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ao(Toolbar toolbar) {
        this.a = toolbar;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Object> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setNavigationOnClickListener(aVar);
        }
    }

    /* compiled from: ToolbarNavigationClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnClickListener {
        private final Toolbar a;
        private final Observer<? super Object> b;

        a(Toolbar toolbar, Observer<? super Object> observer) {
            this.a = toolbar;
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
            this.a.setNavigationOnClickListener(null);
        }
    }
}
