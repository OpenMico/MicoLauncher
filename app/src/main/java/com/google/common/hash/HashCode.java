package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedInts;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public abstract class HashCode {
    private static final char[] a = "0123456789abcdef".toCharArray();

    abstract void a(byte[] bArr, int i, int i2);

    abstract boolean a(HashCode hashCode);

    public abstract byte[] asBytes();

    public abstract int asInt();

    public abstract long asLong();

    public abstract int bits();

    public abstract long padToLong();

    HashCode() {
    }

    @CanIgnoreReturnValue
    public int writeBytesTo(byte[] bArr, int i, int i2) {
        int min = Ints.min(i2, bits() / 8);
        Preconditions.checkPositionIndexes(i, i + min, bArr.length);
        a(bArr, i, min);
        return min;
    }

    public byte[] a() {
        return asBytes();
    }

    public static HashCode fromInt(int i) {
        return new b(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final int hash;

        @Override // com.google.common.hash.HashCode
        public int bits() {
            return 32;
        }

        b(int i) {
            this.hash = i;
        }

        @Override // com.google.common.hash.HashCode
        public byte[] asBytes() {
            int i = this.hash;
            return new byte[]{(byte) i, (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24)};
        }

        @Override // com.google.common.hash.HashCode
        public int asInt() {
            return this.hash;
        }

        @Override // com.google.common.hash.HashCode
        public long asLong() {
            throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
        }

        @Override // com.google.common.hash.HashCode
        public long padToLong() {
            return UnsignedInts.toLong(this.hash);
        }

        @Override // com.google.common.hash.HashCode
        void a(byte[] bArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i + i3] = (byte) (this.hash >> (i3 * 8));
            }
        }

        @Override // com.google.common.hash.HashCode
        boolean a(HashCode hashCode) {
            return this.hash == hashCode.asInt();
        }
    }

    public static HashCode fromLong(long j) {
        return new c(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class c extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final long hash;

        @Override // com.google.common.hash.HashCode
        public int bits() {
            return 64;
        }

        c(long j) {
            this.hash = j;
        }

        @Override // com.google.common.hash.HashCode
        public byte[] asBytes() {
            long j = this.hash;
            return new byte[]{(byte) j, (byte) (j >> 8), (byte) (j >> 16), (byte) (j >> 24), (byte) (j >> 32), (byte) (j >> 40), (byte) (j >> 48), (byte) (j >> 56)};
        }

        @Override // com.google.common.hash.HashCode
        public int asInt() {
            return (int) this.hash;
        }

        @Override // com.google.common.hash.HashCode
        public long asLong() {
            return this.hash;
        }

        @Override // com.google.common.hash.HashCode
        public long padToLong() {
            return this.hash;
        }

        @Override // com.google.common.hash.HashCode
        void a(byte[] bArr, int i, int i2) {
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i + i3] = (byte) (this.hash >> (i3 * 8));
            }
        }

        @Override // com.google.common.hash.HashCode
        boolean a(HashCode hashCode) {
            return this.hash == hashCode.asLong();
        }
    }

    public static HashCode fromBytes(byte[] bArr) {
        boolean z = true;
        if (bArr.length < 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "A HashCode must contain at least 1 byte.");
        return a((byte[]) bArr.clone());
    }

    public static HashCode a(byte[] bArr) {
        return new a(bArr);
    }

    /* loaded from: classes2.dex */
    public static final class a extends HashCode implements Serializable {
        private static final long serialVersionUID = 0;
        final byte[] bytes;

        a(byte[] bArr) {
            this.bytes = (byte[]) Preconditions.checkNotNull(bArr);
        }

        @Override // com.google.common.hash.HashCode
        public int bits() {
            return this.bytes.length * 8;
        }

        @Override // com.google.common.hash.HashCode
        public byte[] asBytes() {
            return (byte[]) this.bytes.clone();
        }

        @Override // com.google.common.hash.HashCode
        public int asInt() {
            Preconditions.checkState(this.bytes.length >= 4, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", this.bytes.length);
            byte[] bArr = this.bytes;
            return ((bArr[3] & 255) << 24) | ((bArr[1] & 255) << 8) | (bArr[0] & 255) | ((bArr[2] & 255) << 16);
        }

        @Override // com.google.common.hash.HashCode
        public long asLong() {
            Preconditions.checkState(this.bytes.length >= 8, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", this.bytes.length);
            return padToLong();
        }

        @Override // com.google.common.hash.HashCode
        public long padToLong() {
            long j = this.bytes[0] & 255;
            for (int i = 1; i < Math.min(this.bytes.length, 8); i++) {
                j |= (this.bytes[i] & 255) << (i * 8);
            }
            return j;
        }

        @Override // com.google.common.hash.HashCode
        void a(byte[] bArr, int i, int i2) {
            System.arraycopy(this.bytes, 0, bArr, i, i2);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.hash.HashCode
        public byte[] a() {
            return this.bytes;
        }

        @Override // com.google.common.hash.HashCode
        boolean a(HashCode hashCode) {
            if (this.bytes.length != hashCode.a().length) {
                return false;
            }
            boolean z = true;
            int i = 0;
            while (true) {
                byte[] bArr = this.bytes;
                if (i >= bArr.length) {
                    return z;
                }
                z &= bArr[i] == hashCode.a()[i];
                i++;
            }
        }
    }

    public static HashCode fromString(String str) {
        boolean z = true;
        Preconditions.checkArgument(str.length() >= 2, "input string (%s) must have at least 2 characters", str);
        if (str.length() % 2 != 0) {
            z = false;
        }
        Preconditions.checkArgument(z, "input string (%s) must have an even number of characters", str);
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            bArr[i / 2] = (byte) ((a(str.charAt(i)) << 4) + a(str.charAt(i + 1)));
        }
        return a(bArr);
    }

    private static int a(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return (c2 - 'a') + 10;
        }
        throw new IllegalArgumentException("Illegal hexadecimal character: " + c2);
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof HashCode)) {
            return false;
        }
        HashCode hashCode = (HashCode) obj;
        return bits() == hashCode.bits() && a(hashCode);
    }

    public final int hashCode() {
        if (bits() >= 32) {
            return asInt();
        }
        byte[] a2 = a();
        int i = a2[0] & 255;
        for (int i2 = 1; i2 < a2.length; i2++) {
            i |= (a2[i2] & 255) << (i2 * 8);
        }
        return i;
    }

    public final String toString() {
        byte[] a2 = a();
        StringBuilder sb = new StringBuilder(a2.length * 2);
        for (byte b2 : a2) {
            sb.append(a[(b2 >> 4) & 15]);
            sb.append(a[b2 & 15]);
        }
        return sb.toString();
    }
}
