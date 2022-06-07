package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/* loaded from: classes4.dex */
public class SocksAuthRequestDecoder extends ReplayingDecoder<a> {
    private SocksSubnegotiationVersion c;
    private int d;
    private String e;
    private String f;
    private SocksRequest g = a.a;

    /* loaded from: classes4.dex */
    enum a {
        CHECK_PROTOCOL_VERSION,
        READ_USERNAME,
        READ_PASSWORD
    }

    public SocksAuthRequestDecoder() {
        super(a.CHECK_PROTOCOL_VERSION);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case CHECK_PROTOCOL_VERSION:
                this.c = SocksSubnegotiationVersion.valueOf(byteBuf.readByte());
                if (this.c == SocksSubnegotiationVersion.AUTH_PASSWORD) {
                    checkpoint(a.READ_USERNAME);
                }
                break;
            case READ_USERNAME:
                this.d = byteBuf.readByte();
                this.e = a.a(byteBuf, this.d);
                checkpoint(a.READ_PASSWORD);
            case READ_PASSWORD:
                this.d = byteBuf.readByte();
                this.f = a.a(byteBuf, this.d);
                this.g = new SocksAuthRequest(this.e, this.f);
                break;
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.g);
    }
}
