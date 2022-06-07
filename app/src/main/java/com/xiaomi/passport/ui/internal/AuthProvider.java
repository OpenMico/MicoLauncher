package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH$J\u001a\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\tH\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AuthProvider;", "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "signInAndStoreAccount", "Lcom/xiaomi/passport/ui/internal/Source;", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", c.R, "Landroid/content/Context;", "credential", "Lcom/xiaomi/passport/ui/internal/AuthCredential;", "signInWithAuthCredential", "storePassToken", "", "accountInfo", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public abstract class AuthProvider {
    @NotNull
    private final String name;

    @NotNull
    protected abstract Source<AccountInfo> signInWithAuthCredential(@NotNull Context context, @NotNull AuthCredential authCredential);

    public AuthProvider(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.name = name;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public Source<AccountInfo> signInAndStoreAccount(@NotNull Context context, @NotNull AuthCredential credential) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(credential, "credential");
        return signInWithAuthCredential(context, credential).next(new AuthProvider$signInAndStoreAccount$1(this, context)).next(AuthProvider$signInAndStoreAccount$2.INSTANCE);
    }

    public final void storePassToken(Context context, AccountInfo accountInfo) {
        if (!TextUtils.isEmpty(accountInfo != null ? accountInfo.passToken : null)) {
            AuthenticatorUtil.addOrUpdateAccountManager(context, accountInfo);
        }
    }
}
