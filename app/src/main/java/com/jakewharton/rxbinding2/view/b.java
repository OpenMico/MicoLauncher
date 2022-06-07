package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_MenuItemActionViewExpandEvent.java */
/* loaded from: classes2.dex */
final class b extends MenuItemActionViewExpandEvent {
    private final MenuItem a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(MenuItem menuItem) {
        if (menuItem != null) {
            this.a = menuItem;
            return;
        }
        throw new NullPointerException("Null menuItem");
    }

    @Override // com.jakewharton.rxbinding2.view.MenuItemActionViewEvent
    @NonNull
    public MenuItem menuItem() {
        return this.a;
    }

    public String toString() {
        return "MenuItemActionViewExpandEvent{menuItem=" + this.a + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MenuItemActionViewExpandEvent) {
            return this.a.equals(((MenuItemActionViewExpandEvent) obj).menuItem());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1000003;
    }
}
