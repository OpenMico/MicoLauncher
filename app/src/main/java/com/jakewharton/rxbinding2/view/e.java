package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_ViewGroupHierarchyChildViewAddEvent.java */
/* loaded from: classes2.dex */
final class e extends ViewGroupHierarchyChildViewAddEvent {
    private final ViewGroup a;
    private final View b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(ViewGroup viewGroup, View view) {
        if (viewGroup != null) {
            this.a = viewGroup;
            if (view != null) {
                this.b = view;
                return;
            }
            throw new NullPointerException("Null child");
        }
        throw new NullPointerException("Null view");
    }

    @Override // com.jakewharton.rxbinding2.view.ViewGroupHierarchyChangeEvent
    @NonNull
    public ViewGroup view() {
        return this.a;
    }

    @Override // com.jakewharton.rxbinding2.view.ViewGroupHierarchyChangeEvent
    @NonNull
    public View child() {
        return this.b;
    }

    public String toString() {
        return "ViewGroupHierarchyChildViewAddEvent{view=" + this.a + ", child=" + this.b + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewGroupHierarchyChildViewAddEvent)) {
            return false;
        }
        ViewGroupHierarchyChildViewAddEvent viewGroupHierarchyChildViewAddEvent = (ViewGroupHierarchyChildViewAddEvent) obj;
        return this.a.equals(viewGroupHierarchyChildViewAddEvent.view()) && this.b.equals(viewGroupHierarchyChildViewAddEvent.child());
    }

    public int hashCode() {
        return ((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode();
    }
}
