package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/* loaded from: classes4.dex */
public class SocksAuthResponseDecoder extends ReplayingDecoder<a> {
    private SocksSubnegotiationVersion c;
    private SocksAuthStatus d;
    private SocksResponse e = a.b;

    /* loaded from: classes4.dex */
    enum a {
        CHECK_PROTOCOL_VERSION,
        READ_AUTH_RESPONSE
    }

    public SocksAuthResponseDecoder() {
        super(a.CHECK_PROTOCOL_VERSION);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case CHECK_PROTOCOL_VERSION:
                this.c = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
                if (this.c == SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    checkpoint(a.READ_AUTH_RESPONSE);
                }
                break;
            case READ_AUTH_RESPONSE:
                this.d = SocksAuthStatus.valueOf(byteBuf.readByte());
                this.e = new SocksAuthResponse(this.d);
                break;
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.e);
    }
}
