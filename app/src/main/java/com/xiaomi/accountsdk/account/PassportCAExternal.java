package com.xiaomi.accountsdk.account;

import com.xiaomi.account.data.PassportCAToken;

/* loaded from: classes2.dex */
public interface PassportCAExternal {
    PassportCAToken loadCAToken();

    long loadNextCAEnabledTime(long j);

    void saveCAToken(PassportCAToken passportCAToken);

    void saveNextCAEnabledTime(long j);
}
