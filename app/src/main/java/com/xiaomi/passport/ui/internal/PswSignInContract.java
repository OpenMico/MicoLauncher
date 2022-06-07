package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.passport.ui.internal.SignInContract;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: FragmentIdPswAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PswSignInContract;", "", "()V", "Presenter", "View", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PswSignInContract {

    /* compiled from: FragmentIdPswAuth.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0013\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H&J\u0018\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0004H&J0\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016H&¨\u0006\u0017"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PswSignInContract$Presenter;", "", "getSignedInUserIds", "", "", "()[Ljava/lang/String;", "signInIdAndPsw", "", "id", "psw", "signInPhoneAndPsw", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "signInVStep2", "step1Token", "metaLoginData", "Lcom/xiaomi/accountsdk/account/data/MetaLoginData;", "step2code", "trustCurrentEnv", "", "signInWithAuthCredential", "authCredential", "Lcom/xiaomi/passport/ui/internal/IdPswBaseAuthCredential;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public interface Presenter {
        @NotNull
        String[] getSignedInUserIds();

        void signInIdAndPsw(@NotNull String str, @NotNull String str2);

        void signInPhoneAndPsw(@NotNull PhoneWrapper phoneWrapper, @NotNull String str);

        void signInVStep2(@NotNull String str, @NotNull String str2, @NotNull MetaLoginData metaLoginData, @NotNull String str3, boolean z);

        void signInWithAuthCredential(@NotNull IdPswBaseAuthCredential idPswBaseAuthCredential);
    }

    /* compiled from: FragmentIdPswAuth.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J \u0010\f\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH&¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PswSignInContract$View;", "Lcom/xiaomi/passport/ui/internal/SignInContract$View;", "showCaptcha", "", "captcha", "Lcom/xiaomi/passport/ui/internal/Captcha;", "authCredential", "Lcom/xiaomi/passport/ui/internal/IdPswBaseAuthCredential;", "showPswError", "msg", "", "showUserNameError", "showVStep2Code", "step1Token", "metaLoginData", "Lcom/xiaomi/accountsdk/account/data/MetaLoginData;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public interface View extends SignInContract.View {
        void showCaptcha(@NotNull Captcha captcha, @NotNull IdPswBaseAuthCredential idPswBaseAuthCredential);

        void showPswError(@NotNull String str);

        void showUserNameError(@NotNull String str);

        void showVStep2Code(@NotNull IdPswBaseAuthCredential idPswBaseAuthCredential, @NotNull String str, @NotNull MetaLoginData metaLoginData);
    }
}
