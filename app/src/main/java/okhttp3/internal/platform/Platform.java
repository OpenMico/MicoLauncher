package okhttp3.internal.platform;

import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.micolauncher.skills.voip.model.MicoVoipClient;
import com.xiaomi.onetrack.a.a;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.Util;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.BasicTrustRootIndex;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Platform.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 12\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\nH\u0016J-\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0011\u0010\u0014\u001a\r\u0012\t\u0012\u00070\u0016¢\u0006\u0002\b\u00170\u0015H\u0016J\u0012\u0010\u0018\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J \u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0006\u0010 \u001a\u00020\u0013J\u0012\u0010!\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0012\u0010\"\u001a\u0004\u0018\u00010\u00012\u0006\u0010#\u001a\u00020\u0013H\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J&\u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\u00132\b\b\u0002\u0010(\u001a\u00020\u001f2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*H\u0016J\u001a\u0010+\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\u00132\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020\fH\u0016J\b\u00100\u001a\u00020\u0013H\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\t\u001a\u00020\nH\u0014¨\u00062"}, d2 = {"Lokhttp3/internal/platform/Platform;", "", "()V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "buildCertificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "buildTrustRootIndex", "Lokhttp3/internal/tls/TrustRootIndex;", "configureSslSocketFactory", "socketFactory", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "Lkotlin/jvm/JvmSuppressWildcards;", "configureTrustManager", "connectSocket", "socket", "Ljava/net/Socket;", "address", "Ljava/net/InetSocketAddress;", "connectTimeout", "", "getPrefix", "getSelectedProtocol", "getStackTraceForCloseable", "closer", "isCleartextTrafficPermitted", "", MicoVoipClient.MICOLAUNCHER_LOG, "message", a.d, ai.aF, "", "logCloseableLeak", "stackTrace", "newSSLContext", "Ljavax/net/ssl/SSLContext;", "platformTrustManager", "toString", "Companion", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public class Platform {
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final Companion Companion = new Companion(null);
    private static volatile Platform platform = Companion.findPlatform();
    private static final Logger logger = Logger.getLogger(OkHttpClient.class.getName());

    @JvmStatic
    @NotNull
    public static final Platform get() {
        return Companion.get();
    }

    public void afterHandshake(@NotNull SSLSocket sslSocket) {
        Intrinsics.checkParameterIsNotNull(sslSocket, "sslSocket");
    }

    public void configureSslSocketFactory(@NotNull SSLSocketFactory socketFactory) {
        Intrinsics.checkParameterIsNotNull(socketFactory, "socketFactory");
    }

    public void configureTlsExtensions(@NotNull SSLSocket sslSocket, @Nullable String str, @NotNull List<Protocol> protocols) {
        Intrinsics.checkParameterIsNotNull(sslSocket, "sslSocket");
        Intrinsics.checkParameterIsNotNull(protocols, "protocols");
    }

    public void configureTrustManager(@Nullable X509TrustManager x509TrustManager) {
    }

    @NotNull
    public final String getPrefix() {
        return "OkHttp";
    }

    @Nullable
    public String getSelectedProtocol(@NotNull SSLSocket sslSocket) {
        Intrinsics.checkParameterIsNotNull(sslSocket, "sslSocket");
        return null;
    }

    public boolean isCleartextTrafficPermitted(@NotNull String hostname) {
        Intrinsics.checkParameterIsNotNull(hostname, "hostname");
        return true;
    }

    @NotNull
    public SSLContext newSSLContext() {
        SSLContext instance = SSLContext.getInstance("TLS");
        Intrinsics.checkExpressionValueIsNotNull(instance, "SSLContext.getInstance(\"TLS\")");
        return instance;
    }

    @NotNull
    public X509TrustManager platformTrustManager() {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        Intrinsics.checkExpressionValueIsNotNull(factory, "factory");
        TrustManager[] trustManagers = factory.getTrustManagers();
        if (trustManagers == null) {
            Intrinsics.throwNpe();
        }
        boolean z = true;
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            z = false;
        }
        if (z) {
            TrustManager trustManager = trustManagers[0];
            if (trustManager != null) {
                return (X509TrustManager) trustManager;
            }
            throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected default trust managers: ");
        String arrays = Arrays.toString(trustManagers);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        sb.append(arrays);
        throw new IllegalStateException(sb.toString().toString());
    }

    @Nullable
    public X509TrustManager trustManager(@NotNull SSLSocketFactory sslSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sslSocketFactory, "sslSocketFactory");
        try {
            Class<?> sslContextClass = Class.forName("sun.security.ssl.SSLContextImpl");
            Intrinsics.checkExpressionValueIsNotNull(sslContextClass, "sslContextClass");
            Object readFieldOrNull = Util.readFieldOrNull(sslSocketFactory, sslContextClass, c.R);
            if (readFieldOrNull != null) {
                return (X509TrustManager) Util.readFieldOrNull(readFieldOrNull, X509TrustManager.class, "trustManager");
            }
            return null;
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public void connectSocket(@NotNull Socket socket, @NotNull InetSocketAddress address, int i) throws IOException {
        Intrinsics.checkParameterIsNotNull(socket, "socket");
        Intrinsics.checkParameterIsNotNull(address, "address");
        socket.connect(address, i);
    }

    public static /* synthetic */ void log$default(Platform platform2, String str, int i, Throwable th, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                i = 4;
            }
            if ((i2 & 4) != 0) {
                th = null;
            }
            platform2.log(str, i, th);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: log");
    }

    public void log(@NotNull String message, int i, @Nullable Throwable th) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        logger.log(i == 5 ? Level.WARNING : Level.INFO, message, th);
    }

    @Nullable
    public Object getStackTraceForCloseable(@NotNull String closer) {
        Intrinsics.checkParameterIsNotNull(closer, "closer");
        if (logger.isLoggable(Level.FINE)) {
            return new Throwable(closer);
        }
        return null;
    }

    public void logCloseableLeak(@NotNull String message, @Nullable Object obj) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        if (obj == null) {
            message = message + " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);";
        }
        log(message, 5, (Throwable) obj);
    }

    @NotNull
    public CertificateChainCleaner buildCertificateChainCleaner(@NotNull X509TrustManager trustManager) {
        Intrinsics.checkParameterIsNotNull(trustManager, "trustManager");
        return new BasicCertificateChainCleaner(buildTrustRootIndex(trustManager));
    }

    @NotNull
    public final CertificateChainCleaner buildCertificateChainCleaner(@NotNull SSLSocketFactory sslSocketFactory) {
        Intrinsics.checkParameterIsNotNull(sslSocketFactory, "sslSocketFactory");
        X509TrustManager trustManager = trustManager(sslSocketFactory);
        if (trustManager != null) {
            return buildCertificateChainCleaner(trustManager);
        }
        throw new IllegalStateException("Unable to extract the trust manager on " + Companion.get() + ", sslSocketFactory is " + sslSocketFactory.getClass());
    }

    @NotNull
    public TrustRootIndex buildTrustRootIndex(@NotNull X509TrustManager trustManager) {
        Intrinsics.checkParameterIsNotNull(trustManager, "trustManager");
        X509Certificate[] acceptedIssuers = trustManager.getAcceptedIssuers();
        Intrinsics.checkExpressionValueIsNotNull(acceptedIssuers, "trustManager.acceptedIssuers");
        return new BasicTrustRootIndex((X509Certificate[]) Arrays.copyOf(acceptedIssuers, acceptedIssuers.length));
    }

    @NotNull
    public String toString() {
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkExpressionValueIsNotNull(simpleName, "javaClass.simpleName");
        return simpleName;
    }

    /* compiled from: Platform.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011J\u0014\u0010\u0015\u001a\u00020\u00162\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\b\u0010\u0018\u001a\u00020\u000fH\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\bR\u0014\u0010\n\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0016\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lokhttp3/internal/platform/Platform$Companion;", "", "()V", "INFO", "", "WARN", "isBouncyCastlePreferred", "", "()Z", "isConscryptPreferred", "isOpenJSSEPreferred", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "platform", "Lokhttp3/internal/platform/Platform;", "alpnProtocolNames", "", "", "protocols", "Lokhttp3/Protocol;", "concatLengthPrefixed", "", "findPlatform", BluetoothConstants.GET, "resetForTests", "", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final Platform get() {
            return Platform.platform;
        }

        public static /* synthetic */ void resetForTests$default(Companion companion, Platform platform, int i, Object obj) {
            if ((i & 1) != 0) {
                platform = companion.findPlatform();
            }
            companion.resetForTests(platform);
        }

        public final void resetForTests(@NotNull Platform platform) {
            Intrinsics.checkParameterIsNotNull(platform, "platform");
            Platform.platform = platform;
        }

        @NotNull
        public final List<String> alpnProtocolNames(@NotNull List<? extends Protocol> protocols) {
            Intrinsics.checkParameterIsNotNull(protocols, "protocols");
            ArrayList arrayList = new ArrayList();
            for (Object obj : protocols) {
                if (((Protocol) obj) != Protocol.HTTP_1_0) {
                    arrayList.add(obj);
                }
            }
            ArrayList<Protocol> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (Protocol protocol : arrayList2) {
                arrayList3.add(protocol.toString());
            }
            return arrayList3;
        }

        private final boolean isConscryptPreferred() {
            Provider provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(provider, "Security.getProviders()[0]");
            return Intrinsics.areEqual("Conscrypt", provider.getName());
        }

        private final boolean isOpenJSSEPreferred() {
            Provider provider = Security.getProviders()[0];
            Intrinsics.checkExpressionValueIsNotNull(provider, "Security.getProviders()[0]");
            return Intrinsics.areEqual("OpenJSSE", provider.getName());
        }

        private final boolean isBouncyCastlePreferred() {
            return Intrinsics.areEqual(System.getProperty("okhttp.platform"), "bouncycastle");
        }

        public final Platform findPlatform() {
            OpenJSSEPlatform buildIfSupported;
            BouncyCastlePlatform buildIfSupported2;
            ConscryptPlatform buildIfSupported3;
            Platform buildIfSupported4 = Android10Platform.Companion.buildIfSupported();
            if (buildIfSupported4 != null) {
                return buildIfSupported4;
            }
            Platform buildIfSupported5 = AndroidPlatform.Companion.buildIfSupported();
            if (buildIfSupported5 != null) {
                return buildIfSupported5;
            }
            Companion companion = this;
            if (companion.isConscryptPreferred() && (buildIfSupported3 = ConscryptPlatform.Companion.buildIfSupported()) != null) {
                return buildIfSupported3;
            }
            if (companion.isBouncyCastlePreferred() && (buildIfSupported2 = BouncyCastlePlatform.Companion.buildIfSupported()) != null) {
                return buildIfSupported2;
            }
            if (companion.isOpenJSSEPreferred() && (buildIfSupported = OpenJSSEPlatform.Companion.buildIfSupported()) != null) {
                return buildIfSupported;
            }
            Jdk9Platform buildIfSupported6 = Jdk9Platform.Companion.buildIfSupported();
            if (buildIfSupported6 != null) {
                return buildIfSupported6;
            }
            Platform buildIfSupported7 = Jdk8WithJettyBootPlatform.Companion.buildIfSupported();
            return buildIfSupported7 != null ? buildIfSupported7 : new Platform();
        }

        @NotNull
        public final byte[] concatLengthPrefixed(@NotNull List<? extends Protocol> protocols) {
            Intrinsics.checkParameterIsNotNull(protocols, "protocols");
            Buffer buffer = new Buffer();
            for (String str : alpnProtocolNames(protocols)) {
                buffer.writeByte(str.length());
                buffer.writeUtf8(str);
            }
            return buffer.readByteArray();
        }
    }
}
