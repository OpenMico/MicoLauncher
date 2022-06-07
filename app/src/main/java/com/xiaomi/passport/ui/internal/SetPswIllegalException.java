package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SetPswIllegalException;", "Lcom/xiaomi/passport/ui/internal/PassportUIException;", "authCredential", "Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "(Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;)V", "getAuthCredential", "()Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class SetPswIllegalException extends PassportUIException {
    @NotNull
    private final ChoosePhoneSmsAuthCredential authCredential;

    public SetPswIllegalException(@NotNull ChoosePhoneSmsAuthCredential authCredential) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        this.authCredential = authCredential;
    }

    @NotNull
    public final ChoosePhoneSmsAuthCredential getAuthCredential() {
        return this.authCredential;
    }
}
