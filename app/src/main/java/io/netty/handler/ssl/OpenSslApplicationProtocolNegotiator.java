package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;

/* loaded from: classes4.dex */
public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
    ApplicationProtocolConfig.Protocol protocol();

    ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior();

    ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior();
}
