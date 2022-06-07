package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class SocksMessageEncoder extends MessageToByteEncoder<SocksMessage> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, SocksMessage socksMessage, ByteBuf byteBuf) throws Exception {
        socksMessage.encodeAsByteBuf(byteBuf);
    }
}
