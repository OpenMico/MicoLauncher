package com.xiaomi.passport.ui.internal;

import com.umeng.analytics.pro.c;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SNSAuthCredential;", "Lcom/xiaomi/passport/ui/internal/AuthCredential;", c.M, "", "appId", "sid", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAppId", "()Ljava/lang/String;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public abstract class SNSAuthCredential extends AuthCredential {
    @NotNull
    private final String appId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SNSAuthCredential(@NotNull String provider, @NotNull String appId, @NotNull String sid) {
        super(provider, sid);
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Intrinsics.checkParameterIsNotNull(appId, "appId");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        this.appId = appId;
    }

    @NotNull
    public final String getAppId() {
        return this.appId;
    }
}
