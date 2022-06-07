package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.NetUtil;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public final class Socks4ServerEncoder extends MessageToByteEncoder<Socks4CommandResponse> {
    public static final Socks4ServerEncoder INSTANCE = new Socks4ServerEncoder();
    private static final byte[] a = {0, 0, 0, 0};

    private Socks4ServerEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Socks4CommandResponse socks4CommandResponse, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(0);
        byteBuf.writeByte(socks4CommandResponse.status().byteValue());
        byteBuf.writeShort(socks4CommandResponse.dstPort());
        byteBuf.writeBytes(socks4CommandResponse.dstAddr() == null ? a : NetUtil.createByteArrayFromIpAddressString(socks4CommandResponse.dstAddr()));
    }
}
