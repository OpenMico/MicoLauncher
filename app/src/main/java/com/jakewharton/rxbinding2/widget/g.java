package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AdapterViewItemSelectionObservable.java */
/* loaded from: classes2.dex */
final class g extends InitialValueObservable<Integer> {
    private final AdapterView<?> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(AdapterView<?> adapterView) {
        this.a = adapterView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnItemSelectedListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Integer getInitialValue() {
        return Integer.valueOf(this.a.getSelectedItemPosition());
    }

    /* compiled from: AdapterViewItemSelectionObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemSelectedListener {
        private final AdapterView<?> a;
        private final Observer<? super Integer> b;

        a(AdapterView<?> adapterView, Observer<? super Integer> observer) {
            this.a = adapterView;
            this.b = observer;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (!isDisposed()) {
                this.b.onNext(Integer.valueOf(i));
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
            if (!isDisposed()) {
                this.b.onNext(-1);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnItemSelectedListener(null);
        }
    }
}
