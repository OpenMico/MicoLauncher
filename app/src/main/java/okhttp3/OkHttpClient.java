package okhttp3;

import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.api.b;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Interceptor;
import okhttp3.WebSocket;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OkHttpClient.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0016\u0018\u0000 t2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002stB\u0007\b\u0016¢\u0006\u0002\u0010\u0004B\u000f\b\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\bPJ\u000f\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007¢\u0006\u0002\bQJ\r\u0010\u000e\u001a\u00020\u000fH\u0007¢\u0006\u0002\bRJ\r\u0010\u0014\u001a\u00020\u0015H\u0007¢\u0006\u0002\bSJ\r\u0010\u0017\u001a\u00020\u000fH\u0007¢\u0006\u0002\bTJ\r\u0010\u0018\u001a\u00020\u0019H\u0007¢\u0006\u0002\bUJ\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0007¢\u0006\u0002\bVJ\r\u0010\u001f\u001a\u00020 H\u0007¢\u0006\u0002\bWJ\r\u0010\"\u001a\u00020#H\u0007¢\u0006\u0002\bXJ\r\u0010%\u001a\u00020&H\u0007¢\u0006\u0002\bYJ\r\u0010(\u001a\u00020)H\u0007¢\u0006\u0002\bZJ\r\u0010+\u001a\u00020,H\u0007¢\u0006\u0002\b[J\r\u0010.\u001a\u00020,H\u0007¢\u0006\u0002\b\\J\r\u0010/\u001a\u000200H\u0007¢\u0006\u0002\b]J\u0013\u00102\u001a\b\u0012\u0004\u0012\u0002030\u001cH\u0007¢\u0006\u0002\b^J\u0013\u00104\u001a\b\u0012\u0004\u0012\u0002030\u001cH\u0007¢\u0006\u0002\b_J\b\u0010`\u001a\u00020\u0006H\u0016J\u0010\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020dH\u0016J\u0018\u0010e\u001a\u00020f2\u0006\u0010c\u001a\u00020d2\u0006\u0010g\u001a\u00020hH\u0016J\r\u00105\u001a\u00020\u000fH\u0007¢\u0006\u0002\biJ\u0013\u00106\u001a\b\u0012\u0004\u0012\u0002070\u001cH\u0007¢\u0006\u0002\bjJ\u000f\u00108\u001a\u0004\u0018\u000109H\u0007¢\u0006\u0002\bkJ\r\u0010;\u001a\u00020\tH\u0007¢\u0006\u0002\blJ\r\u0010<\u001a\u00020=H\u0007¢\u0006\u0002\bmJ\r\u0010?\u001a\u00020\u000fH\u0007¢\u0006\u0002\bnJ\r\u0010@\u001a\u00020,H\u0007¢\u0006\u0002\boJ\r\u0010E\u001a\u00020FH\u0007¢\u0006\u0002\bpJ\r\u0010H\u001a\u00020IH\u0007¢\u0006\u0002\bqJ\r\u0010L\u001a\u00020\u000fH\u0007¢\u0006\u0002\brR\u0013\u0010\b\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\nR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0013\u0010\u000e\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u00128G¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0013R\u0013\u0010\u0014\u001a\u00020\u00158G¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0013\u0010\u0018\u001a\u00020\u00198G¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u001aR\u0019\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001eR\u0013\u0010\u001f\u001a\u00020 8G¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010!R\u0013\u0010\"\u001a\u00020#8G¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010$R\u0013\u0010%\u001a\u00020&8G¢\u0006\b\n\u0000\u001a\u0004\b%\u0010'R\u0013\u0010(\u001a\u00020)8G¢\u0006\b\n\u0000\u001a\u0004\b(\u0010*R\u0013\u0010+\u001a\u00020,8G¢\u0006\b\n\u0000\u001a\u0004\b+\u0010-R\u0013\u0010.\u001a\u00020,8G¢\u0006\b\n\u0000\u001a\u0004\b.\u0010-R\u0013\u0010/\u001a\u0002008G¢\u0006\b\n\u0000\u001a\u0004\b/\u00101R\u0019\u00102\u001a\b\u0012\u0004\u0012\u0002030\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u001eR\u0019\u00104\u001a\b\u0012\u0004\u0012\u0002030\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b4\u0010\u001eR\u0013\u00105\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u0010R\u0019\u00106\u001a\b\u0012\u0004\u0012\u0002070\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b6\u0010\u001eR\u0015\u00108\u001a\u0004\u0018\u0001098G¢\u0006\b\n\u0000\u001a\u0004\b8\u0010:R\u0013\u0010;\u001a\u00020\t8G¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\nR\u0013\u0010<\u001a\u00020=8G¢\u0006\b\n\u0000\u001a\u0004\b<\u0010>R\u0013\u0010?\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\b?\u0010\u0010R\u0013\u0010@\u001a\u00020,8G¢\u0006\b\n\u0000\u001a\u0004\b@\u0010-R\u0011\u0010A\u001a\u00020B¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0013\u0010E\u001a\u00020F8G¢\u0006\b\n\u0000\u001a\u0004\bE\u0010GR\u0011\u0010H\u001a\u00020I8G¢\u0006\u0006\u001a\u0004\bH\u0010JR\u0010\u0010K\u001a\u0004\u0018\u00010IX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010L\u001a\u00020\u000f8G¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\u0010R\u0015\u0010M\u001a\u0004\u0018\u00010N8G¢\u0006\b\n\u0000\u001a\u0004\bM\u0010O¨\u0006u"}, d2 = {"Lokhttp3/OkHttpClient;", "", "Lokhttp3/Call$Factory;", "Lokhttp3/WebSocket$Factory;", "()V", "builder", "Lokhttp3/OkHttpClient$Builder;", "(Lokhttp3/OkHttpClient$Builder;)V", "authenticator", "Lokhttp3/Authenticator;", "()Lokhttp3/Authenticator;", "cache", "Lokhttp3/Cache;", "()Lokhttp3/Cache;", "callTimeoutMillis", "", "()I", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "certificatePinner", "Lokhttp3/CertificatePinner;", "()Lokhttp3/CertificatePinner;", "connectTimeoutMillis", "connectionPool", "Lokhttp3/ConnectionPool;", "()Lokhttp3/ConnectionPool;", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "()Ljava/util/List;", "cookieJar", "Lokhttp3/CookieJar;", "()Lokhttp3/CookieJar;", "dispatcher", "Lokhttp3/Dispatcher;", "()Lokhttp3/Dispatcher;", b.P, "Lokhttp3/Dns;", "()Lokhttp3/Dns;", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "()Lokhttp3/EventListener$Factory;", "followRedirects", "", "()Z", "followSslRedirects", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "()Ljavax/net/ssl/HostnameVerifier;", "interceptors", "Lokhttp3/Interceptor;", "networkInterceptors", "pingIntervalMillis", "protocols", "Lokhttp3/Protocol;", "proxy", "Ljava/net/Proxy;", "()Ljava/net/Proxy;", "proxyAuthenticator", "proxySelector", "Ljava/net/ProxySelector;", "()Ljava/net/ProxySelector;", "readTimeoutMillis", "retryOnConnectionFailure", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase", "()Lokhttp3/internal/connection/RouteDatabase;", "socketFactory", "Ljavax/net/SocketFactory;", "()Ljavax/net/SocketFactory;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "()Ljavax/net/ssl/SSLSocketFactory;", "sslSocketFactoryOrNull", "writeTimeoutMillis", "x509TrustManager", "Ljavax/net/ssl/X509TrustManager;", "()Ljavax/net/ssl/X509TrustManager;", "-deprecated_authenticator", "-deprecated_cache", "-deprecated_callTimeoutMillis", "-deprecated_certificatePinner", "-deprecated_connectTimeoutMillis", "-deprecated_connectionPool", "-deprecated_connectionSpecs", "-deprecated_cookieJar", "-deprecated_dispatcher", "-deprecated_dns", "-deprecated_eventListenerFactory", "-deprecated_followRedirects", "-deprecated_followSslRedirects", "-deprecated_hostnameVerifier", "-deprecated_interceptors", "-deprecated_networkInterceptors", "newBuilder", "newCall", "Lokhttp3/Call;", "request", "Lokhttp3/Request;", "newWebSocket", "Lokhttp3/WebSocket;", "listener", "Lokhttp3/WebSocketListener;", "-deprecated_pingIntervalMillis", "-deprecated_protocols", "-deprecated_proxy", "-deprecated_proxyAuthenticator", "-deprecated_proxySelector", "-deprecated_readTimeoutMillis", "-deprecated_retryOnConnectionFailure", "-deprecated_socketFactory", "-deprecated_sslSocketFactory", "-deprecated_writeTimeoutMillis", "Builder", "Companion", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public class OkHttpClient implements Cloneable, Call.Factory, WebSocket.Factory {
    @NotNull
    private final Authenticator authenticator;
    @Nullable
    private final Cache cache;
    private final int callTimeoutMillis;
    @Nullable
    private final CertificateChainCleaner certificateChainCleaner;
    @NotNull
    private final CertificatePinner certificatePinner;
    private final int connectTimeoutMillis;
    @NotNull
    private final ConnectionPool connectionPool;
    @NotNull
    private final List<ConnectionSpec> connectionSpecs;
    @NotNull
    private final CookieJar cookieJar;
    @NotNull
    private final Dispatcher dispatcher;
    @NotNull
    private final Dns dns;
    @NotNull
    private final EventListener.Factory eventListenerFactory;
    private final boolean followRedirects;
    private final boolean followSslRedirects;
    @NotNull
    private final HostnameVerifier hostnameVerifier;
    @NotNull
    private final List<Interceptor> interceptors;
    @NotNull
    private final List<Interceptor> networkInterceptors;
    private final int pingIntervalMillis;
    @NotNull
    private final List<Protocol> protocols;
    @Nullable
    private final Proxy proxy;
    @NotNull
    private final Authenticator proxyAuthenticator;
    @NotNull
    private final ProxySelector proxySelector;
    private final int readTimeoutMillis;
    private final boolean retryOnConnectionFailure;
    @NotNull
    private final RouteDatabase routeDatabase;
    @NotNull
    private final SocketFactory socketFactory;
    private final SSLSocketFactory sslSocketFactoryOrNull;
    private final int writeTimeoutMillis;
    @Nullable
    private final X509TrustManager x509TrustManager;
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableListOf(Protocol.HTTP_2, Protocol.HTTP_1_1);
    @NotNull
    private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS = Util.immutableListOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT);

    /* JADX WARN: Removed duplicated region for block: B:38:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public OkHttpClient(@org.jetbrains.annotations.NotNull okhttp3.OkHttpClient.Builder r4) {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.OkHttpClient.<init>(okhttp3.OkHttpClient$Builder):void");
    }

    @NotNull
    public Object clone() {
        return super.clone();
    }

    @JvmName(name = "dispatcher")
    @NotNull
    public final Dispatcher dispatcher() {
        return this.dispatcher;
    }

    @JvmName(name = "connectionPool")
    @NotNull
    public final ConnectionPool connectionPool() {
        return this.connectionPool;
    }

    @JvmName(name = "interceptors")
    @NotNull
    public final List<Interceptor> interceptors() {
        return this.interceptors;
    }

    @JvmName(name = "networkInterceptors")
    @NotNull
    public final List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    @JvmName(name = "eventListenerFactory")
    @NotNull
    public final EventListener.Factory eventListenerFactory() {
        return this.eventListenerFactory;
    }

    @JvmName(name = "retryOnConnectionFailure")
    public final boolean retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    @JvmName(name = "authenticator")
    @NotNull
    public final Authenticator authenticator() {
        return this.authenticator;
    }

    @JvmName(name = "followRedirects")
    public final boolean followRedirects() {
        return this.followRedirects;
    }

    @JvmName(name = "followSslRedirects")
    public final boolean followSslRedirects() {
        return this.followSslRedirects;
    }

    @JvmName(name = "cookieJar")
    @NotNull
    public final CookieJar cookieJar() {
        return this.cookieJar;
    }

    @JvmName(name = "cache")
    @Nullable
    public final Cache cache() {
        return this.cache;
    }

    @JvmName(name = b.P)
    @NotNull
    public final Dns dns() {
        return this.dns;
    }

    @JvmName(name = "proxy")
    @Nullable
    public final Proxy proxy() {
        return this.proxy;
    }

    @JvmName(name = "proxySelector")
    @NotNull
    public final ProxySelector proxySelector() {
        return this.proxySelector;
    }

    @JvmName(name = "proxyAuthenticator")
    @NotNull
    public final Authenticator proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    @JvmName(name = "socketFactory")
    @NotNull
    public final SocketFactory socketFactory() {
        return this.socketFactory;
    }

    @JvmName(name = "sslSocketFactory")
    @NotNull
    public final SSLSocketFactory sslSocketFactory() {
        SSLSocketFactory sSLSocketFactory = this.sslSocketFactoryOrNull;
        if (sSLSocketFactory != null) {
            return sSLSocketFactory;
        }
        throw new IllegalStateException("CLEARTEXT-only client");
    }

    @JvmName(name = "x509TrustManager")
    @Nullable
    public final X509TrustManager x509TrustManager() {
        return this.x509TrustManager;
    }

    @JvmName(name = "connectionSpecs")
    @NotNull
    public final List<ConnectionSpec> connectionSpecs() {
        return this.connectionSpecs;
    }

    @JvmName(name = "protocols")
    @NotNull
    public final List<Protocol> protocols() {
        return this.protocols;
    }

    @JvmName(name = "hostnameVerifier")
    @NotNull
    public final HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    @JvmName(name = "certificatePinner")
    @NotNull
    public final CertificatePinner certificatePinner() {
        return this.certificatePinner;
    }

    @JvmName(name = "certificateChainCleaner")
    @Nullable
    public final CertificateChainCleaner certificateChainCleaner() {
        return this.certificateChainCleaner;
    }

    @JvmName(name = "callTimeoutMillis")
    public final int callTimeoutMillis() {
        return this.callTimeoutMillis;
    }

    @JvmName(name = "connectTimeoutMillis")
    public final int connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @JvmName(name = "readTimeoutMillis")
    public final int readTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    @JvmName(name = "writeTimeoutMillis")
    public final int writeTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    @JvmName(name = "pingIntervalMillis")
    public final int pingIntervalMillis() {
        return this.pingIntervalMillis;
    }

    @NotNull
    public final RouteDatabase getRouteDatabase() {
        return this.routeDatabase;
    }

    public OkHttpClient() {
        this(new Builder());
    }

    @Override // okhttp3.Call.Factory
    @NotNull
    public Call newCall(@NotNull Request request) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        return new RealCall(this, request, false);
    }

    @Override // okhttp3.WebSocket.Factory
    @NotNull
    public WebSocket newWebSocket(@NotNull Request request, @NotNull WebSocketListener listener) {
        Intrinsics.checkParameterIsNotNull(request, "request");
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        RealWebSocket realWebSocket = new RealWebSocket(TaskRunner.INSTANCE, request, listener, new Random(), this.pingIntervalMillis);
        realWebSocket.connect(this);
        return realWebSocket;
    }

    @NotNull
    public Builder newBuilder() {
        return new Builder(this);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dispatcher", imports = {}))
    @JvmName(name = "-deprecated_dispatcher")
    @NotNull
    /* renamed from: -deprecated_dispatcher */
    public final Dispatcher m1903deprecated_dispatcher() {
        return this.dispatcher;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionPool", imports = {}))
    @JvmName(name = "-deprecated_connectionPool")
    @NotNull
    /* renamed from: -deprecated_connectionPool */
    public final ConnectionPool m1900deprecated_connectionPool() {
        return this.connectionPool;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "interceptors", imports = {}))
    @JvmName(name = "-deprecated_interceptors")
    @NotNull
    /* renamed from: -deprecated_interceptors */
    public final List<Interceptor> m1909deprecated_interceptors() {
        return this.interceptors;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "networkInterceptors", imports = {}))
    @JvmName(name = "-deprecated_networkInterceptors")
    @NotNull
    /* renamed from: -deprecated_networkInterceptors */
    public final List<Interceptor> m1910deprecated_networkInterceptors() {
        return this.networkInterceptors;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "eventListenerFactory", imports = {}))
    @JvmName(name = "-deprecated_eventListenerFactory")
    @NotNull
    /* renamed from: -deprecated_eventListenerFactory */
    public final EventListener.Factory m1905deprecated_eventListenerFactory() {
        return this.eventListenerFactory;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "retryOnConnectionFailure", imports = {}))
    @JvmName(name = "-deprecated_retryOnConnectionFailure")
    /* renamed from: -deprecated_retryOnConnectionFailure */
    public final boolean m1917deprecated_retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "authenticator", imports = {}))
    @JvmName(name = "-deprecated_authenticator")
    @NotNull
    /* renamed from: -deprecated_authenticator */
    public final Authenticator m1895deprecated_authenticator() {
        return this.authenticator;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "followRedirects", imports = {}))
    @JvmName(name = "-deprecated_followRedirects")
    /* renamed from: -deprecated_followRedirects */
    public final boolean m1906deprecated_followRedirects() {
        return this.followRedirects;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "followSslRedirects", imports = {}))
    @JvmName(name = "-deprecated_followSslRedirects")
    /* renamed from: -deprecated_followSslRedirects */
    public final boolean m1907deprecated_followSslRedirects() {
        return this.followSslRedirects;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cookieJar", imports = {}))
    @JvmName(name = "-deprecated_cookieJar")
    @NotNull
    /* renamed from: -deprecated_cookieJar */
    public final CookieJar m1902deprecated_cookieJar() {
        return this.cookieJar;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "cache", imports = {}))
    @JvmName(name = "-deprecated_cache")
    @Nullable
    /* renamed from: -deprecated_cache */
    public final Cache m1896deprecated_cache() {
        return this.cache;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "dns", imports = {}))
    @JvmName(name = "-deprecated_dns")
    @NotNull
    /* renamed from: -deprecated_dns */
    public final Dns m1904deprecated_dns() {
        return this.dns;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxy", imports = {}))
    @JvmName(name = "-deprecated_proxy")
    @Nullable
    /* renamed from: -deprecated_proxy */
    public final Proxy m1913deprecated_proxy() {
        return this.proxy;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxySelector", imports = {}))
    @JvmName(name = "-deprecated_proxySelector")
    @NotNull
    /* renamed from: -deprecated_proxySelector */
    public final ProxySelector m1915deprecated_proxySelector() {
        return this.proxySelector;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "proxyAuthenticator", imports = {}))
    @JvmName(name = "-deprecated_proxyAuthenticator")
    @NotNull
    /* renamed from: -deprecated_proxyAuthenticator */
    public final Authenticator m1914deprecated_proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "socketFactory", imports = {}))
    @JvmName(name = "-deprecated_socketFactory")
    @NotNull
    /* renamed from: -deprecated_socketFactory */
    public final SocketFactory m1918deprecated_socketFactory() {
        return this.socketFactory;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sslSocketFactory", imports = {}))
    @JvmName(name = "-deprecated_sslSocketFactory")
    @NotNull
    /* renamed from: -deprecated_sslSocketFactory */
    public final SSLSocketFactory m1919deprecated_sslSocketFactory() {
        return sslSocketFactory();
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectionSpecs", imports = {}))
    @JvmName(name = "-deprecated_connectionSpecs")
    @NotNull
    /* renamed from: -deprecated_connectionSpecs */
    public final List<ConnectionSpec> m1901deprecated_connectionSpecs() {
        return this.connectionSpecs;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "protocols", imports = {}))
    @JvmName(name = "-deprecated_protocols")
    @NotNull
    /* renamed from: -deprecated_protocols */
    public final List<Protocol> m1912deprecated_protocols() {
        return this.protocols;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "hostnameVerifier", imports = {}))
    @JvmName(name = "-deprecated_hostnameVerifier")
    @NotNull
    /* renamed from: -deprecated_hostnameVerifier */
    public final HostnameVerifier m1908deprecated_hostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "certificatePinner", imports = {}))
    @JvmName(name = "-deprecated_certificatePinner")
    @NotNull
    /* renamed from: -deprecated_certificatePinner */
    public final CertificatePinner m1898deprecated_certificatePinner() {
        return this.certificatePinner;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "callTimeoutMillis", imports = {}))
    @JvmName(name = "-deprecated_callTimeoutMillis")
    /* renamed from: -deprecated_callTimeoutMillis */
    public final int m1897deprecated_callTimeoutMillis() {
        return this.callTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "connectTimeoutMillis", imports = {}))
    @JvmName(name = "-deprecated_connectTimeoutMillis")
    /* renamed from: -deprecated_connectTimeoutMillis */
    public final int m1899deprecated_connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "readTimeoutMillis", imports = {}))
    @JvmName(name = "-deprecated_readTimeoutMillis")
    /* renamed from: -deprecated_readTimeoutMillis */
    public final int m1916deprecated_readTimeoutMillis() {
        return this.readTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "writeTimeoutMillis", imports = {}))
    @JvmName(name = "-deprecated_writeTimeoutMillis")
    /* renamed from: -deprecated_writeTimeoutMillis */
    public final int m1920deprecated_writeTimeoutMillis() {
        return this.writeTimeoutMillis;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "pingIntervalMillis", imports = {}))
    @JvmName(name = "-deprecated_pingIntervalMillis")
    /* renamed from: -deprecated_pingIntervalMillis */
    public final int m1911deprecated_pingIntervalMillis() {
        return this.pingIntervalMillis;
    }

    /* compiled from: OkHttpClient.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000ô\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J<\u0010\u0098\u0001\u001a\u00020\u00002*\b\u0004\u0010\u0099\u0001\u001a#\u0012\u0017\u0012\u00150\u009b\u0001¢\u0006\u000f\b\u009c\u0001\u0012\n\b\u009d\u0001\u0012\u0005\b\b(\u009e\u0001\u0012\u0005\u0012\u00030\u009f\u00010\u009a\u0001H\u0087\b¢\u0006\u0003\b \u0001J\u0010\u0010\u0098\u0001\u001a\u00020\u00002\u0007\u0010¡\u0001\u001a\u00020]J<\u0010¢\u0001\u001a\u00020\u00002*\b\u0004\u0010\u0099\u0001\u001a#\u0012\u0017\u0012\u00150\u009b\u0001¢\u0006\u000f\b\u009c\u0001\u0012\n\b\u009d\u0001\u0012\u0005\b\b(\u009e\u0001\u0012\u0005\u0012\u00030\u009f\u00010\u009a\u0001H\u0087\b¢\u0006\u0003\b£\u0001J\u0010\u0010¢\u0001\u001a\u00020\u00002\u0007\u0010¡\u0001\u001a\u00020]J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\u0007\u0010¤\u0001\u001a\u00020\u0003J\u0010\u0010\f\u001a\u00020\u00002\b\u0010\f\u001a\u0004\u0018\u00010\rJ\u0012\u0010\u0012\u001a\u00020\u00002\b\u0010¥\u0001\u001a\u00030¦\u0001H\u0007J\u001a\u0010\u0012\u001a\u00020\u00002\b\u0010§\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u0001J\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u001fJ\u0012\u0010$\u001a\u00020\u00002\b\u0010¥\u0001\u001a\u00030¦\u0001H\u0007J\u001a\u0010$\u001a\u00020\u00002\b\u0010§\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u0001J\u000e\u0010'\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(J\u0014\u0010-\u001a\u00020\u00002\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.J\u000e\u00104\u001a\u00020\u00002\u0006\u00104\u001a\u000205J\u000e\u0010:\u001a\u00020\u00002\u0006\u0010:\u001a\u00020;J\u000e\u0010@\u001a\u00020\u00002\u0006\u0010@\u001a\u00020AJ\u0011\u0010«\u0001\u001a\u00020\u00002\b\u0010«\u0001\u001a\u00030¬\u0001J\u000e\u0010F\u001a\u00020\u00002\u0006\u0010F\u001a\u00020GJ\u000e\u0010L\u001a\u00020\u00002\u0006\u0010L\u001a\u00020MJ\u000f\u0010R\u001a\u00020\u00002\u0007\u0010\u00ad\u0001\u001a\u00020MJ\u000e\u0010U\u001a\u00020\u00002\u0006\u0010U\u001a\u00020VJ\f\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\J\f\u0010_\u001a\b\u0012\u0004\u0012\u00020]0\\J\u0012\u0010a\u001a\u00020\u00002\b\u0010¥\u0001\u001a\u00030¦\u0001H\u0007J\u001a\u0010a\u001a\u00020\u00002\b\u0010®\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u0001J\u0014\u0010d\u001a\u00020\u00002\f\u0010d\u001a\b\u0012\u0004\u0012\u00020e0.J\u0010\u0010h\u001a\u00020\u00002\b\u0010h\u001a\u0004\u0018\u00010iJ\u000e\u0010n\u001a\u00020\u00002\u0006\u0010n\u001a\u00020\u0007J\u000e\u0010q\u001a\u00020\u00002\u0006\u0010q\u001a\u00020rJ\u0012\u0010w\u001a\u00020\u00002\b\u0010¥\u0001\u001a\u00030¦\u0001H\u0007J\u001a\u0010w\u001a\u00020\u00002\b\u0010§\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u0001J\u000e\u0010z\u001a\u00020\u00002\u0006\u0010z\u001a\u00020MJ\u0011\u0010\u0083\u0001\u001a\u00020\u00002\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001J\u0013\u0010¯\u0001\u001a\u00020\u00002\b\u0010¯\u0001\u001a\u00030\u008a\u0001H\u0007J\u001b\u0010¯\u0001\u001a\u00020\u00002\b\u0010¯\u0001\u001a\u00030\u008a\u00012\b\u0010°\u0001\u001a\u00030\u0093\u0001J\u0013\u0010\u008f\u0001\u001a\u00020\u00002\b\u0010¥\u0001\u001a\u00030¦\u0001H\u0007J\u001b\u0010\u008f\u0001\u001a\u00020\u00002\b\u0010§\u0001\u001a\u00030¨\u00012\b\u0010©\u0001\u001a\u00030ª\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u001fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0015\"\u0004\b&\u0010\u0017R\u001a\u0010'\u001a\u00020(X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R \u0010-\u001a\b\u0012\u0004\u0012\u00020/0.X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001a\u0010:\u001a\u00020;X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020MX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020MX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010O\"\u0004\bT\u0010QR\u001a\u0010U\u001a\u00020VX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\b\u0012\u0004\u0012\u00020]0\\X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b^\u00101R\u001a\u0010_\u001a\b\u0012\u0004\u0012\u00020]0\\X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u00101R\u001a\u0010a\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010\u0015\"\u0004\bc\u0010\u0017R \u0010d\u001a\b\u0012\u0004\u0012\u00020e0.X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u00101\"\u0004\bg\u00103R\u001c\u0010h\u001a\u0004\u0018\u00010iX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010k\"\u0004\bl\u0010mR\u001a\u0010n\u001a\u00020\u0007X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010\t\"\u0004\bp\u0010\u000bR\u001c\u0010q\u001a\u0004\u0018\u00010rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bs\u0010t\"\u0004\bu\u0010vR\u001a\u0010w\u001a\u00020\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bx\u0010\u0015\"\u0004\by\u0010\u0017R\u001a\u0010z\u001a\u00020MX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b{\u0010O\"\u0004\b|\u0010QR\u001f\u0010}\u001a\u0004\u0018\u00010~X\u0080\u000e¢\u0006\u0011\n\u0000\u001a\u0005\b\u007f\u0010\u0080\u0001\"\u0006\b\u0081\u0001\u0010\u0082\u0001R \u0010\u0083\u0001\u001a\u00030\u0084\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001\"\u0006\b\u0087\u0001\u0010\u0088\u0001R\"\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"\u0006\b\u008d\u0001\u0010\u008e\u0001R\u001d\u0010\u008f\u0001\u001a\u00020\u0013X\u0080\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0090\u0001\u0010\u0015\"\u0005\b\u0091\u0001\u0010\u0017R\"\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u0001X\u0080\u000e¢\u0006\u0012\n\u0000\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0006\b\u0096\u0001\u0010\u0097\u0001¨\u0006±\u0001"}, d2 = {"Lokhttp3/OkHttpClient$Builder;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "()V", "authenticator", "Lokhttp3/Authenticator;", "getAuthenticator$okhttp", "()Lokhttp3/Authenticator;", "setAuthenticator$okhttp", "(Lokhttp3/Authenticator;)V", "cache", "Lokhttp3/Cache;", "getCache$okhttp", "()Lokhttp3/Cache;", "setCache$okhttp", "(Lokhttp3/Cache;)V", "callTimeout", "", "getCallTimeout$okhttp", "()I", "setCallTimeout$okhttp", "(I)V", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "setCertificateChainCleaner$okhttp", "(Lokhttp3/internal/tls/CertificateChainCleaner;)V", "certificatePinner", "Lokhttp3/CertificatePinner;", "getCertificatePinner$okhttp", "()Lokhttp3/CertificatePinner;", "setCertificatePinner$okhttp", "(Lokhttp3/CertificatePinner;)V", "connectTimeout", "getConnectTimeout$okhttp", "setConnectTimeout$okhttp", "connectionPool", "Lokhttp3/ConnectionPool;", "getConnectionPool$okhttp", "()Lokhttp3/ConnectionPool;", "setConnectionPool$okhttp", "(Lokhttp3/ConnectionPool;)V", "connectionSpecs", "", "Lokhttp3/ConnectionSpec;", "getConnectionSpecs$okhttp", "()Ljava/util/List;", "setConnectionSpecs$okhttp", "(Ljava/util/List;)V", "cookieJar", "Lokhttp3/CookieJar;", "getCookieJar$okhttp", "()Lokhttp3/CookieJar;", "setCookieJar$okhttp", "(Lokhttp3/CookieJar;)V", "dispatcher", "Lokhttp3/Dispatcher;", "getDispatcher$okhttp", "()Lokhttp3/Dispatcher;", "setDispatcher$okhttp", "(Lokhttp3/Dispatcher;)V", b.P, "Lokhttp3/Dns;", "getDns$okhttp", "()Lokhttp3/Dns;", "setDns$okhttp", "(Lokhttp3/Dns;)V", "eventListenerFactory", "Lokhttp3/EventListener$Factory;", "getEventListenerFactory$okhttp", "()Lokhttp3/EventListener$Factory;", "setEventListenerFactory$okhttp", "(Lokhttp3/EventListener$Factory;)V", "followRedirects", "", "getFollowRedirects$okhttp", "()Z", "setFollowRedirects$okhttp", "(Z)V", "followSslRedirects", "getFollowSslRedirects$okhttp", "setFollowSslRedirects$okhttp", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "getHostnameVerifier$okhttp", "()Ljavax/net/ssl/HostnameVerifier;", "setHostnameVerifier$okhttp", "(Ljavax/net/ssl/HostnameVerifier;)V", "interceptors", "", "Lokhttp3/Interceptor;", "getInterceptors$okhttp", "networkInterceptors", "getNetworkInterceptors$okhttp", "pingInterval", "getPingInterval$okhttp", "setPingInterval$okhttp", "protocols", "Lokhttp3/Protocol;", "getProtocols$okhttp", "setProtocols$okhttp", "proxy", "Ljava/net/Proxy;", "getProxy$okhttp", "()Ljava/net/Proxy;", "setProxy$okhttp", "(Ljava/net/Proxy;)V", "proxyAuthenticator", "getProxyAuthenticator$okhttp", "setProxyAuthenticator$okhttp", "proxySelector", "Ljava/net/ProxySelector;", "getProxySelector$okhttp", "()Ljava/net/ProxySelector;", "setProxySelector$okhttp", "(Ljava/net/ProxySelector;)V", "readTimeout", "getReadTimeout$okhttp", "setReadTimeout$okhttp", "retryOnConnectionFailure", "getRetryOnConnectionFailure$okhttp", "setRetryOnConnectionFailure$okhttp", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "getRouteDatabase$okhttp", "()Lokhttp3/internal/connection/RouteDatabase;", "setRouteDatabase$okhttp", "(Lokhttp3/internal/connection/RouteDatabase;)V", "socketFactory", "Ljavax/net/SocketFactory;", "getSocketFactory$okhttp", "()Ljavax/net/SocketFactory;", "setSocketFactory$okhttp", "(Ljavax/net/SocketFactory;)V", "sslSocketFactoryOrNull", "Ljavax/net/ssl/SSLSocketFactory;", "getSslSocketFactoryOrNull$okhttp", "()Ljavax/net/ssl/SSLSocketFactory;", "setSslSocketFactoryOrNull$okhttp", "(Ljavax/net/ssl/SSLSocketFactory;)V", "writeTimeout", "getWriteTimeout$okhttp", "setWriteTimeout$okhttp", "x509TrustManagerOrNull", "Ljavax/net/ssl/X509TrustManager;", "getX509TrustManagerOrNull$okhttp", "()Ljavax/net/ssl/X509TrustManager;", "setX509TrustManagerOrNull$okhttp", "(Ljavax/net/ssl/X509TrustManager;)V", "addInterceptor", "block", "Lkotlin/Function1;", "Lokhttp3/Interceptor$Chain;", "Lkotlin/ParameterName;", "name", "chain", "Lokhttp3/Response;", "-addInterceptor", "interceptor", "addNetworkInterceptor", "-addNetworkInterceptor", "build", "duration", "Ljava/time/Duration;", RtspHeaders.Values.TIMEOUT, "", "unit", "Ljava/util/concurrent/TimeUnit;", "eventListener", "Lokhttp3/EventListener;", "followProtocolRedirects", ai.aR, "sslSocketFactory", "trustManager", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Builder {
        @NotNull
        private Authenticator authenticator;
        @Nullable
        private Cache cache;
        private int callTimeout;
        @Nullable
        private CertificateChainCleaner certificateChainCleaner;
        @NotNull
        private CertificatePinner certificatePinner;
        private int connectTimeout;
        @NotNull
        private ConnectionPool connectionPool;
        @NotNull
        private List<ConnectionSpec> connectionSpecs;
        @NotNull
        private CookieJar cookieJar;
        @NotNull
        private Dispatcher dispatcher;
        @NotNull
        private Dns dns;
        @NotNull
        private EventListener.Factory eventListenerFactory;
        private boolean followRedirects;
        private boolean followSslRedirects;
        @NotNull
        private HostnameVerifier hostnameVerifier;
        @NotNull
        private final List<Interceptor> interceptors;
        @NotNull
        private final List<Interceptor> networkInterceptors;
        private int pingInterval;
        @NotNull
        private List<? extends Protocol> protocols;
        @Nullable
        private Proxy proxy;
        @NotNull
        private Authenticator proxyAuthenticator;
        @Nullable
        private ProxySelector proxySelector;
        private int readTimeout;
        private boolean retryOnConnectionFailure;
        @Nullable
        private RouteDatabase routeDatabase;
        @NotNull
        private SocketFactory socketFactory;
        @Nullable
        private SSLSocketFactory sslSocketFactoryOrNull;
        private int writeTimeout;
        @Nullable
        private X509TrustManager x509TrustManagerOrNull;

        public Builder() {
            this.dispatcher = new Dispatcher();
            this.connectionPool = new ConnectionPool();
            this.interceptors = new ArrayList();
            this.networkInterceptors = new ArrayList();
            this.eventListenerFactory = Util.asFactory(EventListener.NONE);
            this.retryOnConnectionFailure = true;
            this.authenticator = Authenticator.NONE;
            this.followRedirects = true;
            this.followSslRedirects = true;
            this.cookieJar = CookieJar.NO_COOKIES;
            this.dns = Dns.SYSTEM;
            this.proxyAuthenticator = Authenticator.NONE;
            SocketFactory socketFactory = SocketFactory.getDefault();
            Intrinsics.checkExpressionValueIsNotNull(socketFactory, "SocketFactory.getDefault()");
            this.socketFactory = socketFactory;
            this.connectionSpecs = OkHttpClient.Companion.getDEFAULT_CONNECTION_SPECS$okhttp();
            this.protocols = OkHttpClient.Companion.getDEFAULT_PROTOCOLS$okhttp();
            this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
            this.certificatePinner = CertificatePinner.DEFAULT;
            this.connectTimeout = 10000;
            this.readTimeout = 10000;
            this.writeTimeout = 10000;
        }

        @NotNull
        public final Dispatcher getDispatcher$okhttp() {
            return this.dispatcher;
        }

        public final void setDispatcher$okhttp(@NotNull Dispatcher dispatcher) {
            Intrinsics.checkParameterIsNotNull(dispatcher, "<set-?>");
            this.dispatcher = dispatcher;
        }

        @NotNull
        public final ConnectionPool getConnectionPool$okhttp() {
            return this.connectionPool;
        }

        public final void setConnectionPool$okhttp(@NotNull ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "<set-?>");
            this.connectionPool = connectionPool;
        }

        @NotNull
        public final List<Interceptor> getInterceptors$okhttp() {
            return this.interceptors;
        }

        @NotNull
        public final List<Interceptor> getNetworkInterceptors$okhttp() {
            return this.networkInterceptors;
        }

        @NotNull
        public final EventListener.Factory getEventListenerFactory$okhttp() {
            return this.eventListenerFactory;
        }

        public final void setEventListenerFactory$okhttp(@NotNull EventListener.Factory factory) {
            Intrinsics.checkParameterIsNotNull(factory, "<set-?>");
            this.eventListenerFactory = factory;
        }

        public final boolean getRetryOnConnectionFailure$okhttp() {
            return this.retryOnConnectionFailure;
        }

        public final void setRetryOnConnectionFailure$okhttp(boolean z) {
            this.retryOnConnectionFailure = z;
        }

        @NotNull
        public final Authenticator getAuthenticator$okhttp() {
            return this.authenticator;
        }

        public final void setAuthenticator$okhttp(@NotNull Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "<set-?>");
            this.authenticator = authenticator;
        }

        public final boolean getFollowRedirects$okhttp() {
            return this.followRedirects;
        }

        public final void setFollowRedirects$okhttp(boolean z) {
            this.followRedirects = z;
        }

        public final boolean getFollowSslRedirects$okhttp() {
            return this.followSslRedirects;
        }

        public final void setFollowSslRedirects$okhttp(boolean z) {
            this.followSslRedirects = z;
        }

        @NotNull
        public final CookieJar getCookieJar$okhttp() {
            return this.cookieJar;
        }

        public final void setCookieJar$okhttp(@NotNull CookieJar cookieJar) {
            Intrinsics.checkParameterIsNotNull(cookieJar, "<set-?>");
            this.cookieJar = cookieJar;
        }

        @Nullable
        public final Cache getCache$okhttp() {
            return this.cache;
        }

        public final void setCache$okhttp(@Nullable Cache cache) {
            this.cache = cache;
        }

        @NotNull
        public final Dns getDns$okhttp() {
            return this.dns;
        }

        public final void setDns$okhttp(@NotNull Dns dns) {
            Intrinsics.checkParameterIsNotNull(dns, "<set-?>");
            this.dns = dns;
        }

        @Nullable
        public final Proxy getProxy$okhttp() {
            return this.proxy;
        }

        public final void setProxy$okhttp(@Nullable Proxy proxy) {
            this.proxy = proxy;
        }

        @Nullable
        public final ProxySelector getProxySelector$okhttp() {
            return this.proxySelector;
        }

        public final void setProxySelector$okhttp(@Nullable ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
        }

        @NotNull
        public final Authenticator getProxyAuthenticator$okhttp() {
            return this.proxyAuthenticator;
        }

        public final void setProxyAuthenticator$okhttp(@NotNull Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "<set-?>");
            this.proxyAuthenticator = authenticator;
        }

        @NotNull
        public final SocketFactory getSocketFactory$okhttp() {
            return this.socketFactory;
        }

        public final void setSocketFactory$okhttp(@NotNull SocketFactory socketFactory) {
            Intrinsics.checkParameterIsNotNull(socketFactory, "<set-?>");
            this.socketFactory = socketFactory;
        }

        @Nullable
        public final SSLSocketFactory getSslSocketFactoryOrNull$okhttp() {
            return this.sslSocketFactoryOrNull;
        }

        public final void setSslSocketFactoryOrNull$okhttp(@Nullable SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactoryOrNull = sSLSocketFactory;
        }

        @Nullable
        public final X509TrustManager getX509TrustManagerOrNull$okhttp() {
            return this.x509TrustManagerOrNull;
        }

        public final void setX509TrustManagerOrNull$okhttp(@Nullable X509TrustManager x509TrustManager) {
            this.x509TrustManagerOrNull = x509TrustManager;
        }

        @NotNull
        public final List<ConnectionSpec> getConnectionSpecs$okhttp() {
            return this.connectionSpecs;
        }

        public final void setConnectionSpecs$okhttp(@NotNull List<ConnectionSpec> list) {
            Intrinsics.checkParameterIsNotNull(list, "<set-?>");
            this.connectionSpecs = list;
        }

        @NotNull
        public final List<Protocol> getProtocols$okhttp() {
            return this.protocols;
        }

        public final void setProtocols$okhttp(@NotNull List<? extends Protocol> list) {
            Intrinsics.checkParameterIsNotNull(list, "<set-?>");
            this.protocols = list;
        }

        @NotNull
        public final HostnameVerifier getHostnameVerifier$okhttp() {
            return this.hostnameVerifier;
        }

        public final void setHostnameVerifier$okhttp(@NotNull HostnameVerifier hostnameVerifier) {
            Intrinsics.checkParameterIsNotNull(hostnameVerifier, "<set-?>");
            this.hostnameVerifier = hostnameVerifier;
        }

        @NotNull
        public final CertificatePinner getCertificatePinner$okhttp() {
            return this.certificatePinner;
        }

        public final void setCertificatePinner$okhttp(@NotNull CertificatePinner certificatePinner) {
            Intrinsics.checkParameterIsNotNull(certificatePinner, "<set-?>");
            this.certificatePinner = certificatePinner;
        }

        @Nullable
        public final CertificateChainCleaner getCertificateChainCleaner$okhttp() {
            return this.certificateChainCleaner;
        }

        public final void setCertificateChainCleaner$okhttp(@Nullable CertificateChainCleaner certificateChainCleaner) {
            this.certificateChainCleaner = certificateChainCleaner;
        }

        public final int getCallTimeout$okhttp() {
            return this.callTimeout;
        }

        public final void setCallTimeout$okhttp(int i) {
            this.callTimeout = i;
        }

        public final int getConnectTimeout$okhttp() {
            return this.connectTimeout;
        }

        public final void setConnectTimeout$okhttp(int i) {
            this.connectTimeout = i;
        }

        public final int getReadTimeout$okhttp() {
            return this.readTimeout;
        }

        public final void setReadTimeout$okhttp(int i) {
            this.readTimeout = i;
        }

        public final int getWriteTimeout$okhttp() {
            return this.writeTimeout;
        }

        public final void setWriteTimeout$okhttp(int i) {
            this.writeTimeout = i;
        }

        public final int getPingInterval$okhttp() {
            return this.pingInterval;
        }

        public final void setPingInterval$okhttp(int i) {
            this.pingInterval = i;
        }

        @Nullable
        public final RouteDatabase getRouteDatabase$okhttp() {
            return this.routeDatabase;
        }

        public final void setRouteDatabase$okhttp(@Nullable RouteDatabase routeDatabase) {
            this.routeDatabase = routeDatabase;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull OkHttpClient okHttpClient) {
            this();
            Intrinsics.checkParameterIsNotNull(okHttpClient, "okHttpClient");
            this.dispatcher = okHttpClient.dispatcher();
            this.connectionPool = okHttpClient.connectionPool();
            CollectionsKt.addAll(this.interceptors, okHttpClient.interceptors());
            CollectionsKt.addAll(this.networkInterceptors, okHttpClient.networkInterceptors());
            this.eventListenerFactory = okHttpClient.eventListenerFactory();
            this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure();
            this.authenticator = okHttpClient.authenticator();
            this.followRedirects = okHttpClient.followRedirects();
            this.followSslRedirects = okHttpClient.followSslRedirects();
            this.cookieJar = okHttpClient.cookieJar();
            this.cache = okHttpClient.cache();
            this.dns = okHttpClient.dns();
            this.proxy = okHttpClient.proxy();
            this.proxySelector = okHttpClient.proxySelector();
            this.proxyAuthenticator = okHttpClient.proxyAuthenticator();
            this.socketFactory = okHttpClient.socketFactory();
            this.sslSocketFactoryOrNull = okHttpClient.sslSocketFactoryOrNull;
            this.x509TrustManagerOrNull = okHttpClient.x509TrustManager();
            this.connectionSpecs = okHttpClient.connectionSpecs();
            this.protocols = okHttpClient.protocols();
            this.hostnameVerifier = okHttpClient.hostnameVerifier();
            this.certificatePinner = okHttpClient.certificatePinner();
            this.certificateChainCleaner = okHttpClient.certificateChainCleaner();
            this.callTimeout = okHttpClient.callTimeoutMillis();
            this.connectTimeout = okHttpClient.connectTimeoutMillis();
            this.readTimeout = okHttpClient.readTimeoutMillis();
            this.writeTimeout = okHttpClient.writeTimeoutMillis();
            this.pingInterval = okHttpClient.pingIntervalMillis();
            this.routeDatabase = okHttpClient.getRouteDatabase();
        }

        @NotNull
        public final Builder dispatcher(@NotNull Dispatcher dispatcher) {
            Intrinsics.checkParameterIsNotNull(dispatcher, "dispatcher");
            Builder builder = this;
            builder.dispatcher = dispatcher;
            return builder;
        }

        @NotNull
        public final Builder connectionPool(@NotNull ConnectionPool connectionPool) {
            Intrinsics.checkParameterIsNotNull(connectionPool, "connectionPool");
            Builder builder = this;
            builder.connectionPool = connectionPool;
            return builder;
        }

        @NotNull
        public final List<Interceptor> interceptors() {
            return this.interceptors;
        }

        @NotNull
        public final Builder addInterceptor(@NotNull Interceptor interceptor) {
            Intrinsics.checkParameterIsNotNull(interceptor, "interceptor");
            Builder builder = this;
            builder.interceptors.add(interceptor);
            return builder;
        }

        @JvmName(name = "-addInterceptor")
        @NotNull
        /* renamed from: -addInterceptor */
        public final Builder m1921addInterceptor(@NotNull final Function1<? super Interceptor.Chain, Response> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            Interceptor.Companion companion = Interceptor.Companion;
            return addInterceptor(new Interceptor() { // from class: okhttp3.OkHttpClient$Builder$addInterceptor$$inlined$invoke$1
                @Override // okhttp3.Interceptor
                @NotNull
                public Response intercept(@NotNull Interceptor.Chain chain) {
                    Intrinsics.checkParameterIsNotNull(chain, "chain");
                    return (Response) Function1.this.invoke(chain);
                }
            });
        }

        @NotNull
        public final List<Interceptor> networkInterceptors() {
            return this.networkInterceptors;
        }

        @NotNull
        public final Builder addNetworkInterceptor(@NotNull Interceptor interceptor) {
            Intrinsics.checkParameterIsNotNull(interceptor, "interceptor");
            Builder builder = this;
            builder.networkInterceptors.add(interceptor);
            return builder;
        }

        @JvmName(name = "-addNetworkInterceptor")
        @NotNull
        /* renamed from: -addNetworkInterceptor */
        public final Builder m1922addNetworkInterceptor(@NotNull final Function1<? super Interceptor.Chain, Response> block) {
            Intrinsics.checkParameterIsNotNull(block, "block");
            Interceptor.Companion companion = Interceptor.Companion;
            return addNetworkInterceptor(new Interceptor() { // from class: okhttp3.OkHttpClient$Builder$addNetworkInterceptor$$inlined$invoke$1
                @Override // okhttp3.Interceptor
                @NotNull
                public Response intercept(@NotNull Interceptor.Chain chain) {
                    Intrinsics.checkParameterIsNotNull(chain, "chain");
                    return (Response) Function1.this.invoke(chain);
                }
            });
        }

        @NotNull
        public final Builder eventListener(@NotNull EventListener eventListener) {
            Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
            Builder builder = this;
            builder.eventListenerFactory = Util.asFactory(eventListener);
            return builder;
        }

        @NotNull
        public final Builder eventListenerFactory(@NotNull EventListener.Factory eventListenerFactory) {
            Intrinsics.checkParameterIsNotNull(eventListenerFactory, "eventListenerFactory");
            Builder builder = this;
            builder.eventListenerFactory = eventListenerFactory;
            return builder;
        }

        @NotNull
        public final Builder retryOnConnectionFailure(boolean z) {
            Builder builder = this;
            builder.retryOnConnectionFailure = z;
            return builder;
        }

        @NotNull
        public final Builder authenticator(@NotNull Authenticator authenticator) {
            Intrinsics.checkParameterIsNotNull(authenticator, "authenticator");
            Builder builder = this;
            builder.authenticator = authenticator;
            return builder;
        }

        @NotNull
        public final Builder followRedirects(boolean z) {
            Builder builder = this;
            builder.followRedirects = z;
            return builder;
        }

        @NotNull
        public final Builder followSslRedirects(boolean z) {
            Builder builder = this;
            builder.followSslRedirects = z;
            return builder;
        }

        @NotNull
        public final Builder cookieJar(@NotNull CookieJar cookieJar) {
            Intrinsics.checkParameterIsNotNull(cookieJar, "cookieJar");
            Builder builder = this;
            builder.cookieJar = cookieJar;
            return builder;
        }

        @NotNull
        public final Builder cache(@Nullable Cache cache) {
            Builder builder = this;
            builder.cache = cache;
            return builder;
        }

        @NotNull
        public final Builder dns(@NotNull Dns dns) {
            Intrinsics.checkParameterIsNotNull(dns, "dns");
            Builder builder = this;
            if (!Intrinsics.areEqual(dns, builder.dns)) {
                builder.routeDatabase = null;
            }
            builder.dns = dns;
            return builder;
        }

        @NotNull
        public final Builder proxy(@Nullable Proxy proxy) {
            Builder builder = this;
            if (!Intrinsics.areEqual(proxy, builder.proxy)) {
                builder.routeDatabase = null;
            }
            builder.proxy = proxy;
            return builder;
        }

        @NotNull
        public final Builder proxySelector(@NotNull ProxySelector proxySelector) {
            Intrinsics.checkParameterIsNotNull(proxySelector, "proxySelector");
            Builder builder = this;
            if (!Intrinsics.areEqual(proxySelector, builder.proxySelector)) {
                builder.routeDatabase = null;
            }
            builder.proxySelector = proxySelector;
            return builder;
        }

        @NotNull
        public final Builder proxyAuthenticator(@NotNull Authenticator proxyAuthenticator) {
            Intrinsics.checkParameterIsNotNull(proxyAuthenticator, "proxyAuthenticator");
            Builder builder = this;
            if (!Intrinsics.areEqual(proxyAuthenticator, builder.proxyAuthenticator)) {
                builder.routeDatabase = null;
            }
            builder.proxyAuthenticator = proxyAuthenticator;
            return builder;
        }

        @NotNull
        public final Builder socketFactory(@NotNull SocketFactory socketFactory) {
            Intrinsics.checkParameterIsNotNull(socketFactory, "socketFactory");
            Builder builder = this;
            if (!(socketFactory instanceof SSLSocketFactory)) {
                if (!Intrinsics.areEqual(socketFactory, builder.socketFactory)) {
                    builder.routeDatabase = null;
                }
                builder.socketFactory = socketFactory;
                return builder;
            }
            throw new IllegalArgumentException("socketFactory instanceof SSLSocketFactory".toString());
        }

        @Deprecated(level = DeprecationLevel.ERROR, message = "Use the sslSocketFactory overload that accepts a X509TrustManager.")
        @NotNull
        public final Builder sslSocketFactory(@NotNull SSLSocketFactory sslSocketFactory) {
            Intrinsics.checkParameterIsNotNull(sslSocketFactory, "sslSocketFactory");
            Builder builder = this;
            if (!Intrinsics.areEqual(sslSocketFactory, builder.sslSocketFactoryOrNull)) {
                builder.routeDatabase = null;
            }
            builder.sslSocketFactoryOrNull = sslSocketFactory;
            builder.certificateChainCleaner = Platform.Companion.get().buildCertificateChainCleaner(sslSocketFactory);
            return builder;
        }

        @NotNull
        public final Builder sslSocketFactory(@NotNull SSLSocketFactory sslSocketFactory, @NotNull X509TrustManager trustManager) {
            Intrinsics.checkParameterIsNotNull(sslSocketFactory, "sslSocketFactory");
            Intrinsics.checkParameterIsNotNull(trustManager, "trustManager");
            Builder builder = this;
            if ((!Intrinsics.areEqual(sslSocketFactory, builder.sslSocketFactoryOrNull)) || (!Intrinsics.areEqual(trustManager, builder.x509TrustManagerOrNull))) {
                builder.routeDatabase = null;
            }
            builder.sslSocketFactoryOrNull = sslSocketFactory;
            builder.certificateChainCleaner = CertificateChainCleaner.Companion.get(trustManager);
            builder.x509TrustManagerOrNull = trustManager;
            return builder;
        }

        @NotNull
        public final Builder connectionSpecs(@NotNull List<ConnectionSpec> connectionSpecs) {
            Intrinsics.checkParameterIsNotNull(connectionSpecs, "connectionSpecs");
            Builder builder = this;
            if (!Intrinsics.areEqual(connectionSpecs, builder.connectionSpecs)) {
                builder.routeDatabase = null;
            }
            builder.connectionSpecs = Util.toImmutableList(connectionSpecs);
            return builder;
        }

        @NotNull
        public final Builder protocols(@NotNull List<? extends Protocol> protocols) {
            Intrinsics.checkParameterIsNotNull(protocols, "protocols");
            Builder builder = this;
            List mutableList = CollectionsKt.toMutableList((Collection) protocols);
            boolean z = false;
            if (mutableList.contains(Protocol.H2_PRIOR_KNOWLEDGE) || mutableList.contains(Protocol.HTTP_1_1)) {
                if (!mutableList.contains(Protocol.H2_PRIOR_KNOWLEDGE) || mutableList.size() <= 1) {
                    z = true;
                }
                if (!z) {
                    throw new IllegalArgumentException(("protocols containing h2_prior_knowledge cannot use other protocols: " + mutableList).toString());
                } else if (!(!mutableList.contains(Protocol.HTTP_1_0))) {
                    throw new IllegalArgumentException(("protocols must not contain http/1.0: " + mutableList).toString());
                } else if (mutableList == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<okhttp3.Protocol?>");
                } else if (!mutableList.contains(null)) {
                    mutableList.remove(Protocol.SPDY_3);
                    if (!Intrinsics.areEqual(mutableList, builder.protocols)) {
                        builder.routeDatabase = null;
                    }
                    List<? extends Protocol> unmodifiableList = Collections.unmodifiableList(mutableList);
                    Intrinsics.checkExpressionValueIsNotNull(unmodifiableList, "Collections.unmodifiableList(protocolsCopy)");
                    builder.protocols = unmodifiableList;
                    return builder;
                } else {
                    throw new IllegalArgumentException("protocols must not contain null".toString());
                }
            } else {
                throw new IllegalArgumentException(("protocols must contain h2_prior_knowledge or http/1.1: " + mutableList).toString());
            }
        }

        @NotNull
        public final Builder hostnameVerifier(@NotNull HostnameVerifier hostnameVerifier) {
            Intrinsics.checkParameterIsNotNull(hostnameVerifier, "hostnameVerifier");
            Builder builder = this;
            if (!Intrinsics.areEqual(hostnameVerifier, builder.hostnameVerifier)) {
                builder.routeDatabase = null;
            }
            builder.hostnameVerifier = hostnameVerifier;
            return builder;
        }

        @NotNull
        public final Builder certificatePinner(@NotNull CertificatePinner certificatePinner) {
            Intrinsics.checkParameterIsNotNull(certificatePinner, "certificatePinner");
            Builder builder = this;
            if (!Intrinsics.areEqual(certificatePinner, builder.certificatePinner)) {
                builder.routeDatabase = null;
            }
            builder.certificatePinner = certificatePinner;
            return builder;
        }

        @NotNull
        public final Builder callTimeout(long j, @NotNull TimeUnit unit) {
            Intrinsics.checkParameterIsNotNull(unit, "unit");
            Builder builder = this;
            builder.callTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, j, unit);
            return builder;
        }

        @IgnoreJRERequirement
        @NotNull
        public final Builder callTimeout(@NotNull Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.callTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        @NotNull
        public final Builder connectTimeout(long j, @NotNull TimeUnit unit) {
            Intrinsics.checkParameterIsNotNull(unit, "unit");
            Builder builder = this;
            builder.connectTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, j, unit);
            return builder;
        }

        @IgnoreJRERequirement
        @NotNull
        public final Builder connectTimeout(@NotNull Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.connectTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        @NotNull
        public final Builder readTimeout(long j, @NotNull TimeUnit unit) {
            Intrinsics.checkParameterIsNotNull(unit, "unit");
            Builder builder = this;
            builder.readTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, j, unit);
            return builder;
        }

        @IgnoreJRERequirement
        @NotNull
        public final Builder readTimeout(@NotNull Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.readTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        @NotNull
        public final Builder writeTimeout(long j, @NotNull TimeUnit unit) {
            Intrinsics.checkParameterIsNotNull(unit, "unit");
            Builder builder = this;
            builder.writeTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, j, unit);
            return builder;
        }

        @IgnoreJRERequirement
        @NotNull
        public final Builder writeTimeout(@NotNull Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.writeTimeout = Util.checkDuration(RtspHeaders.Values.TIMEOUT, duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        @NotNull
        public final Builder pingInterval(long j, @NotNull TimeUnit unit) {
            Intrinsics.checkParameterIsNotNull(unit, "unit");
            Builder builder = this;
            builder.pingInterval = Util.checkDuration(ai.aR, j, unit);
            return builder;
        }

        @IgnoreJRERequirement
        @NotNull
        public final Builder pingInterval(@NotNull Duration duration) {
            Intrinsics.checkParameterIsNotNull(duration, "duration");
            Builder builder = this;
            builder.pingInterval = Util.checkDuration(RtspHeaders.Values.TIMEOUT, duration.toMillis(), TimeUnit.MILLISECONDS);
            return builder;
        }

        @NotNull
        public final OkHttpClient build() {
            return new OkHttpClient(this);
        }
    }

    /* compiled from: OkHttpClient.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007¨\u0006\u000f"}, d2 = {"Lokhttp3/OkHttpClient$Companion;", "", "()V", "DEFAULT_CONNECTION_SPECS", "", "Lokhttp3/ConnectionSpec;", "getDEFAULT_CONNECTION_SPECS$okhttp", "()Ljava/util/List;", "DEFAULT_PROTOCOLS", "Lokhttp3/Protocol;", "getDEFAULT_PROTOCOLS$okhttp", "newSslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final List<Protocol> getDEFAULT_PROTOCOLS$okhttp() {
            return OkHttpClient.DEFAULT_PROTOCOLS;
        }

        @NotNull
        public final List<ConnectionSpec> getDEFAULT_CONNECTION_SPECS$okhttp() {
            return OkHttpClient.DEFAULT_CONNECTION_SPECS;
        }

        public final SSLSocketFactory newSslSocketFactory(X509TrustManager x509TrustManager) {
            try {
                SSLContext newSSLContext = Platform.Companion.get().newSSLContext();
                newSSLContext.init(null, new TrustManager[]{x509TrustManager}, null);
                SSLSocketFactory socketFactory = newSSLContext.getSocketFactory();
                Intrinsics.checkExpressionValueIsNotNull(socketFactory, "sslContext.socketFactory");
                return socketFactory;
            } catch (GeneralSecurityException e) {
                throw new AssertionError("No System TLS", e);
            }
        }
    }
}
