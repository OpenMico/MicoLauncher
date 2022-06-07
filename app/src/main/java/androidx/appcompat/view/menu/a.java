package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;

/* compiled from: BaseMenuWrapper.java */
/* loaded from: classes.dex */
public abstract class a {
    final Context a;
    private SimpleArrayMap<SupportMenuItem, MenuItem> b;
    private SimpleArrayMap<SupportSubMenu, SubMenu> c;

    public a(Context context) {
        this.a = context;
    }

    final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.b == null) {
            this.b = new SimpleArrayMap<>();
        }
        MenuItem menuItem2 = this.b.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        MenuItemWrapperICS menuItemWrapperICS = new MenuItemWrapperICS(this.a, supportMenuItem);
        this.b.put(supportMenuItem, menuItemWrapperICS);
        return menuItemWrapperICS;
    }

    final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (this.c == null) {
            this.c = new SimpleArrayMap<>();
        }
        SubMenu subMenu2 = this.c.get(supportSubMenu);
        if (subMenu2 != null) {
            return subMenu2;
        }
        e eVar = new e(this.a, supportSubMenu);
        this.c.put(supportSubMenu, eVar);
        return eVar;
    }

    final void a() {
        SimpleArrayMap<SupportMenuItem, MenuItem> simpleArrayMap = this.b;
        if (simpleArrayMap != null) {
            simpleArrayMap.clear();
        }
        SimpleArrayMap<SupportSubMenu, SubMenu> simpleArrayMap2 = this.c;
        if (simpleArrayMap2 != null) {
            simpleArrayMap2.clear();
        }
    }

    final void a(int i) {
        if (this.b != null) {
            int i2 = 0;
            while (i2 < this.b.size()) {
                if (this.b.keyAt(i2).getGroupId() == i) {
                    this.b.removeAt(i2);
                    i2--;
                }
                i2++;
            }
        }
    }

    final void b(int i) {
        if (this.b != null) {
            for (int i2 = 0; i2 < this.b.size(); i2++) {
                if (this.b.keyAt(i2).getItemId() == i) {
                    this.b.removeAt(i2);
                    return;
                }
            }
        }
    }
}
