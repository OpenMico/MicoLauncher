package org.eclipse.jetty.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.HttpSchemes;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersions;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.BufferCache;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.Timeout;

/* loaded from: classes5.dex */
public class HttpExchange {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final Logger LOG = Log.getLogger(HttpExchange.class);
    public static final int STATUS_CANCELLED = 11;
    public static final int STATUS_CANCELLING = 10;
    public static final int STATUS_COMPLETED = 7;
    public static final int STATUS_EXCEPTED = 9;
    public static final int STATUS_EXPIRED = 8;
    public static final int STATUS_PARSING_CONTENT = 6;
    public static final int STATUS_PARSING_HEADERS = 5;
    public static final int STATUS_SENDING_REQUEST = 3;
    public static final int STATUS_START = 0;
    public static final int STATUS_WAITING_FOR_COMMIT = 2;
    public static final int STATUS_WAITING_FOR_CONNECTION = 1;
    public static final int STATUS_WAITING_FOR_RESPONSE = 4;
    private Address _address;
    private volatile AbstractHttpConnection _connection;
    boolean _onDone;
    boolean _onRequestCompleteDone;
    boolean _onResponseCompleteDone;
    private Buffer _requestContent;
    private InputStream _requestContentSource;
    private volatile Timeout.Task _timeoutTask;
    private String _uri;
    private String _method = "GET";
    private Buffer _scheme = HttpSchemes.HTTP_BUFFER;
    private int _version = 11;
    private final HttpFields _requestFields = new HttpFields();
    private AtomicInteger _status = new AtomicInteger(0);
    private boolean _retryStatus = false;
    private boolean _configureListeners = true;
    private HttpEventListener _listener = new Listener();
    private Address _localAddress = null;
    private long _timeout = -1;
    private long _lastStateChange = System.currentTimeMillis();
    private long _sent = -1;
    private int _lastState = -1;
    private int _lastStatePeriod = -1;

    @Deprecated
    /* loaded from: classes5.dex */
    public static class ContentExchange extends ContentExchange {
    }

    public static String toState(int i) {
        switch (i) {
            case 0:
                return "START";
            case 1:
                return "CONNECTING";
            case 2:
                return "CONNECTED";
            case 3:
                return "SENDING";
            case 4:
                return "WAITING";
            case 5:
                return "HEADERS";
            case 6:
                return "CONTENT";
            case 7:
                return "COMPLETED";
            case 8:
                return "EXPIRED";
            case 9:
                return "EXCEPTED";
            case 10:
                return "CANCELLING";
            case 11:
                return "CANCELLED";
            default:
                return "UNKNOWN";
        }
    }

    protected void onRequestCommitted() throws IOException {
    }

    protected void onRequestComplete() throws IOException {
    }

    public void onResponseComplete() throws IOException {
    }

    protected void onResponseContent(Buffer buffer) throws IOException {
    }

    public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
    }

    protected void onResponseHeaderComplete() throws IOException {
    }

    public void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException {
    }

    public Connection onSwitchProtocol(EndPoint endPoint) throws IOException {
        return null;
    }

    protected void expire(HttpDestination httpDestination) {
        if (getStatus() < 7) {
            setStatus(8);
        }
        httpDestination.exchangeExpired(this);
        AbstractHttpConnection abstractHttpConnection = this._connection;
        if (abstractHttpConnection != null) {
            abstractHttpConnection.exchangeExpired(this);
        }
    }

    public int getStatus() {
        return this._status.get();
    }

    @Deprecated
    public void waitForStatus(int i) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public int waitForDone() throws InterruptedException {
        int i;
        synchronized (this) {
            while (!isDone()) {
                wait();
            }
            i = this._status.get();
        }
        return i;
    }

    public void reset() {
        synchronized (this) {
            this._timeoutTask = null;
            this._onRequestCompleteDone = false;
            this._onResponseCompleteDone = false;
            this._onDone = false;
            setStatus(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean setStatus(int r8) {
        /*
            Method dump skipped, instructions count: 518
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.client.HttpExchange.setStatus(int):boolean");
    }

    private boolean setStatusExpired(int i, int i2) {
        boolean compareAndSet = this._status.compareAndSet(i2, i);
        if (compareAndSet) {
            getEventListener().onExpire();
        }
        return compareAndSet;
    }

    public boolean isDone() {
        boolean z;
        synchronized (this) {
            z = this._onDone;
        }
        return z;
    }

    @Deprecated
    public boolean isDone(int i) {
        return isDone();
    }

    public HttpEventListener getEventListener() {
        return this._listener;
    }

    public void setEventListener(HttpEventListener httpEventListener) {
        this._listener = httpEventListener;
    }

    public void setTimeout(long j) {
        this._timeout = j;
    }

    public long getTimeout() {
        return this._timeout;
    }

    public void setURL(String str) {
        setURI(URI.create(str));
    }

    public void setAddress(Address address) {
        this._address = address;
    }

    public Address getAddress() {
        return this._address;
    }

    public Address getLocalAddress() {
        return this._localAddress;
    }

    public void setScheme(Buffer buffer) {
        this._scheme = buffer;
    }

    public void setScheme(String str) {
        if (str == null) {
            return;
        }
        if ("http".equalsIgnoreCase(str)) {
            setScheme(HttpSchemes.HTTP_BUFFER);
        } else if ("https".equalsIgnoreCase(str)) {
            setScheme(HttpSchemes.HTTPS_BUFFER);
        } else {
            setScheme(new ByteArrayBuffer(str));
        }
    }

    public Buffer getScheme() {
        return this._scheme;
    }

    public void setVersion(int i) {
        this._version = i;
    }

    public void setVersion(String str) {
        BufferCache.CachedBuffer cachedBuffer = HttpVersions.CACHE.get(str);
        if (cachedBuffer == null) {
            this._version = 10;
        } else {
            this._version = cachedBuffer.getOrdinal();
        }
    }

    public int getVersion() {
        return this._version;
    }

    public void setMethod(String str) {
        this._method = str;
    }

    public String getMethod() {
        return this._method;
    }

    @Deprecated
    public String getURI() {
        return getRequestURI();
    }

    public String getRequestURI() {
        return this._uri;
    }

    @Deprecated
    public void setURI(String str) {
        setRequestURI(str);
    }

    public void setRequestURI(String str) {
        this._uri = str;
    }

    public void setURI(URI uri) {
        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("!Absolute URI: " + uri);
        } else if (!uri.isOpaque()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("URI = {}", uri.toASCIIString());
            }
            String scheme = uri.getScheme();
            int port = uri.getPort();
            if (port <= 0) {
                port = "https".equalsIgnoreCase(scheme) ? 443 : 80;
            }
            setScheme(scheme);
            setAddress(new Address(uri.getHost(), port));
            String completePath = new HttpURI(uri).getCompletePath();
            if (completePath == null) {
                completePath = "/";
            }
            setRequestURI(completePath);
        } else {
            throw new IllegalArgumentException("Opaque URI: " + uri);
        }
    }

    public void addRequestHeader(String str, String str2) {
        getRequestFields().add(str, str2);
    }

    public void addRequestHeader(Buffer buffer, Buffer buffer2) {
        getRequestFields().add(buffer, buffer2);
    }

    public void setRequestHeader(String str, String str2) {
        getRequestFields().put(str, str2);
    }

    public void setRequestHeader(Buffer buffer, Buffer buffer2) {
        getRequestFields().put(buffer, buffer2);
    }

    public void setRequestContentType(String str) {
        getRequestFields().put(HttpHeaders.CONTENT_TYPE_BUFFER, str);
    }

    public HttpFields getRequestFields() {
        return this._requestFields;
    }

    public void setRequestContent(Buffer buffer) {
        this._requestContent = buffer;
    }

    public void setRequestContentSource(InputStream inputStream) {
        this._requestContentSource = inputStream;
        InputStream inputStream2 = this._requestContentSource;
        if (inputStream2 != null && inputStream2.markSupported()) {
            this._requestContentSource.mark(Integer.MAX_VALUE);
        }
    }

    public InputStream getRequestContentSource() {
        return this._requestContentSource;
    }

    public Buffer getRequestContentChunk(Buffer buffer) throws IOException {
        synchronized (this) {
            if (this._requestContentSource != null) {
                if (buffer == null) {
                    buffer = new ByteArrayBuffer(8192);
                }
                int read = this._requestContentSource.read(buffer.array(), buffer.putIndex(), buffer.space());
                if (read >= 0) {
                    buffer.setPutIndex(buffer.putIndex() + read);
                    return buffer;
                }
            }
            return null;
        }
    }

    public Buffer getRequestContent() {
        return this._requestContent;
    }

    public boolean getRetryStatus() {
        return this._retryStatus;
    }

    public void setRetryStatus(boolean z) {
        this._retryStatus = z;
    }

    public void cancel() {
        setStatus(10);
        abort();
    }

    public void done() {
        synchronized (this) {
            disassociate();
            this._onDone = true;
            notifyAll();
        }
    }

    private void abort() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        try {
            if (abstractHttpConnection != null) {
                try {
                    abstractHttpConnection.close();
                } catch (IOException e) {
                    LOG.debug(e);
                }
            }
        } finally {
            disassociate();
        }
    }

    public void associate(AbstractHttpConnection abstractHttpConnection) {
        if (abstractHttpConnection.getEndPoint().getLocalAddr() != null) {
            this._localAddress = new Address(abstractHttpConnection.getEndPoint().getLocalAddr(), abstractHttpConnection.getEndPoint().getLocalPort());
        }
        this._connection = abstractHttpConnection;
        if (getStatus() == 10) {
            abort();
        }
    }

    boolean isAssociated() {
        return this._connection != null;
    }

    public AbstractHttpConnection disassociate() {
        AbstractHttpConnection abstractHttpConnection = this._connection;
        this._connection = null;
        if (getStatus() == 10) {
            setStatus(11);
        }
        return abstractHttpConnection;
    }

    public String toString() {
        String state = toState(getStatus());
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - this._lastStateChange;
        String format = this._lastState >= 0 ? String.format("%s@%x=%s//%s%s#%s(%dms)->%s(%dms)", getClass().getSimpleName(), Integer.valueOf(hashCode()), this._method, this._address, this._uri, toState(this._lastState), Integer.valueOf(this._lastStatePeriod), state, Long.valueOf(j)) : String.format("%s@%x=%s//%s%s#%s(%dms)", getClass().getSimpleName(), Integer.valueOf(hashCode()), this._method, this._address, this._uri, state, Long.valueOf(j));
        if (getStatus() < 3 || this._sent <= 0) {
            return format;
        }
        return format + "sent=" + (currentTimeMillis - this._sent) + "ms";
    }

    protected void onConnectionFailed(Throwable th) {
        Logger logger = LOG;
        logger.warn("CONNECTION FAILED " + this, th);
    }

    protected void onException(Throwable th) {
        Logger logger = LOG;
        logger.warn(Log.EXCEPTION + this, th);
    }

    protected void onExpire() {
        Logger logger = LOG;
        logger.warn("EXPIRED " + this, new Object[0]);
    }

    protected void onRetry() throws IOException {
        InputStream inputStream = this._requestContentSource;
        if (inputStream == null) {
            return;
        }
        if (inputStream.markSupported()) {
            this._requestContent = null;
            this._requestContentSource.reset();
            return;
        }
        throw new IOException("Unsupported retry attempt");
    }

    public boolean configureListeners() {
        return this._configureListeners;
    }

    public void setConfigureListeners(boolean z) {
        this._configureListeners = z;
    }

    public void scheduleTimeout(final HttpDestination httpDestination) {
        this._timeoutTask = new Timeout.Task() { // from class: org.eclipse.jetty.client.HttpExchange.1
            @Override // org.eclipse.jetty.util.thread.Timeout.Task
            public void expired() {
                HttpExchange.this.expire(httpDestination);
            }
        };
        HttpClient httpClient = httpDestination.getHttpClient();
        long timeout = getTimeout();
        if (timeout > 0) {
            httpClient.schedule(this._timeoutTask, timeout);
        } else {
            httpClient.schedule(this._timeoutTask);
        }
    }

    public void cancelTimeout(HttpClient httpClient) {
        Timeout.Task task = this._timeoutTask;
        if (task != null) {
            httpClient.cancel(task);
        }
        this._timeoutTask = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public class Listener implements HttpEventListener {
        private Listener() {
            HttpExchange.this = r1;
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onConnectionFailed(Throwable th) {
            try {
                HttpExchange.this.onConnectionFailed(th);
            } finally {
                HttpExchange.this.done();
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onException(Throwable th) {
            try {
                HttpExchange.this.onException(th);
            } finally {
                HttpExchange.this.done();
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onExpire() {
            try {
                HttpExchange.this.onExpire();
            } finally {
                HttpExchange.this.done();
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRequestCommitted() throws IOException {
            HttpExchange.this.onRequestCommitted();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRequestComplete() throws IOException {
            try {
                HttpExchange.this.onRequestComplete();
                synchronized (HttpExchange.this) {
                    try {
                        HttpExchange.this._onRequestCompleteDone = true;
                        HttpExchange.this._onDone |= HttpExchange.this._onResponseCompleteDone;
                        if (HttpExchange.this._onDone) {
                            HttpExchange.this.disassociate();
                        }
                        HttpExchange.this.notifyAll();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                synchronized (HttpExchange.this) {
                    try {
                        HttpExchange.this._onRequestCompleteDone = true;
                        HttpExchange.this._onDone |= HttpExchange.this._onResponseCompleteDone;
                        if (HttpExchange.this._onDone) {
                            HttpExchange.this.disassociate();
                        }
                        HttpExchange.this.notifyAll();
                        throw th2;
                    } catch (Throwable th3) {
                        throw th3;
                    }
                }
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseComplete() throws IOException {
            try {
                HttpExchange.this.onResponseComplete();
                synchronized (HttpExchange.this) {
                    try {
                        HttpExchange.this._onResponseCompleteDone = true;
                        HttpExchange.this._onDone |= HttpExchange.this._onRequestCompleteDone;
                        if (HttpExchange.this._onDone) {
                            HttpExchange.this.disassociate();
                        }
                        HttpExchange.this.notifyAll();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                synchronized (HttpExchange.this) {
                    try {
                        HttpExchange.this._onResponseCompleteDone = true;
                        HttpExchange.this._onDone |= HttpExchange.this._onRequestCompleteDone;
                        if (HttpExchange.this._onDone) {
                            HttpExchange.this.disassociate();
                        }
                        HttpExchange.this.notifyAll();
                        throw th2;
                    } catch (Throwable th3) {
                        throw th3;
                    }
                }
            }
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseContent(Buffer buffer) throws IOException {
            HttpExchange.this.onResponseContent(buffer);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseHeader(Buffer buffer, Buffer buffer2) throws IOException {
            HttpExchange.this.onResponseHeader(buffer, buffer2);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseHeaderComplete() throws IOException {
            HttpExchange.this.onResponseHeaderComplete();
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onResponseStatus(Buffer buffer, int i, Buffer buffer2) throws IOException {
            HttpExchange.this.onResponseStatus(buffer, i, buffer2);
        }

        @Override // org.eclipse.jetty.client.HttpEventListener
        public void onRetry() {
            HttpExchange.this.setRetryStatus(true);
            try {
                HttpExchange.this.onRetry();
            } catch (IOException e) {
                HttpExchange.LOG.debug(e);
            }
        }
    }

    @Deprecated
    /* loaded from: classes5.dex */
    public static class CachedExchange extends CachedExchange {
        public CachedExchange(boolean z) {
            super(z);
        }
    }
}
