package org.eclipse.jetty.client;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.jetty.client.security.Authentication;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.View;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes5.dex */
public abstract class AbstractHttpConnection extends AbstractConnection implements Dumpable {
    private static final Logger LOG = Log.getLogger(AbstractHttpConnection.class);
    protected Buffer _connectionHeader;
    protected HttpDestination _destination;
    protected volatile HttpExchange _exchange;
    protected HttpGenerator _generator;
    protected HttpParser _parser;
    protected HttpExchange _pipeline;
    protected boolean _reserved;
    protected int _status;
    protected boolean _http11 = true;
    private final Timeout.Task _idleTimeout = new ConnectionIdleTask();
    private AtomicBoolean _idle = new AtomicBoolean(false);

    @Override // org.eclipse.jetty.io.Connection
    public abstract Connection handle() throws IOException;

    @Override // org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return false;
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onClose() {
    }

    public AbstractHttpConnection(Buffers buffers, Buffers buffers2, EndPoint endPoint) {
        super(endPoint);
        this._generator = new HttpGenerator(buffers, endPoint);
        this._parser = new HttpParser(buffers2, endPoint, new Handler());
    }

    public void setReserved(boolean z) {
        this._reserved = z;
    }

    public boolean isReserved() {
        return this._reserved;
    }

    public HttpDestination getDestination() {
        return this._destination;
    }

    public void setDestination(HttpDestination httpDestination) {
        this._destination = httpDestination;
    }

    public boolean send(HttpExchange httpExchange) throws IOException {
        LOG.debug("Send {} on {}", httpExchange, this);
        synchronized (this) {
            if (this._exchange == null) {
                this._exchange = httpExchange;
                this._exchange.associate(this);
                if (!this._endp.isOpen()) {
                    this._exchange.disassociate();
                    this._exchange = null;
                    return false;
                }
                this._exchange.setStatus(2);
                adjustIdleTimeout();
                return true;
            } else if (this._pipeline == null) {
                this._pipeline = httpExchange;
                return true;
            } else {
                throw new IllegalStateException(this + " PIPELINED!!!  _exchange=" + this._exchange);
            }
        }
    }

    private void adjustIdleTimeout() throws IOException {
        long timeout = this._exchange.getTimeout();
        if (timeout <= 0) {
            timeout = this._destination.getHttpClient().getTimeout();
        }
        long maxIdleTime = this._endp.getMaxIdleTime();
        if (timeout > 0 && timeout > maxIdleTime) {
            this._endp.setMaxIdleTime(((int) timeout) * 2);
        }
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isIdle() {
        boolean z;
        synchronized (this) {
            z = this._exchange == null;
        }
        return z;
    }

    protected void commitRequest() throws IOException {
        synchronized (this) {
            this._status = 0;
            if (this._exchange.getStatus() == 2) {
                this._exchange.setStatus(3);
                this._generator.setVersion(this._exchange.getVersion());
                String method = this._exchange.getMethod();
                String requestURI = this._exchange.getRequestURI();
                if (this._destination.isProxied()) {
                    if (!HttpMethods.CONNECT.equals(method) && requestURI.startsWith("/")) {
                        boolean isSecure = this._destination.isSecure();
                        String host = this._destination.getAddress().getHost();
                        int port = this._destination.getAddress().getPort();
                        StringBuilder sb = new StringBuilder();
                        sb.append(isSecure ? "https" : "http");
                        sb.append("://");
                        sb.append(host);
                        if ((!isSecure || port != 443) && (isSecure || port != 80)) {
                            sb.append(Constants.COLON_SEPARATOR);
                            sb.append(port);
                        }
                        sb.append(requestURI);
                        requestURI = sb.toString();
                    }
                    Authentication proxyAuthentication = this._destination.getProxyAuthentication();
                    if (proxyAuthentication != null) {
                        proxyAuthentication.setCredentials(this._exchange);
                    }
                }
                this._generator.setRequest(method, requestURI);
                this._parser.setHeadResponse("HEAD".equalsIgnoreCase(method));
                HttpFields requestFields = this._exchange.getRequestFields();
                if (this._exchange.getVersion() >= 11 && !requestFields.containsKey(HttpHeaders.HOST_BUFFER)) {
                    requestFields.add(HttpHeaders.HOST_BUFFER, this._destination.getHostHeader());
                }
                Buffer requestContent = this._exchange.getRequestContent();
                if (requestContent != null) {
                    requestFields.putLongField("Content-Length", requestContent.length());
                    this._generator.completeHeader(requestFields, false);
                    this._generator.addContent(new View(requestContent), true);
                    this._exchange.setStatus(4);
                } else if (this._exchange.getRequestContentSource() != null) {
                    this._generator.completeHeader(requestFields, false);
                } else {
                    requestFields.remove("Content-Length");
                    this._generator.completeHeader(requestFields, true);
                    this._exchange.setStatus(4);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public void reset() throws IOException {
        this._connectionHeader = null;
        this._parser.reset();
        this._generator.reset();
        this._http11 = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class Handler extends HttpParser.EventHandler {
        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        }

        private Handler() {
            AbstractHttpConnection.this = r1;
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void startResponse(Buffer buffer, int i, Buffer buffer2) throws IOException {
            HttpExchange httpExchange = AbstractHttpConnection.this._exchange;
            if (httpExchange == null) {
                AbstractHttpConnection.LOG.warn("No exchange for response", new Object[0]);
                AbstractHttpConnection.this._endp.close();
                return;
            }
            if (i == 100 || i == 102) {
                httpExchange.setEventListener(new NonFinalResponseListener(httpExchange));
            } else if (i == 200 && HttpMethods.CONNECT.equalsIgnoreCase(httpExchange.getMethod())) {
                AbstractHttpConnection.this._parser.setHeadResponse(true);
            }
            AbstractHttpConnection.this._http11 = HttpVersions.HTTP_1_1_BUFFER.equals(buffer);
            AbstractHttpConnection.this._status = i;
            httpExchange.getEventListener().onResponseStatus(buffer, i, buffer2);
            httpExchange.setStatus(5);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
            HttpExchange httpExchange = AbstractHttpConnection.this._exchange;
            if (httpExchange != null) {
                if (HttpHeaders.CACHE.getOrdinal(buffer) == 1) {
                    AbstractHttpConnection.this._connectionHeader = HttpHeaderValues.CACHE.lookup(buffer2);
                }
                httpExchange.getEventListener().onResponseHeader(buffer, buffer2);
            }
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void headerComplete() throws IOException {
            HttpExchange httpExchange = AbstractHttpConnection.this._exchange;
            if (httpExchange != null) {
                httpExchange.setStatus(6);
            }
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void content(Buffer buffer) throws IOException {
            HttpExchange httpExchange = AbstractHttpConnection.this._exchange;
            if (httpExchange != null) {
                httpExchange.getEventListener().onResponseContent(buffer);
            }
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void messageComplete(long j) throws IOException {
            HttpExchange httpExchange = AbstractHttpConnection.this._exchange;
            if (httpExchange != null) {
                httpExchange.setStatus(7);
            }
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void earlyEOF() {
            HttpExchange httpExchange = AbstractHttpConnection.this._exchange;
            if (httpExchange != null && !httpExchange.isDone() && httpExchange.setStatus(9)) {
                httpExchange.getEventListener().onException(new EofException("early EOF"));
            }
        }
    }

    @Override // org.eclipse.jetty.io.AbstractConnection
    public String toString() {
        Object[] objArr = new Object[4];
        objArr[0] = super.toString();
        HttpDestination httpDestination = this._destination;
        objArr[1] = httpDestination == null ? "?.?.?.?:??" : httpDestination.getAddress();
        objArr[2] = this._generator;
        objArr[3] = this._parser;
        return String.format("%s %s g=%s p=%s", objArr);
    }

    public String toDetailString() {
        return toString() + " ex=" + this._exchange + " idle for " + this._idleTimeout.getAge();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0021, code lost:
        if (r6._parser.isState(1) != false) goto L_0x0063;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void close() throws java.io.IOException {
        /*
            r6 = this;
            org.eclipse.jetty.client.HttpExchange r0 = r6._exchange
            r1 = 1
            if (r0 == 0) goto L_0x0063
            boolean r2 = r0.isDone()
            if (r2 != 0) goto L_0x0063
            int r2 = r0.getStatus()
            switch(r2) {
                case 6: goto L_0x0013;
                case 7: goto L_0x0063;
                case 8: goto L_0x0063;
                case 9: goto L_0x0063;
                case 10: goto L_0x0063;
                case 11: goto L_0x0063;
                default: goto L_0x0012;
            }
        L_0x0012:
            goto L_0x0024
        L_0x0013:
            org.eclipse.jetty.io.EndPoint r2 = r6._endp
            boolean r2 = r2.isInputShutdown()
            if (r2 == 0) goto L_0x0024
            org.eclipse.jetty.http.HttpParser r2 = r6._parser
            boolean r2 = r2.isState(r1)
            if (r2 == 0) goto L_0x0024
            goto L_0x0063
        L_0x0024:
            java.lang.String r2 = r0.toString()
            org.eclipse.jetty.io.EndPoint r3 = r6._endp
            boolean r3 = r3.isOpen()
            if (r3 == 0) goto L_0x003e
            org.eclipse.jetty.io.EndPoint r3 = r6._endp
            boolean r3 = r3.isInputShutdown()
            if (r3 == 0) goto L_0x003b
            java.lang.String r3 = "half closed: "
            goto L_0x0040
        L_0x003b:
            java.lang.String r3 = "local close: "
            goto L_0x0040
        L_0x003e:
            java.lang.String r3 = "closed: "
        L_0x0040:
            r4 = 9
            boolean r4 = r0.setStatus(r4)
            if (r4 == 0) goto L_0x0063
            org.eclipse.jetty.client.HttpEventListener r0 = r0.getEventListener()
            org.eclipse.jetty.io.EofException r4 = new org.eclipse.jetty.io.EofException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r2)
            java.lang.String r2 = r5.toString()
            r4.<init>(r2)
            r0.onException(r4)
        L_0x0063:
            org.eclipse.jetty.io.EndPoint r0 = r6._endp
            boolean r0 = r0.isOpen()
            if (r0 == 0) goto L_0x0075
            org.eclipse.jetty.io.EndPoint r0 = r6._endp
            r0.close()
            org.eclipse.jetty.client.HttpDestination r0 = r6._destination
            r0.returnConnection(r6, r1)
        L_0x0075:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.AbstractHttpConnection.close():void");
    }

    public void setIdleTimeout() {
        synchronized (this) {
            if (this._idle.compareAndSet(false, true)) {
                this._destination.getHttpClient().scheduleIdle(this._idleTimeout);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public boolean cancelIdleTimeout() {
        synchronized (this) {
            if (!this._idle.compareAndSet(true, false)) {
                return false;
            }
            this._destination.getHttpClient().cancel(this._idleTimeout);
            return true;
        }
    }

    public void exchangeExpired(HttpExchange httpExchange) {
        synchronized (this) {
            if (this._exchange == httpExchange) {
                try {
                    this._destination.returnConnection(this, true);
                } catch (IOException e) {
                    LOG.ignore(e);
                }
            }
        }
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        synchronized (this) {
            appendable.append(String.valueOf(this)).append("\n");
            AggregateLifeCycle.dump(appendable, str, Collections.singletonList(this._endp));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class ConnectionIdleTask extends Timeout.Task {
        private ConnectionIdleTask() {
            AbstractHttpConnection.this = r1;
        }

        @Override // org.eclipse.jetty.util.thread.Timeout.Task
        public void expired() {
            if (AbstractHttpConnection.this._idle.compareAndSet(true, false)) {
                AbstractHttpConnection.this._destination.returnIdleConnection(AbstractHttpConnection.this);
            }
        }
    }

    /* loaded from: classes5.dex */
    private class NonFinalResponseListener implements HttpEventListener {
        final HttpExchange _exchange;
        final HttpEventListener _next;

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRequestCommitted() throws IOException {
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRequestComplete() throws IOException {
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseContent(Buffer buffer) throws IOException {
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException {
        }

        public NonFinalResponseListener(HttpExchange httpExchange) {
            AbstractHttpConnection.this = r1;
            this._exchange = httpExchange;
            this._next = httpExchange.getEventListener();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
            this._next.onResponseHeader(buffer, buffer2);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseHeaderComplete() throws IOException {
            this._next.onResponseHeaderComplete();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseComplete() throws IOException {
            this._exchange.setEventListener(this._next);
            this._exchange.setStatus(4);
            AbstractHttpConnection.this._parser.reset();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onConnectionFailed(Throwable th) {
            this._exchange.setEventListener(this._next);
            this._next.onConnectionFailed(th);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onException(Throwable th) {
            this._exchange.setEventListener(this._next);
            this._next.onException(th);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onExpire() {
            this._exchange.setEventListener(this._next);
            this._next.onExpire();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRetry() {
            this._exchange.setEventListener(this._next);
            this._next.onRetry();
        }
    }
}
