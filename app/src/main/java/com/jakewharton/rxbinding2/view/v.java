package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.RequiresApi;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewScrollChangeEventObservable.java */
@RequiresApi(23)
/* loaded from: classes2.dex */
final class v extends Observable<ViewScrollChangeEvent> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public v(View view) {
        this.a = view;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super ViewScrollChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnScrollChangeListener(aVar);
        }
    }

    /* compiled from: ViewScrollChangeEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnScrollChangeListener {
        private final View a;
        private final Observer<? super ViewScrollChangeEvent> b;

        a(View view, Observer<? super ViewScrollChangeEvent> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnScrollChangeListener
        public void onScrollChange(View view, int i, int i2, int i3, int i4) {
            if (!isDisposed()) {
                this.b.onNext(ViewScrollChangeEvent.create(view, i, i2, i3, i4));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnScrollChangeListener(null);
        }
    }
}
