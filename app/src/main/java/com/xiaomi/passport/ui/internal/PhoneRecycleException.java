package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhoneRecycleException;", "Lcom/xiaomi/passport/ui/internal/PassportUIException;", "authCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "userInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "(Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;)V", "getAuthCredential", "()Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "getUserInfo", "()Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhoneRecycleException extends PassportUIException {
    @NotNull
    private final PhoneSmsAuthCredential authCredential;
    @NotNull
    private final RegisterUserInfo userInfo;

    public PhoneRecycleException(@NotNull PhoneSmsAuthCredential authCredential, @NotNull RegisterUserInfo userInfo) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        Intrinsics.checkParameterIsNotNull(userInfo, "userInfo");
        this.authCredential = authCredential;
        this.userInfo = userInfo;
    }

    @NotNull
    public final PhoneSmsAuthCredential getAuthCredential() {
        return this.authCredential;
    }

    @NotNull
    public final RegisterUserInfo getUserInfo() {
        return this.userInfo;
    }
}
