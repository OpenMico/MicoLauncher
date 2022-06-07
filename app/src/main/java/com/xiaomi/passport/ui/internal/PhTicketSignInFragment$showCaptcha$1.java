package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "captchaCode", "", "lastIck", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PhTicketSignInFragment$showCaptcha$1 extends Lambda implements Function2<String, String, Unit> {
    final /* synthetic */ PhoneWrapper $phone;
    final /* synthetic */ PhTicketSignInFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PhTicketSignInFragment$showCaptcha$1(PhTicketSignInFragment phTicketSignInFragment, PhoneWrapper phoneWrapper) {
        super(2);
        this.this$0 = phTicketSignInFragment;
        this.$phone = phoneWrapper;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(String str, String str2) {
        invoke2(str, str2);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull String captchaCode, @NotNull String lastIck) {
        Intrinsics.checkParameterIsNotNull(captchaCode, "captchaCode");
        Intrinsics.checkParameterIsNotNull(lastIck, "lastIck");
        PhTicketSignInFragment.access$getPresenter$p(this.this$0).sendTicket(this.$phone, new CaptchaCode(captchaCode, lastIck));
    }
}
