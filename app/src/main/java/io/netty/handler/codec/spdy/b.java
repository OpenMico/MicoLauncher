package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/* compiled from: SpdyHeaderBlockDecoder.java */
/* loaded from: classes4.dex */
public abstract class b {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, SpdyHeadersFrame spdyHeadersFrame) throws Exception;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(SpdyHeadersFrame spdyHeadersFrame) throws Exception;

    public static b a(SpdyVersion spdyVersion, int i) {
        return new e(spdyVersion, i);
    }
}
