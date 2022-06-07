package com.xiaomi.account.diagnosis;

import com.xiaomi.accountsdk.account.XMPassportSettings;
import java.io.File;

/* loaded from: classes2.dex */
public class DiagnosisConstants {
    private static final String LOGCAT_SUB_DIR = "logcat";
    private static final String XM_PASSPORT_DIR = ".XMPassport";
    private static final String ZIP_SUB_DIR = "zip";

    private DiagnosisConstants() {
    }

    public static File getPassportCacheDir() {
        return new File(XMPassportSettings.getApplicationContext().getCacheDir(), XM_PASSPORT_DIR);
    }

    public static File getLogcatSubDir() {
        return new File(getPassportCacheDir(), "logcat");
    }

    public static File getZipSubDir() {
        return new File(getPassportCacheDir(), ZIP_SUB_DIR);
    }
}
