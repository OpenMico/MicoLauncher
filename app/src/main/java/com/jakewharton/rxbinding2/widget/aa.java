package com.jakewharton.rxbinding2.widget;

import android.widget.RadioGroup;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: RadioGroupCheckedChangeObservable.java */
/* loaded from: classes2.dex */
final class aa extends InitialValueObservable<Integer> {
    private final RadioGroup a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aa(RadioGroup radioGroup) {
        this.a = radioGroup;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnCheckedChangeListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public Integer getInitialValue() {
        return Integer.valueOf(this.a.getCheckedRadioButtonId());
    }

    /* compiled from: RadioGroupCheckedChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements RadioGroup.OnCheckedChangeListener {
        private final RadioGroup a;
        private final Observer<? super Integer> b;
        private int c = -1;

        a(RadioGroup radioGroup, Observer<? super Integer> observer) {
            this.a = radioGroup;
            this.b = observer;
        }

        @Override // android.widget.RadioGroup.OnCheckedChangeListener
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (!isDisposed() && i != this.c) {
                this.c = i;
                this.b.onNext(Integer.valueOf(i));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnCheckedChangeListener(null);
        }
    }
}
