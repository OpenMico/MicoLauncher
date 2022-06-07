package com.jakewharton.rxbinding4.widget;

import android.widget.SearchView;
import com.jakewharton.rxbinding4.InitialValueObservable;
import com.jakewharton.rxbinding4.internal.Preconditions;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.OneTrack;
import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: SearchViewQueryTextChangeEventsObservable.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\fH\u0014R\u0014\u0010\u0006\u001a\u00020\u00028TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SearchViewQueryTextChangeEventsObservable;", "Lcom/jakewharton/rxbinding4/InitialValueObservable;", "Lcom/jakewharton/rxbinding4/widget/SearchViewQueryTextEvent;", OneTrack.Event.VIEW, "Landroid/widget/SearchView;", "(Landroid/widget/SearchView;)V", "initialValue", "getInitialValue", "()Lcom/jakewharton/rxbinding4/widget/SearchViewQueryTextEvent;", "subscribeListener", "", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Listener", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
final class as extends InitialValueObservable<SearchViewQueryTextEvent> {
    private final SearchView a;

    public as(@NotNull SearchView view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        this.a = view;
    }

    @Override // com.jakewharton.rxbinding4.InitialValueObservable
    protected void subscribeListener(@NotNull Observer<? super SearchViewQueryTextEvent> observer) {
        Intrinsics.checkParameterIsNotNull(observer, "observer");
        if (Preconditions.checkMainThread(observer)) {
            a aVar = new a(this.a, observer);
            this.a.setOnQueryTextListener(aVar);
            observer.onSubscribe(aVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @NotNull
    /* renamed from: a */
    public SearchViewQueryTextEvent getInitialValue() {
        SearchView searchView = this.a;
        CharSequence query = searchView.getQuery();
        Intrinsics.checkExpressionValueIsNotNull(query, "view.query");
        return new SearchViewQueryTextEvent(searchView, query, false);
    }

    /* compiled from: SearchViewQueryTextChangeEventsObservable.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0014J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000eH\u0016R\u0016\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SearchViewQueryTextChangeEventsObservable$Listener;", "Lio/reactivex/rxjava3/android/MainThreadDisposable;", "Landroid/widget/SearchView$OnQueryTextListener;", OneTrack.Event.VIEW, "Landroid/widget/SearchView;", "observer", "Lio/reactivex/rxjava3/core/Observer;", "Lcom/jakewharton/rxbinding4/widget/SearchViewQueryTextEvent;", "(Landroid/widget/SearchView;Lio/reactivex/rxjava3/core/Observer;)V", "onDispose", "", "onQueryTextChange", "", ai.az, "", "onQueryTextSubmit", "query", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    private static final class a extends MainThreadDisposable implements SearchView.OnQueryTextListener {
        private final SearchView a;
        private final Observer<? super SearchViewQueryTextEvent> b;

        public a(@NotNull SearchView view, @NotNull Observer<? super SearchViewQueryTextEvent> observer) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(observer, "observer");
            this.a = view;
            this.b = observer;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(@NotNull String s) {
            Intrinsics.checkParameterIsNotNull(s, "s");
            if (isDisposed()) {
                return false;
            }
            this.b.onNext(new SearchViewQueryTextEvent(this.a, s, false));
            return true;
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(@NotNull String query) {
            Intrinsics.checkParameterIsNotNull(query, "query");
            if (isDisposed()) {
                return false;
            }
            this.b.onNext(new SearchViewQueryTextEvent(this.a, query, true));
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.reactivex.rxjava3.android.MainThreadDisposable
        public void onDispose() {
            this.a.setOnQueryTextListener(null);
        }
    }
}
