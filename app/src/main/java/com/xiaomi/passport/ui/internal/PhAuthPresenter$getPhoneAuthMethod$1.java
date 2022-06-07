package com.xiaomi.passport.ui.internal;

import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PhAuthContract;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: FragmentGetPhAuthMethod.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/passport/ui/internal/PhoneAuthMethod;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PhAuthPresenter$getPhoneAuthMethod$1 extends Lambda implements Function1<PhoneAuthMethod, Unit> {
    final /* synthetic */ PhoneWrapper $phone;
    final /* synthetic */ PhAuthPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhAuthPresenter$getPhoneAuthMethod$1(PhAuthPresenter phAuthPresenter, PhoneWrapper phoneWrapper) {
        super(1);
        this.this$0 = phAuthPresenter;
        this.$phone = phoneWrapper;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(PhoneAuthMethod phoneAuthMethod) {
        invoke2(phoneAuthMethod);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull PhoneAuthMethod it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_GET_PHONE_AUTHMETHOD_SUCCESS);
        this.this$0.getView().dismissProgress();
        switch (it) {
            case SMS:
                TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_GET_PHONE_AUTHMETHOD_SMS);
                PhAuthContract.Presenter.DefaultImpls.sendTicket$default(this.this$0, this.$phone, null, 2, null);
                return;
            case PSW:
                TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_GET_PHONE_AUTHMETHOD_PSW);
                PhAuthContract.View view = this.this$0.getView();
                String phone = this.$phone.getPhone();
                if (phone == null) {
                    Intrinsics.throwNpe();
                }
                view.gotoPswSignIn(phone);
                return;
            default:
                return;
        }
    }
}
