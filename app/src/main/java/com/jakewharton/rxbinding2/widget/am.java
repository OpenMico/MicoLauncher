package com.jakewharton.rxbinding2.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: TextViewTextObservable.java */
/* loaded from: classes2.dex */
final class am extends InitialValueObservable<CharSequence> {
    private final TextView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public am(TextView textView) {
        this.a = textView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super CharSequence> observer) {
        a aVar = new a(this.a, observer);
        observer.onSubscribe(aVar);
        this.a.addTextChangedListener(aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public CharSequence getInitialValue() {
        return this.a.getText();
    }

    /* compiled from: TextViewTextObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements TextWatcher {
        private final TextView a;
        private final Observer<? super CharSequence> b;

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        a(TextView textView, Observer<? super CharSequence> observer) {
            this.a = textView;
            this.b = observer;
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!isDisposed()) {
                this.b.onNext(charSequence);
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeTextChangedListener(this);
        }
    }
}
