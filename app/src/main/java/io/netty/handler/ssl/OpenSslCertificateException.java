package io.netty.handler.ssl;

import java.security.cert.CertificateException;

/* loaded from: classes4.dex */
public final class OpenSslCertificateException extends CertificateException {
    private static final long serialVersionUID = 5542675253797129798L;
    private final int errorCode;

    public OpenSslCertificateException(int i) {
        this((String) null, i);
    }

    public OpenSslCertificateException(String str, int i) {
        super(str);
        this.errorCode = a(i);
    }

    public OpenSslCertificateException(String str, Throwable th, int i) {
        super(str, th);
        this.errorCode = a(i);
    }

    public OpenSslCertificateException(Throwable th, int i) {
        this(null, th, i);
    }

    public int errorCode() {
        return this.errorCode;
    }

    private static int a(int i) {
        if (i >= 0 && i <= 65) {
            return i;
        }
        throw new IllegalArgumentException("errorCode must be 0 => 65. See https://www.openssl.org/docs/manmaster/apps/verify.html .");
    }
}
