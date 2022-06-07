package org.apache.commons.codec.binary;

import com.google.common.base.Ascii;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.math.BigInteger;
import okio.Utf8;
import org.apache.commons.codec.binary.BaseNCodec;

/* loaded from: classes5.dex */
public class Base64 extends BaseNCodec {
    static final byte[] a = {13, 10};
    private static final byte[] b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] d = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, Utf8.REPLACEMENT_BYTE, 52, 53, 54, 55, 56, 57, 58, 59, 60, HttpConstants.EQUALS, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, Utf8.REPLACEMENT_BYTE, -1, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32, BinaryMemcacheOpcodes.SASL_AUTH, 34, BinaryMemcacheOpcodes.GATK, BinaryMemcacheOpcodes.GATKQ, 37, 38, 39, 40, 41, 42, 43, HttpConstants.COMMA, 45, 46, 47, 48, 49, 50, 51};
    private final byte[] e;
    private final byte[] f;
    private final byte[] g;
    private final int h;
    private final int i;

    public Base64() {
        this(0);
    }

    public Base64(boolean z) {
        this(76, a, z);
    }

    public Base64(int i) {
        this(i, a);
    }

    public Base64(int i, byte[] bArr) {
        this(i, bArr, false);
    }

    public Base64(int i, byte[] bArr, boolean z) {
        super(3, 4, i, bArr == null ? 0 : bArr.length);
        this.f = d;
        if (bArr == null) {
            this.i = 4;
            this.g = null;
        } else if (containsAlphabetOrPad(bArr)) {
            String newStringUtf8 = StringUtils.newStringUtf8(bArr);
            throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + newStringUtf8 + "]");
        } else if (i > 0) {
            this.i = bArr.length + 4;
            this.g = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.g, 0, bArr.length);
        } else {
            this.i = 4;
            this.g = null;
        }
        this.h = this.i - 1;
        this.e = z ? c : b;
    }

    public boolean isUrlSafe() {
        return this.e == c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void b(byte[] bArr, int i, int i2, BaseNCodec.a aVar) {
        if (!aVar.f) {
            if (i2 < 0) {
                aVar.f = true;
                if (!(aVar.h == 0 && this.lineLength == 0)) {
                    byte[] ensureBufferSize = ensureBufferSize(this.i, aVar);
                    int i3 = aVar.d;
                    switch (aVar.h) {
                        case 0:
                            break;
                        case 1:
                            int i4 = aVar.d;
                            aVar.d = i4 + 1;
                            ensureBufferSize[i4] = this.e[(aVar.a >> 2) & 63];
                            int i5 = aVar.d;
                            aVar.d = i5 + 1;
                            ensureBufferSize[i5] = this.e[(aVar.a << 4) & 63];
                            if (this.e == b) {
                                int i6 = aVar.d;
                                aVar.d = i6 + 1;
                                ensureBufferSize[i6] = this.pad;
                                int i7 = aVar.d;
                                aVar.d = i7 + 1;
                                ensureBufferSize[i7] = this.pad;
                                break;
                            }
                            break;
                        case 2:
                            int i8 = aVar.d;
                            aVar.d = i8 + 1;
                            ensureBufferSize[i8] = this.e[(aVar.a >> 10) & 63];
                            int i9 = aVar.d;
                            aVar.d = i9 + 1;
                            ensureBufferSize[i9] = this.e[(aVar.a >> 4) & 63];
                            int i10 = aVar.d;
                            aVar.d = i10 + 1;
                            ensureBufferSize[i10] = this.e[(aVar.a << 2) & 63];
                            if (this.e == b) {
                                int i11 = aVar.d;
                                aVar.d = i11 + 1;
                                ensureBufferSize[i11] = this.pad;
                                break;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Impossible modulus " + aVar.h);
                    }
                    aVar.g += aVar.d - i3;
                    if (this.lineLength > 0 && aVar.g > 0) {
                        System.arraycopy(this.g, 0, ensureBufferSize, aVar.d, this.g.length);
                        aVar.d += this.g.length;
                        return;
                    }
                    return;
                }
                return;
            }
            int i12 = i;
            for (int i13 = 0; i13 < i2; i13++) {
                byte[] ensureBufferSize2 = ensureBufferSize(this.i, aVar);
                aVar.h = (aVar.h + 1) % 3;
                i12++;
                byte b2 = bArr[i12];
                int i14 = b2;
                if (b2 < 0) {
                    i14 = b2 + 256;
                }
                aVar.a = (aVar.a << 8) + (i14 == 1 ? 1 : 0);
                if (aVar.h == 0) {
                    int i15 = aVar.d;
                    aVar.d = i15 + 1;
                    ensureBufferSize2[i15] = this.e[(aVar.a >> 18) & 63];
                    int i16 = aVar.d;
                    aVar.d = i16 + 1;
                    ensureBufferSize2[i16] = this.e[(aVar.a >> 12) & 63];
                    int i17 = aVar.d;
                    aVar.d = i17 + 1;
                    ensureBufferSize2[i17] = this.e[(aVar.a >> 6) & 63];
                    int i18 = aVar.d;
                    aVar.d = i18 + 1;
                    ensureBufferSize2[i18] = this.e[aVar.a & 63];
                    aVar.g += 4;
                    if (this.lineLength > 0 && this.lineLength <= aVar.g) {
                        System.arraycopy(this.g, 0, ensureBufferSize2, aVar.d, this.g.length);
                        aVar.d += this.g.length;
                        aVar.g = 0;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void a(byte[] bArr, int i, int i2, BaseNCodec.a aVar) {
        byte b2;
        if (!aVar.f) {
            if (i2 < 0) {
                aVar.f = true;
            }
            int i3 = 0;
            while (true) {
                if (i3 >= i2) {
                    break;
                }
                byte[] ensureBufferSize = ensureBufferSize(this.h, aVar);
                i++;
                byte b3 = bArr[i];
                if (b3 == this.pad) {
                    aVar.f = true;
                    break;
                }
                if (b3 >= 0) {
                    byte[] bArr2 = d;
                    if (b3 < bArr2.length && (b2 = bArr2[b3]) >= 0) {
                        aVar.h = (aVar.h + 1) % 4;
                        aVar.a = (aVar.a << 6) + b2;
                        if (aVar.h == 0) {
                            int i4 = aVar.d;
                            aVar.d = i4 + 1;
                            ensureBufferSize[i4] = (byte) ((aVar.a >> 16) & 255);
                            int i5 = aVar.d;
                            aVar.d = i5 + 1;
                            ensureBufferSize[i5] = (byte) ((aVar.a >> 8) & 255);
                            int i6 = aVar.d;
                            aVar.d = i6 + 1;
                            ensureBufferSize[i6] = (byte) (aVar.a & 255);
                        }
                    }
                }
                i3++;
            }
            if (aVar.f && aVar.h != 0) {
                byte[] ensureBufferSize2 = ensureBufferSize(this.h, aVar);
                switch (aVar.h) {
                    case 1:
                        return;
                    case 2:
                        aVar.a >>= 4;
                        int i7 = aVar.d;
                        aVar.d = i7 + 1;
                        ensureBufferSize2[i7] = (byte) (aVar.a & 255);
                        return;
                    case 3:
                        aVar.a >>= 2;
                        int i8 = aVar.d;
                        aVar.d = i8 + 1;
                        ensureBufferSize2[i8] = (byte) ((aVar.a >> 8) & 255);
                        int i9 = aVar.d;
                        aVar.d = i9 + 1;
                        ensureBufferSize2[i9] = (byte) (aVar.a & 255);
                        return;
                    default:
                        throw new IllegalStateException("Impossible modulus " + aVar.h);
                }
            }
        }
    }

    @Deprecated
    public static boolean isArrayByteBase64(byte[] bArr) {
        return isBase64(bArr);
    }

    public static boolean isBase64(byte b2) {
        if (b2 != 61) {
            if (b2 >= 0) {
                byte[] bArr = d;
                if (b2 >= bArr.length || bArr[b2] == -1) {
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isBase64(String str) {
        return isBase64(StringUtils.getBytesUtf8(str));
    }

    public static boolean isBase64(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            if (!(isBase64(bArr[i]) || isWhiteSpace(bArr[i]))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] encodeBase64(byte[] bArr) {
        return encodeBase64(bArr, false);
    }

    public static String encodeBase64String(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] bArr) {
        return encodeBase64(bArr, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] bArr) {
        return StringUtils.newStringUtf8(encodeBase64(bArr, false, true));
    }

    public static byte[] encodeBase64Chunked(byte[] bArr) {
        return encodeBase64(bArr, true);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z) {
        return encodeBase64(bArr, z, false);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z, boolean z2) {
        return encodeBase64(bArr, z, z2, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] bArr, boolean z, boolean z2, int i) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        Base64 base64 = z ? new Base64(z2) : new Base64(0, a, z2);
        long encodedLength = base64.getEncodedLength(bArr);
        if (encodedLength <= i) {
            return base64.encode(bArr);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + encodedLength + ") than the specified maximum size of " + i);
    }

    public static byte[] decodeBase64(String str) {
        return new Base64().decode(str);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        return new Base64().decode(bArr);
    }

    public static BigInteger decodeInteger(byte[] bArr) {
        return new BigInteger(1, decodeBase64(bArr));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger != null) {
            return encodeBase64(a(bigInteger), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    static byte[] a(BigInteger bigInteger) {
        int bitLength = ((bigInteger.bitLength() + 7) >> 3) << 3;
        byte[] byteArray = bigInteger.toByteArray();
        if (bigInteger.bitLength() % 8 != 0 && (bigInteger.bitLength() / 8) + 1 == bitLength / 8) {
            return byteArray;
        }
        int i = 0;
        int length = byteArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            length--;
            i = 1;
        }
        int i2 = bitLength / 8;
        int i3 = i2 - length;
        byte[] bArr = new byte[i2];
        System.arraycopy(byteArray, i, bArr, i3, length);
        return bArr;
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    protected boolean isInAlphabet(byte b2) {
        if (b2 >= 0) {
            byte[] bArr = this.f;
            if (b2 < bArr.length && bArr[b2] != -1) {
                return true;
            }
        }
        return false;
    }
}
