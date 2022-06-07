package io.netty.handler.ssl.util;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Date;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

/* compiled from: OpenJdkSelfSignedCertGenerator.java */
/* loaded from: classes4.dex */
final class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String[] a(String str, KeyPair keyPair, SecureRandom secureRandom, Date date, Date date2) throws Exception {
        PrivateKey privateKey = keyPair.getPrivate();
        X509CertInfo x509CertInfo = new X509CertInfo();
        X500Name x500Name = new X500Name("CN=" + str);
        x509CertInfo.set("version", new CertificateVersion(2));
        x509CertInfo.set("serialNumber", new CertificateSerialNumber(new BigInteger(64, secureRandom)));
        try {
            x509CertInfo.set("subject", new CertificateSubjectName(x500Name));
        } catch (CertificateException unused) {
            x509CertInfo.set("subject", x500Name);
        }
        try {
            x509CertInfo.set("issuer", new CertificateIssuerName(x500Name));
        } catch (CertificateException unused2) {
            x509CertInfo.set("issuer", x500Name);
        }
        x509CertInfo.set("validity", new CertificateValidity(date, date2));
        x509CertInfo.set("key", new CertificateX509Key(keyPair.getPublic()));
        x509CertInfo.set("algorithmID", new CertificateAlgorithmId(new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid)));
        X509CertImpl x509CertImpl = new X509CertImpl(x509CertInfo);
        x509CertImpl.sign(privateKey, "SHA1withRSA");
        x509CertInfo.set("algorithmID.algorithm", x509CertImpl.get("x509.algorithm"));
        X509CertImpl x509CertImpl2 = new X509CertImpl(x509CertInfo);
        x509CertImpl2.sign(privateKey, "SHA1withRSA");
        x509CertImpl2.verify(keyPair.getPublic());
        return SelfSignedCertificate.a(str, privateKey, x509CertImpl2);
    }
}
