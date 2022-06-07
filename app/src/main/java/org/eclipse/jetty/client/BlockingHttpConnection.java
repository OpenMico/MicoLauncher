package org.eclipse.jetty.client;

import java.io.IOException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class BlockingHttpConnection extends AbstractHttpConnection {
    private static final Logger LOG = Log.getLogger(BlockingHttpConnection.class);
    private boolean _requestComplete;
    private Buffer _requestContentChunk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BlockingHttpConnection(Buffers buffers, Buffers buffers2, EndPoint endPoint) {
        super(buffers, buffers2, endPoint);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.client.AbstractHttpConnection
    public void reset() throws IOException {
        this._requestComplete = false;
        super.reset();
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0152 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0003 A[SYNTHETIC] */
    @Override // org.eclipse.jetty.client.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.io.Connection handle() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 904
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.BlockingHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }

    @Override // org.eclipse.jetty.client.AbstractHttpConnection
    public boolean send(HttpExchange httpExchange) throws IOException {
        boolean send = super.send(httpExchange);
        if (send) {
            synchronized (this) {
                notifyAll();
            }
        }
        return send;
    }
}
