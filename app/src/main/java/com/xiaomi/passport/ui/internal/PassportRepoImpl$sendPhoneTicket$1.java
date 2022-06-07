package com.xiaomi.passport.ui.internal;

import android.graphics.Bitmap;
import android.util.Pair;
import com.xiaomi.accountsdk.account.data.SendPhoneTicketParams;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.passport.StatConstants;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import java.util.concurrent.ExecutionException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$sendPhoneTicket$1 extends Lambda implements Function0<String> {
    final /* synthetic */ PhoneWrapper $authCredential;
    final /* synthetic */ CaptchaCode $captchaCode;
    final /* synthetic */ PassportRepoImpl this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportRepoImpl$sendPhoneTicket$1(PassportRepoImpl passportRepoImpl, PhoneWrapper phoneWrapper, CaptchaCode captchaCode) {
        super(0);
        this.this$0 = passportRepoImpl;
        this.$authCredential = phoneWrapper;
        this.$captchaCode = captchaCode;
    }

    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final String invoke() {
        Pair captchaImageNullSafe;
        SendPhoneTicketParams.Builder serviceId = new SendPhoneTicketParams.Builder().serviceId(this.$authCredential.getSid());
        CaptchaCode captchaCode = this.$captchaCode;
        String str = null;
        String captchaCode2 = captchaCode != null ? captchaCode.getCaptchaCode() : null;
        CaptchaCode captchaCode3 = this.$captchaCode;
        if (captchaCode3 != null) {
            str = captchaCode3.getCaptchaIck();
        }
        SendPhoneTicketParams.Builder captchaCode4 = serviceId.captchaCode(captchaCode2, str);
        if (this.$authCredential.getPhone() != null) {
            this.this$0.checkPhone(this.$authCredential.getPhone());
            captchaCode4.phone(this.$authCredential.getPhone());
        } else {
            captchaCode4.phoneHashActivatorToken(this.$authCredential.getActivateInfo());
        }
        try {
            new PhoneLoginController().sendPhoneTicket(captchaCode4.build(), new PhoneLoginController.SendPhoneTicketCallback() { // from class: com.xiaomi.passport.ui.internal.PassportRepoImpl$sendPhoneTicket$1$future$1
                @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketCallback
                public void onActivatorTokenExpired() {
                }

                @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketCallback
                public void onNeedCaptchaCode(@Nullable String str2) {
                }

                @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketCallback
                public void onPhoneNumInvalid() {
                }

                @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketCallback
                public void onSMSReachLimit() {
                }

                @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketCallback
                public void onSentFailed(@Nullable PhoneLoginController.ErrorCode errorCode, @Nullable String str2) {
                }

                @Override // com.xiaomi.passport.uicontroller.PhoneLoginController.SendPhoneTicketCallback
                public void onSentSuccess(int i) {
                }
            }).get();
            return StatConstants.BIND_SUCCESS;
        } catch (ExecutionException e) {
            if (e.getCause() instanceof NeedCaptchaException) {
                Throwable cause = e.getCause();
                if (cause == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.accountsdk.account.exception.NeedCaptchaException");
                }
                String url = ((NeedCaptchaException) cause).getCaptchaUrl();
                PassportRepoImpl passportRepoImpl = this.this$0;
                Intrinsics.checkExpressionValueIsNotNull(url, "url");
                captchaImageNullSafe = passportRepoImpl.getCaptchaImageNullSafe(url);
                Object obj = captchaImageNullSafe.second;
                Intrinsics.checkExpressionValueIsNotNull(obj, "captcha.second");
                throw new CaptchaException(new Captcha((Bitmap) captchaImageNullSafe.first, (String) obj, url));
            }
            Throwable cause2 = e.getCause();
            if (cause2 == null) {
                Intrinsics.throwNpe();
            }
            throw cause2;
        }
    }
}
