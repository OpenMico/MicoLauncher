package com.xiaomi.passport.ui.internal;

import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: FragmentGetPhAuthMethod.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PhAuthPresenter$sendTicket$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ PhoneWrapper $phone;
    final /* synthetic */ PhAuthPresenter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhAuthPresenter$sendTicket$1(PhAuthPresenter phAuthPresenter, PhoneWrapper phoneWrapper) {
        super(1);
        this.this$0 = phAuthPresenter;
        this.$phone = phoneWrapper;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull String it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_SEND_TICKET_SUCCESS);
        this.this$0.getView().dismissProgress();
        this.this$0.getView().gotoTicketSignIn(this.$phone);
    }
}
