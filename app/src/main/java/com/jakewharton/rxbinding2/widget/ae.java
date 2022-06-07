package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: SearchViewQueryTextChangesObservable.java */
/* loaded from: classes2.dex */
final class ae extends InitialValueObservable<CharSequence> {
    private final SearchView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ae(SearchView searchView) {
        this.a = searchView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super CharSequence> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnQueryTextListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public CharSequence getInitialValue() {
        return this.a.getQuery();
    }

    /* compiled from: SearchViewQueryTextChangesObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements SearchView.OnQueryTextListener {
        private final SearchView a;
        private final Observer<? super CharSequence> b;

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(String str) {
            return false;
        }

        a(SearchView searchView, Observer<? super CharSequence> observer) {
            this.a = searchView;
            this.b = observer;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            if (isDisposed()) {
                return false;
            }
            this.b.onNext(str);
            return true;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnQueryTextListener(null);
        }
    }
}
