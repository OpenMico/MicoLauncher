package io.netty.handler.codec.spdy;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.HeadersUtils;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultSpdyHeaders extends DefaultHeaders<CharSequence, CharSequence, SpdyHeaders> implements SpdyHeaders {
    private static final DefaultHeaders.NameValidator<CharSequence> b = new DefaultHeaders.NameValidator<CharSequence>() { // from class: io.netty.handler.codec.spdy.DefaultSpdyHeaders.1
        /* renamed from: a */
        public void validateName(CharSequence charSequence) {
            a.a(charSequence);
        }
    };

    public DefaultSpdyHeaders() {
        this(true);
    }

    public DefaultSpdyHeaders(boolean z) {
        super(AsciiString.CASE_INSENSITIVE_HASHER, z ? a.a : CharSequenceValueConverter.INSTANCE, z ? b : DefaultHeaders.NameValidator.NOT_NULL);
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaders
    public String getAsString(CharSequence charSequence) {
        return HeadersUtils.getAsString(this, charSequence);
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaders
    public List<String> getAllAsString(CharSequence charSequence) {
        return HeadersUtils.getAllAsString(this, charSequence);
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaders
    public Iterator<Map.Entry<String, String>> iteratorAsString() {
        return HeadersUtils.iteratorAsString(this);
    }

    public boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return contains(charSequence, charSequence2, false);
    }

    @Override // io.netty.handler.codec.spdy.SpdyHeaders
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return contains((DefaultSpdyHeaders) charSequence, charSequence2, (HashingStrategy<? super CharSequence>) (z ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER));
    }

    /* loaded from: classes4.dex */
    private static final class a extends CharSequenceValueConverter {
        public static final a a = new a();

        private a() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // io.netty.handler.codec.CharSequenceValueConverter, io.netty.handler.codec.ValueConverter
        public CharSequence convertObject(Object obj) {
            CharSequence convertObject = super.convertObject(obj);
            a.b(convertObject);
            return convertObject;
        }
    }
}
