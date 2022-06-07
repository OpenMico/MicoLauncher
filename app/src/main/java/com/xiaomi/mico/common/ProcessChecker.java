package com.xiaomi.mico.common;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import com.xiaomi.idm.api.IDMServer;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
public class ProcessChecker {
    public static final String PROCESS_NAME_UNKNOWN_PROCESS = "UnknownProcess";
    private static AtomicReference<ActivityManager.RunningAppProcessInfo> a;

    public static boolean isMainProcess(Context context) {
        return isMainProcess(context, getCurrentProcessName(context));
    }

    public static boolean isMainProcess(Context context, String str) {
        return context.getPackageName().equals(str);
    }

    public static String getCurrentProcessName(Context context) {
        ActivityManager.RunningAppProcessInfo a2 = a(context);
        return a2 == null ? PROCESS_NAME_UNKNOWN_PROCESS : a2.processName;
    }

    public static boolean isUnknownProcess(String str) {
        return PROCESS_NAME_UNKNOWN_PROCESS.equals(str);
    }

    private static ActivityManager.RunningAppProcessInfo a(Context context) {
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo;
        AtomicReference<ActivityManager.RunningAppProcessInfo> atomicReference = a;
        if (!(atomicReference == null || (runningAppProcessInfo = atomicReference.get()) == null)) {
            return runningAppProcessInfo;
        }
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo2 : ((ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses()) {
            if (runningAppProcessInfo2.pid == myPid) {
                a = new AtomicReference<>(runningAppProcessInfo2);
                return runningAppProcessInfo2;
            }
        }
        return null;
    }

    public static void quitProcessOnUnknownProcess() {
        System.exit(-1);
    }
}
