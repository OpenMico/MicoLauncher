package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.EncodedHttpURI;
import org.eclipse.jetty.http.Generator;
import org.eclipse.jetty.http.HttpBuffers;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpGenerator;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.http.Parser;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.UncheckedPrintWriter;
import org.eclipse.jetty.server.nio.NIOConnector;
import org.eclipse.jetty.server.ssl.SslConnector;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

/* loaded from: classes5.dex */
public abstract class AbstractHttpConnection extends AbstractConnection {
    private static final int UNKNOWN = -2;
    private Object _associatedObject;
    private String _charset;
    protected final Connector _connector;
    protected final Generator _generator;
    protected volatile ServletInputStream _in;
    int _include;
    protected volatile Output _out;
    protected final Parser _parser;
    protected volatile PrintWriter _printWriter;
    protected final Request _request;
    protected final HttpFields _requestFields;
    private int _requests;
    protected final Response _response;
    protected final HttpFields _responseFields;
    protected final Server _server;
    protected final HttpURI _uri;
    protected volatile OutputWriter _writer;
    private static final Logger LOG = Log.getLogger(AbstractHttpConnection.class);
    private static final ThreadLocal<AbstractHttpConnection> __currentConnection = new ThreadLocal<>();
    private int _version = -2;
    private boolean _expect = false;
    private boolean _expect100Continue = false;
    private boolean _expect102Processing = false;
    private boolean _head = false;
    private boolean _host = false;
    private boolean _delayedHandling = false;
    private boolean _earlyEOF = false;

    @Override // org.eclipse.jetty.io.Connection
    public abstract Connection handle() throws IOException;

    public static AbstractHttpConnection getCurrentConnection() {
        return __currentConnection.get();
    }

    protected static void setCurrentConnection(AbstractHttpConnection abstractHttpConnection) {
        __currentConnection.set(abstractHttpConnection);
    }

    public AbstractHttpConnection(Connector connector, EndPoint endPoint, Server server) {
        super(endPoint);
        this._uri = "UTF-8".equals(URIUtil.__CHARSET) ? new HttpURI() : new EncodedHttpURI(URIUtil.__CHARSET);
        this._connector = connector;
        HttpBuffers httpBuffers = (HttpBuffers) this._connector;
        this._parser = newHttpParser(httpBuffers.getRequestBuffers(), endPoint, new RequestHandler());
        this._requestFields = new HttpFields();
        this._responseFields = new HttpFields();
        this._request = new Request(this);
        this._response = new Response(this);
        this._generator = newHttpGenerator(httpBuffers.getResponseBuffers(), endPoint);
        this._generator.setSendServerVersion(server.getSendServerVersion());
        this._server = server;
    }

    public AbstractHttpConnection(Connector connector, EndPoint endPoint, Server server, Parser parser, Generator generator, Request request) {
        super(endPoint);
        this._uri = URIUtil.__CHARSET.equals("UTF-8") ? new HttpURI() : new EncodedHttpURI(URIUtil.__CHARSET);
        this._connector = connector;
        this._parser = parser;
        this._requestFields = new HttpFields();
        this._responseFields = new HttpFields();
        this._request = request;
        this._response = new Response(this);
        this._generator = generator;
        this._generator.setSendServerVersion(server.getSendServerVersion());
        this._server = server;
    }

    protected HttpParser newHttpParser(Buffers buffers, EndPoint endPoint, HttpParser.EventHandler eventHandler) {
        return new HttpParser(buffers, endPoint, eventHandler);
    }

    protected HttpGenerator newHttpGenerator(Buffers buffers, EndPoint endPoint) {
        return new HttpGenerator(buffers, endPoint);
    }

    public Parser getParser() {
        return this._parser;
    }

    public int getRequests() {
        return this._requests;
    }

    public Server getServer() {
        return this._server;
    }

    public Object getAssociatedObject() {
        return this._associatedObject;
    }

    public void setAssociatedObject(Object obj) {
        this._associatedObject = obj;
    }

    public Connector getConnector() {
        return this._connector;
    }

    public HttpFields getRequestFields() {
        return this._requestFields;
    }

    public HttpFields getResponseFields() {
        return this._responseFields;
    }

    public boolean isConfidential(Request request) {
        Connector connector = this._connector;
        return connector != null && connector.isConfidential(request);
    }

    public boolean isIntegral(Request request) {
        Connector connector = this._connector;
        return connector != null && connector.isIntegral(request);
    }

    public boolean getResolveNames() {
        return this._connector.getResolveNames();
    }

    public Request getRequest() {
        return this._request;
    }

    public Response getResponse() {
        return this._response;
    }

    public ServletInputStream getInputStream() throws IOException {
        if (this._expect100Continue) {
            if (((HttpParser) this._parser).getHeaderBuffer() == null || ((HttpParser) this._parser).getHeaderBuffer().length() < 2) {
                if (!this._generator.isCommitted()) {
                    ((HttpGenerator) this._generator).send1xx(100);
                } else {
                    throw new IllegalStateException("Committed before 100 Continues");
                }
            }
            this._expect100Continue = false;
        }
        if (this._in == null) {
            this._in = new HttpInput(this);
        }
        return this._in;
    }

    public ServletOutputStream getOutputStream() {
        if (this._out == null) {
            this._out = new Output();
        }
        return this._out;
    }

    public PrintWriter getPrintWriter(String str) {
        getOutputStream();
        if (this._writer == null) {
            this._writer = new OutputWriter();
            if (this._server.isUncheckedPrintWriter()) {
                this._printWriter = new UncheckedPrintWriter(this._writer);
            } else {
                this._printWriter = new PrintWriter(this._writer) { // from class: org.eclipse.jetty.server.AbstractHttpConnection.1
                    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
                    public void close() {
                        synchronized (this.lock) {
                            try {
                                this.out.close();
                            } catch (IOException unused) {
                                setError();
                            }
                        }
                    }
                };
            }
        }
        this._writer.setCharacterEncoding(str);
        return this._printWriter;
    }

    public boolean isResponseCommitted() {
        return this._generator.isCommitted();
    }

    public boolean isEarlyEOF() {
        return this._earlyEOF;
    }

    public void reset() {
        this._parser.reset();
        this._parser.returnBuffers();
        this._requestFields.clear();
        this._request.recycle();
        this._generator.reset();
        this._generator.returnBuffers();
        this._responseFields.clear();
        this._response.recycle();
        this._uri.clear();
        this._writer = null;
        this._earlyEOF = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x01be, code lost:
        if (r15._server != null) goto L_0x0186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x012a, code lost:
        if (r15._server != null) goto L_0x0159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0157, code lost:
        if (r15._server != null) goto L_0x0159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0159, code lost:
        r6 = true;
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0184, code lost:
        if (r15._server != null) goto L_0x0186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0186, code lost:
        r7 = true;
        r6 = r6;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01ed A[Catch: all -> 0x01f6, TryCatch #10 {all -> 0x01f6, blocks: (B:17:0x004f, B:41:0x00de, B:43:0x00e8, B:45:0x00ee, B:112:0x01cd, B:114:0x01d7, B:116:0x01dd, B:119:0x01e3, B:121:0x01ed, B:123:0x01f3, B:124:0x01f5), top: B:196:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x004f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:226:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r11v0, types: [org.eclipse.jetty.util.log.Logger] */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.eclipse.jetty.server.AsyncContinuation] */
    /* JADX WARN: Type inference failed for: r6v19 */
    /* JADX WARN: Type inference failed for: r6v25, types: [java.lang.Throwable, org.eclipse.jetty.io.RuntimeIOException] */
    /* JADX WARN: Type inference failed for: r6v56 */
    /* JADX WARN: Type inference failed for: r6v57 */
    /* JADX WARN: Type inference failed for: r6v58 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v21 */
    /* JADX WARN: Type inference failed for: r7v24, types: [org.eclipse.jetty.util.log.Logger] */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v36, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r7v37, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r7v38, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r7v50 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleRequest() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 794
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.handleRequest():void");
    }

    public void commitResponse(boolean z) throws IOException {
        if (!this._generator.isCommitted()) {
            this._generator.setResponse(this._response.getStatus(), this._response.getReason());
            try {
                if (this._expect100Continue && this._response.getStatus() != 100) {
                    this._generator.setPersistent(false);
                }
                this._generator.completeHeader(this._responseFields, z);
            } catch (RuntimeException e) {
                Logger logger = LOG;
                logger.warn("header full: " + e, new Object[0]);
                this._response.reset();
                this._generator.reset();
                this._generator.setResponse(500, null);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                throw new HttpException(500);
            }
        }
        if (z) {
            this._generator.complete();
        }
    }

    public void completeResponse() throws IOException {
        if (!this._generator.isCommitted()) {
            this._generator.setResponse(this._response.getStatus(), this._response.getReason());
            try {
                this._generator.completeHeader(this._responseFields, true);
            } catch (RuntimeException e) {
                Logger logger = LOG;
                logger.warn("header full: " + e, new Object[0]);
                LOG.debug(e);
                this._response.reset();
                this._generator.reset();
                this._generator.setResponse(500, null);
                this._generator.completeHeader(this._responseFields, true);
                this._generator.complete();
                throw new HttpException(500);
            }
        }
        this._generator.complete();
    }

    public void flushResponse() throws IOException {
        try {
            commitResponse(false);
            this._generator.flushBuffer();
        } catch (IOException e) {
            if (!(e instanceof EofException)) {
                throw new EofException(e);
            }
        }
    }

    public Generator getGenerator() {
        return this._generator;
    }

    public boolean isIncluding() {
        return this._include > 0;
    }

    public void include() {
        this._include++;
    }

    public void included() {
        this._include--;
        if (this._out != null) {
            this._out.reopen();
        }
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isIdle() {
        return this._generator.isIdle() && (this._parser.isIdle() || this._delayedHandling);
    }

    @Override // org.eclipse.jetty.io.Connection
    public boolean isSuspended() {
        return this._request.getAsyncContinuation().isSuspended();
    }

    @Override // org.eclipse.jetty.io.Connection
    public void onClose() {
        LOG.debug("closed {}", this);
    }

    public boolean isExpecting100Continues() {
        return this._expect100Continue;
    }

    public boolean isExpecting102Processing() {
        return this._expect102Processing;
    }

    public int getMaxIdleTime() {
        if (this._connector.isLowResources() && this._endp.getMaxIdleTime() == this._connector.getMaxIdleTime()) {
            return this._connector.getLowResourceMaxIdleTime();
        }
        if (this._endp.getMaxIdleTime() > 0) {
            return this._endp.getMaxIdleTime();
        }
        return this._connector.getMaxIdleTime();
    }

    @Override // org.eclipse.jetty.io.AbstractConnection
    public String toString() {
        return String.format("%s,g=%s,p=%s,r=%d", super.toString(), this._generator, this._parser, Integer.valueOf(this._requests));
    }

    protected void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
        Buffer asImmutableBuffer = buffer2.asImmutableBuffer();
        this._host = false;
        this._expect = false;
        this._expect100Continue = false;
        this._expect102Processing = false;
        this._delayedHandling = false;
        this._charset = null;
        if (this._request.getTimeStamp() == 0) {
            this._request.setTimeStamp(System.currentTimeMillis());
        }
        this._request.setMethod(buffer.toString());
        try {
            this._head = false;
            int ordinal = HttpMethods.CACHE.getOrdinal(buffer);
            if (ordinal == 3) {
                this._head = true;
                this._uri.parse(asImmutableBuffer.array(), asImmutableBuffer.getIndex(), asImmutableBuffer.length());
            } else if (ordinal != 8) {
                this._uri.parse(asImmutableBuffer.array(), asImmutableBuffer.getIndex(), asImmutableBuffer.length());
            } else {
                this._uri.parseConnect(asImmutableBuffer.array(), asImmutableBuffer.getIndex(), asImmutableBuffer.length());
            }
            this._request.setUri(this._uri);
            if (buffer3 == null) {
                this._request.setProtocol("");
                this._version = 9;
                return;
            }
            BufferCache.CachedBuffer cachedBuffer = HttpVersions.CACHE.get(buffer3);
            if (cachedBuffer != null) {
                this._version = HttpVersions.CACHE.getOrdinal(cachedBuffer);
                if (this._version <= 0) {
                    this._version = 10;
                }
                this._request.setProtocol(cachedBuffer.toString());
                return;
            }
            throw new HttpException(400, null);
        } catch (Exception e) {
            LOG.debug(e);
            if (e instanceof HttpException) {
                throw ((HttpException) e);
            }
            throw new HttpException(400, null, e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
        if (r0 != 40) goto L_0x008d;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void parsedHeader(org.eclipse.jetty.io.Buffer r6, org.eclipse.jetty.io.Buffer r7) throws java.io.IOException {
        /*
            r5 = this;
            org.eclipse.jetty.http.HttpHeaders r0 = org.eclipse.jetty.http.HttpHeaders.CACHE
            int r0 = r0.getOrdinal(r6)
            r1 = 16
            if (r0 == r1) goto L_0x0081
            r1 = 21
            if (r0 == r1) goto L_0x007a
            r1 = 24
            r2 = 1
            if (r0 == r1) goto L_0x0021
            r1 = 27
            if (r0 == r1) goto L_0x001d
            r1 = 40
            if (r0 == r1) goto L_0x007a
            goto L_0x008d
        L_0x001d:
            r5._host = r2
            goto L_0x008d
        L_0x0021:
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            org.eclipse.jetty.io.Buffer r7 = r0.lookup(r7)
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            int r0 = r0.getOrdinal(r7)
            switch(r0) {
                case 6: goto L_0x0043;
                case 7: goto L_0x003c;
                default: goto L_0x0030;
            }
        L_0x0030:
            java.lang.String r0 = r7.toString()
            java.lang.String r1 = ","
            java.lang.String[] r0 = r0.split(r1)
            r1 = 0
            goto L_0x004a
        L_0x003c:
            org.eclipse.jetty.http.Generator r0 = r5._generator
            boolean r0 = r0 instanceof org.eclipse.jetty.http.HttpGenerator
            r5._expect102Processing = r0
            goto L_0x008d
        L_0x0043:
            org.eclipse.jetty.http.Generator r0 = r5._generator
            boolean r0 = r0 instanceof org.eclipse.jetty.http.HttpGenerator
            r5._expect100Continue = r0
            goto L_0x008d
        L_0x004a:
            if (r0 == 0) goto L_0x008d
            int r3 = r0.length
            if (r1 >= r3) goto L_0x008d
            org.eclipse.jetty.http.HttpHeaderValues r3 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            r4 = r0[r1]
            java.lang.String r4 = r4.trim()
            org.eclipse.jetty.io.BufferCache$CachedBuffer r3 = r3.get(r4)
            if (r3 != 0) goto L_0x0060
            r5._expect = r2
            goto L_0x0077
        L_0x0060:
            int r3 = r3.getOrdinal()
            switch(r3) {
                case 6: goto L_0x0071;
                case 7: goto L_0x006a;
                default: goto L_0x0067;
            }
        L_0x0067:
            r5._expect = r2
            goto L_0x0077
        L_0x006a:
            org.eclipse.jetty.http.Generator r3 = r5._generator
            boolean r3 = r3 instanceof org.eclipse.jetty.http.HttpGenerator
            r5._expect102Processing = r3
            goto L_0x0077
        L_0x0071:
            org.eclipse.jetty.http.Generator r3 = r5._generator
            boolean r3 = r3 instanceof org.eclipse.jetty.http.HttpGenerator
            r5._expect100Continue = r3
        L_0x0077:
            int r1 = r1 + 1
            goto L_0x004a
        L_0x007a:
            org.eclipse.jetty.http.HttpHeaderValues r0 = org.eclipse.jetty.http.HttpHeaderValues.CACHE
            org.eclipse.jetty.io.Buffer r7 = r0.lookup(r7)
            goto L_0x008d
        L_0x0081:
            org.eclipse.jetty.io.BufferCache r0 = org.eclipse.jetty.http.MimeTypes.CACHE
            org.eclipse.jetty.io.Buffer r7 = r0.lookup(r7)
            java.lang.String r0 = org.eclipse.jetty.http.MimeTypes.getCharsetFromContentType(r7)
            r5._charset = r0
        L_0x008d:
            org.eclipse.jetty.http.HttpFields r0 = r5._requestFields
            r0.add(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.AbstractHttpConnection.parsedHeader(org.eclipse.jetty.io.Buffer, org.eclipse.jetty.io.Buffer):void");
    }

    protected void headerComplete() throws IOException {
        this._requests++;
        this._generator.setVersion(this._version);
        switch (this._version) {
            case 10:
                this._generator.setHead(this._head);
                if (this._parser.isPersistent()) {
                    this._responseFields.add(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.KEEP_ALIVE_BUFFER);
                    this._generator.setPersistent(true);
                } else if (HttpMethods.CONNECT.equals(this._request.getMethod())) {
                    this._generator.setPersistent(true);
                    this._parser.setPersistent(true);
                }
                if (this._server.getSendDateHeader()) {
                    this._generator.setDate(this._request.getTimeStampBuffer());
                    break;
                }
                break;
            case 11:
                this._generator.setHead(this._head);
                if (!this._parser.isPersistent()) {
                    this._responseFields.add(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    this._generator.setPersistent(false);
                }
                if (this._server.getSendDateHeader()) {
                    this._generator.setDate(this._request.getTimeStampBuffer());
                }
                if (!this._host) {
                    LOG.debug("!host {}", this);
                    this._generator.setResponse(400, null);
                    this._responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    this._generator.completeHeader(this._responseFields, true);
                    this._generator.complete();
                    return;
                } else if (this._expect) {
                    LOG.debug("!expectation {}", this);
                    this._generator.setResponse(417, null);
                    this._responseFields.put(HttpHeaders.CONNECTION_BUFFER, HttpHeaderValues.CLOSE_BUFFER);
                    this._generator.completeHeader(this._responseFields, true);
                    this._generator.complete();
                    return;
                }
                break;
        }
        String str = this._charset;
        if (str != null) {
            this._request.setCharacterEncodingUnchecked(str);
        }
        if ((((HttpParser) this._parser).getContentLength() > 0 || ((HttpParser) this._parser).isChunking()) && !this._expect100Continue) {
            this._delayedHandling = true;
        } else {
            handleRequest();
        }
    }

    protected void content(Buffer buffer) throws IOException {
        if (this._delayedHandling) {
            this._delayedHandling = false;
            handleRequest();
        }
    }

    public void messageComplete(long j) throws IOException {
        if (this._delayedHandling) {
            this._delayedHandling = false;
            handleRequest();
        }
    }

    public void earlyEOF() {
        this._earlyEOF = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class RequestHandler extends HttpParser.EventHandler {
        private RequestHandler() {
            AbstractHttpConnection.this = r1;
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void startRequest(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            AbstractHttpConnection.this.startRequest(buffer, buffer2, buffer3);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void parsedHeader(Buffer buffer, Buffer buffer2) throws IOException {
            AbstractHttpConnection.this.parsedHeader(buffer, buffer2);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void headerComplete() throws IOException {
            AbstractHttpConnection.this.headerComplete();
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void content(Buffer buffer) throws IOException {
            AbstractHttpConnection.this.content(buffer);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void messageComplete(long j) throws IOException {
            AbstractHttpConnection.this.messageComplete(j);
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void startResponse(Buffer buffer, int i, Buffer buffer2) {
            if (AbstractHttpConnection.LOG.isDebugEnabled()) {
                Logger logger = AbstractHttpConnection.LOG;
                logger.debug("Bad request!: " + buffer + StringUtils.SPACE + i + StringUtils.SPACE + buffer2, new Object[0]);
            }
        }

        @Override // org.eclipse.jetty.http.HttpParser.EventHandler
        public void earlyEOF() {
            AbstractHttpConnection.this.earlyEOF();
        }
    }

    /* loaded from: classes5.dex */
    public class Output extends HttpOutput {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        Output() {
            super(r1);
            AbstractHttpConnection.this = r1;
        }

        @Override // org.eclipse.jetty.server.HttpOutput, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (!isClosed()) {
                if (AbstractHttpConnection.this.isIncluding() || this._generator.isCommitted()) {
                    AbstractHttpConnection.this.flushResponse();
                } else {
                    AbstractHttpConnection.this.commitResponse(true);
                }
                super.close();
            }
        }

        @Override // org.eclipse.jetty.server.HttpOutput, java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            if (!this._generator.isCommitted()) {
                AbstractHttpConnection.this.commitResponse(false);
            }
            super.flush();
        }

        @Override // org.eclipse.jetty.server.HttpOutput, javax.servlet.ServletOutputStream
        public void print(String str) throws IOException {
            if (!isClosed()) {
                AbstractHttpConnection.this.getPrintWriter(null).print(str);
                return;
            }
            throw new IOException("Closed");
        }

        public void sendResponse(Buffer buffer) throws IOException {
            ((HttpGenerator) this._generator).sendResponse(buffer);
        }

        public void sendContent(Object obj) throws IOException {
            if (isClosed()) {
                throw new IOException("Closed");
            } else if (!this._generator.isWritten()) {
                Resource resource = null;
                if (obj instanceof HttpContent) {
                    HttpContent httpContent = (HttpContent) obj;
                    Buffer contentType = httpContent.getContentType();
                    if (contentType != null && !AbstractHttpConnection.this._responseFields.containsKey(HttpHeaders.CONTENT_TYPE_BUFFER)) {
                        String setCharacterEncoding = AbstractHttpConnection.this._response.getSetCharacterEncoding();
                        if (setCharacterEncoding == null) {
                            AbstractHttpConnection.this._responseFields.add(HttpHeaders.CONTENT_TYPE_BUFFER, contentType);
                        } else if (contentType instanceof BufferCache.CachedBuffer) {
                            BufferCache.CachedBuffer associate = ((BufferCache.CachedBuffer) contentType).getAssociate(setCharacterEncoding);
                            if (associate != null) {
                                AbstractHttpConnection.this._responseFields.put(HttpHeaders.CONTENT_TYPE_BUFFER, associate);
                            } else {
                                HttpFields httpFields = AbstractHttpConnection.this._responseFields;
                                Buffer buffer = HttpHeaders.CONTENT_TYPE_BUFFER;
                                httpFields.put(buffer, contentType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(setCharacterEncoding, ";= "));
                            }
                        } else {
                            HttpFields httpFields2 = AbstractHttpConnection.this._responseFields;
                            Buffer buffer2 = HttpHeaders.CONTENT_TYPE_BUFFER;
                            httpFields2.put(buffer2, contentType + ";charset=" + QuotedStringTokenizer.quoteIfNeeded(setCharacterEncoding, ";= "));
                        }
                    }
                    if (httpContent.getContentLength() > 0) {
                        AbstractHttpConnection.this._responseFields.putLongField(HttpHeaders.CONTENT_LENGTH_BUFFER, httpContent.getContentLength());
                    }
                    Buffer lastModified = httpContent.getLastModified();
                    long lastModified2 = httpContent.getResource().lastModified();
                    if (lastModified != null) {
                        AbstractHttpConnection.this._responseFields.put(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified);
                    } else if (!(httpContent.getResource() == null || lastModified2 == -1)) {
                        AbstractHttpConnection.this._responseFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, lastModified2);
                    }
                    Buffer directBuffer = (AbstractHttpConnection.this._connector instanceof NIOConnector) && ((NIOConnector) AbstractHttpConnection.this._connector).getUseDirectBuffers() && !(AbstractHttpConnection.this._connector instanceof SslConnector) ? httpContent.getDirectBuffer() : httpContent.getIndirectBuffer();
                    obj = directBuffer == null ? httpContent.getInputStream() : directBuffer;
                } else if (obj instanceof Resource) {
                    resource = (Resource) obj;
                    AbstractHttpConnection.this._responseFields.putDateField(HttpHeaders.LAST_MODIFIED_BUFFER, resource.lastModified());
                    obj = resource.getInputStream();
                }
                if (obj instanceof Buffer) {
                    this._generator.addContent((Buffer) obj, true);
                    AbstractHttpConnection.this.commitResponse(true);
                } else if (obj instanceof InputStream) {
                    InputStream inputStream = (InputStream) obj;
                    try {
                        int readFrom = this._generator.getUncheckedBuffer().readFrom(inputStream, this._generator.prepareUncheckedAddContent());
                        while (readFrom >= 0) {
                            this._generator.completeUncheckedAddContent();
                            AbstractHttpConnection.this._out.flush();
                            readFrom = this._generator.getUncheckedBuffer().readFrom(inputStream, this._generator.prepareUncheckedAddContent());
                        }
                        this._generator.completeUncheckedAddContent();
                        AbstractHttpConnection.this._out.flush();
                        if (resource != null) {
                            resource.release();
                        } else {
                            inputStream.close();
                        }
                    } catch (Throwable th) {
                        if (resource != null) {
                            resource.release();
                        } else {
                            inputStream.close();
                        }
                        throw th;
                    }
                } else {
                    throw new IllegalArgumentException("unknown content type?");
                }
            } else {
                throw new IllegalStateException("!empty");
            }
        }
    }

    /* loaded from: classes5.dex */
    public class OutputWriter extends HttpWriter {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        OutputWriter() {
            super(r1._out);
            AbstractHttpConnection.this = r1;
        }
    }
}
