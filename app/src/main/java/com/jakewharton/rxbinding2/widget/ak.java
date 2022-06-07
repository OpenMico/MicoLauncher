package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: TextViewEditorActionObservable.java */
/* loaded from: classes2.dex */
final class ak extends Observable<Integer> {
    private final TextView a;
    private final Predicate<? super Integer> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ak(TextView textView, Predicate<? super Integer> predicate) {
        this.a = textView;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer, this.b);
            observer.onSubscribe(aVar);
            this.a.setOnEditorActionListener(aVar);
        }
    }

    /* compiled from: TextViewEditorActionObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements TextView.OnEditorActionListener {
        private final TextView a;
        private final Observer<? super Integer> b;
        private final Predicate<? super Integer> c;

        a(TextView textView, Observer<? super Integer> observer, Predicate<? super Integer> predicate) {
            this.a = textView;
            this.b = observer;
            this.c = predicate;
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            try {
                if (isDisposed() || !this.c.test(Integer.valueOf(i))) {
                    return false;
                }
                this.b.onNext(Integer.valueOf(i));
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
