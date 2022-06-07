package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import java.util.LinkedHashSet;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.npn.NextProtoNego;

/* compiled from: JdkNpnSslEngine.java */
/* loaded from: classes4.dex */
final class f extends g {
    private static boolean a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        d();
        return a;
    }

    private static void d() {
        if (!a) {
            try {
                Class.forName("sun.security.ssl.NextProtoNegoExtension", true, null);
                a = true;
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(SSLEngine sSLEngine, final JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
        super(sSLEngine);
        ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "applicationNegotiator");
        if (z) {
            final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolSelectionListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
            NextProtoNego.put(sSLEngine, new NextProtoNego.ServerProvider() { // from class: io.netty.handler.ssl.f.1
            });
            return;
        }
        final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
        NextProtoNego.put(sSLEngine, new NextProtoNego.ClientProvider() { // from class: io.netty.handler.ssl.f.2
        });
    }

    @Override // io.netty.handler.ssl.g, javax.net.ssl.SSLEngine
    public void closeInbound() throws SSLException {
        NextProtoNego.remove(c());
        super.closeInbound();
    }

    @Override // io.netty.handler.ssl.g, javax.net.ssl.SSLEngine
    public void closeOutbound() {
        NextProtoNego.remove(c());
        super.closeOutbound();
    }
}
