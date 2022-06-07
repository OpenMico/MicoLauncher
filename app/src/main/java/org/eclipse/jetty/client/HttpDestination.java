package org.eclipse.jetty.client;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.SelectConnector;
import org.eclipse.jetty.client.security.Authentication;
import org.eclipse.jetty.client.security.SecurityListener;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpMethods;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class HttpDestination implements Dumpable {
    private static final Logger LOG = Log.getLogger(HttpDestination.class);
    private final Address _address;
    private PathMap _authorizations;
    private final HttpClient _client;
    private List<HttpCookie> _cookies;
    private final ByteArrayBuffer _hostHeader;
    private volatile int _maxConnections;
    private volatile int _maxQueueSize;
    private volatile Address _proxy;
    private Authentication _proxyAuthentication;
    private final boolean _ssl;
    private final List<HttpExchange> _queue = new LinkedList();
    private final List<AbstractHttpConnection> _connections = new LinkedList();
    private final BlockingQueue<Object> _newQueue = new ArrayBlockingQueue(10, true);
    private final List<AbstractHttpConnection> _idle = new ArrayList();
    private int _pendingConnections = 0;
    private int _newConnection = 0;

    public HttpDestination(HttpClient httpClient, Address address, boolean z) {
        this._client = httpClient;
        this._address = address;
        this._ssl = z;
        this._maxConnections = this._client.getMaxConnectionsPerAddress();
        this._maxQueueSize = this._client.getMaxQueueSizePerAddress();
        String host = address.getHost();
        if (address.getPort() != (this._ssl ? 443 : 80)) {
            host = host + Constants.COLON_SEPARATOR + address.getPort();
        }
        this._hostHeader = new ByteArrayBuffer(host);
    }

    public HttpClient getHttpClient() {
        return this._client;
    }

    public Address getAddress() {
        return this._address;
    }

    public boolean isSecure() {
        return this._ssl;
    }

    public Buffer getHostHeader() {
        return this._hostHeader;
    }

    public int getMaxConnections() {
        return this._maxConnections;
    }

    public void setMaxConnections(int i) {
        this._maxConnections = i;
    }

    public int getMaxQueueSize() {
        return this._maxQueueSize;
    }

    public void setMaxQueueSize(int i) {
        this._maxQueueSize = i;
    }

    public int getConnections() {
        int size;
        synchronized (this) {
            size = this._connections.size();
        }
        return size;
    }

    public int getIdleConnections() {
        int size;
        synchronized (this) {
            size = this._idle.size();
        }
        return size;
    }

    public void addAuthorization(String str, Authentication authentication) {
        synchronized (this) {
            if (this._authorizations == null) {
                this._authorizations = new PathMap();
            }
            this._authorizations.put(str, authentication);
        }
    }

    public void addCookie(HttpCookie httpCookie) {
        synchronized (this) {
            if (this._cookies == null) {
                this._cookies = new ArrayList();
            }
            this._cookies.add(httpCookie);
        }
    }

    private AbstractHttpConnection getConnection(long j) throws IOException {
        AbstractHttpConnection abstractHttpConnection = null;
        while (abstractHttpConnection == null) {
            abstractHttpConnection = getIdleConnection();
            if (abstractHttpConnection != null || j <= 0) {
                break;
            }
            boolean z = false;
            synchronized (this) {
                if (this._connections.size() + this._pendingConnections < this._maxConnections) {
                    this._newConnection++;
                    z = true;
                }
            }
            if (z) {
                startNewConnection();
                try {
                    Object take = this._newQueue.take();
                    if (!(take instanceof AbstractHttpConnection)) {
                        throw ((IOException) take);
                        break;
                    }
                    abstractHttpConnection = (AbstractHttpConnection) take;
                } catch (InterruptedException e) {
                    LOG.ignore(e);
                }
            } else {
                try {
                    Thread.currentThread();
                    Thread.sleep(200L);
                    j -= 200;
                } catch (InterruptedException e2) {
                    LOG.ignore(e2);
                }
            }
        }
        return abstractHttpConnection;
    }

    public AbstractHttpConnection reserveConnection(long j) throws IOException {
        AbstractHttpConnection connection = getConnection(j);
        if (connection != null) {
            connection.setReserved(true);
        }
        return connection;
    }

    public AbstractHttpConnection getIdleConnection() throws IOException {
        AbstractHttpConnection abstractHttpConnection = null;
        do {
            synchronized (this) {
                if (abstractHttpConnection != null) {
                    this._connections.remove(abstractHttpConnection);
                    abstractHttpConnection.close();
                    abstractHttpConnection = null;
                }
                if (this._idle.size() > 0) {
                    abstractHttpConnection = this._idle.remove(this._idle.size() - 1);
                }
            }
            if (abstractHttpConnection == null) {
                return null;
            }
        } while (!abstractHttpConnection.cancelIdleTimeout());
        return abstractHttpConnection;
    }

    protected void startNewConnection() {
        try {
            synchronized (this) {
                this._pendingConnections++;
            }
            HttpClient.Connector connector = this._client._connector;
            if (connector != null) {
                connector.startConnection(this);
            }
        } catch (Exception e) {
            LOG.debug(e);
            onConnectionFailed(e);
        }
    }

    public void onConnectionFailed(Throwable th) {
        boolean z;
        Throwable th2;
        synchronized (this) {
            this._pendingConnections--;
            z = false;
            th2 = null;
            if (this._newConnection > 0) {
                this._newConnection--;
                th2 = th;
            } else if (this._queue.size() > 0) {
                HttpExchange remove = this._queue.remove(0);
                if (remove.setStatus(9)) {
                    remove.getEventListener().onConnectionFailed(th);
                }
                if (!this._queue.isEmpty() && this._client.isStarted()) {
                    z = true;
                }
            }
        }
        if (z) {
            startNewConnection();
        }
        if (th2 != null) {
            try {
                this._newQueue.put(th2);
            } catch (InterruptedException e) {
                LOG.ignore(e);
            }
        }
    }

    public void onException(Throwable th) {
        synchronized (this) {
            this._pendingConnections--;
            if (this._queue.size() > 0) {
                HttpExchange remove = this._queue.remove(0);
                if (remove.setStatus(9)) {
                    remove.getEventListener().onException(th);
                }
            }
        }
    }

    public void onNewConnection(AbstractHttpConnection abstractHttpConnection) throws IOException {
        synchronized (this) {
            this._pendingConnections--;
            this._connections.add(abstractHttpConnection);
            if (this._newConnection > 0) {
                this._newConnection--;
            } else {
                if (this._queue.size() == 0) {
                    abstractHttpConnection.setIdleTimeout();
                    this._idle.add(abstractHttpConnection);
                } else {
                    EndPoint endPoint = abstractHttpConnection.getEndPoint();
                    if (!isProxied() || !(endPoint instanceof SelectConnector.UpgradableEndPoint)) {
                        send(abstractHttpConnection, this._queue.remove(0));
                    } else {
                        ConnectExchange connectExchange = new ConnectExchange(getAddress(), (SelectConnector.UpgradableEndPoint) endPoint, this._queue.get(0));
                        connectExchange.setAddress(getProxy());
                        send(abstractHttpConnection, connectExchange);
                    }
                }
                abstractHttpConnection = null;
            }
        }
        if (abstractHttpConnection != null) {
            try {
                this._newQueue.put(abstractHttpConnection);
            } catch (InterruptedException e) {
                LOG.ignore(e);
            }
        }
    }

    public void returnConnection(AbstractHttpConnection abstractHttpConnection, boolean z) throws IOException {
        boolean z2;
        if (abstractHttpConnection.isReserved()) {
            abstractHttpConnection.setReserved(false);
        }
        if (z) {
            try {
                abstractHttpConnection.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
        if (this._client.isStarted()) {
            if (z || !abstractHttpConnection.getEndPoint().isOpen()) {
                synchronized (this) {
                    this._connections.remove(abstractHttpConnection);
                    z2 = !this._queue.isEmpty();
                }
                if (z2) {
                    startNewConnection();
                    return;
                }
                return;
            }
            synchronized (this) {
                if (this._queue.size() == 0) {
                    abstractHttpConnection.setIdleTimeout();
                    this._idle.add(abstractHttpConnection);
                } else {
                    send(abstractHttpConnection, this._queue.remove(0));
                }
                notifyAll();
            }
        }
    }

    public void returnIdleConnection(AbstractHttpConnection abstractHttpConnection) {
        abstractHttpConnection.onIdleExpired((abstractHttpConnection == null || abstractHttpConnection.getEndPoint() == null) ? -1L : abstractHttpConnection.getEndPoint().getMaxIdleTime());
        boolean z = false;
        synchronized (this) {
            this._idle.remove(abstractHttpConnection);
            this._connections.remove(abstractHttpConnection);
            if (!this._queue.isEmpty() && this._client.isStarted()) {
                z = true;
            }
        }
        if (z) {
            startNewConnection();
        }
    }

    public void send(HttpExchange httpExchange) throws IOException {
        LinkedList<String> registeredListeners = this._client.getRegisteredListeners();
        if (registeredListeners != null) {
            for (int size = registeredListeners.size(); size > 0; size--) {
                String str = registeredListeners.get(size - 1);
                try {
                    httpExchange.setEventListener((HttpEventListener) Class.forName(str).getDeclaredConstructor(HttpDestination.class, HttpExchange.class).newInstance(this, httpExchange));
                } catch (Exception e) {
                    throw new IOException("Unable to instantiate registered listener for destination: " + str) { // from class: org.eclipse.jetty.client.HttpDestination.1
                        {
                            HttpDestination.this = this;
                            initCause(e);
                        }
                    };
                }
            }
        }
        if (this._client.hasRealms()) {
            httpExchange.setEventListener(new SecurityListener(this, httpExchange));
        }
        doSend(httpExchange);
    }

    public void resend(HttpExchange httpExchange) throws IOException {
        httpExchange.getEventListener().onRetry();
        httpExchange.reset();
        doSend(httpExchange);
    }

    protected void doSend(HttpExchange httpExchange) throws IOException {
        Authentication authentication;
        List<HttpCookie> list = this._cookies;
        if (list != null) {
            StringBuilder sb = null;
            for (HttpCookie httpCookie : list) {
                if (sb == null) {
                    sb = new StringBuilder();
                } else {
                    sb.append("; ");
                }
                sb.append(httpCookie.getName());
                sb.append("=");
                sb.append(httpCookie.getValue());
            }
            if (sb != null) {
                httpExchange.addRequestHeader("Cookie", sb.toString());
            }
        }
        PathMap pathMap = this._authorizations;
        if (!(pathMap == null || (authentication = (Authentication) pathMap.match(httpExchange.getRequestURI())) == null)) {
            authentication.setCredentials(httpExchange);
        }
        httpExchange.scheduleTimeout(this);
        AbstractHttpConnection idleConnection = getIdleConnection();
        if (idleConnection != null) {
            send(idleConnection, httpExchange);
            return;
        }
        boolean z = false;
        synchronized (this) {
            if (this._queue.size() != this._maxQueueSize) {
                this._queue.add(httpExchange);
                if (this._connections.size() + this._pendingConnections < this._maxConnections) {
                    z = true;
                }
            } else {
                throw new RejectedExecutionException("Queue full for address " + this._address);
            }
        }
        if (z) {
            startNewConnection();
        }
    }

    public void exchangeExpired(HttpExchange httpExchange) {
        synchronized (this) {
            this._queue.remove(httpExchange);
        }
    }

    protected void send(AbstractHttpConnection abstractHttpConnection, HttpExchange httpExchange) throws IOException {
        synchronized (this) {
            if (!abstractHttpConnection.send(httpExchange)) {
                if (httpExchange.getStatus() <= 1) {
                    this._queue.add(0, httpExchange);
                }
                returnIdleConnection(abstractHttpConnection);
            }
        }
    }

    public synchronized String toString() {
        return String.format("HttpDestination@%x//%s:%d(%d/%d,%d,%d/%d)%n", Integer.valueOf(hashCode()), this._address.getHost(), Integer.valueOf(this._address.getPort()), Integer.valueOf(this._connections.size()), Integer.valueOf(this._maxConnections), Integer.valueOf(this._idle.size()), Integer.valueOf(this._queue.size()), Integer.valueOf(this._maxQueueSize));
    }

    public synchronized String toDetailString() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(toString());
        sb.append('\n');
        synchronized (this) {
            for (AbstractHttpConnection abstractHttpConnection : this._connections) {
                sb.append(abstractHttpConnection.toDetailString());
                if (this._idle.contains(abstractHttpConnection)) {
                    sb.append(" IDLE");
                }
                sb.append('\n');
            }
        }
        return sb.toString();
        sb.append("--");
        sb.append('\n');
        return sb.toString();
    }

    public void setProxy(Address address) {
        this._proxy = address;
    }

    public Address getProxy() {
        return this._proxy;
    }

    public Authentication getProxyAuthentication() {
        return this._proxyAuthentication;
    }

    public void setProxyAuthentication(Authentication authentication) {
        this._proxyAuthentication = authentication;
    }

    public boolean isProxied() {
        return this._proxy != null;
    }

    public void close() throws IOException {
        synchronized (this) {
            for (AbstractHttpConnection abstractHttpConnection : this._connections) {
                abstractHttpConnection.close();
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
            appendable.append(String.valueOf(this) + "idle=" + this._idle.size() + " pending=" + this._pendingConnections).append("\n");
            AggregateLifeCycle.dump(appendable, str, this._connections);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class ConnectExchange extends ContentExchange {
        private final HttpExchange exchange;
        private final SelectConnector.UpgradableEndPoint proxyEndPoint;

        public ConnectExchange(Address address, SelectConnector.UpgradableEndPoint upgradableEndPoint, HttpExchange httpExchange) {
            HttpDestination.this = r1;
            this.proxyEndPoint = upgradableEndPoint;
            this.exchange = httpExchange;
            setMethod(HttpMethods.CONNECT);
            setVersion(httpExchange.getVersion());
            String address2 = address.toString();
            setRequestURI(address2);
            addRequestHeader("Host", address2);
            addRequestHeader(HttpHeaders.PROXY_CONNECTION, "keep-alive");
            addRequestHeader("User-Agent", "Jetty-Client");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.eclipse.jetty.client.HttpExchange
        public void onResponseComplete() throws IOException {
            int responseStatus = getResponseStatus();
            if (responseStatus == 200) {
                this.proxyEndPoint.upgrade();
            } else if (responseStatus == 504) {
                onExpire();
            } else {
                onException(new ProtocolException("Proxy: " + this.proxyEndPoint.getRemoteAddr() + Constants.COLON_SEPARATOR + this.proxyEndPoint.getRemotePort() + " didn't return http return code 200, but " + responseStatus + " while trying to request: " + this.exchange.getAddress().toString()));
            }
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        protected void onConnectionFailed(Throwable th) {
            HttpDestination.this.onConnectionFailed(th);
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        protected void onException(Throwable th) {
            HttpDestination.this._queue.remove(this.exchange);
            if (this.exchange.setStatus(9)) {
                this.exchange.getEventListener().onException(th);
            }
        }

        @Override // org.eclipse.jetty.client.HttpExchange
        protected void onExpire() {
            HttpDestination.this._queue.remove(this.exchange);
            if (this.exchange.setStatus(8)) {
                this.exchange.getEventListener().onExpire();
            }
        }
    }
}
