package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

/* loaded from: classes4.dex */
public final class OpenSslDefaultApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final ApplicationProtocolConfig a;

    public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig applicationProtocolConfig) {
        this.a = (ApplicationProtocolConfig) ObjectUtil.checkNotNull(applicationProtocolConfig, "config");
    }

    @Override // io.netty.handler.ssl.ApplicationProtocolNegotiator
    public List<String> protocols() {
        return this.a.supportedProtocols();
    }

    @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
    public ApplicationProtocolConfig.Protocol protocol() {
        return this.a.protocol();
    }

    @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
    public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
        return this.a.selectorFailureBehavior();
    }

    @Override // io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator
    public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.a.selectedListenerFailureBehavior();
    }
}
