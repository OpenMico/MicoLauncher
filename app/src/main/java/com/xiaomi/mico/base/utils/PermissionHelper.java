package com.xiaomi.mico.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PermissionHelper {
    private final Activity b;
    private Listener d;
    private boolean e;
    private int a = 0;
    private HashMap<String, a> c = new HashMap<>();

    /* loaded from: classes3.dex */
    public interface Listener {
        void onAllPermissionGranted();

        void onGoToSetting();

        void onPermissionDenied(String str);
    }

    public PermissionHelper(Activity activity) {
        this.b = activity;
    }

    public PermissionHelper listener(Listener listener) {
        this.d = listener;
        return this;
    }

    public PermissionHelper withPermission(String str, @StringRes int i, @StringRes int i2) {
        withPermission(str, i, i2, false);
        return this;
    }

    public PermissionHelper withPermission(String str, @StringRes int i, @StringRes int i2, boolean z) {
        this.a++;
        this.c.put(str, new a(str, this.a, i, i2, z));
        return this;
    }

    public void check() {
        if (!this.e) {
            for (a aVar : this.c.values()) {
                if (!(aVar.e || a(aVar.a, aVar.b, aVar.g) == 0)) {
                    return;
                }
            }
            Listener listener = this.d;
            if (listener != null) {
                listener.onAllPermissionGranted();
            }
        }
    }

    public static void gotoPermissionSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addFlags(268435456);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    private int a(String str, int i, int i2) {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this.b, str);
        Log.i("PermissionHelper", String.format("%s %d", str, Integer.valueOf(checkSelfPermission)));
        if (checkSelfPermission != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.b, str)) {
                Log.i("PermissionHelper", "showRequestPermissionRationale");
                this.e = true;
            } else {
                ActivityCompat.requestPermissions(this.b, new String[]{str}, i2);
                Log.i("PermissionHelper", "requestPermissions");
            }
        }
        return checkSelfPermission;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (!(strArr == null || strArr.length == 0)) {
            Log.i("PermissionHelper", String.format("onRequestPermissionsResult %s %s", strArr[0], Integer.valueOf(iArr[0])));
            for (a aVar : this.c.values()) {
                if (aVar.g == i) {
                    if (iArr.length <= 0 || iArr[0] != 0) {
                        a aVar2 = this.c.get(strArr[0]);
                        if (aVar2.d) {
                            aVar2.e = true;
                            check();
                        } else {
                            this.e = true;
                        }
                    } else {
                        check();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a {
        public String a;
        @StringRes
        public int b;
        @StringRes
        public int c;
        public boolean d;
        public boolean e;
        private final int g;

        public a(String str, int i, int i2, int i3, boolean z) {
            this.d = false;
            this.a = str;
            this.b = i2;
            this.c = i3;
            this.d = z;
            this.g = i;
        }
    }
}
