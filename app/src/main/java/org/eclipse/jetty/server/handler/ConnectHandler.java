package org.eclipse.jetty.server.handler;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ConnectedEndPoint;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.AsyncConnection;
import org.eclipse.jetty.io.nio.IndirectNIOBuffer;
import org.eclipse.jetty.io.nio.SelectChannelEndPoint;
import org.eclipse.jetty.io.nio.SelectorManager;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.HostMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.slf4j.Marker;

/* loaded from: classes5.dex */
public class ConnectHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger(ConnectHandler.class);
    private HostMap<String> _black;
    private volatile int _connectTimeout;
    private volatile boolean _privateThreadPool;
    private final SelectorManager _selectorManager;
    private volatile ThreadPool _threadPool;
    private HostMap<String> _white;
    private volatile int _writeTimeout;

    protected boolean handleAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String str) throws ServletException, IOException {
        return true;
    }

    protected void prepareContext(HttpServletRequest httpServletRequest, ConcurrentMap<String, Object> concurrentMap) {
    }

    public ConnectHandler() {
        this(null);
    }

    public ConnectHandler(String[] strArr, String[] strArr2) {
        this(null, strArr, strArr2);
    }

    public ConnectHandler(Handler handler) {
        this._selectorManager = new Manager();
        this._connectTimeout = 5000;
        this._writeTimeout = 30000;
        this._white = new HostMap<>();
        this._black = new HostMap<>();
        setHandler(handler);
    }

    public ConnectHandler(Handler handler, String[] strArr, String[] strArr2) {
        this._selectorManager = new Manager();
        this._connectTimeout = 5000;
        this._writeTimeout = 30000;
        this._white = new HostMap<>();
        this._black = new HostMap<>();
        setHandler(handler);
        set(strArr, this._white);
        set(strArr2, this._black);
    }

    public int getConnectTimeout() {
        return this._connectTimeout;
    }

    public void setConnectTimeout(int i) {
        this._connectTimeout = i;
    }

    public int getWriteTimeout() {
        return this._writeTimeout;
    }

    public void setWriteTimeout(int i) {
        this._writeTimeout = i;
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.server.Handler
    public void setServer(Server server) {
        super.setServer(server);
        server.getContainer().update(this, (Object) null, this._selectorManager, "selectManager");
        if (this._privateThreadPool) {
            server.getContainer().update((Object) this, (Object) null, (Object) Boolean.valueOf(this._privateThreadPool), "threadpool", true);
        } else {
            this._threadPool = server.getThreadPool();
        }
    }

    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        if (getServer() != null) {
            getServer().getContainer().update((Object) this, (Object) (this._privateThreadPool ? this._threadPool : null), (Object) threadPool, "threadpool", true);
        }
        this._privateThreadPool = threadPool != null;
        this._threadPool = threadPool;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        if (this._threadPool == null) {
            this._threadPool = getServer().getThreadPool();
            this._privateThreadPool = false;
        }
        if ((this._threadPool instanceof LifeCycle) && !((LifeCycle) this._threadPool).isRunning()) {
            ((LifeCycle) this._threadPool).start();
        }
        this._selectorManager.start();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        this._selectorManager.stop();
        ThreadPool threadPool = this._threadPool;
        if (this._privateThreadPool && this._threadPool != null && (threadPool instanceof LifeCycle)) {
            ((LifeCycle) threadPool).stop();
        }
        super.doStop();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (HttpMethods.CONNECT.equalsIgnoreCase(httpServletRequest.getMethod())) {
            LOG.debug("CONNECT request for {}", httpServletRequest.getRequestURI());
            try {
                handleConnect(request, httpServletRequest, httpServletResponse, httpServletRequest.getRequestURI());
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn("ConnectHandler " + request.getUri() + StringUtils.SPACE + e, new Object[0]);
                LOG.debug(e);
            }
        } else {
            super.handle(str, request, httpServletRequest, httpServletResponse);
        }
    }

    protected void handleConnect(Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String str) throws ServletException, IOException {
        if (handleAuthentication(httpServletRequest, httpServletResponse, str)) {
            int i = 80;
            int indexOf = str.indexOf(58);
            int i2 = 0;
            if (indexOf > 0) {
                String substring = str.substring(0, indexOf);
                i = Integer.parseInt(str.substring(indexOf + 1));
                str = substring;
            }
            if (!validateDestination(str)) {
                Logger logger = LOG;
                logger.info("ProxyHandler: Forbidden destination " + str, new Object[0]);
                httpServletResponse.setStatus(403);
                request.setHandled(true);
                return;
            }
            try {
                SocketChannel connectToServer = connectToServer(httpServletRequest, str, i);
                AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
                Buffer headerBuffer = ((HttpParser) currentConnection.getParser()).getHeaderBuffer();
                Buffer bodyBuffer = ((HttpParser) currentConnection.getParser()).getBodyBuffer();
                int length = headerBuffer == null ? 0 : headerBuffer.length();
                if (bodyBuffer != null) {
                    i2 = bodyBuffer.length();
                }
                int i3 = length + i2;
                IndirectNIOBuffer indirectNIOBuffer = null;
                if (i3 > 0) {
                    indirectNIOBuffer = new IndirectNIOBuffer(i3);
                    if (headerBuffer != null) {
                        indirectNIOBuffer.put(headerBuffer);
                        headerBuffer.clear();
                    }
                    if (bodyBuffer != null) {
                        indirectNIOBuffer.put(bodyBuffer);
                        bodyBuffer.clear();
                    }
                }
                ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                prepareContext(httpServletRequest, concurrentHashMap);
                ClientToProxyConnection prepareConnections = prepareConnections(concurrentHashMap, connectToServer, indirectNIOBuffer);
                httpServletResponse.setStatus(200);
                request.getConnection().getGenerator().setPersistent(true);
                httpServletResponse.getOutputStream().close();
                upgradeConnection(httpServletRequest, httpServletResponse, prepareConnections);
            } catch (SocketException e) {
                Logger logger2 = LOG;
                logger2.info("ConnectHandler: SocketException " + e.getMessage(), new Object[0]);
                httpServletResponse.setStatus(500);
                request.setHandled(true);
            } catch (SocketTimeoutException e2) {
                Logger logger3 = LOG;
                logger3.info("ConnectHandler: SocketTimeoutException" + e2.getMessage(), new Object[0]);
                httpServletResponse.setStatus(504);
                request.setHandled(true);
            } catch (IOException e3) {
                Logger logger4 = LOG;
                logger4.info("ConnectHandler: IOException" + e3.getMessage(), new Object[0]);
                httpServletResponse.setStatus(500);
                request.setHandled(true);
            }
        }
    }

    private ClientToProxyConnection prepareConnections(ConcurrentMap<String, Object> concurrentMap, SocketChannel socketChannel, Buffer buffer) {
        AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
        ProxyToServerConnection newProxyToServerConnection = newProxyToServerConnection(concurrentMap, buffer);
        ClientToProxyConnection newClientToProxyConnection = newClientToProxyConnection(concurrentMap, socketChannel, currentConnection.getEndPoint(), currentConnection.getTimeStamp());
        newClientToProxyConnection.setConnection(newProxyToServerConnection);
        newProxyToServerConnection.setConnection(newClientToProxyConnection);
        return newClientToProxyConnection;
    }

    protected ClientToProxyConnection newClientToProxyConnection(ConcurrentMap<String, Object> concurrentMap, SocketChannel socketChannel, EndPoint endPoint, long j) {
        return new ClientToProxyConnection(concurrentMap, socketChannel, endPoint, j);
    }

    protected ProxyToServerConnection newProxyToServerConnection(ConcurrentMap<String, Object> concurrentMap, Buffer buffer) {
        return new ProxyToServerConnection(concurrentMap, buffer);
    }

    private SocketChannel connectToServer(HttpServletRequest httpServletRequest, String str, int i) throws IOException {
        SocketChannel connect = connect(httpServletRequest, str, i);
        connect.configureBlocking(false);
        return connect;
    }

    protected SocketChannel connect(HttpServletRequest httpServletRequest, String str, int i) throws IOException {
        SocketChannel open = SocketChannel.open();
        if (open != null) {
            try {
                LOG.debug("Establishing connection to {}:{}", str, Integer.valueOf(i));
                open.socket().setTcpNoDelay(true);
                open.socket().connect(new InetSocketAddress(str, i), getConnectTimeout());
                LOG.debug("Established connection to {}:{}", str, Integer.valueOf(i));
                return open;
            } catch (IOException e) {
                Logger logger = LOG;
                logger.debug("Failed to establish connection to " + str + Constants.COLON_SEPARATOR + i, e);
                try {
                    open.close();
                } catch (IOException e2) {
                    LOG.ignore(e2);
                }
                throw e;
            }
        } else {
            throw new IOException("unable to connect to " + str + Constants.COLON_SEPARATOR + i);
        }
    }

    private void upgradeConnection(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Connection connection) throws IOException {
        httpServletRequest.setAttribute("org.eclipse.jetty.io.Connection", connection);
        httpServletResponse.setStatus(101);
        LOG.debug("Upgraded connection to {}", connection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void register(SocketChannel socketChannel, ProxyToServerConnection proxyToServerConnection) throws IOException {
        this._selectorManager.register(socketChannel, proxyToServerConnection);
        proxyToServerConnection.waitReady(this._connectTimeout);
    }

    protected int read(EndPoint endPoint, Buffer buffer, ConcurrentMap<String, Object> concurrentMap) throws IOException {
        return endPoint.fill(buffer);
    }

    protected int write(EndPoint endPoint, Buffer buffer, ConcurrentMap<String, Object> concurrentMap) throws IOException {
        if (buffer == null) {
            return 0;
        }
        int length = buffer.length();
        StringBuilder sb = LOG.isDebugEnabled() ? new StringBuilder() : null;
        int flush = endPoint.flush(buffer);
        if (sb != null) {
            sb.append(flush);
        }
        while (buffer.length() > 0 && !endPoint.isOutputShutdown()) {
            if (endPoint.isBlocking() || endPoint.blockWritable(getWriteTimeout())) {
                int flush2 = endPoint.flush(buffer);
                if (sb != null) {
                    sb.append(Marker.ANY_NON_NULL_MARKER);
                    sb.append(flush2);
                }
            } else {
                throw new IOException("Write timeout");
            }
        }
        LOG.debug("Written {}/{} bytes {}", sb, Integer.valueOf(length), endPoint);
        buffer.compact();
        return length;
    }

    /* loaded from: classes5.dex */
    private class Manager extends SelectorManager {
        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected void endPointClosed(SelectChannelEndPoint selectChannelEndPoint) {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.jetty.io.nio.SelectorManager
        public void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection) {
        }

        private Manager() {
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey) throws IOException {
            SelectChannelEndPoint selectChannelEndPoint = new SelectChannelEndPoint(socketChannel, selectSet, selectionKey, socketChannel.socket().getSoTimeout());
            selectChannelEndPoint.setConnection(selectSet.getManager().newConnection(socketChannel, selectChannelEndPoint, selectionKey.attachment()));
            selectChannelEndPoint.setMaxIdleTime(ConnectHandler.this._writeTimeout);
            return selectChannelEndPoint;
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj) {
            ProxyToServerConnection proxyToServerConnection = (ProxyToServerConnection) obj;
            proxyToServerConnection.setTimeStamp(System.currentTimeMillis());
            proxyToServerConnection.setEndPoint(asyncEndPoint);
            return proxyToServerConnection;
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected void endPointOpened(SelectChannelEndPoint selectChannelEndPoint) {
            ((ProxyToServerConnection) selectChannelEndPoint.getSelectionKey().attachment()).ready();
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        public boolean dispatch(Runnable runnable) {
            return ConnectHandler.this._threadPool.dispatch(runnable);
        }
    }

    /* loaded from: classes5.dex */
    public class ProxyToServerConnection implements AsyncConnection {
        private final ConcurrentMap<String, Object> _context;
        private volatile Buffer _data;
        private volatile AsyncEndPoint _endPoint;
        private volatile long _timestamp;
        private volatile ClientToProxyConnection _toClient;
        private final CountDownLatch _ready = new CountDownLatch(1);
        private final Buffer _buffer = new IndirectNIOBuffer(4096);

        @Override // org.eclipse.jetty.io.Connection
        public boolean isIdle() {
            return false;
        }

        @Override // org.eclipse.jetty.io.Connection
        public boolean isSuspended() {
            return false;
        }

        @Override // org.eclipse.jetty.io.Connection
        public void onClose() {
        }

        @Override // org.eclipse.jetty.io.nio.AsyncConnection
        public void onInputShutdown() throws IOException {
        }

        public ProxyToServerConnection(ConcurrentMap<String, Object> concurrentMap, Buffer buffer) {
            this._context = concurrentMap;
            this._data = buffer;
        }

        public String toString() {
            return "ProxyToServer(:" + this._endPoint.getLocalPort() + "<=>:" + this._endPoint.getRemotePort() + ")";
        }

        @Override // org.eclipse.jetty.io.Connection
        public Connection handle() throws IOException {
            try {
                ConnectHandler.LOG.debug("{}: begin reading from server", this);
                try {
                    writeData();
                    while (true) {
                        int read = ConnectHandler.this.read(this._endPoint, this._buffer, this._context);
                        if (read == -1) {
                            ConnectHandler.LOG.debug("{}: server closed connection {}", this, this._endPoint);
                            if (!this._endPoint.isOutputShutdown() && this._endPoint.isOpen()) {
                                this._toClient.shutdownOutput();
                            }
                            closeClient();
                        } else if (read == 0) {
                            break;
                        } else {
                            ConnectHandler.LOG.debug("{}: read from server {} bytes {}", this, Integer.valueOf(read), this._endPoint);
                            ConnectHandler.LOG.debug("{}: written to {} {} bytes", this, this._toClient, Integer.valueOf(ConnectHandler.this.write(this._toClient._endPoint, this._buffer, this._context)));
                        }
                    }
                    ConnectHandler.LOG.debug("{}: end reading from server", this);
                    return this;
                } catch (RuntimeException e) {
                    Logger logger = ConnectHandler.LOG;
                    logger.warn(this + ": unexpected exception", e);
                    close();
                    throw e;
                } catch (ClosedChannelException e2) {
                    ConnectHandler.LOG.debug(e2);
                    throw e2;
                } catch (IOException e3) {
                    Logger logger2 = ConnectHandler.LOG;
                    logger2.warn(this + ": unexpected exception", e3);
                    close();
                    throw e3;
                }
            } catch (Throwable th) {
                ConnectHandler.LOG.debug("{}: end reading from server", this);
                throw th;
            }
        }

        private void writeData() throws IOException {
            synchronized (this) {
                if (this._data != null) {
                    ConnectHandler.LOG.debug("{}: written to server {} bytes", this, Integer.valueOf(ConnectHandler.this.write(this._endPoint, this._data, this._context)));
                    this._data = null;
                }
            }
        }

        public void setConnection(ClientToProxyConnection clientToProxyConnection) {
            this._toClient = clientToProxyConnection;
        }

        @Override // org.eclipse.jetty.io.Connection
        public long getTimeStamp() {
            return this._timestamp;
        }

        public void setTimeStamp(long j) {
            this._timestamp = j;
        }

        public void setEndPoint(AsyncEndPoint asyncEndPoint) {
            this._endPoint = asyncEndPoint;
        }

        public void ready() {
            this._ready.countDown();
        }

        public void waitReady(long j) throws IOException {
            try {
                this._ready.await(j, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException() { // from class: org.eclipse.jetty.server.handler.ConnectHandler.ProxyToServerConnection.1
                    {
                        initCause(e);
                    }
                };
            }
        }

        public void closeClient() throws IOException {
            this._toClient.closeClient();
        }

        public void closeServer() throws IOException {
            this._endPoint.close();
        }

        public void close() {
            try {
                closeClient();
            } catch (IOException e) {
                Logger logger = ConnectHandler.LOG;
                logger.debug(this + ": unexpected exception closing the client", e);
            }
            try {
                closeServer();
            } catch (IOException e2) {
                Logger logger2 = ConnectHandler.LOG;
                logger2.debug(this + ": unexpected exception closing the server", e2);
            }
        }

        public void shutdownOutput() throws IOException {
            writeData();
            this._endPoint.shutdownOutput();
        }

        @Override // org.eclipse.jetty.io.Connection
        public void onIdleExpired(long j) {
            try {
                shutdownOutput();
            } catch (Exception e) {
                ConnectHandler.LOG.debug(e);
                close();
            }
        }
    }

    /* loaded from: classes5.dex */
    public class ClientToProxyConnection implements AsyncConnection {
        private final SocketChannel _channel;
        private final ConcurrentMap<String, Object> _context;
        private final EndPoint _endPoint;
        private final long _timestamp;
        private volatile ProxyToServerConnection _toServer;
        private final Buffer _buffer = new IndirectNIOBuffer(4096);
        private boolean _firstTime = true;

        @Override // org.eclipse.jetty.io.Connection
        public boolean isIdle() {
            return false;
        }

        @Override // org.eclipse.jetty.io.Connection
        public boolean isSuspended() {
            return false;
        }

        @Override // org.eclipse.jetty.io.Connection
        public void onClose() {
        }

        @Override // org.eclipse.jetty.io.nio.AsyncConnection
        public void onInputShutdown() throws IOException {
        }

        public ClientToProxyConnection(ConcurrentMap<String, Object> concurrentMap, SocketChannel socketChannel, EndPoint endPoint, long j) {
            this._context = concurrentMap;
            this._channel = socketChannel;
            this._endPoint = endPoint;
            this._timestamp = j;
        }

        public String toString() {
            return "ClientToProxy(:" + this._endPoint.getLocalPort() + "<=>:" + this._endPoint.getRemotePort() + ")";
        }

        @Override // org.eclipse.jetty.io.Connection
        public Connection handle() throws IOException {
            try {
                ConnectHandler.LOG.debug("{}: begin reading from client", this);
                try {
                    try {
                        try {
                            if (this._firstTime) {
                                this._firstTime = false;
                                ConnectHandler.this.register(this._channel, this._toServer);
                                ConnectHandler.LOG.debug("{}: registered channel {} with connection {}", this, this._channel, this._toServer);
                            }
                            while (true) {
                                int read = ConnectHandler.this.read(this._endPoint, this._buffer, this._context);
                                if (read == -1) {
                                    ConnectHandler.LOG.debug("{}: client closed connection {}", this, this._endPoint);
                                    if (!this._endPoint.isOutputShutdown() && this._endPoint.isOpen()) {
                                        this._toServer.shutdownOutput();
                                    }
                                    closeServer();
                                } else if (read == 0) {
                                    break;
                                } else {
                                    ConnectHandler.LOG.debug("{}: read from client {} bytes {}", this, Integer.valueOf(read), this._endPoint);
                                    ConnectHandler.LOG.debug("{}: written to {} {} bytes", this, this._toServer, Integer.valueOf(ConnectHandler.this.write(this._toServer._endPoint, this._buffer, this._context)));
                                }
                            }
                            ConnectHandler.LOG.debug("{}: end reading from client", this);
                            return this;
                        } catch (ClosedChannelException e) {
                            ConnectHandler.LOG.debug(e);
                            closeServer();
                            throw e;
                        }
                    } catch (RuntimeException e2) {
                        Logger logger = ConnectHandler.LOG;
                        logger.warn(this + ": unexpected exception", e2);
                        close();
                        throw e2;
                    }
                } catch (IOException e3) {
                    Logger logger2 = ConnectHandler.LOG;
                    logger2.warn(this + ": unexpected exception", e3);
                    close();
                    throw e3;
                }
            } catch (Throwable th) {
                ConnectHandler.LOG.debug("{}: end reading from client", this);
                throw th;
            }
        }

        @Override // org.eclipse.jetty.io.Connection
        public long getTimeStamp() {
            return this._timestamp;
        }

        public void setConnection(ProxyToServerConnection proxyToServerConnection) {
            this._toServer = proxyToServerConnection;
        }

        public void closeClient() throws IOException {
            this._endPoint.close();
        }

        public void closeServer() throws IOException {
            this._toServer.closeServer();
        }

        public void close() {
            try {
                closeClient();
            } catch (IOException e) {
                Logger logger = ConnectHandler.LOG;
                logger.debug(this + ": unexpected exception closing the client", e);
            }
            try {
                closeServer();
            } catch (IOException e2) {
                Logger logger2 = ConnectHandler.LOG;
                logger2.debug(this + ": unexpected exception closing the server", e2);
            }
        }

        public void shutdownOutput() throws IOException {
            this._endPoint.shutdownOutput();
        }

        @Override // org.eclipse.jetty.io.Connection
        public void onIdleExpired(long j) {
            try {
                shutdownOutput();
            } catch (Exception e) {
                ConnectHandler.LOG.debug(e);
                close();
            }
        }
    }

    public void addWhite(String str) {
        add(str, this._white);
    }

    public void addBlack(String str) {
        add(str, this._black);
    }

    public void setWhite(String[] strArr) {
        set(strArr, this._white);
    }

    public void setBlack(String[] strArr) {
        set(strArr, this._black);
    }

    protected void set(String[] strArr, HostMap<String> hostMap) {
        hostMap.clear();
        if (strArr != null && strArr.length > 0) {
            for (String str : strArr) {
                add(str, hostMap);
            }
        }
    }

    private void add(String str, HostMap<String> hostMap) {
        if (str != null && str.length() > 0) {
            String trim = str.trim();
            if (hostMap.get(trim) == null) {
                hostMap.put(trim, trim);
            }
        }
    }

    public boolean validateDestination(String str) {
        if (this._white.size() <= 0 || this._white.getLazyMatches(str) != null) {
            return this._black.size() <= 0 || this._black.getLazyMatches(str) == null;
        }
        return false;
    }

    @Override // org.eclipse.jetty.server.handler.AbstractHandlerContainer, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        if (this._privateThreadPool) {
            dump(appendable, str, Arrays.asList(this._threadPool, this._selectorManager), TypeUtil.asList(getHandlers()), getBeans());
        } else {
            dump(appendable, str, Arrays.asList(this._selectorManager), TypeUtil.asList(getHandlers()), getBeans());
        }
    }
}
