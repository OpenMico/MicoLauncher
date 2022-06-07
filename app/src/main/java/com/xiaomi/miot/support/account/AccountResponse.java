package com.xiaomi.miot.support.account;

/* loaded from: classes2.dex */
public interface AccountResponse {
    void onFailure(int i, String str);

    void onSuccess(AccountInfo accountInfo);
}
