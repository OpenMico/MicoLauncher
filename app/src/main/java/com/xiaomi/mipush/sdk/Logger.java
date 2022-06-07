package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.milink.base.contract.LockContract;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;
import com.xiaomi.push.aj;
import com.xiaomi.push.dh;
import com.xiaomi.push.di;
import java.io.File;

/* loaded from: classes4.dex */
public class Logger {
    private static boolean a = false;
    private static LoggerInterface b;

    private static boolean a(Context context) {
        try {
            String[] strArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            if (strArr != null) {
                for (String str : strArr) {
                    if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void disablePushFileLog(Context context) {
        a = true;
        setPushLog(context);
    }

    public static void enablePushFileLog(Context context) {
        a = false;
        setPushLog(context);
    }

    public static File getLogFile(String str) {
        File file;
        try {
            file = new File(str);
        } catch (NullPointerException unused) {
            b.d("null pointer exception while retrieve file.");
        }
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile() && !listFiles[i].getName().contains(LockContract.Matcher.LOCK) && listFiles[i].getName().contains(MicoVoipClient.MICOLAUNCHER_LOG)) {
                    return listFiles[i];
                }
            }
            return null;
        }
        return null;
    }

    public static LoggerInterface getUserLogger() {
        return b;
    }

    public static void setLogger(Context context, LoggerInterface loggerInterface) {
        b = loggerInterface;
        setPushLog(context);
    }

    public static void setPushLog(Context context) {
        boolean z = false;
        boolean z2 = b != null;
        if (a) {
            z2 = false;
        } else if (a(context)) {
            z = true;
        }
        di diVar = null;
        LoggerInterface loggerInterface = z2 ? b : null;
        if (z) {
            diVar = new di(context);
        }
        b.a(new dh(loggerInterface, diVar));
    }

    public static void uploadLogFile(Context context, boolean z) {
        aj.a(context).a(new bb(context, z));
    }
}
