package com.xiaomi.passport.ui.internal;

import android.widget.Toast;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PhTicketSignInPresenter$sendTicket$2 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ PhoneWrapper $phone;
    final /* synthetic */ PhTicketSignInPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhTicketSignInPresenter$sendTicket$2(PhTicketSignInPresenter phTicketSignInPresenter, PhoneWrapper phoneWrapper) {
        super(1);
        this.this$0 = phTicketSignInPresenter;
        this.$phone = phoneWrapper;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Throwable it) {
        String str;
        Intrinsics.checkParameterIsNotNull(it, "it");
        this.this$0.getView().enableSendTicketBtn();
        if (it instanceof CaptchaException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_REGAIN_SEND_TICKET_CAPTCHA_EXCEPTION);
            this.this$0.getView().showCaptcha(((CaptchaException) it).getCaptcha(), this.$phone);
        } else if (it instanceof IOException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_REGAIN_SEND_TICKET_IO_EXCEPTION);
            this.this$0.getView().showNetworkError((IOException) it);
        } else if (it instanceof ReachLimitException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_REGAIN_SEND_TICKET_REACH_LIMIT_EXCEPTION);
            this.this$0.getView().showInvalidTicket(R.string.passport_send_too_many_sms);
        } else if (it instanceof InvalidPhoneNumException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_REGAIN_SEND_TICKET_INVALID_PHONENUM_EXCEPTION);
            this.this$0.getView().showInvalidTicket(R.string.passport_error_phone_error);
        } else if (it instanceof TokenExpiredException) {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_REGAIN_SEND_TICKET_TOKEN_EXPIRED_EXCEPTION);
            PhTicketSignInPresenter phTicketSignInPresenter = this.this$0;
            phTicketSignInPresenter.invalidateCachePhoneNum(phTicketSignInPresenter.getContext(), this.$phone);
            Toast.makeText(this.this$0.getContext(), R.string.passport_activate_token_expired, 0).show();
        } else {
            TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_REGAIN_SEND_TICKET_UNKNOW_ERROR);
            str = this.this$0.TAG;
            AccountLog.e(str, "", it);
            this.this$0.getView().showUnKnowError(it);
        }
    }
}
