package androidx.work.impl.utils;

import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import java.util.HashMap;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WakeLocks {
    private static final String a = Logger.tagWithPrefix("WakeLocks");
    private static final WeakHashMap<PowerManager.WakeLock, String> b = new WeakHashMap<>();

    public static PowerManager.WakeLock newWakeLock(@NonNull Context context, @NonNull String str) {
        String str2 = "WorkManager: " + str;
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getApplicationContext().getSystemService("power")).newWakeLock(1, str2);
        synchronized (b) {
            b.put(newWakeLock, str2);
        }
        return newWakeLock;
    }

    public static void checkWakeLocks() {
        HashMap hashMap = new HashMap();
        synchronized (b) {
            hashMap.putAll(b);
        }
        for (PowerManager.WakeLock wakeLock : hashMap.keySet()) {
            if (wakeLock != null && wakeLock.isHeld()) {
                Logger.get().warning(a, String.format("WakeLock held for %s", hashMap.get(wakeLock)), new Throwable[0]);
            }
        }
    }

    private WakeLocks() {
    }
}
