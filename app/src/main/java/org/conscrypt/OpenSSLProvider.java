package org.conscrypt;

import androidx.exifinterface.media.ExifInterface;
import java.security.Provider;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes5.dex */
public final class OpenSSLProvider extends Provider {
    private static final String PREFIX = OpenSSLProvider.class.getPackage().getName() + ".";
    private static final String STANDARD_EC_PRIVATE_KEY_INTERFACE_CLASS_NAME = "java.security.interfaces.ECPrivateKey";
    private static final String STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME = "java.security.interfaces.RSAPrivateKey";
    private static final String STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME = "java.security.interfaces.RSAPublicKey";
    private static final long serialVersionUID = 2996752495318905136L;

    public OpenSSLProvider() {
        this(Platform.getDefaultProviderName());
    }

    public OpenSSLProvider(String str) {
        this(str, Platform.provideTrustManagerByDefault());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLProvider(String str, boolean z) {
        super(str, 1.0d, "Android's OpenSSL-backed security provider");
        NativeCrypto.checkAvailability();
        Platform.setup();
        String str2 = PREFIX + "OpenSSLContextImpl";
        String str3 = str2 + "$TLSv13";
        put("SSLContext.SSL", str3);
        put("SSLContext.TLS", str3);
        put("SSLContext.TLSv1", str2 + "$TLSv1");
        put("SSLContext.TLSv1.1", str2 + "$TLSv11");
        put("SSLContext.TLSv1.2", str2 + "$TLSv12");
        put("SSLContext.TLSv1.3", str3);
        put("SSLContext.Default", PREFIX + "DefaultSSLContextImpl");
        if (z) {
            put("TrustManagerFactory.PKIX", TrustManagerFactoryImpl.class.getName());
            put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
        }
        put("AlgorithmParameters.AES", PREFIX + "IvParameters$AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.2", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.22", "AES");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.42", "AES");
        put("AlgorithmParameters.ChaCha20", PREFIX + "IvParameters$ChaCha20");
        put("AlgorithmParameters.DESEDE", PREFIX + "IvParameters$DESEDE");
        put("Alg.Alias.AlgorithmParameters.TDEA", "DESEDE");
        put("Alg.Alias.AlgorithmParameters.1.2.840.113549.3.7", "DESEDE");
        put("AlgorithmParameters.GCM", PREFIX + "GCMParameters");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.6", "GCM");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.26", "GCM");
        put("Alg.Alias.AlgorithmParameters.2.16.840.1.101.3.4.1.46", "GCM");
        put("AlgorithmParameters.OAEP", PREFIX + "OAEPParameters");
        put("AlgorithmParameters.PSS", PREFIX + "PSSParameters");
        put("AlgorithmParameters.EC", PREFIX + "ECParameters");
        put("MessageDigest.SHA-1", PREFIX + "OpenSSLMessageDigestJDK$SHA1");
        put("Alg.Alias.MessageDigest.SHA1", MessageDigestAlgorithms.SHA_1);
        put("Alg.Alias.MessageDigest.SHA", MessageDigestAlgorithms.SHA_1);
        put("Alg.Alias.MessageDigest.1.3.14.3.2.26", MessageDigestAlgorithms.SHA_1);
        put("MessageDigest.SHA-224", PREFIX + "OpenSSLMessageDigestJDK$SHA224");
        put("Alg.Alias.MessageDigest.SHA224", "SHA-224");
        put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.4", "SHA-224");
        put("MessageDigest.SHA-256", PREFIX + "OpenSSLMessageDigestJDK$SHA256");
        put("Alg.Alias.MessageDigest.SHA256", MessageDigestAlgorithms.SHA_256);
        put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.1", MessageDigestAlgorithms.SHA_256);
        put("MessageDigest.SHA-384", PREFIX + "OpenSSLMessageDigestJDK$SHA384");
        put("Alg.Alias.MessageDigest.SHA384", MessageDigestAlgorithms.SHA_384);
        put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.2", MessageDigestAlgorithms.SHA_384);
        put("MessageDigest.SHA-512", PREFIX + "OpenSSLMessageDigestJDK$SHA512");
        put("Alg.Alias.MessageDigest.SHA512", MessageDigestAlgorithms.SHA_512);
        put("Alg.Alias.MessageDigest.2.16.840.1.101.3.4.2.3", MessageDigestAlgorithms.SHA_512);
        put("MessageDigest.MD5", PREFIX + "OpenSSLMessageDigestJDK$MD5");
        put("Alg.Alias.MessageDigest.1.2.840.113549.2.5", "MD5");
        put("KeyGenerator.ARC4", PREFIX + "KeyGeneratorImpl$ARC4");
        put("Alg.Alias.KeyGenerator.RC4", "ARC4");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
        put("KeyGenerator.AES", PREFIX + "KeyGeneratorImpl$AES");
        put("KeyGenerator.ChaCha20", PREFIX + "KeyGeneratorImpl$ChaCha20");
        put("KeyGenerator.DESEDE", PREFIX + "KeyGeneratorImpl$DESEDE");
        put("Alg.Alias.KeyGenerator.TDEA", "DESEDE");
        put("KeyGenerator.HmacMD5", PREFIX + "KeyGeneratorImpl$HmacMD5");
        put("Alg.Alias.KeyGenerator.1.3.6.1.5.5.8.1.1", "HmacMD5");
        put("Alg.Alias.KeyGenerator.HMAC-MD5", "HmacMD5");
        put("Alg.Alias.KeyGenerator.HMAC/MD5", "HmacMD5");
        put("KeyGenerator.HmacSHA1", PREFIX + "KeyGeneratorImpl$HmacSHA1");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.2.7", "HmacSHA1");
        put("Alg.Alias.KeyGenerator.1.3.6.1.5.5.8.1.2", "HmacSHA1");
        put("Alg.Alias.KeyGenerator.HMAC-SHA1", "HmacSHA1");
        put("Alg.Alias.KeyGenerator.HMAC/SHA1", "HmacSHA1");
        put("KeyGenerator.HmacSHA224", PREFIX + "KeyGeneratorImpl$HmacSHA224");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.2.8", "HmacSHA224");
        put("Alg.Alias.KeyGenerator.HMAC-SHA224", "HmacSHA224");
        put("Alg.Alias.KeyGenerator.HMAC/SHA224", "HmacSHA224");
        put("KeyGenerator.HmacSHA256", PREFIX + "KeyGeneratorImpl$HmacSHA256");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.2.9", "HmacSHA256");
        put("Alg.Alias.KeyGenerator.2.16.840.1.101.3.4.2.1", "HmacSHA256");
        put("Alg.Alias.KeyGenerator.HMAC-SHA256", "HmacSHA256");
        put("Alg.Alias.KeyGenerator.HMAC/SHA256", "HmacSHA256");
        put("KeyGenerator.HmacSHA384", PREFIX + "KeyGeneratorImpl$HmacSHA384");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.2.10", "HmacSHA384");
        put("Alg.Alias.KeyGenerator.HMAC-SHA384", "HmacSHA384");
        put("Alg.Alias.KeyGenerator.HMAC/SHA384", "HmacSHA384");
        put("KeyGenerator.HmacSHA512", PREFIX + "KeyGeneratorImpl$HmacSHA512");
        put("Alg.Alias.KeyGenerator.1.2.840.113549.2.11", "HmacSHA512");
        put("Alg.Alias.KeyGenerator.HMAC-SHA512", "HmacSHA512");
        put("Alg.Alias.KeyGenerator.HMAC/SHA512", "HmacSHA512");
        put("KeyPairGenerator.RSA", PREFIX + "OpenSSLRSAKeyPairGenerator");
        put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1.1", "RSA");
        put("Alg.Alias.KeyPairGenerator.1.2.840.113549.1.1.7", "RSA");
        put("Alg.Alias.KeyPairGenerator.2.5.8.1.1", "RSA");
        put("KeyPairGenerator.EC", PREFIX + "OpenSSLECKeyPairGenerator");
        put("Alg.Alias.KeyPairGenerator.1.2.840.10045.2.1", "EC");
        put("Alg.Alias.KeyPairGenerator.1.3.133.16.840.63.0.2", "EC");
        put("KeyFactory.RSA", PREFIX + "OpenSSLRSAKeyFactory");
        put("Alg.Alias.KeyFactory.1.2.840.113549.1.1.1", "RSA");
        put("Alg.Alias.KeyFactory.1.2.840.113549.1.1.7", "RSA");
        put("Alg.Alias.KeyFactory.2.5.8.1.1", "RSA");
        put("KeyFactory.EC", PREFIX + "OpenSSLECKeyFactory");
        put("Alg.Alias.KeyFactory.1.2.840.10045.2.1", "EC");
        put("Alg.Alias.KeyFactory.1.3.133.16.840.63.0.2", "EC");
        put("SecretKeyFactory.DESEDE", PREFIX + "DESEDESecretKeyFactory");
        put("Alg.Alias.SecretKeyFactory.TDEA", "DESEDE");
        putECDHKeyAgreementImplClass("OpenSSLECDHKeyAgreement");
        putSignatureImplClass("MD5withRSA", "OpenSSLSignature$MD5RSA");
        put("Alg.Alias.Signature.MD5withRSAEncryption", "MD5withRSA");
        put("Alg.Alias.Signature.MD5/RSA", "MD5withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.4", "MD5withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.4", "MD5withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.2.5with1.2.840.113549.1.1.1", "MD5withRSA");
        putSignatureImplClass("SHA1withRSA", "OpenSSLSignature$SHA1RSA");
        put("Alg.Alias.Signature.SHA1withRSAEncryption", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA1/RSA", "SHA1withRSA");
        put("Alg.Alias.Signature.SHA-1/RSA", "SHA1withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.1", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.113549.1.1.5", "SHA1withRSA");
        put("Alg.Alias.Signature.1.3.14.3.2.29", "SHA1withRSA");
        put("Alg.Alias.Signature.OID.1.3.14.3.2.29", "SHA1withRSA");
        putSignatureImplClass("SHA224withRSA", "OpenSSLSignature$SHA224RSA");
        put("Alg.Alias.Signature.SHA224withRSAEncryption", "SHA224withRSA");
        put("Alg.Alias.Signature.SHA224/RSA", "SHA224withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.14", "SHA224withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.14", "SHA224withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.1", "SHA224withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.113549.1.1.14", "SHA224withRSA");
        putSignatureImplClass("SHA256withRSA", "OpenSSLSignature$SHA256RSA");
        put("Alg.Alias.Signature.SHA256withRSAEncryption", "SHA256withRSA");
        put("Alg.Alias.Signature.SHA256/RSA", "SHA256withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.11", "SHA256withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.11", "SHA256withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.1", "SHA256withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.113549.1.1.11", "SHA256withRSA");
        putSignatureImplClass("SHA384withRSA", "OpenSSLSignature$SHA384RSA");
        put("Alg.Alias.Signature.SHA384withRSAEncryption", "SHA384withRSA");
        put("Alg.Alias.Signature.SHA384/RSA", "SHA384withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.12", "SHA384withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.12", "SHA384withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.113549.1.1.1", "SHA384withRSA");
        putSignatureImplClass("SHA512withRSA", "OpenSSLSignature$SHA512RSA");
        put("Alg.Alias.Signature.SHA512withRSAEncryption", "SHA512withRSA");
        put("Alg.Alias.Signature.SHA512/RSA", "SHA512withRSA");
        put("Alg.Alias.Signature.1.2.840.113549.1.1.13", "SHA512withRSA");
        put("Alg.Alias.Signature.OID.1.2.840.113549.1.1.13", "SHA512withRSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.113549.1.1.1", "SHA512withRSA");
        putRAWRSASignatureImplClass("OpenSSLSignatureRawRSA");
        putSignatureImplClass("NONEwithECDSA", "OpenSSLSignatureRawECDSA");
        putSignatureImplClass("SHA1withECDSA", "OpenSSLSignature$SHA1ECDSA");
        put("Alg.Alias.Signature.ECDSA", "SHA1withECDSA");
        put("Alg.Alias.Signature.ECDSAwithSHA1", "SHA1withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.1", "SHA1withECDSA");
        put("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10045.2.1", "SHA1withECDSA");
        putSignatureImplClass("SHA224withECDSA", "OpenSSLSignature$SHA224ECDSA");
        put("Alg.Alias.Signature.SHA224/ECDSA", "SHA224withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.1", "SHA224withECDSA");
        put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.1", "SHA224withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.4with1.2.840.10045.2.1", "SHA224withECDSA");
        putSignatureImplClass("SHA256withECDSA", "OpenSSLSignature$SHA256ECDSA");
        put("Alg.Alias.Signature.SHA256/ECDSA", "SHA256withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.2", "SHA256withECDSA");
        put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.2", "SHA256withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.1with1.2.840.10045.2.1", "SHA256withECDSA");
        putSignatureImplClass("SHA384withECDSA", "OpenSSLSignature$SHA384ECDSA");
        put("Alg.Alias.Signature.SHA384/ECDSA", "SHA384withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.3", "SHA384withECDSA");
        put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.3", "SHA384withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.2with1.2.840.10045.2.1", "SHA384withECDSA");
        putSignatureImplClass("SHA512withECDSA", "OpenSSLSignature$SHA512ECDSA");
        put("Alg.Alias.Signature.SHA512/ECDSA", "SHA512withECDSA");
        put("Alg.Alias.Signature.1.2.840.10045.4.3.4", "SHA512withECDSA");
        put("Alg.Alias.Signature.OID.1.2.840.10045.4.3.4", "SHA512withECDSA");
        put("Alg.Alias.Signature.2.16.840.1.101.3.4.2.3with1.2.840.10045.2.1", "SHA512withECDSA");
        putSignatureImplClass("SHA1withRSA/PSS", "OpenSSLSignature$SHA1RSAPSS");
        put("Alg.Alias.Signature.SHA1withRSAandMGF1", "SHA1withRSA/PSS");
        putSignatureImplClass("SHA224withRSA/PSS", "OpenSSLSignature$SHA224RSAPSS");
        put("Alg.Alias.Signature.SHA224withRSAandMGF1", "SHA224withRSA/PSS");
        putSignatureImplClass("SHA256withRSA/PSS", "OpenSSLSignature$SHA256RSAPSS");
        put("Alg.Alias.Signature.SHA256withRSAandMGF1", "SHA256withRSA/PSS");
        putSignatureImplClass("SHA384withRSA/PSS", "OpenSSLSignature$SHA384RSAPSS");
        put("Alg.Alias.Signature.SHA384withRSAandMGF1", "SHA384withRSA/PSS");
        putSignatureImplClass("SHA512withRSA/PSS", "OpenSSLSignature$SHA512RSAPSS");
        put("Alg.Alias.Signature.SHA512withRSAandMGF1", "SHA512withRSA/PSS");
        put("SecureRandom.SHA1PRNG", PREFIX + "OpenSSLRandom");
        put("SecureRandom.SHA1PRNG ImplementedIn", ExifInterface.TAG_SOFTWARE);
        putRSACipherImplClass("RSA/ECB/NoPadding", "OpenSSLCipherRSA$Raw");
        put("Alg.Alias.Cipher.RSA/None/NoPadding", "RSA/ECB/NoPadding");
        putRSACipherImplClass("RSA/ECB/PKCS1Padding", "OpenSSLCipherRSA$PKCS1");
        put("Alg.Alias.Cipher.RSA/None/PKCS1Padding", "RSA/ECB/PKCS1Padding");
        putRSACipherImplClass("RSA/ECB/OAEPPadding", "OpenSSLCipherRSA$OAEP$SHA1");
        put("Alg.Alias.Cipher.RSA/None/OAEPPadding", "RSA/ECB/OAEPPadding");
        putRSACipherImplClass("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA1");
        put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-1AndMGF1Padding", "RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        putRSACipherImplClass("RSA/ECB/OAEPWithSHA-224AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA224");
        put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-224AndMGF1Padding", "RSA/ECB/OAEPWithSHA-224AndMGF1Padding");
        putRSACipherImplClass("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA256");
        put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-256AndMGF1Padding", "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        putRSACipherImplClass("RSA/ECB/OAEPWithSHA-384AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA384");
        put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-384AndMGF1Padding", "RSA/ECB/OAEPWithSHA-384AndMGF1Padding");
        putRSACipherImplClass("RSA/ECB/OAEPWithSHA-512AndMGF1Padding", "OpenSSLCipherRSA$OAEP$SHA512");
        put("Alg.Alias.Cipher.RSA/None/OAEPWithSHA-512AndMGF1Padding", "RSA/ECB/OAEPWithSHA-512AndMGF1Padding");
        putSymmetricCipherImplClass("AES/ECB/NoPadding", "OpenSSLEvpCipherAES$AES$ECB$NoPadding");
        putSymmetricCipherImplClass("AES/ECB/PKCS5Padding", "OpenSSLEvpCipherAES$AES$ECB$PKCS5Padding");
        put("Alg.Alias.Cipher.AES/ECB/PKCS7Padding", "AES/ECB/PKCS5Padding");
        putSymmetricCipherImplClass("AES/CBC/NoPadding", "OpenSSLEvpCipherAES$AES$CBC$NoPadding");
        putSymmetricCipherImplClass("AES/CBC/PKCS5Padding", "OpenSSLEvpCipherAES$AES$CBC$PKCS5Padding");
        put("Alg.Alias.Cipher.AES/CBC/PKCS7Padding", "AES/CBC/PKCS5Padding");
        putSymmetricCipherImplClass("AES/CTR/NoPadding", "OpenSSLEvpCipherAES$AES$CTR");
        putSymmetricCipherImplClass("AES_128/ECB/NoPadding", "OpenSSLEvpCipherAES$AES_128$ECB$NoPadding");
        putSymmetricCipherImplClass("AES_128/ECB/PKCS5Padding", "OpenSSLEvpCipherAES$AES_128$ECB$PKCS5Padding");
        put("Alg.Alias.Cipher.AES_128/ECB/PKCS7Padding", "AES_128/ECB/PKCS5Padding");
        putSymmetricCipherImplClass("AES_128/CBC/NoPadding", "OpenSSLEvpCipherAES$AES_128$CBC$NoPadding");
        putSymmetricCipherImplClass("AES_128/CBC/PKCS5Padding", "OpenSSLEvpCipherAES$AES_128$CBC$PKCS5Padding");
        put("Alg.Alias.Cipher.AES_128/CBC/PKCS7Padding", "AES_128/CBC/PKCS5Padding");
        put("Alg.Alias.Cipher.PBEWithHmacSHA1AndAES_128", "AES_128/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA224AndAES_128", "AES_128/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA256AndAES_128", "AES_128/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA384AndAES_128", "AES_128/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA512AndAES_128", "AES_128/CBC/PKCS5PADDING");
        putSymmetricCipherImplClass("AES_256/ECB/NoPadding", "OpenSSLEvpCipherAES$AES_256$ECB$NoPadding");
        putSymmetricCipherImplClass("AES_256/ECB/PKCS5Padding", "OpenSSLEvpCipherAES$AES_256$ECB$PKCS5Padding");
        put("Alg.Alias.Cipher.AES_256/ECB/PKCS7Padding", "AES_256/ECB/PKCS5Padding");
        putSymmetricCipherImplClass("AES_256/CBC/NoPadding", "OpenSSLEvpCipherAES$AES_256$CBC$NoPadding");
        putSymmetricCipherImplClass("AES_256/CBC/PKCS5Padding", "OpenSSLEvpCipherAES$AES_256$CBC$PKCS5Padding");
        put("Alg.Alias.Cipher.AES_256/CBC/PKCS7Padding", "AES_256/CBC/PKCS5Padding");
        put("Alg.Alias.Cipher.PBEWithHmacSHA1AndAES_256", "AES_256/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA224AndAES_256", "AES_256/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA256AndAES_256", "AES_256/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA384AndAES_256", "AES_256/CBC/PKCS5PADDING");
        put("Alg.Alias.Cipher.PBEWithHmacSHA512AndAES_256", "AES_256/CBC/PKCS5PADDING");
        putSymmetricCipherImplClass("DESEDE/CBC/NoPadding", "OpenSSLEvpCipherDESEDE$CBC$NoPadding");
        putSymmetricCipherImplClass("DESEDE/CBC/PKCS5Padding", "OpenSSLEvpCipherDESEDE$CBC$PKCS5Padding");
        put("Alg.Alias.Cipher.DESEDE/CBC/PKCS7Padding", "DESEDE/CBC/PKCS5Padding");
        putSymmetricCipherImplClass("ARC4", "OpenSSLEvpCipherARC4");
        put("Alg.Alias.Cipher.ARCFOUR", "ARC4");
        put("Alg.Alias.Cipher.RC4", "ARC4");
        put("Alg.Alias.Cipher.1.2.840.113549.3.4", "ARC4");
        put("Alg.Alias.Cipher.OID.1.2.840.113549.3.4", "ARC4");
        putSymmetricCipherImplClass("AES/GCM/NoPadding", "OpenSSLAeadCipherAES$GCM");
        put("Alg.Alias.Cipher.GCM", "AES/GCM/NoPadding");
        put("Alg.Alias.Cipher.2.16.840.1.101.3.4.1.6", "AES/GCM/NoPadding");
        put("Alg.Alias.Cipher.2.16.840.1.101.3.4.1.26", "AES/GCM/NoPadding");
        put("Alg.Alias.Cipher.2.16.840.1.101.3.4.1.46", "AES/GCM/NoPadding");
        putSymmetricCipherImplClass("AES_128/GCM/NoPadding", "OpenSSLAeadCipherAES$GCM$AES_128");
        putSymmetricCipherImplClass("AES_256/GCM/NoPadding", "OpenSSLAeadCipherAES$GCM$AES_256");
        putSymmetricCipherImplClass("AES/GCM-SIV/NoPadding", "OpenSSLAeadCipherAES$GCM_SIV");
        putSymmetricCipherImplClass("AES_128/GCM-SIV/NoPadding", "OpenSSLAeadCipherAES$GCM_SIV$AES_128");
        putSymmetricCipherImplClass("AES_256/GCM-SIV/NoPadding", "OpenSSLAeadCipherAES$GCM_SIV$AES_256");
        putSymmetricCipherImplClass("ChaCha20", "OpenSSLCipherChaCha20");
        putSymmetricCipherImplClass("ChaCha20/Poly1305/NoPadding", "OpenSSLAeadCipherChaCha20");
        put("Alg.Alias.Cipher.ChaCha20-Poly1305", "ChaCha20/Poly1305/NoPadding");
        putMacImplClass("HmacMD5", "OpenSSLMac$HmacMD5");
        put("Alg.Alias.Mac.1.3.6.1.5.5.8.1.1", "HmacMD5");
        put("Alg.Alias.Mac.HMAC-MD5", "HmacMD5");
        put("Alg.Alias.Mac.HMAC/MD5", "HmacMD5");
        putMacImplClass("HmacSHA1", "OpenSSLMac$HmacSHA1");
        put("Alg.Alias.Mac.1.2.840.113549.2.7", "HmacSHA1");
        put("Alg.Alias.Mac.1.3.6.1.5.5.8.1.2", "HmacSHA1");
        put("Alg.Alias.Mac.HMAC-SHA1", "HmacSHA1");
        put("Alg.Alias.Mac.HMAC/SHA1", "HmacSHA1");
        putMacImplClass("HmacSHA224", "OpenSSLMac$HmacSHA224");
        put("Alg.Alias.Mac.1.2.840.113549.2.8", "HmacSHA224");
        put("Alg.Alias.Mac.HMAC-SHA224", "HmacSHA224");
        put("Alg.Alias.Mac.HMAC/SHA224", "HmacSHA224");
        put("Alg.Alias.Mac.PBEWITHHMACSHA224", "HmacSHA224");
        putMacImplClass("HmacSHA256", "OpenSSLMac$HmacSHA256");
        put("Alg.Alias.Mac.1.2.840.113549.2.9", "HmacSHA256");
        put("Alg.Alias.Mac.2.16.840.1.101.3.4.2.1", "HmacSHA256");
        put("Alg.Alias.Mac.HMAC-SHA256", "HmacSHA256");
        put("Alg.Alias.Mac.HMAC/SHA256", "HmacSHA256");
        put("Alg.Alias.Mac.PBEWITHHMACSHA256", "HmacSHA256");
        putMacImplClass("HmacSHA384", "OpenSSLMac$HmacSHA384");
        put("Alg.Alias.Mac.1.2.840.113549.2.10", "HmacSHA384");
        put("Alg.Alias.Mac.HMAC-SHA384", "HmacSHA384");
        put("Alg.Alias.Mac.HMAC/SHA384", "HmacSHA384");
        put("Alg.Alias.Mac.PBEWITHHMACSHA384", "HmacSHA384");
        putMacImplClass("HmacSHA512", "OpenSSLMac$HmacSHA512");
        put("Alg.Alias.Mac.1.2.840.113549.2.11", "HmacSHA512");
        put("Alg.Alias.Mac.HMAC-SHA512", "HmacSHA512");
        put("Alg.Alias.Mac.HMAC/SHA512", "HmacSHA512");
        put("Alg.Alias.Mac.PBEWITHHMACSHA512", "HmacSHA512");
        put("CertificateFactory.X509", PREFIX + "OpenSSLX509CertificateFactory");
        put("Alg.Alias.CertificateFactory.X.509", "X509");
    }

    private void putMacImplClass(String str, String str2) {
        String str3 = "Mac." + str;
        putImplClassWithKeyConstraints(str3, PREFIX + str2, PREFIX + "OpenSSLKeyHolder", "RAW");
    }

    private void putSymmetricCipherImplClass(String str, String str2) {
        putImplClassWithKeyConstraints("Cipher." + str, PREFIX + str2, null, "RAW");
    }

    private void putRSACipherImplClass(String str, String str2) {
        String str3 = "Cipher." + str;
        putImplClassWithKeyConstraints(str3, PREFIX + str2, PREFIX + "OpenSSLRSAPrivateKey|" + STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME + "|" + PREFIX + "OpenSSLRSAPublicKey|" + STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME, null);
    }

    private void putSignatureImplClass(String str, String str2) {
        String str3 = "Signature." + str;
        putImplClassWithKeyConstraints(str3, PREFIX + str2, PREFIX + "OpenSSLKeyHolder|" + STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME + "|" + STANDARD_EC_PRIVATE_KEY_INTERFACE_CLASS_NAME + "|" + STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME, "PKCS#8|X.509");
    }

    private void putRAWRSASignatureImplClass(String str) {
        putImplClassWithKeyConstraints("Signature.NONEwithRSA", PREFIX + str, PREFIX + "OpenSSLRSAPrivateKey|" + STANDARD_RSA_PRIVATE_KEY_INTERFACE_CLASS_NAME + "|" + PREFIX + "OpenSSLRSAPublicKey|" + STANDARD_RSA_PUBLIC_KEY_INTERFACE_CLASS_NAME, null);
    }

    private void putECDHKeyAgreementImplClass(String str) {
        putImplClassWithKeyConstraints("KeyAgreement.ECDH", PREFIX + str, PREFIX + "OpenSSLKeyHolder|" + STANDARD_EC_PRIVATE_KEY_INTERFACE_CLASS_NAME, "PKCS#8");
    }

    private void putImplClassWithKeyConstraints(String str, String str2, String str3, String str4) {
        put(str, str2);
        if (str3 != null) {
            put(str + " SupportedKeyClasses", str3);
        }
        if (str4 != null) {
            put(str + " SupportedKeyFormats", str4);
        }
    }
}
