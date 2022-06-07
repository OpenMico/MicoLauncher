package org.eclipse.jetty.client;

import java.io.IOException;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.AsyncConnection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class AsyncHttpConnection extends AbstractHttpConnection implements AsyncConnection {
    private static final Logger LOG = Log.getLogger(AsyncHttpConnection.class);
    private final AsyncEndPoint _asyncEndp;
    private boolean _requestComplete;
    private Buffer _requestContentChunk;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AsyncHttpConnection(Buffers buffers, Buffers buffers2, EndPoint endPoint) {
        super(buffers, buffers2, endPoint);
        this._asyncEndp = (AsyncEndPoint) endPoint;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.client.AbstractHttpConnection
    public void reset() throws IOException {
        this._requestComplete = false;
        super.reset();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x02ec A[Catch: all -> 0x038c, TryCatch #7 {all -> 0x038c, blocks: (B:6:0x000a, B:65:0x013d, B:67:0x0156, B:69:0x015e, B:76:0x016e, B:78:0x0176, B:82:0x0181, B:84:0x018b, B:85:0x019b, B:134:0x0241, B:135:0x0260, B:176:0x02d3, B:178:0x02ec, B:180:0x02f4, B:187:0x0304, B:189:0x030c, B:193:0x0317, B:195:0x0321, B:196:0x0331, B:227:0x038b, B:86:0x019c, B:88:0x01a2, B:89:0x01ab, B:91:0x01af, B:93:0x01b7, B:95:0x01bb, B:96:0x01c2, B:97:0x01c5, B:102:0x01ce, B:103:0x01d3, B:104:0x01da, B:105:0x01dc, B:107:0x01e0, B:109:0x01e6, B:113:0x01ed, B:114:0x01f0, B:197:0x0332, B:199:0x0338, B:200:0x0341, B:202:0x0345, B:204:0x034d, B:206:0x0351, B:207:0x0358, B:208:0x035b, B:213:0x0364, B:214:0x0369, B:215:0x0370, B:216:0x0372, B:218:0x0376, B:220:0x037c, B:224:0x0383, B:225:0x0386, B:136:0x0261, B:138:0x0267, B:139:0x0270, B:141:0x0274, B:143:0x027c, B:145:0x0280, B:146:0x0287, B:148:0x028b, B:150:0x028f, B:151:0x0298, B:153:0x029c, B:155:0x02a2, B:156:0x02a7), top: B:240:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x02aa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x016c  */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v29 */
    @Override // org.eclipse.jetty.client.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.io.Connection handle() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 965
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.AsyncHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }

    @Override // org.eclipse.jetty.io.nio.AsyncConnection
    public void onInputShutdown() throws IOException {
        if (this._generator.isIdle()) {
            this._endp.shutdownOutput();
        }
    }

    @Override // org.eclipse.jetty.client.AbstractHttpConnection
    public boolean send(HttpExchange httpExchange) throws IOException {
        boolean send = super.send(httpExchange);
        if (send) {
            this._asyncEndp.asyncDispatch();
        }
        return send;
    }
}
