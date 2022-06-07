package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AdapterViewItemClickEventObservable.java */
/* loaded from: classes2.dex */
final class c extends Observable<AdapterViewItemClickEvent> {
    private final AdapterView<?> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(AdapterView<?> adapterView) {
        this.a = adapterView;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super AdapterViewItemClickEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnItemClickListener(aVar);
        }
    }

    /* compiled from: AdapterViewItemClickEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemClickListener {
        private final AdapterView<?> a;
        private final Observer<? super AdapterViewItemClickEvent> b;

        a(AdapterView<?> adapterView, Observer<? super AdapterViewItemClickEvent> observer) {
            this.a = adapterView;
            this.b = observer;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (!isDisposed()) {
                this.b.onNext(AdapterViewItemClickEvent.create(adapterView, view, i, j));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnItemClickListener(null);
        }
    }
}
