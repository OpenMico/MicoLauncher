package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: TextViewEditorActionEventObservable.java */
/* loaded from: classes2.dex */
final class aj extends Observable<TextViewEditorActionEvent> {
    private final TextView a;
    private final Predicate<? super TextViewEditorActionEvent> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public aj(TextView textView, Predicate<? super TextViewEditorActionEvent> predicate) {
        this.a = textView;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super TextViewEditorActionEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer, this.b);
            observer.onSubscribe(aVar);
            this.a.setOnEditorActionListener(aVar);
        }
    }

    /* compiled from: TextViewEditorActionEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements TextView.OnEditorActionListener {
        private final TextView a;
        private final Observer<? super TextViewEditorActionEvent> b;
        private final Predicate<? super TextViewEditorActionEvent> c;

        a(TextView textView, Observer<? super TextViewEditorActionEvent> observer, Predicate<? super TextViewEditorActionEvent> predicate) {
            this.a = textView;
            this.b = observer;
            this.c = predicate;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            TextViewEditorActionEvent create = TextViewEditorActionEvent.create(this.a, i, keyEvent);
            try {
                if (isDisposed() || !this.c.test(create)) {
                    return false;
                }
                this.b.onNext(create);
                return true;
            } catch (Exception e) {
                this.b.onError(e);
                dispose();
                return false;
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnEditorActionListener(null);
        }
    }
}
