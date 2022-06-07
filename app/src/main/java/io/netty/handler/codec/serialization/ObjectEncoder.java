package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.Serializable;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class ObjectEncoder extends MessageToByteEncoder<Serializable> {
    private static final byte[] a = new byte[4];

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Serializable serializable, ByteBuf byteBuf) throws Exception {
        int writerIndex = byteBuf.writerIndex();
        ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(byteBuf);
        byteBufOutputStream.write(a);
        d dVar = new d(byteBufOutputStream);
        dVar.writeObject(serializable);
        dVar.flush();
        dVar.close();
        byteBuf.setInt(writerIndex, (byteBuf.writerIndex() - writerIndex) - 4);
    }
}
