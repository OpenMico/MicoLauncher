package io.netty.handler.ssl;

import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;

/* loaded from: classes4.dex */
public interface JdkApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {

    /* loaded from: classes4.dex */
    public interface ProtocolSelectionListener {
        void selected(String str) throws Exception;

        void unsupported();
    }

    /* loaded from: classes4.dex */
    public interface ProtocolSelectionListenerFactory {
        ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list);
    }

    /* loaded from: classes4.dex */
    public interface ProtocolSelector {
        String select(List<String> list) throws Exception;

        void unsupported();
    }

    /* loaded from: classes4.dex */
    public interface ProtocolSelectorFactory {
        ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set);
    }

    /* loaded from: classes4.dex */
    public interface SslEngineWrapperFactory {
        SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z);
    }

    ProtocolSelectionListenerFactory protocolListenerFactory();

    ProtocolSelectorFactory protocolSelectorFactory();

    SslEngineWrapperFactory wrapperFactory();
}
