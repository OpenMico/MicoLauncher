package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AutoCompleteTextViewItemClickEventObservable.java */
/* loaded from: classes2.dex */
final class i extends Observable<AdapterViewItemClickEvent> {
    private final AutoCompleteTextView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(AutoCompleteTextView autoCompleteTextView) {
        this.a = autoCompleteTextView;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super AdapterViewItemClickEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnItemClickListener(aVar);
        }
    }

    /* compiled from: AutoCompleteTextViewItemClickEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemClickListener {
        private final AutoCompleteTextView a;
        private final Observer<? super AdapterViewItemClickEvent> b;

        a(AutoCompleteTextView autoCompleteTextView, Observer<? super AdapterViewItemClickEvent> observer) {
            this.a = autoCompleteTextView;
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
