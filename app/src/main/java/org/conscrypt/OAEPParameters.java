package org.conscrypt;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes5.dex */
public class OAEPParameters extends AlgorithmParametersSpi {
    private static final String MGF1_OID = "1.2.840.113549.1.1.8";
    private static final String PSPECIFIED_OID = "1.2.840.113549.1.1.9";
    private OAEPParameterSpec spec = OAEPParameterSpec.DEFAULT;
    private static final Map<String, String> OID_TO_NAME = new HashMap();
    private static final Map<String, String> NAME_TO_OID = new HashMap();

    @Override // java.security.AlgorithmParametersSpi
    protected String engineToString() {
        return "Conscrypt OAEP AlgorithmParameters";
    }

    static {
        OID_TO_NAME.put("1.3.14.3.2.26", MessageDigestAlgorithms.SHA_1);
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.4", "SHA-224");
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.1", MessageDigestAlgorithms.SHA_256);
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.2", MessageDigestAlgorithms.SHA_384);
        OID_TO_NAME.put("2.16.840.1.101.3.4.2.3", MessageDigestAlgorithms.SHA_512);
        for (Map.Entry<String, String> entry : OID_TO_NAME.entrySet()) {
            NAME_TO_OID.put(entry.getValue(), entry.getKey());
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        if (algorithmParameterSpec instanceof OAEPParameterSpec) {
            this.spec = (OAEPParameterSpec) algorithmParameterSpec;
            return;
        }
        throw new InvalidParameterSpecException("Only OAEPParameterSpec is supported");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        Throwable th;
        long j;
        long j2;
        Throwable th2;
        long j3;
        try {
            j2 = NativeCrypto.asn1_read_init(bArr);
            try {
                j = NativeCrypto.asn1_read_sequence(j2);
                try {
                    PSource.PSpecified pSpecified = PSource.PSpecified.DEFAULT;
                    String readHash = readHash(j);
                    String readMgfHash = readMgfHash(j);
                    if (NativeCrypto.asn1_read_next_tag_is(j, 2)) {
                        try {
                            j3 = NativeCrypto.asn1_read_tagged(j);
                            try {
                                long asn1_read_sequence = NativeCrypto.asn1_read_sequence(j3);
                                if (NativeCrypto.asn1_read_oid(asn1_read_sequence).equals(PSPECIFIED_OID)) {
                                    pSpecified = new PSource.PSpecified(NativeCrypto.asn1_read_octetstring(asn1_read_sequence));
                                    if (NativeCrypto.asn1_read_is_empty(asn1_read_sequence)) {
                                        NativeCrypto.asn1_read_free(asn1_read_sequence);
                                        NativeCrypto.asn1_read_free(j3);
                                    } else {
                                        throw new IOException("Error reading ASN.1 encoding");
                                    }
                                } else {
                                    throw new IOException("Error reading ASN.1 encoding");
                                }
                            } catch (Throwable th3) {
                                th2 = th3;
                                NativeCrypto.asn1_read_free(0L);
                                NativeCrypto.asn1_read_free(j3);
                                throw th2;
                            }
                        } catch (Throwable th4) {
                            th2 = th4;
                            j3 = 0;
                        }
                    }
                    if (!NativeCrypto.asn1_read_is_empty(j) || !NativeCrypto.asn1_read_is_empty(j2)) {
                        throw new IOException("Error reading ASN.1 encoding");
                    }
                    this.spec = new OAEPParameterSpec(readHash, "MGF1", new MGF1ParameterSpec(readMgfHash), pSpecified);
                    NativeCrypto.asn1_read_free(j);
                    NativeCrypto.asn1_read_free(j2);
                } catch (Throwable th5) {
                    th = th5;
                    NativeCrypto.asn1_read_free(j);
                    NativeCrypto.asn1_read_free(j2);
                    throw th;
                }
            } catch (Throwable th6) {
                th = th6;
                j = 0;
            }
        } catch (Throwable th7) {
            th = th7;
            j2 = 0;
            j = 0;
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr, String str) throws IOException {
        if (str == null || str.equals("ASN.1")) {
            engineInit(bArr);
            return;
        }
        throw new IOException("Unsupported format: " + str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String readHash(long j) throws IOException {
        if (!NativeCrypto.asn1_read_next_tag_is(j, 0)) {
            return MessageDigestAlgorithms.SHA_1;
        }
        long j2 = 0;
        try {
            j2 = NativeCrypto.asn1_read_tagged(j);
            return getHashName(j2);
        } finally {
            NativeCrypto.asn1_read_free(j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String readMgfHash(long j) throws IOException {
        long j2;
        Throwable th;
        if (!NativeCrypto.asn1_read_next_tag_is(j, 1)) {
            return MessageDigestAlgorithms.SHA_1;
        }
        try {
            j2 = NativeCrypto.asn1_read_tagged(j);
        } catch (Throwable th2) {
            th = th2;
            j2 = 0;
        }
        try {
            long asn1_read_sequence = NativeCrypto.asn1_read_sequence(j2);
            if (NativeCrypto.asn1_read_oid(asn1_read_sequence).equals(MGF1_OID)) {
                String hashName = getHashName(asn1_read_sequence);
                if (NativeCrypto.asn1_read_is_empty(asn1_read_sequence)) {
                    NativeCrypto.asn1_read_free(asn1_read_sequence);
                    NativeCrypto.asn1_read_free(j2);
                    return hashName;
                }
                throw new IOException("Error reading ASN.1 encoding");
            }
            throw new IOException("Error reading ASN.1 encoding");
        } catch (Throwable th3) {
            th = th3;
            NativeCrypto.asn1_read_free(0L);
            NativeCrypto.asn1_read_free(j2);
            throw th;
        }
    }

    private static String getHashName(long j) throws IOException {
        long j2;
        Throwable th;
        try {
            j2 = NativeCrypto.asn1_read_sequence(j);
            try {
                String asn1_read_oid = NativeCrypto.asn1_read_oid(j2);
                if (!NativeCrypto.asn1_read_is_empty(j2)) {
                    NativeCrypto.asn1_read_null(j2);
                }
                if (!NativeCrypto.asn1_read_is_empty(j2) || !OID_TO_NAME.containsKey(asn1_read_oid)) {
                    throw new IOException("Error reading ASN.1 encoding");
                }
                String str = OID_TO_NAME.get(asn1_read_oid);
                NativeCrypto.asn1_read_free(j2);
                return str;
            } catch (Throwable th2) {
                th = th2;
                NativeCrypto.asn1_read_free(j2);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            j2 = 0;
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> cls) throws InvalidParameterSpecException {
        if (cls != null && cls == OAEPParameterSpec.class) {
            return this.spec;
        }
        throw new InvalidParameterSpecException("Unsupported class: " + cls);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        long j;
        long j2;
        Throwable th;
        IOException e;
        long j3;
        Throwable th2;
        try {
            long j4 = 0;
            try {
                j2 = NativeCrypto.asn1_write_init();
                try {
                    long asn1_write_sequence = NativeCrypto.asn1_write_sequence(j2);
                    try {
                        writeHashAndMgfHash(asn1_write_sequence, this.spec.getDigestAlgorithm(), (MGF1ParameterSpec) this.spec.getMGFParameters());
                        PSource.PSpecified pSpecified = (PSource.PSpecified) this.spec.getPSource();
                        if (pSpecified.getValue().length != 0) {
                            try {
                                j3 = NativeCrypto.asn1_write_tag(asn1_write_sequence, 2);
                                try {
                                    j4 = writeAlgorithmIdentifier(j3, PSPECIFIED_OID);
                                    NativeCrypto.asn1_write_octetstring(j4, pSpecified.getValue());
                                    NativeCrypto.asn1_write_flush(asn1_write_sequence);
                                    NativeCrypto.asn1_write_free(j4);
                                    NativeCrypto.asn1_write_free(j3);
                                } catch (Throwable th3) {
                                    th2 = th3;
                                    NativeCrypto.asn1_write_flush(asn1_write_sequence);
                                    NativeCrypto.asn1_write_free(j4);
                                    NativeCrypto.asn1_write_free(j3);
                                    throw th2;
                                }
                            } catch (Throwable th4) {
                                th2 = th4;
                                j3 = 0;
                            }
                        }
                        byte[] asn1_write_finish = NativeCrypto.asn1_write_finish(j2);
                        NativeCrypto.asn1_write_free(asn1_write_sequence);
                        NativeCrypto.asn1_write_free(j2);
                        return asn1_write_finish;
                    } catch (IOException e2) {
                        e = e2;
                        NativeCrypto.asn1_write_cleanup(j2);
                        throw e;
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (Throwable th5) {
                    th = th5;
                    j = 0;
                    NativeCrypto.asn1_write_free(j);
                    NativeCrypto.asn1_write_free(j2);
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                j2 = 0;
            } catch (Throwable th6) {
                th = th6;
                j = 0;
                j2 = 0;
            }
        } catch (Throwable th7) {
            th = th7;
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        if (str == null || str.equals("ASN.1")) {
            return engineGetEncoded();
        }
        throw new IOException("Unsupported format: " + str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void writeHashAndMgfHash(long j, String str, MGF1ParameterSpec mGF1ParameterSpec) throws IOException {
        Throwable th;
        long j2;
        long j3;
        Throwable th2;
        long j4;
        long j5 = 0;
        if (!str.equals(MessageDigestAlgorithms.SHA_1)) {
            try {
                j4 = NativeCrypto.asn1_write_tag(j, 0);
                try {
                    long writeAlgorithmIdentifier = writeAlgorithmIdentifier(j4, NAME_TO_OID.get(str));
                    try {
                        NativeCrypto.asn1_write_null(writeAlgorithmIdentifier);
                        NativeCrypto.asn1_write_flush(j);
                        NativeCrypto.asn1_write_free(writeAlgorithmIdentifier);
                        NativeCrypto.asn1_write_free(j4);
                    } catch (Throwable th3) {
                        th2 = th3;
                        j5 = writeAlgorithmIdentifier;
                        NativeCrypto.asn1_write_flush(j);
                        NativeCrypto.asn1_write_free(j5);
                        NativeCrypto.asn1_write_free(j4);
                        throw th2;
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                }
            } catch (Throwable th5) {
                th2 = th5;
                j4 = 0;
            }
        }
        if (!mGF1ParameterSpec.getDigestAlgorithm().equals(MessageDigestAlgorithms.SHA_1)) {
            try {
                j3 = NativeCrypto.asn1_write_tag(j, 1);
                try {
                    j2 = writeAlgorithmIdentifier(j3, MGF1_OID);
                    try {
                        j5 = writeAlgorithmIdentifier(j2, NAME_TO_OID.get(mGF1ParameterSpec.getDigestAlgorithm()));
                        NativeCrypto.asn1_write_null(j5);
                        NativeCrypto.asn1_write_flush(j);
                        NativeCrypto.asn1_write_free(j5);
                        NativeCrypto.asn1_write_free(j2);
                        NativeCrypto.asn1_write_free(j3);
                    } catch (Throwable th6) {
                        th = th6;
                        NativeCrypto.asn1_write_flush(j);
                        NativeCrypto.asn1_write_free(j5);
                        NativeCrypto.asn1_write_free(j2);
                        NativeCrypto.asn1_write_free(j3);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    j2 = 0;
                }
            } catch (Throwable th8) {
                th = th8;
                j3 = 0;
                j2 = 0;
            }
        }
    }

    private static long writeAlgorithmIdentifier(long j, String str) throws IOException {
        IOException e;
        long j2;
        try {
            j2 = NativeCrypto.asn1_write_sequence(j);
        } catch (IOException e2) {
            e = e2;
            j2 = 0;
        }
        try {
            NativeCrypto.asn1_write_oid(j2, str);
            return j2;
        } catch (IOException e3) {
            e = e3;
            NativeCrypto.asn1_write_free(j2);
            throw e;
        }
    }
}
