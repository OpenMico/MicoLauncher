package androidx.core.content.pm;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutInfoCompatSaver;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import com.xiaomi.idm.api.IDMServer;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class ShortcutManagerCompat {
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    public static final int FLAG_MATCH_CACHED = 8;
    public static final int FLAG_MATCH_DYNAMIC = 2;
    public static final int FLAG_MATCH_MANIFEST = 1;
    public static final int FLAG_MATCH_PINNED = 4;
    private static volatile ShortcutInfoCompatSaver<?> a;
    private static volatile List<ShortcutInfoChangeListener> b;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface ShortcutMatchFlags {
    }

    private ShortcutManagerCompat() {
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isRequestPinShortcutSupported(@androidx.annotation.NonNull android.content.Context r3) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 26
            if (r0 < r1) goto L_0x0013
            java.lang.Class<android.content.pm.ShortcutManager> r0 = android.content.pm.ShortcutManager.class
            java.lang.Object r3 = r3.getSystemService(r0)
            android.content.pm.ShortcutManager r3 = (android.content.pm.ShortcutManager) r3
            boolean r3 = r3.isRequestPinShortcutSupported()
            return r3
        L_0x0013:
            java.lang.String r0 = "com.android.launcher.permission.INSTALL_SHORTCUT"
            int r0 = androidx.core.content.ContextCompat.checkSelfPermission(r3, r0)
            r1 = 0
            if (r0 == 0) goto L_0x001d
            return r1
        L_0x001d:
            android.content.pm.PackageManager r3 = r3.getPackageManager()
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r2 = "com.android.launcher.action.INSTALL_SHORTCUT"
            r0.<init>(r2)
            java.util.List r3 = r3.queryBroadcastReceivers(r0, r1)
            java.util.Iterator r3 = r3.iterator()
        L_0x0030:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x0050
            java.lang.Object r0 = r3.next()
            android.content.pm.ResolveInfo r0 = (android.content.pm.ResolveInfo) r0
            android.content.pm.ActivityInfo r0 = r0.activityInfo
            java.lang.String r0 = r0.permission
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x004e
            java.lang.String r2 = "com.android.launcher.permission.INSTALL_SHORTCUT"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0030
        L_0x004e:
            r3 = 1
            return r3
        L_0x0050:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.pm.ShortcutManagerCompat.isRequestPinShortcutSupported(android.content.Context):boolean");
    }

    public static boolean requestPinShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat, @Nullable final IntentSender intentSender) {
        if (Build.VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcutInfoCompat.toShortcutInfo(), intentSender);
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false;
        }
        Intent a2 = shortcutInfoCompat.a(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
        if (intentSender == null) {
            context.sendBroadcast(a2);
            return true;
        }
        context.sendOrderedBroadcast(a2, null, new BroadcastReceiver() { // from class: androidx.core.content.pm.ShortcutManagerCompat.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                try {
                    intentSender.sendIntent(context2, 0, null, null, null);
                } catch (IntentSender.SendIntentException unused) {
                }
            }
        }, null, -1, null, null);
        return true;
    }

    @NonNull
    public static Intent createShortcutResultIntent(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Intent createShortcutResultIntent = Build.VERSION.SDK_INT >= 26 ? ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcutInfoCompat.toShortcutInfo()) : null;
        if (createShortcutResultIntent == null) {
            createShortcutResultIntent = new Intent();
        }
        return shortcutInfoCompat.a(createShortcutResultIntent);
    }

    @NonNull
    public static List<ShortcutInfoCompat> getShortcuts(@NonNull Context context, int i) {
        if (Build.VERSION.SDK_INT >= 30) {
            return ShortcutInfoCompat.a(context, ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getShortcuts(i));
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
            ArrayList arrayList = new ArrayList();
            if ((i & 1) != 0) {
                arrayList.addAll(shortcutManager.getManifestShortcuts());
            }
            if ((i & 2) != 0) {
                arrayList.addAll(shortcutManager.getDynamicShortcuts());
            }
            if ((i & 4) != 0) {
                arrayList.addAll(shortcutManager.getPinnedShortcuts());
            }
            return ShortcutInfoCompat.a(context, arrayList);
        }
        if ((i & 2) != 0) {
            try {
                return a(context).getShortcuts();
            } catch (Exception unused) {
            }
        }
        return Collections.emptyList();
    }

    public static boolean addDynamicShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        if (Build.VERSION.SDK_INT <= 29) {
            a(context, list);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList();
            for (ShortcutInfoCompat shortcutInfoCompat : list) {
                arrayList.add(shortcutInfoCompat.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).addDynamicShortcuts(arrayList)) {
                return false;
            }
        }
        a(context).addShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutAdded(list);
        }
        return true;
    }

    public static int getMaxShortcutCountPerActivity(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getMaxShortcutCountPerActivity();
        }
        return 5;
    }

    public static boolean isRateLimitingActive(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRateLimitingActive();
        }
        return getShortcuts(context, 3).size() == getMaxShortcutCountPerActivity(context);
    }

    public static int getIconMaxWidth(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getIconMaxWidth();
        }
        return a(context, true);
    }

    public static int getIconMaxHeight(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getIconMaxHeight();
        }
        return a(context, false);
    }

    public static void reportShortcutUsed(@NonNull Context context, @NonNull String str) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(str);
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).reportShortcutUsed(str);
        }
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutUsageReported(Collections.singletonList(str));
        }
    }

    public static boolean setDynamicShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(list);
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList(list.size());
            for (ShortcutInfoCompat shortcutInfoCompat : list) {
                arrayList.add(shortcutInfoCompat.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).setDynamicShortcuts(arrayList)) {
                return false;
            }
        }
        a(context).removeAllShortcuts();
        a(context).addShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onAllShortcutsRemoved();
            shortcutInfoChangeListener.onShortcutAdded(list);
        }
        return true;
    }

    @NonNull
    public static List<ShortcutInfoCompat> getDynamicShortcuts(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            List<ShortcutInfo> dynamicShortcuts = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getDynamicShortcuts();
            ArrayList arrayList = new ArrayList(dynamicShortcuts.size());
            for (ShortcutInfo shortcutInfo : dynamicShortcuts) {
                arrayList.add(new ShortcutInfoCompat.Builder(context, shortcutInfo).build());
            }
            return arrayList;
        }
        try {
            return a(context).getShortcuts();
        } catch (Exception unused) {
            return new ArrayList();
        }
    }

    public static boolean updateShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        if (Build.VERSION.SDK_INT <= 29) {
            a(context, list);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList();
            for (ShortcutInfoCompat shortcutInfoCompat : list) {
                arrayList.add(shortcutInfoCompat.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).updateShortcuts(arrayList)) {
                return false;
            }
        }
        a(context).addShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutUpdated(list);
        }
        return true;
    }

    @VisibleForTesting
    static boolean a(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Bitmap decodeStream;
        IconCompat iconCompat;
        if (shortcutInfoCompat.i == null) {
            return false;
        }
        int i = shortcutInfoCompat.i.mType;
        if (i != 6 && i != 4) {
            return true;
        }
        InputStream uriInputStream = shortcutInfoCompat.i.getUriInputStream(context);
        if (uriInputStream == null || (decodeStream = BitmapFactory.decodeStream(uriInputStream)) == null) {
            return false;
        }
        if (i == 6) {
            iconCompat = IconCompat.createWithAdaptiveBitmap(decodeStream);
        } else {
            iconCompat = IconCompat.createWithBitmap(decodeStream);
        }
        shortcutInfoCompat.i = iconCompat;
        return true;
    }

    @VisibleForTesting
    static void a(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        for (ShortcutInfoCompat shortcutInfoCompat : new ArrayList(list)) {
            if (!a(context, shortcutInfoCompat)) {
                list.remove(shortcutInfoCompat);
            }
        }
    }

    public static void disableShortcuts(@NonNull Context context, @NonNull List<String> list, @Nullable CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).disableShortcuts(list, charSequence);
        }
        a(context).removeShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutRemoved(list);
        }
    }

    public static void enableShortcuts(@NonNull Context context, @NonNull List<ShortcutInfoCompat> list) {
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList arrayList = new ArrayList(list.size());
            for (ShortcutInfoCompat shortcutInfoCompat : list) {
                arrayList.add(shortcutInfoCompat.b);
            }
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).enableShortcuts(arrayList);
        }
        a(context).addShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutAdded(list);
        }
    }

    public static void removeDynamicShortcuts(@NonNull Context context, @NonNull List<String> list) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeDynamicShortcuts(list);
        }
        a(context).removeShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutRemoved(list);
        }
    }

    public static void removeAllDynamicShortcuts(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeAllDynamicShortcuts();
        }
        a(context).removeAllShortcuts();
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onAllShortcutsRemoved();
        }
    }

    public static void removeLongLivedShortcuts(@NonNull Context context, @NonNull List<String> list) {
        if (Build.VERSION.SDK_INT < 30) {
            removeDynamicShortcuts(context, list);
            return;
        }
        ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeLongLivedShortcuts(list);
        a(context).removeShortcuts(list);
        for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
            shortcutInfoChangeListener.onShortcutRemoved(list);
        }
    }

    public static boolean pushDynamicShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(shortcutInfoCompat);
        int maxShortcutCountPerActivity = getMaxShortcutCountPerActivity(context);
        if (maxShortcutCountPerActivity == 0) {
            return false;
        }
        if (Build.VERSION.SDK_INT <= 29) {
            a(context, shortcutInfoCompat);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).pushDynamicShortcut(shortcutInfoCompat.toShortcutInfo());
        } else if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
            if (shortcutManager.isRateLimitingActive()) {
                return false;
            }
            List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
            if (dynamicShortcuts.size() >= maxShortcutCountPerActivity) {
                shortcutManager.removeDynamicShortcuts(Arrays.asList(a.a(dynamicShortcuts)));
            }
            shortcutManager.addDynamicShortcuts(Arrays.asList(shortcutInfoCompat.toShortcutInfo()));
        }
        ShortcutInfoCompatSaver<?> a2 = a(context);
        try {
            List<ShortcutInfoCompat> shortcuts = a2.getShortcuts();
            if (shortcuts.size() >= maxShortcutCountPerActivity) {
                a2.removeShortcuts(Arrays.asList(a(shortcuts)));
            }
            a2.addShortcuts(Arrays.asList(shortcutInfoCompat));
            for (ShortcutInfoChangeListener shortcutInfoChangeListener : b(context)) {
                shortcutInfoChangeListener.onShortcutAdded(Collections.singletonList(shortcutInfoCompat));
            }
            reportShortcutUsed(context, shortcutInfoCompat.getId());
            return true;
        } catch (Exception unused) {
            for (ShortcutInfoChangeListener shortcutInfoChangeListener2 : b(context)) {
                shortcutInfoChangeListener2.onShortcutAdded(Collections.singletonList(shortcutInfoCompat));
            }
            reportShortcutUsed(context, shortcutInfoCompat.getId());
            return false;
        } catch (Throwable th) {
            for (ShortcutInfoChangeListener shortcutInfoChangeListener3 : b(context)) {
                shortcutInfoChangeListener3.onShortcutAdded(Collections.singletonList(shortcutInfoCompat));
            }
            reportShortcutUsed(context, shortcutInfoCompat.getId());
            throw th;
        }
    }

    private static String a(@NonNull List<ShortcutInfoCompat> list) {
        int i = -1;
        String str = null;
        for (ShortcutInfoCompat shortcutInfoCompat : list) {
            if (shortcutInfoCompat.getRank() > i) {
                str = shortcutInfoCompat.getId();
                i = shortcutInfoCompat.getRank();
            }
        }
        return str;
    }

    private static int a(@NonNull Context context, boolean z) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        int max = Math.max(1, Build.VERSION.SDK_INT < 19 || activityManager == null || activityManager.isLowRamDevice() ? 48 : 96);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (max * ((z ? displayMetrics.xdpi : displayMetrics.ydpi) / 160.0f));
    }

    private static ShortcutInfoCompatSaver<?> a(Context context) {
        if (a == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    a = (ShortcutInfoCompatSaver) Class.forName("androidx.sharetarget.ShortcutInfoCompatSaverImpl", false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", Context.class).invoke(null, context);
                } catch (Exception unused) {
                }
            }
            if (a == null) {
                a = new ShortcutInfoCompatSaver.NoopImpl();
            }
        }
        return a;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List<androidx.core.content.pm.ShortcutInfoChangeListener> b(android.content.Context r8) {
        /*
            java.util.List<androidx.core.content.pm.ShortcutInfoChangeListener> r0 = androidx.core.content.pm.ShortcutManagerCompat.b
            if (r0 != 0) goto L_0x0077
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r1 < r2) goto L_0x0071
            android.content.pm.PackageManager r1 = r8.getPackageManager()
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = "androidx.core.content.pm.SHORTCUT_LISTENER"
            r2.<init>(r3)
            java.lang.String r3 = r8.getPackageName()
            r2.setPackage(r3)
            r3 = 128(0x80, float:1.794E-43)
            java.util.List r1 = r1.queryIntentActivities(r2, r3)
            java.util.Iterator r1 = r1.iterator()
        L_0x002b:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0071
            java.lang.Object r2 = r1.next()
            android.content.pm.ResolveInfo r2 = (android.content.pm.ResolveInfo) r2
            android.content.pm.ActivityInfo r2 = r2.activityInfo
            if (r2 != 0) goto L_0x003c
            goto L_0x002b
        L_0x003c:
            android.os.Bundle r2 = r2.metaData
            if (r2 != 0) goto L_0x0041
            goto L_0x002b
        L_0x0041:
            java.lang.String r3 = "androidx.core.content.pm.shortcut_listener_impl"
            java.lang.String r2 = r2.getString(r3)
            if (r2 != 0) goto L_0x004a
            goto L_0x002b
        L_0x004a:
            java.lang.Class<androidx.core.content.pm.ShortcutManagerCompat> r3 = androidx.core.content.pm.ShortcutManagerCompat.class
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch: Exception -> 0x002b
            r4 = 0
            java.lang.Class r2 = java.lang.Class.forName(r2, r4, r3)     // Catch: Exception -> 0x002b
            java.lang.String r3 = "getInstance"
            r5 = 1
            java.lang.Class[] r6 = new java.lang.Class[r5]     // Catch: Exception -> 0x002b
            java.lang.Class<android.content.Context> r7 = android.content.Context.class
            r6[r4] = r7     // Catch: Exception -> 0x002b
            java.lang.reflect.Method r2 = r2.getMethod(r3, r6)     // Catch: Exception -> 0x002b
            r3 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: Exception -> 0x002b
            r5[r4] = r8     // Catch: Exception -> 0x002b
            java.lang.Object r2 = r2.invoke(r3, r5)     // Catch: Exception -> 0x002b
            androidx.core.content.pm.ShortcutInfoChangeListener r2 = (androidx.core.content.pm.ShortcutInfoChangeListener) r2     // Catch: Exception -> 0x002b
            r0.add(r2)     // Catch: Exception -> 0x002b
            goto L_0x002b
        L_0x0071:
            java.util.List<androidx.core.content.pm.ShortcutInfoChangeListener> r8 = androidx.core.content.pm.ShortcutManagerCompat.b
            if (r8 != 0) goto L_0x0077
            androidx.core.content.pm.ShortcutManagerCompat.b = r0
        L_0x0077:
            java.util.List<androidx.core.content.pm.ShortcutInfoChangeListener> r8 = androidx.core.content.pm.ShortcutManagerCompat.b
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.pm.ShortcutManagerCompat.b(android.content.Context):java.util.List");
    }

    @RequiresApi(25)
    /* loaded from: classes.dex */
    private static class a {
        static String a(@NonNull List<ShortcutInfo> list) {
            int i = -1;
            String str = null;
            for (ShortcutInfo shortcutInfo : list) {
                if (shortcutInfo.getRank() > i) {
                    str = shortcutInfo.getId();
                    i = shortcutInfo.getRank();
                }
            }
            return str;
        }
    }
}
