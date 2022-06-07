package io.netty.handler.codec.http2;

import io.netty.handler.codec.EmptyHeaders;
import io.netty.handler.codec.http2.Http2Headers;

/* loaded from: classes4.dex */
public final class EmptyHttp2Headers extends EmptyHeaders<CharSequence, CharSequence, Http2Headers> implements Http2Headers {
    public static final EmptyHttp2Headers INSTANCE = new EmptyHttp2Headers();

    private EmptyHttp2Headers() {
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public EmptyHttp2Headers method(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public EmptyHttp2Headers scheme(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public EmptyHttp2Headers authority(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public EmptyHttp2Headers path(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public EmptyHttp2Headers status(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence method() {
        return get(Http2Headers.PseudoHeaderName.METHOD.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence scheme() {
        return get(Http2Headers.PseudoHeaderName.SCHEME.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence authority() {
        return get(Http2Headers.PseudoHeaderName.AUTHORITY.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence path() {
        return get(Http2Headers.PseudoHeaderName.PATH.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence status() {
        return get(Http2Headers.PseudoHeaderName.STATUS.value());
    }
}
