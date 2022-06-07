package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.zip.Checksum;

/* compiled from: ChecksumHashFunction.java */
@Immutable
/* loaded from: classes2.dex */
final class h extends c implements Serializable {
    private static final long serialVersionUID = 0;
    private final int bits;
    private final k<? extends Checksum> checksumSupplier;
    private final String toString;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(k<? extends Checksum> kVar, int i, String str) {
        this.checksumSupplier = (k) Preconditions.checkNotNull(kVar);
        Preconditions.checkArgument(i == 32 || i == 64, "bits (%s) must be either 32 or 64", i);
        this.bits = i;
        this.toString = (String) Preconditions.checkNotNull(str);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return this.bits;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new a((Checksum) this.checksumSupplier.get());
    }

    public String toString() {
        return this.toString;
    }

    /* compiled from: ChecksumHashFunction.java */
    /* loaded from: classes2.dex */
    private final class a extends a {
        private final Checksum b;

        private a(Checksum checksum) {
            this.b = (Checksum) Preconditions.checkNotNull(checksum);
        }

        @Override // com.google.common.hash.a
        protected void a(byte b) {
            this.b.update(b);
        }

        @Override // com.google.common.hash.a
        protected void a(byte[] bArr, int i, int i2) {
            this.b.update(bArr, i, i2);
        }

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            long value = this.b.getValue();
            if (h.this.bits == 32) {
                return HashCode.fromInt((int) value);
            }
            return HashCode.fromLong(value);
        }
    }
}
