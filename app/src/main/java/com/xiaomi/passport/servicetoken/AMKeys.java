package com.xiaomi.passport.servicetoken;

/* loaded from: classes4.dex */
public final class AMKeys {
    private static final String AM_USER_DATA_KEY_C_USER_ID = "encrypted_user_id";
    private static final String AM_USER_DATA_KEY_PH_POSTFIX = "_ph";
    private static final String AM_USER_DATA_KEY_SLH_POSTFIX = "_slh";
    private static final String TYPE = "com.xiaomi";

    public String getAmUserDataKeyCUserId() {
        return "encrypted_user_id";
    }

    public String getType() {
        return "com.xiaomi";
    }

    public String getAmUserDataKeySlh(String str) {
        return str + "_slh";
    }

    public String getAmUserDataKeyPh(String str) {
        return str + "_ph";
    }
}
