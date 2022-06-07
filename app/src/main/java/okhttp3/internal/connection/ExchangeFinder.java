package okhttp3.internal.connection;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteSelector;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExchangeFinder.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ0\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020%H\u0002J8\u0010&\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u00102\u0006\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020%2\u0006\u0010'\u001a\u00020%H\u0002J\u0006\u0010(\u001a\u00020%J\b\u0010)\u001a\u00020%H\u0002J\u000e\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lokhttp3/internal/connection/ExchangeFinder;", "", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;)V", "getAddress$okhttp", "()Lokhttp3/Address;", "connectingConnection", "Lokhttp3/internal/connection/RealConnection;", "connectionShutdownCount", "", "nextRouteToTry", "Lokhttp3/Route;", "otherFailureCount", "refusedStreamCount", "routeSelection", "Lokhttp3/internal/connection/RouteSelector$Selection;", "routeSelector", "Lokhttp3/internal/connection/RouteSelector;", "find", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "findConnection", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "", "findHealthyConnection", "doExtensiveHealthChecks", "retryAfterFailure", "retryCurrentRoute", "sameHostAndPort", "url", "Lokhttp3/HttpUrl;", "trackFailure", "", "e", "Ljava/io/IOException;", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class ExchangeFinder {
    @NotNull
    private final Address address;
    private final RealCall call;
    private RealConnection connectingConnection;
    private final RealConnectionPool connectionPool;
    private int connectionShutdownCount;
    private final EventListener eventListener;
    private Route nextRouteToTry;
    private int otherFailureCount;
    private int refusedStreamCount;
    private RouteSelector.Selection routeSelection;
    private RouteSelector routeSelector;

    public ExchangeFinder(@NotNull RealConnectionPool connectionPool, @NotNull Address address, @NotNull RealCall call, @NotNull EventListener eventListener) {
        Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(call, "call");
        Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
        this.connectionPool = connectionPool;
        this.address = address;
        this.call = call;
        this.eventListener = eventListener;
    }

    @NotNull
    public final Address getAddress$okhttp() {
        return this.address;
    }

    @NotNull
    public final ExchangeCodec find(@NotNull OkHttpClient client, @NotNull RealInterceptorChain chain) {
        Intrinsics.checkParameterIsNotNull(client, "client");
        Intrinsics.checkParameterIsNotNull(chain, "chain");
        try {
            return findHealthyConnection(chain.getConnectTimeoutMillis$okhttp(), chain.getReadTimeoutMillis$okhttp(), chain.getWriteTimeoutMillis$okhttp(), client.pingIntervalMillis(), client.retryOnConnectionFailure(), !Intrinsics.areEqual(chain.getRequest$okhttp().method(), "GET")).newCodec$okhttp(client, chain);
        } catch (IOException e) {
            trackFailure(e);
            throw new RouteException(e);
        } catch (RouteException e2) {
            trackFailure(e2.getLastConnectException());
            throw e2;
        }
    }

    private final RealConnection findHealthyConnection(int i, int i2, int i3, int i4, boolean z, boolean z2) throws IOException {
        while (true) {
            RealConnection findConnection = findConnection(i, i2, i3, i4, z);
            if (findConnection.isHealthy(z2)) {
                return findConnection;
            }
            findConnection.noNewExchanges();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d1, code lost:
        if (r4.hasNext() == false) goto L_0x00d3;
     */
    /* JADX WARN: Removed duplicated region for block: B:132:0x00ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v3, types: [T, okhttp3.internal.connection.RealConnection] */
    /* JADX WARN: Type inference failed for: r7v4, types: [T, okhttp3.internal.connection.RealConnection] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final okhttp3.internal.connection.RealConnection findConnection(int r19, int r20, int r21, int r22, boolean r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.ExchangeFinder.findConnection(int, int, int, int, boolean):okhttp3.internal.connection.RealConnection");
    }

    @Nullable
    public final RealConnection connectingConnection() {
        RealConnectionPool realConnectionPool = this.connectionPool;
        if (!Util.assertionsEnabled || Thread.holdsLock(realConnectionPool)) {
            return this.connectingConnection;
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

    public final void trackFailure(@NotNull IOException e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        RealConnectionPool realConnectionPool = this.connectionPool;
        if (!Util.assertionsEnabled || !Thread.holdsLock(realConnectionPool)) {
            synchronized (this.connectionPool) {
                this.nextRouteToTry = null;
                if ((e instanceof StreamResetException) && ((StreamResetException) e).errorCode == ErrorCode.REFUSED_STREAM) {
                    this.refusedStreamCount++;
                } else if (e instanceof ConnectionShutdownException) {
                    this.connectionShutdownCount++;
                } else {
                    this.otherFailureCount++;
                }
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        Intrinsics.checkExpressionValueIsNotNull(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(realConnectionPool);
        throw new AssertionError(sb.toString());
    }

    public final boolean retryAfterFailure() {
        synchronized (this.connectionPool) {
            if (this.refusedStreamCount == 0 && this.connectionShutdownCount == 0 && this.otherFailureCount == 0) {
                return false;
            }
            if (this.nextRouteToTry != null) {
                return true;
            }
            if (retryCurrentRoute()) {
                RealConnection connection = this.call.getConnection();
                if (connection == null) {
                    Intrinsics.throwNpe();
                }
                this.nextRouteToTry = connection.route();
                return true;
            }
            RouteSelector.Selection selection = this.routeSelection;
            if (selection != null && selection.hasNext()) {
                return true;
            }
            RouteSelector routeSelector = this.routeSelector;
            if (routeSelector == null) {
                return true;
            }
            return routeSelector.hasNext();
        }
    }

    private final boolean retryCurrentRoute() {
        RealConnection connection;
        return this.refusedStreamCount <= 1 && this.connectionShutdownCount <= 1 && this.otherFailureCount <= 0 && (connection = this.call.getConnection()) != null && connection.getRouteFailureCount$okhttp() == 0 && Util.canReuseConnectionFor(connection.route().address().url(), this.address.url());
    }

    public final boolean sameHostAndPort(@NotNull HttpUrl url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        HttpUrl url2 = this.address.url();
        return url.port() == url2.port() && Intrinsics.areEqual(url.host(), url2.host());
    }
}
