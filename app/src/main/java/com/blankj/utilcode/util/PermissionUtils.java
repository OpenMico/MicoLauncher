package com.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.Utils;
import com.blankj.utilcode.util.UtilsTransActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public final class PermissionUtils {
    private static PermissionUtils a;
    private static SimpleCallback n;
    private static SimpleCallback o;
    private String[] b;
    private OnExplainListener c;
    private OnRationaleListener d;
    private SingleCallback e;
    private SimpleCallback f;
    private FullCallback g;
    private ThemeCallback h;
    private Set<String> i;
    private List<String> j;
    private List<String> k;
    private List<String> l;
    private List<String> m;

    /* loaded from: classes.dex */
    public interface FullCallback {
        void onDenied(@NonNull List<String> list, @NonNull List<String> list2);

        void onGranted(@NonNull List<String> list);
    }

    /* loaded from: classes.dex */
    public interface OnExplainListener {

        /* loaded from: classes.dex */
        public interface ShouldRequest {
            void start(boolean z);
        }

        void explain(@NonNull UtilsTransActivity utilsTransActivity, @NonNull List<String> list, @NonNull ShouldRequest shouldRequest);
    }

    /* loaded from: classes.dex */
    public interface OnRationaleListener {

        /* loaded from: classes.dex */
        public interface ShouldRequest {
            void again(boolean z);
        }

        void rationale(@NonNull UtilsTransActivity utilsTransActivity, @NonNull ShouldRequest shouldRequest);
    }

    /* loaded from: classes.dex */
    public interface SimpleCallback {
        void onDenied();

        void onGranted();
    }

    /* loaded from: classes.dex */
    public interface SingleCallback {
        void callback(boolean z, @NonNull List<String> list, @NonNull List<String> list2, @NonNull List<String> list3);
    }

    /* loaded from: classes.dex */
    public interface ThemeCallback {
        void onActivityCreate(@NonNull Activity activity);
    }

    public static List<String> getPermissions() {
        return getPermissions(Utils.getApp().getPackageName());
    }

    public static List<String> getPermissions(String str) {
        try {
            String[] strArr = Utils.getApp().getPackageManager().getPackageInfo(str, 4096).requestedPermissions;
            if (strArr == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(strArr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static boolean isGranted(String... strArr) {
        Pair<List<String>, List<String>> a2 = a(strArr);
        if (!((List) a2.second).isEmpty()) {
            return false;
        }
        for (String str : (List) a2.first) {
            if (!a(str)) {
                return false;
            }
        }
        return true;
    }

    private static Pair<List<String>, List<String>> a(String... strArr) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<String> permissions = getPermissions();
        for (String str : strArr) {
            String[] permissions2 = PermissionConstants.getPermissions(str);
            boolean z = false;
            for (String str2 : permissions2) {
                if (permissions.contains(str2)) {
                    arrayList.add(str2);
                    z = true;
                }
            }
            if (!z) {
                arrayList2.add(str);
                Log.e("PermissionUtils", "U should add the permission of " + str + " in manifest.");
            }
        }
        return Pair.create(arrayList, arrayList2);
    }

    private static boolean a(String str) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(Utils.getApp(), str) == 0;
    }

    @RequiresApi(api = 23)
    public static boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(Utils.getApp());
    }

    @RequiresApi(api = 23)
    public static void requestWriteSettings(SimpleCallback simpleCallback) {
        if (!isGrantedWriteSettings()) {
            n = simpleCallback;
            a.a(2);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public static void c(Activity activity, int i) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!b.a(intent)) {
            launchAppDetailsSettings();
        } else {
            activity.startActivityForResult(intent, i);
        }
    }

    @RequiresApi(api = 23)
    public static boolean isGrantedDrawOverlays() {
        return Settings.canDrawOverlays(Utils.getApp());
    }

    @RequiresApi(api = 23)
    public static void requestDrawOverlays(SimpleCallback simpleCallback) {
        if (!isGrantedDrawOverlays()) {
            o = simpleCallback;
            a.a(3);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public static void d(Activity activity, int i) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (!b.a(intent)) {
            launchAppDetailsSettings();
        } else {
            activity.startActivityForResult(intent, i);
        }
    }

    public static void launchAppDetailsSettings() {
        Intent a2 = b.a(Utils.getApp().getPackageName(), true);
        if (b.a(a2)) {
            Utils.getApp().startActivity(a2);
        }
    }

    public static PermissionUtils permissionGroup(String... strArr) {
        return permission(strArr);
    }

    public static PermissionUtils permission(String... strArr) {
        return new PermissionUtils(strArr);
    }

    private PermissionUtils(String... strArr) {
        this.b = strArr;
        a = this;
    }

    public PermissionUtils explain(OnExplainListener onExplainListener) {
        this.c = onExplainListener;
        return this;
    }

    public PermissionUtils rationale(OnRationaleListener onRationaleListener) {
        this.d = onRationaleListener;
        return this;
    }

    public PermissionUtils callback(SingleCallback singleCallback) {
        this.e = singleCallback;
        return this;
    }

    public PermissionUtils callback(SimpleCallback simpleCallback) {
        this.f = simpleCallback;
        return this;
    }

    public PermissionUtils callback(FullCallback fullCallback) {
        this.g = fullCallback;
        return this;
    }

    public PermissionUtils theme(ThemeCallback themeCallback) {
        this.h = themeCallback;
        return this;
    }

    public void request() {
        String[] strArr = this.b;
        if (strArr == null || strArr.length <= 0) {
            Log.w("PermissionUtils", "No permissions to request.");
            return;
        }
        this.i = new LinkedHashSet();
        this.j = new ArrayList();
        this.k = new ArrayList();
        this.l = new ArrayList();
        this.m = new ArrayList();
        Pair<List<String>, List<String>> a2 = a(this.b);
        this.i.addAll((Collection) a2.first);
        this.l.addAll((Collection) a2.second);
        if (Build.VERSION.SDK_INT < 23) {
            this.k.addAll(this.i);
            e();
            return;
        }
        for (String str : this.i) {
            if (a(str)) {
                this.k.add(str);
            } else {
                this.j.add(str);
            }
        }
        if (this.j.isEmpty()) {
            e();
        } else {
            d();
        }
    }

    @RequiresApi(api = 23)
    private void d() {
        a.a(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 23)
    public boolean a(UtilsTransActivity utilsTransActivity, Runnable runnable) {
        boolean z = false;
        if (this.d != null) {
            Iterator<String> it = this.j.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (utilsTransActivity.shouldShowRequestPermissionRationale(it.next())) {
                        b(utilsTransActivity, runnable);
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            this.d = null;
        }
        return z;
    }

    private void b(final UtilsTransActivity utilsTransActivity, final Runnable runnable) {
        a(utilsTransActivity);
        this.d.rationale(utilsTransActivity, new OnRationaleListener.ShouldRequest() { // from class: com.blankj.utilcode.util.PermissionUtils.1
            @Override // com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest
            public void again(boolean z) {
                if (z) {
                    PermissionUtils.this.l = new ArrayList();
                    PermissionUtils.this.m = new ArrayList();
                    runnable.run();
                    return;
                }
                utilsTransActivity.finish();
                PermissionUtils.this.e();
            }
        });
    }

    private void a(Activity activity) {
        for (String str : this.j) {
            if (a(str)) {
                this.k.add(str);
            } else {
                this.l.add(str);
                if (!activity.shouldShowRequestPermissionRationale(str)) {
                    this.m.add(str);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        SingleCallback singleCallback = this.e;
        if (singleCallback != null) {
            singleCallback.callback(this.l.isEmpty(), this.k, this.m, this.l);
            this.e = null;
        }
        if (this.f != null) {
            if (this.l.isEmpty()) {
                this.f.onGranted();
            } else {
                this.f.onDenied();
            }
            this.f = null;
        }
        if (this.g != null) {
            if (this.j.size() == 0 || this.k.size() > 0) {
                this.g.onGranted(this.k);
            }
            if (!this.l.isEmpty()) {
                this.g.onDenied(this.m, this.l);
            }
            this.g = null;
        }
        this.d = null;
        this.h = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Activity activity) {
        a(activity);
        e();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(api = 23)
    /* loaded from: classes.dex */
    public static final class a extends UtilsTransActivity.TransActivityDelegate {
        private static int a = -1;
        private static a b = new a();

        a() {
        }

        public static void a(final int i) {
            UtilsTransActivity.start(new Utils.Consumer<Intent>() { // from class: com.blankj.utilcode.util.PermissionUtils.a.1
                /* renamed from: a */
                public void accept(Intent intent) {
                    intent.putExtra("TYPE", i);
                }
            }, b);
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onCreated(@NonNull final UtilsTransActivity utilsTransActivity, @Nullable Bundle bundle) {
            if (utilsTransActivity != null) {
                utilsTransActivity.getWindow().addFlags(262160);
                int intExtra = utilsTransActivity.getIntent().getIntExtra("TYPE", -1);
                if (intExtra == 1) {
                    if (PermissionUtils.a == null) {
                        Log.e("PermissionUtils", "sInstance is null.");
                        utilsTransActivity.finish();
                    } else if (PermissionUtils.a.j == null) {
                        Log.e("PermissionUtils", "mPermissionsRequest is null.");
                        utilsTransActivity.finish();
                    } else if (PermissionUtils.a.j.size() <= 0) {
                        Log.e("PermissionUtils", "mPermissionsRequest's size is no more than 0.");
                        utilsTransActivity.finish();
                    } else {
                        if (PermissionUtils.a.h != null) {
                            PermissionUtils.a.h.onActivityCreate(utilsTransActivity);
                        }
                        if (PermissionUtils.a.c != null) {
                            PermissionUtils.a.c.explain(utilsTransActivity, PermissionUtils.a.j, new OnExplainListener.ShouldRequest() { // from class: com.blankj.utilcode.util.PermissionUtils.a.2
                                @Override // com.blankj.utilcode.util.PermissionUtils.OnExplainListener.ShouldRequest
                                public void start(boolean z) {
                                    if (!z) {
                                        utilsTransActivity.finish();
                                    } else {
                                        a.this.a(utilsTransActivity);
                                    }
                                }
                            });
                            PermissionUtils.a.c = null;
                            return;
                        }
                        a(utilsTransActivity);
                    }
                } else if (intExtra == 2) {
                    a = 2;
                    PermissionUtils.c(utilsTransActivity, 2);
                } else if (intExtra == 3) {
                    a = 3;
                    PermissionUtils.d(utilsTransActivity, 3);
                } else {
                    utilsTransActivity.finish();
                    Log.e("PermissionUtils", "type is wrong.");
                }
            } else {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(final UtilsTransActivity utilsTransActivity) {
            if (!PermissionUtils.a.a(utilsTransActivity, new Runnable() { // from class: com.blankj.utilcode.util.PermissionUtils.a.3
                @Override // java.lang.Runnable
                public void run() {
                    utilsTransActivity.requestPermissions((String[]) PermissionUtils.a.j.toArray(new String[0]), 1);
                }
            })) {
                utilsTransActivity.requestPermissions((String[]) PermissionUtils.a.j.toArray(new String[0]), 1);
            }
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onRequestPermissionsResult(@NonNull UtilsTransActivity utilsTransActivity, int i, @NonNull String[] strArr, @NonNull int[] iArr) {
            if (utilsTransActivity == null) {
                throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (strArr == null) {
                throw new NullPointerException("Argument 'permissions' of type String[] (#2 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (iArr != null) {
                utilsTransActivity.finish();
                if (PermissionUtils.a != null && PermissionUtils.a.j != null) {
                    PermissionUtils.a.b(utilsTransActivity);
                }
            } else {
                throw new NullPointerException("Argument 'grantResults' of type int[] (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public boolean dispatchTouchEvent(@NonNull UtilsTransActivity utilsTransActivity, MotionEvent motionEvent) {
            if (utilsTransActivity != null) {
                utilsTransActivity.finish();
                return true;
            }
            throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onDestroy(@NonNull UtilsTransActivity utilsTransActivity) {
            if (utilsTransActivity != null) {
                int i = a;
                if (i != -1) {
                    b(i);
                    a = -1;
                }
                super.onDestroy(utilsTransActivity);
                return;
            }
            throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onActivityResult(@NonNull UtilsTransActivity utilsTransActivity, int i, int i2, Intent intent) {
            if (utilsTransActivity != null) {
                utilsTransActivity.finish();
                return;
            }
            throw new NullPointerException("Argument 'activity' of type UtilsTransActivity (#0 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        private void b(int i) {
            if (i == 2) {
                if (PermissionUtils.n != null) {
                    if (PermissionUtils.isGrantedWriteSettings()) {
                        PermissionUtils.n.onGranted();
                    } else {
                        PermissionUtils.n.onDenied();
                    }
                    SimpleCallback unused = PermissionUtils.n = null;
                }
            } else if (i == 3 && PermissionUtils.o != null) {
                if (PermissionUtils.isGrantedDrawOverlays()) {
                    PermissionUtils.o.onGranted();
                } else {
                    PermissionUtils.o.onDenied();
                }
                SimpleCallback unused2 = PermissionUtils.o = null;
            }
        }
    }
}
