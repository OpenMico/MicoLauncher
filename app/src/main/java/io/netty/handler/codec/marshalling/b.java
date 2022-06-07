package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.ByteOutput;

/* compiled from: ChannelBufferByteOutput.java */
/* loaded from: classes4.dex */
class b implements ByteOutput {
    private final ByteBuf a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(ByteBuf byteBuf) {
        this.a = byteBuf;
    }
}
