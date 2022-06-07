package org.eclipse.jetty.server.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class SslCertificates {
    private static final Logger LOG = Log.getLogger(SslCertificates.class);
    static final String CACHED_INFO_ATTR = CachedInfo.class.getName();

    public static X509Certificate[] getCertChain(SSLSession sSLSession) {
        try {
            javax.security.cert.X509Certificate[] peerCertificateChain = sSLSession.getPeerCertificateChain();
            if (!(peerCertificateChain == null || peerCertificateChain.length == 0)) {
                int length = peerCertificateChain.length;
                X509Certificate[] x509CertificateArr = new X509Certificate[length];
                CertificateFactory instance = CertificateFactory.getInstance("X.509");
                for (int i = 0; i < length; i++) {
                    x509CertificateArr[i] = (X509Certificate) instance.generateCertificate(new ByteArrayInputStream(peerCertificateChain[i].getEncoded()));
                }
                return x509CertificateArr;
            }
            return null;
        } catch (SSLPeerUnverifiedException unused) {
            return null;
        } catch (Exception e) {
            LOG.warn(Log.EXCEPTION, e);
            return null;
        }
    }

    public static void customize(SSLSession sSLSession, EndPoint endPoint, Request request) throws IOException {
        Object obj;
        X509Certificate[] x509CertificateArr;
        Object obj2;
        request.setScheme("https");
        try {
            String cipherSuite = sSLSession.getCipherSuite();
            CachedInfo cachedInfo = (CachedInfo) sSLSession.getValue(CACHED_INFO_ATTR);
            if (cachedInfo != null) {
                obj = cachedInfo.getKeySize();
                x509CertificateArr = cachedInfo.getCerts();
                obj2 = cachedInfo.getIdStr();
            } else {
                Integer num = new Integer(ServletSSL.deduceKeyLength(cipherSuite));
                x509CertificateArr = getCertChain(sSLSession);
                String hexString = TypeUtil.toHexString(sSLSession.getId());
                sSLSession.putValue(CACHED_INFO_ATTR, new CachedInfo(num, x509CertificateArr, hexString));
                obj = num;
                obj2 = hexString;
            }
            if (x509CertificateArr != null) {
                request.setAttribute("javax.servlet.request.X509Certificate", x509CertificateArr);
            }
            request.setAttribute("javax.servlet.request.cipher_suite", cipherSuite);
            request.setAttribute("javax.servlet.request.key_size", obj);
            request.setAttribute("javax.servlet.request.ssl_session_id", obj2);
        } catch (Exception e) {
            LOG.warn(Log.EXCEPTION, e);
        }
    }

    /* loaded from: classes5.dex */
    private static class CachedInfo {
        private final X509Certificate[] _certs;
        private final String _idStr;
        private final Integer _keySize;

        CachedInfo(Integer num, X509Certificate[] x509CertificateArr, String str) {
            this._keySize = num;
            this._certs = x509CertificateArr;
            this._idStr = str;
        }

        X509Certificate[] getCerts() {
            return this._certs;
        }

        Integer getKeySize() {
            return this._keySize;
        }

        String getIdStr() {
            return this._idStr;
        }
    }
}
