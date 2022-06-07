package androidx.legacy.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import java.lang.reflect.Method;

@Deprecated
/* loaded from: classes.dex */
public class ActionBarDrawerToggle implements DrawerLayout.DrawerListener {
    private static final int[] b = {16843531};
    final Activity a;
    private final Delegate c;
    private final DrawerLayout d;
    private boolean e;
    private boolean f;
    private Drawable g;
    private Drawable h;
    private b i;
    private final int j;
    private final int k;
    private final int l;
    private a m;

    @Deprecated
    /* loaded from: classes.dex */
    public interface Delegate {
        @Nullable
        Drawable getThemeUpIndicator();

        void setActionBarDescription(@StringRes int i);

        void setActionBarUpIndicator(Drawable drawable, @StringRes int i);
    }

    @Deprecated
    /* loaded from: classes.dex */
    public interface DelegateProvider {
        @Nullable
        Delegate getDrawerToggleDelegate();
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerStateChanged(int i) {
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, @DrawableRes int i, @StringRes int i2, @StringRes int i3) {
        this(activity, drawerLayout, !a(activity), i, i2, i3);
    }

    private static boolean a(Context context) {
        return context.getApplicationInfo().targetSdkVersion >= 21 && Build.VERSION.SDK_INT >= 21;
    }

    public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, boolean z, @DrawableRes int i, @StringRes int i2, @StringRes int i3) {
        this.e = true;
        this.a = activity;
        if (activity instanceof DelegateProvider) {
            this.c = ((DelegateProvider) activity).getDrawerToggleDelegate();
        } else {
            this.c = null;
        }
        this.d = drawerLayout;
        this.j = i;
        this.k = i2;
        this.l = i3;
        this.g = a();
        this.h = ContextCompat.getDrawable(activity, i);
        this.i = new b(this.h);
        this.i.b(z ? 0.33333334f : 0.0f);
    }

    public void syncState() {
        if (this.d.isDrawerOpen(GravityCompat.START)) {
            this.i.a(1.0f);
        } else {
            this.i.a(0.0f);
        }
        if (this.e) {
            a(this.i, this.d.isDrawerOpen(GravityCompat.START) ? this.l : this.k);
        }
    }

    public void setHomeAsUpIndicator(Drawable drawable) {
        if (drawable == null) {
            this.g = a();
            this.f = false;
        } else {
            this.g = drawable;
            this.f = true;
        }
        if (!this.e) {
            a(this.g, 0);
        }
    }

    public void setHomeAsUpIndicator(int i) {
        setHomeAsUpIndicator(i != 0 ? ContextCompat.getDrawable(this.a, i) : null);
    }

    public void setDrawerIndicatorEnabled(boolean z) {
        if (z != this.e) {
            if (z) {
                a(this.i, this.d.isDrawerOpen(GravityCompat.START) ? this.l : this.k);
            } else {
                a(this.g, 0);
            }
            this.e = z;
        }
    }

    public boolean isDrawerIndicatorEnabled() {
        return this.e;
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.f) {
            this.g = a();
        }
        this.h = ContextCompat.getDrawable(this.a, this.j);
        syncState();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332 || !this.e) {
            return false;
        }
        if (this.d.isDrawerVisible(GravityCompat.START)) {
            this.d.closeDrawer(GravityCompat.START);
            return true;
        }
        this.d.openDrawer(GravityCompat.START);
        return true;
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerSlide(View view, float f) {
        float f2;
        float a2 = this.i.a();
        if (f > 0.5f) {
            f2 = Math.max(a2, Math.max(0.0f, f - 0.5f) * 2.0f);
        } else {
            f2 = Math.min(a2, f * 2.0f);
        }
        this.i.a(f2);
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerOpened(View view) {
        this.i.a(1.0f);
        if (this.e) {
            a(this.l);
        }
    }

    @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
    public void onDrawerClosed(View view) {
        this.i.a(0.0f);
        if (this.e) {
            a(this.k);
        }
    }

    private Drawable a() {
        Context context;
        Delegate delegate = this.c;
        if (delegate != null) {
            return delegate.getThemeUpIndicator();
        }
        if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                context = actionBar.getThemedContext();
            } else {
                context = this.a;
            }
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, b, 16843470, 0);
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            return drawable;
        }
        TypedArray obtainStyledAttributes2 = this.a.obtainStyledAttributes(b);
        Drawable drawable2 = obtainStyledAttributes2.getDrawable(0);
        obtainStyledAttributes2.recycle();
        return drawable2;
    }

    private void a(Drawable drawable, int i) {
        Delegate delegate = this.c;
        if (delegate != null) {
            delegate.setActionBarUpIndicator(drawable, i);
        } else if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(drawable);
                actionBar.setHomeActionContentDescription(i);
            }
        } else {
            if (this.m == null) {
                this.m = new a(this.a);
            }
            if (this.m.a != null) {
                try {
                    ActionBar actionBar2 = this.a.getActionBar();
                    this.m.a.invoke(actionBar2, drawable);
                    this.m.b.invoke(actionBar2, Integer.valueOf(i));
                } catch (Exception e) {
                    Log.w("ActionBarDrawerToggle", "Couldn't set home-as-up indicator via JB-MR2 API", e);
                }
            } else if (this.m.c != null) {
                this.m.c.setImageDrawable(drawable);
            } else {
                Log.w("ActionBarDrawerToggle", "Couldn't set home-as-up indicator");
            }
        }
    }

    private void a(int i) {
        Delegate delegate = this.c;
        if (delegate != null) {
            delegate.setActionBarDescription(i);
        } else if (Build.VERSION.SDK_INT >= 18) {
            ActionBar actionBar = this.a.getActionBar();
            if (actionBar != null) {
                actionBar.setHomeActionContentDescription(i);
            }
        } else {
            if (this.m == null) {
                this.m = new a(this.a);
            }
            if (this.m.a != null) {
                try {
                    ActionBar actionBar2 = this.a.getActionBar();
                    this.m.b.invoke(actionBar2, Integer.valueOf(i));
                    actionBar2.setSubtitle(actionBar2.getSubtitle());
                } catch (Exception e) {
                    Log.w("ActionBarDrawerToggle", "Couldn't set content description via JB-MR2 API", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        Method a;
        Method b;
        ImageView c;

        a(Activity activity) {
            try {
                this.a = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.b = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
            } catch (NoSuchMethodException unused) {
                View findViewById = activity.findViewById(16908332);
                if (findViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
                    if (viewGroup.getChildCount() == 2) {
                        View childAt = viewGroup.getChildAt(0);
                        View childAt2 = childAt.getId() != 16908332 ? childAt : viewGroup.getChildAt(1);
                        if (childAt2 instanceof ImageView) {
                            this.c = (ImageView) childAt2;
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class b extends InsetDrawable implements Drawable.Callback {
        private final boolean b;
        private final Rect c;
        private float d;
        private float e;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(Drawable drawable) {
            super(drawable, 0);
            boolean z = false;
            this.b = Build.VERSION.SDK_INT > 18 ? true : z;
            this.c = new Rect();
        }

        public void a(float f) {
            this.d = f;
            invalidateSelf();
        }

        public float a() {
            return this.d;
        }

        public void b(float f) {
            this.e = f;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(@NonNull Canvas canvas) {
            copyBounds(this.c);
            canvas.save();
            int i = 1;
            boolean z = ViewCompat.getLayoutDirection(ActionBarDrawerToggle.this.a.getWindow().getDecorView()) == 1;
            if (z) {
                i = -1;
            }
            float width = this.c.width();
            canvas.translate((-this.e) * width * this.d * i, 0.0f);
            if (z && !this.b) {
                canvas.translate(width, 0.0f);
                canvas.scale(-1.0f, 1.0f);
            }
            super.draw(canvas);
            canvas.restore();
        }
    }
}
