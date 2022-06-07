package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lcom/xiaomi/passport/ui/internal/IdPswBaseAuthCredential;", "Lcom/xiaomi/passport/ui/internal/BaseAuthCredential;", "id", "", "sid", "(Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public abstract class IdPswBaseAuthCredential extends BaseAuthCredential {
    @NotNull
    private final String id;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IdPswBaseAuthCredential(@NotNull String id, @NotNull String sid) {
        super(PassportUI.ID_PSW_AUTH_PROVIDER, sid);
        Intrinsics.checkParameterIsNotNull(id, "id");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        this.id = id;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }
}
