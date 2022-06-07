package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.jboss.marshalling.Unmarshaller;

/* loaded from: classes4.dex */
public class MarshallingDecoder extends LengthFieldBasedFrameDecoder {
    private final UnmarshallerProvider a;

    public MarshallingDecoder(UnmarshallerProvider unmarshallerProvider) {
        this(unmarshallerProvider, 1048576);
    }

    public MarshallingDecoder(UnmarshallerProvider unmarshallerProvider, int i) {
        super(i, 0, 4, 0, 4);
        this.a = unmarshallerProvider;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.LengthFieldBasedFrameDecoder
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = (ByteBuf) super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        Unmarshaller unmarshaller = this.a.getUnmarshaller(channelHandlerContext);
        try {
            unmarshaller.start(new a(byteBuf2));
            Object readObject = unmarshaller.readObject();
            unmarshaller.finish();
            return readObject;
        } finally {
            unmarshaller.close();
        }
    }

    @Override // io.netty.handler.codec.LengthFieldBasedFrameDecoder
    protected ByteBuf extractFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.slice(i, i2);
    }
}
