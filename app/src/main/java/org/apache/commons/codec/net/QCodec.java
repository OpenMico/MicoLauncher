package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.BitSet;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class QCodec extends a implements StringDecoder, StringEncoder {
    private static final BitSet b = new BitSet(256);
    private final Charset a;
    private boolean c;

    @Override // org.apache.commons.codec.net.a
    protected String getEncoding() {
        return "Q";
    }

    static {
        b.set(32);
        b.set(33);
        b.set(34);
        b.set(35);
        b.set(36);
        b.set(37);
        b.set(38);
        b.set(39);
        b.set(40);
        b.set(41);
        b.set(42);
        b.set(43);
        b.set(44);
        b.set(45);
        b.set(46);
        b.set(47);
        for (int i = 48; i <= 57; i++) {
            b.set(i);
        }
        b.set(58);
        b.set(59);
        b.set(60);
        b.set(62);
        b.set(64);
        for (int i2 = 65; i2 <= 90; i2++) {
            b.set(i2);
        }
        b.set(91);
        b.set(92);
        b.set(93);
        b.set(94);
        b.set(96);
        for (int i3 = 97; i3 <= 122; i3++) {
            b.set(i3);
        }
        b.set(123);
        b.set(124);
        b.set(125);
        b.set(126);
    }

    public QCodec() {
        this(Charsets.UTF_8);
    }

    public QCodec(Charset charset) {
        this.c = false;
        this.a = charset;
    }

    public QCodec(String str) {
        this(Charset.forName(str));
    }

    @Override // org.apache.commons.codec.net.a
    protected byte[] doEncoding(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] encodeQuotedPrintable = QuotedPrintableCodec.encodeQuotedPrintable(b, bArr);
        if (this.c) {
            for (int i = 0; i < encodeQuotedPrintable.length; i++) {
                if (encodeQuotedPrintable[i] == 32) {
                    encodeQuotedPrintable[i] = 95;
                }
            }
        }
        return encodeQuotedPrintable;
    }

    @Override // org.apache.commons.codec.net.a
    protected byte[] doDecoding(byte[] bArr) throws DecoderException {
        boolean z;
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (bArr[i] == 95) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (!z) {
            return QuotedPrintableCodec.decodeQuotedPrintable(bArr);
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            if (b2 != 95) {
                bArr2[i2] = b2;
            } else {
                bArr2[i2] = 32;
            }
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(bArr2);
    }

    public String encode(String str, Charset charset) throws EncoderException {
        if (str == null) {
            return null;
        }
        return encodeText(str, charset);
    }

    public String encode(String str, String str2) throws EncoderException {
        if (str == null) {
            return null;
        }
        try {
            return encodeText(str, str2);
        } catch (UnsupportedEncodingException e) {
            throw new EncoderException(e.getMessage(), e);
        }
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) throws EncoderException {
        if (str == null) {
            return null;
        }
        return encode(str, getCharset());
    }

    @Override // org.apache.commons.codec.StringDecoder
    public String decode(String str) throws DecoderException {
        if (str == null) {
            return null;
        }
        try {
            return decodeText(str);
        } catch (UnsupportedEncodingException e) {
            throw new DecoderException(e.getMessage(), e);
        }
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be encoded using Q codec");
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be decoded using Q codec");
    }

    public Charset getCharset() {
        return this.a;
    }

    public String getDefaultCharset() {
        return this.a.name();
    }

    public boolean isEncodeBlanks() {
        return this.c;
    }

    public void setEncodeBlanks(boolean z) {
        this.c = z;
    }
}
