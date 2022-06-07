package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_AdapterViewItemClickEvent.java */
/* loaded from: classes2.dex */
final class k extends AdapterViewItemClickEvent {
    private final AdapterView<?> a;
    private final View b;
    private final int c;
    private final long d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(AdapterView<?> adapterView, View view, int i, long j) {
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

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    @NonNull
    public AdapterView<?> view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    @NonNull
    public View clickedView() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    public int position() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemClickEvent
    public long id() {
        return this.d;
    }

    public String toString() {
        return "AdapterViewItemClickEvent{view=" + this.a + ", clickedView=" + this.b + ", position=" + this.c + ", id=" + this.d + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemClickEvent)) {
            return false;
        }
        AdapterViewItemClickEvent adapterViewItemClickEvent = (AdapterViewItemClickEvent) obj;
        return this.a.equals(adapterViewItemClickEvent.view()) && this.b.equals(adapterViewItemClickEvent.clickedView()) && this.c == adapterViewItemClickEvent.position() && this.d == adapterViewItemClickEvent.id();
    }

    public int hashCode() {
        long j = this.d;
        return ((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode()) * 1000003) ^ this.c) * 1000003) ^ ((int) (j ^ (j >>> 32)));
    }
}
