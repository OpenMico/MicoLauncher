package org.eclipse.jetty.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import org.eclipse.jetty.io.AsyncEndPoint;
import org.eclipse.jetty.io.ConnectedEndPoint;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.nio.AsyncConnection;
import org.eclipse.jetty.io.nio.SelectChannelEndPoint;
import org.eclipse.jetty.io.nio.SelectorManager;
import org.eclipse.jetty.server.AsyncHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.thread.ThreadPool;

/* loaded from: classes5.dex */
public class SelectChannelConnector extends AbstractNIOConnector {
    protected ServerSocketChannel _acceptChannel;
    private int _lowResourcesConnections;
    private int _lowResourcesMaxIdleTime;
    private int _localPort = -1;
    private final SelectorManager _manager = new ConnectorSelectorManager();

    public SelectChannelConnector() {
        this._manager.setMaxIdleTime(getMaxIdleTime());
        addBean(this._manager, true);
        setAcceptors(Math.max(1, (Runtime.getRuntime().availableProcessors() + 3) / 4));
    }

    @Override // org.eclipse.jetty.server.AbstractConnector
    public void setThreadPool(ThreadPool threadPool) {
        super.setThreadPool(threadPool);
        removeBean(this._manager);
        addBean(this._manager, true);
    }

    @Override // org.eclipse.jetty.server.AbstractConnector
    public void accept(int i) throws IOException {
        ServerSocketChannel serverSocketChannel;
        synchronized (this) {
            serverSocketChannel = this._acceptChannel;
        }
        if (serverSocketChannel != null && serverSocketChannel.isOpen() && this._manager.isStarted()) {
            SocketChannel accept = serverSocketChannel.accept();
            accept.configureBlocking(false);
            configure(accept.socket());
            this._manager.register(accept);
        }
    }

    @Override // org.eclipse.jetty.server.Connector
    public void close() throws IOException {
        synchronized (this) {
            if (this._acceptChannel != null) {
                removeBean(this._acceptChannel);
                if (this._acceptChannel.isOpen()) {
                    this._acceptChannel.close();
                }
            }
            this._acceptChannel = null;
            this._localPort = -2;
        }
    }

    @Override // org.eclipse.jetty.server.AbstractConnector, org.eclipse.jetty.server.Connector
    public void customize(EndPoint endPoint, Request request) throws IOException {
        request.setTimeStamp(System.currentTimeMillis());
        endPoint.setMaxIdleTime(this._maxIdleTime);
        super.customize(endPoint, request);
    }

    @Override // org.eclipse.jetty.server.AbstractConnector, org.eclipse.jetty.server.Connector
    public void persist(EndPoint endPoint) throws IOException {
        ((AsyncEndPoint) endPoint).setCheckForIdle(true);
        super.persist(endPoint);
    }

    public SelectorManager getSelectorManager() {
        return this._manager;
    }

    @Override // org.eclipse.jetty.server.Connector
    public synchronized Object getConnection() {
        return this._acceptChannel;
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getLocalPort() {
        int i;
        synchronized (this) {
            i = this._localPort;
        }
        return i;
    }

    public void open() throws IOException {
        synchronized (this) {
            if (this._acceptChannel == null) {
                this._acceptChannel = ServerSocketChannel.open();
                this._acceptChannel.configureBlocking(true);
                this._acceptChannel.socket().setReuseAddress(getReuseAddress());
                this._acceptChannel.socket().bind(getHost() == null ? new InetSocketAddress(getPort()) : new InetSocketAddress(getHost(), getPort()), getAcceptQueueSize());
                this._localPort = this._acceptChannel.socket().getLocalPort();
                if (this._localPort > 0) {
                    addBean(this._acceptChannel);
                } else {
                    throw new IOException("Server channel not bound");
                }
            }
        }
    }

    @Override // org.eclipse.jetty.server.AbstractConnector, org.eclipse.jetty.server.Connector
    public void setMaxIdleTime(int i) {
        this._manager.setMaxIdleTime(i);
        super.setMaxIdleTime(i);
    }

    public int getLowResourcesConnections() {
        return this._lowResourcesConnections;
    }

    public void setLowResourcesConnections(int i) {
        this._lowResourcesConnections = i;
    }

    @Override // org.eclipse.jetty.server.AbstractConnector
    public int getLowResourcesMaxIdleTime() {
        return this._lowResourcesMaxIdleTime;
    }

    @Override // org.eclipse.jetty.server.AbstractConnector
    public void setLowResourcesMaxIdleTime(int i) {
        this._lowResourcesMaxIdleTime = i;
        super.setLowResourcesMaxIdleTime(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.AbstractConnector, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        this._manager.setSelectSets(getAcceptors());
        this._manager.setMaxIdleTime(getMaxIdleTime());
        this._manager.setLowResourcesConnections(getLowResourcesConnections());
        this._manager.setLowResourcesMaxIdleTime(getLowResourcesMaxIdleTime());
        super.doStart();
    }

    protected SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey) throws IOException {
        SelectChannelEndPoint selectChannelEndPoint = new SelectChannelEndPoint(socketChannel, selectSet, selectionKey, this._maxIdleTime);
        selectChannelEndPoint.setConnection(selectSet.getManager().newConnection(socketChannel, selectChannelEndPoint, selectionKey.attachment()));
        return selectChannelEndPoint;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void endPointClosed(SelectChannelEndPoint selectChannelEndPoint) {
        connectionClosed(selectChannelEndPoint.getConnection());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint) {
        return new AsyncHttpConnection(this, asyncEndPoint, getServer());
    }

    /* loaded from: classes5.dex */
    private final class ConnectorSelectorManager extends SelectorManager {
        private ConnectorSelectorManager() {
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        public boolean dispatch(Runnable runnable) {
            ThreadPool threadPool = SelectChannelConnector.this.getThreadPool();
            if (threadPool == null) {
                threadPool = SelectChannelConnector.this.getServer().getThreadPool();
            }
            return threadPool.dispatch(runnable);
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected void endPointClosed(SelectChannelEndPoint selectChannelEndPoint) {
            SelectChannelConnector.this.endPointClosed(selectChannelEndPoint);
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected void endPointOpened(SelectChannelEndPoint selectChannelEndPoint) {
            SelectChannelConnector.this.connectionOpened(selectChannelEndPoint.getConnection());
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected void endPointUpgraded(ConnectedEndPoint connectedEndPoint, Connection connection) {
            SelectChannelConnector.this.connectionUpgraded(connection, connectedEndPoint.getConnection());
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        public AsyncConnection newConnection(SocketChannel socketChannel, AsyncEndPoint asyncEndPoint, Object obj) {
            return SelectChannelConnector.this.newConnection(socketChannel, asyncEndPoint);
        }

        @Override // org.eclipse.jetty.io.nio.SelectorManager
        protected SelectChannelEndPoint newEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey) throws IOException {
            return SelectChannelConnector.this.newEndPoint(socketChannel, selectSet, selectionKey);
        }
    }
}
