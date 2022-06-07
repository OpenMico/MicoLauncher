package com.xiaomi.passport.ui.internal;

import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/NeedBindSnsException;", "Lcom/xiaomi/passport/ui/internal/PassportUIException;", "snsBindParams", "Lcom/xiaomi/passport/snscorelib/internal/entity/SNSBindParameter;", "(Lcom/xiaomi/passport/snscorelib/internal/entity/SNSBindParameter;)V", "getSnsBindParams", "()Lcom/xiaomi/passport/snscorelib/internal/entity/SNSBindParameter;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class NeedBindSnsException extends PassportUIException {
    @NotNull
    private final SNSBindParameter snsBindParams;

    public NeedBindSnsException(@NotNull SNSBindParameter snsBindParams) {
        Intrinsics.checkParameterIsNotNull(snsBindParams, "snsBindParams");
        this.snsBindParams = snsBindParams;
    }

    @NotNull
    public final SNSBindParameter getSnsBindParams() {
        return this.snsBindParams;
    }
}
