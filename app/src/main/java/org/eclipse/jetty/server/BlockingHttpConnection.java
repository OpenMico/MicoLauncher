package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.http.Generator;
import org.eclipse.jetty.http.Parser;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class BlockingHttpConnection extends AbstractHttpConnection {
    private static final Logger LOG = Log.getLogger(BlockingHttpConnection.class);

    public BlockingHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(connector, endPoint, server);
    }

    public BlockingHttpConnection(Connector connector, EndPoint endPoint, Server server, Parser parser, Generator generator, Request request) {
        super(connector, endPoint, server, parser, generator, request);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.AbstractHttpConnection
    public void handleRequest() throws IOException {
        super.handleRequest();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v19, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r2v37, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // org.eclipse.jetty.server.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.io.Connection handle() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 503
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.BlockingHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }
}
