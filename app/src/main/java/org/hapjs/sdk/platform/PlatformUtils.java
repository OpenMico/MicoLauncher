package org.hapjs.sdk.platform;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes5.dex */
public class PlatformUtils {
    private static Boolean a;

    public static void init(Context context) {
        d.a(context);
        if (a == null) {
            setNetworkAvailable(context, true);
        }
    }

    public static void setNetworkAvailable(Context context, boolean z) {
        Boolean bool = a;
        if (bool == null || bool.booleanValue() != z) {
            a = Boolean.valueOf(z);
            if (a.booleanValue()) {
                d.b(context);
            }
        }
    }

    public static String chooseBestPlatform(Context context, boolean z) {
        Map<String, String> a2 = d.a();
        String str = null;
        if (a2 == null || a2.isEmpty()) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        String a3 = c.a(packageManager, "android");
        Iterator<String> it = a2.keySet().iterator();
        String str2 = null;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            PackageInfo b = c.b(packageManager, next);
            if (b != null) {
                boolean z2 = false;
                String a4 = c.a(b.signatures[0].toByteArray());
                if (!(!TextUtils.equals(a4, a3) && (b.applicationInfo.flags & 1) == 0 && (b.applicationInfo.flags & 128) == 0)) {
                    z2 = true;
                }
                if (z || TextUtils.equals(a4, a2.get(next)) || z2) {
                    if (TextUtils.isEmpty(str2) || TextUtils.equals(str2, "org.hapjs.mockup")) {
                        str2 = next;
                    }
                    if (z2 && TextUtils.isEmpty(null)) {
                        str = next;
                        break;
                    }
                }
            }
        }
        return !TextUtils.isEmpty(str) ? str : str2;
    }

    public static boolean isTrustedHost(Context context, String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        String a2 = c.a(packageManager, str);
        if (TextUtils.isEmpty(a2)) {
            return false;
        }
        if (z || "390ac61475dfb8799c5e6250d0b914439432e3153d4532e23c94ef2720275a54".equals(a2)) {
            return true;
        }
        String chooseBestPlatform = chooseBestPlatform(context, z);
        return !TextUtils.isEmpty(chooseBestPlatform) && TextUtils.equals(a2, c.a(packageManager, chooseBestPlatform));
    }
}
