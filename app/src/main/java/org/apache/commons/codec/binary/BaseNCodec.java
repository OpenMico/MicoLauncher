package org.apache.commons.codec.binary;

import java.util.Arrays;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/* loaded from: classes5.dex */
public abstract class BaseNCodec implements BinaryDecoder, BinaryEncoder {
    protected static final int MASK_8BITS = 255;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = 61;
    public static final int PEM_CHUNK_SIZE = 64;
    @Deprecated
    protected final byte PAD;
    private final int a;
    private final int b;
    private final int c;
    protected final int lineLength;
    protected final byte pad;

    protected static boolean isWhiteSpace(byte b) {
        if (b == 13 || b == 32) {
            return true;
        }
        switch (b) {
            case 9:
            case 10:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(byte[] bArr, int i, int i2, a aVar);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b(byte[] bArr, int i, int i2, a aVar);

    protected int getDefaultBufferSize() {
        return 8192;
    }

    protected abstract boolean isInAlphabet(byte b);

    /* loaded from: classes5.dex */
    public static class a {
        int a;
        long b;
        byte[] c;
        int d;
        int e;
        boolean f;
        int g;
        int h;

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", getClass().getSimpleName(), Arrays.toString(this.c), Integer.valueOf(this.g), Boolean.valueOf(this.f), Integer.valueOf(this.a), Long.valueOf(this.b), Integer.valueOf(this.h), Integer.valueOf(this.d), Integer.valueOf(this.e));
        }
    }

    public BaseNCodec(int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4, (byte) 61);
    }

    public BaseNCodec(int i, int i2, int i3, int i4, byte b) {
        this.PAD = (byte) 61;
        this.a = i;
        this.b = i2;
        this.lineLength = i3 > 0 && i4 > 0 ? (i3 / i2) * i2 : 0;
        this.c = i4;
        this.pad = b;
    }

    public boolean a(a aVar) {
        return aVar.c != null;
    }

    public int b(a aVar) {
        if (aVar.c != null) {
            return aVar.d - aVar.e;
        }
        return 0;
    }

    private byte[] c(a aVar) {
        if (aVar.c == null) {
            aVar.c = new byte[getDefaultBufferSize()];
            aVar.d = 0;
            aVar.e = 0;
        } else {
            byte[] bArr = new byte[aVar.c.length * 2];
            System.arraycopy(aVar.c, 0, bArr, 0, aVar.c.length);
            aVar.c = bArr;
        }
        return aVar.c;
    }

    protected byte[] ensureBufferSize(int i, a aVar) {
        if (aVar.c == null || aVar.c.length < aVar.d + i) {
            return c(aVar);
        }
        return aVar.c;
    }

    public int c(byte[] bArr, int i, int i2, a aVar) {
        if (aVar.c == null) {
            return aVar.f ? -1 : 0;
        }
        int min = Math.min(b(aVar), i2);
        System.arraycopy(aVar.c, aVar.e, bArr, i, min);
        aVar.e += min;
        if (aVar.e >= aVar.d) {
            aVar.c = null;
        }
        return min;
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    public String encodeToString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public String encodeAsString(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    public byte[] decode(String str) {
        return decode(StringUtils.getBytesUtf8(str));
    }

    @Override // org.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        a aVar = new a();
        a(bArr, 0, bArr.length, aVar);
        a(bArr, 0, -1, aVar);
        byte[] bArr2 = new byte[aVar.d];
        c(bArr2, 0, bArr2.length, aVar);
        return bArr2;
    }

    @Override // org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        a aVar = new a();
        b(bArr, 0, bArr.length, aVar);
        b(bArr, 0, -1, aVar);
        byte[] bArr2 = new byte[aVar.d - aVar.e];
        c(bArr2, 0, bArr2.length, aVar);
        return bArr2;
    }

    public boolean isInAlphabet(byte[] bArr, boolean z) {
        for (int i = 0; i < bArr.length; i++) {
            if (!(isInAlphabet(bArr[i]) || (z && (bArr[i] == this.pad || isWhiteSpace(bArr[i]))))) {
                return false;
            }
        }
        return true;
    }

    public boolean isInAlphabet(String str) {
        return isInAlphabet(StringUtils.getBytesUtf8(str), true);
    }

    protected boolean containsAlphabetOrPad(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b : bArr) {
            if (this.pad == b || isInAlphabet(b)) {
                return true;
            }
        }
        return false;
    }

    public long getEncodedLength(byte[] bArr) {
        int length = bArr.length;
        int i = this.a;
        long j = (((length + i) - 1) / i) * this.b;
        int i2 = this.lineLength;
        return i2 > 0 ? j + ((((i2 + j) - 1) / i2) * this.c) : j;
    }
}
