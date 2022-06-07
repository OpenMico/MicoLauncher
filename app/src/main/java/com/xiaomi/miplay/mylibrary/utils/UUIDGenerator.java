package com.xiaomi.miplay.mylibrary.utils;

import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.util.UUID;

/* loaded from: classes4.dex */
public class UUIDGenerator {
    private static final String a = "UUIDGenerator";

    public static String getUUID() {
        String replaceAll = UUID.randomUUID().toString().replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
        String str = a;
        Logger.i(str, "uuid:" + replaceAll, new Object[0]);
        return replaceAll;
    }
}
