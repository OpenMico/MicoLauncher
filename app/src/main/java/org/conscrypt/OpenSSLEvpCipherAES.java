package org.conscrypt;

import com.xiaomi.mipush.sdk.Constants;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.crypto.NoSuchPaddingException;
import org.conscrypt.OpenSSLCipher;

/* loaded from: classes5.dex */
public abstract class OpenSSLEvpCipherAES extends OpenSSLEvpCipher {
    private static final int AES_BLOCK_SIZE = 16;

    @Override // org.conscrypt.OpenSSLCipher
    String getBaseCipherName() {
        return "AES";
    }

    @Override // org.conscrypt.OpenSSLCipher
    int getCipherBlockSize() {
        return 16;
    }

    OpenSSLEvpCipherAES(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
        super(mode, padding);
    }

    @Override // org.conscrypt.OpenSSLCipher
    void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
        switch (mode) {
            case CBC:
            case CTR:
            case ECB:
                return;
            default:
                throw new NoSuchAlgorithmException("Unsupported mode " + mode.toString());
        }
    }

    @Override // org.conscrypt.OpenSSLCipher
    void checkSupportedPadding(OpenSSLCipher.Padding padding) throws NoSuchPaddingException {
        switch (padding) {
            case NOPADDING:
            case PKCS5PADDING:
                return;
            default:
                throw new NoSuchPaddingException("Unsupported padding " + padding.toString());
        }
    }

    @Override // org.conscrypt.OpenSSLEvpCipher
    String getCipherName(int i, OpenSSLCipher.Mode mode) {
        return "aes-" + (i * 8) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + mode.toString().toLowerCase(Locale.US);
    }

    /* loaded from: classes5.dex */
    public static class AES extends OpenSSLEvpCipherAES {
        AES(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
            super(mode, padding);
        }

        /* loaded from: classes5.dex */
        public static class CBC extends AES {
            CBC(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.CBC, padding);
            }

            /* loaded from: classes5.dex */
            public static class NoPadding extends CBC {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            /* loaded from: classes5.dex */
            public static class PKCS5Padding extends CBC {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }
        }

        /* loaded from: classes5.dex */
        public static class CTR extends AES {
            public CTR() {
                super(OpenSSLCipher.Mode.CTR, OpenSSLCipher.Padding.NOPADDING);
            }
        }

        /* loaded from: classes5.dex */
        public static class ECB extends AES {
            ECB(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.ECB, padding);
            }

            /* loaded from: classes5.dex */
            public static class NoPadding extends ECB {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            /* loaded from: classes5.dex */
            public static class PKCS5Padding extends ECB {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }
        }

        @Override // org.conscrypt.OpenSSLCipher
        void checkSupportedKeySize(int i) throws InvalidKeyException {
            if (i != 16 && i != 24 && i != 32) {
                throw new InvalidKeyException("Unsupported key size: " + i + " bytes");
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class AES_128 extends OpenSSLEvpCipherAES {
        AES_128(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
            super(mode, padding);
        }

        /* loaded from: classes5.dex */
        public static class CBC extends AES_128 {
            CBC(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.CBC, padding);
            }

            /* loaded from: classes5.dex */
            public static class NoPadding extends CBC {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            /* loaded from: classes5.dex */
            public static class PKCS5Padding extends CBC {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }
        }

        /* loaded from: classes5.dex */
        public static class CTR extends AES_128 {
            public CTR() {
                super(OpenSSLCipher.Mode.CTR, OpenSSLCipher.Padding.NOPADDING);
            }
        }

        /* loaded from: classes5.dex */
        public static class ECB extends AES_128 {
            ECB(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.ECB, padding);
            }

            /* loaded from: classes5.dex */
            public static class NoPadding extends ECB {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            /* loaded from: classes5.dex */
            public static class PKCS5Padding extends ECB {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }
        }

        @Override // org.conscrypt.OpenSSLCipher
        void checkSupportedKeySize(int i) throws InvalidKeyException {
            if (i != 16) {
                throw new InvalidKeyException("Unsupported key size: " + i + " bytes");
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class AES_256 extends OpenSSLEvpCipherAES {
        AES_256(OpenSSLCipher.Mode mode, OpenSSLCipher.Padding padding) {
            super(mode, padding);
        }

        /* loaded from: classes5.dex */
        public static class CBC extends AES_256 {
            CBC(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.CBC, padding);
            }

            /* loaded from: classes5.dex */
            public static class NoPadding extends CBC {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            /* loaded from: classes5.dex */
            public static class PKCS5Padding extends CBC {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }
        }

        /* loaded from: classes5.dex */
        public static class CTR extends AES_256 {
            public CTR() {
                super(OpenSSLCipher.Mode.CTR, OpenSSLCipher.Padding.NOPADDING);
            }
        }

        /* loaded from: classes5.dex */
        public static class ECB extends AES_256 {
            ECB(OpenSSLCipher.Padding padding) {
                super(OpenSSLCipher.Mode.ECB, padding);
            }

            /* loaded from: classes5.dex */
            public static class NoPadding extends ECB {
                public NoPadding() {
                    super(OpenSSLCipher.Padding.NOPADDING);
                }
            }

            /* loaded from: classes5.dex */
            public static class PKCS5Padding extends ECB {
                public PKCS5Padding() {
                    super(OpenSSLCipher.Padding.PKCS5PADDING);
                }
            }
        }

        @Override // org.conscrypt.OpenSSLCipher
        void checkSupportedKeySize(int i) throws InvalidKeyException {
            if (i != 32) {
                throw new InvalidKeyException("Unsupported key size: " + i + " bytes");
            }
        }
    }
}
