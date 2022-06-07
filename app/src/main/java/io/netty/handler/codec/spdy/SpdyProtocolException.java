package io.netty.handler.codec.spdy;

/* loaded from: classes4.dex */
public class SpdyProtocolException extends Exception {
    private static final long serialVersionUID = 7870000537743847264L;

    public SpdyProtocolException() {
    }

    public SpdyProtocolException(String str, Throwable th) {
        super(str, th);
    }

    public SpdyProtocolException(String str) {
        super(str);
    }

    public SpdyProtocolException(Throwable th) {
        super(th);
    }
}
