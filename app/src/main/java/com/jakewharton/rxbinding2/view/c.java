package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_ViewAttachAttachedEvent.java */
/* loaded from: classes2.dex */
final class c extends ViewAttachAttachedEvent {
    private final View a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(View view) {
        if (view != null) {
            this.a = view;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.view.ViewAttachEvent
    @NonNull
    public View view() {
        return this.a;
    }

    public String toString() {
        return "ViewAttachAttachedEvent{view=" + this.a + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ViewAttachAttachedEvent) {
            return this.a.equals(((ViewAttachAttachedEvent) obj).view());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1000003;
    }
}
