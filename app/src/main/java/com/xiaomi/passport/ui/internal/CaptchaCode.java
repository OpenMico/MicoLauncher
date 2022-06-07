package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Utils.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lcom/xiaomi/passport/ui/internal/CaptchaCode;", "", "captchaCode", "", "captchaIck", "(Ljava/lang/String;Ljava/lang/String;)V", "getCaptchaCode", "()Ljava/lang/String;", "getCaptchaIck", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class CaptchaCode {
    @NotNull
    private final String captchaCode;
    @NotNull
    private final String captchaIck;

    public CaptchaCode(@NotNull String captchaCode, @NotNull String captchaIck) {
        Intrinsics.checkParameterIsNotNull(captchaCode, "captchaCode");
        Intrinsics.checkParameterIsNotNull(captchaIck, "captchaIck");
        this.captchaCode = captchaCode;
        this.captchaIck = captchaIck;
    }

    @NotNull
    public final String getCaptchaCode() {
        return this.captchaCode;
    }

    @NotNull
    public final String getCaptchaIck() {
        return this.captchaIck;
    }
}
