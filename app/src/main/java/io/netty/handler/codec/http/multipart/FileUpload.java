package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public interface FileUpload extends HttpData {
    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    FileUpload copy();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    FileUpload duplicate();

    String getContentTransferEncoding();

    String getContentType();

    String getFilename();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    FileUpload replace(ByteBuf byteBuf);

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FileUpload retain();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FileUpload retain(int i);

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder
    FileUpload retainedDuplicate();

    void setContentTransferEncoding(String str);

    void setContentType(String str);

    void setFilename(String str);

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FileUpload touch();

    @Override // io.netty.handler.codec.http.multipart.HttpData, io.netty.buffer.ByteBufHolder, io.netty.util.ReferenceCounted
    FileUpload touch(Object obj);
}
