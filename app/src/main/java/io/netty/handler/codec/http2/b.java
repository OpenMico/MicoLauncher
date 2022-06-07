package io.netty.handler.codec.http2;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.UnsupportedValueConverter;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.AsciiString;

/* compiled from: CharSequenceMap.java */
/* loaded from: classes4.dex */
final class b<V> extends DefaultHeaders<CharSequence, V, b<V>> {
    public b() {
        this(true);
    }

    public b(boolean z) {
        this(z, UnsupportedValueConverter.instance());
    }

    public b(boolean z, ValueConverter<V> valueConverter) {
        super(z ? AsciiString.CASE_SENSITIVE_HASHER : AsciiString.CASE_INSENSITIVE_HASHER, valueConverter);
    }
}
