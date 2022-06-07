package com.xiaomi.phonenum;

import java.io.File;

/* loaded from: classes4.dex */
public class Constant {
    public static final String ACTION_BIND_SERVICE = "com.xiaomi.simactivate.service.ACTION_BIND_SYSTEM_PHONE_SERVICE";
    public static final String BASE_URL;
    public static final boolean DEBUG = false;
    public static final String OBTAIN_STRATEGY_URL;
    public static final String PHONE_SERVICE_PACKAGE = "com.xiaomi.simactivate.service";
    public static final String UTF_8 = "utf-8";
    private static boolean a = new File("/data/system/xiaomi_account_preview").exists();

    static {
        BASE_URL = a ? "http://act.preview.account.xiaomi.com/pass/activator" : "https://act.account.xiaomi.com/pass/activator";
        OBTAIN_STRATEGY_URL = BASE_URL + "/getCloudControl";
    }

    public static void setUsePreview(boolean z) {
        a = z;
    }
}
