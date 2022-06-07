package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;

/* compiled from: ToolbarActionBar.java */
/* loaded from: classes.dex */
class e extends ActionBar {
    DecorToolbar a;
    boolean b;
    Window.Callback c;
    private boolean d;
    private boolean e;
    private ArrayList<ActionBar.OnMenuVisibilityListener> f = new ArrayList<>();
    private final Runnable g = new Runnable() { // from class: androidx.appcompat.app.e.1
        @Override // java.lang.Runnable
        public void run() {
            e.this.c();
        }
    };
    private final Toolbar.OnMenuItemClickListener h = new Toolbar.OnMenuItemClickListener() { // from class: androidx.appcompat.app.e.2
        @Override // androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            return e.this.c.onMenuItemSelected(0, menuItem);
        }
    };

    @Override // androidx.appcompat.app.ActionBar
    public int getNavigationItemCount() {
        return 0;
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getNavigationMode() {
        return 0;
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getSelectedNavigationIndex() {
        return -1;
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getTabCount() {
        return 0;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setStackedBackgroundDrawable(Drawable drawable) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        this.a = new ToolbarWidgetWrapper(toolbar, false);
        this.c = new c(callback);
        this.a.setWindowCallback(this.c);
        toolbar.setOnMenuItemClickListener(this.h);
        this.a.setWindowTitle(charSequence);
    }

    public Window.Callback b() {
        return this.c;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(View view) {
        setCustomView(view, new ActionBar.LayoutParams(-2, -2));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        if (view != null) {
            view.setLayoutParams(layoutParams);
        }
        this.a.setCustomView(view);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(this.a.getContext()).inflate(i, this.a.getViewGroup(), false));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setIcon(int i) {
        this.a.setIcon(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setIcon(Drawable drawable) {
        this.a.setIcon(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setLogo(int i) {
        this.a.setLogo(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setLogo(Drawable drawable) {
        this.a.setLogo(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setElevation(float f) {
        ViewCompat.setElevation(this.a.getViewGroup(), f);
    }

    @Override // androidx.appcompat.app.ActionBar
    public float getElevation() {
        return ViewCompat.getElevation(this.a.getViewGroup());
    }

    @Override // androidx.appcompat.app.ActionBar
    public Context getThemedContext() {
        return this.a.getContext();
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isTitleTruncated() {
        return super.isTitleTruncated();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.a.setNavigationIcon(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.a.setNavigationIcon(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.a.setNavigationContentDescription(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.a.setNavigationContentDescription(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.a.setDropdownParams(spinnerAdapter, new c(onNavigationListener));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        if (this.a.getNavigationMode() == 1) {
            this.a.setDropdownSelectedPosition(i);
            return;
        }
        throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setTitle(CharSequence charSequence) {
        this.a.setTitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setTitle(int i) {
        DecorToolbar decorToolbar = this.a;
        decorToolbar.setTitle(i != 0 ? decorToolbar.getContext().getText(i) : null);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setWindowTitle(CharSequence charSequence) {
        this.a.setWindowTitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean requestFocus() {
        ViewGroup viewGroup = this.a.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSubtitle(CharSequence charSequence) {
        this.a.setSubtitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSubtitle(int i) {
        DecorToolbar decorToolbar = this.a;
        decorToolbar.setSubtitle(i != 0 ? decorToolbar.getContext().getText(i) : null);
    }

    @Override // androidx.appcompat.app.ActionBar
    @SuppressLint({"WrongConstant"})
    public void setDisplayOptions(int i) {
        setDisplayOptions(i, -1);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        this.a.setDisplayOptions((i & i2) | ((~i2) & this.a.getDisplayOptions()));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.a.setBackgroundDrawable(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public View getCustomView() {
        return this.a.getCustomView();
    }

    @Override // androidx.appcompat.app.ActionBar
    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    @Override // androidx.appcompat.app.ActionBar
    public CharSequence getSubtitle() {
        return this.a.getSubtitle();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setNavigationMode(int i) {
        if (i != 2) {
            this.a.setNavigationMode(i);
            return;
        }
        throw new IllegalArgumentException("Tabs not supported in this configuration");
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getDisplayOptions() {
        return this.a.getDisplayOptions();
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab newTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeAllTabs() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public void selectTab(ActionBar.Tab tab) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab getTabAt(int i) {
        throw new UnsupportedOperationException("Tabs are not supported in toolbar action bars");
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getHeight() {
        return this.a.getHeight();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void show() {
        this.a.setVisibility(0);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void hide() {
        this.a.setVisibility(8);
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isShowing() {
        return this.a.getVisibility() == 0;
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean openOptionsMenu() {
        return this.a.showOverflowMenu();
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean closeOptionsMenu() {
        return this.a.hideOverflowMenu();
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean invalidateOptionsMenu() {
        this.a.getViewGroup().removeCallbacks(this.g);
        ViewCompat.postOnAnimation(this.a.getViewGroup(), this.g);
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean collapseActionView() {
        if (!this.a.hasExpandedActionView()) {
            return false;
        }
        this.a.collapseActionView();
        return true;
    }

    void c() {
        Menu d = d();
        MenuBuilder menuBuilder = d instanceof MenuBuilder ? (MenuBuilder) d : null;
        if (menuBuilder != null) {
            menuBuilder.stopDispatchingItemsChanged();
        }
        try {
            d.clear();
            if (!this.c.onCreatePanelMenu(0, d) || !this.c.onPreparePanel(0, null, d)) {
                d.clear();
            }
        } finally {
            if (menuBuilder != null) {
                menuBuilder.startDispatchingItemsChanged();
            }
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean onMenuKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            openOptionsMenu();
        }
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu d = d();
        if (d == null) {
            return false;
        }
        boolean z = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z = false;
        }
        d.setQwertyMode(z);
        return d.performShortcut(i, keyEvent, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.appcompat.app.ActionBar
    public void a() {
        this.a.getViewGroup().removeCallbacks(this.g);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.f.add(onMenuVisibilityListener);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.f.remove(onMenuVisibilityListener);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z != this.e) {
            this.e = z;
            int size = this.f.size();
            for (int i = 0; i < size; i++) {
                this.f.get(i).onMenuVisibilityChanged(z);
            }
        }
    }

    /* compiled from: ToolbarActionBar.java */
    /* loaded from: classes.dex */
    private class c extends WindowCallbackWrapper {
        public c(Window.Callback callback) {
            super(callback);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view, Menu menu) {
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (onPreparePanel && !e.this.b) {
                e.this.a.setMenuPrepared();
                e.this.b = true;
            }
            return onPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public View onCreatePanelView(int i) {
            if (i == 0) {
                return new View(e.this.a.getContext());
            }
            return super.onCreatePanelView(i);
        }
    }

    private Menu d() {
        if (!this.d) {
            this.a.setMenuCallbacks(new a(), new b());
            this.d = true;
        }
        return this.a.getMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ToolbarActionBar.java */
    /* loaded from: classes.dex */
    public final class a implements MenuPresenter.Callback {
        private boolean b;

        a() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            if (e.this.c == null) {
                return false;
            }
            e.this.c.onMenuOpened(108, menuBuilder);
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
            if (!this.b) {
                this.b = true;
                e.this.a.dismissPopupMenus();
                if (e.this.c != null) {
                    e.this.c.onPanelClosed(108, menuBuilder);
                }
                this.b = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ToolbarActionBar.java */
    /* loaded from: classes.dex */
    public final class b implements MenuBuilder.Callback {
        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            return false;
        }

        b() {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
            if (e.this.c == null) {
                return;
            }
            if (e.this.a.isOverflowMenuShowing()) {
                e.this.c.onPanelClosed(108, menuBuilder);
            } else if (e.this.c.onPreparePanel(0, null, menuBuilder)) {
                e.this.c.onMenuOpened(108, menuBuilder);
            }
        }
    }
}
