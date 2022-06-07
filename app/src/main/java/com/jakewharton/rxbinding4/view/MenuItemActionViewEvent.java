package com.jakewharton.rxbinding4.view;

import android.view.MenuItem;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: MenuItemActionViewEvent.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lcom/jakewharton/rxbinding4/view/MenuItemActionViewEvent;", "", "()V", "menuItem", "Landroid/view/MenuItem;", "getMenuItem", "()Landroid/view/MenuItem;", "Lcom/jakewharton/rxbinding4/view/MenuItemActionViewCollapseEvent;", "Lcom/jakewharton/rxbinding4/view/MenuItemActionViewExpandEvent;", "rxbinding_release"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes2.dex */
public abstract class MenuItemActionViewEvent {
    @NotNull
    public abstract MenuItem getMenuItem();

    private MenuItemActionViewEvent() {
    }

    public /* synthetic */ MenuItemActionViewEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
