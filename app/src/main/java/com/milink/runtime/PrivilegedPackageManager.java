package com.milink.runtime;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import androidx.annotation.NonNull;
import com.milink.base.utils.Logger;
import com.milink.base.utils.SignatureUtils;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class PrivilegedPackageManager {
    private static final String[] a;
    private static final PrivilegedPackageManager b = new PrivilegedPackageManager();
    private final Map<String, String> c = new ConcurrentHashMap();
    private final Map<Integer, String> d = new ConcurrentHashMap();
    private String e;

    static {
        boolean endsWith = "0.1.4".endsWith("SNAPSHOT");
        String[] strArr = new String[5];
        strArr[0] = "7B6DC7079C34739CE81159719FB5EB61D2A03225";
        strArr[1] = "B3D1CE9C2C6403E9685324BCD57F677B13A53174";
        strArr[2] = "E22CDA406937EAC46BDE7AA2F2092DAACC9EFFF6";
        strArr[3] = "0832F8EB8BB228121A6EA90CADD89D582CB19C7D";
        strArr[4] = endsWith ? "27196E386B875E76ADF700E7EA84E4C6EEE33DFA" : "NULL";
        Arrays.sort(strArr);
        a = strArr;
    }

    public static boolean isPrivilegedPackage(Context context, String str) {
        return b.a(context, str);
    }

    private static int b(@NonNull Context context, @NonNull String str) {
        if (context.getPackageName().equals(str)) {
            return context.getApplicationInfo().uid;
        }
        int callingUid = Binder.getCallingUid();
        if (callingUid != context.getApplicationInfo().uid) {
            return callingUid;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                return packageManager.getPackageUid(str, 0);
            }
            return packageManager.getApplicationInfo(str, 0).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            return -1;
        }
    }

    boolean a(@NonNull Context context, String str) {
        String str2;
        int b2 = b(context, str);
        if (b2 != -1 && (str2 = this.d.get(Integer.valueOf(b2))) != null && str2.equals(str)) {
            return true;
        }
        boolean c = c(context, str);
        if (c && b2 != -1) {
            this.d.put(Integer.valueOf(b2), str);
        }
        return c;
    }

    private boolean c(@NonNull Context context, String str) {
        if ((context.getApplicationInfo().flags & 2) != 0) {
            Logger.d("PrivilegedPackageManager", "Privileged -- for debug", new Object[0]);
            return true;
        }
        try {
            if ((context.getPackageManager().getApplicationInfo(str, 0).flags & 129) != 0) {
                Logger.d("PrivilegedPackageManager", "Privileged type: system", new Object[0]);
                return true;
            }
            String signature = SignatureUtils.getSignature(context, str);
            if (signature == null) {
                Logger.e("PrivilegedPackageManager", "rejected access for %s, because not found signature.", str);
                return false;
            } else if (Objects.equals(a(context), signature)) {
                Logger.d("PrivilegedPackageManager", "Privileged type: same signature", new Object[0]);
                return true;
            } else if (Arrays.binarySearch(a, signature) >= 0) {
                Logger.d("PrivilegedPackageManager", "Privileged type: internal", new Object[0]);
                return true;
            } else {
                String str2 = this.c.get(str);
                if (str2 == null || !str2.equalsIgnoreCase(signature)) {
                    Logger.d("PrivilegedPackageManager", "Not a privilege package : %s", str);
                    return false;
                }
                Logger.d("PrivilegedPackageManager", "Privileged type: white list", new Object[0]);
                return true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Logger.w("PrivilegedPackageManager", "Check calling package, but not exist this app.", new Object[0]);
            return false;
        }
    }

    private String a(Context context) {
        if (this.e == null) {
            this.e = SignatureUtils.getSignature(context, context.getPackageName());
        }
        return this.e;
    }
}
