package com.jakewharton.rxbinding4.widget;

import android.widget.SearchView;
import androidx.annotation.CheckResult;
import com.jakewharton.rxbinding4.InitialValueObservable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 3}, d1 = {"com/jakewharton/rxbinding4/widget/RxSearchView__SearchViewQueryConsumerKt", "com/jakewharton/rxbinding4/widget/RxSearchView__SearchViewQueryTextChangeEventsObservableKt", "com/jakewharton/rxbinding4/widget/RxSearchView__SearchViewQueryTextChangesObservableKt"}, k = 4, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class RxSearchView {
    @CheckResult
    @NotNull
    public static final Consumer<? super CharSequence> query(@NotNull SearchView searchView, boolean z) {
        return af.a(searchView, z);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<SearchViewQueryTextEvent> queryTextChangeEvents(@NotNull SearchView searchView) {
        return ag.a(searchView);
    }

    @CheckResult
    @NotNull
    public static final InitialValueObservable<CharSequence> queryTextChanges(@NotNull SearchView searchView) {
        return ah.a(searchView);
    }
}
