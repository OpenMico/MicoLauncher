package org.eclipse.jetty.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import org.eclipse.jetty.http.HttpException;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ConnectedEndPoint;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.io.nio.ChannelEndPoint;
import org.eclipse.jetty.server.BlockingHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class BlockingChannelConnector extends AbstractNIOConnector {
    private static final Logger LOG = Log.getLogger(BlockingChannelConnector.class);
    private transient ServerSocketChannel _acceptChannel;
    private final Set<BlockingChannelEndPoint> _endpoints = new ConcurrentHashSet();

    @Override // org.eclipse.jetty.server.Connector
    public Object getConnection() {
        return this._acceptChannel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.server.AbstractConnector, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        super.doStart();
        getThreadPool().dispatch(new Runnable() { // from class: org.eclipse.jetty.server.nio.BlockingChannelConnector.1
            @Override // java.lang.Runnable
            public void run() {
                while (BlockingChannelConnector.this.isRunning()) {
                    try {
                        Thread.sleep(400L);
                        long currentTimeMillis = System.currentTimeMillis();
                        for (BlockingChannelEndPoint blockingChannelEndPoint : BlockingChannelConnector.this._endpoints) {
                            blockingChannelEndPoint.checkIdleTimestamp(currentTimeMillis);
                        }
                    } catch (InterruptedException e) {
                        BlockingChannelConnector.LOG.ignore(e);
                    } catch (Exception e2) {
                        BlockingChannelConnector.LOG.warn(e2);
                    }
                }
            }
        });
    }

    @Override // org.eclipse.jetty.server.Connector
    public void open() throws IOException {
        this._acceptChannel = ServerSocketChannel.open();
        this._acceptChannel.configureBlocking(true);
        this._acceptChannel.socket().bind(getHost() == null ? new InetSocketAddress(getPort()) : new InetSocketAddress(getHost(), getPort()), getAcceptQueueSize());
    }

    @Override // org.eclipse.jetty.server.Connector
    public void close() throws IOException {
        ServerSocketChannel serverSocketChannel = this._acceptChannel;
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
        this._acceptChannel = null;
    }

    @Override // org.eclipse.jetty.server.AbstractConnector
    public void accept(int i) throws IOException, InterruptedException {
        SocketChannel accept = this._acceptChannel.accept();
        accept.configureBlocking(true);
        configure(accept.socket());
        new BlockingChannelEndPoint(accept).dispatch();
    }

    @Override // org.eclipse.jetty.server.AbstractConnector, org.eclipse.jetty.server.Connector
    public void customize(EndPoint endPoint, Request request) throws IOException {
        super.customize(endPoint, request);
        endPoint.setMaxIdleTime(this._maxIdleTime);
        configure(((SocketChannel) endPoint.getTransport()).socket());
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getLocalPort() {
        ServerSocketChannel serverSocketChannel = this._acceptChannel;
        if (serverSocketChannel == null || !serverSocketChannel.isOpen()) {
            return -1;
        }
        return this._acceptChannel.socket().getLocalPort();
    }

    /* loaded from: classes5.dex */
    private class BlockingChannelEndPoint extends ChannelEndPoint implements Runnable, ConnectedEndPoint {
        private Connection _connection;
        private volatile long _idleTimestamp;
        private int _timeout;

        BlockingChannelEndPoint(ByteChannel byteChannel) throws IOException {
            super(byteChannel, BlockingChannelConnector.this._maxIdleTime);
            this._connection = new BlockingHttpConnection(BlockingChannelConnector.this, this, BlockingChannelConnector.this.getServer());
        }

        @Override // org.eclipse.jetty.io.ConnectedEndPoint
        public Connection getConnection() {
            return this._connection;
        }

        @Override // org.eclipse.jetty.io.ConnectedEndPoint
        public void setConnection(Connection connection) {
            this._connection = connection;
        }

        public void checkIdleTimestamp(long j) {
            if (this._idleTimestamp != 0 && this._timeout > 0 && j > this._idleTimestamp + this._timeout) {
                idleExpired();
            }
        }

        protected void idleExpired() {
            try {
                super.close();
            } catch (IOException e) {
                BlockingChannelConnector.LOG.ignore(e);
            }
        }

        void dispatch() throws IOException {
            if (!BlockingChannelConnector.this.getThreadPool().dispatch(this)) {
                BlockingChannelConnector.LOG.warn("dispatch failed for  {}", this._connection);
                super.close();
            }
        }

        @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
        public int fill(Buffer buffer) throws IOException {
            this._idleTimestamp = System.currentTimeMillis();
            return super.fill(buffer);
        }

        @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
        public int flush(Buffer buffer) throws IOException {
            this._idleTimestamp = System.currentTimeMillis();
            return super.flush(buffer);
        }

        @Override // org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
        public int flush(Buffer buffer, Buffer buffer2, Buffer buffer3) throws IOException {
            this._idleTimestamp = System.currentTimeMillis();
            return super.flush(buffer, buffer2, buffer3);
        }

        @Override // java.lang.Runnable
        public void run() {
            int lowResourcesMaxIdleTime;
            try {
                try {
                    try {
                        this._timeout = getMaxIdleTime();
                        BlockingChannelConnector.this.connectionOpened(this._connection);
                        BlockingChannelConnector.this._endpoints.add(this);
                        while (isOpen()) {
                            this._idleTimestamp = System.currentTimeMillis();
                            if (this._connection.isIdle()) {
                                if (BlockingChannelConnector.this.getServer().getThreadPool().isLowOnThreads() && (lowResourcesMaxIdleTime = BlockingChannelConnector.this.getLowResourcesMaxIdleTime()) >= 0 && this._timeout != lowResourcesMaxIdleTime) {
                                    this._timeout = lowResourcesMaxIdleTime;
                                }
                            } else if (this._timeout != getMaxIdleTime()) {
                                this._timeout = getMaxIdleTime();
                            }
                            this._connection = this._connection.handle();
                        }
                        BlockingChannelConnector.this.connectionClosed(this._connection);
                        BlockingChannelConnector.this._endpoints.remove(this);
                        if (!this._socket.isClosed()) {
                            long currentTimeMillis = System.currentTimeMillis();
                            int maxIdleTime = getMaxIdleTime();
                            this._socket.setSoTimeout(getMaxIdleTime());
                            while (this._socket.getInputStream().read() >= 0 && System.currentTimeMillis() - currentTimeMillis < maxIdleTime) {
                            }
                            if (!this._socket.isClosed()) {
                                this._socket.close();
                            }
                        }
                    } catch (IOException e) {
                        BlockingChannelConnector.LOG.ignore(e);
                    }
                } catch (HttpException e2) {
                    BlockingChannelConnector.LOG.debug("BAD", e2);
                    try {
                        super.close();
                    } catch (IOException e3) {
                        BlockingChannelConnector.LOG.ignore(e3);
                    }
                    BlockingChannelConnector.this.connectionClosed(this._connection);
                    BlockingChannelConnector.this._endpoints.remove(this);
                    if (!this._socket.isClosed()) {
                        long currentTimeMillis2 = System.currentTimeMillis();
                        int maxIdleTime2 = getMaxIdleTime();
                        this._socket.setSoTimeout(getMaxIdleTime());
                        while (this._socket.getInputStream().read() >= 0 && System.currentTimeMillis() - currentTimeMillis2 < maxIdleTime2) {
                        }
                        if (!this._socket.isClosed()) {
                            this._socket.close();
                        }
                    }
                } catch (EofException e4) {
                    BlockingChannelConnector.LOG.debug("EOF", e4);
                    try {
                        close();
                    } catch (IOException e5) {
                        BlockingChannelConnector.LOG.ignore(e5);
                    }
                    BlockingChannelConnector.this.connectionClosed(this._connection);
                    BlockingChannelConnector.this._endpoints.remove(this);
                    if (!this._socket.isClosed()) {
                        long currentTimeMillis3 = System.currentTimeMillis();
                        int maxIdleTime3 = getMaxIdleTime();
                        this._socket.setSoTimeout(getMaxIdleTime());
                        while (this._socket.getInputStream().read() >= 0 && System.currentTimeMillis() - currentTimeMillis3 < maxIdleTime3) {
                        }
                        if (!this._socket.isClosed()) {
                            this._socket.close();
                        }
                    }
                } catch (Throwable th) {
                    BlockingChannelConnector.LOG.warn("handle failed", th);
                    try {
                        super.close();
                    } catch (IOException e6) {
                        BlockingChannelConnector.LOG.ignore(e6);
                    }
                    BlockingChannelConnector.this.connectionClosed(this._connection);
                    BlockingChannelConnector.this._endpoints.remove(this);
                    if (!this._socket.isClosed()) {
                        long currentTimeMillis4 = System.currentTimeMillis();
                        int maxIdleTime4 = getMaxIdleTime();
                        this._socket.setSoTimeout(getMaxIdleTime());
                        while (this._socket.getInputStream().read() >= 0 && System.currentTimeMillis() - currentTimeMillis4 < maxIdleTime4) {
                        }
                        if (!this._socket.isClosed()) {
                            this._socket.close();
                        }
                    }
                }
            } catch (Throwable th2) {
                BlockingChannelConnector.this.connectionClosed(this._connection);
                BlockingChannelConnector.this._endpoints.remove(this);
                try {
                    if (!this._socket.isClosed()) {
                        long currentTimeMillis5 = System.currentTimeMillis();
                        int maxIdleTime5 = getMaxIdleTime();
                        this._socket.setSoTimeout(getMaxIdleTime());
                        while (this._socket.getInputStream().read() >= 0 && System.currentTimeMillis() - currentTimeMillis5 < maxIdleTime5) {
                        }
                        if (!this._socket.isClosed()) {
                            this._socket.close();
                        }
                    }
                } catch (IOException e7) {
                    BlockingChannelConnector.LOG.ignore(e7);
                }
                throw th2;
            }
        }

        public String toString() {
            return String.format("BCEP@%x{l(%s)<->r(%s),open=%b,ishut=%b,oshut=%b}-{%s}", Integer.valueOf(hashCode()), this._socket.getRemoteSocketAddress(), this._socket.getLocalSocketAddress(), Boolean.valueOf(isOpen()), Boolean.valueOf(isInputShutdown()), Boolean.valueOf(isOutputShutdown()), this._connection);
        }
    }
}
