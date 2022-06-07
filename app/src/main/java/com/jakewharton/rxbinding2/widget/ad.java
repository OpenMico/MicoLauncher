package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/* compiled from: SearchViewQueryTextChangeEventsObservable.java */
/* loaded from: classes2.dex */
final class ad extends InitialValueObservable<SearchViewQueryTextEvent> {
    private final SearchView a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ad(SearchView searchView) {
        this.a = searchView;
    }

    @Override // com.jakewharton.rxbinding2.InitialValueObservable
    protected void subscribeListener(Observer<? super SearchViewQueryTextEvent> observer) {
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnQueryTextListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public SearchViewQueryTextEvent getInitialValue() {
        SearchView searchView = this.a;
        return SearchViewQueryTextEvent.create(searchView, searchView.getQuery(), false);
    }

    /* compiled from: SearchViewQueryTextChangeEventsObservable.java */
    /* loaded from: classes2.dex */
    static final class a extends MainThreadDisposable implements SearchView.OnQueryTextListener {
        private final SearchView a;
        private final Observer<? super SearchViewQueryTextEvent> b;

        a(SearchView searchView, Observer<? super SearchViewQueryTextEvent> observer) {
            this.a = searchView;
            this.b = observer;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String str) {
            if (isDisposed()) {
                return false;
            }
            this.b.onNext(SearchViewQueryTextEvent.create(this.a, str, false));
            return true;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(String str) {
            if (isDisposed()) {
                return false;
            }
            this.b.onNext(SearchViewQueryTextEvent.create(this.a, str, true));
            return true;
        }

        @Override // io.reactivex.android.MainThreadDisposable
        protected void onDispose() {
            this.a.setOnQueryTextListener(null);
        }
    }
}
