package androidx.appcompat.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.a;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/* loaded from: classes.dex */
public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    boolean a;
    View.OnClickListener b;
    private final Delegate c;
    private final DrawerLayout d;
    private DrawerArrowDrawable e;
    private boolean f;
    private Drawable g;
    private boolean h;
    private final int i;
    private final int j;
    private boolean k;

    /* loaded from: classes.dex */
    public interface Delegate {
        Context getActionBarThemedContext();

        Drawable getThemeUpIndicator();

        boolean isNavigationVisible();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    /* loaded from: classes.dex */
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerStateChanged(int i) {
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @StringRes int i, @StringRes int i2) {
        this(activity, null, drawerLayout, null, i, i2);
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int i, @StringRes int i2) {
        this(activity, toolbar, drawerLayout, null, i, i2);
    }

    ActionBarDrawerToggle(Activity activity, Toolbar toolbar, DrawerLayout drawerLayout, DrawerArrowDrawable drawerArrowDrawable, @StringRes int i, @StringRes int i2) {
        this.f = true;
        this.a = true;
        this.k = false;
        if (toolbar != null) {
            this.c = new b(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.app.ActionBarDrawerToggle.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ActionBarDrawerToggle.this.a) {
                        ActionBarDrawerToggle.this.a();
                    } else if (ActionBarDrawerToggle.this.b != null) {
                        ActionBarDrawerToggle.this.b.onClick(view);
                    }
                }
            });
        } else if (activity instanceof DelegateProvider) {
            this.c = ((DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.c = new a(activity);
        }
        this.d = drawerLayout;
        this.i = i;
        this.j = i2;
        if (drawerArrowDrawable == null) {
            this.e = new DrawerArrowDrawable(this.c.getActionBarThemedContext());
        } else {
            this.e = drawerArrowDrawable;
        }
        this.g = b();
    }

    public void syncState() {
        if (this.d.isDrawerOpen(GravityCompat.START)) {
            a(1.0f);
        } else {
            a(0.0f);
        }
        if (this.a) {
            a(this.e, this.d.isDrawerOpen(GravityCompat.START) ? this.j : this.i);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.h) {
            this.g = b();
        }
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.a) {
            return false;
        }
        a();
        return true;
    }

    void a() {
        int drawerLockMode = this.d.getDrawerLockMode(GravityCompat.START);
        if (this.d.isDrawerVisible(GravityCompat.START) && drawerLockMode != 2) {
            this.d.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != 1) {
            this.d.openDrawer(GravityCompat.START);
        }
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.g = b();
            this.h = false;
        } else {
            this.g = drawable;
            this.h = true;
        }
        if (!this.a) {
            a(this.g, 0);
        }
    }

    public void setHomeAsUpIndicator(int i) {
        setHomeAsUpIndicator(i != 0 ? this.d.getResources().getDrawable(i) : null);
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.a;
    }

    public void setDrawerIndicatorEnabled(boolean z) {
        if (z != this.a) {
            if (z) {
                a(this.e, this.d.isDrawerOpen(GravityCompat.START) ? this.j : this.i);
            } else {
                a(this.g, 0);
            }
            this.a = z;
        }
    }

    @NonNull
    public DrawerArrowDrawable getDrawerArrowDrawable() {
        return this.e;
    }

    public void setDrawerArrowDrawable(@NonNull DrawerArrowDrawable drawerArrowDrawable) {
        this.e = drawerArrowDrawable;
        syncState();
    }

    public void setDrawerSlideAnimationEnabled(boolean z) {
        this.f = z;
        if (!z) {
            a(0.0f);
        }
    }

    public boolean isDrawerSlideAnimationEnabled() {
        return this.f;
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerSlide(View view, float f) {
        if (this.f) {
            a(Math.min(1.0f, Math.max(0.0f, f)));
        } else {
            a(0.0f);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerOpened(View view) {
        a(1.0f);
        if (this.a) {
            a(this.j);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerClosed(View view) {
        a(0.0f);
        if (this.a) {
            a(this.i);
        }
    }

    public View.OnClickListener getToolbarNavigationClickListener() {
        return this.b;
    }

    public void setToolbarNavigationClickListener(View.OnClickListener onClickListener) {
        this.b = onClickListener;
    }

    void a(Drawable drawable, int i) {
        if (!this.k && !this.c.isNavigationVisible()) {
            Log.w("ActionBarDrawerToggle", "DrawerToggle may not show up because NavigationIcon is not visible. You may need to call actionbar.setDisplayHomeAsUpEnabled(true);");
            this.k = true;
        }
        this.c.setActionBarUpIndicator(drawable, i);
    }

    void a(int i) {
        this.c.setActionBarDescription(i);
    }

    Drawable b() {
        return this.c.getThemeUpIndicator();
    }

    private void a(float f) {
        if (f == 1.0f) {
            this.e.setVerticalMirror(true);
        } else if (f == 0.0f) {
            this.e.setVerticalMirror(false);
        }
        this.e.setProgress(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a implements Delegate {
        private final Activity a;
        private a.C0002a b;

        a(Activity activity) {
            this.a = activity;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            if (Build.VERSION.SDK_INT < 18) {
                return a.a(this.a);
            }
            TypedArray obtainStyledAttributes = getActionBarThemedContext().obtainStyledAttributes(null, new int[]{16843531}, 16843470, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                return actionBar.getThemedContext();
            }
            return this.a;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            ActionBar actionBar = this.a.getActionBar();
            return (actionBar == null || (actionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 18) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(i);
                return;
            }
            actionBar.setDisplayShowHomeEnabled(true);
            this.b = a.a(this.a, drawable, i);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            if (Build.VERSION.SDK_INT >= 18) {
                ActionBar actionBar = this.a.getActionBar();
                if (actionBar != null) {
                    actionBar.setHomeActionContentDescription(i);
                    return;
                }
                return;
            }
            this.b = a.a(this.b, this.a, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b implements Delegate {
        final Toolbar a;
        final Drawable b;
        final CharSequence c;

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            return true;
        }

        b(Toolbar toolbar) {
            this.a = toolbar;
            this.b = toolbar.getNavigationIcon();
            this.c = toolbar.getNavigationContentDescription();
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, @StringRes int i) {
            this.a.setNavigationIcon(drawable);
            setActionBarDescription(i);
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(@StringRes int i) {
            if (i == 0) {
                this.a.setNavigationContentDescription(this.c);
            } else {
                this.a.setNavigationContentDescription(i);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            return this.b;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return this.a.getContext();
        }
    }
}
