package androidx.core.telephony;

import android.annotation.SuppressLint;
import android.os.Build;
import android.telephony.TelephonyManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class TelephonyManagerCompat {
    private static Method a;
    private static Method b;

    @Nullable
    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @SuppressLint({"MissingPermission"})
    public static String getImei(@NonNull TelephonyManager telephonyManager) {
        int subscriptionId;
        if (Build.VERSION.SDK_INT >= 26) {
            return b.a(telephonyManager);
        }
        if (Build.VERSION.SDK_INT < 22 || (subscriptionId = getSubscriptionId(telephonyManager)) == Integer.MAX_VALUE || subscriptionId == -1) {
            return telephonyManager.getDeviceId();
        }
        int slotIndex = SubscriptionManagerCompat.getSlotIndex(subscriptionId);
        if (Build.VERSION.SDK_INT >= 23) {
            return a.a(telephonyManager, slotIndex);
        }
        try {
            if (a == null) {
                a = TelephonyManager.class.getDeclaredMethod("getDeviceId", Integer.TYPE);
                a.setAccessible(true);
            }
            return (String) a.invoke(telephonyManager, Integer.valueOf(slotIndex));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return null;
        }
    }

    @SuppressLint({"SoonBlockedPrivateApi"})
    public static int getSubscriptionId(@NonNull TelephonyManager telephonyManager) {
        if (Build.VERSION.SDK_INT >= 30) {
            return c.a(telephonyManager);
        }
        if (Build.VERSION.SDK_INT < 22) {
            return Integer.MAX_VALUE;
        }
        try {
            if (b == null) {
                b = TelephonyManager.class.getDeclaredMethod("getSubId", new Class[0]);
                b.setAccessible(true);
            }
            Integer num = (Integer) b.invoke(telephonyManager, new Object[0]);
            if (num == null || num.intValue() == -1) {
                return Integer.MAX_VALUE;
            }
            return num.intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return Integer.MAX_VALUE;
        }
    }

    private TelephonyManagerCompat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class c {
        @DoNotInline
        static int a(TelephonyManager telephonyManager) {
            return telephonyManager.getSubscriptionId();
        }
    }

    @RequiresApi(26)
    /* loaded from: classes.dex */
    private static class b {
        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission("android.permission.READ_PHONE_STATE")
        @DoNotInline
        static String a(TelephonyManager telephonyManager) {
            return telephonyManager.getImei();
        }
    }

    @RequiresApi(23)
    /* loaded from: classes.dex */
    private static class a {
        @SuppressLint({"MissingPermission"})
        @Nullable
        @RequiresPermission("android.permission.READ_PHONE_STATE")
        @DoNotInline
        static String a(TelephonyManager telephonyManager, int i) {
            return telephonyManager.getDeviceId(i);
        }
    }
}
