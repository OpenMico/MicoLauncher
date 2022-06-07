package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/* loaded from: classes4.dex */
public class SocksInitResponseDecoder extends ReplayingDecoder<a> {
    private SocksProtocolVersion c;
    private SocksAuthScheme d;
    private SocksResponse e = a.b;

    /* loaded from: classes4.dex */
    enum a {
        CHECK_PROTOCOL_VERSION,
        READ_PREFFERED_AUTH_TYPE
    }

    public SocksInitResponseDecoder() {
        super(a.CHECK_PROTOCOL_VERSION);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case CHECK_PROTOCOL_VERSION:
                this.c = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.c == SocksProtocolVersion.SOCKS5) {
                    checkpoint(a.READ_PREFFERED_AUTH_TYPE);
                }
                break;
            case READ_PREFFERED_AUTH_TYPE:
                this.d = SocksAuthScheme.valueOf(byteBuf.readByte());
                this.e = new SocksInitResponse(this.d);
                break;
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.e);
    }
}
