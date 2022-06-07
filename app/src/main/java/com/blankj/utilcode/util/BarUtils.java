package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.drawerlayout.widget.DrawerLayout;

/* loaded from: classes.dex */
public final class BarUtils {
    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static int getStatusBarHeight() {
        Resources resources = Utils.getApp().getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static void setStatusBarVisibility(@NonNull Activity activity, boolean z) {
        if (activity != null) {
            setStatusBarVisibility(activity.getWindow(), z);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setStatusBarVisibility(@NonNull Window window, boolean z) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (z) {
            window.clearFlags(1024);
            d(window);
            a(window);
        } else {
            window.addFlags(1024);
            c(window);
            b(window);
        }
    }

    public static boolean isStatusBarVisible(@NonNull Activity activity) {
        if (activity != null) {
            return (activity.getWindow().getAttributes().flags & 1024) == 0;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setStatusBarLightMode(@NonNull Activity activity, boolean z) {
        if (activity != null) {
            setStatusBarLightMode(activity.getWindow(), z);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setStatusBarLightMode(@NonNull Window window, boolean z) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 23) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
        }
    }

    public static boolean isStatusBarLightMode(@NonNull Activity activity) {
        if (activity != null) {
            return isStatusBarLightMode(activity.getWindow());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isStatusBarLightMode(@NonNull Window window) {
        if (window != null) {
            return Build.VERSION.SDK_INT >= 23 && (window.getDecorView().getSystemUiVisibility() & 8192) != 0;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void addMarginTopEqualStatusBarHeight(@NonNull View view) {
        if (view == null) {
            throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19) {
            view.setTag("TAG_OFFSET");
            Object tag = view.getTag(-123);
            if (tag == null || !((Boolean) tag).booleanValue()) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                view.setTag(-123, true);
            }
        }
    }

    public static void subtractMarginTopEqualStatusBarHeight(@NonNull View view) {
        Object tag;
        if (view == null) {
            throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19 && (tag = view.getTag(-123)) != null && ((Boolean) tag).booleanValue()) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin - getStatusBarHeight(), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            view.setTag(-123, false);
        }
    }

    private static void a(@NonNull Window window) {
        View findViewWithTag;
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19 && (findViewWithTag = window.getDecorView().findViewWithTag("TAG_OFFSET")) != null) {
            addMarginTopEqualStatusBarHeight(findViewWithTag);
        }
    }

    private static void b(@NonNull Window window) {
        View findViewWithTag;
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19 && (findViewWithTag = window.getDecorView().findViewWithTag("TAG_OFFSET")) != null) {
            subtractMarginTopEqualStatusBarHeight(findViewWithTag);
        }
    }

    public static View setStatusBarColor(@NonNull Activity activity, @ColorInt int i) {
        if (activity != null) {
            return setStatusBarColor(activity, i, false);
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static View setStatusBarColor(@NonNull Activity activity, @ColorInt int i, boolean z) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT < 19) {
            return null;
        } else {
            transparentStatusBar(activity);
            return a(activity, i, z);
        }
    }

    public static View setStatusBarColor(@NonNull Window window, @ColorInt int i) {
        if (window != null) {
            return setStatusBarColor(window, i, false);
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static View setStatusBarColor(@NonNull Window window, @ColorInt int i, boolean z) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT < 19) {
            return null;
        } else {
            transparentStatusBar(window);
            return a(window, i, z);
        }
    }

    public static void setStatusBarColor(@NonNull View view, @ColorInt int i) {
        Activity a;
        if (view == null) {
            throw new NullPointerException("Argument 'fakeStatusBar' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19 && (a = b.a(view.getContext())) != null) {
            transparentStatusBar(a);
            view.setVisibility(0);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = getStatusBarHeight();
            view.setBackgroundColor(i);
        }
    }

    public static void setStatusBarCustom(@NonNull View view) {
        Activity a;
        if (view == null) {
            throw new NullPointerException("Argument 'fakeStatusBar' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19 && (a = b.a(view.getContext())) != null) {
            transparentStatusBar(a);
            view.setVisibility(0);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-1, getStatusBarHeight()));
                return;
            }
            layoutParams.width = -1;
            layoutParams.height = getStatusBarHeight();
        }
    }

    public static void setStatusBarColor4Drawer(@NonNull DrawerLayout drawerLayout, @NonNull View view, @ColorInt int i) {
        if (drawerLayout == null) {
            throw new NullPointerException("Argument 'drawer' of type DrawerLayout (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (view != null) {
            setStatusBarColor4Drawer(drawerLayout, view, i, false);
        } else {
            throw new NullPointerException("Argument 'fakeStatusBar' of type View (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void setStatusBarColor4Drawer(@NonNull DrawerLayout drawerLayout, @NonNull View view, @ColorInt int i, boolean z) {
        Activity a;
        if (drawerLayout == null) {
            throw new NullPointerException("Argument 'drawer' of type DrawerLayout (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (view == null) {
            throw new NullPointerException("Argument 'fakeStatusBar' of type View (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19 && (a = b.a(view.getContext())) != null) {
            transparentStatusBar(a);
            drawerLayout.setFitsSystemWindows(false);
            setStatusBarColor(view, i);
            int childCount = drawerLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                drawerLayout.getChildAt(i2).setFitsSystemWindows(false);
            }
            if (z) {
                a(a);
            } else {
                setStatusBarColor(a, i, false);
            }
        }
    }

    private static View a(@NonNull Activity activity, int i, boolean z) {
        if (activity != null) {
            return a(activity.getWindow(), i, z);
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static View a(@NonNull Window window, int i, boolean z) {
        ViewGroup viewGroup;
        if (window != null) {
            if (z) {
                viewGroup = (ViewGroup) window.getDecorView();
            } else {
                viewGroup = (ViewGroup) window.findViewById(16908290);
            }
            View findViewWithTag = viewGroup.findViewWithTag("TAG_STATUS_BAR");
            if (findViewWithTag != null) {
                if (findViewWithTag.getVisibility() == 8) {
                    findViewWithTag.setVisibility(0);
                }
                findViewWithTag.setBackgroundColor(i);
                return findViewWithTag;
            }
            View a = a(window.getContext(), i);
            viewGroup.addView(a);
            return a;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static void a(@NonNull Activity activity) {
        if (activity != null) {
            c(activity.getWindow());
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static void c(@NonNull Window window) {
        if (window != null) {
            View findViewWithTag = ((ViewGroup) window.getDecorView()).findViewWithTag("TAG_STATUS_BAR");
            if (findViewWithTag != null) {
                findViewWithTag.setVisibility(8);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static void d(@NonNull Window window) {
        if (window != null) {
            View findViewWithTag = ((ViewGroup) window.getDecorView()).findViewWithTag("TAG_STATUS_BAR");
            if (findViewWithTag != null) {
                findViewWithTag.setVisibility(0);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static View a(@NonNull Context context, int i) {
        if (context != null) {
            View view = new View(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, getStatusBarHeight()));
            view.setBackgroundColor(i);
            view.setTag("TAG_STATUS_BAR");
            return view;
        }
        throw new NullPointerException("Argument 'context' of type Context (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void transparentStatusBar(@NonNull Activity activity) {
        if (activity != null) {
            transparentStatusBar(activity.getWindow());
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void transparentStatusBar(@NonNull Window window) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(67108864);
                window.addFlags(Integer.MIN_VALUE);
                window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | 1280);
                window.setStatusBarColor(0);
                return;
            }
            window.addFlags(67108864);
        }
    }

    public static int getActionBarHeight() {
        TypedValue typedValue = new TypedValue();
        if (Utils.getApp().getTheme().resolveAttribute(16843499, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, Utils.getApp().getResources().getDisplayMetrics());
        }
        return 0;
    }

    @RequiresPermission("android.permission.EXPAND_STATUS_BAR")
    public static void setNotificationBarVisibility(boolean z) {
        a(z ? Build.VERSION.SDK_INT <= 16 ? "expand" : "expandNotificationsPanel" : Build.VERSION.SDK_INT <= 16 ? "collapse" : "collapsePanels");
    }

    private static void a(String str) {
        try {
            Class.forName("android.app.StatusBarManager").getMethod(str, new Class[0]).invoke(Utils.getApp().getSystemService("statusbar"), new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNavBarHeight() {
        Resources resources = Utils.getApp().getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier != 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static void setNavBarVisibility(@NonNull Activity activity, boolean z) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19) {
            setNavBarVisibility(activity.getWindow(), z);
        }
    }

    public static void setNavBarVisibility(@NonNull Window window, boolean z) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                int id = childAt.getId();
                if (id != -1 && "navigationBarBackground".equals(a(id))) {
                    childAt.setVisibility(z ? 0 : 4);
                }
            }
            if (z) {
                viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() & (-4611));
            } else {
                viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() | 4610);
            }
        }
    }

    public static boolean isNavBarVisible(@NonNull Activity activity) {
        if (activity != null) {
            return isNavBarVisible(activity.getWindow());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isNavBarVisible(@NonNull Window window) {
        boolean z;
        if (window != null) {
            ViewGroup viewGroup = (ViewGroup) window.getDecorView();
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    z = false;
                    break;
                }
                View childAt = viewGroup.getChildAt(i);
                int id = childAt.getId();
                if (id != -1 && "navigationBarBackground".equals(a(id)) && childAt.getVisibility() == 0) {
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                return z;
            }
            if (b.q() && Build.VERSION.SDK_INT >= 17 && Build.VERSION.SDK_INT < 29) {
                try {
                    return Settings.Global.getInt(Utils.getApp().getContentResolver(), "navigationbar_hide_bar_enabled") == 0;
                } catch (Exception unused) {
                }
            }
            return (viewGroup.getSystemUiVisibility() & 2) == 0;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static String a(int i) {
        try {
            return Utils.getApp().getResources().getResourceEntryName(i);
        } catch (Exception unused) {
            return "";
        }
    }

    @RequiresApi(21)
    public static void setNavBarColor(@NonNull Activity activity, @ColorInt int i) {
        if (activity != null) {
            setNavBarColor(activity.getWindow(), i);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresApi(21)
    public static void setNavBarColor(@NonNull Window window, @ColorInt int i) {
        if (window != null) {
            window.addFlags(Integer.MIN_VALUE);
            window.setNavigationBarColor(i);
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresApi(21)
    public static int getNavBarColor(@NonNull Activity activity) {
        if (activity != null) {
            return getNavBarColor(activity.getWindow());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @RequiresApi(21)
    public static int getNavBarColor(@NonNull Window window) {
        if (window != null) {
            return window.getNavigationBarColor();
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isSupportNavBar() {
        if (Build.VERSION.SDK_INT >= 17) {
            WindowManager windowManager = (WindowManager) Utils.getApp().getSystemService("window");
            if (windowManager == null) {
                return false;
            }
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point = new Point();
            Point point2 = new Point();
            defaultDisplay.getSize(point);
            defaultDisplay.getRealSize(point2);
            return (point2.y == point.y && point2.x == point.x) ? false : true;
        }
        return !ViewConfiguration.get(Utils.getApp()).hasPermanentMenuKey() && !KeyCharacterMap.deviceHasKey(4);
    }

    public static void setNavBarLightMode(@NonNull Activity activity, boolean z) {
        if (activity != null) {
            setNavBarLightMode(activity.getWindow(), z);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void setNavBarLightMode(@NonNull Window window, boolean z) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (Build.VERSION.SDK_INT >= 26) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(z ? systemUiVisibility | 16 : systemUiVisibility & (-17));
        }
    }

    public static boolean isNavBarLightMode(@NonNull Activity activity) {
        if (activity != null) {
            return isNavBarLightMode(activity.getWindow());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isNavBarLightMode(@NonNull Window window) {
        if (window != null) {
            return Build.VERSION.SDK_INT >= 26 && (window.getDecorView().getSystemUiVisibility() & 16) != 0;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }
}
