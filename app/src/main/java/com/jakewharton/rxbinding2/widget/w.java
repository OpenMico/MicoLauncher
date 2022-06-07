package com.jakewharton.rxbinding2.widget;

import android.widget.TextView;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_TextViewTextChangeEvent.java */
/* loaded from: classes2.dex */
final class w extends TextViewTextChangeEvent {
    private final TextView a;
    private final CharSequence b;
    private final int c;
    private final int d;
    private final int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public w(TextView textView, CharSequence charSequence, int i, int i2, int i3) {
        if (textView != null) {
            this.a = textView;
            if (charSequence != null) {
                this.b = charSequence;
                this.c = i;
                this.d = i2;
                this.e = i3;
                return;
            }
            throw new NullPointerException("Null text");
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
    @NonNull
    public TextView view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
    @NonNull
    public CharSequence text() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
    public int start() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
    public int before() {
        return this.d;
    }

    @Override // com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
    public int count() {
        return this.e;
    }

    public String toString() {
        return "TextViewTextChangeEvent{view=" + this.a + ", text=" + ((Object) this.b) + ", start=" + this.c + ", before=" + this.d + ", count=" + this.e + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TextViewTextChangeEvent)) {
            return false;
        }
        TextViewTextChangeEvent textViewTextChangeEvent = (TextViewTextChangeEvent) obj;
        return this.a.equals(textViewTextChangeEvent.view()) && this.b.equals(textViewTextChangeEvent.text()) && this.c == textViewTextChangeEvent.start() && this.d == textViewTextChangeEvent.before() && this.e == textViewTextChangeEvent.count();
    }

    public int hashCode() {
        return ((((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode()) * 1000003) ^ this.c) * 1000003) ^ this.d) * 1000003) ^ this.e;
    }
}
