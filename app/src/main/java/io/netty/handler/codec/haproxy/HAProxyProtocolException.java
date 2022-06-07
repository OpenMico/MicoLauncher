package io.netty.handler.codec.haproxy;

import io.netty.handler.codec.DecoderException;

/* loaded from: classes4.dex */
public class HAProxyProtocolException extends DecoderException {
    private static final long serialVersionUID = 713710864325167351L;

    public HAProxyProtocolException() {
    }

    public HAProxyProtocolException(String str, Throwable th) {
        super(str, th);
    }

    public HAProxyProtocolException(String str) {
        super(str);
    }

    public HAProxyProtocolException(Throwable th) {
        super(th);
    }
}
