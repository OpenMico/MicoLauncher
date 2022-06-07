package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import java.io.IOException;

/* loaded from: classes4.dex */
public interface Attribute extends HttpData {
    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    Attribute copy();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    Attribute duplicate();

    String getValue() throws IOException;

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    Attribute replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Attribute retain();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Attribute retain(int i);

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    Attribute retainedDuplicate();

    void setValue(String str) throws IOException;

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Attribute touch();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    Attribute touch(Object obj);
}
