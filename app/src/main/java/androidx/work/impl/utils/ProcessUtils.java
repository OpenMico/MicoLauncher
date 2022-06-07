package androidx.work.impl.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.work.Configuration;
import androidx.work.Logger;
import com.xiaomi.idm.api.IDMServer;
import java.lang.reflect.Method;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class ProcessUtils {
    private static final String a = Logger.tagWithPrefix("ProcessUtils");

    private ProcessUtils() {
    }

    public static boolean isDefaultProcess(@NonNull Context context, @NonNull Configuration configuration) {
        String processName = getProcessName(context);
        if (!TextUtils.isEmpty(configuration.getDefaultProcessName())) {
            return TextUtils.equals(processName, configuration.getDefaultProcessName());
        }
        return TextUtils.equals(processName, context.getApplicationInfo().processName);
    }

    @Nullable
    @SuppressLint({"PrivateApi", "DiscouragedPrivateApi"})
    public static String getProcessName(@NonNull Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        Object obj;
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread", false, ProcessUtils.class.getClassLoader());
            if (Build.VERSION.SDK_INT >= 18) {
                Method declaredMethod = cls.getDeclaredMethod("currentProcessName", new Class[0]);
                declaredMethod.setAccessible(true);
                obj = declaredMethod.invoke(null, new Object[0]);
            } else {
                Method declaredMethod2 = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
                declaredMethod2.setAccessible(true);
                Method declaredMethod3 = cls.getDeclaredMethod("getProcessName", new Class[0]);
                declaredMethod3.setAccessible(true);
                obj = declaredMethod3.invoke(declaredMethod2.invoke(null, new Object[0]), new Object[0]);
            }
            if (obj instanceof String) {
                return (String) obj;
            }
        } catch (Throwable th) {
            Logger.get().debug(a, "Unable to check ActivityThread for processName", th);
        }
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY);
        if (!(activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null || runningAppProcesses.isEmpty())) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }
}
