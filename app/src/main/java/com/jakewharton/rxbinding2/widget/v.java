package com.jakewharton.rxbinding2.widget;

import android.view.KeyEvent;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: AutoValue_TextViewEditorActionEvent.java */
/* loaded from: classes2.dex */
final class v extends TextViewEditorActionEvent {
    private final TextView a;
    private final int b;
    private final KeyEvent c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public v(TextView textView, int i, @Nullable KeyEvent keyEvent) {
        if (textView != null) {
            this.a = textView;
            this.b = i;
            this.c = keyEvent;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
    @NonNull
    public TextView view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
    public int actionId() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewEditorActionEvent
    @Nullable
    public KeyEvent keyEvent() {
        return this.c;
    }

    public String toString() {
        return "TextViewEditorActionEvent{view=" + this.a + ", actionId=" + this.b + ", keyEvent=" + this.c + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextViewEditorActionEvent)) {
            return false;
        }
        TextViewEditorActionEvent textViewEditorActionEvent = (TextViewEditorActionEvent) obj;
        if (this.a.equals(textViewEditorActionEvent.view()) && this.b == textViewEditorActionEvent.actionId()) {
            KeyEvent keyEvent = this.c;
            if (keyEvent == null) {
                if (textViewEditorActionEvent.keyEvent() == null) {
                    return true;
                }
            } else if (keyEvent.equals(textViewEditorActionEvent.keyEvent())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b) * 1000003;
        KeyEvent keyEvent = this.c;
        return hashCode ^ (keyEvent == null ? 0 : keyEvent.hashCode());
    }
}
