package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.marshalling.c;
import java.util.List;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

/* loaded from: classes4.dex */
public class CompatibleMarshallingDecoder extends ReplayingDecoder<Void> {
    private boolean c;
    protected final int maxObjectSize;
    protected final UnmarshallerProvider provider;

    public CompatibleMarshallingDecoder(UnmarshallerProvider unmarshallerProvider, int i) {
        this.provider = unmarshallerProvider;
        this.maxObjectSize = i;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.c) {
            byteBuf.skipBytes(actualReadableBytes());
            checkpoint();
            return;
        }
        Unmarshaller unmarshaller = this.provider.getUnmarshaller(channelHandlerContext);
        ByteInput aVar = new a(byteBuf);
        int i = this.maxObjectSize;
        if (i != Integer.MAX_VALUE) {
            aVar = new c(aVar, i);
        }
        try {
            try {
                unmarshaller.start(aVar);
                Object readObject = unmarshaller.readObject();
                unmarshaller.finish();
                list.add(readObject);
            } catch (c.a unused) {
                this.c = true;
                throw new TooLongFrameException();
            }
        } finally {
            unmarshaller.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (byteBuf.readableBytes()) {
            case 0:
                return;
            case 1:
                if (byteBuf.getByte(byteBuf.readerIndex()) == 121) {
                    byteBuf.skipBytes(1);
                    return;
                }
                break;
        }
        decode(channelHandlerContext, byteBuf, list);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (th instanceof TooLongFrameException) {
            channelHandlerContext.close();
        } else {
            super.exceptionCaught(channelHandlerContext, th);
        }
    }
}
