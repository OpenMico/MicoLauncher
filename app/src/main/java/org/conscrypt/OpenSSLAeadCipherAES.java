package org.conscrypt;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.conscrypt.OpenSSLCipher;

/* loaded from: classes5.dex */
public abstract class OpenSSLAeadCipherAES extends OpenSSLAeadCipher {
    private static final int AES_BLOCK_SIZE = 16;

    @Override // org.conscrypt.OpenSSLCipher
    String getBaseCipherName() {
        return "AES";
    }

    @Override // org.conscrypt.OpenSSLCipher
    int getCipherBlockSize() {
        return 16;
    }

    OpenSSLAeadCipherAES(OpenSSLCipher.Mode mode) {
        super(mode);
    }

    @Override // org.conscrypt.OpenSSLCipher
    void checkSupportedKeySize(int i) throws InvalidKeyException {
        if (i != 16 && i != 32) {
            throw new InvalidKeyException("Unsupported key size: " + i + " bytes (must be 16 or 32)");
        }
    }

    @Override // org.conscrypt.OpenSSLCipher
    protected AlgorithmParameterSpec getParameterSpec(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
        if (algorithmParameters == null) {
            return null;
        }
        AlgorithmParameterSpec fromGCMParameters = Platform.fromGCMParameters(algorithmParameters);
        return fromGCMParameters != null ? fromGCMParameters : super.getParameterSpec(algorithmParameters);
    }

    @Override // org.conscrypt.OpenSSLCipher, javax.crypto.CipherSpi
    protected AlgorithmParameters engineGetParameters() {
        if (this.iv == null) {
            return null;
        }
        AlgorithmParameterSpec gCMParameterSpec = Platform.toGCMParameterSpec(this.tagLengthInBytes * 8, this.iv);
        if (gCMParameterSpec == null) {
            return super.engineGetParameters();
        }
        try {
            AlgorithmParameters instance = AlgorithmParameters.getInstance("GCM");
            instance.init(gCMParameterSpec);
            return instance;
        } catch (NoSuchAlgorithmException e) {
            throw ((Error) new AssertionError("GCM not supported").initCause(e));
        } catch (InvalidParameterSpecException unused) {
            return null;
        }
    }

    @Override // org.conscrypt.OpenSSLAeadCipher, org.conscrypt.OpenSSLCipher
    int getOutputSizeForFinal(int i) {
        if (isEncrypting()) {
            return this.bufCount + i + this.tagLengthInBytes;
        }
        return Math.max(0, (this.bufCount + i) - this.tagLengthInBytes);
    }

    /* loaded from: classes5.dex */
    public static class GCM extends OpenSSLAeadCipherAES {
        public GCM() {
            super(OpenSSLCipher.Mode.GCM);
        }

        @Override // org.conscrypt.OpenSSLCipher
        void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
            if (mode != OpenSSLCipher.Mode.GCM) {
                throw new NoSuchAlgorithmException("Mode must be GCM");
            }
        }

        @Override // org.conscrypt.OpenSSLAeadCipher
        long getEVP_AEAD(int i) throws InvalidKeyException {
            if (i == 16) {
                return NativeCrypto.EVP_aead_aes_128_gcm();
            }
            if (i == 32) {
                return NativeCrypto.EVP_aead_aes_256_gcm();
            }
            throw new RuntimeException("Unexpected key length: " + i);
        }

        /* loaded from: classes5.dex */
        public static class AES_128 extends GCM {
            @Override // org.conscrypt.OpenSSLAeadCipherAES, org.conscrypt.OpenSSLCipher
            void checkSupportedKeySize(int i) throws InvalidKeyException {
                if (i != 16) {
                    throw new InvalidKeyException("Unsupported key size: " + i + " bytes (must be 16)");
                }
            }
        }

        /* loaded from: classes5.dex */
        public static class AES_256 extends GCM {
            @Override // org.conscrypt.OpenSSLAeadCipherAES, org.conscrypt.OpenSSLCipher
            void checkSupportedKeySize(int i) throws InvalidKeyException {
                if (i != 32) {
                    throw new InvalidKeyException("Unsupported key size: " + i + " bytes (must be 32)");
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class GCM_SIV extends OpenSSLAeadCipherAES {
        @Override // org.conscrypt.OpenSSLAeadCipher
        boolean allowsNonceReuse() {
            return true;
        }

        public GCM_SIV() {
            super(OpenSSLCipher.Mode.GCM_SIV);
        }

        @Override // org.conscrypt.OpenSSLCipher
        void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
            if (mode != OpenSSLCipher.Mode.GCM_SIV) {
                throw new NoSuchAlgorithmException("Mode must be GCM-SIV");
            }
        }

        @Override // org.conscrypt.OpenSSLAeadCipher
        void checkSupportedTagLength(int i) throws InvalidAlgorithmParameterException {
            if (i != 128) {
                throw new InvalidAlgorithmParameterException("Tag length must be 128 bits");
            }
        }

        @Override // org.conscrypt.OpenSSLAeadCipher
        long getEVP_AEAD(int i) throws InvalidKeyException {
            if (i == 16) {
                return NativeCrypto.EVP_aead_aes_128_gcm_siv();
            }
            if (i == 32) {
                return NativeCrypto.EVP_aead_aes_256_gcm_siv();
            }
            throw new RuntimeException("Unexpected key length: " + i);
        }

        /* loaded from: classes5.dex */
        public static class AES_128 extends GCM_SIV {
            @Override // org.conscrypt.OpenSSLAeadCipherAES, org.conscrypt.OpenSSLCipher
            void checkSupportedKeySize(int i) throws InvalidKeyException {
                if (i != 16) {
                    throw new InvalidKeyException("Unsupported key size: " + i + " bytes (must be 16)");
                }
            }
        }

        /* loaded from: classes5.dex */
        public static class AES_256 extends GCM_SIV {
            @Override // org.conscrypt.OpenSSLAeadCipherAES, org.conscrypt.OpenSSLCipher
            void checkSupportedKeySize(int i) throws InvalidKeyException {
                if (i != 32) {
                    throw new InvalidKeyException("Unsupported key size: " + i + " bytes (must be 32)");
                }
            }
        }
    }
}
