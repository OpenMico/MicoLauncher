package io.netty.handler.ssl.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

/* loaded from: classes4.dex */
public final class SelfSignedCertificate {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
    private static final Date b = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotBefore", System.currentTimeMillis() - 31536000000L));
    private static final Date c = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
    private final File d;
    private final File e;
    private final X509Certificate f;
    private final PrivateKey g;

    public SelfSignedCertificate() throws CertificateException {
        this(b, c);
    }

    public SelfSignedCertificate(Date date, Date date2) throws CertificateException {
        this("example.com", date, date2);
    }

    public SelfSignedCertificate(String str) throws CertificateException {
        this(str, b, c);
    }

    public SelfSignedCertificate(String str, Date date, Date date2) throws CertificateException {
        this(str, c.a(), 1024, date, date2);
    }

    public SelfSignedCertificate(String str, SecureRandom secureRandom, int i) throws CertificateException {
        this(str, secureRandom, i, b, c);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.security.SecureRandom] */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r6v9 */
    public SelfSignedCertificate(String str, SecureRandom secureRandom, int i, Date date, Date date2) throws CertificateException {
        String[] strArr;
        Throwable th;
        Exception e;
        CertificateException certificateException;
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
            instance.initialize(i, (SecureRandom) secureRandom);
            KeyPair generateKeyPair = instance.generateKeyPair();
            try {
                try {
                    strArr = b.a(str, generateKeyPair, secureRandom, date, date2);
                } finally {
                    try {
                        this.d = new File(strArr[0]);
                        this.e = new File(strArr[1]);
                        this.g = generateKeyPair.getPrivate();
                        secureRandom = 0;
                        FileInputStream fileInputStream = new FileInputStream(this.d);
                        try {
                            this.f = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(fileInputStream);
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                                InternalLogger internalLogger = a;
                                internalLogger.warn("Failed to close a file: " + this.d, (Throwable) e2);
                                return;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            throw new CertificateEncodingException(e);
                        }
                    } catch (Throwable th2) {
                    }
                }
                this.d = new File(strArr[0]);
                this.e = new File(strArr[1]);
                this.g = generateKeyPair.getPrivate();
                secureRandom = 0;
            } catch (Throwable th3) {
                th = th3;
            }
            try {
                FileInputStream fileInputStream2 = new FileInputStream(this.d);
                this.f = (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(fileInputStream2);
                fileInputStream2.close();
            } catch (Exception e4) {
                e = e4;
            } catch (Throwable th4) {
                th = th4;
                if (secureRandom != 0) {
                    try {
                        secureRandom.close();
                    } catch (IOException e5) {
                        InternalLogger internalLogger2 = a;
                        internalLogger2.warn("Failed to close a file: " + this.d, (Throwable) e5);
                    }
                }
                throw th;
            }
        } catch (NoSuchAlgorithmException e6) {
            throw new Error(e6);
        }
    }

    public File certificate() {
        return this.d;
    }

    public File privateKey() {
        return this.e;
    }

    public X509Certificate cert() {
        return this.f;
    }

    public PrivateKey key() {
        return this.g;
    }

    public void delete() {
        a(this.d);
        a(this.e);
    }

    /* JADX WARN: Finally extract failed */
    public static String[] a(String str, PrivateKey privateKey, X509Certificate x509Certificate) throws IOException, CertificateEncodingException {
        ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(privateKey.getEncoded());
        try {
            ByteBuf encode = Base64.encode(wrappedBuffer, true);
            String str2 = "-----BEGIN PRIVATE KEY-----\n" + encode.toString(CharsetUtil.US_ASCII) + "\n-----END PRIVATE KEY-----\n";
            encode.release();
            wrappedBuffer.release();
            File createTempFile = File.createTempFile("keyutil_" + str + '_', ".key");
            createTempFile.deleteOnExit();
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            try {
                fileOutputStream.write(str2.getBytes(CharsetUtil.US_ASCII));
                fileOutputStream.close();
                ByteBuf wrappedBuffer2 = Unpooled.wrappedBuffer(x509Certificate.getEncoded());
                try {
                    ByteBuf encode2 = Base64.encode(wrappedBuffer2, true);
                    String str3 = "-----BEGIN CERTIFICATE-----\n" + encode2.toString(CharsetUtil.US_ASCII) + "\n-----END CERTIFICATE-----\n";
                    encode2.release();
                    wrappedBuffer2.release();
                    File createTempFile2 = File.createTempFile("keyutil_" + str + '_', ".crt");
                    createTempFile2.deleteOnExit();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(createTempFile2);
                    try {
                        fileOutputStream2.write(str3.getBytes(CharsetUtil.US_ASCII));
                        fileOutputStream2.close();
                        return new String[]{createTempFile2.getPath(), createTempFile.getPath()};
                    } catch (Throwable th) {
                        a(createTempFile2, fileOutputStream2);
                        a(createTempFile2);
                        a(createTempFile);
                        throw th;
                    }
                } catch (Throwable th2) {
                    wrappedBuffer2.release();
                    throw th2;
                }
            } catch (Throwable th3) {
                a(createTempFile, fileOutputStream);
                a(createTempFile);
                throw th3;
            }
        } catch (Throwable th4) {
            wrappedBuffer.release();
            throw th4;
        }
    }

    private static void a(File file) {
        if (!file.delete()) {
            InternalLogger internalLogger = a;
            internalLogger.warn("Failed to delete a file: " + file);
        }
    }

    private static void a(File file, OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            InternalLogger internalLogger = a;
            internalLogger.warn("Failed to close a file: " + file, (Throwable) e);
        }
    }
}
