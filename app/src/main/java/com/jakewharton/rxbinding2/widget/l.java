package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_AdapterViewItemLongClickEvent.java */
/* loaded from: classes2.dex */
final class l extends AdapterViewItemLongClickEvent {
    private final AdapterView<?> a;
    private final View b;
    private final int c;
    private final long d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public l(AdapterView<?> adapterView, View view, int i, long j) {
        if (adapterView != null) {
            this.a = adapterView;
            if (view != null) {
                this.b = view;
                this.c = i;
                this.d = j;
                return;
            }
            throw new NullPointerException("Null clickedView");
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    @NonNull
    public AdapterView<?> view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    @NonNull
    public View clickedView() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    public int position() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemLongClickEvent
    public long id() {
        return this.d;
    }

    public String toString() {
        return "AdapterViewItemLongClickEvent{view=" + this.a + ", clickedView=" + this.b + ", position=" + this.c + ", id=" + this.d + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemLongClickEvent)) {
            return false;
        }
        AdapterViewItemLongClickEvent adapterViewItemLongClickEvent = (AdapterViewItemLongClickEvent) obj;
        return this.a.equals(adapterViewItemLongClickEvent.view()) && this.b.equals(adapterViewItemLongClickEvent.clickedView()) && this.c == adapterViewItemLongClickEvent.position() && this.d == adapterViewItemLongClickEvent.id();
    }

    public int hashCode() {
        long j = this.d;
        return ((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode()) * 1000003) ^ this.c) * 1000003) ^ ((int) (j ^ (j >>> 32)));
    }
}
