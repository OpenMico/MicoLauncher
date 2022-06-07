package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes2.dex */
public abstract class SearchViewQueryTextEvent {
    public abstract boolean isSubmitted();

    @NonNull
    public abstract CharSequence queryText();

    @NonNull
    public abstract SearchView view();

    @NonNull
    @CheckResult
    public static SearchViewQueryTextEvent create(@NonNull SearchView searchView, @NonNull CharSequence charSequence, boolean z) {
        return new p(searchView, charSequence, z);
    }
}
