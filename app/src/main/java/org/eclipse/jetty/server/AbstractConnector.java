package org.eclipse.jetty.server;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpBuffers;
import org.eclipse.jetty.http.HttpBuffersImpl;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.io.Buffers;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.statistic.CounterStatistic;
import org.eclipse.jetty.util.statistic.SampleStatistic;
import org.eclipse.jetty.util.thread.ThreadPool;

/* loaded from: classes5.dex */
public abstract class AbstractConnector extends AggregateLifeCycle implements HttpBuffers, Connector, Dumpable {
    private static final Logger LOG = Log.getLogger(AbstractConnector.class);
    private transient Thread[] _acceptorThreads;
    private boolean _forwarded;
    private String _forwardedCipherSuiteHeader;
    private String _forwardedSslSessionIdHeader;
    private String _host;
    private String _hostHeader;
    private String _name;
    private Server _server;
    private ThreadPool _threadPool;
    private boolean _useDNS;
    private int _port = 0;
    private String _integralScheme = "https";
    private int _integralPort = 0;
    private String _confidentialScheme = "https";
    private int _confidentialPort = 0;
    private int _acceptQueueSize = 0;
    private int _acceptors = 1;
    private int _acceptorPriorityOffset = 0;
    private String _forwardedHostHeader = "X-Forwarded-Host";
    private String _forwardedServerHeader = HttpHeaders.X_FORWARDED_SERVER;
    private String _forwardedForHeader = "X-Forwarded-For";
    private String _forwardedProtoHeader = "X-Forwarded-Proto";
    private boolean _reuseAddress = true;
    protected int _maxIdleTime = 200000;
    protected int _lowResourceMaxIdleTime = -1;
    protected int _soLingerTime = -1;
    private final AtomicLong _statsStartedAt = new AtomicLong(-1);
    private final CounterStatistic _connectionStats = new CounterStatistic();
    private final SampleStatistic _requestStats = new SampleStatistic();
    private final SampleStatistic _connectionDurationStats = new SampleStatistic();
    protected final HttpBuffersImpl _buffers = new HttpBuffersImpl();

    protected abstract void accept(int i) throws IOException, InterruptedException;

    @Override // org.eclipse.jetty.server.Connector
    public boolean isIntegral(Request request) {
        return false;
    }

    @Override // org.eclipse.jetty.server.Connector
    public void persist(EndPoint endPoint) throws IOException {
    }

    public void stopAccept(int i) throws Exception {
    }

    public AbstractConnector() {
        addBean(this._buffers);
    }

    @Override // org.eclipse.jetty.server.Connector
    public Server getServer() {
        return this._server;
    }

    @Override // org.eclipse.jetty.server.Connector
    public void setServer(Server server) {
        this._server = server;
    }

    public ThreadPool getThreadPool() {
        return this._threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        removeBean(this._threadPool);
        this._threadPool = threadPool;
        addBean(this._threadPool);
    }

    @Override // org.eclipse.jetty.server.Connector
    public void setHost(String str) {
        this._host = str;
    }

    @Override // org.eclipse.jetty.server.Connector
    public String getHost() {
        return this._host;
    }

    @Override // org.eclipse.jetty.server.Connector
    public void setPort(int i) {
        this._port = i;
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getPort() {
        return this._port;
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getMaxIdleTime() {
        return this._maxIdleTime;
    }

    @Override // org.eclipse.jetty.server.Connector
    public void setMaxIdleTime(int i) {
        this._maxIdleTime = i;
    }

    public int getLowResourcesMaxIdleTime() {
        return this._lowResourceMaxIdleTime;
    }

    public void setLowResourcesMaxIdleTime(int i) {
        this._lowResourceMaxIdleTime = i;
    }

    @Override // org.eclipse.jetty.server.Connector
    @Deprecated
    public final int getLowResourceMaxIdleTime() {
        return getLowResourcesMaxIdleTime();
    }

    @Override // org.eclipse.jetty.server.Connector
    @Deprecated
    public final void setLowResourceMaxIdleTime(int i) {
        setLowResourcesMaxIdleTime(i);
    }

    public int getSoLingerTime() {
        return this._soLingerTime;
    }

    public int getAcceptQueueSize() {
        return this._acceptQueueSize;
    }

    public void setAcceptQueueSize(int i) {
        this._acceptQueueSize = i;
    }

    public int getAcceptors() {
        return this._acceptors;
    }

    public void setAcceptors(int i) {
        if (i > Runtime.getRuntime().availableProcessors() * 2) {
            Logger logger = LOG;
            logger.warn("Acceptors should be <=2*availableProcessors: " + this, new Object[0]);
        }
        this._acceptors = i;
    }

    public void setSoLingerTime(int i) {
        this._soLingerTime = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        if (this._server != null) {
            open();
            if (this._threadPool == null) {
                this._threadPool = this._server.getThreadPool();
                addBean(this._threadPool, false);
            }
            super.doStart();
            synchronized (this) {
                this._acceptorThreads = new Thread[getAcceptors()];
                for (int i = 0; i < this._acceptorThreads.length; i++) {
                    if (!this._threadPool.dispatch(new Acceptor(i))) {
                        throw new IllegalStateException("!accepting");
                    }
                }
                if (this._threadPool.isLowOnThreads()) {
                    LOG.warn("insufficient threads configured for {}", this);
                }
            }
            LOG.info("Started {}", this);
            return;
        }
        throw new IllegalStateException("No server");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        Thread[] threadArr;
        try {
            close();
        } catch (IOException e) {
            LOG.warn(e);
        }
        super.doStop();
        synchronized (this) {
            threadArr = this._acceptorThreads;
            this._acceptorThreads = null;
        }
        if (threadArr != null) {
            for (Thread thread : threadArr) {
                if (thread != null) {
                    thread.interrupt();
                }
            }
        }
    }

    public void join() throws InterruptedException {
        Thread[] threadArr;
        synchronized (this) {
            threadArr = this._acceptorThreads;
        }
        if (threadArr != null) {
            for (Thread thread : threadArr) {
                if (thread != null) {
                    thread.join();
                }
            }
        }
    }

    protected void configure(Socket socket) throws IOException {
        try {
            socket.setTcpNoDelay(true);
            if (this._soLingerTime >= 0) {
                socket.setSoLinger(true, this._soLingerTime / 1000);
            } else {
                socket.setSoLinger(false, 0);
            }
        } catch (Exception e) {
            LOG.ignore(e);
        }
    }

    @Override // org.eclipse.jetty.server.Connector
    public void customize(EndPoint endPoint, Request request) throws IOException {
        if (isForwarded()) {
            checkForwardedHeaders(endPoint, request);
        }
    }

    protected void checkForwardedHeaders(EndPoint endPoint, Request request) throws IOException {
        String stringField;
        String stringField2;
        HttpFields requestFields = request.getConnection().getRequestFields();
        if (!(getForwardedCipherSuiteHeader() == null || (stringField2 = requestFields.getStringField(getForwardedCipherSuiteHeader())) == null)) {
            request.setAttribute("javax.servlet.request.cipher_suite", stringField2);
        }
        if (!(getForwardedSslSessionIdHeader() == null || (stringField = requestFields.getStringField(getForwardedSslSessionIdHeader())) == null)) {
            request.setAttribute("javax.servlet.request.ssl_session_id", stringField);
            request.setScheme("https");
        }
        String leftMostFieldValue = getLeftMostFieldValue(requestFields, getForwardedHostHeader());
        String leftMostFieldValue2 = getLeftMostFieldValue(requestFields, getForwardedServerHeader());
        String leftMostFieldValue3 = getLeftMostFieldValue(requestFields, getForwardedForHeader());
        String leftMostFieldValue4 = getLeftMostFieldValue(requestFields, getForwardedProtoHeader());
        InetAddress inetAddress = null;
        if (this._hostHeader != null) {
            requestFields.put(HttpHeaders.HOST_BUFFER, this._hostHeader);
            request.setServerName(null);
            request.setServerPort(-1);
            request.getServerName();
        } else if (leftMostFieldValue != null) {
            requestFields.put(HttpHeaders.HOST_BUFFER, leftMostFieldValue);
            request.setServerName(null);
            request.setServerPort(-1);
            request.getServerName();
        } else if (leftMostFieldValue2 != null) {
            request.setServerName(leftMostFieldValue2);
        }
        if (leftMostFieldValue3 != null) {
            request.setRemoteAddr(leftMostFieldValue3);
            if (this._useDNS) {
                try {
                    inetAddress = InetAddress.getByName(leftMostFieldValue3);
                } catch (UnknownHostException e) {
                    LOG.ignore(e);
                }
            }
            if (inetAddress != null) {
                leftMostFieldValue3 = inetAddress.getHostName();
            }
            request.setRemoteHost(leftMostFieldValue3);
        }
        if (leftMostFieldValue4 != null) {
            request.setScheme(leftMostFieldValue4);
        }
    }

    protected String getLeftMostFieldValue(HttpFields httpFields, String str) {
        String stringField;
        if (str == null || (stringField = httpFields.getStringField(str)) == null) {
            return null;
        }
        int indexOf = stringField.indexOf(44);
        return indexOf == -1 ? stringField : stringField.substring(0, indexOf);
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getConfidentialPort() {
        return this._confidentialPort;
    }

    @Override // org.eclipse.jetty.server.Connector
    public String getConfidentialScheme() {
        return this._confidentialScheme;
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getIntegralPort() {
        return this._integralPort;
    }

    @Override // org.eclipse.jetty.server.Connector
    public String getIntegralScheme() {
        return this._integralScheme;
    }

    @Override // org.eclipse.jetty.server.Connector
    public boolean isConfidential(Request request) {
        return this._forwarded && request.getScheme().equalsIgnoreCase("https");
    }

    public void setConfidentialPort(int i) {
        this._confidentialPort = i;
    }

    public void setConfidentialScheme(String str) {
        this._confidentialScheme = str;
    }

    public void setIntegralPort(int i) {
        this._integralPort = i;
    }

    public void setIntegralScheme(String str) {
        this._integralScheme = str;
    }

    @Override // org.eclipse.jetty.server.Connector
    public boolean getResolveNames() {
        return this._useDNS;
    }

    public void setResolveNames(boolean z) {
        this._useDNS = z;
    }

    public boolean isForwarded() {
        return this._forwarded;
    }

    public void setForwarded(boolean z) {
        if (z) {
            LOG.debug("{} is forwarded", this);
        }
        this._forwarded = z;
    }

    public String getHostHeader() {
        return this._hostHeader;
    }

    public void setHostHeader(String str) {
        this._hostHeader = str;
    }

    public String getForwardedHostHeader() {
        return this._forwardedHostHeader;
    }

    public void setForwardedHostHeader(String str) {
        this._forwardedHostHeader = str;
    }

    public String getForwardedServerHeader() {
        return this._forwardedServerHeader;
    }

    public void setForwardedServerHeader(String str) {
        this._forwardedServerHeader = str;
    }

    public String getForwardedForHeader() {
        return this._forwardedForHeader;
    }

    public void setForwardedForHeader(String str) {
        this._forwardedForHeader = str;
    }

    public String getForwardedProtoHeader() {
        return this._forwardedProtoHeader;
    }

    public void setForwardedProtoHeader(String str) {
        this._forwardedProtoHeader = str;
    }

    public String getForwardedCipherSuiteHeader() {
        return this._forwardedCipherSuiteHeader;
    }

    public void setForwardedCipherSuiteHeader(String str) {
        this._forwardedCipherSuiteHeader = str;
    }

    public String getForwardedSslSessionIdHeader() {
        return this._forwardedSslSessionIdHeader;
    }

    public void setForwardedSslSessionIdHeader(String str) {
        this._forwardedSslSessionIdHeader = str;
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public int getRequestBufferSize() {
        return this._buffers.getRequestBufferSize();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setRequestBufferSize(int i) {
        this._buffers.setRequestBufferSize(i);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public int getRequestHeaderSize() {
        return this._buffers.getRequestHeaderSize();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setRequestHeaderSize(int i) {
        this._buffers.setRequestHeaderSize(i);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public int getResponseBufferSize() {
        return this._buffers.getResponseBufferSize();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setResponseBufferSize(int i) {
        this._buffers.setResponseBufferSize(i);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public int getResponseHeaderSize() {
        return this._buffers.getResponseHeaderSize();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setResponseHeaderSize(int i) {
        this._buffers.setResponseHeaderSize(i);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public Buffers.Type getRequestBufferType() {
        return this._buffers.getRequestBufferType();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public Buffers.Type getRequestHeaderType() {
        return this._buffers.getRequestHeaderType();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public Buffers.Type getResponseBufferType() {
        return this._buffers.getResponseBufferType();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public Buffers.Type getResponseHeaderType() {
        return this._buffers.getResponseHeaderType();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setRequestBuffers(Buffers buffers) {
        this._buffers.setRequestBuffers(buffers);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setResponseBuffers(Buffers buffers) {
        this._buffers.setResponseBuffers(buffers);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public Buffers getRequestBuffers() {
        return this._buffers.getRequestBuffers();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public Buffers getResponseBuffers() {
        return this._buffers.getResponseBuffers();
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public void setMaxBuffers(int i) {
        this._buffers.setMaxBuffers(i);
    }

    @Override // org.eclipse.jetty.http.HttpBuffers
    public int getMaxBuffers() {
        return this._buffers.getMaxBuffers();
    }

    public String toString() {
        Object[] objArr = new Object[3];
        objArr[0] = getClass().getSimpleName();
        objArr[1] = getHost() == null ? StringUtil.ALL_INTERFACES : getHost();
        objArr[2] = Integer.valueOf(getLocalPort() <= 0 ? getPort() : getLocalPort());
        return String.format("%s@%s:%d", objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class Acceptor implements Runnable {
        int _acceptor;

        Acceptor(int i) {
            this._acceptor = 0;
            this._acceptor = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            Thread currentThread = Thread.currentThread();
            synchronized (AbstractConnector.this) {
                try {
                    if (AbstractConnector.this._acceptorThreads != null) {
                        AbstractConnector.this._acceptorThreads[this._acceptor] = currentThread;
                        String name = AbstractConnector.this._acceptorThreads[this._acceptor].getName();
                        currentThread.setName(name + " Acceptor" + this._acceptor + StringUtils.SPACE + AbstractConnector.this);
                        int priority = currentThread.getPriority();
                        try {
                            currentThread.setPriority(priority - AbstractConnector.this._acceptorPriorityOffset);
                            while (AbstractConnector.this.isRunning() && AbstractConnector.this.getConnection() != null) {
                                try {
                                    try {
                                        AbstractConnector.this.accept(this._acceptor);
                                    } catch (EofException e) {
                                        AbstractConnector.LOG.ignore(e);
                                    }
                                } catch (IOException e2) {
                                    AbstractConnector.LOG.ignore(e2);
                                } catch (InterruptedException e3) {
                                    AbstractConnector.LOG.ignore(e3);
                                }
                            }
                            currentThread.setPriority(priority);
                            currentThread.setName(name);
                            synchronized (AbstractConnector.this) {
                                try {
                                    if (AbstractConnector.this._acceptorThreads != null) {
                                        AbstractConnector.this._acceptorThreads[this._acceptor] = null;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            currentThread.setPriority(priority);
                            currentThread.setName(name);
                            synchronized (AbstractConnector.this) {
                                try {
                                    if (AbstractConnector.this._acceptorThreads != null) {
                                        AbstractConnector.this._acceptorThreads[this._acceptor] = null;
                                    }
                                    throw th2;
                                } catch (Throwable th3) {
                                    throw th3;
                                }
                            }
                        }
                    }
                } catch (Throwable th4) {
                    throw th4;
                }
            }
        }
    }

    @Override // org.eclipse.jetty.server.Connector
    public String getName() {
        if (this._name == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getHost() == null ? StringUtil.ALL_INTERFACES : getHost());
            sb.append(Constants.COLON_SEPARATOR);
            sb.append(getLocalPort() <= 0 ? getPort() : getLocalPort());
            this._name = sb.toString();
        }
        return this._name;
    }

    public void setName(String str) {
        this._name = str;
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getRequests() {
        return (int) this._requestStats.getTotal();
    }

    @Override // org.eclipse.jetty.server.Connector
    public long getConnectionsDurationTotal() {
        return this._connectionDurationStats.getTotal();
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getConnections() {
        return (int) this._connectionStats.getTotal();
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getConnectionsOpen() {
        return (int) this._connectionStats.getCurrent();
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getConnectionsOpenMax() {
        return (int) this._connectionStats.getMax();
    }

    @Override // org.eclipse.jetty.server.Connector
    public double getConnectionsDurationMean() {
        return this._connectionDurationStats.getMean();
    }

    @Override // org.eclipse.jetty.server.Connector
    public long getConnectionsDurationMax() {
        return this._connectionDurationStats.getMax();
    }

    @Override // org.eclipse.jetty.server.Connector
    public double getConnectionsDurationStdDev() {
        return this._connectionDurationStats.getStdDev();
    }

    @Override // org.eclipse.jetty.server.Connector
    public double getConnectionsRequestsMean() {
        return this._requestStats.getMean();
    }

    @Override // org.eclipse.jetty.server.Connector
    public int getConnectionsRequestsMax() {
        return (int) this._requestStats.getMax();
    }

    @Override // org.eclipse.jetty.server.Connector
    public double getConnectionsRequestsStdDev() {
        return this._requestStats.getStdDev();
    }

    @Override // org.eclipse.jetty.server.Connector
    public void statsReset() {
        updateNotEqual(this._statsStartedAt, -1L, System.currentTimeMillis());
        this._requestStats.reset();
        this._connectionStats.reset();
        this._connectionDurationStats.reset();
    }

    @Override // org.eclipse.jetty.server.Connector
    public void setStatsOn(boolean z) {
        long j = -1;
        if (!z || this._statsStartedAt.get() == -1) {
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Statistics on = " + z + " for " + this, new Object[0]);
            }
            statsReset();
            AtomicLong atomicLong = this._statsStartedAt;
            if (z) {
                j = System.currentTimeMillis();
            }
            atomicLong.set(j);
        }
    }

    @Override // org.eclipse.jetty.server.Connector
    public boolean getStatsOn() {
        return this._statsStartedAt.get() != -1;
    }

    @Override // org.eclipse.jetty.server.Connector
    public long getStatsOnMs() {
        long j = this._statsStartedAt.get();
        if (j != -1) {
            return System.currentTimeMillis() - j;
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void connectionOpened(Connection connection) {
        if (this._statsStartedAt.get() != -1) {
            this._connectionStats.increment();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void connectionUpgraded(Connection connection, Connection connection2) {
        this._requestStats.set(connection instanceof AbstractHttpConnection ? ((AbstractHttpConnection) connection).getRequests() : 0L);
    }

    protected void connectionClosed(Connection connection) {
        connection.onClose();
        if (this._statsStartedAt.get() != -1) {
            long currentTimeMillis = System.currentTimeMillis() - connection.getTimeStamp();
            this._requestStats.set(connection instanceof AbstractHttpConnection ? ((AbstractHttpConnection) connection).getRequests() : 0);
            this._connectionStats.decrement();
            this._connectionDurationStats.set(currentTimeMillis);
        }
    }

    public int getAcceptorPriorityOffset() {
        return this._acceptorPriorityOffset;
    }

    public void setAcceptorPriorityOffset(int i) {
        this._acceptorPriorityOffset = i;
    }

    public boolean getReuseAddress() {
        return this._reuseAddress;
    }

    public void setReuseAddress(boolean z) {
        this._reuseAddress = z;
    }

    @Override // org.eclipse.jetty.server.Connector
    public boolean isLowResources() {
        ThreadPool threadPool = this._threadPool;
        if (threadPool != null) {
            return threadPool.isLowOnThreads();
        }
        return this._server.getThreadPool().isLowOnThreads();
    }

    private void updateNotEqual(AtomicLong atomicLong, long j, long j2) {
        long j3 = atomicLong.get();
        while (j != j3 && !atomicLong.compareAndSet(j3, j2)) {
            j3 = atomicLong.get();
        }
    }
}
