package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public abstract class AbstractHttp2StreamFrame implements Http2StreamFrame {
    private Object a;

    @Override // io.netty.handler.codec.http2.Http2StreamFrame
    public AbstractHttp2StreamFrame setStream(Object obj) {
        this.a = obj;
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2StreamFrame
    public Object stream() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Http2StreamFrame)) {
            return false;
        }
        Http2StreamFrame http2StreamFrame = (Http2StreamFrame) obj;
        Object obj2 = this.a;
        if (obj2 == null) {
            return http2StreamFrame.stream() == null;
        }
        return obj2.equals(http2StreamFrame.stream());
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 61432814;
        }
        return obj.hashCode();
    }
}
