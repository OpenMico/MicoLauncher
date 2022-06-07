package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Checksum;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class Hashing {
    static final int a = (int) System.currentTimeMillis();

    /* loaded from: classes2.dex */
    private static class d {
        static final HashFunction a = new q("MD5", "Hashing.md5()");
    }

    /* loaded from: classes2.dex */
    private static class e {
        static final HashFunction a = new q(MessageDigestAlgorithms.SHA_1, "Hashing.sha1()");
    }

    /* loaded from: classes2.dex */
    private static class f {
        static final HashFunction a = new q(MessageDigestAlgorithms.SHA_256, "Hashing.sha256()");
    }

    /* loaded from: classes2.dex */
    private static class g {
        static final HashFunction a = new q(MessageDigestAlgorithms.SHA_384, "Hashing.sha384()");
    }

    /* loaded from: classes2.dex */
    private static class h {
        static final HashFunction a = new q(MessageDigestAlgorithms.SHA_512, "Hashing.sha512()");
    }

    public static HashFunction goodFastHash(int i) {
        int a2 = a(i);
        if (a2 == 32) {
            return s.b;
        }
        if (a2 <= 128) {
            return r.b;
        }
        int i2 = (a2 + 127) / 128;
        HashFunction[] hashFunctionArr = new HashFunction[i2];
        hashFunctionArr[0] = r.b;
        int i3 = a;
        for (int i4 = 1; i4 < i2; i4++) {
            i3 += 1500450271;
            hashFunctionArr[i4] = murmur3_128(i3);
        }
        return new b(hashFunctionArr);
    }

    public static HashFunction murmur3_32(int i) {
        return new s(i);
    }

    public static HashFunction murmur3_32() {
        return s.a;
    }

    public static HashFunction murmur3_128(int i) {
        return new r(i);
    }

    public static HashFunction murmur3_128() {
        return r.a;
    }

    public static HashFunction sipHash24() {
        return t.a;
    }

    public static HashFunction sipHash24(long j, long j2) {
        return new t(2, 4, j, j2);
    }

    @Deprecated
    public static HashFunction md5() {
        return d.a;
    }

    @Deprecated
    public static HashFunction sha1() {
        return e.a;
    }

    public static HashFunction sha256() {
        return f.a;
    }

    public static HashFunction sha384() {
        return g.a;
    }

    public static HashFunction sha512() {
        return h.a;
    }

    public static HashFunction hmacMd5(Key key) {
        return new p("HmacMD5", key, a("hmacMd5", key));
    }

    public static HashFunction hmacMd5(byte[] bArr) {
        return hmacMd5(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacMD5"));
    }

    public static HashFunction hmacSha1(Key key) {
        return new p("HmacSHA1", key, a("hmacSha1", key));
    }

    public static HashFunction hmacSha1(byte[] bArr) {
        return hmacSha1(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA1"));
    }

    public static HashFunction hmacSha256(Key key) {
        return new p("HmacSHA256", key, a("hmacSha256", key));
    }

    public static HashFunction hmacSha256(byte[] bArr) {
        return hmacSha256(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA256"));
    }

    public static HashFunction hmacSha512(Key key) {
        return new p("HmacSHA512", key, a("hmacSha512", key));
    }

    public static HashFunction hmacSha512(byte[] bArr) {
        return hmacSha512(new SecretKeySpec((byte[]) Preconditions.checkNotNull(bArr), "HmacSHA512"));
    }

    private static String a(String str, Key key) {
        return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", str, key.getAlgorithm(), key.getFormat());
    }

    public static HashFunction crc32c() {
        return i.a;
    }

    public static HashFunction crc32() {
        return a.CRC_32.hashFunction;
    }

    public static HashFunction adler32() {
        return a.ADLER_32.hashFunction;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    @Immutable
    /* loaded from: classes2.dex */
    public static abstract class a extends Enum<a> implements k<Checksum> {
        public final HashFunction hashFunction;

        private a(String str, int i, String str2) {
            this.hashFunction = new h(this, 32, str2);
        }
    }

    public static HashFunction farmHashFingerprint64() {
        return j.a;
    }

    public static int consistentHash(HashCode hashCode, int i) {
        return consistentHash(hashCode.padToLong(), i);
    }

    public static int consistentHash(long j, int i) {
        int i2 = 0;
        Preconditions.checkArgument(i > 0, "buckets must be positive: %s", i);
        c cVar = new c(j);
        while (true) {
            int a2 = (int) ((i2 + 1) / cVar.a());
            if (a2 < 0 || a2 >= i) {
                break;
            }
            i2 = a2;
        }
        return i2;
    }

    public static HashCode combineOrdered(Iterable<HashCode> iterable) {
        Iterator<HashCode> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] bArr = new byte[it.next().bits() / 8];
        for (HashCode hashCode : iterable) {
            byte[] asBytes = hashCode.asBytes();
            Preconditions.checkArgument(asBytes.length == bArr.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < asBytes.length; i++) {
                bArr[i] = (byte) ((bArr[i] * 37) ^ asBytes[i]);
            }
        }
        return HashCode.a(bArr);
    }

    public static HashCode combineUnordered(Iterable<HashCode> iterable) {
        Iterator<HashCode> it = iterable.iterator();
        Preconditions.checkArgument(it.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] bArr = new byte[it.next().bits() / 8];
        for (HashCode hashCode : iterable) {
            byte[] asBytes = hashCode.asBytes();
            Preconditions.checkArgument(asBytes.length == bArr.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < asBytes.length; i++) {
                bArr[i] = (byte) (bArr[i] + asBytes[i]);
            }
        }
        return HashCode.a(bArr);
    }

    static int a(int i) {
        Preconditions.checkArgument(i > 0, "Number of bits must be positive");
        return (i + 31) & (-32);
    }

    public static HashFunction concatenating(HashFunction hashFunction, HashFunction hashFunction2, HashFunction... hashFunctionArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(hashFunction);
        arrayList.add(hashFunction2);
        arrayList.addAll(Arrays.asList(hashFunctionArr));
        return new b((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    public static HashFunction concatenating(Iterable<HashFunction> iterable) {
        Preconditions.checkNotNull(iterable);
        ArrayList arrayList = new ArrayList();
        for (HashFunction hashFunction : iterable) {
            arrayList.add(hashFunction);
        }
        Preconditions.checkArgument(arrayList.size() > 0, "number of hash functions (%s) must be > 0", arrayList.size());
        return new b((HashFunction[]) arrayList.toArray(new HashFunction[0]));
    }

    /* loaded from: classes2.dex */
    private static final class b extends b {
        private b(HashFunction... hashFunctionArr) {
            super(hashFunctionArr);
            for (HashFunction hashFunction : hashFunctionArr) {
                Preconditions.checkArgument(hashFunction.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", hashFunction.bits(), (Object) hashFunction);
            }
        }

        @Override // com.google.common.hash.b
        HashCode a(Hasher[] hasherArr) {
            byte[] bArr = new byte[bits() / 8];
            int i = 0;
            for (Hasher hasher : hasherArr) {
                HashCode hash = hasher.hash();
                i += hash.writeBytesTo(bArr, i, hash.bits() / 8);
            }
            return HashCode.a(bArr);
        }

        @Override // com.google.common.hash.HashFunction
        public int bits() {
            int i = 0;
            for (HashFunction hashFunction : this.a) {
                i += hashFunction.bits();
            }
            return i;
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof b) {
                return Arrays.equals(this.a, ((b) obj).a);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.a);
        }
    }

    /* loaded from: classes2.dex */
    public static final class c {
        private long a;

        public c(long j) {
            this.a = j;
        }

        public double a() {
            this.a = (this.a * 2862933555777941757L) + 1;
            return (((int) (this.a >>> 33)) + 1) / 2.147483648E9d;
        }
    }

    private Hashing() {
    }
}
