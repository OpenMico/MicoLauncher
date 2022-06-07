package org.conscrypt.ct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes5.dex */
public class SignedCertificateTimestamp {
    private final byte[] extensions;
    private final byte[] logId;
    private final Origin origin;
    private final DigitallySigned signature;
    private final long timestamp;
    private final Version version;

    /* loaded from: classes5.dex */
    public enum Origin {
        EMBEDDED,
        TLS_EXTENSION,
        OCSP_RESPONSE
    }

    /* loaded from: classes5.dex */
    public enum SignatureType {
        CERTIFICATE_TIMESTAMP,
        TREE_HASH
    }

    /* loaded from: classes5.dex */
    public enum Version {
        V1
    }

    public SignedCertificateTimestamp(Version version, byte[] bArr, long j, byte[] bArr2, DigitallySigned digitallySigned, Origin origin) {
        this.version = version;
        this.logId = bArr;
        this.timestamp = j;
        this.extensions = bArr2;
        this.signature = digitallySigned;
        this.origin = origin;
    }

    public Version getVersion() {
        return this.version;
    }

    public byte[] getLogID() {
        return this.logId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public byte[] getExtensions() {
        return this.extensions;
    }

    public DigitallySigned getSignature() {
        return this.signature;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public static SignedCertificateTimestamp decode(InputStream inputStream, Origin origin) throws SerializationException {
        int readNumber = Serialization.readNumber(inputStream, 1);
        if (readNumber == Version.V1.ordinal()) {
            return new SignedCertificateTimestamp(Version.V1, Serialization.readFixedBytes(inputStream, 32), Serialization.readLong(inputStream, 8), Serialization.readVariableBytes(inputStream, 2), DigitallySigned.decode(inputStream), origin);
        }
        throw new SerializationException("Unsupported SCT version " + readNumber);
    }

    public static SignedCertificateTimestamp decode(byte[] bArr, Origin origin) throws SerializationException {
        return decode(new ByteArrayInputStream(bArr), origin);
    }

    public void encodeTBS(OutputStream outputStream, CertificateEntry certificateEntry) throws SerializationException {
        Serialization.writeNumber(outputStream, this.version.ordinal(), 1);
        Serialization.writeNumber(outputStream, SignatureType.CERTIFICATE_TIMESTAMP.ordinal(), 1);
        Serialization.writeNumber(outputStream, this.timestamp, 8);
        certificateEntry.encode(outputStream);
        Serialization.writeVariableBytes(outputStream, this.extensions, 2);
    }

    public byte[] encodeTBS(CertificateEntry certificateEntry) throws SerializationException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encodeTBS(byteArrayOutputStream, certificateEntry);
        return byteArrayOutputStream.toByteArray();
    }
}
