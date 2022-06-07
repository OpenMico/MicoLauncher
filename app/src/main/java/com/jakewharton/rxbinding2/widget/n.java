package com.jakewharton.rxbinding2.widget;

import android.widget.AdapterView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_AdapterViewNothingSelectionEvent.java */
/* loaded from: classes2.dex */
final class n extends AdapterViewNothingSelectionEvent {
    private final AdapterView<?> a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(AdapterView<?> adapterView) {
        if (adapterView != null) {
            this.a = adapterView;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.AdapterViewSelectionEvent
    @NonNull
    public AdapterView<?> view() {
        return this.a;
    }

    public String toString() {
        return "AdapterViewNothingSelectionEvent{view=" + this.a + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AdapterViewNothingSelectionEvent) {
            return this.a.equals(((AdapterViewNothingSelectionEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1000003;
    }
}
