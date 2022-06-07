package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AdapterViewItemClickObservable.java */
/* loaded from: classes2.dex */
final class d extends Observable<Integer> {
    private final AdapterView<?> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(AdapterView<?> adapterView) {
        this.a = adapterView;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnItemClickListener(aVar);
        }
    }

    /* compiled from: AdapterViewItemClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemClickListener {
        private final AdapterView<?> a;
        private final Observer<? super Integer> b;

        a(AdapterView<?> adapterView, Observer<? super Integer> observer) {
            this.a = adapterView;
            this.b = observer;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (!isDisposed()) {
                this.b.onNext(Integer.valueOf(i));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnItemClickListener(null);
        }
    }
}
