package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.blankj.utilcode.util.Utils;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ActivityUtils {
    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void addActivityLifecycleCallbacks(@Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        b.a(activityLifecycleCallbacks);
    }

    public static void addActivityLifecycleCallbacks(@Nullable Activity activity, @Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        b.a(activity, activityLifecycleCallbacks);
    }

    public static void removeActivityLifecycleCallbacks(@Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        b.b(activityLifecycleCallbacks);
    }

    public static void removeActivityLifecycleCallbacks(@Nullable Activity activity) {
        b.a(activity);
    }

    public static void removeActivityLifecycleCallbacks(@Nullable Activity activity, @Nullable Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        b.b(activity, activityLifecycleCallbacks);
    }

    @Nullable
    public static Activity getActivityByContext(@NonNull Context context) {
        if (context != null) {
            Activity a = a(context);
            if (!isActivityAlive(a)) {
                return null;
            }
            return a;
        }
        throw new NullPointerException("Argument 'context' of type Context (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x000d  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.app.Activity a(@androidx.annotation.Nullable android.content.Context r3) {
        /*
            r0 = 0
            if (r3 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L_0x0009:
            boolean r2 = r3 instanceof android.content.ContextWrapper
            if (r2 == 0) goto L_0x002e
            boolean r2 = r3 instanceof android.app.Activity
            if (r2 == 0) goto L_0x0014
            android.app.Activity r3 = (android.app.Activity) r3
            return r3
        L_0x0014:
            android.app.Activity r2 = b(r3)
            if (r2 == 0) goto L_0x001b
            return r2
        L_0x001b:
            r1.add(r3)
            android.content.ContextWrapper r3 = (android.content.ContextWrapper) r3
            android.content.Context r3 = r3.getBaseContext()
            if (r3 != 0) goto L_0x0027
            return r0
        L_0x0027:
            boolean r2 = r1.contains(r3)
            if (r2 == 0) goto L_0x0009
            return r0
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ActivityUtils.a(android.content.Context):android.app.Activity");
    }

    @Nullable
    private static Activity b(@Nullable Context context) {
        if (context != null && context.getClass().getName().equals("com.android.internal.policy.DecorContext")) {
            try {
                Field declaredField = context.getClass().getDeclaredField("mActivityContext");
                declaredField.setAccessible(true);
                return (Activity) ((WeakReference) declaredField.get(context)).get();
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static boolean isActivityExists(@NonNull String str, @NonNull String str2) {
        if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            Intent intent = new Intent();
            intent.setClassName(str, str2);
            PackageManager packageManager = Utils.getApp().getPackageManager();
            return (packageManager.resolveActivity(intent, 0) == null || intent.resolveActivity(packageManager) == null || packageManager.queryIntentActivities(intent, 0).size() == 0) ? false : true;
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Class<? extends Activity> cls) {
        if (cls != null) {
            Context a = a();
            a(a, null, a.getPackageName(), cls.getName(), null);
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivity(@NonNull Class<? extends Activity> cls, @Nullable Bundle bundle) {
        if (cls != null) {
            Context a = a();
            a(a, null, a.getPackageName(), cls.getName(), bundle);
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivity(@NonNull Class<? extends Activity> cls, @AnimRes int i, @AnimRes int i2) {
        if (cls != null) {
            Context a = a();
            a(a, null, a.getPackageName(), cls.getName(), a(a, i, i2));
            if (Build.VERSION.SDK_INT < 16 && (a instanceof Activity)) {
                ((Activity) a).overridePendingTransition(i, i2);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, null, activity.getPackageName(), cls.getName(), null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, @Nullable Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, null, activity.getPackageName(), cls.getName(), bundle);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, View... viewArr) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, null, activity.getPackageName(), cls.getName(), a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, @AnimRes int i, @AnimRes int i2) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, null, activity.getPackageName(), cls.getName(), a(activity, i, i2));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<? extends Activity> cls) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            Context a = a();
            a(a, bundle, a.getPackageName(), cls.getName(), null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<? extends Activity> cls, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            Context a = a();
            a(a, bundle, a.getPackageName(), cls.getName(), bundle2);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Class<? extends Activity> cls, @AnimRes int i, @AnimRes int i2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            Context a = a();
            a(a, bundle, a.getPackageName(), cls.getName(), a(a, i, i2));
            if (Build.VERSION.SDK_INT < 16 && (a instanceof Activity)) {
                ((Activity) a).overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), bundle2);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, View... viewArr) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, @AnimRes int i, @AnimRes int i2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), a(activity, i, i2));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull String str, @NonNull String str2) {
        if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(a(), null, str, str2, null);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull String str, @NonNull String str2, @Nullable Bundle bundle) {
        if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(a(), null, str, str2, bundle);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull String str, @NonNull String str2, @AnimRes int i, @AnimRes int i2) {
        if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            Context a = a();
            a(a, null, str, str2, a(a, i, i2));
            if (Build.VERSION.SDK_INT < 16 && (a instanceof Activity)) {
                ((Activity) a).overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, null, str, str2, null);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @Nullable Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, null, str, str2, bundle);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, View... viewArr) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, null, str, str2, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull String str, @NonNull String str2, @AnimRes int i, @AnimRes int i2) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, null, str, str2, a(activity, i, i2));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(a(), bundle, str, str2, null);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(a(), bundle, str, str2, bundle2);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull String str, @NonNull String str2, @AnimRes int i, @AnimRes int i2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            Context a = a();
            a(a, bundle, str, str2, a(a, i, i2));
            if (Build.VERSION.SDK_INT < 16 && (a instanceof Activity)) {
                ((Activity) a).overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, null);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, bundle2);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, View... viewArr) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, @AnimRes int i, @AnimRes int i2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, a(activity, i, i2));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static boolean startActivity(@NonNull Intent intent) {
        if (intent != null) {
            return a(intent, a(), (Bundle) null);
        }
        throw new NullPointerException("Argument 'intent' of type Intent (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean startActivity(@NonNull Intent intent, @Nullable Bundle bundle) {
        if (intent != null) {
            return a(intent, a(), bundle);
        }
        throw new NullPointerException("Argument 'intent' of type Intent (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean startActivity(@NonNull Intent intent, @AnimRes int i, @AnimRes int i2) {
        if (intent != null) {
            Context a = a();
            boolean a2 = a(intent, a, a(a, i, i2));
            if (a2 && Build.VERSION.SDK_INT < 16 && (a instanceof Activity)) {
                ((Activity) a).overridePendingTransition(i, i2);
            }
            return a2;
        }
        throw new NullPointerException("Argument 'intent' of type Intent (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @Nullable Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, bundle);
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, View... viewArr) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivity(@NonNull Activity activity, @NonNull Intent intent, @AnimRes int i, @AnimRes int i2) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, a(activity, i, i2));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i, @Nullable Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i, bundle);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i, View... viewArr) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i, @AnimRes int i2, @AnimRes int i3) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, (Bundle) null, activity.getPackageName(), cls.getName(), i, a(activity, i2, i3));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i2, i3);
            }
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), i, bundle2);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i, View... viewArr) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), i, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull Class<? extends Activity> cls, int i, @AnimRes int i2, @AnimRes int i3) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(activity, bundle, activity.getPackageName(), cls.getName(), i, a(activity, i2, i3));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i2, i3);
            }
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, i, bundle2);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i, View... viewArr) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, i, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Activity activity, @NonNull String str, @NonNull String str2, int i, @AnimRes int i2, @AnimRes int i3) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(activity, bundle, str, str2, i, a(activity, i2, i3));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i2, i3);
            }
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i, @Nullable Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, i, bundle);
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i, View... viewArr) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, i, a(activity, viewArr));
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int i, @AnimRes int i2, @AnimRes int i3) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, activity, i, a(activity, i2, i3));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i2, i3);
            }
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i, @Nullable Bundle bundle) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i, bundle);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i, View... viewArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i, a(fragment, viewArr));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i, @AnimRes int i2, @AnimRes int i3) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, (Bundle) null, Utils.getApp().getPackageName(), cls.getName(), i, a(fragment, i2, i3));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i, bundle2);
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i, View... viewArr) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i, a(fragment, viewArr));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull Class<? extends Activity> cls, int i, @AnimRes int i2, @AnimRes int i3) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (cls != null) {
            a(fragment, bundle, Utils.getApp().getPackageName(), cls.getName(), i, a(fragment, i2, i3));
        } else {
            throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(fragment, bundle, str, str2, i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i, @Nullable Bundle bundle2) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(fragment, bundle, str, str2, i, bundle2);
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i, View... viewArr) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(fragment, bundle, str, str2, i, a(fragment, viewArr));
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 6, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Bundle bundle, @NonNull Fragment fragment, @NonNull String str, @NonNull String str2, int i, @AnimRes int i2, @AnimRes int i3) {
        if (bundle == null) {
            throw new NullPointerException("Argument 'extras' of type Bundle (#0 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#1 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#2 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (str2 != null) {
            a(fragment, bundle, str, str2, i, a(fragment, i2, i3));
        } else {
            throw new NullPointerException("Argument 'cls' of type String (#3 out of 7, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, fragment, i, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i, @Nullable Bundle bundle) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, fragment, i, bundle);
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i, View... viewArr) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, fragment, i, a(fragment, viewArr));
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivityForResult(@NonNull Fragment fragment, @NonNull Intent intent, int i, @AnimRes int i2, @AnimRes int i3) {
        if (fragment == null) {
            throw new NullPointerException("Argument 'fragment' of type Fragment (#0 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intent != null) {
            a(intent, fragment, i, a(fragment, i2, i3));
        } else {
            throw new NullPointerException("Argument 'intent' of type Intent (#1 out of 5, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivities(@NonNull Intent[] intentArr) {
        if (intentArr != null) {
            a(intentArr, a(), (Bundle) null);
            return;
        }
        throw new NullPointerException("Argument 'intents' of type Intent[] (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivities(@NonNull Intent[] intentArr, @Nullable Bundle bundle) {
        if (intentArr != null) {
            a(intentArr, a(), bundle);
            return;
        }
        throw new NullPointerException("Argument 'intents' of type Intent[] (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivities(@NonNull Intent[] intentArr, @AnimRes int i, @AnimRes int i2) {
        if (intentArr != null) {
            Context a = a();
            a(intentArr, a, a(a, i, i2));
            if (Build.VERSION.SDK_INT < 16 && (a instanceof Activity)) {
                ((Activity) a).overridePendingTransition(i, i2);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'intents' of type Intent[] (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intentArr != null) {
            a(intentArr, activity, (Bundle) null);
        } else {
            throw new NullPointerException("Argument 'intents' of type Intent[] (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr, @Nullable Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intentArr != null) {
            a(intentArr, activity, bundle);
        } else {
            throw new NullPointerException("Argument 'intents' of type Intent[] (#1 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startActivities(@NonNull Activity activity, @NonNull Intent[] intentArr, @AnimRes int i, @AnimRes int i2) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (intentArr != null) {
            a(intentArr, activity, a(activity, i, i2));
            if (Build.VERSION.SDK_INT < 16) {
                activity.overridePendingTransition(i, i2);
            }
        } else {
            throw new NullPointerException("Argument 'intents' of type Intent[] (#1 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void startHomeActivity() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(268435456);
        startActivity(intent);
    }

    public static void startLauncherActivity() {
        startLauncherActivity(Utils.getApp().getPackageName());
    }

    public static void startLauncherActivity(@NonNull String str) {
        if (str != null) {
            String launcherActivity = getLauncherActivity(str);
            if (!TextUtils.isEmpty(launcherActivity)) {
                startActivity(str, launcherActivity);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'pkg' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static List<Activity> getActivityList() {
        return b.c();
    }

    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    public static String getLauncherActivity(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (b.o(str)) {
            return "";
        } else {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            List<ResolveInfo> queryIntentActivities = Utils.getApp().getPackageManager().queryIntentActivities(intent, 0);
            return (queryIntentActivities == null || queryIntentActivities.size() == 0) ? "" : queryIntentActivities.get(0).activityInfo.name;
        }
    }

    public static List<String> getMainActivities() {
        return getMainActivities(Utils.getApp().getPackageName());
    }

    public static List<String> getMainActivities(@NonNull String str) {
        if (str != null) {
            ArrayList arrayList = new ArrayList();
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.setPackage(str);
            List<ResolveInfo> queryIntentActivities = Utils.getApp().getPackageManager().queryIntentActivities(intent, 0);
            int size = queryIntentActivities.size();
            if (size == 0) {
                return arrayList;
            }
            for (int i = 0; i < size; i++) {
                ResolveInfo resolveInfo = queryIntentActivities.get(i);
                if (resolveInfo.activityInfo.processName.equals(str)) {
                    arrayList.add(resolveInfo.activityInfo.name);
                }
            }
            return arrayList;
        }
        throw new NullPointerException("Argument 'pkg' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static Activity getTopActivity() {
        return b.b();
    }

    public static boolean isActivityAlive(Context context) {
        return isActivityAlive(getActivityByContext(context));
    }

    public static boolean isActivityAlive(Activity activity) {
        return activity != null && !activity.isFinishing() && (Build.VERSION.SDK_INT < 17 || !activity.isDestroyed());
    }

    public static boolean isActivityExistsInStack(@NonNull Activity activity) {
        if (activity != null) {
            for (Activity activity2 : b.c()) {
                if (activity2.equals(activity)) {
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isActivityExistsInStack(@NonNull Class<? extends Activity> cls) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (activity.getClass().equals(cls)) {
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishActivity(@NonNull Activity activity) {
        if (activity != null) {
            finishActivity(activity, false);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishActivity(@NonNull Activity activity, boolean z) {
        if (activity != null) {
            activity.finish();
            if (!z) {
                activity.overridePendingTransition(0, 0);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishActivity(@NonNull Activity activity, @AnimRes int i, @AnimRes int i2) {
        if (activity != null) {
            activity.finish();
            activity.overridePendingTransition(i, i2);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishActivity(@NonNull Class<? extends Activity> cls) {
        if (cls != null) {
            finishActivity(cls, false);
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishActivity(@NonNull Class<? extends Activity> cls, boolean z) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (activity.getClass().equals(cls)) {
                    activity.finish();
                    if (!z) {
                        activity.overridePendingTransition(0, 0);
                    }
                }
            }
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishActivity(@NonNull Class<? extends Activity> cls, @AnimRes int i, @AnimRes int i2) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (activity.getClass().equals(cls)) {
                    activity.finish();
                    activity.overridePendingTransition(i, i2);
                }
            }
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z) {
        if (activity != null) {
            return finishToActivity(activity, z, false);
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z, boolean z2) {
        if (activity != null) {
            for (Activity activity2 : b.c()) {
                if (!activity2.equals(activity)) {
                    finishActivity(activity2, z2);
                } else if (!z) {
                    return true;
                } else {
                    finishActivity(activity2, z2);
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean finishToActivity(@NonNull Activity activity, boolean z, @AnimRes int i, @AnimRes int i2) {
        if (activity != null) {
            for (Activity activity2 : b.c()) {
                if (!activity2.equals(activity)) {
                    finishActivity(activity2, i, i2);
                } else if (!z) {
                    return true;
                } else {
                    finishActivity(activity2, i, i2);
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean finishToActivity(@NonNull Class<? extends Activity> cls, boolean z) {
        if (cls != null) {
            return finishToActivity(cls, z, false);
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean finishToActivity(@NonNull Class<? extends Activity> cls, boolean z, boolean z2) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (!activity.getClass().equals(cls)) {
                    finishActivity(activity, z2);
                } else if (!z) {
                    return true;
                } else {
                    finishActivity(activity, z2);
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean finishToActivity(@NonNull Class<? extends Activity> cls, boolean z, @AnimRes int i, @AnimRes int i2) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (!activity.getClass().equals(cls)) {
                    finishActivity(activity, i, i2);
                } else if (!z) {
                    return true;
                } else {
                    finishActivity(activity, i, i2);
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishOtherActivities(@NonNull Class<? extends Activity> cls) {
        if (cls != null) {
            finishOtherActivities(cls, false);
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishOtherActivities(@NonNull Class<? extends Activity> cls, boolean z) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (!activity.getClass().equals(cls)) {
                    finishActivity(activity, z);
                }
            }
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishOtherActivities(@NonNull Class<? extends Activity> cls, @AnimRes int i, @AnimRes int i2) {
        if (cls != null) {
            for (Activity activity : b.c()) {
                if (!activity.getClass().equals(cls)) {
                    finishActivity(activity, i, i2);
                }
            }
            return;
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    public static void finishAllActivities(boolean z) {
        for (Activity activity : b.c()) {
            activity.finish();
            if (!z) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    public static void finishAllActivities(@AnimRes int i, @AnimRes int i2) {
        for (Activity activity : b.c()) {
            activity.finish();
            activity.overridePendingTransition(i, i2);
        }
    }

    public static void finishAllActivitiesExceptNewest() {
        finishAllActivitiesExceptNewest(false);
    }

    public static void finishAllActivitiesExceptNewest(boolean z) {
        List<Activity> c = b.c();
        for (int i = 1; i < c.size(); i++) {
            finishActivity(c.get(i), z);
        }
    }

    public static void finishAllActivitiesExceptNewest(@AnimRes int i, @AnimRes int i2) {
        List<Activity> c = b.c();
        for (int i3 = 1; i3 < c.size(); i3++) {
            finishActivity(c.get(i3), i, i2);
        }
    }

    @Nullable
    public static Drawable getActivityIcon(@NonNull Activity activity) {
        if (activity != null) {
            return getActivityIcon(activity.getComponentName());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Nullable
    public static Drawable getActivityIcon(@NonNull Class<? extends Activity> cls) {
        if (cls != null) {
            return getActivityIcon(new ComponentName(Utils.getApp(), cls));
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Nullable
    public static Drawable getActivityIcon(@NonNull ComponentName componentName) {
        if (componentName != null) {
            try {
                return Utils.getApp().getPackageManager().getActivityIcon(componentName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new NullPointerException("Argument 'activityName' of type ComponentName (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    @Nullable
    public static Drawable getActivityLogo(@NonNull Activity activity) {
        if (activity != null) {
            return getActivityLogo(activity.getComponentName());
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Nullable
    public static Drawable getActivityLogo(@NonNull Class<? extends Activity> cls) {
        if (cls != null) {
            return getActivityLogo(new ComponentName(Utils.getApp(), cls));
        }
        throw new NullPointerException("Argument 'clz' of type Class<? extends Activity> (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Nullable
    public static Drawable getActivityLogo(@NonNull ComponentName componentName) {
        if (componentName != null) {
            try {
                return Utils.getApp().getPackageManager().getActivityLogo(componentName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            throw new NullPointerException("Argument 'activityName' of type ComponentName (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    private static void a(Context context, Bundle bundle, String str, String str2, @Nullable Bundle bundle2) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        a(intent, context, bundle2);
    }

    private static boolean a(Intent intent, Context context, Bundle bundle) {
        if (!a(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        if (bundle == null || Build.VERSION.SDK_INT < 16) {
            context.startActivity(intent);
            return true;
        }
        context.startActivity(intent, bundle);
        return true;
    }

    private static boolean a(Intent intent) {
        return Utils.getApp().getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    private static boolean a(Activity activity, Bundle bundle, String str, String str2, int i, @Nullable Bundle bundle2) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        return a(intent, activity, i, bundle2);
    }

    private static boolean a(Intent intent, Activity activity, int i, @Nullable Bundle bundle) {
        if (!a(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        } else if (bundle == null || Build.VERSION.SDK_INT < 16) {
            activity.startActivityForResult(intent, i);
            return true;
        } else {
            activity.startActivityForResult(intent, i, bundle);
            return true;
        }
    }

    private static void a(Intent[] intentArr, Context context, @Nullable Bundle bundle) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intentArr) {
                intent.addFlags(268435456);
            }
        }
        if (bundle == null || Build.VERSION.SDK_INT < 16) {
            context.startActivities(intentArr);
        } else {
            context.startActivities(intentArr, bundle);
        }
    }

    private static boolean a(Fragment fragment, Bundle bundle, String str, String str2, int i, @Nullable Bundle bundle2) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setComponent(new ComponentName(str, str2));
        return a(intent, fragment, i, bundle2);
    }

    private static boolean a(Intent intent, Fragment fragment, int i, @Nullable Bundle bundle) {
        if (!a(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        } else if (fragment.getActivity() == null) {
            Log.e("ActivityUtils", "Fragment " + fragment + " not attached to Activity");
            return false;
        } else if (bundle == null || Build.VERSION.SDK_INT < 16) {
            fragment.startActivityForResult(intent, i);
            return true;
        } else {
            fragment.startActivityForResult(intent, i, bundle);
            return true;
        }
    }

    private static Bundle a(Fragment fragment, int i, int i2) {
        FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            return null;
        }
        return ActivityOptionsCompat.makeCustomAnimation(activity, i, i2).toBundle();
    }

    private static Bundle a(Context context, int i, int i2) {
        return ActivityOptionsCompat.makeCustomAnimation(context, i, i2).toBundle();
    }

    private static Bundle a(Fragment fragment, View[] viewArr) {
        FragmentActivity activity = fragment.getActivity();
        if (activity == null) {
            return null;
        }
        return a(activity, viewArr);
    }

    private static Bundle a(Activity activity, View[] viewArr) {
        int length;
        if (Build.VERSION.SDK_INT < 21 || viewArr == null || (length = viewArr.length) <= 0) {
            return null;
        }
        Pair[] pairArr = new Pair[length];
        for (int i = 0; i < length; i++) {
            pairArr[i] = Pair.create(viewArr[i], viewArr[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairArr).toBundle();
    }

    private static Context a() {
        if (!b.e()) {
            return Utils.getApp();
        }
        Activity topActivity = getTopActivity();
        return topActivity == null ? Utils.getApp() : topActivity;
    }
}
