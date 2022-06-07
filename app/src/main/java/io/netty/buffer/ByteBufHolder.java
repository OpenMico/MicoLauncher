package io.netty.buffer;

import io.netty.util.ReferenceCounted;

/* loaded from: classes4.dex */
public interface ByteBufHolder extends ReferenceCounted {
    ByteBuf content();

    ByteBufHolder copy();

    ByteBufHolder duplicate();

    ByteBufHolder replace(ByteBuf byteBuf);

    @Override // io.netty.util.ReferenceCounted
    ByteBufHolder retain();

    @Override // io.netty.util.ReferenceCounted
    ByteBufHolder retain(int i);

    ByteBufHolder retainedDuplicate();

    @Override // io.netty.util.ReferenceCounted
    ByteBufHolder touch();

    @Override // io.netty.util.ReferenceCounted
    ByteBufHolder touch(Object obj);
}
