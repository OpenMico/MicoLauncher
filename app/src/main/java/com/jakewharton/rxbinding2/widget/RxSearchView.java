package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.internal.Preconditions;
import io.reactivex.functions.Consumer;

/* loaded from: classes2.dex */
public final class RxSearchView {
    @NonNull
    @CheckResult
    public static InitialValueObservable<SearchViewQueryTextEvent> queryTextChangeEvents(@NonNull SearchView searchView) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new ad(searchView);
    }

    @NonNull
    @CheckResult
    public static InitialValueObservable<CharSequence> queryTextChanges(@NonNull SearchView searchView) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new ae(searchView);
    }

    @NonNull
    @CheckResult
    public static Consumer<? super CharSequence> query(@NonNull final SearchView searchView, final boolean z) {
        Preconditions.checkNotNull(searchView, "view == null");
        return new Consumer<CharSequence>() { // from class: com.jakewharton.rxbinding2.widget.RxSearchView.1
            /* renamed from: a */
            public void accept(CharSequence charSequence) {
                searchView.setQuery(charSequence, z);
            }
        };
    }

    private RxSearchView() {
        throw new AssertionError("No instances.");
    }
}
