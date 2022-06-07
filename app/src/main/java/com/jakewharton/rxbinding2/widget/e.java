package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.functions.Predicate;

/* compiled from: AdapterViewItemLongClickEventObservable.java */
/* loaded from: classes2.dex */
final class e extends Observable<AdapterViewItemLongClickEvent> {
    private final AdapterView<?> a;
    private final Predicate<? super AdapterViewItemLongClickEvent> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(AdapterView<?> adapterView, Predicate<? super AdapterViewItemLongClickEvent> predicate) {
        this.a = adapterView;
        this.b = predicate;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super AdapterViewItemLongClickEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer, this.b);
            observer.onSubscribe(aVar);
            this.a.setOnItemLongClickListener(aVar);
        }
    }

    /* compiled from: AdapterViewItemLongClickEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements AdapterView.OnItemLongClickListener {
        private final AdapterView<?> a;
        private final Observer<? super AdapterViewItemLongClickEvent> b;
        private final Predicate<? super AdapterViewItemLongClickEvent> c;

        a(AdapterView<?> adapterView, Observer<? super AdapterViewItemLongClickEvent> observer, Predicate<? super AdapterViewItemLongClickEvent> predicate) {
            this.a = adapterView;
            this.b = observer;
            this.c = predicate;
        }

        @Override // android.widget.AdapterView.OnItemLongClickListener
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (isDisposed()) {
                return false;
            }
            AdapterViewItemLongClickEvent create = AdapterViewItemLongClickEvent.create(adapterView, view, i, j);
            try {
                if (!this.c.test(create)) {
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
            this.a.setOnItemLongClickListener(null);
        }
    }
}
