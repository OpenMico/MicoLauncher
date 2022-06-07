package com.jakewharton.rxbinding2.widget;

import android.widget.CompoundButton;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: CompoundButtonCheckedChangeObservable.java */
/* loaded from: classes2.dex */
final class x extends InitialValueObservable<Boolean> {
    private final CompoundButton a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public x(CompoundButton compoundButton) {
        this.a = compoundButton;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super Boolean> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnCheckedChangeListener(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Boolean getInitialValue() {
        return Boolean.valueOf(this.a.isChecked());
    }

    /* compiled from: CompoundButtonCheckedChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements CompoundButton.OnCheckedChangeListener {
        private final CompoundButton a;
        private final Observer<? super Boolean> b;

        a(CompoundButton compoundButton, Observer<? super Boolean> observer) {
            this.a = compoundButton;
            this.b = observer;
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (!isDisposed()) {
                this.b.onNext(Boolean.valueOf(z));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnCheckedChangeListener(null);
        }
    }
}
