package com.jakewharton.rxbinding2.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: TextViewAfterTextChangeEventObservable.java */
/* loaded from: classes2.dex */
final class ah extends InitialValueObservable<TextViewAfterTextChangeEvent> {
    private final TextView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ah(TextView textView) {
        this.a = textView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super TextViewAfterTextChangeEvent> observer) {
        a aVar = new a(this.a, observer);
        observer.onSubscribe(aVar);
        this.a.addTextChangedListener(aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public TextViewAfterTextChangeEvent getInitialValue() {
        TextView textView = this.a;
        return TextViewAfterTextChangeEvent.create(textView, textView.getEditableText());
    }

    /* compiled from: TextViewAfterTextChangeEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements TextWatcher {
        private final TextView a;
        private final Observer<? super TextViewAfterTextChangeEvent> b;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        a(TextView textView, Observer<? super TextViewAfterTextChangeEvent> observer) {
            this.a = textView;
            this.b = observer;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            this.b.onNext(TextViewAfterTextChangeEvent.create(this.a, editable));
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.removeTextChangedListener(this);
        }
    }
}
