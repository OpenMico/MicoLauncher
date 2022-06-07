package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.MetaLoginData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lcom/xiaomi/passport/ui/internal/IdPswVStep2AuthCredential;", "Lcom/xiaomi/passport/ui/internal/IdPswBaseAuthCredential;", "id", "", "step1Token", "metaLoginData", "Lcom/xiaomi/accountsdk/account/data/MetaLoginData;", "step2code", "trustCurrentEnv", "", "sid", "(Ljava/lang/String;Ljava/lang/String;Lcom/xiaomi/accountsdk/account/data/MetaLoginData;Ljava/lang/String;ZLjava/lang/String;)V", "getMetaLoginData", "()Lcom/xiaomi/accountsdk/account/data/MetaLoginData;", "getStep1Token", "()Ljava/lang/String;", "getStep2code", "getTrustCurrentEnv", "()Z", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class IdPswVStep2AuthCredential extends IdPswBaseAuthCredential {
    @NotNull
    private final MetaLoginData metaLoginData;
    @NotNull
    private final String step1Token;
    @NotNull
    private final String step2code;
    private final boolean trustCurrentEnv;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IdPswVStep2AuthCredential(@NotNull String id, @NotNull String step1Token, @NotNull MetaLoginData metaLoginData, @NotNull String step2code, boolean z, @NotNull String sid) {
        super(id, sid);
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull(step1Token, "step1Token");
        Intrinsics.checkParameterIsNotNull(metaLoginData, "metaLoginData");
        Intrinsics.checkParameterIsNotNull(step2code, "step2code");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        this.step1Token = step1Token;
        this.metaLoginData = metaLoginData;
        this.step2code = step2code;
        this.trustCurrentEnv = z;
    }

    @NotNull
    public final MetaLoginData getMetaLoginData() {
        return this.metaLoginData;
    }

    @NotNull
    public final String getStep1Token() {
        return this.step1Token;
    }

    @NotNull
    public final String getStep2code() {
        return this.step2code;
    }

    public final boolean getTrustCurrentEnv() {
        return this.trustCurrentEnv;
    }
}
