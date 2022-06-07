package com.xiaomi.accountsdk.utils;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.XMPassportSettings;

/* loaded from: classes2.dex */
public class UserSpaceIdUtil {
    private UserSpaceIdUtil() {
    }

    public static String getNullableUserSpaceIdCookie() {
        return getNullableUserSpaceIdCookie(XMPassportSettings.getUserHandleId(), XMPassportSettings.getOwnerHandleId());
    }

    static String getNullableUserSpaceIdCookie(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, str2)) {
            return null;
        }
        return str;
    }
}
