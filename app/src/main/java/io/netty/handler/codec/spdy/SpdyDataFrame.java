package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

/* loaded from: classes4.dex */
public interface SpdyDataFrame extends ByteBufHolder, SpdyStreamFrame {
    @Override // io.netty.buffer.ByteBufHolder
    ByteBuf content();

    @Override // io.netty.buffer.ByteBufHolder
    SpdyDataFrame copy();

    @Override // io.netty.buffer.ByteBufHolder
    SpdyDataFrame duplicate();

    @Override // io.netty.buffer.ByteBufHolder
    SpdyDataFrame replace(ByteBuf byteBuf);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    SpdyDataFrame retain();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    SpdyDataFrame retain(int i);

    @Override // io.netty.buffer.ByteBufHolder
    SpdyDataFrame retainedDuplicate();

    SpdyDataFrame setLast(boolean z);

    SpdyDataFrame setStreamId(int i);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    SpdyDataFrame touch();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    SpdyDataFrame touch(Object obj);
}
