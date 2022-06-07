package io.netty.handler.ssl;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

/* compiled from: OpenSslX509Certificate.java */
/* loaded from: classes4.dex */
final class k extends X509Certificate {
    private final byte[] bytes;
    private X509Certificate wrapped;

    public k(byte[] bArr) {
        this.bytes = bArr;
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        a().checkValidity();
    }

    @Override // java.security.cert.X509Certificate
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        a().checkValidity(date);
    }

    @Override // java.security.cert.X509Certificate
    public int getVersion() {
        return a().getVersion();
    }

    @Override // java.security.cert.X509Certificate
    public BigInteger getSerialNumber() {
        return a().getSerialNumber();
    }

    @Override // java.security.cert.X509Certificate
    public Principal getIssuerDN() {
        return a().getIssuerDN();
    }

    @Override // java.security.cert.X509Certificate
    public Principal getSubjectDN() {
        return a().getSubjectDN();
    }

    @Override // java.security.cert.X509Certificate
    public Date getNotBefore() {
        return a().getNotBefore();
    }

    @Override // java.security.cert.X509Certificate
    public Date getNotAfter() {
        return a().getNotAfter();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return a().getTBSCertificate();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSignature() {
        return a().getSignature();
    }

    @Override // java.security.cert.X509Certificate
    public String getSigAlgName() {
        return a().getSigAlgName();
    }

    @Override // java.security.cert.X509Certificate
    public String getSigAlgOID() {
        return a().getSigAlgOID();
    }

    @Override // java.security.cert.X509Certificate
    public byte[] getSigAlgParams() {
        return a().getSigAlgParams();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getIssuerUniqueID() {
        return a().getIssuerUniqueID();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getSubjectUniqueID() {
        return a().getSubjectUniqueID();
    }

    @Override // java.security.cert.X509Certificate
    public boolean[] getKeyUsage() {
        return a().getKeyUsage();
    }

    @Override // java.security.cert.X509Certificate
    public int getBasicConstraints() {
        return a().getBasicConstraints();
    }

    @Override // java.security.cert.Certificate
    public byte[] getEncoded() {
        return (byte[]) this.bytes.clone();
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        a().verify(publicKey);
    }

    @Override // java.security.cert.Certificate
    public void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        a().verify(publicKey, str);
    }

    @Override // java.security.cert.Certificate
    public String toString() {
        return a().toString();
    }

    @Override // java.security.cert.Certificate
    public PublicKey getPublicKey() {
        return a().getPublicKey();
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        return a().hasUnsupportedCriticalExtension();
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getCriticalExtensionOIDs() {
        return a().getCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Extension
    public Set<String> getNonCriticalExtensionOIDs() {
        return a().getNonCriticalExtensionOIDs();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        return a().getExtensionValue(str);
    }

    private X509Certificate a() {
        X509Certificate x509Certificate = this.wrapped;
        if (x509Certificate != null) {
            return x509Certificate;
        }
        try {
            X509Certificate x509Certificate2 = (X509Certificate) SslContext.d.generateCertificate(new ByteArrayInputStream(this.bytes));
            this.wrapped = x509Certificate2;
            return x509Certificate2;
        } catch (CertificateException e) {
            throw new IllegalStateException(e);
        }
    }
}
