package com.xiaomi.micolauncher.common.player;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;
import miuix.arch.component.AppCallbackMediator;

/* loaded from: classes3.dex */
public class AppComponentProxy {
    public static void attachBaseContext(Application application) {
        try {
            AppCallbackMediator.setupAppComponentManager(application);
            AppCallbackMediator.attachBaseContext(application);
        } catch (Throwable unused) {
            Log.e("AppComponentProxy", "attachBaseContext error");
        }
    }

    public static void onCreate() {
        try {
            AppCallbackMediator.onCreate();
        } catch (Throwable unused) {
            Log.e("AppComponentProxy", "onCreate error");
        }
    }

    public static void onTerminate() {
        try {
            AppCallbackMediator.onRemove();
        } catch (Throwable unused) {
            Log.e("AppComponentProxy", "onTerminate error");
        }
    }

    public static void onConfigurationChanged(Configuration configuration) {
        try {
            AppCallbackMediator.onConfigurationChanged(configuration);
        } catch (Throwable unused) {
            Log.e("AppComponentProxy", "onConfigurationChanged error");
        }
    }

    public static void onLowMemory() {
        try {
            AppCallbackMediator.onLowMemory();
        } catch (Throwable unused) {
            Log.e("AppComponentProxy", "onLowMemory error");
        }
    }

    public static void onTrimMemory(int i) {
        try {
            AppCallbackMediator.onTrimMemory(i);
        } catch (Throwable unused) {
            Log.e("AppComponentProxy", "onTrimMemory error");
        }
    }
}
