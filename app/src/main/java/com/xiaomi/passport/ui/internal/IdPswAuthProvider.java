package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.passport.ui.internal.PswSignInContract;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004H\u0016J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014¨\u0006\u0013"}, d2 = {"Lcom/xiaomi/passport/ui/internal/IdPswAuthProvider;", "Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "()V", "getFragment", "Lcom/xiaomi/passport/ui/internal/BaseSignInFragment;", "sid", "", "getFragmentWithUserId", BaseConstants.EXTRA_USER_ID, "setPresenter", "", "fragment", "signInWithAuthCredential", "Lcom/xiaomi/passport/ui/internal/Source;", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", c.R, "Landroid/content/Context;", "credential", "Lcom/xiaomi/passport/ui/internal/AuthCredential;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class IdPswAuthProvider extends BaseAuthProvider {
    public IdPswAuthProvider() {
        super(PassportUI.ID_PSW_AUTH_PROVIDER);
    }

    @Override // com.xiaomi.passport.ui.internal.BaseAuthProvider
    public void setPresenter(@NotNull String sid, @NotNull BaseSignInFragment fragment) {
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        PswSignInFragment pswSignInFragment = (PswSignInFragment) fragment;
        Context context = pswSignInFragment.getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        pswSignInFragment.setPresenter(new PswSignInPresenter(context, sid, (PswSignInContract.View) fragment, null, 8, null));
    }

    @Override // com.xiaomi.passport.ui.internal.BaseAuthProvider
    @NotNull
    public BaseSignInFragment getFragment(@NotNull String sid) {
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        return PswSignInFragment.Companion.newInstance(sid);
    }

    @NotNull
    public final BaseSignInFragment getFragmentWithUserId(@NotNull String sid, @NotNull String userId) {
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        Intrinsics.checkParameterIsNotNull(userId, "userId");
        return PswSignInFragment.Companion.newInstance(sid, userId);
    }

    @Override // com.xiaomi.passport.ui.internal.AuthProvider
    @NotNull
    protected Source<AccountInfo> signInWithAuthCredential(@NotNull Context context, @NotNull AuthCredential credential) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(credential, "credential");
        if (credential instanceof IdPswAuthCredential) {
            return getPassportRepo().signInIdAndPsw((IdPswAuthCredential) credential);
        }
        if (credential instanceof IdPswVStep2AuthCredential) {
            return getPassportRepo().signInWithVStep2code((IdPswVStep2AuthCredential) credential);
        }
        throw new IllegalStateException("not support originAuthCredential:" + credential);
    }
}
