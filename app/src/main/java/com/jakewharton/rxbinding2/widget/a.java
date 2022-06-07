package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: AbsListViewScrollEventObservable.java */
/* loaded from: classes2.dex */
final class a extends Observable<AbsListViewScrollEvent> {
    private final AbsListView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(AbsListView absListView) {
        this.a = absListView;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super AbsListViewScrollEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            C0126a aVar = new C0126a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnScrollListener(aVar);
        }
    }

    /* compiled from: AbsListViewScrollEventObservable.java */
    /* renamed from: com.jakewharton.rxbinding2.widget.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    static final class C0126a extends MainThreadDisposable implements AbsListView.OnScrollListener {
        private final AbsListView a;
        private final Observer<? super AbsListViewScrollEvent> b;
        private int c = 0;

        C0126a(AbsListView absListView, Observer<? super AbsListViewScrollEvent> observer) {
            this.a = absListView;
            this.b = observer;
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            this.c = i;
            if (!isDisposed()) {
                AbsListView absListView2 = this.a;
                this.b.onNext(AbsListViewScrollEvent.create(absListView2, i, absListView2.getFirstVisiblePosition(), this.a.getChildCount(), this.a.getCount()));
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            if (!isDisposed()) {
                this.b.onNext(AbsListViewScrollEvent.create(this.a, this.c, i, i2, i3));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnScrollListener(null);
        }
    }
}
