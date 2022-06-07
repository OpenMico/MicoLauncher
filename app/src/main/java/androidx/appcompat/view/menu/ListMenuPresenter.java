package androidx.appcompat.view.menu;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ListMenuPresenter implements AdapterView.OnItemClickListener, MenuPresenter {
    public static final String VIEWS_TAG = "android:menu:list";
    Context a;
    LayoutInflater b;
    MenuBuilder c;
    ExpandedMenuView d;
    int e;
    int f;
    int g;
    a h;
    private MenuPresenter.Callback i;
    private int j;

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

    public ListMenuPresenter(Context context, int i) {
        this(i, 0);
        this.a = context;
        this.b = LayoutInflater.from(this.a);
    }

    public ListMenuPresenter(int i, int i2) {
        this.g = i;
        this.f = i2;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        int i = this.f;
        if (i != 0) {
            this.a = new ContextThemeWrapper(context, i);
            this.b = LayoutInflater.from(this.a);
        } else if (this.a != null) {
            this.a = context;
            if (this.b == null) {
                this.b = LayoutInflater.from(this.a);
            }
        }
        this.c = menuBuilder;
        a aVar = this.h;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.d == null) {
            this.d = (ExpandedMenuView) this.b.inflate(R.layout.abc_expanded_menu_layout, viewGroup, false);
            if (this.h == null) {
                this.h = new a();
            }
            this.d.setAdapter((ListAdapter) this.h);
            this.d.setOnItemClickListener(this);
        }
        return this.d;
    }

    public ListAdapter getAdapter() {
        if (this.h == null) {
            this.h = new a();
        }
        return this.h;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        a aVar = this.h;
        if (aVar != null) {
            aVar.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.i = callback;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        new b(subMenuBuilder).a(null);
        MenuPresenter.Callback callback = this.i;
        if (callback == null) {
            return true;
        }
        callback.onOpenSubMenu(subMenuBuilder);
        return true;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        MenuPresenter.Callback callback = this.i;
        if (callback != null) {
            callback.onCloseMenu(menuBuilder, z);
        }
    }

    public void setItemIndexOffset(int i) {
        this.e = i;
        if (this.d != null) {
            updateMenuView(false);
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.c.performItemAction(this.h.getItem(i), this, 0);
    }

    public void saveHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        ExpandedMenuView expandedMenuView = this.d;
        if (expandedMenuView != null) {
            expandedMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
    }

    public void restoreHierarchyState(Bundle bundle) {
        SparseArray<Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            this.d.restoreHierarchyState(sparseParcelableArray);
        }
    }

    public void setId(int i) {
        this.j = i;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public int getId() {
        return this.j;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        if (this.d == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        saveHierarchyState(bundle);
        return bundle;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        restoreHierarchyState((Bundle) parcelable);
    }

    /* loaded from: classes.dex */
    public class a extends BaseAdapter {
        private int b = -1;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public a() {
            ListMenuPresenter.this = r1;
            a();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int size = ListMenuPresenter.this.c.getNonActionItems().size() - ListMenuPresenter.this.e;
            return this.b < 0 ? size : size - 1;
        }

        /* renamed from: a */
        public MenuItemImpl getItem(int i) {
            ArrayList<MenuItemImpl> nonActionItems = ListMenuPresenter.this.c.getNonActionItems();
            int i2 = i + ListMenuPresenter.this.e;
            int i3 = this.b;
            if (i3 >= 0 && i2 >= i3) {
                i2++;
            }
            return nonActionItems.get(i2);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ListMenuPresenter.this.b.inflate(ListMenuPresenter.this.g, viewGroup, false);
            }
            ((MenuView.ItemView) view).initialize(getItem(i), 0);
            return view;
        }

        void a() {
            MenuItemImpl expandedItem = ListMenuPresenter.this.c.getExpandedItem();
            if (expandedItem != null) {
                ArrayList<MenuItemImpl> nonActionItems = ListMenuPresenter.this.c.getNonActionItems();
                int size = nonActionItems.size();
                for (int i = 0; i < size; i++) {
                    if (nonActionItems.get(i) == expandedItem) {
                        this.b = i;
                        return;
                    }
                }
            }
            this.b = -1;
        }

        @Override // android.widget.BaseAdapter
        public void notifyDataSetChanged() {
            a();
            super.notifyDataSetChanged();
        }
    }
}
