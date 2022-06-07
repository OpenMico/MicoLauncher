package com.jakewharton.rxbinding2.view;

import android.view.View;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewFocusChangeObservable.java */
/* loaded from: classes2.dex */
final class o extends InitialValueObservable<Boolean> {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public o(View view) {
        this.a = view;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super Boolean> observer) {
        a aVar = new a(this.a, observer);
        observer.onSubscribe(aVar);
        this.a.setOnFocusChangeListener(aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Boolean getInitialValue() {
        return Boolean.valueOf(this.a.hasFocus());
    }

    /* compiled from: ViewFocusChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements View.OnFocusChangeListener {
        private final View a;
        private final Observer<? super Boolean> b;

        a(View view, Observer<? super Boolean> observer) {
            this.a = view;
            this.b = observer;
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            if (!isDisposed()) {
                this.b.onNext(Boolean.valueOf(z));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnFocusChangeListener(null);
        }
    }
}
