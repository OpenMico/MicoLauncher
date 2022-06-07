package io.netty.handler.codec.serialization;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/* loaded from: classes4.dex */
public class ObjectDecoder extends LengthFieldBasedFrameDecoder {
    private final ClassResolver a;

    public ObjectDecoder(ClassResolver classResolver) {
        this(1048576, classResolver);
    }

    public ObjectDecoder(int i, ClassResolver classResolver) {
        super(i, 0, 4, 0, 4);
        this.a = classResolver;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.LengthFieldBasedFrameDecoder
    public Object decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        ByteBuf byteBuf2 = (ByteBuf) super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        c cVar = new c(new ByteBufInputStream(byteBuf2), this.a);
        Object readObject = cVar.readObject();
        cVar.close();
        return readObject;
    }

    @Override // io.netty.handler.codec.LengthFieldBasedFrameDecoder
    protected ByteBuf extractFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.slice(i, i2);
    }
}
