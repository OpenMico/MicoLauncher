package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewGroup;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: ViewGroupHierarchyChangeEventObservable.java */
/* loaded from: classes2.dex */
final class p extends Observable<ViewGroupHierarchyChangeEvent> {
    private final ViewGroup a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(ViewGroup viewGroup) {
        this.a = viewGroup;
    }

    @Override // io.reactivex.Observable
    protected void subscribeActual(Observer<? super ViewGroupHierarchyChangeEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            observer.onSubscribe(aVar);
            this.a.setOnHierarchyChangeListener(aVar);
        }
    }

    /* compiled from: ViewGroupHierarchyChangeEventObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements ViewGroup.OnHierarchyChangeListener {
        private final ViewGroup a;
        private final Observer<? super ViewGroupHierarchyChangeEvent> b;

        a(ViewGroup viewGroup, Observer<? super ViewGroupHierarchyChangeEvent> observer) {
            this.a = viewGroup;
            this.b = observer;
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            if (!isDisposed()) {
                this.b.onNext(ViewGroupHierarchyChildViewAddEvent.create(this.a, view2));
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            if (!isDisposed()) {
                this.b.onNext(ViewGroupHierarchyChildViewRemoveEvent.create(this.a, view2));
            }
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnHierarchyChangeListener(null);
        }
    }
}
