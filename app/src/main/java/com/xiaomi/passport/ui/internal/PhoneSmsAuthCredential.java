package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "Lcom/xiaomi/passport/ui/internal/BaseAuthCredential;", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "ticket", "", "sid", "(Lcom/xiaomi/passport/ui/internal/PhoneWrapper;Ljava/lang/String;Ljava/lang/String;)V", "newPsw", "getNewPsw", "()Ljava/lang/String;", "setNewPsw", "(Ljava/lang/String;)V", "getPhone", "()Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "getTicket", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class PhoneSmsAuthCredential extends BaseAuthCredential {
    @Nullable
    private String newPsw;
    @NotNull
    private final PhoneWrapper phone;
    @NotNull
    private final String ticket;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhoneSmsAuthCredential(@NotNull PhoneWrapper phone, @NotNull String ticket, @NotNull String sid) {
        super(PassportUI.PHONE_SMS_AUTH_PROVIDER, sid);
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        Intrinsics.checkParameterIsNotNull(ticket, "ticket");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        this.phone = phone;
        this.ticket = ticket;
    }

    @NotNull
    public final PhoneWrapper getPhone() {
        return this.phone;
    }

    @NotNull
    public final String getTicket() {
        return this.ticket;
    }

    @Nullable
    public final String getNewPsw() {
        return this.newPsw;
    }

    public final void setNewPsw(@Nullable String str) {
        this.newPsw = str;
    }
}
