package com.jakewharton.rxbinding2.widget;

import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_AdapterViewItemSelectionEvent.java */
/* loaded from: classes2.dex */
final class m extends AdapterViewItemSelectionEvent {
    private final AdapterView<?> a;
    private final View b;
    private final int c;
    private final long d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(AdapterView<?> adapterView, View view, int i, long j) {
        if (adapterView != null) {
            this.a = adapterView;
            if (view != null) {
                this.b = view;
                this.c = i;
                this.d = j;
                return;
            }
            throw new NullPointerException("Null selectedView");
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewSelectionEvent
    @NonNull
    public AdapterView<?> view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemSelectionEvent
    @NonNull
    public View selectedView() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemSelectionEvent
    public int position() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewItemSelectionEvent
    public long id() {
        return this.d;
    }

    public String toString() {
        return "AdapterViewItemSelectionEvent{view=" + this.a + ", selectedView=" + this.b + ", position=" + this.c + ", id=" + this.d + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AdapterViewItemSelectionEvent)) {
            return false;
        }
        AdapterViewItemSelectionEvent adapterViewItemSelectionEvent = (AdapterViewItemSelectionEvent) obj;
        return this.a.equals(adapterViewItemSelectionEvent.view()) && this.b.equals(adapterViewItemSelectionEvent.selectedView()) && this.c == adapterViewItemSelectionEvent.position() && this.d == adapterViewItemSelectionEvent.id();
    }

    public int hashCode() {
        long j = this.d;
        return ((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode()) * 1000003) ^ this.c) * 1000003) ^ ((int) (j ^ (j >>> 32)));
    }
}
