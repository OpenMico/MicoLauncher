package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.passport.ui.internal.MenuBuilder;

/* loaded from: classes4.dex */
public class SubMenuBuilder extends MenuBuilder implements SubMenu {
    private MenuItemImpl mItem;
    private MenuBuilder mParentMenu;

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder, android.view.SubMenu
    public void clearHeader() {
    }

    public SubMenuBuilder(Context context, MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        super(context);
        this.mParentMenu = menuBuilder;
        this.mItem = menuItemImpl;
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder, android.view.Menu
    public void setQwertyMode(boolean z) {
        this.mParentMenu.setQwertyMode(z);
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public boolean isQwertyMode() {
        return this.mParentMenu.isQwertyMode();
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public void setShortcutsVisible(boolean z) {
        this.mParentMenu.setShortcutsVisible(z);
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public boolean isShortcutsVisible() {
        return this.mParentMenu.isShortcutsVisible();
    }

    public Menu getParentMenu() {
        return this.mParentMenu;
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return this.mItem;
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public void setCallback(MenuBuilder.Callback callback) {
        this.mParentMenu.setCallback(callback);
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public MenuBuilder getRootMenu() {
        return this.mParentMenu;
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public boolean dispatchMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return super.dispatchMenuItemSelected(menuBuilder, menuItem) || this.mParentMenu.dispatchMenuItemSelected(menuBuilder, menuItem);
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        this.mItem.setIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        this.mItem.setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        super.setHeaderIconInt(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        super.setHeaderIconInt(getContext().getResources().getDrawable(i));
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        super.setHeaderTitleInt(charSequence);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        super.setHeaderTitleInt(getContext().getResources().getString(i));
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view) {
        super.setHeaderViewInt(view);
        return this;
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.expandItemActionView(menuItemImpl);
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        return this.mParentMenu.collapseItemActionView(menuItemImpl);
    }

    @Override // com.xiaomi.passport.ui.internal.MenuBuilder
    public String getActionViewStatesKey() {
        MenuItemImpl menuItemImpl = this.mItem;
        int itemId = menuItemImpl != null ? menuItemImpl.getItemId() : 0;
        if (itemId == 0) {
            return null;
        }
        return super.getActionViewStatesKey() + Constants.COLON_SEPARATOR + itemId;
    }
}
