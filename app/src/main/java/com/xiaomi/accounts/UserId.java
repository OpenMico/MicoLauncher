package com.xiaomi.accounts;

import android.os.Binder;

/* loaded from: classes2.dex */
public final class UserId {
    public static final boolean MU_ENABLED = true;
    public static final int PER_USER_RANGE = 100000;
    public static final int USER_ALL = -1;

    public static final int getUserId(int i) {
        return i / 100000;
    }

    public static final int getCallingUserId() {
        return getUserId(Binder.getCallingUid());
    }

    public static final int getUid(int i, int i2) {
        return (i * 100000) + (i2 % 100000);
    }

    public static final int getAppId(int i) {
        return i % 100000;
    }
}
