package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;

/* loaded from: classes5.dex */
public class QuotedPrintableCodec implements BinaryDecoder, BinaryEncoder, StringDecoder, StringEncoder {
    private static final BitSet c = new BitSet(256);
    private final Charset a;
    private final boolean b;

    private static boolean a(int i) {
        return i == 32 || i == 9;
    }

    static {
        for (int i = 33; i <= 60; i++) {
            c.set(i);
        }
        for (int i2 = 62; i2 <= 126; i2++) {
            c.set(i2);
        }
        c.set(9);
        c.set(32);
    }

    public QuotedPrintableCodec() {
        this(Charsets.UTF_8, false);
    }

    public QuotedPrintableCodec(boolean z) {
        this(Charsets.UTF_8, z);
    }

    public QuotedPrintableCodec(Charset charset) {
        this(charset, false);
    }

    public QuotedPrintableCodec(Charset charset, boolean z) {
        this.a = charset;
        this.b = z;
    }

    public QuotedPrintableCodec(String str) throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException {
        this(Charset.forName(str), false);
    }

    private static final int a(int i, ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(61);
        char upperCase = Character.toUpperCase(Character.forDigit((i >> 4) & 15, 16));
        char upperCase2 = Character.toUpperCase(Character.forDigit(i & 15, 16));
        byteArrayOutputStream.write(upperCase);
        byteArrayOutputStream.write(upperCase2);
        return 3;
    }

    private static int a(int i, byte[] bArr) {
        byte b = bArr[i];
        return b < 0 ? b + 256 : b;
    }

    private static int a(int i, boolean z, ByteArrayOutputStream byteArrayOutputStream) {
        if (z) {
            return a(i, byteArrayOutputStream);
        }
        byteArrayOutputStream.write(i);
        return 1;
    }

    public static final byte[] encodeQuotedPrintable(BitSet bitSet, byte[] bArr) {
        return encodeQuotedPrintable(bitSet, bArr, false);
    }

    public static final byte[] encodeQuotedPrintable(BitSet bitSet, byte[] bArr, boolean z) {
        if (bArr == null) {
            return null;
        }
        if (bitSet == null) {
            bitSet = c;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (z) {
            int i = 1;
            for (int i2 = 0; i2 < bArr.length - 3; i2++) {
                int a = a(i2, bArr);
                if (i < 73) {
                    i += a(a, !bitSet.get(a), byteArrayOutputStream);
                } else {
                    a(a, !bitSet.get(a) || a(a), byteArrayOutputStream);
                    byteArrayOutputStream.write(61);
                    byteArrayOutputStream.write(13);
                    byteArrayOutputStream.write(10);
                    i = 1;
                }
            }
            int a2 = a(bArr.length - 3, bArr);
            if (i + a(a2, !bitSet.get(a2) || (a(a2) && i > 68), byteArrayOutputStream) > 71) {
                byteArrayOutputStream.write(61);
                byteArrayOutputStream.write(13);
                byteArrayOutputStream.write(10);
            }
            int length = bArr.length - 2;
            while (length < bArr.length) {
                int a3 = a(length, bArr);
                a(a3, !bitSet.get(a3) || (length > bArr.length + (-2) && a(a3)), byteArrayOutputStream);
                length++;
            }
        } else {
            for (byte b : bArr) {
                int i3 = b;
                if (b < 0) {
                    i3 = b + 256;
                }
                if (bitSet.get(i3 == 1 ? 1 : 0)) {
                    byteArrayOutputStream.write(i3);
                } else {
                    a(i3, byteArrayOutputStream);
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static final byte[] decodeQuotedPrintable(byte[] bArr) throws DecoderException {
        if (bArr == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == 61) {
                i++;
                try {
                    if (bArr[i] != 13) {
                        int a = b.a(bArr[i]);
                        i++;
                        byteArrayOutputStream.write((char) ((a << 4) + b.a(bArr[i])));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new DecoderException("Invalid quoted-printable encoding", e);
                }
            } else if (!(b == 13 || b == 10)) {
                byteArrayOutputStream.write(b);
            }
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        return encodeQuotedPrintable(c, bArr, this.b);
    }

    @Override // org.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) throws DecoderException {
        return decodeQuotedPrintable(bArr);
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) throws EncoderException {
        return encode(str, getCharset());
    }

    public String decode(String str, Charset charset) throws DecoderException {
        if (str == null) {
            return null;
        }
        return new String(decode(StringUtils.getBytesUsAscii(str)), charset);
    }

    public String decode(String str, String str2) throws DecoderException, UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return new String(decode(StringUtils.getBytesUsAscii(str)), str2);
    }

    @Override // org.apache.commons.codec.StringDecoder
    public String decode(String str) throws DecoderException {
        return decode(str, getCharset());
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be quoted-printable encoded");
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be quoted-printable decoded");
    }

    public Charset getCharset() {
        return this.a;
    }

    public String getDefaultCharset() {
        return this.a.name();
    }

    public String encode(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(encode(str.getBytes(charset)));
    }

    public String encode(String str, String str2) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(encode(str.getBytes(str2)));
    }
}
