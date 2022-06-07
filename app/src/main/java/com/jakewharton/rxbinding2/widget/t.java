package com.jakewharton.rxbinding2.widget;

import android.text.Editable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: AutoValue_TextViewAfterTextChangeEvent.java */
/* loaded from: classes2.dex */
final class t extends TextViewAfterTextChangeEvent {
    private final TextView a;
    private final Editable b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public t(TextView textView, @Nullable Editable editable) {
        if (textView != null) {
            this.a = textView;
            this.b = editable;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
    @NonNull
    public TextView view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
    @Nullable
    public Editable editable() {
        return this.b;
    }

    public String toString() {
        return "TextViewAfterTextChangeEvent{view=" + this.a + ", editable=" + ((Object) this.b) + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextViewAfterTextChangeEvent)) {
            return false;
        }
        TextViewAfterTextChangeEvent textViewAfterTextChangeEvent = (TextViewAfterTextChangeEvent) obj;
        if (this.a.equals(textViewAfterTextChangeEvent.view())) {
            Editable editable = this.b;
            if (editable == null) {
                if (textViewAfterTextChangeEvent.editable() == null) {
                    return true;
                }
            } else if (editable.equals(textViewAfterTextChangeEvent.editable())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (this.a.hashCode() ^ 1000003) * 1000003;
        Editable editable = this.b;
        return hashCode ^ (editable == null ? 0 : editable.hashCode());
    }
}
