package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.passport.ui.internal.SignInContract;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentGetPhAuthMethod.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhAuthContract;", "", "()V", "Presenter", "View", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhAuthContract {

    /* compiled from: FragmentGetPhAuthMethod.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH&¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhAuthContract$View;", "Lcom/xiaomi/passport/ui/internal/SignInContract$View;", "clearPhonePopList", "", "gotoPswSignIn", BaseConstants.EXTRA_USER_ID, "", "gotoTicketSignIn", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "showCaptcha", "captcha", "Lcom/xiaomi/passport/ui/internal/Captcha;", "showPhoneNumError", "msgRes", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public interface View extends SignInContract.View {
        void clearPhonePopList();

        void gotoPswSignIn(@NotNull String str);

        void gotoTicketSignIn(@NotNull PhoneWrapper phoneWrapper);

        void showCaptcha(@NotNull Captcha captcha, @NotNull PhoneWrapper phoneWrapper);

        void showPhoneNumError(int i);
    }

    /* compiled from: FragmentGetPhAuthMethod.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u001c\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH&¨\u0006\t"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhAuthContract$Presenter;", "", "getPhoneAuthMethod", "", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "sendTicket", "captchaCode", "Lcom/xiaomi/passport/ui/internal/CaptchaCode;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public interface Presenter {
        void getPhoneAuthMethod(@Nullable PhoneWrapper phoneWrapper);

        void sendTicket(@NotNull PhoneWrapper phoneWrapper, @Nullable CaptchaCode captchaCode);

        /* compiled from: FragmentGetPhAuthMethod.kt */
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
