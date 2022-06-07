package com.xiaomi.passport.ui.internal;

import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhTicketSignInPresenter$signInWithAuthCredential$1 extends Lambda implements Function1<AccountInfo, Unit> {
    final /* synthetic */ PhoneSmsAuthCredential $authCredential;
    final /* synthetic */ PhTicketSignInPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhTicketSignInPresenter$signInWithAuthCredential$1(PhTicketSignInPresenter phTicketSignInPresenter, PhoneSmsAuthCredential phoneSmsAuthCredential) {
        super(1);
        this.this$0 = phTicketSignInPresenter;
        this.$authCredential = phoneSmsAuthCredential;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(AccountInfo accountInfo) {
        invoke2(accountInfo);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull AccountInfo it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        this.this$0.getView().dismissProgress();
        this.this$0.getView().loginSuccess(it);
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_TICKET_LOGIN_SUCCESS);
        if (this.$authCredential.getPhone().getActivateInfo() != null) {
            PhTicketSignInPresenter phTicketSignInPresenter = this.this$0;
            phTicketSignInPresenter.invalidateCachePhoneNum(phTicketSignInPresenter.getContext(), this.$authCredential.getPhone());
        }
    }
}
