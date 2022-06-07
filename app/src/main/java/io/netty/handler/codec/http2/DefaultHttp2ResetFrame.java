package io.netty.handler.codec.http2;

import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public final class DefaultHttp2ResetFrame extends AbstractHttp2StreamFrame implements Http2ResetFrame {
    private final long a;

    public DefaultHttp2ResetFrame(Http2Error http2Error) {
        this.a = ((Http2Error) ObjectUtil.checkNotNull(http2Error, "error")).code();
    }

    public DefaultHttp2ResetFrame(long j) {
        this.a = j;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame, io.netty.handler.codec.http2.Http2StreamFrame
    public DefaultHttp2ResetFrame setStream(Object obj) {
        super.setStream(obj);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2ResetFrame
    public long errorCode() {
        return this.a;
    }

    public String toString() {
        return "DefaultHttp2ResetFrame(stream=" + stream() + "errorCode=" + this.a + ")";
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame
    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttp2ResetFrame)) {
            return false;
        }
        return super.equals(obj) && this.a == ((DefaultHttp2ResetFrame) obj).a;
    }

    @Override // io.netty.handler.codec.http2.AbstractHttp2StreamFrame
    public int hashCode() {
        long j = this.a;
        return (super.hashCode() * 31) + ((int) (j ^ (j >>> 32)));
    }
}
