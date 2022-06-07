package com.xiaomi.accountsdk.diagnosis.b;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.accountsdk.diagnosis.e.d;
import com.xiaomi.mico.common.ProcessChecker;
import com.xiaomi.mipush.sdk.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class b {
    private static String a;
    private static final ThreadLocal<SimpleDateFormat> b = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.accountsdk.diagnosis.b.b.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        }
    };

    private static String a() {
        return b.get().format(new Date());
    }

    public static String a(c cVar, String str) {
        String b2 = b();
        int myPid = Process.myPid();
        int myTid = Process.myTid();
        return a() + StringUtils.SPACE + myPid + Constants.ACCEPT_TIME_SEPARATOR_SERVER + myTid + "/" + b2 + StringUtils.SPACE + cVar.toString() + "/" + str + ": ";
    }

    private static String b() {
        if (a == null) {
            String a2 = d.a(Process.myPid());
            if (TextUtils.isEmpty(a2)) {
                a2 = ProcessChecker.PROCESS_NAME_UNKNOWN_PROCESS;
            }
            a = a2;
        }
        return a;
    }
}
