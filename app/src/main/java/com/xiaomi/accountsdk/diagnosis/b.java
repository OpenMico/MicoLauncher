package com.xiaomi.accountsdk.diagnosis;

import java.io.File;
import xcrash.TombstoneParser;

/* loaded from: classes2.dex */
public class b {
    public static File a() {
        return new File(DiagnosisController.getApplicationContext().getCacheDir(), ".XMPassport");
    }

    public static File b() {
        return new File(a(), TombstoneParser.keyLogcat);
    }

    public static File c() {
        return new File(a(), "zip");
    }
}
