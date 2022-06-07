package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.Serializable;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class Base64Variant implements Serializable {
    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    private static final long serialVersionUID = 1;
    final String _name;
    private final transient int[] a;
    private final transient char[] b;
    private final transient byte[] c;
    private final transient boolean d;
    private final transient char e;
    private final transient int f;

    public boolean equals(Object obj) {
        return obj == this;
    }

    public Base64Variant(String str, String str2, boolean z, char c, int i) {
        this.a = new int[128];
        this.b = new char[64];
        this.c = new byte[64];
        this._name = str;
        this.d = z;
        this.e = c;
        this.f = i;
        int length = str2.length();
        if (length == 64) {
            str2.getChars(0, length, this.b, 0);
            Arrays.fill(this.a, -1);
            for (int i2 = 0; i2 < length; i2++) {
                char c2 = this.b[i2];
                this.c[i2] = (byte) c2;
                this.a[c2] = i2;
            }
            if (z) {
                this.a[c] = -2;
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Base64Alphabet length must be exactly 64 (was " + length + ")");
    }

    public Base64Variant(Base64Variant base64Variant, String str, int i) {
        this(base64Variant, str, base64Variant.d, base64Variant.e, i);
    }

    public Base64Variant(Base64Variant base64Variant, String str, boolean z, char c, int i) {
        this.a = new int[128];
        this.b = new char[64];
        this.c = new byte[64];
        this._name = str;
        byte[] bArr = base64Variant.c;
        System.arraycopy(bArr, 0, this.c, 0, bArr.length);
        char[] cArr = base64Variant.b;
        System.arraycopy(cArr, 0, this.b, 0, cArr.length);
        int[] iArr = base64Variant.a;
        System.arraycopy(iArr, 0, this.a, 0, iArr.length);
        this.d = z;
        this.e = c;
        this.f = i;
    }

    protected Object readResolve() {
        return Base64Variants.valueOf(this._name);
    }

    public String getName() {
        return this._name;
    }

    public boolean usesPadding() {
        return this.d;
    }

    public boolean usesPaddingChar(char c) {
        return c == this.e;
    }

    public boolean usesPaddingChar(int i) {
        return i == this.e;
    }

    public char getPaddingChar() {
        return this.e;
    }

    public byte getPaddingByte() {
        return (byte) this.e;
    }

    public int getMaxLineLength() {
        return this.f;
    }

    public int decodeBase64Char(char c) {
        if (c <= 127) {
            return this.a[c];
        }
        return -1;
    }

    public int decodeBase64Char(int i) {
        if (i <= 127) {
            return this.a[i];
        }
        return -1;
    }

    public int decodeBase64Byte(byte b) {
        if (b < 0) {
            return -1;
        }
        return this.a[b];
    }

    public char encodeBase64BitsAsChar(int i) {
        return this.b[i];
    }

    public int encodeBase64Chunk(int i, char[] cArr, int i2) {
        int i3 = i2 + 1;
        char[] cArr2 = this.b;
        cArr[i2] = cArr2[(i >> 18) & 63];
        int i4 = i3 + 1;
        cArr[i3] = cArr2[(i >> 12) & 63];
        int i5 = i4 + 1;
        cArr[i4] = cArr2[(i >> 6) & 63];
        int i6 = i5 + 1;
        cArr[i5] = cArr2[i & 63];
        return i6;
    }

    public void encodeBase64Chunk(StringBuilder sb, int i) {
        sb.append(this.b[(i >> 18) & 63]);
        sb.append(this.b[(i >> 12) & 63]);
        sb.append(this.b[(i >> 6) & 63]);
        sb.append(this.b[i & 63]);
    }

    public int encodeBase64Partial(int i, int i2, char[] cArr, int i3) {
        int i4 = i3 + 1;
        char[] cArr2 = this.b;
        cArr[i3] = cArr2[(i >> 18) & 63];
        int i5 = i4 + 1;
        cArr[i4] = cArr2[(i >> 12) & 63];
        if (this.d) {
            int i6 = i5 + 1;
            cArr[i5] = i2 == 2 ? cArr2[(i >> 6) & 63] : this.e;
            int i7 = i6 + 1;
            cArr[i6] = this.e;
            return i7;
        } else if (i2 != 2) {
            return i5;
        } else {
            int i8 = i5 + 1;
            cArr[i5] = cArr2[(i >> 6) & 63];
            return i8;
        }
    }

    public void encodeBase64Partial(StringBuilder sb, int i, int i2) {
        sb.append(this.b[(i >> 18) & 63]);
        sb.append(this.b[(i >> 12) & 63]);
        if (this.d) {
            sb.append(i2 == 2 ? this.b[(i >> 6) & 63] : this.e);
            sb.append(this.e);
        } else if (i2 == 2) {
            sb.append(this.b[(i >> 6) & 63]);
        }
    }

    public byte encodeBase64BitsAsByte(int i) {
        return this.c[i];
    }

    public int encodeBase64Chunk(int i, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        byte[] bArr2 = this.c;
        bArr[i2] = bArr2[(i >> 18) & 63];
        int i4 = i3 + 1;
        bArr[i3] = bArr2[(i >> 12) & 63];
        int i5 = i4 + 1;
        bArr[i4] = bArr2[(i >> 6) & 63];
        int i6 = i5 + 1;
        bArr[i5] = bArr2[i & 63];
        return i6;
    }

    public int encodeBase64Partial(int i, int i2, byte[] bArr, int i3) {
        int i4 = i3 + 1;
        byte[] bArr2 = this.c;
        bArr[i3] = bArr2[(i >> 18) & 63];
        int i5 = i4 + 1;
        bArr[i4] = bArr2[(i >> 12) & 63];
        if (this.d) {
            byte b = (byte) this.e;
            int i6 = i5 + 1;
            bArr[i5] = i2 == 2 ? bArr2[(i >> 6) & 63] : b;
            int i7 = i6 + 1;
            bArr[i6] = b;
            return i7;
        } else if (i2 != 2) {
            return i5;
        } else {
            int i8 = i5 + 1;
            bArr[i5] = bArr2[(i >> 6) & 63];
            return i8;
        }
    }

    public String encode(byte[] bArr) {
        return encode(bArr, false);
    }

    public String encode(byte[] bArr, boolean z) {
        int length = bArr.length;
        StringBuilder sb = new StringBuilder((length >> 2) + length + (length >> 3));
        if (z) {
            sb.append('\"');
        }
        int maxLineLength = getMaxLineLength() >> 2;
        int i = 0;
        int i2 = length - 3;
        while (i <= i2) {
            int i3 = i + 1;
            int i4 = i3 + 1;
            int i5 = ((bArr[i] << 8) | (bArr[i3] & 255)) << 8;
            i = i4 + 1;
            encodeBase64Chunk(sb, i5 | (bArr[i4] & 255));
            maxLineLength--;
            if (maxLineLength <= 0) {
                sb.append('\\');
                sb.append('n');
                maxLineLength = getMaxLineLength() >> 2;
            }
        }
        int i6 = length - i;
        if (i6 > 0) {
            int i7 = i + 1;
            int i8 = bArr[i] << 16;
            if (i6 == 2) {
                i8 |= (bArr[i7] & 255) << 8;
            }
            encodeBase64Partial(sb, i8, i6);
        }
        if (z) {
            sb.append('\"');
        }
        return sb.toString();
    }

    public byte[] decode(String str) throws IllegalArgumentException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        decode(str, byteArrayBuilder);
        return byteArrayBuilder.toByteArray();
    }

    public void decode(String str, ByteArrayBuilder byteArrayBuilder) throws IllegalArgumentException {
        int length = str.length();
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            char charAt = str.charAt(i);
            if (charAt > ' ') {
                int decodeBase64Char = decodeBase64Char(charAt);
                if (decodeBase64Char < 0) {
                    _reportInvalidBase64(charAt, 0, null);
                }
                if (i2 >= length) {
                    _reportBase64EOF();
                }
                int i3 = i2 + 1;
                char charAt2 = str.charAt(i2);
                int decodeBase64Char2 = decodeBase64Char(charAt2);
                if (decodeBase64Char2 < 0) {
                    _reportInvalidBase64(charAt2, 1, null);
                }
                int i4 = (decodeBase64Char << 6) | decodeBase64Char2;
                if (i3 >= length) {
                    if (!usesPadding()) {
                        byteArrayBuilder.append(i4 >> 4);
                        return;
                    }
                    _reportBase64EOF();
                }
                int i5 = i3 + 1;
                char charAt3 = str.charAt(i3);
                int decodeBase64Char3 = decodeBase64Char(charAt3);
                if (decodeBase64Char3 < 0) {
                    if (decodeBase64Char3 != -2) {
                        _reportInvalidBase64(charAt3, 2, null);
                    }
                    if (i5 >= length) {
                        _reportBase64EOF();
                    }
                    i = i5 + 1;
                    char charAt4 = str.charAt(i5);
                    if (!usesPaddingChar(charAt4)) {
                        _reportInvalidBase64(charAt4, 3, "expected padding character '" + getPaddingChar() + LrcRow.SINGLE_QUOTE);
                    }
                    byteArrayBuilder.append(i4 >> 4);
                } else {
                    int i6 = (i4 << 6) | decodeBase64Char3;
                    if (i5 >= length) {
                        if (!usesPadding()) {
                            byteArrayBuilder.appendTwoBytes(i6 >> 2);
                            return;
                        }
                        _reportBase64EOF();
                    }
                    i = i5 + 1;
                    char charAt5 = str.charAt(i5);
                    int decodeBase64Char4 = decodeBase64Char(charAt5);
                    if (decodeBase64Char4 < 0) {
                        if (decodeBase64Char4 != -2) {
                            _reportInvalidBase64(charAt5, 3, null);
                        }
                        byteArrayBuilder.appendTwoBytes(i6 >> 2);
                    } else {
                        byteArrayBuilder.appendThreeBytes((i6 << 6) | decodeBase64Char4);
                    }
                }
            } else {
                i = i2;
            }
        }
    }

    public String toString() {
        return this._name;
    }

    public int hashCode() {
        return this._name.hashCode();
    }

    protected void _reportInvalidBase64(char c, int i, String str) throws IllegalArgumentException {
        String str2;
        if (c <= ' ') {
            str2 = "Illegal white space character (code 0x" + Integer.toHexString(c) + ") as character #" + (i + 1) + " of 4-char base64 unit: can only used between units";
        } else if (usesPaddingChar(c)) {
            str2 = "Unexpected padding character ('" + getPaddingChar() + "') as character #" + (i + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
        } else if (!Character.isDefined(c) || Character.isISOControl(c)) {
            str2 = "Illegal character (code 0x" + Integer.toHexString(c) + ") in base64 content";
        } else {
            str2 = "Illegal character '" + c + "' (code 0x" + Integer.toHexString(c) + ") in base64 content";
        }
        if (str != null) {
            str2 = str2 + ": " + str;
        }
        throw new IllegalArgumentException(str2);
    }

    protected void _reportBase64EOF() throws IllegalArgumentException {
        throw new IllegalArgumentException(missingPaddingMessage());
    }

    public String missingPaddingMessage() {
        return String.format("Unexpected end of base64-encoded String: base64 variant '%s' expects padding (one or more '%c' characters) at the end", getName(), Character.valueOf(getPaddingChar()));
    }
}
