package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    private static final Interpolator s = new AccelerateInterpolator();
    private static final Interpolator t = new DecelerateInterpolator();
    private boolean A;
    private boolean C;
    private boolean E;
    private boolean G;
    Context a;
    ActionBarOverlayLayout b;
    ActionBarContainer c;
    DecorToolbar d;
    ActionBarContextView e;
    View f;
    ScrollingTabContainerView g;
    ActionModeImpl h;
    ActionMode i;
    ActionMode.Callback j;
    boolean l;
    boolean m;
    ViewPropertyAnimatorCompatSet n;
    boolean o;
    private Context u;
    private Activity v;
    private TabImpl x;
    private boolean z;
    private ArrayList<TabImpl> w = new ArrayList<>();
    private int y = -1;
    private ArrayList<ActionBar.OnMenuVisibilityListener> B = new ArrayList<>();
    private int D = 0;
    boolean k = true;
    private boolean F = true;
    final ViewPropertyAnimatorListener p = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.WindowDecorActionBar.1
        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.k && WindowDecorActionBar.this.f != null) {
                WindowDecorActionBar.this.f.setTranslationY(0.0f);
                WindowDecorActionBar.this.c.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.c.setVisibility(8);
            WindowDecorActionBar.this.c.setTransitioning(false);
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            windowDecorActionBar.n = null;
            windowDecorActionBar.b();
            if (WindowDecorActionBar.this.b != null) {
                ViewCompat.requestApplyInsets(WindowDecorActionBar.this.b);
            }
        }
    };
    final ViewPropertyAnimatorListener q = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.WindowDecorActionBar.2
        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            windowDecorActionBar.n = null;
            windowDecorActionBar.c.requestLayout();
        }
    };
    final ViewPropertyAnimatorUpdateListener r = new ViewPropertyAnimatorUpdateListener() { // from class: androidx.appcompat.app.WindowDecorActionBar.3
        @Override // androidx.core.view.ViewPropertyAnimatorUpdateListener
        public void onAnimationUpdate(View view) {
            ((View) WindowDecorActionBar.this.c.getParent()).invalidate();
        }
    };

    static boolean a(boolean z, boolean z2, boolean z3) {
        if (z3) {
            return true;
        }
        return !z && !z2;
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStopped() {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    public WindowDecorActionBar(Activity activity, boolean z) {
        this.v = activity;
        View decorView = activity.getWindow().getDecorView();
        a(decorView);
        if (!z) {
            this.f = decorView.findViewById(16908290);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        a(dialog.getWindow().getDecorView());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public WindowDecorActionBar(View view) {
        a(view);
    }

    private void a(View view) {
        this.b = (ActionBarOverlayLayout) view.findViewById(R.id.decor_content_parent);
        ActionBarOverlayLayout actionBarOverlayLayout = this.b;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        this.d = b(view.findViewById(R.id.action_bar));
        this.e = (ActionBarContextView) view.findViewById(R.id.action_context_bar);
        this.c = (ActionBarContainer) view.findViewById(R.id.action_bar_container);
        DecorToolbar decorToolbar = this.d;
        if (decorToolbar == null || this.e == null || this.c == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.a = decorToolbar.getContext();
        boolean z = (this.d.getDisplayOptions() & 4) != 0;
        if (z) {
            this.z = true;
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.a);
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || z);
        a(actionBarPolicy.hasEmbeddedTabs());
        TypedArray obtainStyledAttributes = this.a.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            setElevation(dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    private DecorToolbar b(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't make a decor toolbar out of ");
        sb.append(view != null ? view.getClass().getSimpleName() : "null");
        throw new IllegalStateException(sb.toString());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setElevation(float f) {
        ViewCompat.setElevation(this.c, f);
    }

    @Override // androidx.appcompat.app.ActionBar
    public float getElevation() {
        return ViewCompat.getElevation(this.c);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        a(ActionBarPolicy.get(this.a).hasEmbeddedTabs());
    }

    private void a(boolean z) {
        this.C = z;
        if (!this.C) {
            this.d.setEmbeddedTabView(null);
            this.c.setTabContainer(this.g);
        } else {
            this.c.setTabContainer(null);
            this.d.setEmbeddedTabView(this.g);
        }
        boolean z2 = true;
        boolean z3 = getNavigationMode() == 2;
        ScrollingTabContainerView scrollingTabContainerView = this.g;
        if (scrollingTabContainerView != null) {
            if (z3) {
                scrollingTabContainerView.setVisibility(0);
                ActionBarOverlayLayout actionBarOverlayLayout = this.b;
                if (actionBarOverlayLayout != null) {
                    ViewCompat.requestApplyInsets(actionBarOverlayLayout);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
        }
        this.d.setCollapsible(!this.C && z3);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.b;
        if (this.C || !z3) {
            z2 = false;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z2);
    }

    private void c() {
        if (this.g == null) {
            ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.a);
            if (this.C) {
                scrollingTabContainerView.setVisibility(0);
                this.d.setEmbeddedTabView(scrollingTabContainerView);
            } else {
                if (getNavigationMode() == 2) {
                    scrollingTabContainerView.setVisibility(0);
                    ActionBarOverlayLayout actionBarOverlayLayout = this.b;
                    if (actionBarOverlayLayout != null) {
                        ViewCompat.requestApplyInsets(actionBarOverlayLayout);
                    }
                } else {
                    scrollingTabContainerView.setVisibility(8);
                }
                this.c.setTabContainer(scrollingTabContainerView);
            }
            this.g = scrollingTabContainerView;
        }
    }

    void b() {
        ActionMode.Callback callback = this.j;
        if (callback != null) {
            callback.onDestroyActionMode(this.i);
            this.i = null;
            this.j = null;
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onWindowVisibilityChanged(int i) {
        this.D = i;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet;
        this.G = z;
        if (!z && (viewPropertyAnimatorCompatSet = this.n) != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.B.add(onMenuVisibilityListener);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.B.remove(onMenuVisibilityListener);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z != this.A) {
            this.A = z;
            int size = this.B.size();
            for (int i = 0; i < size; i++) {
                this.B.get(i).onMenuVisibilityChanged(z);
            }
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(i, this.d.getViewGroup(), false));
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
    public void setHomeButtonEnabled(boolean z) {
        this.d.setHomeButtonEnabled(z);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setTitle(int i) {
        setTitle(this.a.getString(i));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSubtitle(int i) {
        setSubtitle(this.a.getString(i));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        switch (this.d.getNavigationMode()) {
            case 1:
                this.d.setDropdownSelectedPosition(i);
                return;
            case 2:
                selectTab(this.w.get(i));
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeAllTabs() {
        d();
    }

    private void d() {
        if (this.x != null) {
            selectTab(null);
        }
        this.w.clear();
        ScrollingTabContainerView scrollingTabContainerView = this.g;
        if (scrollingTabContainerView != null) {
            scrollingTabContainerView.removeAllTabs();
        }
        this.y = -1;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setTitle(CharSequence charSequence) {
        this.d.setTitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setWindowTitle(CharSequence charSequence) {
        this.d.setWindowTitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean requestFocus() {
        ViewGroup viewGroup = this.d.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSubtitle(CharSequence charSequence) {
        this.d.setSubtitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayOptions(int i) {
        if ((i & 4) != 0) {
            this.z = true;
        }
        this.d.setDisplayOptions(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        int displayOptions = this.d.getDisplayOptions();
        if ((i2 & 4) != 0) {
            this.z = true;
        }
        this.d.setDisplayOptions((i & i2) | ((~i2) & displayOptions));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setBackgroundDrawable(Drawable drawable) {
        this.c.setPrimaryBackground(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setStackedBackgroundDrawable(Drawable drawable) {
        this.c.setStackedBackground(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public View getCustomView() {
        return this.d.getCustomView();
    }

    @Override // androidx.appcompat.app.ActionBar
    public CharSequence getTitle() {
        return this.d.getTitle();
    }

    @Override // androidx.appcompat.app.ActionBar
    public CharSequence getSubtitle() {
        return this.d.getSubtitle();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getNavigationMode() {
        return this.d.getNavigationMode();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getDisplayOptions() {
        return this.d.getDisplayOptions();
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionModeImpl actionModeImpl = this.h;
        if (actionModeImpl != null) {
            actionModeImpl.finish();
        }
        this.b.setHideOnContentScrollEnabled(false);
        this.e.killMode();
        ActionModeImpl actionModeImpl2 = new ActionModeImpl(this.e.getContext(), callback);
        if (!actionModeImpl2.dispatchOnCreate()) {
            return null;
        }
        this.h = actionModeImpl2;
        actionModeImpl2.invalidate();
        this.e.initForMode(actionModeImpl2);
        animateToMode(true);
        this.e.sendAccessibilityEvent(32);
        return actionModeImpl2;
    }

    private void a(ActionBar.Tab tab, int i) {
        TabImpl tabImpl = (TabImpl) tab;
        if (tabImpl.getCallback() != null) {
            tabImpl.setPosition(i);
            this.w.add(i, tabImpl);
            int size = this.w.size();
            while (true) {
                i++;
                if (i < size) {
                    this.w.get(i).setPosition(i);
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab) {
        addTab(tab, this.w.isEmpty());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i) {
        addTab(tab, i, this.w.isEmpty());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, boolean z) {
        c();
        this.g.addTab(tab, z);
        a(tab, this.w.size());
        if (z) {
            selectTab(tab);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        c();
        this.g.addTab(tab, i, z);
        a(tab, i);
        if (z) {
            selectTab(tab);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab newTab() {
        return new TabImpl();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeTab(ActionBar.Tab tab) {
        removeTabAt(tab.getPosition());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeTabAt(int i) {
        if (this.g != null) {
            TabImpl tabImpl = this.x;
            int position = tabImpl != null ? tabImpl.getPosition() : this.y;
            this.g.removeTabAt(i);
            TabImpl remove = this.w.remove(i);
            if (remove != null) {
                remove.setPosition(-1);
            }
            int size = this.w.size();
            for (int i2 = i; i2 < size; i2++) {
                this.w.get(i2).setPosition(i2);
            }
            if (position == i) {
                selectTab(this.w.isEmpty() ? null : this.w.get(Math.max(0, i - 1)));
            }
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void selectTab(ActionBar.Tab tab) {
        int i = -1;
        if (getNavigationMode() != 2) {
            if (tab != null) {
                i = tab.getPosition();
            }
            this.y = i;
            return;
        }
        FragmentTransaction disallowAddToBackStack = (!(this.v instanceof FragmentActivity) || this.d.getViewGroup().isInEditMode()) ? null : ((FragmentActivity) this.v).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        TabImpl tabImpl = this.x;
        if (tabImpl != tab) {
            ScrollingTabContainerView scrollingTabContainerView = this.g;
            if (tab != null) {
                i = tab.getPosition();
            }
            scrollingTabContainerView.setTabSelected(i);
            TabImpl tabImpl2 = this.x;
            if (tabImpl2 != null) {
                tabImpl2.getCallback().onTabUnselected(this.x, disallowAddToBackStack);
            }
            this.x = (TabImpl) tab;
            TabImpl tabImpl3 = this.x;
            if (tabImpl3 != null) {
                tabImpl3.getCallback().onTabSelected(this.x, disallowAddToBackStack);
            }
        } else if (tabImpl != null) {
            tabImpl.getCallback().onTabReselected(this.x, disallowAddToBackStack);
            this.g.animateToTab(tab.getPosition());
        }
        if (disallowAddToBackStack != null && !disallowAddToBackStack.isEmpty()) {
            disallowAddToBackStack.commit();
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        return this.x;
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getHeight() {
        return this.c.getHeight();
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void enableContentAnimations(boolean z) {
        this.k = z;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void show() {
        if (this.l) {
            this.l = false;
            b(false);
        }
    }

    private void e() {
        if (!this.E) {
            this.E = true;
            ActionBarOverlayLayout actionBarOverlayLayout = this.b;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(true);
            }
            b(false);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void showForSystem() {
        if (this.m) {
            this.m = false;
            b(true);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void hide() {
        if (!this.l) {
            this.l = true;
            b(false);
        }
    }

    private void f() {
        if (this.E) {
            this.E = false;
            ActionBarOverlayLayout actionBarOverlayLayout = this.b;
            if (actionBarOverlayLayout != null) {
                actionBarOverlayLayout.setShowingForActionMode(false);
            }
            b(false);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void hideForSystem() {
        if (!this.m) {
            this.m = true;
            b(true);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHideOnContentScrollEnabled(boolean z) {
        if (!z || this.b.isInOverlayMode()) {
            this.o = z;
            this.b.setHideOnContentScrollEnabled(z);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isHideOnContentScrollEnabled() {
        return this.b.isHideOnContentScrollEnabled();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getHideOffset() {
        return this.b.getActionBarHideOffset();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHideOffset(int i) {
        if (i == 0 || this.b.isInOverlayMode()) {
            this.b.setActionBarHideOffset(i);
            return;
        }
        throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
    }

    private void b(boolean z) {
        if (a(this.l, this.m, this.E)) {
            if (!this.F) {
                this.F = true;
                doShow(z);
            }
        } else if (this.F) {
            this.F = false;
            doHide(z);
        }
    }

    public void doShow(boolean z) {
        View view;
        View view2;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.n;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        this.c.setVisibility(0);
        if (this.D != 0 || (!this.G && !z)) {
            this.c.setAlpha(1.0f);
            this.c.setTranslationY(0.0f);
            if (this.k && (view = this.f) != null) {
                view.setTranslationY(0.0f);
            }
            this.q.onAnimationEnd(null);
        } else {
            this.c.setTranslationY(0.0f);
            float f = -this.c.getHeight();
            if (z) {
                int[] iArr = {0, 0};
                this.c.getLocationInWindow(iArr);
                f -= iArr[1];
            }
            this.c.setTranslationY(f);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat translationY = ViewCompat.animate(this.c).translationY(0.0f);
            translationY.setUpdateListener(this.r);
            viewPropertyAnimatorCompatSet2.play(translationY);
            if (this.k && (view2 = this.f) != null) {
                view2.setTranslationY(f);
                viewPropertyAnimatorCompatSet2.play(ViewCompat.animate(this.f).translationY(0.0f));
            }
            viewPropertyAnimatorCompatSet2.setInterpolator(t);
            viewPropertyAnimatorCompatSet2.setDuration(250L);
            viewPropertyAnimatorCompatSet2.setListener(this.q);
            this.n = viewPropertyAnimatorCompatSet2;
            viewPropertyAnimatorCompatSet2.start();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.b;
        if (actionBarOverlayLayout != null) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
    }

    public void doHide(boolean z) {
        View view;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.n;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
        }
        if (this.D != 0 || (!this.G && !z)) {
            this.p.onAnimationEnd(null);
            return;
        }
        this.c.setAlpha(1.0f);
        this.c.setTransitioning(true);
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
        float f = -this.c.getHeight();
        if (z) {
            int[] iArr = {0, 0};
            this.c.getLocationInWindow(iArr);
            f -= iArr[1];
        }
        ViewPropertyAnimatorCompat translationY = ViewCompat.animate(this.c).translationY(f);
        translationY.setUpdateListener(this.r);
        viewPropertyAnimatorCompatSet2.play(translationY);
        if (this.k && (view = this.f) != null) {
            viewPropertyAnimatorCompatSet2.play(ViewCompat.animate(view).translationY(f));
        }
        viewPropertyAnimatorCompatSet2.setInterpolator(s);
        viewPropertyAnimatorCompatSet2.setDuration(250L);
        viewPropertyAnimatorCompatSet2.setListener(this.p);
        this.n = viewPropertyAnimatorCompatSet2;
        viewPropertyAnimatorCompatSet2.start();
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isShowing() {
        int height = getHeight();
        return this.F && (height == 0 || getHideOffset() < height);
    }

    public void animateToMode(boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
        if (z) {
            e();
        } else {
            f();
        }
        if (g()) {
            if (z) {
                viewPropertyAnimatorCompat = this.d.setupAnimatorToVisibility(4, 100L);
                viewPropertyAnimatorCompat2 = this.e.setupAnimatorToVisibility(0, 200L);
            } else {
                viewPropertyAnimatorCompat2 = this.d.setupAnimatorToVisibility(0, 200L);
                viewPropertyAnimatorCompat = this.e.setupAnimatorToVisibility(8, 100L);
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat, viewPropertyAnimatorCompat2);
            viewPropertyAnimatorCompatSet.start();
        } else if (z) {
            this.d.setVisibility(4);
            this.e.setVisibility(0);
        } else {
            this.d.setVisibility(0);
            this.e.setVisibility(8);
        }
    }

    private boolean g() {
        return ViewCompat.isLaidOut(this.c);
    }

    @Override // androidx.appcompat.app.ActionBar
    public Context getThemedContext() {
        if (this.u == null) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.u = new ContextThemeWrapper(this.a, i);
            } else {
                this.u = this.a;
            }
        }
        return this.u;
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isTitleTruncated() {
        DecorToolbar decorToolbar = this.d;
        return decorToolbar != null && decorToolbar.isTitleTruncated();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.d.setNavigationIcon(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.d.setNavigationIcon(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.d.setNavigationContentDescription(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.d.setNavigationContentDescription(i);
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStarted() {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.n;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
            this.n = null;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean collapseActionView() {
        DecorToolbar decorToolbar = this.d;
        if (decorToolbar == null || !decorToolbar.hasExpandedActionView()) {
            return false;
        }
        this.d.collapseActionView();
        return true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context b;
        private final MenuBuilder c;
        private ActionMode.Callback d;
        private WeakReference<View> e;

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.b = context;
            this.d = callback;
            this.c = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.c.setCallback(this);
        }

        @Override // androidx.appcompat.view.ActionMode
        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.b);
        }

        @Override // androidx.appcompat.view.ActionMode
        public Menu getMenu() {
            return this.c;
        }

        @Override // androidx.appcompat.view.ActionMode
        public void finish() {
            if (WindowDecorActionBar.this.h == this) {
                if (!WindowDecorActionBar.a(WindowDecorActionBar.this.l, WindowDecorActionBar.this.m, false)) {
                    WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
                    windowDecorActionBar.i = this;
                    windowDecorActionBar.j = this.d;
                } else {
                    this.d.onDestroyActionMode(this);
                }
                this.d = null;
                WindowDecorActionBar.this.animateToMode(false);
                WindowDecorActionBar.this.e.closeMode();
                WindowDecorActionBar.this.d.getViewGroup().sendAccessibilityEvent(32);
                WindowDecorActionBar.this.b.setHideOnContentScrollEnabled(WindowDecorActionBar.this.o);
                WindowDecorActionBar.this.h = null;
            }
        }

        @Override // androidx.appcompat.view.ActionMode
        public void invalidate() {
            if (WindowDecorActionBar.this.h == this) {
                this.c.stopDispatchingItemsChanged();
                try {
                    this.d.onPrepareActionMode(this, this.c);
                } finally {
                    this.c.startDispatchingItemsChanged();
                }
            }
        }

        public boolean dispatchOnCreate() {
            this.c.stopDispatchingItemsChanged();
            try {
                return this.d.onCreateActionMode(this, this.c);
            } finally {
                this.c.startDispatchingItemsChanged();
            }
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setCustomView(View view) {
            WindowDecorActionBar.this.e.setCustomView(view);
            this.e = new WeakReference<>(view);
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.e.setSubtitle(charSequence);
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.e.setTitle(charSequence);
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setTitle(int i) {
            setTitle(WindowDecorActionBar.this.a.getResources().getString(i));
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setSubtitle(int i) {
            setSubtitle(WindowDecorActionBar.this.a.getResources().getString(i));
        }

        @Override // androidx.appcompat.view.ActionMode
        public CharSequence getTitle() {
            return WindowDecorActionBar.this.e.getTitle();
        }

        @Override // androidx.appcompat.view.ActionMode
        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.e.getSubtitle();
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setTitleOptionalHint(boolean z) {
            super.setTitleOptionalHint(z);
            WindowDecorActionBar.this.e.setTitleOptional(z);
        }

        @Override // androidx.appcompat.view.ActionMode
        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.e.isTitleOptional();
        }

        @Override // androidx.appcompat.view.ActionMode
        public View getCustomView() {
            WeakReference<View> weakReference = this.e;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            ActionMode.Callback callback = this.d;
            if (callback != null) {
                return callback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.d == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
            if (this.d != null) {
                invalidate();
                WindowDecorActionBar.this.e.showOverflowMenu();
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public class TabImpl extends ActionBar.Tab {
        private ActionBar.TabListener b;
        private Object c;
        private Drawable d;
        private CharSequence e;
        private CharSequence f;
        private int g = -1;
        private View h;

        public TabImpl() {
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public Object getTag() {
            return this.c;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setTag(Object obj) {
            this.c = obj;
            return this;
        }

        public ActionBar.TabListener getCallback() {
            return this.b;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
            this.b = tabListener;
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public View getCustomView() {
            return this.h;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(View view) {
            this.h = view;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(int i) {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(i, (ViewGroup) null));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public Drawable getIcon() {
            return this.d;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public int getPosition() {
            return this.g;
        }

        public void setPosition(int i) {
            this.g = i;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public CharSequence getText() {
            return this.e;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setIcon(Drawable drawable) {
            this.d = drawable;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setIcon(int i) {
            return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.a, i));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setText(CharSequence charSequence) {
            this.e = charSequence;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setText(int i) {
            return setText(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(int i) {
            return setContentDescription(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(CharSequence charSequence) {
            this.f = charSequence;
            if (this.g >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.g);
            }
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public CharSequence getContentDescription() {
            return this.f;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(View view) {
        this.d.setCustomView(view);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        this.d.setCustomView(view);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.d.setDropdownParams(spinnerAdapter, new c(onNavigationListener));
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getSelectedNavigationIndex() {
        switch (this.d.getNavigationMode()) {
            case 1:
                return this.d.getDropdownSelectedPosition();
            case 2:
                TabImpl tabImpl = this.x;
                if (tabImpl != null) {
                    return tabImpl.getPosition();
                }
                return -1;
            default:
                return -1;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getNavigationItemCount() {
        switch (this.d.getNavigationMode()) {
            case 1:
                return this.d.getDropdownItemCount();
            case 2:
                return this.w.size();
            default:
                return 0;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getTabCount() {
        return this.w.size();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setNavigationMode(int i) {
        ActionBarOverlayLayout actionBarOverlayLayout;
        int navigationMode = this.d.getNavigationMode();
        if (navigationMode == 2) {
            this.y = getSelectedNavigationIndex();
            selectTab(null);
            this.g.setVisibility(8);
        }
        if (!(navigationMode == i || this.C || (actionBarOverlayLayout = this.b) == null)) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
        this.d.setNavigationMode(i);
        boolean z = false;
        if (i == 2) {
            c();
            this.g.setVisibility(0);
            int i2 = this.y;
            if (i2 != -1) {
                setSelectedNavigationItem(i2);
                this.y = -1;
            }
        }
        this.d.setCollapsible(i == 2 && !this.C);
        ActionBarOverlayLayout actionBarOverlayLayout2 = this.b;
        if (i == 2 && !this.C) {
            z = true;
        }
        actionBarOverlayLayout2.setHasNonEmbeddedTabs(z);
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab getTabAt(int i) {
        return this.w.get(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setIcon(int i) {
        this.d.setIcon(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setIcon(Drawable drawable) {
        this.d.setIcon(drawable);
    }

    public boolean hasIcon() {
        return this.d.hasIcon();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setLogo(int i) {
        this.d.setLogo(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setLogo(Drawable drawable) {
        this.d.setLogo(drawable);
    }

    public boolean hasLogo() {
        return this.d.hasLogo();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        if (!this.z) {
            setDisplayHomeAsUpEnabled(z);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menu;
        ActionModeImpl actionModeImpl = this.h;
        if (actionModeImpl == null || (menu = actionModeImpl.getMenu()) == null) {
            return false;
        }
        boolean z = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z = false;
        }
        menu.setQwertyMode(z);
        return menu.performShortcut(i, keyEvent, 0);
    }
}
