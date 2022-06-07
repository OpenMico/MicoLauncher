package com.google.common.hash;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Immutable;
import com.xiaomi.mico.base.utils.CommonUtils;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Murmur3_32HashFunction.java */
@Immutable
/* loaded from: classes2.dex */
public final class s extends c implements Serializable {
    static final HashFunction a = new s(0);
    static final HashFunction b = new s(Hashing.a);
    private static final long serialVersionUID = 0;
    private final int seed;

    /* JADX INFO: Access modifiers changed from: private */
    public static long c(char c) {
        return (((c & '?') | 128) << 16) | (((c >>> '\f') | CommonUtils.IMAGE_WIDTH_THRESHOLD) & 255) | ((((c >>> 6) & 63) | 128) << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long d(char c) {
        return (((c & '?') | 128) << 8) | (((c >>> 6) | 960) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long d(int i) {
        return (((i >>> 18) | 240) & 255) | ((((i >>> 12) & 63) | 128) << 8) | ((((i >>> 6) & 63) | 128) << 16) | (((i & 63) | 128) << 24);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 32;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(int i) {
        this.seed = i;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new a(this.seed);
    }

    public String toString() {
        return "Hashing.murmur3_32(" + this.seed + ")";
    }

    public boolean equals(@NullableDecl Object obj) {
        return (obj instanceof s) && this.seed == ((s) obj).seed;
    }

    public int hashCode() {
        return getClass().hashCode() ^ this.seed;
    }

    @Override // com.google.common.hash.c, com.google.common.hash.HashFunction
    public HashCode hashInt(int i) {
        return d(c(this.seed, c(i)), 4);
    }

    @Override // com.google.common.hash.c, com.google.common.hash.HashFunction
    public HashCode hashLong(long j) {
        return d(c(c(this.seed, c((int) j)), c((int) (j >>> 32))), 8);
    }

    @Override // com.google.common.hash.c, com.google.common.hash.HashFunction
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int i = this.seed;
        for (int i2 = 1; i2 < charSequence.length(); i2 += 2) {
            i = c(i, c(charSequence.charAt(i2 - 1) | (charSequence.charAt(i2) << 16)));
        }
        if ((charSequence.length() & 1) == 1) {
            i ^= c((int) charSequence.charAt(charSequence.length() - 1));
        }
        return d(i, charSequence.length() * 2);
    }

    @Override // com.google.common.hash.c, com.google.common.hash.HashFunction
    public HashCode hashString(CharSequence charSequence, Charset charset) {
        if (!Charsets.UTF_8.equals(charset)) {
            return hashBytes(charSequence.toString().getBytes(charset));
        }
        int length = charSequence.length();
        int i = 0;
        int i2 = this.seed;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i3 + 4;
            if (i5 > length) {
                break;
            }
            char charAt = charSequence.charAt(i3);
            char charAt2 = charSequence.charAt(i3 + 1);
            char charAt3 = charSequence.charAt(i3 + 2);
            char charAt4 = charSequence.charAt(i3 + 3);
            if (charAt >= 128 || charAt2 >= 128 || charAt3 >= 128 || charAt4 >= 128) {
                break;
            }
            i2 = c(i2, c((charAt2 << '\b') | charAt | (charAt3 << 16) | (charAt4 << 24)));
            i4 += 4;
            i3 = i5;
        }
        long j = 0;
        while (i3 < length) {
            char charAt5 = charSequence.charAt(i3);
            if (charAt5 < 128) {
                j |= charAt5 << i;
                i += 8;
                i4++;
            } else if (charAt5 < 2048) {
                j |= d(charAt5) << i;
                i += 16;
                i4 += 2;
            } else if (charAt5 < 55296 || charAt5 > 57343) {
                j |= c(charAt5) << i;
                i += 24;
                i4 += 3;
            } else {
                int codePointAt = Character.codePointAt(charSequence, i3);
                if (codePointAt == charAt5) {
                    return hashBytes(charSequence.toString().getBytes(charset));
                }
                i3++;
                j |= d(codePointAt) << i;
                i4 += 4;
            }
            if (i >= 32) {
                i2 = c(i2, c((int) j));
                j >>>= 32;
                i -= 32;
            }
            i3++;
        }
        return d(c((int) j) ^ i2, i4);
    }

    @Override // com.google.common.hash.c, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        int i3 = 0;
        int i4 = this.seed;
        int i5 = 0;
        while (true) {
            int i6 = i5 + 4;
            if (i6 > i2) {
                break;
            }
            i4 = c(i4, c(b(bArr, i5 + i)));
            i5 = i6;
        }
        int i7 = 0;
        while (i5 < i2) {
            i3 ^= UnsignedBytes.toInt(bArr[i + i5]) << i7;
            i5++;
            i7 += 8;
        }
        return d(c(i3) ^ i4, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(byte[] bArr, int i) {
        return Ints.fromBytes(bArr[i + 3], bArr[i + 2], bArr[i + 1], bArr[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(int i) {
        return Integer.rotateLeft(i * (-862048943), 15) * 461845907;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(int i, int i2) {
        return (Integer.rotateLeft(i ^ i2, 13) * 5) - 430675100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HashCode d(int i, int i2) {
        int i3 = i ^ i2;
        int i4 = (i3 ^ (i3 >>> 16)) * (-2048144789);
        int i5 = (i4 ^ (i4 >>> 13)) * (-1028477387);
        return HashCode.fromInt(i5 ^ (i5 >>> 16));
    }

    /* compiled from: Murmur3_32HashFunction.java */
    @CanIgnoreReturnValue
    /* loaded from: classes2.dex */
    private static final class a extends d {
        private int a;
        private long b;
        private int c;
        private int d = 0;
        private boolean e = false;

        a(int i) {
            this.a = i;
        }

        private void a(int i, long j) {
            long j2 = this.b;
            int i2 = this.c;
            this.b = ((j & 4294967295L) << i2) | j2;
            this.c = i2 + (i * 8);
            this.d += i;
            if (this.c >= 32) {
                this.a = s.c(this.a, s.c((int) this.b));
                this.b >>>= 32;
                this.c -= 32;
            }
        }

        @Override // com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putByte(byte b) {
            a(1, b & 255);
            return this;
        }

        @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putBytes(byte[] bArr, int i, int i2) {
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            while (true) {
                int i4 = i3 + 4;
                if (i4 <= i2) {
                    a(4, s.b(bArr, i3 + i));
                    i3 = i4;
                }
            }
            while (i3 < i2) {
                putByte(bArr[i + i3]);
                i3++;
            }
            return this;
        }

        @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putBytes(ByteBuffer byteBuffer) {
            ByteOrder order = byteBuffer.order();
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            while (byteBuffer.remaining() >= 4) {
                putInt(byteBuffer.getInt());
            }
            while (byteBuffer.hasRemaining()) {
                putByte(byteBuffer.get());
            }
            byteBuffer.order(order);
            return this;
        }

        @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putInt(int i) {
            a(4, i);
            return this;
        }

        @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putLong(long j) {
            a(4, (int) j);
            a(4, j >>> 32);
            return this;
        }

        @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putChar(char c) {
            a(2, c);
            return this;
        }

        @Override // com.google.common.hash.d, com.google.common.hash.Hasher, com.google.common.hash.PrimitiveSink
        public Hasher putString(CharSequence charSequence, Charset charset) {
            if (!Charsets.UTF_8.equals(charset)) {
                return super.putString(charSequence, charset);
            }
            int length = charSequence.length();
            int i = 0;
            while (true) {
                int i2 = i + 4;
                if (i2 > length) {
                    break;
                }
                char charAt = charSequence.charAt(i);
                char charAt2 = charSequence.charAt(i + 1);
                char charAt3 = charSequence.charAt(i + 2);
                char charAt4 = charSequence.charAt(i + 3);
                if (charAt >= 128 || charAt2 >= 128 || charAt3 >= 128 || charAt4 >= 128) {
                    break;
                }
                a(4, (charAt2 << '\b') | charAt | (charAt3 << 16) | (charAt4 << 24));
                i = i2;
            }
            while (i < length) {
                char charAt5 = charSequence.charAt(i);
                if (charAt5 < 128) {
                    a(1, charAt5);
                } else if (charAt5 < 2048) {
                    a(2, s.d(charAt5));
                } else if (charAt5 < 55296 || charAt5 > 57343) {
                    a(3, s.c(charAt5));
                } else {
                    int codePointAt = Character.codePointAt(charSequence, i);
                    if (codePointAt == charAt5) {
                        putBytes(charSequence.subSequence(i, length).toString().getBytes(charset));
                        return this;
                    }
                    i++;
                    a(4, s.d(codePointAt));
                }
                i++;
            }
            return this;
        }

        @Override // com.google.common.hash.Hasher
        public HashCode hash() {
            Preconditions.checkState(!this.e);
            this.e = true;
            this.a ^= s.c((int) this.b);
            return s.d(this.a, this.d);
        }
    }
}
