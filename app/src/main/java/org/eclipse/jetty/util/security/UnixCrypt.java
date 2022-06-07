package org.eclipse.jetty.util.security;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.io.PrintStream;
import java.lang.reflect.Array;
import okio.Utf8;

/* loaded from: classes5.dex */
public class UnixCrypt {
    private static final byte[] IP = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, HttpConstants.COMMA, BinaryMemcacheOpcodes.GATKQ, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, SignedBytes.MAX_POWER_OF_TWO, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, BinaryMemcacheOpcodes.SASL_AUTH, 25, 17, 9, 1, 59, 51, 43, BinaryMemcacheOpcodes.GATK, Ascii.ESC, 19, 11, 3, HttpConstants.EQUALS, 53, 45, 37, 29, 21, 13, 5, Utf8.REPLACEMENT_BYTE, 55, 47, 39, Ascii.US, 23, 15, 7};
    private static final byte[] ExpandTr = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, Ascii.ESC, 28, 29, 28, 29, 30, Ascii.US, 32, 1};
    private static final byte[] PC1 = {57, 49, 41, BinaryMemcacheOpcodes.SASL_AUTH, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, BinaryMemcacheOpcodes.GATK, Ascii.ESC, 19, 11, 3, 60, 52, HttpConstants.COMMA, BinaryMemcacheOpcodes.GATKQ, Utf8.REPLACEMENT_BYTE, 55, 47, 39, Ascii.US, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, HttpConstants.EQUALS, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
    private static final byte[] Rotates = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    private static final byte[] PC2 = {9, 18, 14, 17, 11, 24, 1, 5, 22, 25, 3, 28, 15, 6, 21, 10, BinaryMemcacheOpcodes.GATK, 38, 23, 19, 12, 4, 26, 8, 43, 54, 16, 7, Ascii.ESC, 20, 13, 2, 0, 0, 41, 52, Ascii.US, 37, 47, 55, 0, 0, 30, 40, 51, 45, BinaryMemcacheOpcodes.SASL_AUTH, 48, 0, 0, HttpConstants.COMMA, 49, 39, 56, 34, 53, 0, 0, 46, 42, 50, BinaryMemcacheOpcodes.GATKQ, 29, 32};
    private static final byte[][] S = {new byte[]{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}, new byte[]{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}, new byte[]{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}, new byte[]{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}, new byte[]{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}, new byte[]{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}, new byte[]{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}, new byte[]{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};
    private static final byte[] P32Tr = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, Ascii.US, 10, 2, 8, 24, 14, 32, Ascii.ESC, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};
    private static final byte[] CIFP = {1, 2, 3, 4, 17, 18, 19, 20, 5, 6, 7, 8, 21, 22, 23, 24, 9, 10, 11, 12, 25, 26, Ascii.ESC, 28, 13, 14, 15, 16, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 49, 50, 51, 52, 37, 38, 39, 40, 53, 54, 55, 56, 41, 42, 43, HttpConstants.COMMA, 57, 58, 59, 60, 45, 46, 47, 48, HttpConstants.EQUALS, 62, Utf8.REPLACEMENT_BYTE, SignedBytes.MAX_POWER_OF_TWO};
    private static final byte[] ITOA64 = {46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] A64TOI = new byte[128];
    private static final long[][] PC1ROT = (long[][]) Array.newInstance(long.class, 16, 16);
    private static final long[][][] PC2ROT = (long[][][]) Array.newInstance(long.class, 2, 16, 16);
    private static final long[][] IE3264 = (long[][]) Array.newInstance(long.class, 8, 16);
    private static final long[][] SPE = (long[][]) Array.newInstance(long.class, 8, 64);
    private static final long[][] CF6464 = (long[][]) Array.newInstance(long.class, 16, 16);

    private static int to_six_bit(int i) {
        return ((i >> 16) & 252) | ((i << 26) & (-67108864)) | ((i << 12) & 16515072) | ((i >> 2) & 64512);
    }

    private static long to_six_bit(long j) {
        return ((j >> 16) & 1082331758844L) | ((j << 26) & (-288230371923853312L)) | ((j << 12) & 70931694147600384L) | ((j >> 2) & 277076930264064L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v39 */
    static {
        int i = 64;
        int i2 = 2;
        int i3 = 3;
        byte[] bArr = new byte[64];
        byte[] bArr2 = new byte[64];
        for (int i4 = 0; i4 < 64; i4++) {
            A64TOI[ITOA64[i4]] = (byte) i4;
        }
        for (int i5 = 0; i5 < 64; i5++) {
            bArr[i5] = 0;
        }
        for (int i6 = 0; i6 < 64; i6++) {
            byte b = PC2[i6];
            if (b != 0) {
                byte[] bArr3 = Rotates;
                int i7 = b + (bArr3[0] - 1);
                if (i7 % 28 < bArr3[0]) {
                    i7 -= 28;
                }
                byte b2 = PC1[i7];
                byte b3 = b2;
                if (b2 > 0) {
                    int i8 = b2 - 1;
                    b3 = ((i8 | 7) - (i8 & 7)) + 1;
                }
                bArr[i6] = b3 == 1 ? (byte) 1 : (byte) 0;
            }
        }
        init_perm(PC1ROT, bArr, 8);
        for (int i9 = 0; i9 < 2; i9++) {
            for (int i10 = 0; i10 < 64; i10++) {
                bArr2[i10] = 0;
                bArr[i10] = 0;
            }
            for (int i11 = 0; i11 < 64; i11++) {
                byte b4 = PC2[i11];
                if (b4 != 0) {
                    bArr2[b4 - 1] = (byte) (i11 + 1);
                }
            }
            for (int i12 = 0; i12 < 64; i12++) {
                byte b5 = PC2[i12];
                if (b5 != 0) {
                    int i13 = b5 + i9;
                    if (i13 % 28 <= i9) {
                        i13 -= 28;
                    }
                    bArr[i12] = bArr2[i13];
                }
            }
            init_perm(PC2ROT[i9], bArr, 8);
        }
        for (int i14 = 0; i14 < 8; i14++) {
            int i15 = 0;
            while (i15 < 8) {
                int i16 = i15 < 2 ? 0 : IP[ExpandTr[((i14 * 6) + i15) - 2] - 1];
                if (i16 > 32) {
                    i16 -= 32;
                } else if (i16 > 0) {
                    i16--;
                }
                if (i16 > 0) {
                    int i17 = i16 - 1;
                    i16 = ((i17 | 7) - (i17 & 7)) + 1;
                }
                bArr[(i14 * 8) + i15] = i16 == 1 ? (byte) 1 : (byte) 0;
                i15++;
            }
        }
        init_perm(IE3264, bArr, 8);
        int i18 = 0;
        while (i18 < 64) {
            byte b6 = IP[CIFP[i18] - 1];
            if (b6 > 0) {
                int i19 = b6 - 1;
                b6 = ((i19 | 7) - (i19 & 7)) + 1;
            }
            i18++;
            bArr[(b6 == true ? 1 : 0) - 1] = (byte) i18;
        }
        init_perm(CF6464, bArr, 8);
        for (int i20 = 0; i20 < 48; i20++) {
            bArr[i20] = P32Tr[ExpandTr[i20] - 1];
        }
        int i21 = 0;
        while (i21 < 8) {
            int i22 = 0;
            while (i22 < i) {
                byte b7 = S[i21][(((i22 >> 0) & 1) << 5) | (((i22 >> 1) & 1) << i3) | (((i22 >> 2) & 1) << i2) | (((i22 >> 3) & 1) << 1) | (((i22 >> 4) & 1) << 0) | (((i22 >> 5) & 1) << 4)];
                int i23 = (((b7 >> 0) & 1) << i3) | (((b7 >> 3) & 1) << 0) | (((b7 >> 2) & 1) << 1) | (((b7 >> 1) & 1) << i2);
                for (int i24 = 0; i24 < 32; i24++) {
                    bArr2[i24] = 0;
                }
                for (int i25 = 0; i25 < 4; i25++) {
                    bArr2[(i21 * 4) + i25] = (byte) ((i23 >> i25) & 1);
                }
                long j = 0;
                int i26 = 24;
                while (true) {
                    i26--;
                    if (i26 >= 0) {
                        j = bArr2[bArr[i26 + 24] - 1] | (bArr2[bArr[i26] - 1] << 32) | (j << 1);
                    }
                }
                SPE[i21][i22] = to_six_bit(j);
                i22++;
                i = 64;
                i2 = 2;
                i3 = 3;
            }
            i21++;
            i = 64;
            i2 = 2;
            i3 = 3;
        }
    }

    private UnixCrypt() {
    }

    private static long perm6464(long j, long[][] jArr) {
        long j2 = 0;
        long j3 = j;
        int i = 8;
        while (true) {
            i--;
            if (i < 0) {
                return j2;
            }
            int i2 = (int) (255 & j3);
            j3 >>= 8;
            int i3 = i << 1;
            j2 = j2 | jArr[i3][i2 & 15] | jArr[i3 + 1][i2 >> 4];
        }
    }

    private static long perm3264(int i, long[][] jArr) {
        long j = 0;
        int i2 = i;
        int i3 = 4;
        while (true) {
            i3--;
            if (i3 < 0) {
                return j;
            }
            int i4 = i2 & 255;
            i2 >>= 8;
            int i5 = i3 << 1;
            j = j | jArr[i5][i4 & 15] | jArr[i5 + 1][i4 >> 4];
        }
    }

    private static long[] des_setkey(long j) {
        long perm6464 = perm6464(j, PC1ROT);
        long[] jArr = new long[16];
        jArr[0] = perm6464 & (-217020518463700993L);
        long j2 = perm6464;
        for (int i = 1; i < 16; i++) {
            jArr[i] = j2;
            j2 = perm6464(j2, PC2ROT[Rotates[i] - 1]);
            jArr[i] = j2 & (-217020518463700993L);
        }
        return jArr;
    }

    private static long des_cipher(long j, int i, int i2, long[] jArr) {
        int i3 = to_six_bit(i);
        long j2 = j & 6148914691236517205L;
        int i4 = 1;
        long j3 = (j & (-6148914694099828736L)) | ((j >> 1) & 1431655765);
        char c = ' ';
        long j4 = 4294967295L;
        long perm3264 = perm3264((int) (((((j2 << 32) | (j2 << 1)) & (-4294967296L)) | ((j3 | (j3 >> 32)) & 4294967295L)) >> 32), IE3264);
        long perm32642 = perm3264((int) (perm3264 & (-1)), IE3264);
        long j5 = perm3264;
        int i5 = i2;
        while (true) {
            i5--;
            if (i5 < 0) {
                return perm6464(((((j5 >> 35) & 252645135) | (((j5 & (-1)) << 1) & 4042322160L)) << 32) | (252645135 & (perm32642 >> 35)) | ((((-1) & perm32642) << 1) & 4042322160L), CF6464);
            }
            char c2 = 0;
            long j6 = perm32642;
            long j7 = j5;
            int i6 = 0;
            while (i6 < 8) {
                int i7 = i6 << 1;
                long j8 = i3;
                long j9 = ((j6 >> c) ^ j6) & j8 & j4;
                long j10 = ((j9 | (j9 << c)) ^ j6) ^ jArr[i7];
                long[][] jArr2 = SPE;
                j7 ^= ((((((jArr2[i4][(int) ((j10 >> 50) & 63)] ^ jArr2[c2][(int) ((j10 >> 58) & 63)]) ^ jArr2[2][(int) ((j10 >> 42) & 63)]) ^ jArr2[3][(int) ((j10 >> 34) & 63)]) ^ jArr2[4][(int) ((j10 >> 26) & 63)]) ^ jArr2[5][(int) ((j10 >> 18) & 63)]) ^ jArr2[6][(int) ((j10 >> 10) & 63)]) ^ jArr2[7][(int) ((j10 >> 2) & 63)];
                long j11 = ((j7 >> 32) ^ j7) & j8 & 4294967295L;
                long j12 = jArr[i7 + i4] ^ ((j11 | (j11 << 32)) ^ j7);
                j6 ^= jArr2[7][(int) ((j12 >> 2) & 63)] ^ ((((((jArr2[i4][(int) ((j12 >> 50) & 63)] ^ jArr2[0][(int) ((j12 >> 58) & 63)]) ^ jArr2[2][(int) ((j12 >> 42) & 63)]) ^ jArr2[3][(int) ((j12 >> 34) & 63)]) ^ jArr2[4][(int) ((j12 >> 26) & 63)]) ^ jArr2[5][(int) ((j12 >> 18) & 63)]) ^ jArr2[6][(int) ((j12 >> 10) & 63)]);
                i6++;
                c2 = 0;
                i4 = 1;
                j4 = 4294967295L;
                c = ' ';
            }
            long j13 = j7 ^ j6;
            perm32642 = j6 ^ j13;
            j5 = j13 ^ perm32642;
            i4 = 1;
            j4 = 4294967295L;
            c = ' ';
        }
    }

    private static void init_perm(long[][] jArr, byte[] bArr, int i) {
        for (int i2 = 0; i2 < i * 8; i2++) {
            int i3 = bArr[i2] - 1;
            if (i3 >= 0) {
                int i4 = i3 >> 2;
                int i5 = 1 << (i3 & 3);
                for (int i6 = 0; i6 < 16; i6++) {
                    int i7 = (i2 & 7) + ((7 - (i2 >> 3)) << 3);
                    if ((i6 & i5) != 0) {
                        long[] jArr2 = jArr[i4];
                        jArr2[i6] = jArr2[i6] | (1 << i7);
                    }
                }
            }
        }
    }

    public static String crypt(String str, String str2) {
        byte[] bArr = new byte[13];
        if (str == null || str2 == null) {
            return "*";
        }
        int length = str.length();
        long j = 0;
        int i = 0;
        while (i < 8) {
            j = (j << 8) | (i < length ? str.charAt(i) * 2 : 0);
            i++;
        }
        long[] des_setkey = des_setkey(j);
        int i2 = 0;
        int i3 = 2;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            char charAt = i3 < str2.length() ? str2.charAt(i3) : '.';
            bArr[i3] = (byte) charAt;
            i2 = (i2 << 6) | (A64TOI[charAt] & 255);
        }
        long des_cipher = des_cipher(0L, i2, 25, des_setkey);
        int i4 = 12;
        bArr[12] = ITOA64[(((int) des_cipher) << 2) & 63];
        char c = 4;
        while (true) {
            des_cipher >>= c;
            i4--;
            if (i4 < 2) {
                return new String(bArr, 0, 13);
            }
            bArr[i4] = ITOA64[((int) des_cipher) & 63];
            c = 6;
        }
    }

    public static void main(String[] strArr) {
        if (strArr.length != 2) {
            System.err.println("Usage - java org.eclipse.util.UnixCrypt <key> <salt>");
            System.exit(1);
        }
        PrintStream printStream = System.err;
        printStream.println("Crypt=" + crypt(strArr[0], strArr[1]));
    }
}
