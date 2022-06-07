package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public interface StompFrame extends LastStompContentSubframe, StompHeadersSubframe {
    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    StompFrame copy();

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    StompFrame duplicate();

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    StompFrame replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    StompFrame retain();

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    StompFrame retain(int i);

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder
    StompFrame retainedDuplicate();

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    StompFrame touch();

    @Override // io.netty.handler.codec.stomp.LastStompContentSubframe, io.netty.handler.codec.stomp.StompContentSubframe, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    StompFrame touch(Object obj);
}
