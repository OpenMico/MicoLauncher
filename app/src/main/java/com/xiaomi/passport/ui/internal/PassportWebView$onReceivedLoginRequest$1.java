package com.xiaomi.passport.ui.internal;

import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: webkit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/passport/servicetoken/ServiceTokenResult;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportWebView$onReceivedLoginRequest$1 extends Lambda implements Function0<ServiceTokenResult> {
    final /* synthetic */ ServiceTokenFuture $serviceToken;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportWebView$onReceivedLoginRequest$1(ServiceTokenFuture serviceTokenFuture) {
        super(0);
        this.$serviceToken = serviceTokenFuture;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    public final ServiceTokenResult invoke() {
        return this.$serviceToken.get();
    }
}
