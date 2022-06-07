package com.milink.base.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import androidx.annotation.NonNull;
import com.xiaomi.idm.api.IDMServer;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AndroidContextUtil {
    public static String getCurrentProcessName(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) Objects.requireNonNull((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY))).getRunningAppProcesses();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static boolean isInMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static Context getSafeContext(@NonNull Context context) {
        Objects.requireNonNull(context);
        return context instanceof Application ? context : context.getApplicationContext();
    }

    public static void checkInMainThread() {
        if (!isInMainThread()) {
            throw new IllegalStateException("must call in main thread");
        }
    }

    public static void checkNotInMainThread() {
        if (isInMainThread()) {
            throw new IllegalStateException("can't call in main thread");
        }
    }

    public static void checkContext(Context context) {
        Objects.requireNonNull(context, "context can not be null");
    }

    @SuppressLint({"Recycle"})
    public static boolean isProviderExist(@NonNull Context context, @NonNull String str) {
        ContentProviderClient acquireContentProviderClient = ((ContentResolver) Objects.requireNonNull(context.getContentResolver())).acquireContentProviderClient(str);
        boolean z = acquireContentProviderClient != null;
        if (acquireContentProviderClient != null) {
            acquireContentProviderClient.release();
        }
        return z;
    }

    public static void compatNotifyChange(@NonNull ContentResolver contentResolver, @NonNull Uri uri, ContentObserver contentObserver, int i) {
        Objects.requireNonNull(contentResolver);
        if (Build.VERSION.SDK_INT >= 24) {
            contentResolver.notifyChange(uri, contentObserver, i);
        } else {
            contentResolver.notifyChange(uri, contentObserver);
        }
    }
}
