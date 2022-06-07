package com.xiaomi.account.diagnosis.encrypt;

/* loaded from: classes2.dex */
public class EncryptLog {

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class EncryptLogSenderHolder {
        private static EncryptLogSender INSTANCE = EncryptLogSender.getInstance();

        private EncryptLogSenderHolder() {
        }
    }

    private static EncryptLogSender getEncryptSender() {
        return EncryptLogSenderHolder.INSTANCE;
    }

    private EncryptLog() {
    }

    public static int v(String str, String str2) {
        return getEncryptSender().printLog(2, str, str2, null);
    }

    public static int v(String str, String str2, Throwable th) {
        return getEncryptSender().printLog(2, str, str2, th);
    }

    public static int d(String str, String str2) {
        return getEncryptSender().printLog(3, str, str2, null);
    }

    public static int d(String str, String str2, Throwable th) {
        return getEncryptSender().printLog(3, str, str2, th);
    }

    public static int i(String str, String str2) {
        return getEncryptSender().printLog(4, str, str2, null);
    }

    public static int i(String str, String str2, Throwable th) {
        return getEncryptSender().printLog(4, str, str2, th);
    }

    public static int w(String str, String str2) {
        return getEncryptSender().printLog(5, str, str2, null);
    }

    public static int w(String str, String str2, Throwable th) {
        return getEncryptSender().printLog(5, str, str2, th);
    }

    public static int w(String str, Throwable th) {
        return getEncryptSender().printLog(5, str, "", th);
    }

    public static int e(String str, String str2) {
        return getEncryptSender().printLog(6, str, str2, null);
    }

    public static int e(String str, String str2, Throwable th) {
        return getEncryptSender().printLog(6, str, str2, th);
    }
}
