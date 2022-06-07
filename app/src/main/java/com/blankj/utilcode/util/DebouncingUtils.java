package com.blankj.utilcode.util;

import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import androidx.annotation.NonNull;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class DebouncingUtils {
    private static final Map<String, Long> a = new ConcurrentHashMap(64);

    private DebouncingUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isValid(@NonNull View view) {
        if (view != null) {
            return isValid(view, 1000L);
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isValid(@NonNull View view, long j) {
        if (view != null) {
            return isValid(String.valueOf(view.hashCode()), j);
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isValid(@NonNull String str, long j) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("The key is null.");
        } else if (j >= 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            a(elapsedRealtime);
            Long l = a.get(str);
            if (l != null && elapsedRealtime < l.longValue()) {
                return false;
            }
            a.put(str, Long.valueOf(elapsedRealtime + j));
            return true;
        } else {
            throw new IllegalArgumentException("The duration is less than 0.");
        }
    }

    private static void a(long j) {
        if (a.size() >= 64) {
            Iterator<Map.Entry<String, Long>> it = a.entrySet().iterator();
            while (it.hasNext()) {
                if (j >= it.next().getValue().longValue()) {
                    it.remove();
                }
            }
        }
    }
}
