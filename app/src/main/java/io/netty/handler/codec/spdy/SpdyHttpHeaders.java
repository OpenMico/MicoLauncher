package io.netty.handler.codec.spdy;

import io.netty.util.AsciiString;

/* loaded from: classes4.dex */
public final class SpdyHttpHeaders {

    /* loaded from: classes4.dex */
    public static final class Names {
        public static final AsciiString STREAM_ID = new AsciiString("x-spdy-stream-id");
        public static final AsciiString ASSOCIATED_TO_STREAM_ID = new AsciiString("x-spdy-associated-to-stream-id");
        public static final AsciiString PRIORITY = new AsciiString("x-spdy-priority");
        public static final AsciiString SCHEME = new AsciiString("x-spdy-scheme");

        private Names() {
        }
    }

    private SpdyHttpHeaders() {
    }
}
