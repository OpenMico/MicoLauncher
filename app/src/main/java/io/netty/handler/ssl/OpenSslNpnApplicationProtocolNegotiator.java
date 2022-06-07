package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@Deprecated
/* loaded from: classes4.dex */
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final List<String> a;

    public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this.a = (List) ObjectUtil.checkNotNull(b.a(iterable), "protocols");
    }

    public OpenSslNpnApplicationProtocolNegotiator(String... strArr) {
        this.a = (List) ObjectUtil.checkNotNull(b.a(strArr), "protocols");
    }

    @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
    public ApplicationProtocolConfig.Protocol protocol() {
        return ApplicationProtocolConfig.Protocol.NPN;
    }

    @Override // io.netty.handler.ssl.ApplicationProtocolNegotiator
    public List<String> protocols() {
        return this.a;
    }

    @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
        return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
    }

    @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
    }
}
