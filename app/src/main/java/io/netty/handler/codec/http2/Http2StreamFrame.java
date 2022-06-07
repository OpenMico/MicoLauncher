package io.netty.handler.codec.http2;

/* loaded from: classes4.dex */
public interface Http2StreamFrame extends Http2Frame {
    Http2StreamFrame setStream(Object obj);

    Object stream();
}
