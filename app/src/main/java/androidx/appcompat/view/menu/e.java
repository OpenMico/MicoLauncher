package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.core.internal.view.SupportSubMenu;

/* compiled from: SubMenuWrapperICS.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
class e extends MenuWrapperICS implements SubMenu {
    private final SupportSubMenu b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Context context, SupportSubMenu supportSubMenu) {
        super(context, supportSubMenu);
        this.b = supportSubMenu;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(int i) {
        this.b.setHeaderTitle(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderTitle(CharSequence charSequence) {
        this.b.setHeaderTitle(charSequence);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(int i) {
        this.b.setHeaderIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderIcon(Drawable drawable) {
        this.b.setHeaderIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setHeaderView(View view) {
        this.b.setHeaderView(view);
        return this;
    }

    @Override // android.view.SubMenu
    public void clearHeader() {
        this.b.clearHeader();
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(int i) {
        this.b.setIcon(i);
        return this;
    }

    @Override // android.view.SubMenu
    public SubMenu setIcon(Drawable drawable) {
        this.b.setIcon(drawable);
        return this;
    }

    @Override // android.view.SubMenu
    public MenuItem getItem() {
        return a(this.b.getItem());
    }
}
