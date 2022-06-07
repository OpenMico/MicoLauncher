package com.xiaomi.passport.ui.internal;

import com.umeng.analytics.pro.c;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0016\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\b\"\u0004\b\u000f\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\b¨\u0006\u0012"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AuthCredential;", "", c.M, "", "sid", "(Ljava/lang/String;Ljava/lang/String;)V", "captchaCode", "getCaptchaCode", "()Ljava/lang/String;", "setCaptchaCode", "(Ljava/lang/String;)V", "captchaIck", "getCaptchaIck", "setCaptchaIck", "getProvider", "setProvider", "getSid", "addCaptcha", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class AuthCredential {
    @Nullable
    private String captchaCode;
    @Nullable
    private String captchaIck;
    @NotNull
    private String provider;
    @NotNull
    private final String sid;

    public AuthCredential(@NotNull String provider, @NotNull String sid) {
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        this.provider = provider;
        this.sid = sid;
    }

    @NotNull
    public final String getProvider() {
        return this.provider;
    }

    @NotNull
    public final String getSid() {
        return this.sid;
    }

    public final void setProvider(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.provider = str;
    }

    @Nullable
    public final String getCaptchaCode() {
        return this.captchaCode;
    }

    public final void setCaptchaCode(@Nullable String str) {
        this.captchaCode = str;
    }

    @Nullable
    public final String getCaptchaIck() {
        return this.captchaIck;
    }

    public final void setCaptchaIck(@Nullable String str) {
        this.captchaIck = str;
    }

    @NotNull
    public final AuthCredential addCaptcha(@NotNull String captchaCode, @NotNull String captchaIck) {
        Intrinsics.checkParameterIsNotNull(captchaCode, "captchaCode");
        Intrinsics.checkParameterIsNotNull(captchaIck, "captchaIck");
        this.captchaCode = captchaCode;
        this.captchaIck = captchaIck;
        return this;
    }
}
