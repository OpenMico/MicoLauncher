package com.xiaomi.micolauncher.module.initialize;

import com.xiaomi.accountsdk.account.data.AccountInfo;

/* loaded from: classes3.dex */
public class XiaomiAccountInfo {
    public final String autoLoginUrl;
    public final String encryptedUserId;
    public final boolean hasPwd;
    public final String passToken;
    public final String ph;
    public final String psecurity;
    public final String rePassToken;
    public final String security;
    public final String serviceId;
    public final String serviceToken;
    public final String slh;
    public final String userId;
    public final String userSyncedUrl;

    public XiaomiAccountInfo(AccountInfo accountInfo) {
        this.userId = accountInfo.userId;
        this.serviceId = accountInfo.serviceId;
        this.passToken = accountInfo.passToken;
        this.encryptedUserId = accountInfo.encryptedUserId;
        this.serviceToken = accountInfo.serviceToken;
        this.security = accountInfo.security;
        this.psecurity = accountInfo.psecurity;
        this.autoLoginUrl = accountInfo.autoLoginUrl;
        this.rePassToken = accountInfo.rePassToken;
        this.slh = accountInfo.slh;
        this.ph = accountInfo.ph;
        this.userSyncedUrl = accountInfo.userSyncedUrl;
        this.hasPwd = accountInfo.hasPwd;
    }
}
