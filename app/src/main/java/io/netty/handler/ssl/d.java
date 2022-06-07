package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import java.util.LinkedHashSet;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.alpn.ALPN;

/* compiled from: JdkAlpnSslEngine.java */
/* loaded from: classes4.dex */
final class d extends g {
    private static boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        d();
        return a;
    }

    private static void d() {
        if (!a) {
            try {
                Class.forName("sun.security.ssl.ALPNExtension", true, null);
                a = true;
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(SSLEngine sSLEngine, final JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
        super(sSLEngine);
        ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "applicationNegotiator");
        if (z) {
            final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
            ALPN.put(sSLEngine, new ALPN.ServerProvider() { // from class: io.netty.handler.ssl.d.1
            });
            return;
        }
        final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolSelectionListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
        ALPN.put(sSLEngine, new ALPN.ClientProvider() { // from class: io.netty.handler.ssl.d.2
        });
    }

    @Override // io.netty.handler.ssl.g, javax.net.ssl.SSLEngine
    public void closeInbound() throws SSLException {
        ALPN.remove(c());
        super.closeInbound();
    }

    @Override // io.netty.handler.ssl.g, javax.net.ssl.SSLEngine
    public void closeOutbound() {
        ALPN.remove(c());
        super.closeOutbound();
    }
}
