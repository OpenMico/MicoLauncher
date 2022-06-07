package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import java.util.concurrent.Callable;

/* compiled from: AdapterViewItemLongClickObservable.java */
/* loaded from: classes2.dex */
final class f extends Observable<Integer> {
    private final AdapterView<?> a;
    private final Callable<Boolean> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(AdapterView<?> adapterView, Callable<Boolean> callable) {
        this.a = adapterView;
        this.b = callable;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super Integer> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer, this.b);
            observer.onSubscribe(aVar);
            this.a.setOnItemLongClickListener(aVar);
        }
    }

    /* compiled from: AdapterViewItemLongClickObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemLongClickListener {
        private final AdapterView<?> a;
        private final Observer<? super Integer> b;
        private final Callable<Boolean> c;

        a(AdapterView<?> adapterView, Observer<? super Integer> observer, Callable<Boolean> callable) {
            this.a = adapterView;
            this.b = observer;
            this.c = callable;
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (isDisposed()) {
                return false;
            }
            try {
                if (!this.c.call().booleanValue()) {
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
            this.a.setOnItemLongClickListener(null);
        }
    }
}
