package com.xiaomi.account.diagnosis.log;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.account.diagnosis.util.ProcessUtils;
import com.xiaomi.mico.common.ProcessChecker;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class LogHeader {
    private static final ThreadLocal<SimpleDateFormat> sFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.account.diagnosis.log.LogHeader.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        }
    };
    private static String sProcessName;

    private LogHeader() {
    }

    private static String getFormattedTime() {
        return sFormat.get().format(new Date());
    }

    public static String format(LogLevel logLevel, String str) {
        String cachedProcessName = getCachedProcessName();
        int myPid = Process.myPid();
        int myTid = Process.myTid();
        return getFormattedTime() + StringUtils.SPACE + myPid + Constants.ACCEPT_TIME_SEPARATOR_SERVER + myTid + "/" + cachedProcessName + StringUtils.SPACE + logLevel.toString() + "/" + str + ": ";
    }

    private static String getCachedProcessName() {
        if (sProcessName == null) {
            String processName = ProcessUtils.getProcessName(Process.myPid());
            if (TextUtils.isEmpty(processName)) {
                sProcessName = ProcessChecker.PROCESS_NAME_UNKNOWN_PROCESS;
            } else {
                sProcessName = processName;
            }
        }
        return sProcessName;
    }
}
