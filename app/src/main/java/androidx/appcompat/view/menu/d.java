package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.view.ViewCompat;

/* compiled from: StandardMenuPopup.java */
/* loaded from: classes.dex */
final class d extends c implements View.OnKeyListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener, MenuPresenter {
    private static final int e = R.layout.abc_popup_menu_item_layout;
    final MenuPopupWindow a;
    View c;
    ViewTreeObserver d;
    private final Context f;
    private final MenuBuilder g;
    private final MenuAdapter h;
    private final boolean i;
    private final int j;
    private final int k;
    private final int l;
    private PopupWindow.OnDismissListener n;
    private View o;
    private MenuPresenter.Callback p;
    private boolean q;
    private boolean r;
    private int s;
    private boolean u;
    final ViewTreeObserver.OnGlobalLayoutListener b = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.view.menu.d.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (d.this.isShowing() && !d.this.a.isModal()) {
                View view = d.this.c;
                if (view == null || !view.isShown()) {
                    d.this.dismiss();
                } else {
                    d.this.a.show();
                }
            }
        }
    };
    private final View.OnAttachStateChangeListener m = new View.OnAttachStateChangeListener() { // from class: androidx.appcompat.view.menu.d.2
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            if (d.this.d != null) {
                if (!d.this.d.isAlive()) {
                    d.this.d = view.getViewTreeObserver();
                }
                d.this.d.removeGlobalOnLayoutListener(d.this.b);
            }
            view.removeOnAttachStateChangeListener(this);
        }
    };
    private int t = 0;

    @Override // androidx.appcompat.view.menu.c
    public void a(MenuBuilder menuBuilder) {
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

    public d(Context context, MenuBuilder menuBuilder, View view, int i, int i2, boolean z) {
        this.f = context;
        this.g = menuBuilder;
        this.i = z;
        this.h = new MenuAdapter(menuBuilder, LayoutInflater.from(context), this.i, e);
        this.k = i;
        this.l = i2;
        Resources resources = context.getResources();
        this.j = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        this.o = view;
        this.a = new MenuPopupWindow(this.f, null, this.k, this.l);
        menuBuilder.addMenuPresenter(this, context);
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(boolean z) {
        this.h.setForceShowIcon(z);
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(int i) {
        this.t = i;
    }

    private boolean c() {
        View view;
        if (isShowing()) {
            return true;
        }
        if (this.q || (view = this.o) == null) {
            return false;
        }
        this.c = view;
        this.a.setOnDismissListener(this);
        this.a.setOnItemClickListener(this);
        this.a.setModal(true);
        View view2 = this.c;
        boolean z = this.d == null;
        this.d = view2.getViewTreeObserver();
        if (z) {
            this.d.addOnGlobalLayoutListener(this.b);
        }
        view2.addOnAttachStateChangeListener(this.m);
        this.a.setAnchorView(view2);
        this.a.setDropDownGravity(this.t);
        if (!this.r) {
            this.s = a(this.h, null, this.f, this.j);
            this.r = true;
        }
        this.a.setContentWidth(this.s);
        this.a.setInputMethodMode(2);
        this.a.setEpicenterBounds(b());
        this.a.show();
        ListView listView = this.a.getListView();
        listView.setOnKeyListener(this);
        if (this.u && this.g.getHeaderTitle() != null) {
            FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.f).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup) listView, false);
            TextView textView = (TextView) frameLayout.findViewById(16908310);
            if (textView != null) {
                textView.setText(this.g.getHeaderTitle());
            }
            frameLayout.setEnabled(false);
            listView.addHeaderView(frameLayout, null, false);
        }
        this.a.setAdapter(this.h);
        this.a.show();
        return true;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void show() {
        if (!c()) {
            throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
        }
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void dismiss() {
        if (isShowing()) {
            this.a.dismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public boolean isShowing() {
        return !this.q && this.a.isShowing();
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        this.q = true;
        this.g.close();
        ViewTreeObserver viewTreeObserver = this.d;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.d = this.c.getViewTreeObserver();
            }
            this.d.removeGlobalOnLayoutListener(this.b);
            this.d = null;
        }
        this.c.removeOnAttachStateChangeListener(this.m);
        PopupWindow.OnDismissListener onDismissListener = this.n;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        this.r = false;
        MenuAdapter menuAdapter = this.h;
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(MenuPresenter.Callback callback) {
        this.p = callback;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (subMenuBuilder.hasVisibleItems()) {
            MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.f, subMenuBuilder, this.c, this.i, this.k, this.l);
            menuPopupHelper.setPresenterCallback(this.p);
            menuPopupHelper.setForceShowIcon(c.b(subMenuBuilder));
            menuPopupHelper.setOnDismissListener(this.n);
            this.n = null;
            this.g.close(false);
            int horizontalOffset = this.a.getHorizontalOffset();
            int verticalOffset = this.a.getVerticalOffset();
            if ((Gravity.getAbsoluteGravity(this.t, ViewCompat.getLayoutDirection(this.o)) & 7) == 5) {
                horizontalOffset += this.o.getWidth();
            }
            if (menuPopupHelper.tryShow(horizontalOffset, verticalOffset)) {
                MenuPresenter.Callback callback = this.p;
                if (callback == null) {
                    return true;
                }
                callback.onOpenSubMenu(subMenuBuilder);
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (menuBuilder == this.g) {
            dismiss();
            MenuPresenter.Callback callback = this.p;
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(View view) {
        this.o = view;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 82) {
            return false;
        }
        dismiss();
        return true;
    }

    @Override // androidx.appcompat.view.menu.c
    public void a(PopupWindow.OnDismissListener onDismissListener) {
        this.n = onDismissListener;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public ListView getListView() {
        return this.a.getListView();
    }

    @Override // androidx.appcompat.view.menu.c
    public void b(int i) {
        this.a.setHorizontalOffset(i);
    }

    @Override // androidx.appcompat.view.menu.c
    public void c(int i) {
        this.a.setVerticalOffset(i);
    }

    @Override // androidx.appcompat.view.menu.c
    public void b(boolean z) {
        this.u = z;
    }
}
