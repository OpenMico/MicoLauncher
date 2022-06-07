package com.jakewharton.rxbinding4.view;

import android.view.MenuItem;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MenuItemActionViewEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/jakewharton/rxbinding4/view/MenuItemActionViewCollapseEvent;", "Lcom/jakewharton/rxbinding4/view/MenuItemActionViewEvent;", "menuItem", "Landroid/view/MenuItem;", "(Landroid/view/MenuItem;)V", "getMenuItem", "()Landroid/view/MenuItem;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public final class MenuItemActionViewCollapseEvent extends MenuItemActionViewEvent {
    @NotNull
    private final MenuItem a;

    public static /* synthetic */ MenuItemActionViewCollapseEvent copy$default(MenuItemActionViewCollapseEvent menuItemActionViewCollapseEvent, MenuItem menuItem, int i, Object obj) {
        if ((i & 1) != 0) {
            menuItem = menuItemActionViewCollapseEvent.getMenuItem();
        }
        return menuItemActionViewCollapseEvent.copy(menuItem);
    }

    @NotNull
    public final MenuItem component1() {
        return getMenuItem();
    }

    @NotNull
    public final MenuItemActionViewCollapseEvent copy(@NotNull MenuItem menuItem) {
        Intrinsics.checkParameterIsNotNull(menuItem, "menuItem");
        return new MenuItemActionViewCollapseEvent(menuItem);
    }

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            return (obj instanceof MenuItemActionViewCollapseEvent) && Intrinsics.areEqual(getMenuItem(), ((MenuItemActionViewCollapseEvent) obj).getMenuItem());
        }
        return true;
    }

    public int hashCode() {
        MenuItem menuItem = getMenuItem();
        if (menuItem != null) {
            return menuItem.hashCode();
        }
        return 0;
    }

    @NotNull
    public String toString() {
        return "MenuItemActionViewCollapseEvent(menuItem=" + getMenuItem() + ")";
    }

    @Override // com.jakewharton.rxbinding4.view.MenuItemActionViewEvent
    @NotNull
    public MenuItem getMenuItem() {
        return this.a;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MenuItemActionViewCollapseEvent(@NotNull MenuItem menuItem) {
        super(null);
        Intrinsics.checkParameterIsNotNull(menuItem, "menuItem");
        this.a = menuItem;
    }
}
