package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultHttpContent extends DefaultHttpObject implements HttpContent {
    private final ByteBuf a;

    public DefaultHttpContent(ByteBuf byteBuf) {
        if (byteBuf != null) {
            this.a = byteBuf;
            return;
        }
        throw new NullPointerException("content");
    }

    @Override // io.netty.buffer.ByteBufHolder
    public ByteBuf content() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public HttpContent copy() {
        return replace(this.a.copy());
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public HttpContent duplicate() {
        return replace(this.a.duplicate());
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public HttpContent retainedDuplicate() {
        return replace(this.a.retainedDuplicate());
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder
    public HttpContent replace(ByteBuf byteBuf) {
        return new DefaultHttpContent(byteBuf);
    }

    @Override // io.netty.util.ReferenceCounted
    public int refCnt() {
        return this.a.refCnt();
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public HttpContent retain() {
        this.a.retain();
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public HttpContent retain(int i) {
        this.a.retain(i);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public HttpContent touch() {
        this.a.touch();
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpContent, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    public HttpContent touch(Object obj) {
        this.a.touch(obj);
        return this;
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release() {
        return this.a.release();
    }

    @Override // io.netty.util.ReferenceCounted
    public boolean release(int i) {
        return this.a.release(i);
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + content() + ", decoderResult: " + decoderResult() + ')';
    }
}
