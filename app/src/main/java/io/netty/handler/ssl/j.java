package io.netty.handler.ssl;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Date;
import javax.security.cert.CertificateException;
import javax.security.cert.CertificateExpiredException;
import javax.security.cert.CertificateNotYetValidException;
import javax.security.cert.X509Certificate;

/* compiled from: OpenSslJavaxX509Certificate.java */
/* loaded from: classes4.dex */
final class j extends X509Certificate {
    private final byte[] a;
    private X509Certificate b;

    public j(byte[] bArr) {
        this.a = bArr;
    }

    @Override // javax.security.cert.X509Certificate
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        a().checkValidity();
    }

    @Override // javax.security.cert.X509Certificate
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        a().checkValidity(date);
    }

    @Override // javax.security.cert.X509Certificate
    public int getVersion() {
        return a().getVersion();
    }

    @Override // javax.security.cert.X509Certificate
    public BigInteger getSerialNumber() {
        return a().getSerialNumber();
    }

    @Override // javax.security.cert.X509Certificate
    public Principal getIssuerDN() {
        return a().getIssuerDN();
    }

    @Override // javax.security.cert.X509Certificate
    public Principal getSubjectDN() {
        return a().getSubjectDN();
    }

    @Override // javax.security.cert.X509Certificate
    public Date getNotBefore() {
        return a().getNotBefore();
    }

    @Override // javax.security.cert.X509Certificate
    public Date getNotAfter() {
        return a().getNotAfter();
    }

    @Override // javax.security.cert.X509Certificate
    public String getSigAlgName() {
        return a().getSigAlgName();
    }

    @Override // javax.security.cert.X509Certificate
    public String getSigAlgOID() {
        return a().getSigAlgOID();
    }

    @Override // javax.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        return a().getSigAlgParams();
    }

    @Override // javax.security.cert.Certificate
    public byte[] getEncoded() {
        return (byte[]) this.a.clone();
    }

    @Override // javax.security.cert.Certificate
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        a().verify(publicKey);
    }

    @Override // javax.security.cert.Certificate
    public void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        a().verify(publicKey, str);
    }

    @Override // javax.security.cert.Certificate
    public String toString() {
        return a().toString();
    }

    @Override // javax.security.cert.Certificate
    public PublicKey getPublicKey() {
        return a().getPublicKey();
    }

    private X509Certificate a() {
        X509Certificate x509Certificate = this.b;
        if (x509Certificate != null) {
            return x509Certificate;
        }
        try {
            X509Certificate instance = X509Certificate.getInstance(this.a);
            this.b = instance;
            return instance;
        } catch (CertificateException e) {
            throw new IllegalStateException(e);
        }
    }
}
