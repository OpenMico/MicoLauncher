package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import java.util.List;
import javax.net.ssl.SSLEngine;

/* loaded from: classes4.dex */
public final class JdkAlpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory e = new JdkApplicationProtocolNegotiator.SslEngineWrapperFactory() { // from class: io.netty.handler.ssl.JdkAlpnApplicationProtocolNegotiator.1
        {
            if (!d.a()) {
                throw new RuntimeException("ALPN unsupported. Is your classpatch configured correctly? See http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting");
            }
        }

        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory
        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
            return new d(sSLEngine, jdkApplicationProtocolNegotiator, z);
        }
    };

    @Override // io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator, io.netty.handler.ssl.JdkApplicationProtocolNegotiator
    public /* bridge */ /* synthetic */ JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
        return super.protocolListenerFactory();
    }

    @Override // io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator, io.netty.handler.ssl.JdkApplicationProtocolNegotiator
    public /* bridge */ /* synthetic */ JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
        return super.protocolSelectorFactory();
    }

    @Override // io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator, io.netty.handler.ssl.ApplicationProtocolNegotiator
    public /* bridge */ /* synthetic */ List protocols() {
        return super.protocols();
    }

    @Override // io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator, io.netty.handler.ssl.JdkApplicationProtocolNegotiator
    public /* bridge */ /* synthetic */ JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
        return super.wrapperFactory();
    }

    public JdkAlpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this(false, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(String... strArr) {
        this(false, strArr);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, Iterable<String> iterable) {
        this(z, z, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, String... strArr) {
        this(z, z, strArr);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, boolean z2, Iterable<String> iterable) {
        this(z2 ? a : b, z ? c : d, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, boolean z2, String... strArr) {
        this(z2 ? a : b, z ? c : d, strArr);
    }

    public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        super(e, protocolSelectorFactory, protocolSelectionListenerFactory, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String... strArr) {
        super(e, protocolSelectorFactory, protocolSelectionListenerFactory, strArr);
    }
}
