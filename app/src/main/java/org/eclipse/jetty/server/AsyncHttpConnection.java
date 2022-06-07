package org.eclipse.jetty.server;

import java.io.IOException;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.AsyncConnection;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class AsyncHttpConnection extends AbstractHttpConnection implements AsyncConnection {
    private final AsyncEndPoint _asyncEndp;
    private boolean _readInterested = true;
    private int _total_no_progress;
    private static final int NO_PROGRESS_INFO = Integer.getInteger("org.mortbay.jetty.NO_PROGRESS_INFO", 100).intValue();
    private static final int NO_PROGRESS_CLOSE = Integer.getInteger("org.mortbay.jetty.NO_PROGRESS_CLOSE", 200).intValue();
    private static final Logger LOG = Log.getLogger(AsyncHttpConnection.class);

    public AsyncHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(connector, endPoint, server);
        this._asyncEndp = (AsyncEndPoint) endPoint;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:(10:(2:10|(1:12))(2:13|(16:17|161|19|175|(1:29)|30|(1:32)|33|165|34|(1:38)(1:37)|(1:(5:41|(1:45)|46|(1:50)|51)(1:52))|(2:56|57)|58|178|177))|165|34|(0)|38|(0)|(3:54|56|57)|58|178|177)|161|19|175|(5:21|23|25|27|29)|30|(0)|33) */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01e5, code lost:
        if (r7 != false) goto L_0x01e7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01ed, code lost:
        if (r11._response.getStatus() == 101) goto L_0x01ef;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01ef, code lost:
        r3 = (org.eclipse.jetty.io.Connection) r11._request.getAttribute("org.eclipse.jetty.io.Connection");
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01f9, code lost:
        reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0202, code lost:
        if (r11._generator.isPersistent() == false) goto L_0x0204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x020c, code lost:
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.warn("Safety net oshut!!!  IF YOU SEE THIS, PLEASE RAISE BUGZILLA", new java.lang.Object[0]);
        r11._endp.shutdownOutput();
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x021b, code lost:
        r11._readInterested = false;
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug("Disabled read interest while writing response {}", r11._endp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f6, code lost:
        r7 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0105, code lost:
        if (org.eclipse.jetty.server.AsyncHttpConnection.LOG.isDebugEnabled() != false) goto L_0x0107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0107, code lost:
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug("uri=" + r11._uri, new java.lang.Object[0]);
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug("fields=" + r11._requestFields, new java.lang.Object[0]);
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0140, code lost:
        r11._generator.sendError(r7.getStatus(), r7.getReason(), null, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x014d, code lost:
        r5 = r5 | true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x014f, code lost:
        r6 = r11._parser.isComplete();
        r7 = r11._generator.isComplete();
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x015b, code lost:
        if (r6 == false) goto L_0x0161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x015f, code lost:
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0161, code lost:
        r8 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0162, code lost:
        if (r6 != false) goto L_0x0164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0164, code lost:
        if (r7 != false) goto L_0x0166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x016c, code lost:
        if (r11._response.getStatus() == 101) goto L_0x016e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x017a, code lost:
        r4 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x017b, code lost:
        reset();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0184, code lost:
        if (r11._generator.isPersistent() == false) goto L_0x0186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x018e, code lost:
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.warn("Safety net oshut!!!  IF YOU SEE THIS, PLEASE RAISE BUGZILLA", new java.lang.Object[0]);
        r11._endp.shutdownOutput();
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x019d, code lost:
        r11._readInterested = false;
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug("Disabled read interest while writing response {}", r11._endp);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01ac, code lost:
        if (r8 != false) goto L_0x01c8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01ba, code lost:
        org.eclipse.jetty.server.AsyncHttpConnection.LOG.debug("suspended {}", r11);
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01c8, code lost:
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01cb, code lost:
        r4 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01cc, code lost:
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01e0, code lost:
        r8 = true;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0090  */
    /* JADX WARN: Type inference failed for: r3v48, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Type inference failed for: r3v63, types: [org.eclipse.jetty.io.Connection] */
    /* JADX WARN: Unknown variable types count: 2 */
    @Override // org.eclipse.jetty.server.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.eclipse.jetty.io.Connection handle() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 926
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AsyncHttpConnection.handle():org.eclipse.jetty.io.Connection");
    }

    @Override // org.eclipse.jetty.io.nio.AsyncConnection
    public void onInputShutdown() throws IOException {
        if (this._generator.isIdle() && !this._request.getAsyncContinuation().isSuspended()) {
            this._endp.close();
        }
        if (this._parser.isIdle()) {
            this._parser.setPersistent(false);
        }
    }

    @Override // org.eclipse.jetty.server.AbstractHttpConnection
    public void reset() {
        this._readInterested = true;
        LOG.debug("Enabled read interest {}", this._endp);
        super.reset();
    }

    @Override // org.eclipse.jetty.server.AbstractHttpConnection, org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return !this._readInterested || super.isSuspended();
    }
}
