package com.xiaomi.passport.ui.internal;

import com.umeng.analytics.pro.c;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SnsCodeAuthCredential;", "Lcom/xiaomi/passport/ui/internal/SNSAuthCredential;", c.M, "", "appId", "code", "sid", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCode", "()Ljava/lang/String;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class SnsCodeAuthCredential extends SNSAuthCredential {
    @NotNull
    private final String code;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SnsCodeAuthCredential(@NotNull String provider, @NotNull String appId, @NotNull String code, @NotNull String sid) {
        super(provider, appId, sid);
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Intrinsics.checkParameterIsNotNull(appId, "appId");
        Intrinsics.checkParameterIsNotNull(code, "code");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        this.code = code;
    }

    @NotNull
    public final String getCode() {
        return this.code;
    }
}
