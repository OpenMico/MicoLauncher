package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.NetUtil;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public final class Socks4ClientEncoder extends MessageToByteEncoder<Socks4CommandRequest> {
    public static final Socks4ClientEncoder INSTANCE = new Socks4ClientEncoder();
    private static final byte[] a = {0, 0, 0, 1};

    private Socks4ClientEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, Socks4CommandRequest socks4CommandRequest, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(socks4CommandRequest.version().byteValue());
        byteBuf.writeByte(socks4CommandRequest.type().byteValue());
        byteBuf.writeShort(socks4CommandRequest.dstPort());
        if (NetUtil.isValidIpV4Address(socks4CommandRequest.dstAddr())) {
            byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(socks4CommandRequest.dstAddr()));
            ByteBufUtil.writeAscii(byteBuf, socks4CommandRequest.userId());
            byteBuf.writeByte(0);
            return;
        }
        byteBuf.writeBytes(a);
        ByteBufUtil.writeAscii(byteBuf, socks4CommandRequest.userId());
        byteBuf.writeByte(0);
        ByteBufUtil.writeAscii(byteBuf, socks4CommandRequest.dstAddr());
        byteBuf.writeByte(0);
    }
}
