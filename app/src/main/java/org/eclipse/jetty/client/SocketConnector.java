package org.eclipse.jetty.client;

import java.io.IOException;
import java.net.Socket;
import javax.net.SocketFactory;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.bio.SocketEndPoint;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
class SocketConnector extends AbstractLifeCycle implements HttpClient.Connector {
    private static final Logger LOG = Log.getLogger(SocketConnector.class);
    private final HttpClient _httpClient;

    static /* synthetic */ Logger access$000() {
        return LOG;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SocketConnector(HttpClient httpClient) {
        this._httpClient = httpClient;
    }

    @Override // org.eclipse.jetty.client.HttpClient.Connector
    public void startConnection(final HttpDestination httpDestination) throws IOException {
        Socket newSslSocket = httpDestination.isSecure() ? this._httpClient.getSslContextFactory().newSslSocket() : SocketFactory.getDefault().createSocket();
        newSslSocket.setSoTimeout(0);
        newSslSocket.setTcpNoDelay(true);
        newSslSocket.connect((httpDestination.isProxied() ? httpDestination.getProxy() : httpDestination.getAddress()).toSocketAddress(), this._httpClient.getConnectTimeout());
        final BlockingHttpConnection blockingHttpConnection = new BlockingHttpConnection(this._httpClient.getRequestBuffers(), this._httpClient.getResponseBuffers(), new SocketEndPoint(newSslSocket));
        blockingHttpConnection.setDestination(httpDestination);
        httpDestination.onNewConnection(blockingHttpConnection);
        this._httpClient.getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.client.SocketConnector.1
            /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
                jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0043: INVOKE  
                  (r2v1 ?? I:org.eclipse.jetty.client.HttpDestination)
                  (r3v0 ?? I:org.eclipse.jetty.client.AbstractHttpConnection)
                  (r0 I:boolean)
                 type: VIRTUAL call: org.eclipse.jetty.client.HttpDestination.returnConnection(org.eclipse.jetty.client.AbstractHttpConnection, boolean):void, block:B:18:0x003f
                	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
                	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
                	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
                	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
                */
            @Override // java.lang.Runnable
            public void run() {
                /*
                    r4 = this;
                    r0 = 1
                    org.eclipse.jetty.client.AbstractHttpConnection r1 = r2     // Catch: IOException -> 0x001e, all -> 0x001c
                L_0x0003:
                    org.eclipse.jetty.io.Connection r2 = r1.handle()     // Catch: IOException -> 0x001e, all -> 0x001c
                    if (r2 == r1) goto L_0x000b
                    r1 = r2
                    goto L_0x0003
                L_0x000b:
                    org.eclipse.jetty.client.HttpDestination r1 = r3     // Catch: IOException -> 0x0013
                    org.eclipse.jetty.client.AbstractHttpConnection r2 = r2     // Catch: IOException -> 0x0013
                    r1.returnConnection(r2, r0)     // Catch: IOException -> 0x0013
                    goto L_0x003e
                L_0x0013:
                    r0 = move-exception
                    org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.client.SocketConnector.access$000()
                    r1.debug(r0)
                    goto L_0x003e
                L_0x001c:
                    r1 = move-exception
                    goto L_0x003f
                L_0x001e:
                    r1 = move-exception
                    boolean r2 = r1 instanceof java.io.InterruptedIOException     // Catch: all -> 0x001c
                    if (r2 == 0) goto L_0x002b
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.client.SocketConnector.access$000()     // Catch: all -> 0x001c
                    r2.ignore(r1)     // Catch: all -> 0x001c
                    goto L_0x0037
                L_0x002b:
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.client.SocketConnector.access$000()     // Catch: all -> 0x001c
                    r2.debug(r1)     // Catch: all -> 0x001c
                    org.eclipse.jetty.client.HttpDestination r2 = r3     // Catch: all -> 0x001c
                    r2.onException(r1)     // Catch: all -> 0x001c
                L_0x0037:
                    org.eclipse.jetty.client.HttpDestination r1 = r3     // Catch: IOException -> 0x0013
                    org.eclipse.jetty.client.AbstractHttpConnection r2 = r2     // Catch: IOException -> 0x0013
                    r1.returnConnection(r2, r0)     // Catch: IOException -> 0x0013
                L_0x003e:
                    return
                L_0x003f:
                    org.eclipse.jetty.client.HttpDestination r2 = r3     // Catch: IOException -> 0x0047
                    org.eclipse.jetty.client.AbstractHttpConnection r3 = r2     // Catch: IOException -> 0x0047
                    r2.returnConnection(r3, r0)     // Catch: IOException -> 0x0047
                    goto L_0x004f
                L_0x0047:
                    r0 = move-exception
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.client.SocketConnector.access$000()
                    r2.debug(r0)
                L_0x004f:
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.SocketConnector.AnonymousClass1.run():void");
            }
        });
    }
}
