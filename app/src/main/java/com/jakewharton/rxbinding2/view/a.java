package com.jakewharton.rxbinding2.view;

import android.view.MenuItem;
import androidx.annotation.NonNull;

/* compiled from: AutoValue_MenuItemActionViewCollapseEvent.java */
/* loaded from: classes2.dex */
final class a extends MenuItemActionViewCollapseEvent {
    private final MenuItem a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(MenuItem menuItem) {
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
        return "MenuItemActionViewCollapseEvent{menuItem=" + this.a + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MenuItemActionViewCollapseEvent) {
            return this.a.equals(((MenuItemActionViewCollapseEvent) obj).menuItem());
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ 1000003;
    }
}
