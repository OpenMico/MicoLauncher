package com.xiaomi.passport.ui.internal;

import android.widget.Toast;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PswSignInContract;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentIdPswAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PswSignInPresenter$signInWithAuthCredential$2 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ IdPswBaseAuthCredential $authCredential;
    final /* synthetic */ PswSignInPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PswSignInPresenter$signInWithAuthCredential$2(PswSignInPresenter pswSignInPresenter, IdPswBaseAuthCredential idPswBaseAuthCredential) {
        super(1);
        this.this$0 = pswSignInPresenter;
        this.$authCredential = idPswBaseAuthCredential;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Throwable it) {
        String str;
        String str2;
        Intrinsics.checkParameterIsNotNull(it, "it");
        this.this$0.getView().dismissProgress();
        if (it instanceof IOException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_IO_EXCEPTION);
            str2 = this.this$0.TAG;
            AccountLog.e(str2, "", it);
            this.this$0.getView().showNetworkError((IOException) it);
        } else if (it instanceof NeedNotificationException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_NEED_NOTIFICATION_EXCEPTION);
            PswSignInContract.View view = this.this$0.getView();
            String notificationUrl = ((NeedNotificationException) it).getNotificationUrl();
            Intrinsics.checkExpressionValueIsNotNull(notificationUrl, "it.notificationUrl");
            view.openNotificationUrl(notificationUrl);
        } else if (it instanceof NeedBindSnsException) {
            this.this$0.getView().gotoBindSnsFragment((NeedBindSnsException) it);
        } else if (it instanceof InvalidUserNameException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_INVALID_USER_NAME_EXCEPTION);
            String msg = this.this$0.getContext().getString(R.string.passport_error_user_name);
            if (PassportUI.INSTANCE.getInternational()) {
                msg = msg + this.this$0.getContext().getString(R.string.passport_international_phone_password_login_tip);
            }
            PswSignInContract.View view2 = this.this$0.getView();
            Intrinsics.checkExpressionValueIsNotNull(msg, "msg");
            view2.showUserNameError(msg);
        } else if (it instanceof InvalidCredentialException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_INVALID_CREDENTIAL_EXCEPTION);
            String msg2 = this.this$0.getContext().getString(R.string.passport_bad_authentication);
            if (PassportUI.INSTANCE.getInternational()) {
                msg2 = msg2 + this.this$0.getContext().getString(R.string.passport_international_phone_password_login_tip);
            }
            PswSignInContract.View view3 = this.this$0.getView();
            Intrinsics.checkExpressionValueIsNotNull(msg2, "msg");
            view3.showPswError(msg2);
        } else if (it instanceof CaptchaException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_CAPTCHA_EXCEPTION);
            this.this$0.getView().showCaptcha(((CaptchaException) it).getCaptcha(), this.$authCredential);
        } else if (it instanceof NeedVerificationException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_NEED_VERIFICATION_EXCEPTION);
            NeedVerificationException needVerificationException = (NeedVerificationException) it;
            if (needVerificationException.getStep1Token() == null) {
                Toast.makeText(this.this$0.getContext(), R.string.passport_v_code_error, 1).show();
                return;
            }
            PswSignInContract.View view4 = this.this$0.getView();
            IdPswBaseAuthCredential idPswBaseAuthCredential = this.$authCredential;
            String step1Token = needVerificationException.getStep1Token();
            Intrinsics.checkExpressionValueIsNotNull(step1Token, "it.step1Token");
            MetaLoginData metaLoginData = needVerificationException.getMetaLoginData();
            Intrinsics.checkExpressionValueIsNotNull(metaLoginData, "it.metaLoginData");
            view4.showVStep2Code(idPswBaseAuthCredential, step1Token, metaLoginData);
        } else {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_UNKNOW_ERROR);
            str = this.this$0.TAG;
            AccountLog.e(str, "", it);
            this.this$0.getView().showUnKnowError(it);
        }
    }
}
