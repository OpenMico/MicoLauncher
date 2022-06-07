package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.uc.crashsdk.export.LogType;
import java.lang.Thread;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class AppCompatDelegateImpl extends AppCompatDelegate implements LayoutInflater.Factory2, MenuBuilder.Callback {
    private static final SimpleArrayMap<String, Integer> u = new SimpleArrayMap<>();
    private static final boolean v;
    private static final int[] w;
    private static final boolean x;
    private static final boolean y;
    private static boolean z;
    private h A;
    private CharSequence B;
    private DecorContentParent C;
    private b D;
    private n E;
    private boolean F;
    private boolean G;
    private TextView H;
    private View I;
    private boolean J;
    private boolean K;
    private boolean L;
    private PanelFeatureState[] M;
    private PanelFeatureState N;
    private boolean O;
    private boolean P;
    private boolean Q;
    private boolean R;
    private int S;
    private int T;
    private boolean U;
    private boolean V;
    private j W;
    private j X;
    private final Runnable Y;
    private boolean Z;
    final Object a;
    private Rect aa;
    private Rect ab;
    private AppCompatViewInflater ac;
    private b ad;
    final Context b;
    Window c;
    final AppCompatCallback d;
    ActionBar e;
    MenuInflater f;
    ActionMode g;
    ActionBarContextView h;
    PopupWindow i;
    Runnable j;
    ViewPropertyAnimatorCompat k;
    ViewGroup l;
    boolean m;
    boolean n;
    boolean o;
    boolean p;
    boolean q;
    boolean r;
    boolean s;
    int t;

    void a(ViewGroup viewGroup) {
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onSaveInstanceState(Bundle bundle) {
    }

    static {
        boolean z2 = false;
        v = Build.VERSION.SDK_INT < 21;
        w = new int[]{16842836};
        x = !"robolectric".equals(Build.FINGERPRINT);
        if (Build.VERSION.SDK_INT >= 17) {
            z2 = true;
        }
        y = z2;
        if (v && !z) {
            final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.1
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
                    if (a(th)) {
                        Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
                        notFoundException.initCause(th.getCause());
                        notFoundException.setStackTrace(th.getStackTrace());
                        defaultUncaughtExceptionHandler.uncaughtException(thread, notFoundException);
                        return;
                    }
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th);
                }

                private boolean a(Throwable th) {
                    String message;
                    if (!(th instanceof Resources.NotFoundException) || (message = th.getMessage()) == null) {
                        return false;
                    }
                    return message.contains("drawable") || message.contains("Drawable");
                }
            });
            z = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatDelegateImpl(Activity activity, AppCompatCallback appCompatCallback) {
        this(activity, null, appCompatCallback, activity);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatDelegateImpl(Dialog dialog, AppCompatCallback appCompatCallback) {
        this(dialog.getContext(), dialog.getWindow(), appCompatCallback, dialog);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this(context, window, appCompatCallback, context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback appCompatCallback) {
        this(context, null, appCompatCallback, activity);
    }

    private AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback, Object obj) {
        Integer num;
        AppCompatActivity o;
        this.k = null;
        this.F = true;
        this.S = -100;
        this.Y = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.2
            @Override // java.lang.Runnable
            public void run() {
                if ((AppCompatDelegateImpl.this.t & 1) != 0) {
                    AppCompatDelegateImpl.this.d(0);
                }
                if ((AppCompatDelegateImpl.this.t & 4096) != 0) {
                    AppCompatDelegateImpl.this.d(108);
                }
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                appCompatDelegateImpl.s = false;
                appCompatDelegateImpl.t = 0;
            }
        };
        this.b = context;
        this.d = appCompatCallback;
        this.a = obj;
        if (this.S == -100 && (this.a instanceof Dialog) && (o = o()) != null) {
            this.S = o.getDelegate().getLocalNightMode();
        }
        if (this.S == -100 && (num = u.get(this.a.getClass().getName())) != null) {
            this.S = num.intValue();
            u.remove(this.a.getClass().getName());
        }
        if (window != null) {
            a(window);
        }
        AppCompatDrawableManager.preload();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @NonNull
    @CallSuper
    public Context attachBaseContext2(@NonNull Context context) {
        this.P = true;
        int a2 = a(context, q());
        Configuration configuration = null;
        if (y && (context instanceof ContextThemeWrapper)) {
            try {
                l.a((ContextThemeWrapper) context, a(context, a2, (Configuration) null));
                return context;
            } catch (IllegalStateException unused) {
            }
        }
        if (context instanceof androidx.appcompat.view.ContextThemeWrapper) {
            try {
                ((androidx.appcompat.view.ContextThemeWrapper) context).applyOverrideConfiguration(a(context, a2, (Configuration) null));
                return context;
            } catch (IllegalStateException unused2) {
            }
        }
        if (!x) {
            return super.attachBaseContext2(context);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            Configuration configuration2 = new Configuration();
            configuration2.uiMode = -1;
            configuration2.fontScale = 0.0f;
            Configuration configuration3 = d.a(context, configuration2).getResources().getConfiguration();
            Configuration configuration4 = context.getResources().getConfiguration();
            configuration3.uiMode = configuration4.uiMode;
            if (!configuration3.equals(configuration4)) {
                configuration = a(configuration3, configuration4);
            }
        }
        Configuration a3 = a(context, a2, configuration);
        androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, R.style.Theme_AppCompat_Empty);
        contextThemeWrapper.applyOverrideConfiguration(a3);
        boolean z2 = false;
        try {
            if (context.getTheme() != null) {
                z2 = true;
            }
        } catch (NullPointerException unused3) {
        }
        if (z2) {
            ResourcesCompat.ThemeCompat.rebase(contextThemeWrapper.getTheme());
        }
        return super.attachBaseContext2(contextThemeWrapper);
    }

    @RequiresApi(17)
    /* loaded from: classes.dex */
    private static class l {
        static void a(ContextThemeWrapper contextThemeWrapper, Configuration configuration) {
            contextThemeWrapper.applyOverrideConfiguration(configuration);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onCreate(Bundle bundle) {
        this.P = true;
        b(false);
        k();
        Object obj = this.a;
        if (obj instanceof Activity) {
            String str = null;
            try {
                str = NavUtils.getParentActivityName((Activity) obj);
            } catch (IllegalArgumentException unused) {
            }
            if (str != null) {
                ActionBar a2 = a();
                if (a2 == null) {
                    this.Z = true;
                } else {
                    a2.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            a(this);
        }
        this.Q = true;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onPostCreate(Bundle bundle) {
        l();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public ActionBar getSupportActionBar() {
        i();
        return this.e;
    }

    final ActionBar a() {
        return this.e;
    }

    final Window.Callback b() {
        return this.c.getCallback();
    }

    private void i() {
        l();
        if (this.m && this.e == null) {
            Object obj = this.a;
            if (obj instanceof Activity) {
                this.e = new WindowDecorActionBar((Activity) obj, this.n);
            } else if (obj instanceof Dialog) {
                this.e = new WindowDecorActionBar((Dialog) obj);
            }
            ActionBar actionBar = this.e;
            if (actionBar != null) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(this.Z);
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setSupportActionBar(Toolbar toolbar) {
        if (this.a instanceof Activity) {
            ActionBar supportActionBar = getSupportActionBar();
            if (!(supportActionBar instanceof WindowDecorActionBar)) {
                this.f = null;
                if (supportActionBar != null) {
                    supportActionBar.a();
                }
                if (toolbar != null) {
                    e eVar = new e(toolbar, d(), this.A);
                    this.e = eVar;
                    this.c.setCallback(eVar.b());
                } else {
                    this.e = null;
                    this.c.setCallback(this.A);
                }
                invalidateOptionsMenu();
                return;
            }
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
    }

    final Context c() {
        ActionBar supportActionBar = getSupportActionBar();
        Context themedContext = supportActionBar != null ? supportActionBar.getThemedContext() : null;
        return themedContext == null ? this.b : themedContext;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public MenuInflater getMenuInflater() {
        if (this.f == null) {
            i();
            ActionBar actionBar = this.e;
            this.f = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.b);
        }
        return this.f;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @Nullable
    public <T extends View> T findViewById(@IdRes int i2) {
        l();
        return (T) this.c.findViewById(i2);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onConfigurationChanged(Configuration configuration) {
        ActionBar supportActionBar;
        if (this.m && this.G && (supportActionBar = getSupportActionBar()) != null) {
            supportActionBar.onConfigurationChanged(configuration);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.b);
        b(false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onStart() {
        this.R = true;
        applyDayNight();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onStop() {
        this.R = false;
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(false);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onPostResume() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(View view) {
        l();
        ViewGroup viewGroup = (ViewGroup) this.l.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.A.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(int i2) {
        l();
        ViewGroup viewGroup = (ViewGroup) this.l.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.b).inflate(i2, viewGroup);
        this.A.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        l();
        ViewGroup viewGroup = (ViewGroup) this.l.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.A.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        l();
        ((ViewGroup) this.l.findViewById(16908290)).addView(view, layoutParams);
        this.A.getWrapped().onContentChanged();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x005b  */
    @Override // androidx.appcompat.app.AppCompatDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDestroy() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.a
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L_0x0009
            b(r3)
        L_0x0009:
            boolean r0 = r3.s
            if (r0 == 0) goto L_0x0018
            android.view.Window r0 = r3.c
            android.view.View r0 = r0.getDecorView()
            java.lang.Runnable r1 = r3.Y
            r0.removeCallbacks(r1)
        L_0x0018:
            r0 = 0
            r3.R = r0
            r0 = 1
            r3.r = r0
            int r0 = r3.S
            r1 = -100
            if (r0 == r1) goto L_0x0048
            java.lang.Object r0 = r3.a
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L_0x0048
            android.app.Activity r0 = (android.app.Activity) r0
            boolean r0 = r0.isChangingConfigurations()
            if (r0 == 0) goto L_0x0048
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = androidx.appcompat.app.AppCompatDelegateImpl.u
            java.lang.Object r1 = r3.a
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            int r2 = r3.S
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            goto L_0x0057
        L_0x0048:
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = androidx.appcompat.app.AppCompatDelegateImpl.u
            java.lang.Object r1 = r3.a
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            r0.remove(r1)
        L_0x0057:
            androidx.appcompat.app.ActionBar r0 = r3.e
            if (r0 == 0) goto L_0x005e
            r0.a()
        L_0x005e:
            r3.j()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.onDestroy():void");
    }

    private void j() {
        j jVar = this.W;
        if (jVar != null) {
            jVar.e();
        }
        j jVar2 = this.X;
        if (jVar2 != null) {
            jVar2.e();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setTheme(@StyleRes int i2) {
        this.T = i2;
    }

    private void k() {
        if (this.c == null) {
            Object obj = this.a;
            if (obj instanceof Activity) {
                a(((Activity) obj).getWindow());
            }
        }
        if (this.c == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    private void a(@NonNull Window window) {
        if (this.c == null) {
            Window.Callback callback = window.getCallback();
            if (!(callback instanceof h)) {
                this.A = new h(callback);
                window.setCallback(this.A);
                TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.b, (AttributeSet) null, w);
                Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(0);
                if (drawableIfKnown != null) {
                    window.setBackgroundDrawable(drawableIfKnown);
                }
                obtainStyledAttributes.recycle();
                this.c = window;
                return;
            }
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    private void l() {
        if (!this.G) {
            this.l = m();
            CharSequence d2 = d();
            if (!TextUtils.isEmpty(d2)) {
                DecorContentParent decorContentParent = this.C;
                if (decorContentParent != null) {
                    decorContentParent.setWindowTitle(d2);
                } else if (a() != null) {
                    a().setWindowTitle(d2);
                } else {
                    TextView textView = this.H;
                    if (textView != null) {
                        textView.setText(d2);
                    }
                }
            }
            n();
            a(this.l);
            this.G = true;
            PanelFeatureState a2 = a(0, false);
            if (this.r) {
                return;
            }
            if (a2 == null || a2.j == null) {
                e(108);
            }
        }
    }

    private ViewGroup m() {
        ViewGroup viewGroup;
        Context context;
        TypedArray obtainStyledAttributes = this.b.obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
                requestWindowFeature(1);
            } else if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
                requestWindowFeature(108);
            }
            if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
                requestWindowFeature(109);
            }
            if (obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
                requestWindowFeature(10);
            }
            this.p = obtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
            obtainStyledAttributes.recycle();
            k();
            this.c.getDecorView();
            LayoutInflater from = LayoutInflater.from(this.b);
            if (this.q) {
                viewGroup = this.o ? (ViewGroup) from.inflate(R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) from.inflate(R.layout.abc_screen_simple, (ViewGroup) null);
            } else if (this.p) {
                viewGroup = (ViewGroup) from.inflate(R.layout.abc_dialog_title_material, (ViewGroup) null);
                this.n = false;
                this.m = false;
            } else if (this.m) {
                TypedValue typedValue = new TypedValue();
                this.b.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                if (typedValue.resourceId != 0) {
                    context = new androidx.appcompat.view.ContextThemeWrapper(this.b, typedValue.resourceId);
                } else {
                    context = this.b;
                }
                viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.abc_screen_toolbar, (ViewGroup) null);
                this.C = (DecorContentParent) viewGroup.findViewById(R.id.decor_content_parent);
                this.C.setWindowCallback(b());
                if (this.n) {
                    this.C.initFeature(109);
                }
                if (this.J) {
                    this.C.initFeature(2);
                }
                if (this.K) {
                    this.C.initFeature(5);
                }
            } else {
                viewGroup = null;
            }
            if (viewGroup != null) {
                if (Build.VERSION.SDK_INT >= 21) {
                    ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.3
                        @Override // androidx.core.view.OnApplyWindowInsetsListener
                        public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                            int a2 = AppCompatDelegateImpl.this.a(windowInsetsCompat, (Rect) null);
                            if (systemWindowInsetTop != a2) {
                                windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), a2, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                            }
                            return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                        }
                    });
                } else if (viewGroup instanceof FitWindowsViewGroup) {
                    ((FitWindowsViewGroup) viewGroup).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.4
                        @Override // androidx.appcompat.widget.FitWindowsViewGroup.OnFitSystemWindowsListener
                        public void onFitSystemWindows(Rect rect) {
                            rect.top = AppCompatDelegateImpl.this.a((WindowInsetsCompat) null, rect);
                        }
                    });
                }
                if (this.C == null) {
                    this.H = (TextView) viewGroup.findViewById(R.id.title);
                }
                ViewUtils.makeOptionalFitsSystemWindows(viewGroup);
                ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R.id.action_bar_activity_content);
                ViewGroup viewGroup2 = (ViewGroup) this.c.findViewById(16908290);
                if (viewGroup2 != null) {
                    while (viewGroup2.getChildCount() > 0) {
                        View childAt = viewGroup2.getChildAt(0);
                        viewGroup2.removeViewAt(0);
                        contentFrameLayout.addView(childAt);
                    }
                    viewGroup2.setId(-1);
                    contentFrameLayout.setId(16908290);
                    if (viewGroup2 instanceof FrameLayout) {
                        ((FrameLayout) viewGroup2).setForeground(null);
                    }
                }
                this.c.setContentView(viewGroup);
                contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.5
                    @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
                    public void onAttachedFromWindow() {
                    }

                    @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
                    public void onDetachedFromWindow() {
                        AppCompatDelegateImpl.this.h();
                    }
                });
                return viewGroup;
            }
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.m + ", windowActionBarOverlay: " + this.n + ", android:windowIsFloating: " + this.p + ", windowActionModeOverlay: " + this.o + ", windowNoTitle: " + this.q + " }");
        }
        obtainStyledAttributes.recycle();
        throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
    }

    private void n() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.l.findViewById(16908290);
        View decorView = this.c.getDecorView();
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.b.obtainStyledAttributes(R.styleable.AppCompatTheme);
        obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (obtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            obtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        obtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean requestWindowFeature(int i2) {
        int f2 = f(i2);
        if (this.q && f2 == 108) {
            return false;
        }
        if (this.m && f2 == 1) {
            this.m = false;
        }
        switch (f2) {
            case 1:
                p();
                this.q = true;
                return true;
            case 2:
                p();
                this.J = true;
                return true;
            case 5:
                p();
                this.K = true;
                return true;
            case 10:
                p();
                this.o = true;
                return true;
            case 108:
                p();
                this.m = true;
                return true;
            case 109:
                p();
                this.n = true;
                return true;
            default:
                return this.c.requestFeature(f2);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean hasWindowFeature(int i2) {
        boolean z2;
        switch (f(i2)) {
            case 1:
                z2 = this.q;
                break;
            case 2:
                z2 = this.J;
                break;
            case 5:
                z2 = this.K;
                break;
            case 10:
                z2 = this.o;
                break;
            case 108:
                z2 = this.m;
                break;
            case 109:
                z2 = this.n;
                break;
            default:
                z2 = false;
                break;
        }
        return z2 || this.c.hasFeature(i2);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.B = charSequence;
        DecorContentParent decorContentParent = this.C;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
        } else if (a() != null) {
            a().setWindowTitle(charSequence);
        } else {
            TextView textView = this.H;
            if (textView != null) {
                textView.setText(charSequence);
            }
        }
    }

    final CharSequence d() {
        Object obj = this.a;
        if (obj instanceof Activity) {
            return ((Activity) obj).getTitle();
        }
        return this.B;
    }

    void a(int i2) {
        if (i2 == 108) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.dispatchMenuVisibilityChanged(false);
            }
        } else if (i2 == 0) {
            PanelFeatureState a2 = a(i2, true);
            if (a2.o) {
                a(a2, false);
            }
        }
    }

    void b(int i2) {
        ActionBar supportActionBar;
        if (i2 == 108 && (supportActionBar = getSupportActionBar()) != null) {
            supportActionBar.dispatchMenuVisibilityChanged(true);
        }
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        PanelFeatureState a2;
        Window.Callback b2 = b();
        if (b2 == null || this.r || (a2 = a((Menu) menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return b2.onMenuItemSelected(a2.a, menuItem);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
        a(true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        if (callback != null) {
            ActionMode actionMode = this.g;
            if (actionMode != null) {
                actionMode.finish();
            }
            c cVar = new c(callback);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                this.g = supportActionBar.startActionMode(cVar);
                ActionMode actionMode2 = this.g;
                if (!(actionMode2 == null || (appCompatCallback = this.d) == null)) {
                    appCompatCallback.onSupportActionModeStarted(actionMode2);
                }
            }
            if (this.g == null) {
                this.g = a(cVar);
            }
            return this.g;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void invalidateOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null || !supportActionBar.invalidateOptionsMenu()) {
            e(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    androidx.appcompat.view.ActionMode a(@androidx.annotation.NonNull androidx.appcompat.view.ActionMode.Callback r8) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.a(androidx.appcompat.view.ActionMode$Callback):androidx.appcompat.view.ActionMode");
    }

    final boolean e() {
        ViewGroup viewGroup;
        return this.G && (viewGroup = this.l) != null && ViewCompat.isLaidOut(viewGroup);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setHandleNativeActionModesEnabled(boolean z2) {
        this.F = z2;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean isHandleNativeActionModesEnabled() {
        return this.F;
    }

    void f() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.k;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    boolean g() {
        ActionMode actionMode = this.g;
        if (actionMode != null) {
            actionMode.finish();
            return true;
        }
        ActionBar supportActionBar = getSupportActionBar();
        return supportActionBar != null && supportActionBar.collapseActionView();
    }

    boolean a(int i2, KeyEvent keyEvent) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && supportActionBar.onKeyShortcut(i2, keyEvent)) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.N;
        if (panelFeatureState == null || !a(panelFeatureState, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.N == null) {
                PanelFeatureState a2 = a(0, true);
                b(a2, keyEvent);
                boolean a3 = a(a2, keyEvent.getKeyCode(), keyEvent, 1);
                a2.m = false;
                if (a3) {
                    return true;
                }
            }
            return false;
        }
        PanelFeatureState panelFeatureState2 = this.N;
        if (panelFeatureState2 != null) {
            panelFeatureState2.n = true;
        }
        return true;
    }

    boolean a(KeyEvent keyEvent) {
        View decorView;
        Object obj = this.a;
        boolean z2 = true;
        if (((obj instanceof KeyEventDispatcher.Component) || (obj instanceof AppCompatDialog)) && (decorView = this.c.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.A.getWrapped().dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            z2 = false;
        }
        return z2 ? c(keyCode, keyEvent) : b(keyCode, keyEvent);
    }

    boolean b(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            boolean z2 = this.O;
            this.O = false;
            PanelFeatureState a2 = a(0, false);
            if (a2 != null && a2.o) {
                if (!z2) {
                    a(a2, true);
                }
                return true;
            } else if (g()) {
                return true;
            }
        } else if (i2 == 82) {
            e(0, keyEvent);
            return true;
        }
        return false;
    }

    boolean c(int i2, KeyEvent keyEvent) {
        boolean z2 = true;
        if (i2 == 4) {
            if ((keyEvent.getFlags() & 128) == 0) {
                z2 = false;
            }
            this.O = z2;
        } else if (i2 == 82) {
            d(0, keyEvent);
            return true;
        }
        return false;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean z2 = false;
        if (this.ac == null) {
            String string = this.b.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (string == null) {
                this.ac = new AppCompatViewInflater();
            } else {
                try {
                    this.ac = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.ac = new AppCompatViewInflater();
                }
            }
        }
        if (v) {
            if (this.ad == null) {
                this.ad = new b();
            }
            if (this.ad.a(attributeSet)) {
                z2 = true;
            } else if (!(attributeSet instanceof XmlPullParser)) {
                z2 = a((ViewParent) view);
            } else if (((XmlPullParser) attributeSet).getDepth() > 1) {
                z2 = true;
            }
        } else {
            z2 = false;
        }
        return this.ac.a(view, str, context, attributeSet, z2, v, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean a(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.c.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || ViewCompat.isAttachedToWindow((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void installViewFactory() {
        LayoutInflater from = LayoutInflater.from(this.b);
        if (from.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(from, this);
        } else if (!(from.getFactory2() instanceof AppCompatDelegateImpl)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return createView(view, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Nullable
    private AppCompatActivity o() {
        for (Context context = this.b; context != null; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            }
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
        }
        return null;
    }

    private void a(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i2;
        ViewGroup.LayoutParams layoutParams;
        if (!panelFeatureState.o && !this.r) {
            if (panelFeatureState.a == 0) {
                if ((this.b.getResources().getConfiguration().screenLayout & 15) == 4) {
                    return;
                }
            }
            Window.Callback b2 = b();
            if (b2 == null || b2.onMenuOpened(panelFeatureState.a, panelFeatureState.j)) {
                WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
                if (windowManager != null && b(panelFeatureState, keyEvent)) {
                    if (panelFeatureState.g == null || panelFeatureState.p) {
                        if (panelFeatureState.g == null) {
                            if (!a(panelFeatureState) || panelFeatureState.g == null) {
                                return;
                            }
                        } else if (panelFeatureState.p && panelFeatureState.g.getChildCount() > 0) {
                            panelFeatureState.g.removeAllViews();
                        }
                        if (!c(panelFeatureState) || !panelFeatureState.hasPanelItems()) {
                            panelFeatureState.p = true;
                            return;
                        }
                        ViewGroup.LayoutParams layoutParams2 = panelFeatureState.h.getLayoutParams();
                        if (layoutParams2 == null) {
                            layoutParams2 = new ViewGroup.LayoutParams(-2, -2);
                        }
                        panelFeatureState.g.setBackgroundResource(panelFeatureState.b);
                        ViewParent parent = panelFeatureState.h.getParent();
                        if (parent instanceof ViewGroup) {
                            ((ViewGroup) parent).removeView(panelFeatureState.h);
                        }
                        panelFeatureState.g.addView(panelFeatureState.h, layoutParams2);
                        if (!panelFeatureState.h.hasFocus()) {
                            panelFeatureState.h.requestFocus();
                        }
                    } else if (!(panelFeatureState.i == null || (layoutParams = panelFeatureState.i.getLayoutParams()) == null || layoutParams.width != -1)) {
                        i2 = -1;
                        panelFeatureState.n = false;
                        WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                        layoutParams3.gravity = panelFeatureState.c;
                        layoutParams3.windowAnimations = panelFeatureState.f;
                        windowManager.addView(panelFeatureState.g, layoutParams3);
                        panelFeatureState.o = true;
                        return;
                    }
                    i2 = -2;
                    panelFeatureState.n = false;
                    WindowManager.LayoutParams layoutParams32 = new WindowManager.LayoutParams(i2, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                    layoutParams32.gravity = panelFeatureState.c;
                    layoutParams32.windowAnimations = panelFeatureState.f;
                    windowManager.addView(panelFeatureState.g, layoutParams32);
                    panelFeatureState.o = true;
                    return;
                }
                return;
            }
            a(panelFeatureState, true);
        }
    }

    private boolean a(PanelFeatureState panelFeatureState) {
        panelFeatureState.a(c());
        panelFeatureState.g = new m(panelFeatureState.l);
        panelFeatureState.c = 81;
        return true;
    }

    private void a(boolean z2) {
        DecorContentParent decorContentParent = this.C;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.b).hasPermanentMenuKey() && !this.C.isOverflowMenuShowPending())) {
            PanelFeatureState a2 = a(0, true);
            a2.p = true;
            a(a2, false);
            a(a2, (KeyEvent) null);
            return;
        }
        Window.Callback b2 = b();
        if (this.C.isOverflowMenuShowing() && z2) {
            this.C.hideOverflowMenu();
            if (!this.r) {
                b2.onPanelClosed(108, a(0, true).j);
            }
        } else if (b2 != null && !this.r) {
            if (this.s && (this.t & 1) != 0) {
                this.c.getDecorView().removeCallbacks(this.Y);
                this.Y.run();
            }
            PanelFeatureState a3 = a(0, true);
            if (a3.j != null && !a3.q && b2.onPreparePanel(0, a3.i, a3.j)) {
                b2.onMenuOpened(108, a3.j);
                this.C.showOverflowMenu();
            }
        }
    }

    private boolean b(PanelFeatureState panelFeatureState) {
        Context context = this.b;
        if ((panelFeatureState.a == 0 || panelFeatureState.a == 108) && this.C != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
            Resources.Theme theme2 = null;
            if (typedValue.resourceId != 0) {
                theme2 = context.getResources().newTheme();
                theme2.setTo(theme);
                theme2.applyStyle(typedValue.resourceId, true);
                theme2.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (theme2 == null) {
                    theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                }
                theme2.applyStyle(typedValue.resourceId, true);
            }
            if (theme2 != null) {
                androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
                contextThemeWrapper.getTheme().setTo(theme2);
                context = contextThemeWrapper;
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.a(menuBuilder);
        return true;
    }

    private boolean c(PanelFeatureState panelFeatureState) {
        if (panelFeatureState.i != null) {
            panelFeatureState.h = panelFeatureState.i;
            return true;
        } else if (panelFeatureState.j == null) {
            return false;
        } else {
            if (this.E == null) {
                this.E = new n();
            }
            panelFeatureState.h = (View) panelFeatureState.a(this.E);
            return panelFeatureState.h != null;
        }
    }

    private boolean b(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (this.r) {
            return false;
        }
        if (panelFeatureState.m) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.N;
        if (!(panelFeatureState2 == null || panelFeatureState2 == panelFeatureState)) {
            a(panelFeatureState2, false);
        }
        Window.Callback b2 = b();
        if (b2 != null) {
            panelFeatureState.i = b2.onCreatePanelView(panelFeatureState.a);
        }
        boolean z2 = panelFeatureState.a == 0 || panelFeatureState.a == 108;
        if (z2 && (decorContentParent3 = this.C) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (panelFeatureState.i == null && (!z2 || !(a() instanceof e))) {
            if (panelFeatureState.j == null || panelFeatureState.q) {
                if (panelFeatureState.j == null && (!b(panelFeatureState) || panelFeatureState.j == null)) {
                    return false;
                }
                if (z2 && this.C != null) {
                    if (this.D == null) {
                        this.D = new b();
                    }
                    this.C.setMenu(panelFeatureState.j, this.D);
                }
                panelFeatureState.j.stopDispatchingItemsChanged();
                if (!b2.onCreatePanelMenu(panelFeatureState.a, panelFeatureState.j)) {
                    panelFeatureState.a((MenuBuilder) null);
                    if (z2 && (decorContentParent2 = this.C) != null) {
                        decorContentParent2.setMenu(null, this.D);
                    }
                    return false;
                }
                panelFeatureState.q = false;
            }
            panelFeatureState.j.stopDispatchingItemsChanged();
            if (panelFeatureState.r != null) {
                panelFeatureState.j.restoreActionViewStates(panelFeatureState.r);
                panelFeatureState.r = null;
            }
            if (!b2.onPreparePanel(0, panelFeatureState.i, panelFeatureState.j)) {
                if (z2 && (decorContentParent = this.C) != null) {
                    decorContentParent.setMenu(null, this.D);
                }
                panelFeatureState.j.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.qwertyMode = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.j.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.j.startDispatchingItemsChanged();
        }
        panelFeatureState.m = true;
        panelFeatureState.n = false;
        this.N = panelFeatureState;
        return true;
    }

    void a(@NonNull MenuBuilder menuBuilder) {
        if (!this.L) {
            this.L = true;
            this.C.dismissPopups();
            Window.Callback b2 = b();
            if (b2 != null && !this.r) {
                b2.onPanelClosed(108, menuBuilder);
            }
            this.L = false;
        }
    }

    void c(int i2) {
        a(a(i2, true), true);
    }

    void a(PanelFeatureState panelFeatureState, boolean z2) {
        DecorContentParent decorContentParent;
        if (!z2 || panelFeatureState.a != 0 || (decorContentParent = this.C) == null || !decorContentParent.isOverflowMenuShowing()) {
            WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
            if (!(windowManager == null || !panelFeatureState.o || panelFeatureState.g == null)) {
                windowManager.removeView(panelFeatureState.g);
                if (z2) {
                    a(panelFeatureState.a, panelFeatureState, (Menu) null);
                }
            }
            panelFeatureState.m = false;
            panelFeatureState.n = false;
            panelFeatureState.o = false;
            panelFeatureState.h = null;
            panelFeatureState.p = true;
            if (this.N == panelFeatureState) {
                this.N = null;
                return;
            }
            return;
        }
        a(panelFeatureState.j);
    }

    private boolean d(int i2, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState a2 = a(i2, true);
        if (!a2.o) {
            return b(a2, keyEvent);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean e(int r4, android.view.KeyEvent r5) {
        /*
            r3 = this;
            androidx.appcompat.view.ActionMode r0 = r3.g
            r1 = 0
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = 1
            androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r2 = r3.a(r4, r0)
            if (r4 != 0) goto L_0x0043
            androidx.appcompat.widget.DecorContentParent r4 = r3.C
            if (r4 == 0) goto L_0x0043
            boolean r4 = r4.canShowOverflowMenu()
            if (r4 == 0) goto L_0x0043
            android.content.Context r4 = r3.b
            android.view.ViewConfiguration r4 = android.view.ViewConfiguration.get(r4)
            boolean r4 = r4.hasPermanentMenuKey()
            if (r4 != 0) goto L_0x0043
            androidx.appcompat.widget.DecorContentParent r4 = r3.C
            boolean r4 = r4.isOverflowMenuShowing()
            if (r4 != 0) goto L_0x003c
            boolean r4 = r3.r
            if (r4 != 0) goto L_0x0063
            boolean r4 = r3.b(r2, r5)
            if (r4 == 0) goto L_0x0063
            androidx.appcompat.widget.DecorContentParent r4 = r3.C
            boolean r4 = r4.showOverflowMenu()
            goto L_0x006a
        L_0x003c:
            androidx.appcompat.widget.DecorContentParent r4 = r3.C
            boolean r4 = r4.hideOverflowMenu()
            goto L_0x006a
        L_0x0043:
            boolean r4 = r2.o
            if (r4 != 0) goto L_0x0065
            boolean r4 = r2.n
            if (r4 == 0) goto L_0x004c
            goto L_0x0065
        L_0x004c:
            boolean r4 = r2.m
            if (r4 == 0) goto L_0x0063
            boolean r4 = r2.q
            if (r4 == 0) goto L_0x005b
            r2.m = r1
            boolean r4 = r3.b(r2, r5)
            goto L_0x005c
        L_0x005b:
            r4 = r0
        L_0x005c:
            if (r4 == 0) goto L_0x0063
            r3.a(r2, r5)
            r4 = r0
            goto L_0x006a
        L_0x0063:
            r4 = r1
            goto L_0x006a
        L_0x0065:
            boolean r4 = r2.o
            r3.a(r2, r0)
        L_0x006a:
            if (r4 == 0) goto L_0x0087
            android.content.Context r5 = r3.b
            android.content.Context r5 = r5.getApplicationContext()
            java.lang.String r0 = "audio"
            java.lang.Object r5 = r5.getSystemService(r0)
            android.media.AudioManager r5 = (android.media.AudioManager) r5
            if (r5 == 0) goto L_0x0080
            r5.playSoundEffect(r1)
            goto L_0x0087
        L_0x0080:
            java.lang.String r5 = "AppCompatDelegate"
            java.lang.String r0 = "Couldn't get audio manager"
            android.util.Log.w(r5, r0)
        L_0x0087:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.e(int, android.view.KeyEvent):boolean");
    }

    void a(int i2, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i2 >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.M;
                if (i2 < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i2];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.o) && !this.r) {
            this.A.getWrapped().onPanelClosed(i2, menu);
        }
    }

    PanelFeatureState a(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.M;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i2 = 0; i2 < length; i2++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
            if (panelFeatureState != null && panelFeatureState.j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    protected PanelFeatureState a(int i2, boolean z2) {
        PanelFeatureState[] panelFeatureStateArr = this.M;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i2) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i2 + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.M = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i2];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i2);
        panelFeatureStateArr[i2] = panelFeatureState2;
        return panelFeatureState2;
    }

    private boolean a(PanelFeatureState panelFeatureState, int i2, KeyEvent keyEvent, int i3) {
        boolean z2 = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.m || b(panelFeatureState, keyEvent)) && panelFeatureState.j != null) {
            z2 = panelFeatureState.j.performShortcut(i2, keyEvent, i3);
        }
        if (z2 && (i3 & 1) == 0 && this.C == null) {
            a(panelFeatureState, true);
        }
        return z2;
    }

    private void e(int i2) {
        this.t = (1 << i2) | this.t;
        if (!this.s) {
            ViewCompat.postOnAnimation(this.c.getDecorView(), this.Y);
            this.s = true;
        }
    }

    void d(int i2) {
        PanelFeatureState a2;
        PanelFeatureState a3 = a(i2, true);
        if (a3.j != null) {
            Bundle bundle = new Bundle();
            a3.j.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                a3.r = bundle;
            }
            a3.j.stopDispatchingItemsChanged();
            a3.j.clear();
        }
        a3.q = true;
        a3.p = true;
        if ((i2 == 108 || i2 == 0) && this.C != null && (a2 = a(0, false)) != null) {
            a2.m = false;
            b(a2, (KeyEvent) null);
        }
    }

    final int a(@Nullable WindowInsetsCompat windowInsetsCompat, @Nullable Rect rect) {
        int i2;
        boolean z2;
        boolean z3;
        int i3;
        int i4;
        int i5 = 0;
        if (windowInsetsCompat != null) {
            i2 = windowInsetsCompat.getSystemWindowInsetTop();
        } else {
            i2 = rect != null ? rect.top : 0;
        }
        ActionBarContextView actionBarContextView = this.h;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z2 = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.h.getLayoutParams();
            z2 = true;
            if (this.h.isShown()) {
                if (this.aa == null) {
                    this.aa = new Rect();
                    this.ab = new Rect();
                }
                Rect rect2 = this.aa;
                Rect rect3 = this.ab;
                if (windowInsetsCompat == null) {
                    rect2.set(rect);
                } else {
                    rect2.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                ViewUtils.computeFitSystemWindows(this.l, rect2, rect3);
                int i6 = rect2.top;
                int i7 = rect2.left;
                int i8 = rect2.right;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this.l);
                if (rootWindowInsets == null) {
                    i3 = 0;
                } else {
                    i3 = rootWindowInsets.getSystemWindowInsetLeft();
                }
                if (rootWindowInsets == null) {
                    i4 = 0;
                } else {
                    i4 = rootWindowInsets.getSystemWindowInsetRight();
                }
                if (marginLayoutParams.topMargin == i6 && marginLayoutParams.leftMargin == i7 && marginLayoutParams.rightMargin == i8) {
                    z3 = false;
                } else {
                    marginLayoutParams.topMargin = i6;
                    marginLayoutParams.leftMargin = i7;
                    marginLayoutParams.rightMargin = i8;
                    z3 = true;
                }
                if (i6 <= 0 || this.I != null) {
                    View view = this.I;
                    if (view != null) {
                        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        if (!(marginLayoutParams2.height == marginLayoutParams.topMargin && marginLayoutParams2.leftMargin == i3 && marginLayoutParams2.rightMargin == i4)) {
                            marginLayoutParams2.height = marginLayoutParams.topMargin;
                            marginLayoutParams2.leftMargin = i3;
                            marginLayoutParams2.rightMargin = i4;
                            this.I.setLayoutParams(marginLayoutParams2);
                        }
                    }
                } else {
                    this.I = new View(this.b);
                    this.I.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = i3;
                    layoutParams.rightMargin = i4;
                    this.l.addView(this.I, -1, layoutParams);
                }
                if (this.I == null) {
                    z2 = false;
                }
                if (z2 && this.I.getVisibility() != 0) {
                    a(this.I);
                }
                if (!this.o && z2) {
                    i2 = 0;
                }
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z3 = true;
                z2 = false;
            } else {
                z3 = false;
                z2 = false;
            }
            if (z3) {
                this.h.setLayoutParams(marginLayoutParams);
            }
        }
        View view2 = this.I;
        if (view2 != null) {
            if (!z2) {
                i5 = 8;
            }
            view2.setVisibility(i5);
        }
        return i2;
    }

    private void a(View view) {
        int i2;
        if ((ViewCompat.getWindowSystemUiVisibility(view) & 8192) != 0) {
            i2 = ContextCompat.getColor(this.b, R.color.abc_decor_view_status_guard_light);
        } else {
            i2 = ContextCompat.getColor(this.b, R.color.abc_decor_view_status_guard);
        }
        view.setBackgroundColor(i2);
    }

    private void p() {
        if (this.G) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int f(int i2) {
        if (i2 == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (i2 != 9) {
            return i2;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    void h() {
        DecorContentParent decorContentParent = this.C;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (this.i != null) {
            this.c.getDecorView().removeCallbacks(this.j);
            if (this.i.isShowing()) {
                try {
                    this.i.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.i = null;
        }
        f();
        PanelFeatureState a2 = a(0, false);
        if (a2 != null && a2.j != null) {
            a2.j.close();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean applyDayNight() {
        return b(true);
    }

    private boolean b(boolean z2) {
        if (this.r) {
            return false;
        }
        int q = q();
        boolean b2 = b(a(this.b, q), z2);
        if (q == 0) {
            a(this.b).d();
        } else {
            j jVar = this.W;
            if (jVar != null) {
                jVar.e();
            }
        }
        if (q == 3) {
            b(this.b).d();
        } else {
            j jVar2 = this.X;
            if (jVar2 != null) {
                jVar2.e();
            }
        }
        return b2;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @RequiresApi(17)
    public void setLocalNightMode(int i2) {
        if (this.S != i2) {
            this.S = i2;
            if (this.P) {
                applyDayNight();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public int getLocalNightMode() {
        return this.S;
    }

    int a(@NonNull Context context, int i2) {
        if (i2 == -100) {
            return -1;
        }
        switch (i2) {
            case -1:
            case 1:
            case 2:
                return i2;
            case 0:
                if (Build.VERSION.SDK_INT < 23 || ((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() != 0) {
                    return a(context).a();
                }
                return -1;
            case 3:
                return b(context).a();
            default:
                throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
        }
    }

    private int q() {
        int i2 = this.S;
        return i2 != -100 ? i2 : getDefaultNightMode();
    }

    @NonNull
    private Configuration a(@NonNull Context context, int i2, @Nullable Configuration configuration) {
        int i3;
        switch (i2) {
            case 1:
                i3 = 16;
                break;
            case 2:
                i3 = 32;
                break;
            default:
                i3 = context.getApplicationContext().getResources().getConfiguration().uiMode & 48;
                break;
        }
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i3 | (configuration2.uiMode & (-49));
        return configuration2;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean b(int r7, boolean r8) {
        /*
            r6 = this;
            android.content.Context r0 = r6.b
            r1 = 0
            android.content.res.Configuration r0 = r6.a(r0, r7, r1)
            boolean r2 = r6.r()
            android.content.Context r3 = r6.b
            android.content.res.Resources r3 = r3.getResources()
            android.content.res.Configuration r3 = r3.getConfiguration()
            int r3 = r3.uiMode
            r3 = r3 & 48
            int r0 = r0.uiMode
            r0 = r0 & 48
            r4 = 1
            if (r3 == r0) goto L_0x0047
            if (r8 == 0) goto L_0x0047
            if (r2 != 0) goto L_0x0047
            boolean r8 = r6.P
            if (r8 == 0) goto L_0x0047
            boolean r8 = androidx.appcompat.app.AppCompatDelegateImpl.x
            if (r8 != 0) goto L_0x0030
            boolean r8 = r6.Q
            if (r8 == 0) goto L_0x0047
        L_0x0030:
            java.lang.Object r8 = r6.a
            boolean r5 = r8 instanceof android.app.Activity
            if (r5 == 0) goto L_0x0047
            android.app.Activity r8 = (android.app.Activity) r8
            boolean r8 = r8.isChild()
            if (r8 != 0) goto L_0x0047
            java.lang.Object r8 = r6.a
            android.app.Activity r8 = (android.app.Activity) r8
            androidx.core.app.ActivityCompat.recreate(r8)
            r8 = r4
            goto L_0x0048
        L_0x0047:
            r8 = 0
        L_0x0048:
            if (r8 != 0) goto L_0x0050
            if (r3 == r0) goto L_0x0050
            r6.a(r0, r2, r1)
            r8 = r4
        L_0x0050:
            if (r8 == 0) goto L_0x005d
            java.lang.Object r0 = r6.a
            boolean r1 = r0 instanceof androidx.appcompat.app.AppCompatActivity
            if (r1 == 0) goto L_0x005d
            androidx.appcompat.app.AppCompatActivity r0 = (androidx.appcompat.app.AppCompatActivity) r0
            r0.onNightModeChanged(r7)
        L_0x005d:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.b(int, boolean):boolean");
    }

    private void a(int i2, boolean z2, @Nullable Configuration configuration) {
        Resources resources = this.b.getResources();
        Configuration configuration2 = new Configuration(resources.getConfiguration());
        if (configuration != null) {
            configuration2.updateFrom(configuration);
        }
        configuration2.uiMode = i2 | (resources.getConfiguration().uiMode & (-49));
        resources.updateConfiguration(configuration2, null);
        if (Build.VERSION.SDK_INT < 26) {
            d.a(resources);
        }
        int i3 = this.T;
        if (i3 != 0) {
            this.b.setTheme(i3);
            if (Build.VERSION.SDK_INT >= 23) {
                this.b.getTheme().applyStyle(this.T, true);
            }
        }
        if (z2) {
            Object obj = this.a;
            if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                if (activity instanceof LifecycleOwner) {
                    if (((LifecycleOwner) activity).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        activity.onConfigurationChanged(configuration2);
                    }
                } else if (this.R) {
                    activity.onConfigurationChanged(configuration2);
                }
            }
        }
    }

    private j a(@NonNull Context context) {
        if (this.W == null) {
            this.W = new k(g.a(context));
        }
        return this.W;
    }

    private j b(@NonNull Context context) {
        if (this.X == null) {
            this.X = new i(context);
        }
        return this.X;
    }

    private boolean r() {
        int i2;
        if (!this.V && (this.a instanceof Activity)) {
            PackageManager packageManager = this.b.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    i2 = 269221888;
                } else {
                    i2 = Build.VERSION.SDK_INT >= 24 ? 786432 : 0;
                }
                ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(this.b, this.a.getClass()), i2);
                this.U = (activityInfo == null || (activityInfo.configChanges & 512) == 0) ? false : true;
            } catch (PackageManager.NameNotFoundException e2) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e2);
                this.U = false;
            }
        }
        this.V = true;
        return this.U;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class c implements ActionMode.Callback {
        private ActionMode.Callback b;

        public c(ActionMode.Callback callback) {
            this.b = callback;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.b.onCreateActionMode(actionMode, menu);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.l);
            return this.b.onPrepareActionMode(actionMode, menu);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.b.onActionItemClicked(actionMode, menuItem);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.b.onDestroyActionMode(actionMode);
            if (AppCompatDelegateImpl.this.i != null) {
                AppCompatDelegateImpl.this.c.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.j);
            }
            if (AppCompatDelegateImpl.this.h != null) {
                AppCompatDelegateImpl.this.f();
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                appCompatDelegateImpl.k = ViewCompat.animate(appCompatDelegateImpl.h).alpha(0.0f);
                AppCompatDelegateImpl.this.k.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.c.1
                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.h.setVisibility(8);
                        if (AppCompatDelegateImpl.this.i != null) {
                            AppCompatDelegateImpl.this.i.dismiss();
                        } else if (AppCompatDelegateImpl.this.h.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.h.getParent());
                        }
                        AppCompatDelegateImpl.this.h.killMode();
                        AppCompatDelegateImpl.this.k.setListener(null);
                        AppCompatDelegateImpl.this.k = null;
                        ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.l);
                    }
                });
            }
            if (AppCompatDelegateImpl.this.d != null) {
                AppCompatDelegateImpl.this.d.onSupportActionModeFinished(AppCompatDelegateImpl.this.g);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl2.g = null;
            ViewCompat.requestApplyInsets(appCompatDelegateImpl2.l);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class n implements MenuPresenter.Callback {
        n() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState a = appCompatDelegateImpl.a((Menu) menuBuilder);
            if (a == null) {
                return;
            }
            if (z2) {
                AppCompatDelegateImpl.this.a(a.a, a, rootMenu);
                AppCompatDelegateImpl.this.a(a, true);
                return;
            }
            AppCompatDelegateImpl.this.a(a, z);
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            Window.Callback b;
            if (menuBuilder != menuBuilder.getRootMenu() || !AppCompatDelegateImpl.this.m || (b = AppCompatDelegateImpl.this.b()) == null || AppCompatDelegateImpl.this.r) {
                return true;
            }
            b.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class b implements MenuPresenter.Callback {
        b() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            Window.Callback b = AppCompatDelegateImpl.this.b();
            if (b == null) {
                return true;
            }
            b.onMenuOpened(108, menuBuilder);
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImpl.this.a(menuBuilder);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public static final class PanelFeatureState {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        ViewGroup g;
        View h;
        View i;
        MenuBuilder j;
        ListMenuPresenter k;
        Context l;
        boolean m;
        boolean n;
        boolean o;
        boolean p = false;
        boolean q;
        public boolean qwertyMode;
        Bundle r;

        PanelFeatureState(int i) {
            this.a = i;
        }

        public boolean hasPanelItems() {
            if (this.h == null) {
                return false;
            }
            return this.i != null || this.k.getAdapter().getCount() > 0;
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.j;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.k);
            }
            this.k = null;
        }

        void a(Context context) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme newTheme = context.getResources().newTheme();
            newTheme.setTo(context.getTheme());
            newTheme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                newTheme.applyStyle(typedValue.resourceId, true);
            }
            newTheme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                newTheme.applyStyle(typedValue.resourceId, true);
            } else {
                newTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
            }
            androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
            contextThemeWrapper.getTheme().setTo(newTheme);
            this.l = contextThemeWrapper;
            TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.b = obtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.f = obtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            obtainStyledAttributes.recycle();
        }

        void a(MenuBuilder menuBuilder) {
            ListMenuPresenter listMenuPresenter;
            MenuBuilder menuBuilder2 = this.j;
            if (menuBuilder != menuBuilder2) {
                if (menuBuilder2 != null) {
                    menuBuilder2.removeMenuPresenter(this.k);
                }
                this.j = menuBuilder;
                if (menuBuilder != null && (listMenuPresenter = this.k) != null) {
                    menuBuilder.addMenuPresenter(listMenuPresenter);
                }
            }
        }

        MenuView a(MenuPresenter.Callback callback) {
            if (this.j == null) {
                return null;
            }
            if (this.k == null) {
                this.k = new ListMenuPresenter(this.l, R.layout.abc_list_menu_item_layout);
                this.k.setCallback(callback);
                this.j.addMenuPresenter(this.k);
            }
            return this.k.getMenuView(this.g);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class m extends ContentFrameLayout {
        public m(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !a((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            AppCompatDelegateImpl.this.c(0);
            return true;
        }

        @Override // android.view.View
        public void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }

        private boolean a(int i, int i2) {
            return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class h extends WindowCallbackWrapper {
        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onContentChanged() {
        }

        h(Window.Callback callback) {
            super(callback);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.a(keyEvent.getKeyCode(), keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(false);
            }
            return onPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl.this.b(i);
            return true;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            AppCompatDelegateImpl.this.a(i);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
                return a(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        final android.view.ActionMode a(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.b, callback);
            androidx.appcompat.view.ActionMode startSupportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (startSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(startSupportActionMode);
            }
            return null;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            if (!AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() || i != 0) {
                return super.onWindowStartingActionMode(callback, i);
            }
            return a(callback);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(24)
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            PanelFeatureState a = AppCompatDelegateImpl.this.a(0, true);
            if (a == null || a.j == null) {
                super.onProvideKeyboardShortcuts(list, menu, i);
            } else {
                super.onProvideKeyboardShortcuts(list, a.j, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    /* loaded from: classes.dex */
    public abstract class j {
        private BroadcastReceiver a;

        abstract int a();

        abstract void b();

        @Nullable
        abstract IntentFilter c();

        j() {
        }

        void d() {
            e();
            IntentFilter c = c();
            if (c != null && c.countActions() != 0) {
                if (this.a == null) {
                    this.a = new BroadcastReceiver() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.j.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            j.this.b();
                        }
                    };
                }
                AppCompatDelegateImpl.this.b.registerReceiver(this.a, c);
            }
        }

        void e() {
            if (this.a != null) {
                try {
                    AppCompatDelegateImpl.this.b.unregisterReceiver(this.a);
                } catch (IllegalArgumentException unused) {
                }
                this.a = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class k extends j {
        private final g c;

        k(g gVar) {
            super();
            this.c = gVar;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.j
        public int a() {
            return this.c.a() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.j
        public void b() {
            AppCompatDelegateImpl.this.applyDayNight();
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.j
        IntentFilter c() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class i extends j {
        private final PowerManager c;

        i(Context context) {
            super();
            this.c = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.j
        public int a() {
            return (Build.VERSION.SDK_INT < 21 || !e.a(this.c)) ? 1 : 2;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.j
        public void b() {
            AppCompatDelegateImpl.this.applyDayNight();
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.j
        IntentFilter c() {
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new a();
    }

    /* loaded from: classes.dex */
    private class a implements ActionBarDrawerToggle.Delegate {
        a() {
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new int[]{R.attr.homeAsUpIndicator});
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.c();
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(drawable);
                supportActionBar.setHomeActionContentDescription(i);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeActionContentDescription(i);
            }
        }
    }

    @NonNull
    private static Configuration a(@NonNull Configuration configuration, @Nullable Configuration configuration2) {
        Configuration configuration3 = new Configuration();
        configuration3.fontScale = 0.0f;
        if (configuration2 == null || configuration.diff(configuration2) == 0) {
            return configuration3;
        }
        if (configuration.fontScale != configuration2.fontScale) {
            configuration3.fontScale = configuration2.fontScale;
        }
        if (configuration.mcc != configuration2.mcc) {
            configuration3.mcc = configuration2.mcc;
        }
        if (configuration.mnc != configuration2.mnc) {
            configuration3.mnc = configuration2.mnc;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            f.a(configuration, configuration2, configuration3);
        } else if (!ObjectsCompat.equals(configuration.locale, configuration2.locale)) {
            configuration3.locale = configuration2.locale;
        }
        if (configuration.touchscreen != configuration2.touchscreen) {
            configuration3.touchscreen = configuration2.touchscreen;
        }
        if (configuration.keyboard != configuration2.keyboard) {
            configuration3.keyboard = configuration2.keyboard;
        }
        if (configuration.keyboardHidden != configuration2.keyboardHidden) {
            configuration3.keyboardHidden = configuration2.keyboardHidden;
        }
        if (configuration.navigation != configuration2.navigation) {
            configuration3.navigation = configuration2.navigation;
        }
        if (configuration.navigationHidden != configuration2.navigationHidden) {
            configuration3.navigationHidden = configuration2.navigationHidden;
        }
        if (configuration.orientation != configuration2.orientation) {
            configuration3.orientation = configuration2.orientation;
        }
        if ((configuration.screenLayout & 15) != (configuration2.screenLayout & 15)) {
            configuration3.screenLayout |= configuration2.screenLayout & 15;
        }
        if ((configuration.screenLayout & 192) != (configuration2.screenLayout & 192)) {
            configuration3.screenLayout |= configuration2.screenLayout & 192;
        }
        if ((configuration.screenLayout & 48) != (configuration2.screenLayout & 48)) {
            configuration3.screenLayout |= configuration2.screenLayout & 48;
        }
        if ((configuration.screenLayout & LogType.UNEXP_OTHER) != (configuration2.screenLayout & LogType.UNEXP_OTHER)) {
            configuration3.screenLayout |= configuration2.screenLayout & LogType.UNEXP_OTHER;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            g.a(configuration, configuration2, configuration3);
        }
        if ((configuration.uiMode & 15) != (configuration2.uiMode & 15)) {
            configuration3.uiMode |= configuration2.uiMode & 15;
        }
        if ((configuration.uiMode & 48) != (configuration2.uiMode & 48)) {
            configuration3.uiMode |= configuration2.uiMode & 48;
        }
        if (configuration.screenWidthDp != configuration2.screenWidthDp) {
            configuration3.screenWidthDp = configuration2.screenWidthDp;
        }
        if (configuration.screenHeightDp != configuration2.screenHeightDp) {
            configuration3.screenHeightDp = configuration2.screenHeightDp;
        }
        if (configuration.smallestScreenWidthDp != configuration2.smallestScreenWidthDp) {
            configuration3.smallestScreenWidthDp = configuration2.smallestScreenWidthDp;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            d.a(configuration, configuration2, configuration3);
        }
        return configuration3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(17)
    /* loaded from: classes.dex */
    public static class d {
        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            if (configuration.densityDpi != configuration2.densityDpi) {
                configuration3.densityDpi = configuration2.densityDpi;
            }
        }

        static Context a(@NonNull Context context, @NonNull Configuration configuration) {
            return context.createConfigurationContext(configuration);
        }
    }

    @RequiresApi(21)
    /* loaded from: classes.dex */
    static class e {
        static boolean a(PowerManager powerManager) {
            return powerManager.isPowerSaveMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(24)
    /* loaded from: classes.dex */
    public static class f {
        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            LocaleList locales = configuration.getLocales();
            LocaleList locales2 = configuration2.getLocales();
            if (!locales.equals(locales2)) {
                configuration3.setLocales(locales2);
                configuration3.locale = configuration2.locale;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(26)
    /* loaded from: classes.dex */
    public static class g {
        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                configuration3.colorMode |= configuration2.colorMode & 3;
            }
            if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
                configuration3.colorMode |= configuration2.colorMode & 12;
            }
        }
    }
}
