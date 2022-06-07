package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.MenuItemHoverListener;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class CascadingMenuPopup extends c implements View.OnKeyListener, PopupWindow.OnDismissListener, MenuPresenter {
    private static final int g = R.layout.abc_cascading_menu_item_layout;
    private PopupWindow.OnDismissListener A;
    View d;
    ViewTreeObserver e;
    boolean f;
    private final Context h;
    private final int i;
    private final int j;
    private final int k;
    private final boolean l;
    private View r;
    private boolean t;
    private boolean u;
    private int v;
    private int w;
    private boolean y;
    private MenuPresenter.Callback z;
    private final List<MenuBuilder> m = new ArrayList();
    final List<a> b = new ArrayList();
    final ViewTreeObserver.OnGlobalLayoutListener c = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.b.size() > 0 && !CascadingMenuPopup.this.b.get(0).a.isModal()) {
                View view = CascadingMenuPopup.this.d;
                if (view == null || !view.isShown()) {
                    CascadingMenuPopup.this.dismiss();
                    return;
                }
                for (a aVar : CascadingMenuPopup.this.b) {
                    aVar.a.show();
                }
            }
        }
    };
    private final View.OnAttachStateChangeListener n = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.2
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (CascadingMenuPopup.this.e != null) {
                if (!CascadingMenuPopup.this.e.isAlive()) {
                    CascadingMenuPopup.this.e = view.getViewTreeObserver();
                }
                CascadingMenuPopup.this.e.removeGlobalOnLayoutListener(CascadingMenuPopup.this.c);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private final MenuItemHoverListener o = new MenuItemHoverListener() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3
        @Override // androidx.appcompat.widget.MenuItemHoverListener
        public void onItemHoverExit(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            CascadingMenuPopup.this.a.removeCallbacksAndMessages(menuBuilder);
        }

        @Override // androidx.appcompat.widget.MenuItemHoverListener
        public void onItemHoverEnter(@NonNull final MenuBuilder menuBuilder, @NonNull final MenuItem menuItem) {
            final a aVar = null;
            CascadingMenuPopup.this.a.removeCallbacksAndMessages(null);
            int size = CascadingMenuPopup.this.b.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (menuBuilder == CascadingMenuPopup.this.b.get(i).b) {
                    break;
                } else {
                    i++;
                }
            }
            if (i != -1) {
                int i2 = i + 1;
                if (i2 < CascadingMenuPopup.this.b.size()) {
                    aVar = CascadingMenuPopup.this.b.get(i2);
                }
                CascadingMenuPopup.this.a.postAtTime(new Runnable() { // from class: androidx.appcompat.view.menu.CascadingMenuPopup.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (aVar != null) {
                            CascadingMenuPopup.this.f = true;
                            aVar.b.close(false);
                            CascadingMenuPopup.this.f = false;
                        }
                        if (menuItem.isEnabled() && menuItem.hasSubMenu()) {
                            menuBuilder.performItemAction(menuItem, 4);
                        }
                    }
                }, menuBuilder, SystemClock.uptimeMillis() + 200);
            }
        }
    };
    private int p = 0;
    private int q = 0;
    private boolean x = false;
    private int s = d();
    final Handler a = new Handler();

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface HorizPosition {
    }

    @Override // androidx.appcompat.view.menu.c
    protected boolean a() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        return null;
    }

    public CascadingMenuPopup(@NonNull Context context, @NonNull View view, @AttrRes int i, @StyleRes int i2, boolean z) {
        this.h = context;
        this.r = view;
        this.j = i;
        this.k = i2;
        this.l = z;
        Resources resources = context.getResources();
        this.i = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(boolean z) {
        this.x = z;
    }

    private MenuPopupWindow c() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.h, null, this.j, this.k);
        menuPopupWindow.setHoverListener(this.o);
        menuPopupWindow.setOnItemClickListener(this);
        menuPopupWindow.setOnDismissListener(this);
        menuPopupWindow.setAnchorView(this.r);
        menuPopupWindow.setDropDownGravity(this.q);
        menuPopupWindow.setModal(true);
        menuPopupWindow.setInputMethodMode(2);
        return menuPopupWindow;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void show() {
        if (!isShowing()) {
            for (MenuBuilder menuBuilder : this.m) {
                c(menuBuilder);
            }
            this.m.clear();
            this.d = this.r;
            if (this.d != null) {
                boolean z = this.e == null;
                this.e = this.d.getViewTreeObserver();
                if (z) {
                    this.e.addOnGlobalLayoutListener(this.c);
                }
                this.d.addOnAttachStateChangeListener(this.n);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void dismiss() {
        int size = this.b.size();
        if (size > 0) {
            a[] aVarArr = (a[]) this.b.toArray(new a[size]);
            for (int i = size - 1; i >= 0; i--) {
                a aVar = aVarArr[i];
                if (aVar.a.isShowing()) {
                    aVar.a.dismiss();
                }
            }
        }
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    private int d() {
        return ViewCompat.getLayoutDirection(this.r) == 1 ? 0 : 1;
    }

    private int d(int i) {
        List<a> list = this.b;
        ListView a2 = list.get(list.size() - 1).a();
        int[] iArr = new int[2];
        a2.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        this.d.getWindowVisibleDisplayFrame(rect);
        return this.s == 1 ? (iArr[0] + a2.getWidth()) + i > rect.right ? 0 : 1 : iArr[0] - i < 0 ? 1 : 0;
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(MenuBuilder menuBuilder) {
        menuBuilder.addMenuPresenter(this, this.h);
        if (isShowing()) {
            c(menuBuilder);
        } else {
            this.m.add(menuBuilder);
        }
    }

    private void c(@NonNull MenuBuilder menuBuilder) {
        View view;
        a aVar;
        int i;
        int i2;
        int i3;
        LayoutInflater from = LayoutInflater.from(this.h);
        MenuAdapter menuAdapter = new MenuAdapter(menuBuilder, from, this.l, g);
        if (!isShowing() && this.x) {
            menuAdapter.setForceShowIcon(true);
        } else if (isShowing()) {
            menuAdapter.setForceShowIcon(c.b(menuBuilder));
        }
        int a2 = a(menuAdapter, null, this.h, this.i);
        MenuPopupWindow c = c();
        c.setAdapter(menuAdapter);
        c.setContentWidth(a2);
        c.setDropDownGravity(this.q);
        if (this.b.size() > 0) {
            List<a> list = this.b;
            aVar = list.get(list.size() - 1);
            view = a(aVar, menuBuilder);
        } else {
            aVar = null;
            view = null;
        }
        if (view != null) {
            c.setTouchModal(false);
            c.setEnterTransition(null);
            int d = d(a2);
            boolean z = d == 1;
            this.s = d;
            if (Build.VERSION.SDK_INT >= 26) {
                c.setAnchorView(view);
                i2 = 0;
                i = 0;
            } else {
                int[] iArr = new int[2];
                this.r.getLocationOnScreen(iArr);
                int[] iArr2 = new int[2];
                view.getLocationOnScreen(iArr2);
                if ((this.q & 7) == 5) {
                    iArr[0] = iArr[0] + this.r.getWidth();
                    iArr2[0] = iArr2[0] + view.getWidth();
                }
                i = iArr2[0] - iArr[0];
                i2 = iArr2[1] - iArr[1];
            }
            if ((this.q & 5) == 5) {
                i3 = z ? i + a2 : i - view.getWidth();
            } else {
                i3 = z ? i + view.getWidth() : i - a2;
            }
            c.setHorizontalOffset(i3);
            c.setOverlapAnchor(true);
            c.setVerticalOffset(i2);
        } else {
            if (this.t) {
                c.setHorizontalOffset(this.v);
            }
            if (this.u) {
                c.setVerticalOffset(this.w);
            }
            c.setEpicenterBounds(b());
        }
        this.b.add(new a(c, menuBuilder, this.s));
        c.show();
        ListView listView = c.getListView();
        listView.setOnKeyListener(this);
        if (aVar == null && this.y && menuBuilder.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) from.inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false);
            frameLayout.setEnabled(false);
            ((TextView) frameLayout.findViewById(16908310)).setText(menuBuilder.getHeaderTitle());
            listView.addHeaderView(frameLayout, null, false);
            c.show();
        }
    }

    private MenuItem a(@NonNull MenuBuilder menuBuilder, @NonNull MenuBuilder menuBuilder2) {
        int size = menuBuilder.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = menuBuilder.getItem(i);
            if (item.hasSubMenu() && menuBuilder2 == item.getSubMenu()) {
                return item;
            }
        }
        return null;
    }

    @Nullable
    private View a(@NonNull a aVar, @NonNull MenuBuilder menuBuilder) {
        int i;
        MenuAdapter menuAdapter;
        int firstVisiblePosition;
        MenuItem a2 = a(aVar.b, menuBuilder);
        if (a2 == null) {
            return null;
        }
        ListView a3 = aVar.a();
        ListAdapter adapter = a3.getAdapter();
        int i2 = 0;
        if (adapter instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
            i = headerViewListAdapter.getHeadersCount();
            menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
        } else {
            menuAdapter = (MenuAdapter) adapter;
            i = 0;
        }
        int count = menuAdapter.getCount();
        while (true) {
            if (i2 >= count) {
                i2 = -1;
                break;
            } else if (a2 == menuAdapter.getItem(i2)) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1 && (firstVisiblePosition = (i2 + i) - a3.getFirstVisiblePosition()) >= 0 && firstVisiblePosition < a3.getChildCount()) {
            return a3.getChildAt(firstVisiblePosition);
        }
        return null;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.b.size() > 0 && this.b.get(0).a.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        a aVar;
        int size = this.b.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                aVar = null;
                break;
            }
            aVar = this.b.get(i);
            if (!aVar.a.isShowing()) {
                break;
            }
            i++;
        }
        if (aVar != null) {
            aVar.b.close(false);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        for (a aVar : this.b) {
            a(aVar.a().getAdapter()).notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.z = callback;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        for (a aVar : this.b) {
            if (subMenuBuilder == aVar.b) {
                aVar.a().requestFocus();
                return true;
            }
        }
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        a(subMenuBuilder);
        MenuPresenter.Callback callback = this.z;
        if (callback != null) {
            callback.onOpenSubMenu(subMenuBuilder);
        }
        return true;
    }

    private int d(@NonNull MenuBuilder menuBuilder) {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            if (menuBuilder == this.b.get(i).b) {
                return i;
            }
        }
        return -1;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        int d = d(menuBuilder);
        if (d >= 0) {
            int i = d + 1;
            if (i < this.b.size()) {
                this.b.get(i).b.close(false);
            }
            a remove = this.b.remove(d);
            remove.b.removeMenuPresenter(this);
            if (this.f) {
                remove.a.setExitTransition(null);
                remove.a.setAnimationStyle(0);
            }
            remove.a.dismiss();
            int size = this.b.size();
            if (size > 0) {
                this.s = this.b.get(size - 1).c;
            } else {
                this.s = d();
            }
            if (size == 0) {
                dismiss();
                MenuPresenter.Callback callback = this.z;
                if (callback != null) {
                    callback.onCloseMenu(menuBuilder, true);
                }
                ViewTreeObserver viewTreeObserver = this.e;
                if (viewTreeObserver != null) {
                    if (viewTreeObserver.isAlive()) {
                        this.e.removeGlobalOnLayoutListener(this.c);
                    }
                    this.e = null;
                }
                this.d.removeOnAttachStateChangeListener(this.n);
                this.A.onDismiss();
            } else if (z) {
                this.b.get(0).b.close(false);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(int i) {
        if (this.p != i) {
            this.p = i;
            this.q = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this.r));
        }
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(@NonNull View view) {
        if (this.r != view) {
            this.r = view;
            this.q = GravityCompat.getAbsoluteGravity(this.p, ViewCompat.getLayoutDirection(this.r));
        }
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.A = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public ListView getListView() {
        if (this.b.isEmpty()) {
            return null;
        }
        List<a> list = this.b;
        return list.get(list.size() - 1).a();
    }

    @Override // androidx.appcompat.view.menu.c
    public void b(int i) {
        this.t = true;
        this.v = i;
    }

    @Override // androidx.appcompat.view.menu.c
    public void c(int i) {
        this.u = true;
        this.w = i;
    }

    @Override // androidx.appcompat.view.menu.c
    public void b(boolean z) {
        this.y = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        public final MenuPopupWindow a;
        public final MenuBuilder b;
        public final int c;

        public a(@NonNull MenuPopupWindow menuPopupWindow, @NonNull MenuBuilder menuBuilder, int i) {
            this.a = menuPopupWindow;
            this.b = menuBuilder;
            this.c = i;
        }

        public ListView a() {
            return this.a.getListView();
        }
    }
}
