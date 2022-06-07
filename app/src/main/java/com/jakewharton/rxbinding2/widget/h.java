package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AdapterViewSelectionObservable.java */
/* loaded from: classes2.dex */
final class h extends InitialValueObservable<AdapterViewSelectionEvent> {
    private final AdapterView<?> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(AdapterView<?> adapterView) {
        this.a = adapterView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super AdapterViewSelectionEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnItemSelectedListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public AdapterViewSelectionEvent getInitialValue() {
        int selectedItemPosition = this.a.getSelectedItemPosition();
        if (selectedItemPosition == -1) {
            return AdapterViewNothingSelectionEvent.create(this.a);
        }
        return AdapterViewItemSelectionEvent.create(this.a, this.a.getSelectedView(), selectedItemPosition, this.a.getSelectedItemId());
    }

    /* compiled from: AdapterViewSelectionObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemSelectedListener {
        private final AdapterView<?> a;
        private final Observer<? super AdapterViewSelectionEvent> b;

        a(AdapterView<?> adapterView, Observer<? super AdapterViewSelectionEvent> observer) {
            this.a = adapterView;
            this.b = observer;
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (!isDisposed()) {
                this.b.onNext(AdapterViewItemSelectionEvent.create(adapterView, view, i, j));
            }
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public void onNothingSelected(AdapterView<?> adapterView) {
            if (!isDisposed()) {
                this.b.onNext(AdapterViewNothingSelectionEvent.create(adapterView));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnItemSelectedListener(null);
        }
    }
}
