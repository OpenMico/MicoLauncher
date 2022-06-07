package androidx.core.telephony;

import android.os.Build;
import android.telephony.SubscriptionManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(22)
/* loaded from: classes.dex */
public class SubscriptionManagerCompat {
    private static Method a;

    public static int getSlotIndex(int i) {
        if (i == -1) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return a.a(i);
        }
        try {
            if (a == null) {
                if (Build.VERSION.SDK_INT >= 26) {
                    a = SubscriptionManager.class.getDeclaredMethod("getSlotIndex", Integer.TYPE);
                } else {
                    a = SubscriptionManager.class.getDeclaredMethod("getSlotId", Integer.TYPE);
                }
                a.setAccessible(true);
            }
            Integer num = (Integer) a.invoke(null, Integer.valueOf(i));
            if (num != null) {
                return num.intValue();
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        return -1;
    }

    private SubscriptionManagerCompat() {
    }

    @RequiresApi(29)
    /* loaded from: classes.dex */
    private static class a {
        @DoNotInline
        static int a(int i) {
            return SubscriptionManager.getSlotIndex(i);
        }
    }
}
