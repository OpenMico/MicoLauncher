package androidx.appcompat.view.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewConfigurationCompat;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class MenuBuilder implements SupportMenu {
    private static final int[] d = {1, 4, 5, 3, 2, 0};
    private boolean A;
    CharSequence a;
    Drawable b;
    View c;
    private final Context e;
    private final Resources f;
    private boolean g;
    private boolean h;
    private Callback i;
    private ContextMenu.ContextMenuInfo q;
    private MenuItemImpl y;
    private int p = 0;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v = false;
    private ArrayList<MenuItemImpl> w = new ArrayList<>();
    private CopyOnWriteArrayList<WeakReference<MenuPresenter>> x = new CopyOnWriteArrayList<>();
    private boolean z = false;
    private ArrayList<MenuItemImpl> j = new ArrayList<>();
    private ArrayList<MenuItemImpl> k = new ArrayList<>();
    private boolean l = true;
    private ArrayList<MenuItemImpl> m = new ArrayList<>();
    private ArrayList<MenuItemImpl> n = new ArrayList<>();
    private boolean o = true;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public interface Callback {
        boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem);

        void onMenuModeChange(@NonNull MenuBuilder menuBuilder);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public interface ItemInvoker {
        boolean invokeItem(MenuItemImpl menuItemImpl);
    }

    public String getActionViewStatesKey() {
        return "android:menu:actionviewstates";
    }

    public MenuBuilder getRootMenu() {
        return this;
    }

    public MenuBuilder(Context context) {
        this.e = context;
        this.f = context.getResources();
        b(true);
    }

    public MenuBuilder setDefaultShowAsAction(int i) {
        this.p = i;
        return this;
    }

    public void addMenuPresenter(MenuPresenter menuPresenter) {
        addMenuPresenter(menuPresenter, this.e);
    }

    public void addMenuPresenter(MenuPresenter menuPresenter, Context context) {
        this.x.add(new WeakReference<>(menuPresenter));
        menuPresenter.initForMenu(context, this);
        this.o = true;
    }

    public void removeMenuPresenter(MenuPresenter menuPresenter) {
        Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<MenuPresenter> next = it.next();
            MenuPresenter menuPresenter2 = next.get();
            if (menuPresenter2 == null || menuPresenter2 == menuPresenter) {
                this.x.remove(next);
            }
        }
    }

    private void a(boolean z) {
        if (!this.x.isEmpty()) {
            stopDispatchingItemsChanged();
            Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<MenuPresenter> next = it.next();
                MenuPresenter menuPresenter = next.get();
                if (menuPresenter == null) {
                    this.x.remove(next);
                } else {
                    menuPresenter.updateMenuView(z);
                }
            }
            startDispatchingItemsChanged();
        }
    }

    private boolean a(SubMenuBuilder subMenuBuilder, MenuPresenter menuPresenter) {
        boolean z = false;
        if (this.x.isEmpty()) {
            return false;
        }
        if (menuPresenter != null) {
            z = menuPresenter.onSubMenuSelected(subMenuBuilder);
        }
        Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<MenuPresenter> next = it.next();
            MenuPresenter menuPresenter2 = next.get();
            if (menuPresenter2 == null) {
                this.x.remove(next);
            } else if (!z) {
                z = menuPresenter2.onSubMenuSelected(subMenuBuilder);
            }
        }
        return z;
    }

    private void a(Bundle bundle) {
        Parcelable onSaveInstanceState;
        if (!this.x.isEmpty()) {
            SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
            Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<MenuPresenter> next = it.next();
                MenuPresenter menuPresenter = next.get();
                if (menuPresenter == null) {
                    this.x.remove(next);
                } else {
                    int id = menuPresenter.getId();
                    if (id > 0 && (onSaveInstanceState = menuPresenter.onSaveInstanceState()) != null) {
                        sparseArray.put(id, onSaveInstanceState);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:presenters", sparseArray);
        }
    }

    private void b(Bundle bundle) {
        Parcelable parcelable;
        SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:presenters");
        if (sparseParcelableArray != null && !this.x.isEmpty()) {
            Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<MenuPresenter> next = it.next();
                MenuPresenter menuPresenter = next.get();
                if (menuPresenter == null) {
                    this.x.remove(next);
                } else {
                    int id = menuPresenter.getId();
                    if (id > 0 && (parcelable = (Parcelable) sparseParcelableArray.get(id)) != null) {
                        menuPresenter.onRestoreInstanceState(parcelable);
                    }
                }
            }
        }
    }

    public void savePresenterStates(Bundle bundle) {
        a(bundle);
    }

    public void restorePresenterStates(Bundle bundle) {
        b(bundle);
    }

    public void saveActionViewStates(Bundle bundle) {
        int size = size();
        SparseArray<? extends Parcelable> sparseArray = null;
        for (int i = 0; i < size; i++) {
            MenuItem item = getItem(i);
            View actionView = item.getActionView();
            if (!(actionView == null || actionView.getId() == -1)) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray<>();
                }
                actionView.saveHierarchyState(sparseArray);
                if (item.isActionViewExpanded()) {
                    bundle.putInt("android:menu:expandedactionview", item.getItemId());
                }
            }
            if (item.hasSubMenu()) {
                ((SubMenuBuilder) item.getSubMenu()).saveActionViewStates(bundle);
            }
        }
        if (sparseArray != null) {
            bundle.putSparseParcelableArray(getActionViewStatesKey(), sparseArray);
        }
    }

    public void restoreActionViewStates(Bundle bundle) {
        MenuItem findItem;
        if (bundle != null) {
            SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(getActionViewStatesKey());
            int size = size();
            for (int i = 0; i < size; i++) {
                MenuItem item = getItem(i);
                View actionView = item.getActionView();
                if (!(actionView == null || actionView.getId() == -1)) {
                    actionView.restoreHierarchyState(sparseParcelableArray);
                }
                if (item.hasSubMenu()) {
                    ((SubMenuBuilder) item.getSubMenu()).restoreActionViewStates(bundle);
                }
            }
            int i2 = bundle.getInt("android:menu:expandedactionview");
            if (i2 > 0 && (findItem = findItem(i2)) != null) {
                findItem.expandActionView();
            }
        }
    }

    public void setCallback(Callback callback) {
        this.i = callback;
    }

    public MenuItem addInternal(int i, int i2, int i3, CharSequence charSequence) {
        int a = a(i3);
        MenuItemImpl a2 = a(i, i2, i3, a, charSequence, this.p);
        ContextMenu.ContextMenuInfo contextMenuInfo = this.q;
        if (contextMenuInfo != null) {
            a2.a(contextMenuInfo);
        }
        ArrayList<MenuItemImpl> arrayList = this.j;
        arrayList.add(a(arrayList, a), a2);
        onItemsChanged(true);
        return a2;
    }

    private MenuItemImpl a(int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        return new MenuItemImpl(this, i, i2, i3, i4, charSequence, i5);
    }

    @Override // android.view.Menu
    public MenuItem add(CharSequence charSequence) {
        return addInternal(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(int i) {
        return addInternal(0, 0, 0, this.f.getString(i));
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, CharSequence charSequence) {
        return addInternal(i, i2, i3, charSequence);
    }

    @Override // android.view.Menu
    public MenuItem add(int i, int i2, int i3, int i4) {
        return addInternal(i, i2, i3, this.f.getString(i4));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(CharSequence charSequence) {
        return addSubMenu(0, 0, 0, charSequence);
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i) {
        return addSubMenu(0, 0, 0, this.f.getString(i));
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, CharSequence charSequence) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) addInternal(i, i2, i3, charSequence);
        SubMenuBuilder subMenuBuilder = new SubMenuBuilder(this.e, this, menuItemImpl);
        menuItemImpl.setSubMenu(subMenuBuilder);
        return subMenuBuilder;
    }

    @Override // android.view.Menu
    public SubMenu addSubMenu(int i, int i2, int i3, int i4) {
        return addSubMenu(i, i2, i3, this.f.getString(i4));
    }

    @Override // androidx.core.internal.view.SupportMenu, android.view.Menu
    public void setGroupDividerEnabled(boolean z) {
        this.z = z;
    }

    public boolean isGroupDividerEnabled() {
        return this.z;
    }

    @Override // android.view.Menu
    public int addIntentOptions(int i, int i2, int i3, ComponentName componentName, Intent[] intentArr, Intent intent, int i4, MenuItem[] menuItemArr) {
        PackageManager packageManager = this.e.getPackageManager();
        List<ResolveInfo> queryIntentActivityOptions = packageManager.queryIntentActivityOptions(componentName, intentArr, intent, 0);
        int size = queryIntentActivityOptions != null ? queryIntentActivityOptions.size() : 0;
        if ((i4 & 1) == 0) {
            removeGroup(i);
        }
        for (int i5 = 0; i5 < size; i5++) {
            ResolveInfo resolveInfo = queryIntentActivityOptions.get(i5);
            Intent intent2 = new Intent(resolveInfo.specificIndex < 0 ? intent : intentArr[resolveInfo.specificIndex]);
            intent2.setComponent(new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name));
            MenuItem intent3 = add(i, i2, i3, resolveInfo.loadLabel(packageManager)).setIcon(resolveInfo.loadIcon(packageManager)).setIntent(intent2);
            if (menuItemArr != null && resolveInfo.specificIndex >= 0) {
                menuItemArr[resolveInfo.specificIndex] = intent3;
            }
        }
        return size;
    }

    @Override // android.view.Menu
    public void removeItem(int i) {
        a(findItemIndex(i), true);
    }

    @Override // android.view.Menu
    public void removeGroup(int i) {
        int findGroupIndex = findGroupIndex(i);
        if (findGroupIndex >= 0) {
            int size = this.j.size() - findGroupIndex;
            int i2 = 0;
            while (true) {
                i2++;
                if (i2 >= size || this.j.get(findGroupIndex).getGroupId() != i) {
                    break;
                }
                a(findGroupIndex, false);
            }
            onItemsChanged(true);
        }
    }

    private void a(int i, boolean z) {
        if (i >= 0 && i < this.j.size()) {
            this.j.remove(i);
            if (z) {
                onItemsChanged(true);
            }
        }
    }

    public void removeItemAt(int i) {
        a(i, true);
    }

    public void clearAll() {
        this.r = true;
        clear();
        clearHeader();
        this.x.clear();
        this.r = false;
        this.s = false;
        this.t = false;
        onItemsChanged(true);
    }

    @Override // android.view.Menu
    public void clear() {
        MenuItemImpl menuItemImpl = this.y;
        if (menuItemImpl != null) {
            collapseItemActionView(menuItemImpl);
        }
        this.j.clear();
        onItemsChanged(true);
    }

    public void a(MenuItem menuItem) {
        int groupId = menuItem.getGroupId();
        int size = this.j.size();
        stopDispatchingItemsChanged();
        for (int i = 0; i < size; i++) {
            MenuItemImpl menuItemImpl = this.j.get(i);
            if (menuItemImpl.getGroupId() == groupId && menuItemImpl.isExclusiveCheckable() && menuItemImpl.isCheckable()) {
                menuItemImpl.a(menuItemImpl == menuItem);
            }
        }
        startDispatchingItemsChanged();
    }

    @Override // android.view.Menu
    public void setGroupCheckable(int i, boolean z, boolean z2) {
        int size = this.j.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.j.get(i2);
            if (menuItemImpl.getGroupId() == i) {
                menuItemImpl.setExclusiveCheckable(z2);
                menuItemImpl.setCheckable(z);
            }
        }
    }

    @Override // android.view.Menu
    public void setGroupVisible(int i, boolean z) {
        int size = this.j.size();
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.j.get(i2);
            if (menuItemImpl.getGroupId() == i && menuItemImpl.b(z)) {
                z2 = true;
            }
        }
        if (z2) {
            onItemsChanged(true);
        }
    }

    @Override // android.view.Menu
    public void setGroupEnabled(int i, boolean z) {
        int size = this.j.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.j.get(i2);
            if (menuItemImpl.getGroupId() == i) {
                menuItemImpl.setEnabled(z);
            }
        }
    }

    @Override // android.view.Menu
    public boolean hasVisibleItems() {
        if (this.A) {
            return true;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.j.get(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.Menu
    public MenuItem findItem(int i) {
        MenuItem findItem;
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = this.j.get(i2);
            if (menuItemImpl.getItemId() == i) {
                return menuItemImpl;
            }
            if (menuItemImpl.hasSubMenu() && (findItem = menuItemImpl.getSubMenu().findItem(i)) != null) {
                return findItem;
            }
        }
        return null;
    }

    public int findItemIndex(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.j.get(i2).getItemId() == i) {
                return i2;
            }
        }
        return -1;
    }

    public int findGroupIndex(int i) {
        return findGroupIndex(i, 0);
    }

    public int findGroupIndex(int i, int i2) {
        int size = size();
        if (i2 < 0) {
            i2 = 0;
        }
        while (i2 < size) {
            if (this.j.get(i2).getGroupId() == i) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Override // android.view.Menu
    public int size() {
        return this.j.size();
    }

    @Override // android.view.Menu
    public MenuItem getItem(int i) {
        return this.j.get(i);
    }

    @Override // android.view.Menu
    public boolean isShortcutKey(int i, KeyEvent keyEvent) {
        return a(i, keyEvent) != null;
    }

    @Override // android.view.Menu
    public void setQwertyMode(boolean z) {
        this.g = z;
        onItemsChanged(false);
    }

    private static int a(int i) {
        int i2 = ((-65536) & i) >> 16;
        if (i2 >= 0) {
            int[] iArr = d;
            if (i2 < iArr.length) {
                return (i & 65535) | (iArr[i2] << 16);
            }
        }
        throw new IllegalArgumentException("order does not contain a valid category.");
    }

    public boolean isQwertyMode() {
        return this.g;
    }

    public void setShortcutsVisible(boolean z) {
        if (this.h != z) {
            b(z);
            onItemsChanged(false);
        }
    }

    private void b(boolean z) {
        boolean z2 = true;
        if (!z || this.f.getConfiguration().keyboard == 1 || !ViewConfigurationCompat.shouldShowMenuShortcutsWhenKeyboardPresent(ViewConfiguration.get(this.e), this.e)) {
            z2 = false;
        }
        this.h = z2;
    }

    public boolean isShortcutsVisible() {
        return this.h;
    }

    Resources a() {
        return this.f;
    }

    public Context getContext() {
        return this.e;
    }

    public boolean a(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        Callback callback = this.i;
        return callback != null && callback.onMenuItemSelected(menuBuilder, menuItem);
    }

    public void changeMenuMode() {
        Callback callback = this.i;
        if (callback != null) {
            callback.onMenuModeChange(this);
        }
    }

    private static int a(ArrayList<MenuItemImpl> arrayList, int i) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).getOrdering() <= i) {
                return size + 1;
            }
        }
        return 0;
    }

    @Override // android.view.Menu
    public boolean performShortcut(int i, KeyEvent keyEvent, int i2) {
        MenuItemImpl a = a(i, keyEvent);
        boolean performItemAction = a != null ? performItemAction(a, i2) : false;
        if ((i2 & 2) != 0) {
            close(true);
        }
        return performItemAction;
    }

    void a(List<MenuItemImpl> list, int i, KeyEvent keyEvent) {
        boolean isQwertyMode = isQwertyMode();
        int modifiers = keyEvent.getModifiers();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        if (keyEvent.getKeyData(keyData) || i == 67) {
            int size = this.j.size();
            for (int i2 = 0; i2 < size; i2++) {
                MenuItemImpl menuItemImpl = this.j.get(i2);
                if (menuItemImpl.hasSubMenu()) {
                    ((MenuBuilder) menuItemImpl.getSubMenu()).a(list, i, keyEvent);
                }
                char alphabeticShortcut = isQwertyMode ? menuItemImpl.getAlphabeticShortcut() : menuItemImpl.getNumericShortcut();
                if (((modifiers & SupportMenu.SUPPORTED_MODIFIERS_MASK) == ((isQwertyMode ? menuItemImpl.getAlphabeticModifiers() : menuItemImpl.getNumericModifiers()) & SupportMenu.SUPPORTED_MODIFIERS_MASK)) && alphabeticShortcut != 0 && ((alphabeticShortcut == keyData.meta[0] || alphabeticShortcut == keyData.meta[2] || (isQwertyMode && alphabeticShortcut == '\b' && i == 67)) && menuItemImpl.isEnabled())) {
                    list.add(menuItemImpl);
                }
            }
        }
    }

    MenuItemImpl a(int i, KeyEvent keyEvent) {
        char c;
        ArrayList<MenuItemImpl> arrayList = this.w;
        arrayList.clear();
        a(arrayList, i, keyEvent);
        if (arrayList.isEmpty()) {
            return null;
        }
        int metaState = keyEvent.getMetaState();
        KeyCharacterMap.KeyData keyData = new KeyCharacterMap.KeyData();
        keyEvent.getKeyData(keyData);
        int size = arrayList.size();
        if (size == 1) {
            return arrayList.get(0);
        }
        boolean isQwertyMode = isQwertyMode();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItemImpl menuItemImpl = arrayList.get(i2);
            if (isQwertyMode) {
                c = menuItemImpl.getAlphabeticShortcut();
            } else {
                c = menuItemImpl.getNumericShortcut();
            }
            if ((c == keyData.meta[0] && (metaState & 2) == 0) || ((c == keyData.meta[2] && (metaState & 2) != 0) || (isQwertyMode && c == '\b' && i == 67))) {
                return menuItemImpl;
            }
        }
        return null;
    }

    @Override // android.view.Menu
    public boolean performIdentifierAction(int i, int i2) {
        return performItemAction(findItem(i), i2);
    }

    public boolean performItemAction(MenuItem menuItem, int i) {
        return performItemAction(menuItem, null, i);
    }

    public boolean performItemAction(MenuItem menuItem, MenuPresenter menuPresenter, int i) {
        MenuItemImpl menuItemImpl = (MenuItemImpl) menuItem;
        if (menuItemImpl == null || !menuItemImpl.isEnabled()) {
            return false;
        }
        boolean invoke = menuItemImpl.invoke();
        ActionProvider supportActionProvider = menuItemImpl.getSupportActionProvider();
        boolean z = supportActionProvider != null && supportActionProvider.hasSubMenu();
        if (menuItemImpl.hasCollapsibleActionView()) {
            invoke |= menuItemImpl.expandActionView();
            if (invoke) {
                close(true);
            }
        } else if (menuItemImpl.hasSubMenu() || z) {
            if ((i & 4) == 0) {
                close(false);
            }
            if (!menuItemImpl.hasSubMenu()) {
                menuItemImpl.setSubMenu(new SubMenuBuilder(getContext(), this, menuItemImpl));
            }
            SubMenuBuilder subMenuBuilder = (SubMenuBuilder) menuItemImpl.getSubMenu();
            if (z) {
                supportActionProvider.onPrepareSubMenu(subMenuBuilder);
            }
            invoke |= a(subMenuBuilder, menuPresenter);
            if (!invoke) {
                close(true);
            }
        } else if ((i & 1) == 0) {
            close(true);
        }
        return invoke;
    }

    public final void close(boolean z) {
        if (!this.v) {
            this.v = true;
            Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
            while (it.hasNext()) {
                WeakReference<MenuPresenter> next = it.next();
                MenuPresenter menuPresenter = next.get();
                if (menuPresenter == null) {
                    this.x.remove(next);
                } else {
                    menuPresenter.onCloseMenu(this, z);
                }
            }
            this.v = false;
        }
    }

    @Override // android.view.Menu
    public void close() {
        close(true);
    }

    public void onItemsChanged(boolean z) {
        if (!this.r) {
            if (z) {
                this.l = true;
                this.o = true;
            }
            a(z);
            return;
        }
        this.s = true;
        if (z) {
            this.t = true;
        }
    }

    public void stopDispatchingItemsChanged() {
        if (!this.r) {
            this.r = true;
            this.s = false;
            this.t = false;
        }
    }

    public void startDispatchingItemsChanged() {
        this.r = false;
        if (this.s) {
            this.s = false;
            onItemsChanged(this.t);
        }
    }

    public void a(MenuItemImpl menuItemImpl) {
        this.l = true;
        onItemsChanged(true);
    }

    public void b(MenuItemImpl menuItemImpl) {
        this.o = true;
        onItemsChanged(true);
    }

    @NonNull
    public ArrayList<MenuItemImpl> getVisibleItems() {
        if (!this.l) {
            return this.k;
        }
        this.k.clear();
        int size = this.j.size();
        for (int i = 0; i < size; i++) {
            MenuItemImpl menuItemImpl = this.j.get(i);
            if (menuItemImpl.isVisible()) {
                this.k.add(menuItemImpl);
            }
        }
        this.l = false;
        this.o = true;
        return this.k;
    }

    public void flagActionItems() {
        ArrayList<MenuItemImpl> visibleItems = getVisibleItems();
        if (this.o) {
            Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
            boolean z = false;
            while (it.hasNext()) {
                WeakReference<MenuPresenter> next = it.next();
                MenuPresenter menuPresenter = next.get();
                if (menuPresenter == null) {
                    this.x.remove(next);
                } else {
                    z |= menuPresenter.flagActionItems();
                }
            }
            if (z) {
                this.m.clear();
                this.n.clear();
                int size = visibleItems.size();
                for (int i = 0; i < size; i++) {
                    MenuItemImpl menuItemImpl = visibleItems.get(i);
                    if (menuItemImpl.isActionButton()) {
                        this.m.add(menuItemImpl);
                    } else {
                        this.n.add(menuItemImpl);
                    }
                }
            } else {
                this.m.clear();
                this.n.clear();
                this.n.addAll(getVisibleItems());
            }
            this.o = false;
        }
    }

    public ArrayList<MenuItemImpl> getActionItems() {
        flagActionItems();
        return this.m;
    }

    public ArrayList<MenuItemImpl> getNonActionItems() {
        flagActionItems();
        return this.n;
    }

    public void clearHeader() {
        this.b = null;
        this.a = null;
        this.c = null;
        onItemsChanged(false);
    }

    private void a(int i, CharSequence charSequence, int i2, Drawable drawable, View view) {
        Resources a = a();
        if (view != null) {
            this.c = view;
            this.a = null;
            this.b = null;
        } else {
            if (i > 0) {
                this.a = a.getText(i);
            } else if (charSequence != null) {
                this.a = charSequence;
            }
            if (i2 > 0) {
                this.b = ContextCompat.getDrawable(getContext(), i2);
            } else if (drawable != null) {
                this.b = drawable;
            }
            this.c = null;
        }
        onItemsChanged(false);
    }

    public MenuBuilder setHeaderTitleInt(CharSequence charSequence) {
        a(0, charSequence, 0, null, null);
        return this;
    }

    public MenuBuilder setHeaderTitleInt(int i) {
        a(i, null, 0, null, null);
        return this;
    }

    public MenuBuilder setHeaderIconInt(Drawable drawable) {
        a(0, null, 0, drawable, null);
        return this;
    }

    public MenuBuilder setHeaderIconInt(int i) {
        a(0, null, i, null, null);
        return this;
    }

    public MenuBuilder setHeaderViewInt(View view) {
        a(0, null, 0, null, view);
        return this;
    }

    public CharSequence getHeaderTitle() {
        return this.a;
    }

    public Drawable getHeaderIcon() {
        return this.b;
    }

    public View getHeaderView() {
        return this.c;
    }

    public void setCurrentMenuInfo(ContextMenu.ContextMenuInfo contextMenuInfo) {
        this.q = contextMenuInfo;
    }

    public void setOptionalIconsVisible(boolean z) {
        this.u = z;
    }

    public boolean b() {
        return this.u;
    }

    public boolean expandItemActionView(MenuItemImpl menuItemImpl) {
        boolean z = false;
        if (this.x.isEmpty()) {
            return false;
        }
        stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<MenuPresenter> next = it.next();
            MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.x.remove(next);
            } else {
                z = menuPresenter.expandItemActionView(this, menuItemImpl);
                if (z) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z) {
            this.y = menuItemImpl;
        }
        return z;
    }

    public boolean collapseItemActionView(MenuItemImpl menuItemImpl) {
        boolean z = false;
        if (this.x.isEmpty() || this.y != menuItemImpl) {
            return false;
        }
        stopDispatchingItemsChanged();
        Iterator<WeakReference<MenuPresenter>> it = this.x.iterator();
        while (it.hasNext()) {
            WeakReference<MenuPresenter> next = it.next();
            MenuPresenter menuPresenter = next.get();
            if (menuPresenter == null) {
                this.x.remove(next);
            } else {
                z = menuPresenter.collapseItemActionView(this, menuItemImpl);
                if (z) {
                    break;
                }
            }
        }
        startDispatchingItemsChanged();
        if (z) {
            this.y = null;
        }
        return z;
    }

    public MenuItemImpl getExpandedItem() {
        return this.y;
    }

    public void setOverrideVisibleItems(boolean z) {
        this.A = z;
    }
}
