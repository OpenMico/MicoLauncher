package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "originAuthCredential", "userInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "signIn", "", "(Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;Z)V", "getSignIn", "()Z", "getUserInfo", "()Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class ChoosePhoneSmsAuthCredential extends PhoneSmsAuthCredential {
    private final boolean signIn;
    @NotNull
    private final RegisterUserInfo userInfo;

    public final boolean getSignIn() {
        return this.signIn;
    }

    @NotNull
    public final RegisterUserInfo getUserInfo() {
        return this.userInfo;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChoosePhoneSmsAuthCredential(@NotNull PhoneSmsAuthCredential originAuthCredential, @NotNull RegisterUserInfo userInfo, boolean z) {
        super(originAuthCredential.getPhone(), originAuthCredential.getTicket(), originAuthCredential.getSid());
        Intrinsics.checkParameterIsNotNull(originAuthCredential, "originAuthCredential");
        Intrinsics.checkParameterIsNotNull(userInfo, "userInfo");
        this.userInfo = userInfo;
        this.signIn = z;
        setNewPsw(originAuthCredential.getNewPsw());
    }
}
