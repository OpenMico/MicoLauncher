package okhttp3.internal.connection;

import com.xiaomi.onetrack.api.b;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealCall.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u009d\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007*\u0001)\u0018\u00002\u00020\u0001:\u0002`aB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010,\u001a\u00020-2\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010.\u001a\u00020-H\u0002J\b\u0010/\u001a\u00020-H\u0016J\b\u00100\u001a\u00020\u0000H\u0016J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0002J\u0010\u00105\u001a\u00020-2\u0006\u00106\u001a\u000207H\u0016J\u0016\u00108\u001a\u00020-2\u0006\u00109\u001a\u00020\u00052\u0006\u0010:\u001a\u00020\u0007J\b\u0010;\u001a\u00020<H\u0016J\u0015\u0010=\u001a\u00020-2\u0006\u0010>\u001a\u00020\u0007H\u0000¢\u0006\u0002\b?J\r\u0010@\u001a\u00020<H\u0000¢\u0006\u0002\bAJ\u0015\u0010B\u001a\u00020\u00192\u0006\u0010C\u001a\u00020DH\u0000¢\u0006\u0002\bEJ\b\u0010F\u001a\u00020\u0007H\u0016J\b\u0010G\u001a\u00020\u0007H\u0016J)\u0010H\u001a\u0002HI\"\n\b\u0000\u0010I*\u0004\u0018\u00010J2\u0006\u0010K\u001a\u0002HI2\u0006\u0010L\u001a\u00020\u0007H\u0002¢\u0006\u0002\u0010MJ;\u0010N\u001a\u0002HI\"\n\b\u0000\u0010I*\u0004\u0018\u00010J2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010O\u001a\u00020\u00072\u0006\u0010P\u001a\u00020\u00072\u0006\u0010K\u001a\u0002HIH\u0000¢\u0006\u0004\bQ\u0010RJ\u0019\u0010%\u001a\u0004\u0018\u00010J2\b\u0010K\u001a\u0004\u0018\u00010JH\u0000¢\u0006\u0002\bSJ\r\u0010T\u001a\u00020UH\u0000¢\u0006\u0002\bVJ\u000f\u0010W\u001a\u0004\u0018\u00010XH\u0000¢\u0006\u0002\bYJ\b\u00109\u001a\u00020\u0005H\u0016J\u0006\u0010Z\u001a\u00020\u0007J\b\u0010(\u001a\u00020[H\u0016J\u0006\u0010+\u001a\u00020-J!\u0010\\\u001a\u0002HI\"\n\b\u0000\u0010I*\u0004\u0018\u00010J2\u0006\u0010]\u001a\u0002HIH\u0002¢\u0006\u0002\u0010^J\b\u0010_\u001a\u00020UH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\"\u0010\"\u001a\u0004\u0018\u00010\u00192\b\u0010!\u001a\u0004\u0018\u00010\u0019@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010%\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0010\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0004\n\u0002\u0010*R\u000e\u0010+\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lokhttp3/internal/connection/RealCall;", "Lokhttp3/Call;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "(Lokhttp3/OkHttpClient;Lokhttp3/Request;Z)V", "callStackTrace", "", "canceled", "getClient", "()Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "setConnection", "(Lokhttp3/internal/connection/RealConnection;)V", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "eventListener", "Lokhttp3/EventListener;", "exchange", "Lokhttp3/internal/connection/Exchange;", "exchangeFinder", "Lokhttp3/internal/connection/ExchangeFinder;", "exchangeRequestDone", "exchangeResponseDone", "executed", "getForWebSocket", "()Z", "<set-?>", "interceptorScopedExchange", "getInterceptorScopedExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "noMoreExchanges", "getOriginalRequest", "()Lokhttp3/Request;", RtspHeaders.Values.TIMEOUT, "okhttp3/internal/connection/RealCall$timeout$1", "Lokhttp3/internal/connection/RealCall$timeout$1;", "timeoutEarlyExit", "acquireConnectionNoEvents", "", "callStart", "cancel", "clone", "createAddress", "Lokhttp3/Address;", "url", "Lokhttp3/HttpUrl;", "enqueue", "responseCallback", "Lokhttp3/Callback;", "enterNetworkInterceptorExchange", "request", "newExchangeFinder", "execute", "Lokhttp3/Response;", "exitNetworkInterceptorExchange", "closeExchange", "exitNetworkInterceptorExchange$okhttp", "getResponseWithInterceptorChain", "getResponseWithInterceptorChain$okhttp", "initExchange", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "initExchange$okhttp", "isCanceled", "isExecuted", "maybeReleaseConnection", "E", "Ljava/io/IOException;", "e", "force", "(Ljava/io/IOException;Z)Ljava/io/IOException;", "messageDone", "requestDone", "responseDone", "messageDone$okhttp", "(Lokhttp3/internal/connection/Exchange;ZZLjava/io/IOException;)Ljava/io/IOException;", "noMoreExchanges$okhttp", "redactedUrl", "", "redactedUrl$okhttp", "releaseConnectionNoEvents", "Ljava/net/Socket;", "releaseConnectionNoEvents$okhttp", "retryAfterFailure", "Lokio/AsyncTimeout;", "timeoutExit", "cause", "(Ljava/io/IOException;)Ljava/io/IOException;", "toLoggableString", "AsyncCall", "CallReference", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class RealCall implements Call {
    private Object callStackTrace;
    private boolean canceled;
    @NotNull
    private final OkHttpClient client;
    @Nullable
    private RealConnection connection;
    private final RealConnectionPool connectionPool;
    private final EventListener eventListener;
    private Exchange exchange;
    private ExchangeFinder exchangeFinder;
    private boolean exchangeRequestDone;
    private boolean exchangeResponseDone;
    private boolean executed;
    private final boolean forWebSocket;
    @Nullable
    private Exchange interceptorScopedExchange;
    private boolean noMoreExchanges;
    @NotNull
    private final Request originalRequest;
    private final RealCall$timeout$1 timeout;
    private boolean timeoutEarlyExit;

    /* JADX WARN: Type inference failed for: r2v7, types: [okhttp3.internal.connection.RealCall$timeout$1] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RealCall(@org.jetbrains.annotations.NotNull okhttp3.OkHttpClient r2, @org.jetbrains.annotations.NotNull okhttp3.Request r3, boolean r4) {
        /*
            r1 = this;
            java.lang.String r0 = "client"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r2, r0)
            java.lang.String r0 = "originalRequest"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r3, r0)
            r1.<init>()
            r1.client = r2
            r1.originalRequest = r3
            r1.forWebSocket = r4
            okhttp3.OkHttpClient r2 = r1.client
            okhttp3.ConnectionPool r2 = r2.connectionPool()
            okhttp3.internal.connection.RealConnectionPool r2 = r2.getDelegate$okhttp()
            r1.connectionPool = r2
            okhttp3.OkHttpClient r2 = r1.client
            okhttp3.EventListener$Factory r2 = r2.eventListenerFactory()
            r3 = r1
            okhttp3.Call r3 = (okhttp3.Call) r3
            okhttp3.EventListener r2 = r2.create(r3)
            r1.eventListener = r2
            okhttp3.internal.connection.RealCall$timeout$1 r2 = new okhttp3.internal.connection.RealCall$timeout$1
            r2.<init>()
            okhttp3.OkHttpClient r3 = r1.client
            int r3 = r3.callTimeoutMillis()
            long r3 = (long) r3
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS
            r2.timeout(r3, r0)
            r1.timeout = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealCall.<init>(okhttp3.OkHttpClient, okhttp3.Request, boolean):void");
    }

    @NotNull
    public final OkHttpClient getClient() {
        return this.client;
    }

    @NotNull
    public final Request getOriginalRequest() {
        return this.originalRequest;
    }

    public final boolean getForWebSocket() {
        return this.forWebSocket;
    }

    @Nullable
    public final RealConnection getConnection() {
        return this.connection;
    }

    public final void setConnection(@Nullable RealConnection realConnection) {
        this.connection = realConnection;
    }

    @Nullable
    public final Exchange getInterceptorScopedExchange$okhttp() {
        return this.interceptorScopedExchange;
    }

    @Override // okhttp3.Call
    @NotNull
    public AsyncTimeout timeout() {
        return this.timeout;
    }

    @Override // okhttp3.Call
    @NotNull
    public RealCall clone() {
        return new RealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    @Override // okhttp3.Call
    @NotNull
    public Request request() {
        return this.originalRequest;
    }

    @Override // okhttp3.Call
    public void cancel() {
        RealConnection realConnection;
        synchronized (this.connectionPool) {
            if (!this.canceled) {
                this.canceled = true;
                Exchange exchange = this.exchange;
                ExchangeFinder exchangeFinder = this.exchangeFinder;
                if (exchangeFinder == null || (realConnection = exchangeFinder.connectingConnection()) == null) {
                    realConnection = this.connection;
                }
                Unit unit = Unit.INSTANCE;
                if (exchange != null) {
                    exchange.cancel();
                } else if (realConnection != null) {
                    realConnection.cancel();
                }
                this.eventListener.canceled(this);
            }
        }
    }

    @Override // okhttp3.Call
    public boolean isCanceled() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.canceled;
        }
        return z;
    }

    @Override // okhttp3.Call
    @NotNull
    public Response execute() {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IllegalStateException("Already Executed".toString());
            }
        }
        enter();
        callStart();
        try {
            this.client.dispatcher().executed$okhttp(this);
            return getResponseWithInterceptorChain$okhttp();
        } finally {
            this.client.dispatcher().finished$okhttp(this);
        }
    }

    @Override // okhttp3.Call
    public void enqueue(@NotNull Callback responseCallback) {
        Intrinsics.checkParameterIsNotNull(responseCallback, "responseCallback");
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
                Unit unit = Unit.INSTANCE;
            } else {
                throw new IllegalStateException("Already Executed".toString());
            }
        }
        callStart();
        this.client.dispatcher().enqueue$okhttp(new AsyncCall(this, responseCallback));
    }

    @Override // okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    private final void callStart() {
        this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
        this.eventListener.callStart(this);
    }

    @NotNull
    public final Response getResponseWithInterceptorChain$okhttp() throws IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addAll(arrayList2, this.client.interceptors());
        arrayList2.add(new RetryAndFollowUpInterceptor(this.client));
        arrayList2.add(new BridgeInterceptor(this.client.cookieJar()));
        arrayList2.add(new CacheInterceptor(this.client.cache()));
        arrayList2.add(ConnectInterceptor.INSTANCE);
        if (!this.forWebSocket) {
            CollectionsKt.addAll(arrayList2, this.client.networkInterceptors());
        }
        arrayList2.add(new CallServerInterceptor(this.forWebSocket));
        boolean z = false;
        try {
            try {
                Response proceed = new RealInterceptorChain(this, arrayList, 0, null, this.originalRequest, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis()).proceed(this.originalRequest);
                z = isCanceled();
                if (!z) {
                    return proceed;
                }
                Util.closeQuietly(proceed);
                throw new IOException("Canceled");
            } catch (IOException e) {
                IOException noMoreExchanges$okhttp = noMoreExchanges$okhttp(e);
                if (noMoreExchanges$okhttp == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
                }
                throw noMoreExchanges$okhttp;
            }
        } finally {
            if (!z) {
                noMoreExchanges$okhttp(null);
            }
        }
    }

    public final void enterNetworkInterceptorExchange(@NotNull Request request, boolean z) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        boolean z2 = true;
        if (this.interceptorScopedExchange == null) {
            if (this.exchange != null) {
                z2 = false;
            }
            if (!z2) {
                throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString());
            } else if (z) {
                this.exchangeFinder = new ExchangeFinder(this.connectionPool, createAddress(request.url()), this, this.eventListener);
            }
        } else {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    @NotNull
    public final Exchange initExchange$okhttp(@NotNull RealInterceptorChain chain) {
        Intrinsics.checkParameterIsNotNull(chain, "chain");
        synchronized (this.connectionPool) {
            boolean z = true;
            if (!this.noMoreExchanges) {
                if (this.exchange != null) {
                    z = false;
                }
                if (z) {
                    Unit unit = Unit.INSTANCE;
                } else {
                    throw new IllegalStateException("Check failed.".toString());
                }
            } else {
                throw new IllegalStateException("released".toString());
            }
        }
        ExchangeFinder exchangeFinder = this.exchangeFinder;
        if (exchangeFinder == null) {
            Intrinsics.throwNpe();
        }
        ExchangeCodec find = exchangeFinder.find(this.client, chain);
        EventListener eventListener = this.eventListener;
        ExchangeFinder exchangeFinder2 = this.exchangeFinder;
        if (exchangeFinder2 == null) {
            Intrinsics.throwNpe();
        }
        Exchange exchange = new Exchange(this, eventListener, exchangeFinder2, find);
        this.interceptorScopedExchange = exchange;
        synchronized (this.connectionPool) {
            this.exchange = exchange;
            this.exchangeRequestDone = false;
            this.exchangeResponseDone = false;
        }
        return exchange;
    }

    public final void acquireConnectionNoEvents(@NotNull RealConnection connection) {
        Intrinsics.checkParameterIsNotNull(connection, "connection");
        RealConnectionPool realConnectionPool = this.connectionPool;
        if (!Util.assertionsEnabled || Thread.holdsLock(realConnectionPool)) {
            if (this.connection == null) {
                this.connection = connection;
                connection.getCalls().add(new CallReference(this, this.callStackTrace));
                return;
            }
            throw new IllegalStateException("Check failed.".toString());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(realConnectionPool);
        throw new AssertionError(sb.toString());
    }

    public final <E extends IOException> E messageDone$okhttp(@NotNull Exchange exchange, boolean z, boolean z2, E e) {
        boolean z3;
        Intrinsics.checkParameterIsNotNull(exchange, "exchange");
        synchronized (this.connectionPool) {
            boolean z4 = true;
            if (!Intrinsics.areEqual(exchange, this.exchange)) {
                return e;
            }
            if (z) {
                z3 = !this.exchangeRequestDone;
                this.exchangeRequestDone = true;
            } else {
                z3 = false;
            }
            if (z2) {
                if (!this.exchangeResponseDone) {
                    z3 = true;
                }
                this.exchangeResponseDone = true;
            }
            if (!this.exchangeRequestDone || !this.exchangeResponseDone || !z3) {
                z4 = false;
            } else {
                Exchange exchange2 = this.exchange;
                if (exchange2 == null) {
                    Intrinsics.throwNpe();
                }
                RealConnection connection$okhttp = exchange2.getConnection$okhttp();
                connection$okhttp.setSuccessCount$okhttp(connection$okhttp.getSuccessCount$okhttp() + 1);
                this.exchange = null;
            }
            Unit unit = Unit.INSTANCE;
            return z4 ? (E) maybeReleaseConnection(e, false) : e;
        }
    }

    @Nullable
    public final IOException noMoreExchanges$okhttp(@Nullable IOException iOException) {
        synchronized (this.connectionPool) {
            this.noMoreExchanges = true;
            Unit unit = Unit.INSTANCE;
        }
        return maybeReleaseConnection(iOException, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0019 A[Catch: all -> 0x0013, TryCatch #0 {all -> 0x0013, blocks: (B:6:0x000c, B:14:0x0019, B:16:0x0024, B:19:0x002a, B:21:0x002e, B:23:0x0034, B:25:0x0038, B:26:0x003c, B:28:0x0040, B:32:0x0047, B:53:0x0088, B:54:0x0095), top: B:56:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0088 A[Catch: all -> 0x0013, TRY_ENTER, TryCatch #0 {all -> 0x0013, blocks: (B:6:0x000c, B:14:0x0019, B:16:0x0024, B:19:0x002a, B:21:0x002e, B:23:0x0034, B:25:0x0038, B:26:0x003c, B:28:0x0040, B:32:0x0047, B:53:0x0088, B:54:0x0095), top: B:56:0x000c }] */
    /* JADX WARN: Type inference failed for: r4v4, types: [T, okhttp3.Connection] */
    /* JADX WARN: Type inference failed for: r5v2, types: [T, okhttp3.Connection] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final <E extends java.io.IOException> E maybeReleaseConnection(E r7, boolean r8) {
        /*
            r6 = this;
            kotlin.jvm.internal.Ref$ObjectRef r0 = new kotlin.jvm.internal.Ref$ObjectRef
            r0.<init>()
            okhttp3.internal.connection.RealConnectionPool r1 = r6.connectionPool
            monitor-enter(r1)
            r2 = 0
            r3 = 1
            if (r8 == 0) goto L_0x0016
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch: all -> 0x0013
            if (r4 != 0) goto L_0x0011
            goto L_0x0016
        L_0x0011:
            r4 = r2
            goto L_0x0017
        L_0x0013:
            r7 = move-exception
            goto L_0x0096
        L_0x0016:
            r4 = r3
        L_0x0017:
            if (r4 == 0) goto L_0x0088
            okhttp3.internal.connection.RealConnection r4 = r6.connection     // Catch: all -> 0x0013
            okhttp3.Connection r4 = (okhttp3.Connection) r4     // Catch: all -> 0x0013
            r0.element = r4     // Catch: all -> 0x0013
            okhttp3.internal.connection.RealConnection r4 = r6.connection     // Catch: all -> 0x0013
            r5 = 0
            if (r4 == 0) goto L_0x0033
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch: all -> 0x0013
            if (r4 != 0) goto L_0x0033
            if (r8 != 0) goto L_0x002e
            boolean r8 = r6.noMoreExchanges     // Catch: all -> 0x0013
            if (r8 == 0) goto L_0x0033
        L_0x002e:
            java.net.Socket r8 = r6.releaseConnectionNoEvents$okhttp()     // Catch: all -> 0x0013
            goto L_0x0034
        L_0x0033:
            r8 = r5
        L_0x0034:
            okhttp3.internal.connection.RealConnection r4 = r6.connection     // Catch: all -> 0x0013
            if (r4 == 0) goto L_0x003c
            okhttp3.Connection r5 = (okhttp3.Connection) r5     // Catch: all -> 0x0013
            r0.element = r5     // Catch: all -> 0x0013
        L_0x003c:
            boolean r4 = r6.noMoreExchanges     // Catch: all -> 0x0013
            if (r4 == 0) goto L_0x0046
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch: all -> 0x0013
            if (r4 != 0) goto L_0x0046
            r4 = r3
            goto L_0x0047
        L_0x0046:
            r4 = r2
        L_0x0047:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: all -> 0x0013
            monitor-exit(r1)
            if (r8 == 0) goto L_0x004f
            okhttp3.internal.Util.closeQuietly(r8)
        L_0x004f:
            T r8 = r0.element
            okhttp3.Connection r8 = (okhttp3.Connection) r8
            if (r8 == 0) goto L_0x0066
            okhttp3.EventListener r8 = r6.eventListener
            r1 = r6
            okhttp3.Call r1 = (okhttp3.Call) r1
            T r0 = r0.element
            okhttp3.Connection r0 = (okhttp3.Connection) r0
            if (r0 != 0) goto L_0x0063
            kotlin.jvm.internal.Intrinsics.throwNpe()
        L_0x0063:
            r8.connectionReleased(r1, r0)
        L_0x0066:
            if (r4 == 0) goto L_0x0087
            if (r7 == 0) goto L_0x006b
            r2 = r3
        L_0x006b:
            java.io.IOException r7 = r6.timeoutExit(r7)
            if (r2 == 0) goto L_0x007f
            okhttp3.EventListener r8 = r6.eventListener
            r0 = r6
            okhttp3.Call r0 = (okhttp3.Call) r0
            if (r7 != 0) goto L_0x007b
            kotlin.jvm.internal.Intrinsics.throwNpe()
        L_0x007b:
            r8.callFailed(r0, r7)
            goto L_0x0087
        L_0x007f:
            okhttp3.EventListener r8 = r6.eventListener
            r0 = r6
            okhttp3.Call r0 = (okhttp3.Call) r0
            r8.callEnd(r0)
        L_0x0087:
            return r7
        L_0x0088:
            java.lang.String r7 = "cannot release connection while it is in use"
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch: all -> 0x0013
            java.lang.String r7 = r7.toString()     // Catch: all -> 0x0013
            r8.<init>(r7)     // Catch: all -> 0x0013
            java.lang.Throwable r8 = (java.lang.Throwable) r8     // Catch: all -> 0x0013
            throw r8     // Catch: all -> 0x0013
        L_0x0096:
            monitor-exit(r1)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.RealCall.maybeReleaseConnection(java.io.IOException, boolean):java.io.IOException");
    }

    @Nullable
    public final Socket releaseConnectionNoEvents$okhttp() {
        RealConnectionPool realConnectionPool = this.connectionPool;
        if (!Util.assertionsEnabled || Thread.holdsLock(realConnectionPool)) {
            RealConnection realConnection = this.connection;
            if (realConnection == null) {
                Intrinsics.throwNpe();
            }
            Iterator<Reference<RealCall>> it = realConnection.getCalls().iterator();
            boolean z = false;
            int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                } else if (Intrinsics.areEqual(it.next().get(), this)) {
                    break;
                } else {
                    i++;
                }
            }
            if (i != -1) {
                z = true;
            }
            if (z) {
                RealConnection realConnection2 = this.connection;
                if (realConnection2 == null) {
                    Intrinsics.throwNpe();
                }
                realConnection2.getCalls().remove(i);
                this.connection = null;
                if (realConnection2.getCalls().isEmpty()) {
                    realConnection2.setIdleAtNs$okhttp(System.nanoTime());
                    if (this.connectionPool.connectionBecameIdle(realConnection2)) {
                        return realConnection2.socket();
                    }
                }
                return null;
            }
            throw new IllegalStateException("Check failed.".toString());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(realConnectionPool);
        throw new AssertionError(sb.toString());
    }

    private final <E extends IOException> E timeoutExit(E e) {
        if (this.timeoutEarlyExit || !exit()) {
            return e;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException(RtspHeaders.Values.TIMEOUT);
        if (e != null) {
            interruptedIOException.initCause(e);
        }
        return interruptedIOException;
    }

    public final void timeoutEarlyExit() {
        if (!this.timeoutEarlyExit) {
            this.timeoutEarlyExit = true;
            exit();
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final void exitNetworkInterceptorExchange$okhttp(boolean z) {
        boolean z2 = true;
        if (!this.noMoreExchanges) {
            if (z) {
                Exchange exchange = this.exchange;
                if (exchange != null) {
                    exchange.detachWithViolence();
                }
                if (this.exchange != null) {
                    z2 = false;
                }
                if (!z2) {
                    throw new IllegalStateException("Check failed.".toString());
                }
            }
            this.interceptorScopedExchange = null;
            return;
        }
        throw new IllegalStateException("released".toString());
    }

    private final Address createAddress(HttpUrl httpUrl) {
        CertificatePinner certificatePinner;
        HostnameVerifier hostnameVerifier;
        SSLSocketFactory sSLSocketFactory;
        SSLSocketFactory sSLSocketFactory2 = null;
        HostnameVerifier hostnameVerifier2 = null;
        CertificatePinner certificatePinner2 = null;
        if (httpUrl.isHttps()) {
            sSLSocketFactory = this.client.sslSocketFactory();
            hostnameVerifier = this.client.hostnameVerifier();
            certificatePinner = this.client.certificatePinner();
        } else {
            certificatePinner = certificatePinner2;
            sSLSocketFactory = sSLSocketFactory2;
            hostnameVerifier = hostnameVerifier2;
        }
        return new Address(httpUrl.host(), httpUrl.port(), this.client.dns(), this.client.socketFactory(), sSLSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
    }

    public final boolean retryAfterFailure() {
        ExchangeFinder exchangeFinder = this.exchangeFinder;
        if (exchangeFinder == null) {
            Intrinsics.throwNpe();
        }
        return exchangeFinder.retryAfterFailure();
    }

    public final String toLoggableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "canceled " : "");
        sb.append(this.forWebSocket ? "web socket" : "call");
        sb.append(" to ");
        sb.append(redactedUrl$okhttp());
        return sb.toString();
    }

    @NotNull
    public final String redactedUrl$okhttp() {
        return this.originalRequest.url().redact();
    }

    /* compiled from: RealCall.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0080\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0012\u0010\u001a\u001a\u00020\u00172\n\u0010\u001b\u001a\u00060\u0000R\u00020\u0006J\b\u0010\u001c\u001a\u00020\u0017H\u0016R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lokhttp3/internal/connection/RealCall$AsyncCall;", "Ljava/lang/Runnable;", "responseCallback", "Lokhttp3/Callback;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/Callback;)V", "call", "Lokhttp3/internal/connection/RealCall;", "getCall", "()Lokhttp3/internal/connection/RealCall;", "<set-?>", "Ljava/util/concurrent/atomic/AtomicInteger;", "callsPerHost", "getCallsPerHost", "()Ljava/util/concurrent/atomic/AtomicInteger;", b.E, "", "getHost", "()Ljava/lang/String;", "request", "Lokhttp3/Request;", "getRequest", "()Lokhttp3/Request;", "executeOn", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "reuseCallsPerHostFrom", "other", "run", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public final class AsyncCall implements Runnable {
        @NotNull
        private volatile AtomicInteger callsPerHost = new AtomicInteger(0);
        private final Callback responseCallback;
        final /* synthetic */ RealCall this$0;

        public AsyncCall(RealCall realCall, @NotNull Callback responseCallback) {
            Intrinsics.checkParameterIsNotNull(responseCallback, "responseCallback");
            this.this$0 = realCall;
            this.responseCallback = responseCallback;
        }

        @NotNull
        public final AtomicInteger getCallsPerHost() {
            return this.callsPerHost;
        }

        public final void reuseCallsPerHostFrom(@NotNull AsyncCall other) {
            Intrinsics.checkParameterIsNotNull(other, "other");
            this.callsPerHost = other.callsPerHost;
        }

        @NotNull
        public final String getHost() {
            return this.this$0.getOriginalRequest().url().host();
        }

        @NotNull
        public final Request getRequest() {
            return this.this$0.getOriginalRequest();
        }

        @NotNull
        public final RealCall getCall() {
            return this.this$0;
        }

        public final void executeOn(@NotNull ExecutorService executorService) {
            Intrinsics.checkParameterIsNotNull(executorService, "executorService");
            Dispatcher dispatcher = this.this$0.getClient().dispatcher();
            if (!Util.assertionsEnabled || !Thread.holdsLock(dispatcher)) {
                try {
                    try {
                        executorService.execute(this);
                    } catch (RejectedExecutionException e) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                        interruptedIOException.initCause(e);
                        this.this$0.noMoreExchanges$okhttp(interruptedIOException);
                        this.responseCallback.onFailure(this.this$0, interruptedIOException);
                        this.this$0.getClient().dispatcher().finished$okhttp(this);
                    }
                } catch (Throwable th) {
                    this.this$0.getClient().dispatcher().finished$okhttp(this);
                    throw th;
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Thread ");
                Thread currentThread = Thread.currentThread();
                Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
                sb.append(currentThread.getName());
                sb.append(" MUST NOT hold lock on ");
                sb.append(dispatcher);
                throw new AssertionError(sb.toString());
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            IOException e;
            Dispatcher dispatcher;
            Throwable th;
            Thread currentThread = Thread.currentThread();
            Intrinsics.checkExpressionValueIsNotNull(currentThread, "currentThread");
            String name = currentThread.getName();
            currentThread.setName("OkHttp " + this.this$0.redactedUrl$okhttp());
            try {
                enter();
                try {
                    z = true;
                } catch (IOException e2) {
                    e = e2;
                    z = false;
                } catch (Throwable th2) {
                    th = th2;
                    z = false;
                }
                try {
                    this.responseCallback.onResponse(this.this$0, this.this$0.getResponseWithInterceptorChain$okhttp());
                    dispatcher = this.this$0.getClient().dispatcher();
                } catch (IOException e3) {
                    e = e3;
                    if (z) {
                        Platform.Companion.get().log("Callback failure for " + this.this$0.toLoggableString(), 4, e);
                    } else {
                        this.responseCallback.onFailure(this.this$0, e);
                    }
                    dispatcher = this.this$0.getClient().dispatcher();
                    dispatcher.finished$okhttp(this);
                } catch (Throwable th3) {
                    th = th3;
                    this.this$0.cancel();
                    if (!z) {
                        IOException iOException = new IOException("canceled due to " + th);
                        iOException.addSuppressed(th);
                        this.responseCallback.onFailure(this.this$0, iOException);
                    }
                    throw th;
                }
                dispatcher.finished$okhttp(this);
            } finally {
                currentThread.setName(name);
            }
        }
    }

    /* compiled from: RealCall.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lokhttp3/internal/connection/RealCall$CallReference;", "Ljava/lang/ref/WeakReference;", "Lokhttp3/internal/connection/RealCall;", "referent", "callStackTrace", "", "(Lokhttp3/internal/connection/RealCall;Ljava/lang/Object;)V", "getCallStackTrace", "()Ljava/lang/Object;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class CallReference extends WeakReference<RealCall> {
        @Nullable
        private final Object callStackTrace;

        @Nullable
        public final Object getCallStackTrace() {
            return this.callStackTrace;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CallReference(@NotNull RealCall referent, @Nullable Object obj) {
            super(referent);
            Intrinsics.checkParameterIsNotNull(referent, "referent");
            this.callStackTrace = obj;
        }
    }
}
