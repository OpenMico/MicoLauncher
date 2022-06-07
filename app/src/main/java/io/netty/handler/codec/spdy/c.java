package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.internal.PlatformDependent;

/* compiled from: SpdyHeaderBlockEncoder.java */
/* loaded from: classes4.dex */
public abstract class c {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract ByteBuf encode(ByteBufAllocator byteBufAllocator, SpdyHeadersFrame spdyHeadersFrame) throws Exception;

    public static c a(SpdyVersion spdyVersion, int i, int i2, int i3) {
        if (PlatformDependent.javaVersion() >= 7) {
            return new f(spdyVersion, i);
        }
        return new d(spdyVersion, i, i2, i3);
    }
}
