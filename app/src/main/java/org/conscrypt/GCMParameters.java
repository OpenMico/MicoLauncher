package org.conscrypt;

import java.io.IOException;
import java.security.AlgorithmParametersSpi;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* loaded from: classes5.dex */
public final class GCMParameters extends AlgorithmParametersSpi {
    private static final int DEFAULT_TLEN = 96;
    private byte[] iv;
    private int tLen;

    @Override // java.security.AlgorithmParametersSpi
    protected String engineToString() {
        return "Conscrypt GCM AlgorithmParameters";
    }

    public GCMParameters() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GCMParameters(int i, byte[] bArr) {
        this.tLen = i;
        this.iv = bArr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getTLen() {
        return this.tLen;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public byte[] getIV() {
        return this.iv;
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        GCMParameters fromGCMParameterSpec = Platform.fromGCMParameterSpec(algorithmParameterSpec);
        if (fromGCMParameterSpec != null) {
            this.tLen = fromGCMParameterSpec.tLen;
            this.iv = fromGCMParameterSpec.iv;
            return;
        }
        throw new InvalidParameterSpecException("Only GCMParameterSpec is supported");
    }

    @Override // java.security.AlgorithmParametersSpi
    protected void engineInit(byte[] bArr) throws IOException {
        Throwable th;
        long j;
        try {
            j = NativeCrypto.asn1_read_init(bArr);
            try {
                long asn1_read_sequence = NativeCrypto.asn1_read_sequence(j);
                byte[] asn1_read_octetstring = NativeCrypto.asn1_read_octetstring(asn1_read_sequence);
                int i = 96;
                if (!NativeCrypto.asn1_read_is_empty(asn1_read_sequence)) {
                    i = ((int) NativeCrypto.asn1_read_uint64(asn1_read_sequence)) * 8;
                }
                if (!NativeCrypto.asn1_read_is_empty(asn1_read_sequence) || !NativeCrypto.asn1_read_is_empty(j)) {
                    throw new IOException("Error reading ASN.1 encoding");
                }
                this.iv = asn1_read_octetstring;
                this.tLen = i;
                NativeCrypto.asn1_read_free(asn1_read_sequence);
                NativeCrypto.asn1_read_free(j);
            } catch (Throwable th2) {
                th = th2;
                NativeCrypto.asn1_read_free(0L);
                NativeCrypto.asn1_read_free(j);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
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

    @Override // java.security.AlgorithmParametersSpi
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> cls) throws InvalidParameterSpecException {
        if (cls != null && cls.getName().equals("javax.crypto.spec.GCMParameterSpec")) {
            return cls.cast(Platform.toGCMParameterSpec(this.tLen, this.iv));
        }
        throw new InvalidParameterSpecException("Unsupported class: " + cls);
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded() throws IOException {
        IOException e;
        long j;
        long j2 = 0;
        try {
            j = NativeCrypto.asn1_write_init();
            try {
                j2 = NativeCrypto.asn1_write_sequence(j);
                NativeCrypto.asn1_write_octetstring(j2, this.iv);
                if (this.tLen != 96) {
                    NativeCrypto.asn1_write_uint64(j2, this.tLen / 8);
                }
                byte[] asn1_write_finish = NativeCrypto.asn1_write_finish(j);
                NativeCrypto.asn1_write_free(j2);
                NativeCrypto.asn1_write_free(j);
                return asn1_write_finish;
            } catch (IOException e2) {
                e = e2;
                j2 = j;
                try {
                    NativeCrypto.asn1_write_cleanup(j2);
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    j = j2;
                    NativeCrypto.asn1_write_free(j2);
                    NativeCrypto.asn1_write_free(j);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                NativeCrypto.asn1_write_free(j2);
                NativeCrypto.asn1_write_free(j);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            j2 = 0;
        } catch (Throwable th3) {
            th = th3;
            j = 0;
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    protected byte[] engineGetEncoded(String str) throws IOException {
        if (str == null || str.equals("ASN.1")) {
            return engineGetEncoded();
        }
        throw new IOException("Unsupported format: " + str);
    }
}
