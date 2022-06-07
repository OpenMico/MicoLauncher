package com.jakewharton.rxbinding2.widget;

import android.widget.AbsListView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_AbsListViewScrollEvent.java */
/* loaded from: classes2.dex */
final class j extends AbsListViewScrollEvent {
    private final AbsListView a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(AbsListView absListView, int i, int i2, int i3, int i4) {
        if (absListView != null) {
            this.a = absListView;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    @NonNull
    public AbsListView view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int scrollState() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int firstVisibleItem() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int visibleItemCount() {
        return this.d;
    }

    @Override // com.jakewharton.rxbinding2.widget.AbsListViewScrollEvent
    public int totalItemCount() {
        return this.e;
    }

    public String toString() {
        return "AbsListViewScrollEvent{view=" + this.a + ", scrollState=" + this.b + ", firstVisibleItem=" + this.c + ", visibleItemCount=" + this.d + ", totalItemCount=" + this.e + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbsListViewScrollEvent)) {
            return false;
        }
        AbsListViewScrollEvent absListViewScrollEvent = (AbsListViewScrollEvent) obj;
        return this.a.equals(absListViewScrollEvent.view()) && this.b == absListViewScrollEvent.scrollState() && this.c == absListViewScrollEvent.firstVisibleItem() && this.d == absListViewScrollEvent.visibleItemCount() && this.e == absListViewScrollEvent.totalItemCount();
    }

    public int hashCode() {
        return ((((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b) * 1000003) ^ this.c) * 1000003) ^ this.d) * 1000003) ^ this.e;
    }
}
