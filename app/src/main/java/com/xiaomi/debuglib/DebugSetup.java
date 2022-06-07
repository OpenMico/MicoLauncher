package com.xiaomi.debuglib;

import android.content.Context;
import okhttp3.Interceptor;

/* loaded from: classes3.dex */
public class DebugSetup {
    private static volatile LogCallback a = new LogCallback() { // from class: com.xiaomi.debuglib.DebugSetup.1
        @Override // com.xiaomi.debuglib.DebugSetup.LogCallback
        public void debug(String str) {
        }
    };

    /* loaded from: classes3.dex */
    public interface LogCallback {
        void debug(String str);
    }

    public static Interceptor getNetworkInterceptor() {
        return null;
    }

    public static void setup(Context context) {
    }

    public static void setLogCallback(LogCallback logCallback) {
        a = logCallback;
    }
}
