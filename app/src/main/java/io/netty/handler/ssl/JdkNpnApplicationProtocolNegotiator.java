package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import java.util.List;
import javax.net.ssl.SSLEngine;

/* loaded from: classes4.dex */
public final class JdkNpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory e = new JdkApplicationProtocolNegotiator.SslEngineWrapperFactory() { // from class: io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator.1
        {
            if (!f.a()) {
                throw new RuntimeException("NPN unsupported. Is your classpatch configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
            }
        }

        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory
        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
            return new f(sSLEngine, jdkApplicationProtocolNegotiator, z);
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

    public JdkNpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this(false, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(String... strArr) {
        this(false, strArr);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, Iterable<String> iterable) {
        this(z, z, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, String... strArr) {
        this(z, z, strArr);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, boolean z2, Iterable<String> iterable) {
        this(z ? a : b, z2 ? c : d, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, boolean z2, String... strArr) {
        this(z ? a : b, z2 ? c : d, strArr);
    }

    public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        super(e, protocolSelectorFactory, protocolSelectionListenerFactory, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String... strArr) {
        super(e, protocolSelectorFactory, protocolSelectionListenerFactory, strArr);
    }
}
