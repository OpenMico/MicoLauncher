package com.xiaomi.ai.android.core;

import android.content.Context;
import android.os.Build;
import com.xiaomi.ai.android.capability.Capability;
import com.xiaomi.ai.api.Settings;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.b.c;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.LoggerHooker;

/* loaded from: classes2.dex */
public abstract class Engine {
    public static final int ENGINE_AUTH_ANONYMOUS = 5;
    public static final int ENGINE_AUTH_APP_ANONYMOUS = 5;
    public static final int ENGINE_AUTH_APP_OAUTH = 4;
    public static final int ENGINE_AUTH_DEVICE_ANONYMOUS = 6;
    public static final int ENGINE_AUTH_DEVICE_OAUTH = 1;
    public static final int ENGINE_AUTH_DEVICE_TOKEN = 3;
    public static final int ENGINE_AUTH_MIOT = 2;
    public static final int ENGINE_AUTH_SERVER = 7;

    static {
        System.loadLibrary("aivs_jni");
        c.a(Build.VERSION.SDK_INT);
    }

    public static Engine create(Context context, AivsConfig aivsConfig, Settings.ClientInfo clientInfo, int i) {
        return new d(context, aivsConfig, clientInfo, i);
    }

    public abstract void clearUserData();

    public abstract void finishTrace();

    public abstract String getAccessToken();

    public abstract String getAuthorization();

    public abstract long getExpireAt();

    public abstract int getSDKVersion();

    public abstract void interrupt();

    public abstract boolean postData(byte[] bArr, int i, int i2, boolean z);

    public abstract boolean postData(byte[] bArr, boolean z);

    public abstract boolean postEvent(Event event);

    public abstract boolean postRawData(byte[] bArr, int i, int i2);

    public abstract boolean registerCapability(Capability capability);

    public abstract void release();

    public abstract String requestAuthorization();

    public abstract void restart();

    public abstract boolean setAuthorizationTokens(String str, String str2, long j);

    public abstract void setLoggerHooker(LoggerHooker loggerHooker);

    public abstract boolean start();

    public abstract void startTrace();

    public abstract void traceRequestId(String str);

    public abstract void traceResult(String str, int i);

    public abstract void traceResult(String str, String str2);

    public abstract void traceTimeStamps(String str);

    public abstract void traceTimeStamps(String str, long j);

    public abstract void uploadLogError(String str, String str2);

    public abstract void uploadLogInfo(String str, String str2);

    public abstract void uploadLogWarn(String str, String str2);
}
