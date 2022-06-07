package io.netty.channel.udt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.DefaultByteBufHolder;

/* loaded from: classes4.dex */
public final class UdtMessage extends DefaultByteBufHolder {
    public UdtMessage(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public UdtMessage copy() {
        return (UdtMessage) super.copy();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public UdtMessage duplicate() {
        return (UdtMessage) super.duplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public UdtMessage retainedDuplicate() {
        return (UdtMessage) super.retainedDuplicate();
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder
    public UdtMessage replace(ByteBuf byteBuf) {
        return new UdtMessage(byteBuf);
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public UdtMessage retain() {
        super.retain();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public UdtMessage retain(int i) {
        super.retain(i);
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public UdtMessage touch() {
        super.touch();
        return this;
    }

    @Override // io.netty.buffer.DefaultByteBufHolder, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public UdtMessage touch(Object obj) {
        super.touch(obj);
        return this;
    }
}
