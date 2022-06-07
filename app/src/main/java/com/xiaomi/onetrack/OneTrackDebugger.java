package com.xiaomi.onetrack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.o;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class OneTrackDebugger {
    private static volatile OneTrackDebugger a = null;
    private static String b = "com.xiaomi.onetrack.otdebugger.FloatWindowService";
    private ConcurrentHashMap<Long, Configuration> c = new ConcurrentHashMap<>();

    public static OneTrackDebugger getInstance() {
        if (a == null) {
            synchronized (OneTrackDebugger.class) {
                if (a == null) {
                    a = new OneTrackDebugger();
                }
            }
        }
        return a;
    }

    private OneTrackDebugger() {
    }

    public ConcurrentHashMap<Long, Configuration> getSdkConfig() {
        return this.c;
    }

    public void setSdkConfig(Configuration configuration) {
        this.c.put(Long.valueOf(System.currentTimeMillis()), configuration);
    }

    public void startDebugger() {
        try {
            a.a().startService(new Intent(a.a(), Class.forName(b)));
        } catch (Throwable th) {
            Log.d("startDebugger", th.getMessage());
        }
    }

    public String getOaid(Context context) {
        return com.xiaomi.onetrack.util.oaid.a.a().a(context.getApplicationContext());
    }

    public String getInstanceId() {
        return o.a().b();
    }
}
