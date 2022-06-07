package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class SocksInitRequestDecoder extends ReplayingDecoder<a> {
    private SocksProtocolVersion d;
    private byte e;
    private final List<SocksAuthScheme> c = new ArrayList();
    private SocksRequest f = a.a;

    /* loaded from: classes4.dex */
    enum a {
        CHECK_PROTOCOL_VERSION,
        READ_AUTH_SCHEMES
    }

    public SocksInitRequestDecoder() {
        super(a.CHECK_PROTOCOL_VERSION);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case CHECK_PROTOCOL_VERSION:
                this.d = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.d == SocksProtocolVersion.SOCKS5) {
                    checkpoint(a.READ_AUTH_SCHEMES);
                }
                break;
            case READ_AUTH_SCHEMES:
                this.c.clear();
                this.e = byteBuf.readByte();
                for (int i = 0; i < this.e; i++) {
                    this.c.add(SocksAuthScheme.valueOf(byteBuf.readByte()));
                }
                this.f = new SocksInitRequest(this.c);
                break;
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.f);
    }
}
