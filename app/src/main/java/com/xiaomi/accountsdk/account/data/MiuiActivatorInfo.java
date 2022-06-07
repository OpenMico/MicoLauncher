package com.xiaomi.accountsdk.account.data;

/* loaded from: classes2.dex */
public class MiuiActivatorInfo {
    public final String phone;
    public final String simId;
    public final int simIndex;
    public final String vKey2;
    public final String vKey2Nonce;

    public MiuiActivatorInfo(int i, String str, String str2, String str3, String str4) {
        this.simIndex = i;
        this.phone = str;
        this.simId = str2;
        this.vKey2 = str3;
        this.vKey2Nonce = str4;
    }
}
