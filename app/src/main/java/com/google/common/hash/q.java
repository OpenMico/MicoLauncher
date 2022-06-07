package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/* compiled from: MessageDigestHashFunction.java */
@Immutable
/* loaded from: classes2.dex */
final class q extends c implements Serializable {
    private final int bytes;
    private final MessageDigest prototype;
    private final boolean supportsClone;
    private final String toString;

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(String str, String str2) {
        this.prototype = a(str);
        this.bytes = this.prototype.getDigestLength();
        this.toString = (String) Preconditions.checkNotNull(str2);
        this.supportsClone = a(this.prototype);
    }

    q(String str, int i, String str2) {
        this.toString = (String) Preconditions.checkNotNull(str2);
        this.prototype = a(str);
        int digestLength = this.prototype.getDigestLength();
        Preconditions.checkArgument(i >= 4 && i <= digestLength, "bytes (%s) must be >= 4 and < %s", i, digestLength);
        this.bytes = i;
        this.supportsClone = a(this.prototype);
    }

    private static boolean a(MessageDigest messageDigest) {
        try {
            messageDigest.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return this.bytes * 8;
    }

    public String toString() {
        return this.toString;
    }

    private static MessageDigest a(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        if (this.supportsClone) {
            try {
                return new a((MessageDigest) this.prototype.clone(), this.bytes);
            } catch (CloneNotSupportedException unused) {
            }
        }
        return new a(a(this.prototype.getAlgorithm()), this.bytes);
    }

    /* compiled from: MessageDigestHashFunction.java */
    /* loaded from: classes2.dex */
    private static final class b implements Serializable {
        private static final long serialVersionUID = 0;
        private final String algorithmName;
        private final int bytes;
        private final String toString;

        private b(String str, int i, String str2) {
            this.algorithmName = str;
            this.bytes = i;
            this.toString = str2;
        }

        private Object readResolve() {
            return new q(this.algorithmName, this.bytes, this.toString);
        }
    }

    Object writeReplace() {
        return new b(this.prototype.getAlgorithm(), this.bytes, this.toString);
    }

    /* compiled from: MessageDigestHashFunction.java */
    /* loaded from: classes2.dex */
    private static final class a extends a {
        private final MessageDigest a;
        private final int b;
        private boolean c;

        private a(MessageDigest messageDigest, int i) {
            this.a = messageDigest;
            this.b = i;
        }

        @Override // com.google.common.hash.a
        protected void a(byte b) {
            a();
            this.a.update(b);
        }

        @Override // com.google.common.hash.a
        protected void a(byte[] bArr, int i, int i2) {
            a();
            this.a.update(bArr, i, i2);
        }

        @Override // com.google.common.hash.a
        protected void a(ByteBuffer byteBuffer) {
            a();
            this.a.update(byteBuffer);
        }

        private void a() {
            Preconditions.checkState(!this.c, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            a();
            this.c = true;
            if (this.b == this.a.getDigestLength()) {
                return HashCode.a(this.a.digest());
            }
            return HashCode.a(Arrays.copyOf(this.a.digest(), this.b));
        }
    }
}
