package com.jakewharton.rxbinding2.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: TextViewTextChangeEventObservable.java */
/* loaded from: classes2.dex */
final class al extends InitialValueObservable<TextViewTextChangeEvent> {
    private final TextView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public al(TextView textView) {
        this.a = textView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super TextViewTextChangeEvent> observer) {
        a aVar = new a(this.a, observer);
        observer.onSubscribe(aVar);
        this.a.addTextChangedListener(aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public TextViewTextChangeEvent getInitialValue() {
        TextView textView = this.a;
        return TextViewTextChangeEvent.create(textView, textView.getText(), 0, 0, 0);
    }

    /* compiled from: TextViewTextChangeEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements TextWatcher {
        private final TextView a;
        private final Observer<? super TextViewTextChangeEvent> b;

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        a(TextView textView, Observer<? super TextViewTextChangeEvent> observer) {
            this.a = textView;
            this.b = observer;
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!isDisposed()) {
                this.b.onNext(TextViewTextChangeEvent.create(this.a, charSequence, i, i2, i3));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeTextChangedListener(this);
        }
    }
}
