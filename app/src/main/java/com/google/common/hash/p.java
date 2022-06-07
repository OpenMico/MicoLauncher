package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

/* compiled from: MacHashFunction.java */
@Immutable
/* loaded from: classes2.dex */
final class p extends c {
    private final Mac a;
    private final Key b;
    private final String c;
    private final int d;
    private final boolean e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public p(String str, Key key, String str2) {
        this.a = a(str, key);
        this.b = (Key) Preconditions.checkNotNull(key);
        this.c = (String) Preconditions.checkNotNull(str2);
        this.d = this.a.getMacLength() * 8;
        this.e = a(this.a);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return this.d;
    }

    private static boolean a(Mac mac) {
        try {
            mac.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    private static Mac a(String str, Key key) {
        try {
            Mac instance = Mac.getInstance(str);
            instance.init(key);
            return instance;
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException(e2);
        }
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        if (this.e) {
            try {
                return new a((Mac) this.a.clone());
            } catch (CloneNotSupportedException unused) {
            }
        }
        return new a(a(this.a.getAlgorithm(), this.b));
    }

    public String toString() {
        return this.c;
    }

    /* compiled from: MacHashFunction.java */
    /* loaded from: classes2.dex */
    private static final class a extends a {
        private final Mac a;
        private boolean b;

        private a(Mac mac) {
            this.a = mac;
        }

        @Override // com.google.common.hash.a
        protected void a(byte b) {
            a();
            this.a.update(b);
        }

        @Override // com.google.common.hash.a
        protected void a(byte[] bArr) {
            a();
            this.a.update(bArr);
        }

        @Override // com.google.common.hash.a
        protected void a(byte[] bArr, int i, int i2) {
            a();
            this.a.update(bArr, i, i2);
        }

        @Override // com.google.common.hash.a
        protected void a(ByteBuffer byteBuffer) {
            a();
            Preconditions.checkNotNull(byteBuffer);
            this.a.update(byteBuffer);
        }

        private void a() {
            Preconditions.checkState(!this.b, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            a();
            this.b = true;
            return HashCode.a(this.a.doFinal());
        }
    }
}
