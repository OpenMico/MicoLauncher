package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_ViewLayoutChangeEvent.java */
/* loaded from: classes2.dex */
final class g extends ViewLayoutChangeEvent {
    private final View a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    private final int i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (view != null) {
            this.a = view;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f = i5;
            this.g = i6;
            this.h = i7;
            this.i = i8;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    @NonNull
    public View view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int left() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int top() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int right() {
        return this.d;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int bottom() {
        return this.e;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldLeft() {
        return this.f;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldTop() {
        return this.g;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldRight() {
        return this.h;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewLayoutChangeEvent
    public int oldBottom() {
        return this.i;
    }

    public String toString() {
        return "ViewLayoutChangeEvent{view=" + this.a + ", left=" + this.b + ", top=" + this.c + ", right=" + this.d + ", bottom=" + this.e + ", oldLeft=" + this.f + ", oldTop=" + this.g + ", oldRight=" + this.h + ", oldBottom=" + this.i + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewLayoutChangeEvent)) {
            return false;
        }
        ViewLayoutChangeEvent viewLayoutChangeEvent = (ViewLayoutChangeEvent) obj;
        return this.a.equals(viewLayoutChangeEvent.view()) && this.b == viewLayoutChangeEvent.left() && this.c == viewLayoutChangeEvent.top() && this.d == viewLayoutChangeEvent.right() && this.e == viewLayoutChangeEvent.bottom() && this.f == viewLayoutChangeEvent.oldLeft() && this.g == viewLayoutChangeEvent.oldTop() && this.h == viewLayoutChangeEvent.oldRight() && this.i == viewLayoutChangeEvent.oldBottom();
    }

    public int hashCode() {
        return ((((((((((((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b) * 1000003) ^ this.c) * 1000003) ^ this.d) * 1000003) ^ this.e) * 1000003) ^ this.f) * 1000003) ^ this.g) * 1000003) ^ this.h) * 1000003) ^ this.i;
    }
}
