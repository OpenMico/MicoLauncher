package com.jakewharton.rxbinding2.widget;

import android.database.DataSetObserver;
import android.widget.Adapter;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AdapterDataChangeObservable.java */
/* loaded from: classes2.dex */
final class b<T extends Adapter> extends InitialValueObservable<T> {
    private final T a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(T t) {
        this.a = t;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super T> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.registerDataSetObserver(aVar.a);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public T getInitialValue() {
        return this.a;
    }

    /* compiled from: AdapterDataChangeObservable.java */
    /* loaded from: classes2.dex */
    static final class a<T extends Adapter> extends MainThreadDisposable {
        final DataSetObserver a;
        private final T b;

        a(final T t, final Observer<? super T> observer) {
            this.b = t;
            this.a = new DataSetObserver() { // from class: com.jakewharton.rxbinding2.widget.b.a.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    if (!a.this.isDisposed()) {
                        observer.onNext(t);
                    }
                }
            };
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.b.unregisterDataSetObserver(this.a);
        }
    }
}
