package io.netty.handler.codec.stomp;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.HeadersUtils;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class DefaultStompHeaders extends DefaultHeaders<CharSequence, CharSequence, StompHeaders> implements StompHeaders {
    public DefaultStompHeaders() {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE);
    }

    @Override // io.netty.handler.codec.stomp.StompHeaders
    public String getAsString(CharSequence charSequence) {
        return HeadersUtils.getAsString(this, charSequence);
    }

    @Override // io.netty.handler.codec.stomp.StompHeaders
    public List<String> getAllAsString(CharSequence charSequence) {
        return HeadersUtils.getAllAsString(this, charSequence);
    }

    @Override // io.netty.handler.codec.stomp.StompHeaders
    public Iterator<Map.Entry<String, String>> iteratorAsString() {
        return HeadersUtils.iteratorAsString(this);
    }

    public boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return contains(charSequence, charSequence2, false);
    }

    @Override // io.netty.handler.codec.stomp.StompHeaders
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return contains((DefaultStompHeaders) charSequence, charSequence2, (HashingStrategy<? super CharSequence>) (z ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER));
    }
}
