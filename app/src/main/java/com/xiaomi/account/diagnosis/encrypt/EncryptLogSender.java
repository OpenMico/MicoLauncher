package com.xiaomi.account.diagnosis.encrypt;

import android.util.Base64;
import android.util.Log;
import com.xiaomi.account.diagnosis.log.LogFileAppender;
import com.xiaomi.account.diagnosis.log.LogHeader;
import com.xiaomi.account.diagnosis.log.LogLevel;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.utils.PassportEnvEncryptUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class EncryptLogSender {
    private static final String TAG = "EncryptLogSender";

    public static EncryptLogSender getInstance() {
        return new EncryptLogSender();
    }

    private EncryptLogSender() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int printLog(int i, String str, String str2, Throwable th) {
        int println = println(i, str, generateEncryptMessageLine(str2)) + 0;
        return th != null ? println + println(i, str, generateEncryptMessageLine(Log.getStackTraceString(th))) : println;
    }

    private String generateEncryptMessageLine(String str) {
        try {
            PassportEnvEncryptUtils.EncryptResultWithIv encryptWithChangingIv = PassportEnvEncryptUtils.encryptWithChangingIv(str);
            return String.format("#&^%s!!%s!!%s^&#", encryptWithChangingIv.encryptedKey, Base64.encodeToString(encryptWithChangingIv.iv, 10), encryptWithChangingIv.content);
        } catch (PassportEnvEncryptUtils.EncryptException e) {
            AccountLog.e(TAG, "encrypt failed: ", e);
            return str;
        }
    }

    private static int println(int i, String str, String str2) {
        String format = LogHeader.format(LogLevel.fromInt(i), str);
        LogFileAppender.appendLine(format + str2);
        return Log.println(i, str, str2);
    }
}
