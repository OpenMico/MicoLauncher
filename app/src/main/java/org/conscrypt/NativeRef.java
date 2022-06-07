package org.conscrypt;

/* loaded from: classes5.dex */
abstract class NativeRef {
    final long address;

    abstract void doFree(long j);

    NativeRef(long j) {
        if (j != 0) {
            this.address = j;
            return;
        }
        throw new NullPointerException("address == 0");
    }

    public boolean equals(Object obj) {
        return (obj instanceof NativeRef) && ((NativeRef) obj).address == this.address;
    }

    public int hashCode() {
        long j = this.address;
        return (int) (j ^ (j >>> 32));
    }

    protected void finalize() throws Throwable {
        try {
            if (this.address != 0) {
                doFree(this.address);
            }
        } finally {
            super.finalize();
        }
    }

    /* loaded from: classes5.dex */
    static final class EC_GROUP extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public EC_GROUP(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.EC_GROUP_clear_free(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class EC_POINT extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public EC_POINT(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.EC_POINT_clear_free(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class EVP_CIPHER_CTX extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public EVP_CIPHER_CTX(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.EVP_CIPHER_CTX_free(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class EVP_MD_CTX extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public EVP_MD_CTX(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.EVP_MD_CTX_destroy(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class EVP_PKEY extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public EVP_PKEY(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.EVP_PKEY_free(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class EVP_PKEY_CTX extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public EVP_PKEY_CTX(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.EVP_PKEY_CTX_free(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class HMAC_CTX extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public HMAC_CTX(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.HMAC_CTX_free(j);
        }
    }

    /* loaded from: classes5.dex */
    static final class SSL_SESSION extends NativeRef {
        /* JADX INFO: Access modifiers changed from: package-private */
        public SSL_SESSION(long j) {
            super(j);
        }

        @Override // org.conscrypt.NativeRef
        void doFree(long j) {
            NativeCrypto.SSL_SESSION_free(j);
        }
    }
}
