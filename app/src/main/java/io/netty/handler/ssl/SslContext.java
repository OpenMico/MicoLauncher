package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

/* loaded from: classes4.dex */
public abstract class SslContext {
    static final CertificateFactory d;

    public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();

    public abstract List<String> cipherSuites();

    public abstract boolean isClient();

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator);

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i);

    public abstract long sessionCacheSize();

    public abstract SSLSessionContext sessionContext();

    public abstract long sessionTimeout();

    static {
        try {
            d = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new IllegalStateException("unable to instance X.509 CertificateFactory", e);
        }
    }

    public static SslProvider defaultServerProvider() {
        return a();
    }

    public static SslProvider defaultClientProvider() {
        return a();
    }

    private static SslProvider a() {
        if (OpenSsl.isAvailable()) {
            return SslProvider.OPENSSL;
        }
        return SslProvider.JDK;
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2) throws SSLException {
        return newServerContext(file, file2, (String) null);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String str) throws SSLException {
        return newServerContext(null, file, file2, str);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newServerContext((SslProvider) null, file, file2, str, iterable, iterable2, j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newServerContext((SslProvider) null, file, file2, str, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2) throws SSLException {
        return newServerContext(sslProvider, file, file2, null);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str) throws SSLException {
        return newServerContext(sslProvider, file, file2, str, (Iterable<String>) null, IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0L, 0L);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newServerContext(sslProvider, file, file2, str, iterable, IdentityCipherSuiteFilter.INSTANCE, a(iterable2), j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newServerContext(sslProvider, null, trustManagerFactory, file, file2, str, null, iterable, IdentityCipherSuiteFilter.INSTANCE, a(iterable2), j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newServerContext(sslProvider, null, null, file, file2, str, null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        try {
            return a(sslProvider, a(file), trustManagerFactory, a(file2), a(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, ClientAuth.NONE);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the server-side SSL context", e);
        }
    }

    public static SslContext a(SslProvider sslProvider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth) throws SSLException {
        SslProvider defaultServerProvider = sslProvider == null ? defaultServerProvider() : sslProvider;
        switch (defaultServerProvider) {
            case JDK:
                return new JdkSslServerContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, clientAuth);
            case OPENSSL:
                return new OpenSslServerContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, clientAuth);
            default:
                throw new Error(defaultServerProvider.toString());
        }
    }

    @Deprecated
    public static SslContext newClientContext() throws SSLException {
        return newClientContext(null, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(File file) throws SSLException {
        return newClientContext((SslProvider) null, file);
    }

    @Deprecated
    public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, file, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newClientContext((SslProvider) null, file, trustManagerFactory, iterable, iterable2, j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newClientContext(null, file, trustManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider) throws SSLException {
        return newClientContext(sslProvider, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file) throws SSLException {
        return newClientContext(sslProvider, file, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(sslProvider, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, IdentityCipherSuiteFilter.INSTANCE, null, 0L, 0L);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, null, null, null, iterable, IdentityCipherSuiteFilter.INSTANCE, a(iterable2), j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, null, null, null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        try {
            return a(sslProvider, a(file), trustManagerFactory, a(file2), a(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the client-side SSL context", e);
        }
    }

    public static SslContext a(SslProvider sslProvider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        switch (sslProvider == null ? defaultClientProvider() : sslProvider) {
            case JDK:
                return new JdkSslClientContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
            case OPENSSL:
                return new OpenSslClientContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
            default:
                throw new Error();
        }
    }

    static ApplicationProtocolConfig a(Iterable<String> iterable) {
        if (iterable == null) {
            return ApplicationProtocolConfig.DISABLED;
        }
        return new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN_AND_ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, iterable);
    }

    public final boolean isServer() {
        return !isClient();
    }

    @Deprecated
    public final List<String> nextProtocols() {
        return applicationProtocolNegotiator().protocols();
    }

    public final SslHandler newHandler(ByteBufAllocator byteBufAllocator) {
        return a(newEngine(byteBufAllocator));
    }

    public final SslHandler newHandler(ByteBufAllocator byteBufAllocator, String str, int i) {
        return a(newEngine(byteBufAllocator, str, i));
    }

    private static SslHandler a(SSLEngine sSLEngine) {
        return new SslHandler(sSLEngine);
    }

    protected static PKCS8EncodedKeySpec generateKeySpec(char[] cArr, byte[] bArr) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (cArr == null) {
            return new PKCS8EncodedKeySpec(bArr);
        }
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(bArr);
        SecretKey generateSecret = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName()).generateSecret(new PBEKeySpec(cArr));
        Cipher instance = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        instance.init(2, generateSecret, encryptedPrivateKeyInfo.getAlgParameters());
        return encryptedPrivateKeyInfo.getKeySpec(instance);
    }

    static KeyStore a(X509Certificate[] x509CertificateArr, PrivateKey privateKey, char[] cArr) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore instance = KeyStore.getInstance("JKS");
        instance.load(null, null);
        instance.setKeyEntry("key", privateKey, cArr, x509CertificateArr);
        return instance;
    }

    public static PrivateKey a(File file, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (file == null) {
            return null;
        }
        return a(l.b(file), str);
    }

    public static PrivateKey a(InputStream inputStream, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (inputStream == null) {
            return null;
        }
        return a(l.b(inputStream), str);
    }

    private static PrivateKey a(ByteBuf byteBuf, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        byte[] bArr = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bArr).release();
        PKCS8EncodedKeySpec generateKeySpec = generateKeySpec(str == null ? null : str.toCharArray(), bArr);
        try {
            try {
                try {
                    return KeyFactory.getInstance("RSA").generatePrivate(generateKeySpec);
                } catch (InvalidKeySpecException unused) {
                    return KeyFactory.getInstance("DSA").generatePrivate(generateKeySpec);
                }
            } catch (InvalidKeySpecException e) {
                throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e);
            }
        } catch (InvalidKeySpecException unused2) {
            return KeyFactory.getInstance("EC").generatePrivate(generateKeySpec);
        }
    }

    @Deprecated
    protected static TrustManagerFactory buildTrustManagerFactory(File file, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        return a(a(file), trustManagerFactory);
    }

    public static X509Certificate[] a(File file) throws CertificateException {
        if (file == null) {
            return null;
        }
        return a(l.a(file));
    }

    public static X509Certificate[] a(InputStream inputStream) throws CertificateException {
        if (inputStream == null) {
            return null;
        }
        return a(l.a(inputStream));
    }

    private static X509Certificate[] a(ByteBuf[] byteBufArr) throws CertificateException {
        CertificateFactory instance = CertificateFactory.getInstance("X.509");
        X509Certificate[] x509CertificateArr = new X509Certificate[byteBufArr.length];
        int i = 0;
        for (int i2 = 0; i2 < byteBufArr.length; i2++) {
            try {
                x509CertificateArr[i2] = (X509Certificate) instance.generateCertificate(new ByteBufInputStream(byteBufArr[i2]));
            } finally {
                int length = byteBufArr.length;
                while (i < length) {
                    byteBufArr[i].release();
                    i++;
                }
            }
        }
        return x509CertificateArr;
    }

    static TrustManagerFactory a(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        KeyStore instance = KeyStore.getInstance("JKS");
        instance.load(null, null);
        int i = 1;
        for (X509Certificate x509Certificate : x509CertificateArr) {
            instance.setCertificateEntry(Integer.toString(i), x509Certificate);
            i++;
        }
        if (trustManagerFactory == null) {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        }
        trustManagerFactory.init(instance);
        return trustManagerFactory;
    }

    static PrivateKey b(File file, String str) throws SSLException {
        try {
            return a(file, str);
        } catch (Exception e) {
            throw new SSLException(e);
        }
    }

    static X509Certificate[] b(File file) throws SSLException {
        try {
            return a(file);
        } catch (CertificateException e) {
            throw new SSLException(e);
        }
    }
}
