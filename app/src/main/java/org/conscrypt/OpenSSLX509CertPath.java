package org.conscrypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.conscrypt.OpenSSLX509CertificateFactory;

/* loaded from: classes5.dex */
final class OpenSSLX509CertPath extends CertPath {
    private static final int PUSHBACK_SIZE = 64;
    private static final long serialVersionUID = -3249106005255170761L;
    private final List<? extends X509Certificate> mCertificates;
    private static final byte[] PKCS7_MARKER = {45, 45, 45, 45, 45, 66, 69, 71, 73, 78, 32, 80, 75, 67, 83, 55};
    private static final List<String> ALL_ENCODINGS = Collections.unmodifiableList(Arrays.asList(Encoding.PKI_PATH.apiName, Encoding.PKCS7.apiName));
    private static final Encoding DEFAULT_ENCODING = Encoding.PKI_PATH;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public enum Encoding {
        PKI_PATH("PkiPath"),
        PKCS7("PKCS7");
        
        private final String apiName;

        Encoding(String str) {
            this.apiName = str;
        }

        static Encoding findByApiName(String str) throws CertificateEncodingException {
            Encoding[] values = values();
            for (Encoding encoding : values) {
                if (encoding.apiName.equals(str)) {
                    return encoding;
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Iterator<String> getEncodingsIterator() {
        return ALL_ENCODINGS.iterator();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLX509CertPath(List<? extends X509Certificate> list) {
        super("X.509");
        this.mCertificates = list;
    }

    @Override // java.security.cert.CertPath
    public List<? extends Certificate> getCertificates() {
        return Collections.unmodifiableList(this.mCertificates);
    }

    private byte[] getEncoded(Encoding encoding) throws CertificateEncodingException {
        OpenSSLX509Certificate[] openSSLX509CertificateArr = new OpenSSLX509Certificate[this.mCertificates.size()];
        long[] jArr = new long[openSSLX509CertificateArr.length];
        int i = 0;
        for (int length = openSSLX509CertificateArr.length - 1; length >= 0; length--) {
            X509Certificate x509Certificate = (X509Certificate) this.mCertificates.get(i);
            if (x509Certificate instanceof OpenSSLX509Certificate) {
                openSSLX509CertificateArr[length] = (OpenSSLX509Certificate) x509Certificate;
            } else {
                openSSLX509CertificateArr[length] = OpenSSLX509Certificate.fromX509Der(x509Certificate.getEncoded());
            }
            jArr[length] = openSSLX509CertificateArr[length].getContext();
            i++;
        }
        switch (encoding) {
            case PKI_PATH:
                return NativeCrypto.ASN1_seq_pack_X509(jArr);
            case PKCS7:
                return NativeCrypto.i2d_PKCS7(jArr);
            default:
                throw new CertificateEncodingException("Unknown encoding");
        }
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded() throws CertificateEncodingException {
        return getEncoded(DEFAULT_ENCODING);
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded(String str) throws CertificateEncodingException {
        Encoding findByApiName = Encoding.findByApiName(str);
        if (findByApiName != null) {
            return getEncoded(findByApiName);
        }
        throw new CertificateEncodingException("Invalid encoding: " + str);
    }

    @Override // java.security.cert.CertPath
    public Iterator<String> getEncodings() {
        return getEncodingsIterator();
    }

    private static CertPath fromPkiPathEncoding(InputStream inputStream) throws CertificateException {
        OpenSSLBIOInputStream openSSLBIOInputStream = new OpenSSLBIOInputStream(inputStream, true);
        boolean markSupported = inputStream.markSupported();
        if (markSupported) {
            inputStream.mark(64);
        }
        try {
            try {
                long[] ASN1_seq_unpack_X509_bio = NativeCrypto.ASN1_seq_unpack_X509_bio(openSSLBIOInputStream.getBioContext());
                if (ASN1_seq_unpack_X509_bio == null) {
                    return new OpenSSLX509CertPath(Collections.emptyList());
                }
                ArrayList arrayList = new ArrayList(ASN1_seq_unpack_X509_bio.length);
                for (int length = ASN1_seq_unpack_X509_bio.length - 1; length >= 0; length--) {
                    if (ASN1_seq_unpack_X509_bio[length] != 0) {
                        try {
                            arrayList.add(new OpenSSLX509Certificate(ASN1_seq_unpack_X509_bio[length]));
                        } catch (OpenSSLX509CertificateFactory.ParsingException e) {
                            throw new CertificateParsingException(e);
                        }
                    }
                }
                return new OpenSSLX509CertPath(arrayList);
            } catch (Exception e2) {
                if (markSupported) {
                    try {
                        inputStream.reset();
                    } catch (IOException unused) {
                    }
                }
                throw new CertificateException(e2);
            }
        } finally {
            openSSLBIOInputStream.release();
        }
    }

    private static CertPath fromPkcs7Encoding(InputStream inputStream) throws CertificateException {
        if (inputStream != null) {
            try {
                if (inputStream.available() != 0) {
                    boolean markSupported = inputStream.markSupported();
                    if (markSupported) {
                        inputStream.mark(64);
                    }
                    PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 64);
                    try {
                        byte[] bArr = new byte[PKCS7_MARKER.length];
                        int read = pushbackInputStream.read(bArr);
                        if (read >= 0) {
                            pushbackInputStream.unread(bArr, 0, read);
                            if (read != PKCS7_MARKER.length || !Arrays.equals(PKCS7_MARKER, bArr)) {
                                return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7DerInputStream(pushbackInputStream));
                            }
                            return new OpenSSLX509CertPath(OpenSSLX509Certificate.fromPkcs7PemInputStream(pushbackInputStream));
                        }
                        throw new OpenSSLX509CertificateFactory.ParsingException("inStream is empty");
                    } catch (Exception e) {
                        if (markSupported) {
                            try {
                                inputStream.reset();
                            } catch (IOException unused) {
                            }
                        }
                        throw new CertificateException(e);
                    }
                }
            } catch (IOException e2) {
                throw new CertificateException("Problem reading input stream", e2);
            }
        }
        return new OpenSSLX509CertPath(Collections.emptyList());
    }

    private static CertPath fromEncoding(InputStream inputStream, Encoding encoding) throws CertificateException {
        switch (encoding) {
            case PKI_PATH:
                return fromPkiPathEncoding(inputStream);
            case PKCS7:
                return fromPkcs7Encoding(inputStream);
            default:
                throw new CertificateEncodingException("Unknown encoding");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CertPath fromEncoding(InputStream inputStream, String str) throws CertificateException {
        if (inputStream != null) {
            Encoding findByApiName = Encoding.findByApiName(str);
            if (findByApiName != null) {
                return fromEncoding(inputStream, findByApiName);
            }
            throw new CertificateException("Invalid encoding: " + str);
        }
        throw new CertificateException("inStream == null");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CertPath fromEncoding(InputStream inputStream) throws CertificateException {
        if (inputStream != null) {
            return fromEncoding(inputStream, DEFAULT_ENCODING);
        }
        throw new CertificateException("inStream == null");
    }
}
