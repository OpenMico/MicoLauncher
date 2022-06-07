package com.xiaomi.passport.ui.internal;

import android.widget.Toast;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PhTicketSignInContract;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhTicketSignInPresenter$signInWithAuthCredential$2 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ PhoneSmsAuthCredential $authCredential;
    final /* synthetic */ PhTicketSignInPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhTicketSignInPresenter$signInWithAuthCredential$2(PhTicketSignInPresenter phTicketSignInPresenter, PhoneSmsAuthCredential phoneSmsAuthCredential) {
        super(1);
        this.this$0 = phTicketSignInPresenter;
        this.$authCredential = phoneSmsAuthCredential;
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
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_IO_EXCEPTION);
            str2 = this.this$0.TAG;
            AccountLog.e(str2, "", it);
            this.this$0.getView().showNetworkError((IOException) it);
        } else if (it instanceof NeedNotificationException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_NEED_NOTIFICATION_EXCEPTION);
            PhTicketSignInContract.View view = this.this$0.getView();
            String notificationUrl = ((NeedNotificationException) it).getNotificationUrl();
            Intrinsics.checkExpressionValueIsNotNull(notificationUrl, "it.notificationUrl");
            view.openNotificationUrl(notificationUrl);
        } else if (it instanceof NeedBindSnsException) {
            this.this$0.getView().gotoBindSnsFragment((NeedBindSnsException) it);
        } else if (it instanceof InvalidVerifyCodeException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_INVALID_VERIFYCODE_EXCEPTION);
            this.this$0.getView().showInvalidTicket();
        } else if (it instanceof InvalidPhoneNumException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_INVALID_PHONENUM_EXCEPTION);
            this.this$0.getView().showInvalidTicket(R.string.passport_error_phone_error);
        } else if (it instanceof PhoneRecycleException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_PHONE_RECYCLE_EXCEPTION);
            PhoneRecycleException phoneRecycleException = (PhoneRecycleException) it;
            this.this$0.getView().chooseToSignInOrSignUp(phoneRecycleException.getAuthCredential(), phoneRecycleException.getUserInfo());
        } else if (it instanceof UserRestrictedException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_USER_RESTRICTED_EXCEPTION);
            this.this$0.getView().showInvalidTicket(R.string.phone_bind_too_many);
        } else if (it instanceof TokenExpiredException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_TOKEN_EXPIRED_EXCEPTION);
            PhTicketSignInPresenter phTicketSignInPresenter = this.this$0;
            phTicketSignInPresenter.invalidateCachePhoneNum(phTicketSignInPresenter.getContext(), this.$authCredential.getPhone());
            Toast.makeText(this.this$0.getContext(), R.string.passport_activate_token_expired, 0).show();
        } else if (it instanceof NeedSetPswException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_NEED_SET_PSW_EXCEPTION);
            this.this$0.getView().showSetPsw(((NeedSetPswException) it).getAuthCredential());
        } else if (it instanceof SetPswIllegalException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_SET_PSW_ILLEGAL_EXCEPTION);
            this.this$0.getView().showInvalidPsw(((SetPswIllegalException) it).getAuthCredential(), R.string.passport_password_req_notice);
        } else {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_UNKNOW_ERROR);
            str = this.this$0.TAG;
            AccountLog.e(str, "", it);
            this.this$0.getView().showUnKnowError(it);
        }
    }
}
