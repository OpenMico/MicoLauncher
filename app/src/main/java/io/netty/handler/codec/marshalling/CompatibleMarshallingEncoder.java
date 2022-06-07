package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jboss.marshalling.Marshaller;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class CompatibleMarshallingEncoder extends MessageToByteEncoder<Object> {
    private final MarshallerProvider a;

    public CompatibleMarshallingEncoder(MarshallerProvider marshallerProvider) {
        this.a = marshallerProvider;
    }

    @Override // io.netty.handler.codec.MessageToByteEncoder
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        Marshaller marshaller = this.a.getMarshaller(channelHandlerContext);
        marshaller.start(new b(byteBuf));
        marshaller.writeObject(obj);
        marshaller.finish();
        marshaller.close();
    }
}
