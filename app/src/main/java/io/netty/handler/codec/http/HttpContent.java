package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;

/* loaded from: classes4.dex */
public interface HttpContent extends ByteBufHolder, HttpObject {
    @Override // io.netty.buffer.ByteBufHolder
    HttpContent copy();

    @Override // io.netty.buffer.ByteBufHolder
    HttpContent duplicate();

    @Override // io.netty.buffer.ByteBufHolder
    HttpContent replace(ByteBuf byteBuf);

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    HttpContent retain();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    HttpContent retain(int i);

    @Override // io.netty.buffer.ByteBufHolder
    HttpContent retainedDuplicate();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    HttpContent touch();

    @Override // io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    HttpContent touch(Object obj);
}
