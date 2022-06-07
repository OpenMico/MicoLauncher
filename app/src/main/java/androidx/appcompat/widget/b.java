package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.BaseMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ActionProvider;
import androidx.core.view.GravityCompat;
import java.util.ArrayList;

/* compiled from: ActionMenuPresenter.java */
/* loaded from: classes.dex */
public class b extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
    d a;
    e b;
    a c;
    c d;
    int f;
    private Drawable g;
    private boolean h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private int r;
    private C0004b t;
    private final SparseBooleanArray s = new SparseBooleanArray();
    final f e = new f();

    public b(Context context) {
        super(context, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(@NonNull Context context, @Nullable MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        Resources resources = context.getResources();
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        if (!this.j) {
            this.i = actionBarPolicy.showsOverflowMenuButton();
        }
        if (!this.p) {
            this.k = actionBarPolicy.getEmbeddedMenuWidthLimit();
        }
        if (!this.n) {
            this.m = actionBarPolicy.getMaxActionButtons();
        }
        int i = this.k;
        if (this.i) {
            if (this.a == null) {
                this.a = new d(this.mSystemContext);
                if (this.h) {
                    this.a.setImageDrawable(this.g);
                    this.g = null;
                    this.h = false;
                }
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.a.measure(makeMeasureSpec, makeMeasureSpec);
            }
            i -= this.a.getMeasuredWidth();
        } else {
            this.a = null;
        }
        this.l = i;
        this.r = (int) (resources.getDisplayMetrics().density * 56.0f);
    }

    public void a(Configuration configuration) {
        if (!this.n) {
            this.m = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void a(boolean z) {
        this.i = z;
        this.j = true;
    }

    public void b(boolean z) {
        this.q = z;
    }

    public void a(Drawable drawable) {
        d dVar = this.a;
        if (dVar != null) {
            dVar.setImageDrawable(drawable);
            return;
        }
        this.h = true;
        this.g = drawable;
    }

    public Drawable a() {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.getDrawable();
        }
        if (this.h) {
            return this.g;
        }
        return null;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        MenuView menuView = this.mMenuView;
        MenuView menuView2 = super.getMenuView(viewGroup);
        if (menuView != menuView2) {
            ((ActionMenuView) menuView2).setPresenter(this);
        }
        return menuView2;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.mMenuView);
        if (this.t == null) {
            this.t = new C0004b();
        }
        actionMenuItemView.setPopupCallback(this.t);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        super.updateMenuView(z);
        ((View) this.mMenuView).requestLayout();
        boolean z2 = false;
        if (this.mMenu != null) {
            ArrayList<MenuItemImpl> actionItems = this.mMenu.getActionItems();
            int size = actionItems.size();
            for (int i = 0; i < size; i++) {
                ActionProvider supportActionProvider = actionItems.get(i).getSupportActionProvider();
                if (supportActionProvider != null) {
                    supportActionProvider.setSubUiVisibilityListener(this);
                }
            }
        }
        ArrayList<MenuItemImpl> nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        if (this.i && nonActionItems != null) {
            int size2 = nonActionItems.size();
            if (size2 == 1) {
                z2 = !nonActionItems.get(0).isActionViewExpanded();
            } else if (size2 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.a == null) {
                this.a = new d(this.mSystemContext);
            }
            ViewGroup viewGroup = (ViewGroup) this.a.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.a);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
                actionMenuView.addView(this.a, actionMenuView.generateOverflowButtonLayoutParams());
            }
        } else {
            d dVar = this.a;
            if (dVar != null && dVar.getParent() == this.mMenuView) {
                ((ViewGroup) this.mMenuView).removeView(this.a);
            }
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.i);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter
    public boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.a) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, i);
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        boolean z = false;
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (SubMenuBuilder) subMenuBuilder2.getParentMenu();
        }
        View a2 = a(subMenuBuilder2.getItem());
        if (a2 == null) {
            return false;
        }
        this.f = subMenuBuilder.getItem().getItemId();
        int size = subMenuBuilder.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            MenuItem item = subMenuBuilder.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
            i++;
        }
        this.c = new a(this.mContext, subMenuBuilder, a2);
        this.c.setForceShowIcon(z);
        this.c.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    private View a(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public boolean b() {
        if (!this.i || f() || this.mMenu == null || this.mMenuView == null || this.d != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        this.d = new c(new e(this.mContext, this.mMenu, this.a, true));
        ((View) this.mMenuView).post(this.d);
        return true;
    }

    public boolean c() {
        if (this.d == null || this.mMenuView == null) {
            e eVar = this.b;
            if (eVar == null) {
                return false;
            }
            eVar.dismiss();
            return true;
        }
        ((View) this.mMenuView).removeCallbacks(this.d);
        this.d = null;
        return true;
    }

    public boolean d() {
        return c() | e();
    }

    public boolean e() {
        a aVar = this.c;
        if (aVar == null) {
            return false;
        }
        aVar.dismiss();
        return true;
    }

    public boolean f() {
        e eVar = this.b;
        return eVar != null && eVar.isShowing();
    }

    public boolean g() {
        return this.d != null || f();
    }

    public boolean h() {
        return this.i;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        int i;
        ArrayList<MenuItemImpl> arrayList;
        int i2;
        int i3;
        boolean z;
        b bVar = this;
        View view = null;
        boolean z2 = false;
        if (bVar.mMenu != null) {
            arrayList = bVar.mMenu.getVisibleItems();
            i = arrayList.size();
        } else {
            arrayList = null;
            i = 0;
        }
        int i4 = bVar.m;
        int i5 = bVar.l;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) bVar.mMenuView;
        boolean z3 = false;
        int i6 = 0;
        int i7 = 0;
        int i8 = i4;
        for (int i9 = 0; i9 < i; i9++) {
            MenuItemImpl menuItemImpl = arrayList.get(i9);
            if (menuItemImpl.requiresActionButton()) {
                i6++;
            } else if (menuItemImpl.requestsActionButton()) {
                i7++;
            } else {
                z3 = true;
            }
            if (bVar.q && menuItemImpl.isActionViewExpanded()) {
                i8 = 0;
            }
        }
        if (bVar.i && (z3 || i7 + i6 > i8)) {
            i8--;
        }
        int i10 = i8 - i6;
        SparseBooleanArray sparseBooleanArray = bVar.s;
        sparseBooleanArray.clear();
        if (bVar.o) {
            int i11 = bVar.r;
            i2 = i5 / i11;
            i3 = i11 + ((i5 % i11) / i2);
        } else {
            i3 = 0;
            i2 = 0;
        }
        int i12 = 0;
        int i13 = i5;
        int i14 = 0;
        while (i14 < i) {
            MenuItemImpl menuItemImpl2 = arrayList.get(i14);
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = bVar.getItemView(menuItemImpl2, view, viewGroup);
                if (bVar.o) {
                    int i15 = z2 ? 1 : 0;
                    int i16 = z2 ? 1 : 0;
                    int i17 = z2 ? 1 : 0;
                    int i18 = z2 ? 1 : 0;
                    int i19 = z2 ? 1 : 0;
                    int i20 = z2 ? 1 : 0;
                    i2 -= ActionMenuView.a(itemView, i3, i2, makeMeasureSpec, i15);
                } else {
                    itemView.measure(makeMeasureSpec, makeMeasureSpec);
                }
                i12 = itemView.getMeasuredWidth();
                i13 -= i12;
                if (i12 != 0) {
                    i12 = i12;
                }
                int groupId = menuItemImpl2.getGroupId();
                if (groupId != 0) {
                    z = true;
                    sparseBooleanArray.put(groupId, true);
                } else {
                    z = true;
                }
                menuItemImpl2.setIsActionButton(z);
                z2 = z2;
                i = i;
            } else if (menuItemImpl2.requestsActionButton()) {
                int groupId2 = menuItemImpl2.getGroupId();
                boolean z4 = sparseBooleanArray.get(groupId2);
                boolean z5 = (i10 > 0 || z4) && i13 > 0 && (!bVar.o || i2 > 0);
                if (z5) {
                    boolean z6 = z5;
                    i = i;
                    View itemView2 = bVar.getItemView(menuItemImpl2, null, viewGroup);
                    if (bVar.o) {
                        int a2 = ActionMenuView.a(itemView2, i3, i2, makeMeasureSpec, 0);
                        i2 -= a2;
                        z6 = a2 == 0 ? false : z6;
                    } else {
                        itemView2.measure(makeMeasureSpec, makeMeasureSpec);
                    }
                    int measuredWidth = itemView2.getMeasuredWidth();
                    i13 -= measuredWidth;
                    if (i12 == 0) {
                        i12 = measuredWidth;
                    }
                    z5 = bVar.o ? z6 & (i13 >= 0) : z6 & (i13 + i12 > 0);
                } else {
                    i = i;
                }
                if (z5 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z4) {
                    sparseBooleanArray.put(groupId2, false);
                    for (int i21 = 0; i21 < i14; i21++) {
                        MenuItemImpl menuItemImpl3 = arrayList.get(i21);
                        if (menuItemImpl3.getGroupId() == groupId2) {
                            if (menuItemImpl3.isActionButton()) {
                                i10++;
                            }
                            menuItemImpl3.setIsActionButton(false);
                        }
                    }
                }
                if (z5) {
                    i10--;
                }
                menuItemImpl2.setIsActionButton(z5);
                z2 = false;
            } else {
                boolean z7 = z2 ? 1 : 0;
                Object[] objArr = z2 ? 1 : 0;
                Object[] objArr2 = z2 ? 1 : 0;
                Object[] objArr3 = z2 ? 1 : 0;
                Object[] objArr4 = z2 ? 1 : 0;
                Object[] objArr5 = z2 ? 1 : 0;
                z2 = z7;
                i = i;
                menuItemImpl2.setIsActionButton(z2);
            }
            i14++;
            bVar = this;
            view = null;
        }
        return true;
    }

    @Override // androidx.appcompat.view.menu.BaseMenuPresenter, androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        d();
        super.onCloseMenu(menuBuilder, z);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        g gVar = new g();
        gVar.a = this.f;
        return gVar;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem findItem;
        if (parcelable instanceof g) {
            g gVar = (g) parcelable;
            if (gVar.a > 0 && (findItem = this.mMenu.findItem(gVar.a)) != null) {
                onSubMenuSelected((SubMenuBuilder) findItem.getSubMenu());
            }
        }
    }

    @Override // androidx.core.view.ActionProvider.SubUiVisibilityListener
    public void onSubUiVisibilityChanged(boolean z) {
        if (z) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void a(ActionMenuView actionMenuView) {
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
    }

    /* compiled from: ActionMenuPresenter.java */
    @SuppressLint({"BanParcelableUsage"})
    /* loaded from: classes.dex */
    public static class g implements Parcelable {
        public static final Parcelable.Creator<g> CREATOR = new Parcelable.Creator<g>() { // from class: androidx.appcompat.widget.b.g.1
            /* renamed from: a */
            public g createFromParcel(Parcel parcel) {
                return new g(parcel);
            }

            /* renamed from: a */
            public g[] newArray(int i) {
                return new g[i];
            }
        };
        public int a;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        g() {
        }

        g(Parcel parcel) {
            this.a = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
        }
    }

    /* compiled from: ActionMenuPresenter.java */
    /* loaded from: classes.dex */
    public class d extends AppCompatImageView implements ActionMenuView.ActionMenuChildView {
        @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerAfter() {
            return false;
        }

        @Override // androidx.appcompat.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerBefore() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(Context context) {
            super(context, null, R.attr.actionOverflowButtonStyle);
            b.this = r3;
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            TooltipCompat.setTooltipText(this, getContentDescription());
            setOnTouchListener(new ForwardingListener(this) { // from class: androidx.appcompat.widget.b.d.1
                @Override // androidx.appcompat.widget.ForwardingListener
                public ShowableListMenu getPopup() {
                    if (b.this.b == null) {
                        return null;
                    }
                    return b.this.b.getPopup();
                }

                @Override // androidx.appcompat.widget.ForwardingListener
                public boolean onForwardingStarted() {
                    b.this.b();
                    return true;
                }

                @Override // androidx.appcompat.widget.ForwardingListener
                public boolean onForwardingStopped() {
                    if (b.this.d != null) {
                        return false;
                    }
                    b.this.c();
                    return true;
                }
            });
        }

        @Override // android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            b.this.b();
            return true;
        }

        @Override // android.widget.ImageView
        protected boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (!(drawable == null || background == null)) {
                int width = getWidth();
                int height = getHeight();
                int max = Math.max(width, height) / 2;
                int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                DrawableCompat.setHotspotBounds(background, paddingLeft - max, paddingTop - max, paddingLeft + max, paddingTop + max);
            }
            return frame;
        }
    }

    /* compiled from: ActionMenuPresenter.java */
    /* loaded from: classes.dex */
    public class e extends MenuPopupHelper {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public e(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, R.attr.actionOverflowMenuStyle);
            b.this = r7;
            setGravity(GravityCompat.END);
            setPresenterCallback(r7.e);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public void onDismiss() {
            if (b.this.mMenu != null) {
                b.this.mMenu.close();
            }
            b.this.b = null;
            super.onDismiss();
        }
    }

    /* compiled from: ActionMenuPresenter.java */
    /* loaded from: classes.dex */
    public class a extends MenuPopupHelper {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, R.attr.actionOverflowMenuStyle);
            b.this = r7;
            if (!((MenuItemImpl) subMenuBuilder.getItem()).isActionButton()) {
                setAnchorView(r7.a == null ? (View) r7.mMenuView : r7.a);
            }
            setPresenterCallback(r7.e);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.appcompat.view.menu.MenuPopupHelper
        public void onDismiss() {
            b bVar = b.this;
            bVar.c = null;
            bVar.f = 0;
            super.onDismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ActionMenuPresenter.java */
    /* loaded from: classes.dex */
    public class f implements MenuPresenter.Callback {
        f() {
            b.this = r1;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            if (menuBuilder == b.this.mMenu) {
                return false;
            }
            b.this.f = ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = b.this.getCallback();
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder);
            }
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = b.this.getCallback();
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    /* compiled from: ActionMenuPresenter.java */
    /* loaded from: classes.dex */
    public class c implements Runnable {
        private e b;

        public c(e eVar) {
            b.this = r1;
            this.b = eVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (b.this.mMenu != null) {
                b.this.mMenu.changeMenuMode();
            }
            View view = (View) b.this.mMenuView;
            if (!(view == null || view.getWindowToken() == null || !this.b.tryShow())) {
                b.this.b = this.b;
            }
            b.this.d = null;
        }
    }

    /* compiled from: ActionMenuPresenter.java */
    /* renamed from: androidx.appcompat.widget.b$b */
    /* loaded from: classes.dex */
    private class C0004b extends ActionMenuItemView.PopupCallback {
        C0004b() {
            b.this = r1;
        }

        @Override // androidx.appcompat.view.menu.ActionMenuItemView.PopupCallback
        public ShowableListMenu getPopup() {
            if (b.this.c != null) {
                return b.this.c.getPopup();
            }
            return null;
        }
    }
}
