package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

/* loaded from: classes4.dex */
public interface Http2DataFrame extends ByteBufHolder, Http2StreamFrame {
    @Override // io.netty.buffer.ByteBufHolder
    ByteBuf content();

    @Override // io.netty.buffer.ByteBufHolder
    Http2DataFrame copy();

    @Override // io.netty.buffer.ByteBufHolder
    Http2DataFrame duplicate();

    boolean isEndStream();

    int padding();

    @Override // io.netty.buffer.ByteBufHolder
    Http2DataFrame replace(ByteBuf byteBuf);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Http2DataFrame retain();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Http2DataFrame retain(int i);

    @Override // io.netty.buffer.ByteBufHolder
    Http2DataFrame retainedDuplicate();

    @Override // io.netty.handler.codec.http2.Http2StreamFrame
    Http2DataFrame setStream(Object obj);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Http2DataFrame touch();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Http2DataFrame touch(Object obj);
}
