package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: SipHashFunction.java */
@Immutable
/* loaded from: classes2.dex */
final class t extends c implements Serializable {
    static final HashFunction a = new t(2, 4, 506097522914230528L, 1084818905618843912L);
    private static final long serialVersionUID = 0;
    private final int c;
    private final int d;
    private final long k0;
    private final long k1;

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public t(int i, int i2, long j, long j2) {
        boolean z = true;
        Preconditions.checkArgument(i > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", i);
        Preconditions.checkArgument(i2 <= 0 ? false : z, "The number of SipRound iterations (d=%s) during Finalization must be positive.", i2);
        this.c = i;
        this.d = i2;
        this.k0 = j;
        this.k1 = j2;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new a(this.c, this.d, this.k0, this.k1);
    }

    public String toString() {
        return "Hashing.sipHash" + this.c + "" + this.d + "(" + this.k0 + ", " + this.k1 + ")";
    }

    public boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof t)) {
            return false;
        }
        t tVar = (t) obj;
        return this.c == tVar.c && this.d == tVar.d && this.k0 == tVar.k0 && this.k1 == tVar.k1;
    }

    public int hashCode() {
        return (int) ((((getClass().hashCode() ^ this.c) ^ this.d) ^ this.k0) ^ this.k1);
    }

    /* compiled from: SipHashFunction.java */
    /* loaded from: classes2.dex */
    private static final class a extends f {
        private final int a;
        private final int b;
        private long c = 8317987319222330741L;
        private long d = 7237128888997146477L;
        private long e = 7816392313619706465L;
        private long f = 8387220255154660723L;
        private long g = 0;
        private long h = 0;

        a(int i, int i2, long j, long j2) {
            super(8);
            this.a = i;
            this.b = i2;
            this.c ^= j;
            this.d ^= j2;
            this.e ^= j;
            this.f ^= j2;
        }

        @Override // com.google.common.hash.f
        protected void a(ByteBuffer byteBuffer) {
            this.g += 8;
            a(byteBuffer.getLong());
        }

        @Override // com.google.common.hash.f
        protected void b(ByteBuffer byteBuffer) {
            this.g += byteBuffer.remaining();
            int i = 0;
            while (byteBuffer.hasRemaining()) {
                this.h ^= (byteBuffer.get() & 255) << i;
                i += 8;
            }
        }

        @Override // com.google.common.hash.f
        public HashCode a() {
            this.h ^= this.g << 56;
            a(this.h);
            this.e ^= 255;
            a(this.b);
            return HashCode.fromLong(((this.c ^ this.d) ^ this.e) ^ this.f);
        }

        private void a(long j) {
            this.f ^= j;
            a(this.a);
            this.c = j ^ this.c;
        }

        private void a(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                long j = this.c;
                long j2 = this.d;
                this.c = j + j2;
                this.e += this.f;
                this.d = Long.rotateLeft(j2, 13);
                this.f = Long.rotateLeft(this.f, 16);
                long j3 = this.d;
                long j4 = this.c;
                this.d = j3 ^ j4;
                this.f ^= this.e;
                this.c = Long.rotateLeft(j4, 32);
                long j5 = this.e;
                long j6 = this.d;
                this.e = j5 + j6;
                this.c += this.f;
                this.d = Long.rotateLeft(j6, 17);
                this.f = Long.rotateLeft(this.f, 21);
                long j7 = this.d;
                long j8 = this.e;
                this.d = j7 ^ j8;
                this.f ^= this.c;
                this.e = Long.rotateLeft(j8, 32);
            }
        }
    }
}
