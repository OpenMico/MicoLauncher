package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.io.File;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

/* loaded from: classes4.dex */
public final class SslContextBuilder {
    private final boolean a;
    private SslProvider b;
    private X509Certificate[] c;
    private TrustManagerFactory d;
    private X509Certificate[] e;
    private PrivateKey f;
    private String g;
    private KeyManagerFactory h;
    private Iterable<String> i;
    private ApplicationProtocolConfig k;
    private long l;
    private long m;
    private CipherSuiteFilter j = IdentityCipherSuiteFilter.INSTANCE;
    private ClientAuth n = ClientAuth.NONE;

    public static SslContextBuilder forClient() {
        return new SslContextBuilder(false);
    }

    public static SslContextBuilder forServer(File file, File file2) {
        return new SslContextBuilder(true).keyManager(file, file2);
    }

    public static SslContextBuilder forServer(InputStream inputStream, InputStream inputStream2) {
        return new SslContextBuilder(true).keyManager(inputStream, inputStream2);
    }

    public static SslContextBuilder forServer(PrivateKey privateKey, X509Certificate... x509CertificateArr) {
        return new SslContextBuilder(true).keyManager(privateKey, x509CertificateArr);
    }

    public static SslContextBuilder forServer(File file, File file2, String str) {
        return new SslContextBuilder(true).keyManager(file, file2, str);
    }

    public static SslContextBuilder forServer(InputStream inputStream, InputStream inputStream2, String str) {
        return new SslContextBuilder(true).keyManager(inputStream, inputStream2, str);
    }

    public static SslContextBuilder forServer(PrivateKey privateKey, String str, X509Certificate... x509CertificateArr) {
        return new SslContextBuilder(true).keyManager(privateKey, str, x509CertificateArr);
    }

    public static SslContextBuilder forServer(KeyManagerFactory keyManagerFactory) {
        return new SslContextBuilder(true).keyManager(keyManagerFactory);
    }

    private SslContextBuilder(boolean z) {
        this.a = z;
    }

    public SslContextBuilder sslProvider(SslProvider sslProvider) {
        this.b = sslProvider;
        return this;
    }

    public SslContextBuilder trustManager(File file) {
        try {
            return trustManager(SslContext.a(file));
        } catch (Exception e) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + file, e);
        }
    }

    public SslContextBuilder trustManager(InputStream inputStream) {
        try {
            return trustManager(SslContext.a(inputStream));
        } catch (Exception e) {
            throw new IllegalArgumentException("Input stream does not contain valid certificates.", e);
        }
    }

    public SslContextBuilder trustManager(X509Certificate... x509CertificateArr) {
        this.c = x509CertificateArr != null ? (X509Certificate[]) x509CertificateArr.clone() : null;
        this.d = null;
        return this;
    }

    public SslContextBuilder trustManager(TrustManagerFactory trustManagerFactory) {
        this.c = null;
        this.d = trustManagerFactory;
        return this;
    }

    public SslContextBuilder keyManager(File file, File file2) {
        return keyManager(file, file2, (String) null);
    }

    public SslContextBuilder keyManager(InputStream inputStream, InputStream inputStream2) {
        return keyManager(inputStream, inputStream2, (String) null);
    }

    public SslContextBuilder keyManager(PrivateKey privateKey, X509Certificate... x509CertificateArr) {
        return keyManager(privateKey, (String) null, x509CertificateArr);
    }

    public SslContextBuilder keyManager(File file, File file2, String str) {
        try {
            try {
                return keyManager(SslContext.a(file2, str), str, SslContext.a(file));
            } catch (Exception e) {
                throw new IllegalArgumentException("File does not contain valid private key: " + file2, e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + file, e2);
        }
    }

    public SslContextBuilder keyManager(InputStream inputStream, InputStream inputStream2, String str) {
        try {
            try {
                return keyManager(SslContext.a(inputStream2, str), str, SslContext.a(inputStream));
            } catch (Exception e) {
                throw new IllegalArgumentException("Input stream does not contain valid private key.", e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("Input stream not contain valid certificates.", e2);
        }
    }

    public SslContextBuilder keyManager(PrivateKey privateKey, String str, X509Certificate... x509CertificateArr) {
        if (this.a) {
            ObjectUtil.checkNotNull(x509CertificateArr, "keyCertChain required for servers");
            if (x509CertificateArr.length != 0) {
                ObjectUtil.checkNotNull(privateKey, "key required for servers");
            } else {
                throw new IllegalArgumentException("keyCertChain must be non-empty");
            }
        }
        if (x509CertificateArr == null || x509CertificateArr.length == 0) {
            this.e = null;
        } else {
            for (X509Certificate x509Certificate : x509CertificateArr) {
                if (x509Certificate == null) {
                    throw new IllegalArgumentException("keyCertChain contains null entry");
                }
            }
            this.e = (X509Certificate[]) x509CertificateArr.clone();
        }
        this.f = privateKey;
        this.g = str;
        this.h = null;
        return this;
    }

    public SslContextBuilder keyManager(KeyManagerFactory keyManagerFactory) {
        if (this.a) {
            ObjectUtil.checkNotNull(keyManagerFactory, "keyManagerFactory required for servers");
        }
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = keyManagerFactory;
        return this;
    }

    public SslContextBuilder ciphers(Iterable<String> iterable) {
        return ciphers(iterable, IdentityCipherSuiteFilter.INSTANCE);
    }

    public SslContextBuilder ciphers(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter) {
        ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter");
        this.i = iterable;
        this.j = cipherSuiteFilter;
        return this;
    }

    public SslContextBuilder applicationProtocolConfig(ApplicationProtocolConfig applicationProtocolConfig) {
        this.k = applicationProtocolConfig;
        return this;
    }

    public SslContextBuilder sessionCacheSize(long j) {
        this.l = j;
        return this;
    }

    public SslContextBuilder sessionTimeout(long j) {
        this.m = j;
        return this;
    }

    public SslContextBuilder clientAuth(ClientAuth clientAuth) {
        this.n = (ClientAuth) ObjectUtil.checkNotNull(clientAuth, "clientAuth");
        return this;
    }

    public SslContext build() throws SSLException {
        return this.a ? SslContext.a(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n) : SslContext.a(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
    }
}
