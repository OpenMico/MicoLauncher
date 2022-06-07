package com.jakewharton.rxbinding4.widget;

import android.widget.SearchView;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SearchViewQueryTextEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0007HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00072\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lcom/jakewharton/rxbinding4/widget/SearchViewQueryTextEvent;", "", OneTrack.Event.VIEW, "Landroid/widget/SearchView;", "queryText", "", "isSubmitted", "", "(Landroid/widget/SearchView;Ljava/lang/CharSequence;Z)V", "()Z", "getQueryText", "()Ljava/lang/CharSequence;", "getView", "()Landroid/widget/SearchView;", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class SearchViewQueryTextEvent {
    @NotNull
    private final SearchView a;
    @NotNull
    private final CharSequence b;
    private final boolean c;

    public static /* synthetic */ SearchViewQueryTextEvent copy$default(SearchViewQueryTextEvent searchViewQueryTextEvent, SearchView searchView, CharSequence charSequence, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            searchView = searchViewQueryTextEvent.a;
        }
        if ((i & 2) != 0) {
            charSequence = searchViewQueryTextEvent.b;
        }
        if ((i & 4) != 0) {
            z = searchViewQueryTextEvent.c;
        }
        return searchViewQueryTextEvent.copy(searchView, charSequence, z);
    }

    @NotNull
    public final SearchView component1() {
        return this.a;
    }

    @NotNull
    public final CharSequence component2() {
        return this.b;
    }

    public final boolean component3() {
        return this.c;
    }

    @NotNull
    public final SearchViewQueryTextEvent copy(@NotNull SearchView view, @NotNull CharSequence queryText, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(queryText, "queryText");
        return new SearchViewQueryTextEvent(view, queryText, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SearchViewQueryTextEvent)) {
            return false;
        }
        SearchViewQueryTextEvent searchViewQueryTextEvent = (SearchViewQueryTextEvent) obj;
        return Intrinsics.areEqual(this.a, searchViewQueryTextEvent.a) && Intrinsics.areEqual(this.b, searchViewQueryTextEvent.b) && this.c == searchViewQueryTextEvent.c;
    }

    public int hashCode() {
        SearchView searchView = this.a;
        int i = 0;
        int hashCode = (searchView != null ? searchView.hashCode() : 0) * 31;
        CharSequence charSequence = this.b;
        if (charSequence != null) {
            i = charSequence.hashCode();
        }
        int i2 = (hashCode + i) * 31;
        boolean z = this.c;
        if (z) {
            z = true;
        }
        int i3 = z ? 1 : 0;
        int i4 = z ? 1 : 0;
        int i5 = z ? 1 : 0;
        return i2 + i3;
    }

    @NotNull
    public String toString() {
        return "SearchViewQueryTextEvent(view=" + this.a + ", queryText=" + this.b + ", isSubmitted=" + this.c + ")";
    }

    public SearchViewQueryTextEvent(@NotNull SearchView view, @NotNull CharSequence queryText, boolean z) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(queryText, "queryText");
        this.a = view;
        this.b = queryText;
        this.c = z;
    }

    @NotNull
    public final SearchView getView() {
        return this.a;
    }

    @NotNull
    public final CharSequence getQueryText() {
        return this.b;
    }

    public final boolean isSubmitted() {
        return this.c;
    }
}
