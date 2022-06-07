package com.jakewharton.rxbinding2.view;

import android.view.View;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_ViewScrollChangeEvent.java */
/* loaded from: classes2.dex */
final class h extends ViewScrollChangeEvent {
    private final View a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(View view, int i, int i2, int i3, int i4) {
        if (view != null) {
            this.a = view;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
            return;
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    @NonNull
    public View view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int scrollX() {
        return this.b;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int scrollY() {
        return this.c;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int oldScrollX() {
        return this.d;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewScrollChangeEvent
    public int oldScrollY() {
        return this.e;
    }

    public String toString() {
        return "ViewScrollChangeEvent{view=" + this.a + ", scrollX=" + this.b + ", scrollY=" + this.c + ", oldScrollX=" + this.d + ", oldScrollY=" + this.e + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewScrollChangeEvent)) {
            return false;
        }
        ViewScrollChangeEvent viewScrollChangeEvent = (ViewScrollChangeEvent) obj;
        return this.a.equals(viewScrollChangeEvent.view()) && this.b == viewScrollChangeEvent.scrollX() && this.c == viewScrollChangeEvent.scrollY() && this.d == viewScrollChangeEvent.oldScrollX() && this.e == viewScrollChangeEvent.oldScrollY();
    }

    public int hashCode() {
        return ((((((((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b) * 1000003) ^ this.c) * 1000003) ^ this.d) * 1000003) ^ this.e;
    }
}
