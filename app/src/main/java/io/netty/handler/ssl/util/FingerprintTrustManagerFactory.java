package io.netty.handler.ssl.util;

import com.xiaomi.mipush.sdk.Constants;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public final class FingerprintTrustManagerFactory extends SimpleTrustManagerFactory {
    private static final Pattern a = Pattern.compile("^[0-9a-fA-F:]+$");
    private static final Pattern b = Pattern.compile(Constants.COLON_SEPARATOR);
    private static final FastThreadLocal<MessageDigest> c = new FastThreadLocal<MessageDigest>() { // from class: io.netty.handler.ssl.util.FingerprintTrustManagerFactory.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                throw new Error(e);
            }
        }
    };
    private final TrustManager d;
    private final byte[][] e;

    @Override // io.netty.handler.ssl.util.SimpleTrustManagerFactory
    protected void engineInit(KeyStore keyStore) throws Exception {
    }

    @Override // io.netty.handler.ssl.util.SimpleTrustManagerFactory
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
    }

    public FingerprintTrustManagerFactory(Iterable<String> iterable) {
        this(a(iterable));
    }

    public FingerprintTrustManagerFactory(String... strArr) {
        this(a(Arrays.asList(strArr)));
    }

    public FingerprintTrustManagerFactory(byte[]... bArr) {
        this.d = new X509TrustManager() { // from class: io.netty.handler.ssl.util.FingerprintTrustManagerFactory.2
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                a("client", x509CertificateArr);
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                a("server", x509CertificateArr);
            }

            private void a(String str, X509Certificate[] x509CertificateArr) throws CertificateException {
                boolean z = false;
                X509Certificate x509Certificate = x509CertificateArr[0];
                byte[] a2 = a(x509Certificate);
                byte[][] bArr2 = FingerprintTrustManagerFactory.this.e;
                int length = bArr2.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (Arrays.equals(a2, bArr2[i])) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    throw new CertificateException(str + " certificate with unknown fingerprint: " + x509Certificate.getSubjectDN());
                }
            }

            private byte[] a(X509Certificate x509Certificate) throws CertificateEncodingException {
                MessageDigest messageDigest = (MessageDigest) FingerprintTrustManagerFactory.c.get();
                messageDigest.reset();
                return messageDigest.digest(x509Certificate.getEncoded());
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return EmptyArrays.EMPTY_X509_CERTIFICATES;
            }
        };
        if (bArr != null) {
            ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
            for (byte[] bArr2 : bArr) {
                if (bArr2 == null) {
                    break;
                } else if (bArr2.length == 20) {
                    arrayList.add(bArr2.clone());
                } else {
                    throw new IllegalArgumentException("malformed fingerprint: " + ByteBufUtil.hexDump(Unpooled.wrappedBuffer(bArr2)) + " (expected: SHA1)");
                }
            }
            this.e = (byte[][]) arrayList.toArray(new byte[arrayList.size()]);
            return;
        }
        throw new NullPointerException("fingerprints");
    }

    private static byte[][] a(Iterable<String> iterable) {
        String next;
        if (iterable != null) {
            ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
            Iterator<String> it = iterable.iterator();
            while (it.hasNext() && (next = it.next()) != null) {
                if (a.matcher(next).matches()) {
                    String replaceAll = b.matcher(next).replaceAll("");
                    if (replaceAll.length() == 40) {
                        byte[] bArr = new byte[20];
                        for (int i = 0; i < bArr.length; i++) {
                            int i2 = i << 1;
                            bArr[i] = (byte) Integer.parseInt(replaceAll.substring(i2, i2 + 2), 16);
                        }
                        arrayList.add(bArr);
                    } else {
                        throw new IllegalArgumentException("malformed fingerprint: " + replaceAll + " (expected: SHA1)");
                    }
                } else {
                    throw new IllegalArgumentException("malformed fingerprint: " + next);
                }
            }
            return (byte[][]) arrayList.toArray(new byte[arrayList.size()]);
        }
        throw new NullPointerException("fingerprints");
    }

    @Override // io.netty.handler.ssl.util.SimpleTrustManagerFactory
    protected TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{this.d};
    }
}
