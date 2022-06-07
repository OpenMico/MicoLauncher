package com.jakewharton.rxbinding2.widget;

import android.widget.SearchView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_SearchViewQueryTextEvent.java */
/* loaded from: classes2.dex */
final class p extends SearchViewQueryTextEvent {
    private final SearchView a;
    private final CharSequence b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(SearchView searchView, CharSequence charSequence, boolean z) {
        if (searchView != null) {
            this.a = searchView;
            if (charSequence != null) {
                this.b = charSequence;
                this.c = z;
                return;
            }
            throw new NullPointerException("Null queryText");
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
    @NonNull
    public SearchView view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
    @NonNull
    public CharSequence queryText() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent
    public boolean isSubmitted() {
        return this.c;
    }

    public String toString() {
        return "SearchViewQueryTextEvent{view=" + this.a + ", queryText=" + ((Object) this.b) + ", isSubmitted=" + this.c + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SearchViewQueryTextEvent)) {
            return false;
        }
        SearchViewQueryTextEvent searchViewQueryTextEvent = (SearchViewQueryTextEvent) obj;
        return this.a.equals(searchViewQueryTextEvent.view()) && this.b.equals(searchViewQueryTextEvent.queryText()) && this.c == searchViewQueryTextEvent.isSubmitted();
    }

    public int hashCode() {
        return ((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode()) * 1000003) ^ (this.c ? 1231 : 1237);
    }
}
