package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class MarshallingEncoder extends MessageToByteEncoder<Object> {
    private static final byte[] a = new byte[4];
    private final MarshallerProvider b;

    public MarshallingEncoder(MarshallerProvider marshallerProvider) {
        this.b = marshallerProvider;
    }

    @Override // io.netty.handler.codec.MessageToByteEncoder
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        Marshaller marshaller = this.b.getMarshaller(channelHandlerContext);
        int writerIndex = byteBuf.writerIndex();
        byteBuf.writeBytes(a);
        marshaller.start(new b(byteBuf));
        marshaller.writeObject(obj);
        marshaller.finish();
        marshaller.close();
        byteBuf.setInt(writerIndex, (byteBuf.writerIndex() - writerIndex) - 4);
    }
}
