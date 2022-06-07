package com.xiaomi.passport.ui.internal;

import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: webkit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/passport/servicetoken/ServiceTokenResult;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportWebView$onReceivedLoginRequest$2 extends Lambda implements Function1<ServiceTokenResult, Unit> {
    final /* synthetic */ PassportWebView this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportWebView$onReceivedLoginRequest$2(PassportWebView passportWebView) {
        super(1);
        this.this$0 = passportWebView;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(ServiceTokenResult serviceTokenResult) {
        invoke2(serviceTokenResult);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(ServiceTokenResult serviceTokenResult) {
        if (serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_NONE) {
            PassportWebView passportWebView = this.this$0;
            String str = serviceTokenResult.serviceToken;
            Intrinsics.checkExpressionValueIsNotNull(str, "it.serviceToken");
            passportWebView.loadUrl(str);
        }
    }
}
