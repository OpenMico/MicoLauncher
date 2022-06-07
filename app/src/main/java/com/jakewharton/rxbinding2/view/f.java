package com.jakewharton.rxbinding2.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_ViewGroupHierarchyChildViewRemoveEvent.java */
/* loaded from: classes2.dex */
final class f extends ViewGroupHierarchyChildViewRemoveEvent {
    private final ViewGroup a;
    private final View b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(ViewGroup viewGroup, View view) {
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
        return "ViewGroupHierarchyChildViewRemoveEvent{view=" + this.a + ", child=" + this.b + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewGroupHierarchyChildViewRemoveEvent)) {
            return false;
        }
        ViewGroupHierarchyChildViewRemoveEvent viewGroupHierarchyChildViewRemoveEvent = (ViewGroupHierarchyChildViewRemoveEvent) obj;
        return this.a.equals(viewGroupHierarchyChildViewRemoveEvent.view()) && this.b.equals(viewGroupHierarchyChildViewRemoveEvent.child());
    }

    public int hashCode() {
        return ((this.a.hashCode() ^ 1000003) * 1000003) ^ this.b.hashCode();
    }
}
