package org.apache.commons.codec.binary;

import com.google.common.base.Ascii;
import io.netty.handler.codec.http.HttpConstants;
import org.apache.commons.codec.binary.BaseNCodec;

/* loaded from: classes5.dex */
public class Base32 extends BaseNCodec {
    private static final byte[] a = {13, 10};
    private static final byte[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, Ascii.ESC, 28, 29, 30, Ascii.US, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    private static final byte[] c = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55};
    private static final byte[] d = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, Ascii.ESC, 28, 29, 30, Ascii.US, 32};
    private static final byte[] e = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86};
    private final int f;
    private final byte[] g;
    private final int h;
    private final byte[] i;
    private final byte[] j;

    public Base32() {
        this(false);
    }

    public Base32(byte b2) {
        this(false, b2);
    }

    public Base32(boolean z) {
        this(0, null, z, HttpConstants.EQUALS);
    }

    public Base32(boolean z, byte b2) {
        this(0, null, z, b2);
    }

    public Base32(int i) {
        this(i, a);
    }

    public Base32(int i, byte[] bArr) {
        this(i, bArr, false, HttpConstants.EQUALS);
    }

    public Base32(int i, byte[] bArr, boolean z) {
        this(i, bArr, z, HttpConstants.EQUALS);
    }

    public Base32(int i, byte[] bArr, boolean z, byte b2) {
        super(5, 8, i, bArr == null ? 0 : bArr.length, b2);
        if (z) {
            this.i = e;
            this.g = d;
        } else {
            this.i = c;
            this.g = b;
        }
        if (i <= 0) {
            this.h = 8;
            this.j = null;
        } else if (bArr == null) {
            throw new IllegalArgumentException("lineLength " + i + " > 0, but lineSeparator is null");
        } else if (!containsAlphabetOrPad(bArr)) {
            this.h = bArr.length + 8;
            this.j = new byte[bArr.length];
            System.arraycopy(bArr, 0, this.j, 0, bArr.length);
        } else {
            String newStringUtf8 = StringUtils.newStringUtf8(bArr);
            throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + newStringUtf8 + "]");
        }
        this.f = this.h - 1;
        if (isInAlphabet(b2) || isWhiteSpace(b2)) {
            throw new IllegalArgumentException("pad must not be in alphabet or whitespace");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void a(byte[] bArr, int i, int i2, BaseNCodec.a aVar) {
        int i3;
        if (!aVar.f) {
            boolean z = true;
            if (i2 < 0) {
                aVar.f = true;
            }
            int i4 = 0;
            int i5 = i;
            while (true) {
                if (i4 >= i2) {
                    break;
                }
                i5++;
                byte b2 = bArr[i5];
                if (b2 == this.pad) {
                    aVar.f = z;
                    break;
                }
                byte[] ensureBufferSize = ensureBufferSize(this.f, aVar);
                if (b2 >= 0) {
                    byte[] bArr2 = this.g;
                    if (b2 < bArr2.length) {
                        byte b3 = bArr2[b2];
                        if (b3 >= 0) {
                            int i6 = aVar.h;
                            int i7 = z ? 1 : 0;
                            int i8 = z ? 1 : 0;
                            int i9 = z ? 1 : 0;
                            aVar.h = (i6 + i7) % 8;
                            i3 = i4;
                            aVar.b = (aVar.b << 5) + b3;
                            if (aVar.h == 0) {
                                int i10 = aVar.d;
                                aVar.d = i10 + 1;
                                ensureBufferSize[i10] = (byte) ((aVar.b >> 32) & 255);
                                int i11 = aVar.d;
                                aVar.d = i11 + 1;
                                ensureBufferSize[i11] = (byte) ((aVar.b >> 24) & 255);
                                int i12 = aVar.d;
                                aVar.d = i12 + 1;
                                ensureBufferSize[i12] = (byte) ((aVar.b >> 16) & 255);
                                int i13 = aVar.d;
                                aVar.d = i13 + 1;
                                ensureBufferSize[i13] = (byte) ((aVar.b >> 8) & 255);
                                int i14 = aVar.d;
                                aVar.d = i14 + 1;
                                ensureBufferSize[i14] = (byte) (aVar.b & 255);
                            }
                        } else {
                            i3 = i4;
                        }
                        i4 = i3 + 1;
                        z = true;
                    }
                }
                i3 = i4;
                i4 = i3 + 1;
                z = true;
            }
            if (aVar.f && aVar.h >= 2) {
                byte[] ensureBufferSize2 = ensureBufferSize(this.f, aVar);
                switch (aVar.h) {
                    case 2:
                        int i15 = aVar.d;
                        aVar.d = i15 + 1;
                        ensureBufferSize2[i15] = (byte) ((aVar.b >> 2) & 255);
                        return;
                    case 3:
                        int i16 = aVar.d;
                        aVar.d = i16 + 1;
                        ensureBufferSize2[i16] = (byte) ((aVar.b >> 7) & 255);
                        return;
                    case 4:
                        aVar.b >>= 4;
                        int i17 = aVar.d;
                        aVar.d = i17 + 1;
                        ensureBufferSize2[i17] = (byte) ((aVar.b >> 8) & 255);
                        int i18 = aVar.d;
                        aVar.d = i18 + 1;
                        ensureBufferSize2[i18] = (byte) (aVar.b & 255);
                        return;
                    case 5:
                        aVar.b >>= 1;
                        int i19 = aVar.d;
                        aVar.d = i19 + 1;
                        ensureBufferSize2[i19] = (byte) ((aVar.b >> 16) & 255);
                        int i20 = aVar.d;
                        aVar.d = i20 + 1;
                        ensureBufferSize2[i20] = (byte) ((aVar.b >> 8) & 255);
                        int i21 = aVar.d;
                        aVar.d = i21 + 1;
                        ensureBufferSize2[i21] = (byte) (aVar.b & 255);
                        return;
                    case 6:
                        aVar.b >>= 6;
                        int i22 = aVar.d;
                        aVar.d = i22 + 1;
                        ensureBufferSize2[i22] = (byte) ((aVar.b >> 16) & 255);
                        int i23 = aVar.d;
                        aVar.d = i23 + 1;
                        ensureBufferSize2[i23] = (byte) ((aVar.b >> 8) & 255);
                        int i24 = aVar.d;
                        aVar.d = i24 + 1;
                        ensureBufferSize2[i24] = (byte) (aVar.b & 255);
                        return;
                    case 7:
                        aVar.b >>= 3;
                        int i25 = aVar.d;
                        aVar.d = i25 + 1;
                        ensureBufferSize2[i25] = (byte) ((aVar.b >> 24) & 255);
                        int i26 = aVar.d;
                        aVar.d = i26 + 1;
                        ensureBufferSize2[i26] = (byte) ((aVar.b >> 16) & 255);
                        int i27 = aVar.d;
                        aVar.d = i27 + 1;
                        ensureBufferSize2[i27] = (byte) ((aVar.b >> 8) & 255);
                        int i28 = aVar.d;
                        aVar.d = i28 + 1;
                        ensureBufferSize2[i28] = (byte) (aVar.b & 255);
                        return;
                    default:
                        throw new IllegalStateException("Impossible modulus " + aVar.h);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.apache.commons.codec.binary.BaseNCodec
    public void b(byte[] bArr, int i, int i2, BaseNCodec.a aVar) {
        if (!aVar.f) {
            if (i2 < 0) {
                aVar.f = true;
                if (!(aVar.h == 0 && this.lineLength == 0)) {
                    byte[] ensureBufferSize = ensureBufferSize(this.h, aVar);
                    int i3 = aVar.d;
                    switch (aVar.h) {
                        case 0:
                            break;
                        case 1:
                            int i4 = aVar.d;
                            aVar.d = i4 + 1;
                            ensureBufferSize[i4] = this.i[((int) (aVar.b >> 3)) & 31];
                            int i5 = aVar.d;
                            aVar.d = i5 + 1;
                            ensureBufferSize[i5] = this.i[((int) (aVar.b << 2)) & 31];
                            int i6 = aVar.d;
                            aVar.d = i6 + 1;
                            ensureBufferSize[i6] = this.pad;
                            int i7 = aVar.d;
                            aVar.d = i7 + 1;
                            ensureBufferSize[i7] = this.pad;
                            int i8 = aVar.d;
                            aVar.d = i8 + 1;
                            ensureBufferSize[i8] = this.pad;
                            int i9 = aVar.d;
                            aVar.d = i9 + 1;
                            ensureBufferSize[i9] = this.pad;
                            int i10 = aVar.d;
                            aVar.d = i10 + 1;
                            ensureBufferSize[i10] = this.pad;
                            int i11 = aVar.d;
                            aVar.d = i11 + 1;
                            ensureBufferSize[i11] = this.pad;
                            break;
                        case 2:
                            int i12 = aVar.d;
                            aVar.d = i12 + 1;
                            ensureBufferSize[i12] = this.i[((int) (aVar.b >> 11)) & 31];
                            int i13 = aVar.d;
                            aVar.d = i13 + 1;
                            ensureBufferSize[i13] = this.i[((int) (aVar.b >> 6)) & 31];
                            int i14 = aVar.d;
                            aVar.d = i14 + 1;
                            ensureBufferSize[i14] = this.i[((int) (aVar.b >> 1)) & 31];
                            int i15 = aVar.d;
                            aVar.d = i15 + 1;
                            ensureBufferSize[i15] = this.i[((int) (aVar.b << 4)) & 31];
                            int i16 = aVar.d;
                            aVar.d = i16 + 1;
                            ensureBufferSize[i16] = this.pad;
                            int i17 = aVar.d;
                            aVar.d = i17 + 1;
                            ensureBufferSize[i17] = this.pad;
                            int i18 = aVar.d;
                            aVar.d = i18 + 1;
                            ensureBufferSize[i18] = this.pad;
                            int i19 = aVar.d;
                            aVar.d = i19 + 1;
                            ensureBufferSize[i19] = this.pad;
                            break;
                        case 3:
                            int i20 = aVar.d;
                            aVar.d = i20 + 1;
                            ensureBufferSize[i20] = this.i[((int) (aVar.b >> 19)) & 31];
                            int i21 = aVar.d;
                            aVar.d = i21 + 1;
                            ensureBufferSize[i21] = this.i[((int) (aVar.b >> 14)) & 31];
                            int i22 = aVar.d;
                            aVar.d = i22 + 1;
                            ensureBufferSize[i22] = this.i[((int) (aVar.b >> 9)) & 31];
                            int i23 = aVar.d;
                            aVar.d = i23 + 1;
                            ensureBufferSize[i23] = this.i[((int) (aVar.b >> 4)) & 31];
                            int i24 = aVar.d;
                            aVar.d = i24 + 1;
                            ensureBufferSize[i24] = this.i[((int) (aVar.b << 1)) & 31];
                            int i25 = aVar.d;
                            aVar.d = i25 + 1;
                            ensureBufferSize[i25] = this.pad;
                            int i26 = aVar.d;
                            aVar.d = i26 + 1;
                            ensureBufferSize[i26] = this.pad;
                            int i27 = aVar.d;
                            aVar.d = i27 + 1;
                            ensureBufferSize[i27] = this.pad;
                            break;
                        case 4:
                            int i28 = aVar.d;
                            aVar.d = i28 + 1;
                            ensureBufferSize[i28] = this.i[((int) (aVar.b >> 27)) & 31];
                            int i29 = aVar.d;
                            aVar.d = i29 + 1;
                            ensureBufferSize[i29] = this.i[((int) (aVar.b >> 22)) & 31];
                            int i30 = aVar.d;
                            aVar.d = i30 + 1;
                            ensureBufferSize[i30] = this.i[((int) (aVar.b >> 17)) & 31];
                            int i31 = aVar.d;
                            aVar.d = i31 + 1;
                            ensureBufferSize[i31] = this.i[((int) (aVar.b >> 12)) & 31];
                            int i32 = aVar.d;
                            aVar.d = i32 + 1;
                            ensureBufferSize[i32] = this.i[((int) (aVar.b >> 7)) & 31];
                            int i33 = aVar.d;
                            aVar.d = i33 + 1;
                            ensureBufferSize[i33] = this.i[((int) (aVar.b >> 2)) & 31];
                            int i34 = aVar.d;
                            aVar.d = i34 + 1;
                            ensureBufferSize[i34] = this.i[((int) (aVar.b << 3)) & 31];
                            int i35 = aVar.d;
                            aVar.d = i35 + 1;
                            ensureBufferSize[i35] = this.pad;
                            break;
                        default:
                            throw new IllegalStateException("Impossible modulus " + aVar.h);
                    }
                    aVar.g += aVar.d - i3;
                    if (this.lineLength > 0 && aVar.g > 0) {
                        System.arraycopy(this.j, 0, ensureBufferSize, aVar.d, this.j.length);
                        aVar.d += this.j.length;
                        return;
                    }
                    return;
                }
                return;
            }
            int i36 = i;
            for (int i37 = 0; i37 < i2; i37++) {
                byte[] ensureBufferSize2 = ensureBufferSize(this.h, aVar);
                aVar.h = (aVar.h + 1) % 5;
                i36++;
                byte b2 = bArr[i36];
                int i38 = b2;
                if (b2 < 0) {
                    i38 = b2 + 256;
                }
                aVar.b = (aVar.b << 8) + (i38 == 1 ? 1L : 0L);
                if (aVar.h == 0) {
                    int i39 = aVar.d;
                    aVar.d = i39 + 1;
                    ensureBufferSize2[i39] = this.i[((int) (aVar.b >> 35)) & 31];
                    int i40 = aVar.d;
                    aVar.d = i40 + 1;
                    ensureBufferSize2[i40] = this.i[((int) (aVar.b >> 30)) & 31];
                    int i41 = aVar.d;
                    aVar.d = i41 + 1;
                    ensureBufferSize2[i41] = this.i[((int) (aVar.b >> 25)) & 31];
                    int i42 = aVar.d;
                    aVar.d = i42 + 1;
                    ensureBufferSize2[i42] = this.i[((int) (aVar.b >> 20)) & 31];
                    int i43 = aVar.d;
                    aVar.d = i43 + 1;
                    ensureBufferSize2[i43] = this.i[((int) (aVar.b >> 15)) & 31];
                    int i44 = aVar.d;
                    aVar.d = i44 + 1;
                    ensureBufferSize2[i44] = this.i[((int) (aVar.b >> 10)) & 31];
                    int i45 = aVar.d;
                    aVar.d = i45 + 1;
                    ensureBufferSize2[i45] = this.i[((int) (aVar.b >> 5)) & 31];
                    int i46 = aVar.d;
                    aVar.d = i46 + 1;
                    ensureBufferSize2[i46] = this.i[((int) aVar.b) & 31];
                    aVar.g += 8;
                    if (this.lineLength > 0 && this.lineLength <= aVar.g) {
                        System.arraycopy(this.j, 0, ensureBufferSize2, aVar.d, this.j.length);
                        aVar.d += this.j.length;
                        aVar.g = 0;
                    }
                }
            }
        }
    }

    @Override // org.apache.commons.codec.binary.BaseNCodec
    public boolean isInAlphabet(byte b2) {
        if (b2 >= 0) {
            byte[] bArr = this.g;
            if (b2 < bArr.length && bArr[b2] != -1) {
                return true;
            }
        }
        return false;
    }
}
