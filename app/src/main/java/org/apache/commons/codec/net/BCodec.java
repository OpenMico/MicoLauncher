package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Base64;

/* loaded from: classes5.dex */
public class BCodec extends a implements StringDecoder, StringEncoder {
    private final Charset a;

    @Override // org.apache.commons.codec.net.a
    protected String getEncoding() {
        return "B";
    }

    public BCodec() {
        this(Charsets.UTF_8);
    }

    public BCodec(Charset charset) {
        this.a = charset;
    }

    public BCodec(String str) {
        this(Charset.forName(str));
    }

    @Override // org.apache.commons.codec.net.a
    protected byte[] doEncoding(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeBase64(bArr);
    }

    @Override // org.apache.commons.codec.net.a
    protected byte[] doDecoding(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.decodeBase64(bArr);
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
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be encoded using BCodec");
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be decoded using BCodec");
    }

    public Charset getCharset() {
        return this.a;
    }

    public String getDefaultCharset() {
        return this.a.name();
    }
}
