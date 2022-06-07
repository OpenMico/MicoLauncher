package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.passport.ui.internal.SignInContract;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract;", "", "()V", "Presenter", "View", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhTicketSignInContract {

    /* compiled from: FragmentPhTicketAuth.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0003H&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u0018\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0003H&J\u0010\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H&J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0010H&¨\u0006\u0015"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$View;", "Lcom/xiaomi/passport/ui/internal/SignInContract$View;", "chooseToSignInOrSignUp", "", "authCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "userInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "enableSendTicketBtn", "sendTicketSuccess", "showCaptcha", "captcha", "Lcom/xiaomi/passport/ui/internal/Captcha;", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "showInvalidPsw", "Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "msg", "", "showInvalidTicket", "showSetPsw", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public interface View extends SignInContract.View {
        void chooseToSignInOrSignUp(@NotNull PhoneSmsAuthCredential phoneSmsAuthCredential, @NotNull RegisterUserInfo registerUserInfo);

        void enableSendTicketBtn();

        void sendTicketSuccess();

        void showCaptcha(@NotNull Captcha captcha, @NotNull PhoneWrapper phoneWrapper);

        void showInvalidPsw(@NotNull ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential, int i);

        void showInvalidTicket();

        void showInvalidTicket(int i);

        void showSetPsw(@NotNull ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential);
    }

    /* compiled from: FragmentPhTicketAuth.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\tH&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001c\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\u0018\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H&¨\u0006\u0012"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$Presenter;", "", "chooseSignIn", "", "authCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "userInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "chooseSignUp", "Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "sendTicket", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "captchaCode", "Lcom/xiaomi/passport/ui/internal/CaptchaCode;", "signInPhoneAndTicket", "ticket", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public interface Presenter {
        void chooseSignIn(@NotNull PhoneSmsAuthCredential phoneSmsAuthCredential, @NotNull RegisterUserInfo registerUserInfo);

        void chooseSignUp(@NotNull ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential);

        void chooseSignUp(@NotNull PhoneSmsAuthCredential phoneSmsAuthCredential, @NotNull RegisterUserInfo registerUserInfo);

        void sendTicket(@NotNull PhoneWrapper phoneWrapper, @Nullable CaptchaCode captchaCode);

        void signInPhoneAndTicket(@NotNull PhoneWrapper phoneWrapper, @NotNull String str);

        /* compiled from: FragmentPhTicketAuth.kt */
        @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 10})
        /* loaded from: classes4.dex */
        public static final class DefaultImpls {
            public static /* synthetic */ void sendTicket$default(Presenter presenter, PhoneWrapper phoneWrapper, CaptchaCode captchaCode, int i, Object obj) {
                if (obj == null) {
                    if ((i & 2) != 0) {
                        captchaCode = null;
                    }
                    presenter.sendTicket(phoneWrapper, captchaCode);
                    return;
                }
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendTicket");
            }
        }
    }
}
