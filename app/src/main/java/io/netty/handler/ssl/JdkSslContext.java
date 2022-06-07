package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;

/* loaded from: classes4.dex */
public class JdkSslContext extends SslContext {
    static final String[] a;
    static final List<String> b;
    static final Set<String> c;
    private static final InternalLogger e = InternalLoggerFactory.getInstance(JdkSslContext.class);
    private final String[] f;
    private final List<String> g;
    private final JdkApplicationProtocolNegotiator h;
    private final ClientAuth i;
    private final SSLContext j;
    private final boolean k;

    static {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, null, null);
            SSLEngine createSSLEngine = instance.createSSLEngine();
            String[] supportedProtocols = createSSLEngine.getSupportedProtocols();
            HashSet hashSet = new HashSet(supportedProtocols.length);
            for (String str : supportedProtocols) {
                hashSet.add(str);
            }
            ArrayList arrayList = new ArrayList();
            a(hashSet, arrayList, "TLSv1.2", "TLSv1.1", "TLSv1");
            if (!arrayList.isEmpty()) {
                a = (String[]) arrayList.toArray(new String[arrayList.size()]);
            } else {
                a = createSSLEngine.getEnabledProtocols();
            }
            String[] supportedCipherSuites = createSSLEngine.getSupportedCipherSuites();
            c = new HashSet(supportedCipherSuites.length);
            for (String str2 : supportedCipherSuites) {
                c.add(str2);
            }
            ArrayList arrayList2 = new ArrayList();
            a(c, arrayList2, "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_3DES_EDE_CBC_SHA");
            if (arrayList2.isEmpty()) {
                String[] enabledCipherSuites = createSSLEngine.getEnabledCipherSuites();
                for (String str3 : enabledCipherSuites) {
                    if (!str3.contains("_RC4_")) {
                        arrayList2.add(str3);
                    }
                }
            }
            b = Collections.unmodifiableList(arrayList2);
            if (e.isDebugEnabled()) {
                e.debug("Default protocols (JDK): {} ", Arrays.asList(a));
                e.debug("Default cipher suites (JDK): {}", b);
            }
        } catch (Exception e2) {
            throw new Error("failed to initialize the default SSL context", e2);
        }
    }

    private static void a(Set<String> set, List<String> list, String... strArr) {
        for (String str : strArr) {
            if (set.contains(str)) {
                list.add(str);
            }
        }
    }

    public JdkSslContext(SSLContext sSLContext, boolean z, ClientAuth clientAuth) {
        this(sSLContext, z, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, e.a, clientAuth);
    }

    public JdkSslContext(SSLContext sSLContext, boolean z, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, ClientAuth clientAuth) {
        this(sSLContext, z, iterable, cipherSuiteFilter, a(applicationProtocolConfig, !z), clientAuth);
    }

    public JdkSslContext(SSLContext sSLContext, boolean z, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, ClientAuth clientAuth) {
        this.h = (JdkApplicationProtocolNegotiator) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "apn");
        this.i = (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth");
        this.f = ((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter")).filterCipherSuites(iterable, b, c);
        this.g = Collections.unmodifiableList(Arrays.asList(this.f));
        this.j = (SSLContext) ObjectUtil.checkNotNull(sSLContext, "sslContext");
        this.k = z;
    }

    public final SSLContext context() {
        return this.j;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final boolean isClient() {
        return this.k;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final SSLSessionContext sessionContext() {
        if (isServer()) {
            return context().getServerSessionContext();
        }
        return context().getClientSessionContext();
    }

    @Override // io.netty.handler.ssl.SslContext
    public final List<String> cipherSuites() {
        return this.g;
    }

    @Override // io.netty.handler.ssl.SslContext
    public final long sessionCacheSize() {
        return sessionContext().getSessionCacheSize();
    }

    @Override // io.netty.handler.ssl.SslContext
    public final long sessionTimeout() {
        return sessionContext().getSessionTimeout();
    }

    @Override // io.netty.handler.ssl.SslContext
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        return a(context().createSSLEngine());
    }

    @Override // io.netty.handler.ssl.SslContext
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i) {
        return a(context().createSSLEngine(str, i));
    }

    private SSLEngine a(SSLEngine sSLEngine) {
        sSLEngine.setEnabledCipherSuites(this.f);
        sSLEngine.setEnabledProtocols(a);
        sSLEngine.setUseClientMode(isClient());
        if (isServer()) {
            switch (this.i) {
                case OPTIONAL:
                    sSLEngine.setWantClientAuth(true);
                    break;
                case REQUIRE:
                    sSLEngine.setNeedClientAuth(true);
                    break;
            }
        }
        return this.h.wrapperFactory().wrapSslEngine(sSLEngine, this.h, isServer());
    }

    @Override // io.netty.handler.ssl.SslContext
    public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.h;
    }

    static JdkApplicationProtocolNegotiator a(ApplicationProtocolConfig applicationProtocolConfig, boolean z) {
        if (applicationProtocolConfig == null) {
            return e.a;
        }
        switch (applicationProtocolConfig.protocol()) {
            case NONE:
                return e.a;
            case ALPN:
                if (z) {
                    switch (applicationProtocolConfig.selectorFailureBehavior()) {
                        case FATAL_ALERT:
                            return new JdkAlpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        case NO_ADVERTISE:
                            return new JdkAlpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + applicationProtocolConfig.selectorFailureBehavior() + " failure behavior");
                    }
                } else {
                    switch (applicationProtocolConfig.selectedListenerFailureBehavior()) {
                        case ACCEPT:
                            return new JdkAlpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        case FATAL_ALERT:
                            return new JdkAlpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + applicationProtocolConfig.selectedListenerFailureBehavior() + " failure behavior");
                    }
                }
            case NPN:
                if (z) {
                    switch (applicationProtocolConfig.selectedListenerFailureBehavior()) {
                        case ACCEPT:
                            return new JdkNpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        case FATAL_ALERT:
                            return new JdkNpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + applicationProtocolConfig.selectedListenerFailureBehavior() + " failure behavior");
                    }
                } else {
                    switch (applicationProtocolConfig.selectorFailureBehavior()) {
                        case FATAL_ALERT:
                            return new JdkNpnApplicationProtocolNegotiator(true, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        case NO_ADVERTISE:
                            return new JdkNpnApplicationProtocolNegotiator(false, (Iterable<String>) applicationProtocolConfig.supportedProtocols());
                        default:
                            throw new UnsupportedOperationException("JDK provider does not support " + applicationProtocolConfig.selectorFailureBehavior() + " failure behavior");
                    }
                }
            default:
                throw new UnsupportedOperationException("JDK provider does not support " + applicationProtocolConfig.protocol() + " protocol");
        }
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File file, File file2, String str, KeyManagerFactory keyManagerFactory) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
        String property = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (property == null) {
            property = "SunX509";
        }
        return buildKeyManagerFactory(file, property, file2, str, keyManagerFactory);
    }

    static KeyManagerFactory a(X509Certificate[] x509CertificateArr, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        String property = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (property == null) {
            property = "SunX509";
        }
        return a(x509CertificateArr, property, privateKey, str, keyManagerFactory);
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File file, String str, File file2, String str2, KeyManagerFactory keyManagerFactory) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
        return a(a(file), str, a(file2, str2), str2, keyManagerFactory);
    }

    static KeyManagerFactory a(X509Certificate[] x509CertificateArr, String str, PrivateKey privateKey, String str2, KeyManagerFactory keyManagerFactory) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
        char[] charArray = str2 == null ? EmptyArrays.EMPTY_CHARS : str2.toCharArray();
        KeyStore a2 = a(x509CertificateArr, privateKey, charArray);
        if (keyManagerFactory == null) {
            keyManagerFactory = KeyManagerFactory.getInstance(str);
        }
        keyManagerFactory.init(a2, charArray);
        return keyManagerFactory;
    }
}
