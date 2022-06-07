package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Exception;

/* loaded from: classes4.dex */
public class Http2NoMoreStreamIdsException extends Http2Exception {
    private static final long serialVersionUID = -7756236161274851110L;

    public Http2NoMoreStreamIdsException() {
        super(Http2Error.PROTOCOL_ERROR, "No more streams can be created on this connection", Http2Exception.ShutdownHint.GRACEFUL_SHUTDOWN);
    }

    public Http2NoMoreStreamIdsException(Throwable th) {
        super(Http2Error.PROTOCOL_ERROR, "No more streams can be created on this connection", th, Http2Exception.ShutdownHint.GRACEFUL_SHUTDOWN);
    }
}
