package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Dimension;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.google.android.material.R;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class NavigationMenuPresenter implements MenuPresenter {
    LinearLayout a;
    MenuBuilder b;
    b c;
    LayoutInflater d;
    int e;
    boolean f;
    ColorStateList g;
    ColorStateList h;
    Drawable i;
    int j;
    int k;
    int l;
    boolean m;
    int o;
    private NavigationMenuView q;
    private MenuPresenter.Callback r;
    private int s;
    private int t;
    private int u;
    boolean n = true;
    private int v = -1;
    final View.OnClickListener p = new View.OnClickListener() { // from class: com.google.android.material.internal.NavigationMenuPresenter.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            boolean z = true;
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            MenuItemImpl itemData = ((NavigationMenuItemView) view).getItemData();
            boolean performItemAction = NavigationMenuPresenter.this.b.performItemAction(itemData, NavigationMenuPresenter.this, 0);
            if (itemData == null || !itemData.isCheckable() || !performItemAction) {
                z = false;
            } else {
                NavigationMenuPresenter.this.c.a(itemData);
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            if (z) {
                NavigationMenuPresenter.this.updateMenuView(false);
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface d {
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        this.d = LayoutInflater.from(context);
        this.b = menuBuilder;
        this.o = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.q == null) {
            this.q = (NavigationMenuView) this.d.inflate(R.layout.design_navigation_menu, viewGroup, false);
            NavigationMenuView navigationMenuView = this.q;
            navigationMenuView.setAccessibilityDelegateCompat(new g(navigationMenuView));
            if (this.c == null) {
                this.c = new b();
            }
            int i2 = this.v;
            if (i2 != -1) {
                this.q.setOverScrollMode(i2);
            }
            this.a = (LinearLayout) this.d.inflate(R.layout.design_navigation_item_header, (ViewGroup) this.q, false);
            this.q.setAdapter(this.c);
        }
        return this.q;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        b bVar = this.c;
        if (bVar != null) {
            bVar.a();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.r = callback;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuPresenter.Callback callback = this.r;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public int getId() {
        return this.s;
    }

    public void setId(int i2) {
        this.s = i2;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        if (this.q != null) {
            SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
            this.q.saveHierarchyState(sparseArray);
            bundle.putSparseParcelableArray(ListMenuPresenter.VIEWS_TAG, sparseArray);
        }
        b bVar = this.c;
        if (bVar != null) {
            bundle.putBundle("android:menu:adapter", bVar.c());
        }
        if (this.a != null) {
            SparseArray<? extends Parcelable> sparseArray2 = new SparseArray<>();
            this.a.saveHierarchyState(sparseArray2);
            bundle.putSparseParcelableArray("android:menu:header", sparseArray2);
        }
        return bundle;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray(ListMenuPresenter.VIEWS_TAG);
            if (sparseParcelableArray != null) {
                this.q.restoreHierarchyState(sparseParcelableArray);
            }
            Bundle bundle2 = bundle.getBundle("android:menu:adapter");
            if (bundle2 != null) {
                this.c.a(bundle2);
            }
            SparseArray sparseParcelableArray2 = bundle.getSparseParcelableArray("android:menu:header");
            if (sparseParcelableArray2 != null) {
                this.a.restoreHierarchyState(sparseParcelableArray2);
            }
        }
    }

    public void setCheckedItem(@NonNull MenuItemImpl menuItemImpl) {
        this.c.a(menuItemImpl);
    }

    @Nullable
    public MenuItemImpl getCheckedItem() {
        return this.c.b();
    }

    public View inflateHeaderView(@LayoutRes int i2) {
        View inflate = this.d.inflate(i2, (ViewGroup) this.a, false);
        addHeaderView(inflate);
        return inflate;
    }

    public void addHeaderView(@NonNull View view) {
        this.a.addView(view);
        NavigationMenuView navigationMenuView = this.q;
        navigationMenuView.setPadding(0, 0, 0, navigationMenuView.getPaddingBottom());
    }

    public void removeHeaderView(@NonNull View view) {
        this.a.removeView(view);
        if (this.a.getChildCount() == 0) {
            NavigationMenuView navigationMenuView = this.q;
            navigationMenuView.setPadding(0, this.u, 0, navigationMenuView.getPaddingBottom());
        }
    }

    public int getHeaderCount() {
        return this.a.getChildCount();
    }

    public View getHeaderView(int i2) {
        return this.a.getChildAt(i2);
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.h;
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.h = colorStateList;
        updateMenuView(false);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.g;
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.g = colorStateList;
        updateMenuView(false);
    }

    public void setItemTextAppearance(@StyleRes int i2) {
        this.e = i2;
        this.f = true;
        updateMenuView(false);
    }

    @Nullable
    public Drawable getItemBackground() {
        return this.i;
    }

    public void setItemBackground(@Nullable Drawable drawable) {
        this.i = drawable;
        updateMenuView(false);
    }

    public int getItemHorizontalPadding() {
        return this.j;
    }

    public void setItemHorizontalPadding(int i2) {
        this.j = i2;
        updateMenuView(false);
    }

    public int getItemIconPadding() {
        return this.k;
    }

    public void setItemIconPadding(int i2) {
        this.k = i2;
        updateMenuView(false);
    }

    public void setItemMaxLines(int i2) {
        this.t = i2;
        updateMenuView(false);
    }

    public int getItemMaxLines() {
        return this.t;
    }

    public void setItemIconSize(@Dimension int i2) {
        if (this.l != i2) {
            this.l = i2;
            this.m = true;
            updateMenuView(false);
        }
    }

    public void setUpdateSuspended(boolean z) {
        b bVar = this.c;
        if (bVar != null) {
            bVar.a(z);
        }
    }

    public void setBehindStatusBar(boolean z) {
        if (this.n != z) {
            this.n = z;
            a();
        }
    }

    public boolean isBehindStatusBar() {
        return this.n;
    }

    private void a() {
        int i2 = (this.a.getChildCount() != 0 || !this.n) ? 0 : this.u;
        NavigationMenuView navigationMenuView = this.q;
        navigationMenuView.setPadding(0, i2, 0, navigationMenuView.getPaddingBottom());
    }

    public void dispatchApplyWindowInsets(@NonNull WindowInsetsCompat windowInsetsCompat) {
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        if (this.u != systemWindowInsetTop) {
            this.u = systemWindowInsetTop;
            a();
        }
        NavigationMenuView navigationMenuView = this.q;
        navigationMenuView.setPadding(0, navigationMenuView.getPaddingTop(), 0, windowInsetsCompat.getSystemWindowInsetBottom());
        ViewCompat.dispatchApplyWindowInsets(this.a, windowInsetsCompat);
    }

    public void setOverScrollMode(int i2) {
        this.v = i2;
        NavigationMenuView navigationMenuView = this.q;
        if (navigationMenuView != null) {
            navigationMenuView.setOverScrollMode(i2);
        }
    }

    /* loaded from: classes2.dex */
    private static abstract class k extends RecyclerView.ViewHolder {
        public k(View view) {
            super(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class h extends k {
        public h(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, View.OnClickListener onClickListener) {
            super(layoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false));
            this.itemView.setOnClickListener(onClickListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class j extends k {
        public j(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class i extends k {
        public i(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup) {
            super(layoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a extends k {
        public a(View view) {
            super(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class b extends RecyclerView.Adapter<k> {
        private final ArrayList<d> b = new ArrayList<>();
        private MenuItemImpl c;
        private boolean d;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            return i;
        }

        b() {
            e();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.b.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            d dVar = this.b.get(i);
            if (dVar instanceof e) {
                return 2;
            }
            if (dVar instanceof c) {
                return 3;
            }
            if (dVar instanceof f) {
                return ((f) dVar).a().hasSubMenu() ? 1 : 0;
            }
            throw new RuntimeException("Unknown item type.");
        }

        @Nullable
        /* renamed from: a */
        public k onCreateViewHolder(ViewGroup viewGroup, int i) {
            switch (i) {
                case 0:
                    return new h(NavigationMenuPresenter.this.d, viewGroup, NavigationMenuPresenter.this.p);
                case 1:
                    return new j(NavigationMenuPresenter.this.d, viewGroup);
                case 2:
                    return new i(NavigationMenuPresenter.this.d, viewGroup);
                case 3:
                    return new a(NavigationMenuPresenter.this.a);
                default:
                    return null;
            }
        }

        /* renamed from: a */
        public void onBindViewHolder(@NonNull k kVar, int i) {
            switch (getItemViewType(i)) {
                case 0:
                    NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) kVar.itemView;
                    navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.h);
                    if (NavigationMenuPresenter.this.f) {
                        navigationMenuItemView.setTextAppearance(NavigationMenuPresenter.this.e);
                    }
                    if (NavigationMenuPresenter.this.g != null) {
                        navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.g);
                    }
                    ViewCompat.setBackground(navigationMenuItemView, NavigationMenuPresenter.this.i != null ? NavigationMenuPresenter.this.i.getConstantState().newDrawable() : null);
                    f fVar = (f) this.b.get(i);
                    navigationMenuItemView.setNeedsEmptyIcon(fVar.a);
                    navigationMenuItemView.setHorizontalPadding(NavigationMenuPresenter.this.j);
                    navigationMenuItemView.setIconPadding(NavigationMenuPresenter.this.k);
                    if (NavigationMenuPresenter.this.m) {
                        navigationMenuItemView.setIconSize(NavigationMenuPresenter.this.l);
                    }
                    navigationMenuItemView.setMaxLines(NavigationMenuPresenter.this.t);
                    navigationMenuItemView.initialize(fVar.a(), 0);
                    return;
                case 1:
                    ((TextView) kVar.itemView).setText(((f) this.b.get(i)).a().getTitle());
                    return;
                case 2:
                    e eVar = (e) this.b.get(i);
                    kVar.itemView.setPadding(0, eVar.a(), 0, eVar.b());
                    return;
                default:
                    return;
            }
        }

        /* renamed from: a */
        public void onViewRecycled(k kVar) {
            if (kVar instanceof h) {
                ((NavigationMenuItemView) kVar.itemView).recycle();
            }
        }

        public void a() {
            e();
            notifyDataSetChanged();
        }

        private void e() {
            if (!this.d) {
                boolean z = true;
                this.d = true;
                this.b.clear();
                this.b.add(new c());
                int i = -1;
                int size = NavigationMenuPresenter.this.b.getVisibleItems().size();
                int i2 = 0;
                boolean z2 = false;
                int i3 = 0;
                while (i2 < size) {
                    MenuItemImpl menuItemImpl = NavigationMenuPresenter.this.b.getVisibleItems().get(i2);
                    if (menuItemImpl.isChecked()) {
                        a(menuItemImpl);
                    }
                    if (menuItemImpl.isCheckable()) {
                        menuItemImpl.setExclusiveCheckable(false);
                    }
                    if (menuItemImpl.hasSubMenu()) {
                        SubMenu subMenu = menuItemImpl.getSubMenu();
                        if (subMenu.hasVisibleItems()) {
                            if (i2 != 0) {
                                this.b.add(new e(NavigationMenuPresenter.this.o, 0));
                            }
                            this.b.add(new f(menuItemImpl));
                            int size2 = this.b.size();
                            int size3 = subMenu.size();
                            int i4 = 0;
                            z = false;
                            while (i4 < size3) {
                                MenuItemImpl menuItemImpl2 = (MenuItemImpl) subMenu.getItem(i4);
                                if (menuItemImpl2.isVisible()) {
                                    if (!z && menuItemImpl2.getIcon() != null) {
                                    }
                                    if (menuItemImpl2.isCheckable()) {
                                        menuItemImpl2.setExclusiveCheckable(false);
                                    }
                                    if (menuItemImpl.isChecked()) {
                                        a(menuItemImpl);
                                    }
                                    this.b.add(new f(menuItemImpl2));
                                }
                                i4++;
                                z = true;
                            }
                            if (z) {
                                a(size2, this.b.size());
                            }
                        }
                    } else {
                        int groupId = menuItemImpl.getGroupId();
                        if (groupId != i) {
                            i3 = this.b.size();
                            boolean z3 = menuItemImpl.getIcon() != null;
                            if (i2 != 0) {
                                i3++;
                                this.b.add(new e(NavigationMenuPresenter.this.o, NavigationMenuPresenter.this.o));
                                z2 = z3;
                            } else {
                                z2 = z3;
                            }
                        } else if (!z2 && menuItemImpl.getIcon() != null) {
                            a(i3, this.b.size());
                            z2 = true;
                        }
                        f fVar = new f(menuItemImpl);
                        fVar.a = z2;
                        this.b.add(fVar);
                        i = groupId;
                    }
                    i2++;
                    z = true;
                }
                this.d = false;
            }
        }

        private void a(int i, int i2) {
            while (i < i2) {
                ((f) this.b.get(i)).a = true;
                i++;
            }
        }

        public void a(@NonNull MenuItemImpl menuItemImpl) {
            if (this.c != menuItemImpl && menuItemImpl.isCheckable()) {
                MenuItemImpl menuItemImpl2 = this.c;
                if (menuItemImpl2 != null) {
                    menuItemImpl2.setChecked(false);
                }
                this.c = menuItemImpl;
                menuItemImpl.setChecked(true);
            }
        }

        public MenuItemImpl b() {
            return this.c;
        }

        @NonNull
        public Bundle c() {
            Bundle bundle = new Bundle();
            MenuItemImpl menuItemImpl = this.c;
            if (menuItemImpl != null) {
                bundle.putInt("android:menu:checked", menuItemImpl.getItemId());
            }
            SparseArray<? extends Parcelable> sparseArray = new SparseArray<>();
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                d dVar = this.b.get(i);
                if (dVar instanceof f) {
                    MenuItemImpl a = ((f) dVar).a();
                    View actionView = a != null ? a.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray parcelableSparseArray = new ParcelableSparseArray();
                        actionView.saveHierarchyState(parcelableSparseArray);
                        sparseArray.put(a.getItemId(), parcelableSparseArray);
                    }
                }
            }
            bundle.putSparseParcelableArray("android:menu:action_views", sparseArray);
            return bundle;
        }

        public void a(@NonNull Bundle bundle) {
            MenuItemImpl a;
            View actionView;
            ParcelableSparseArray parcelableSparseArray;
            MenuItemImpl a2;
            int i = bundle.getInt("android:menu:checked", 0);
            if (i != 0) {
                this.d = true;
                int size = this.b.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    d dVar = this.b.get(i2);
                    if ((dVar instanceof f) && (a2 = ((f) dVar).a()) != null && a2.getItemId() == i) {
                        a(a2);
                        break;
                    }
                    i2++;
                }
                this.d = false;
                e();
            }
            SparseArray sparseParcelableArray = bundle.getSparseParcelableArray("android:menu:action_views");
            if (sparseParcelableArray != null) {
                int size2 = this.b.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    d dVar2 = this.b.get(i3);
                    if (!(!(dVar2 instanceof f) || (a = ((f) dVar2).a()) == null || (actionView = a.getActionView()) == null || (parcelableSparseArray = (ParcelableSparseArray) sparseParcelableArray.get(a.getItemId())) == null)) {
                        actionView.restoreHierarchyState(parcelableSparseArray);
                    }
                }
            }
        }

        public void a(boolean z) {
            this.d = z;
        }

        int d() {
            int i = NavigationMenuPresenter.this.a.getChildCount() == 0 ? 0 : 1;
            for (int i2 = 0; i2 < NavigationMenuPresenter.this.c.getItemCount(); i2++) {
                if (NavigationMenuPresenter.this.c.getItemViewType(i2) == 0) {
                    i++;
                }
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class f implements d {
        boolean a;
        private final MenuItemImpl b;

        f(MenuItemImpl menuItemImpl) {
            this.b = menuItemImpl;
        }

        public MenuItemImpl a() {
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class e implements d {
        private final int a;
        private final int b;

        public e(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class c implements d {
        c() {
        }
    }

    /* loaded from: classes2.dex */
    private class g extends RecyclerViewAccessibilityDelegate {
        g(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override // androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate, androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(NavigationMenuPresenter.this.c.d(), 0, false));
        }
    }
}
