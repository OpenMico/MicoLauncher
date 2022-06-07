package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/* loaded from: classes4.dex */
public class SocksCmdResponseDecoder extends ReplayingDecoder<a> {
    private SocksProtocolVersion c;
    private int d;
    private SocksCmdStatus e;
    private SocksAddressType f;
    private byte g;
    private String h;
    private int i;
    private SocksResponse j = a.b;

    /* loaded from: classes4.dex */
    enum a {
        CHECK_PROTOCOL_VERSION,
        READ_CMD_HEADER,
        READ_CMD_ADDRESS
    }

    public SocksCmdResponseDecoder() {
        super(a.CHECK_PROTOCOL_VERSION);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case CHECK_PROTOCOL_VERSION:
                this.c = SocksProtocolVersion.valueOf(byteBuf.readByte());
                if (this.c == SocksProtocolVersion.SOCKS5) {
                    checkpoint(a.READ_CMD_HEADER);
                }
                break;
            case READ_CMD_HEADER:
                this.e = SocksCmdStatus.valueOf(byteBuf.readByte());
                this.g = byteBuf.readByte();
                this.f = SocksAddressType.valueOf(byteBuf.readByte());
                checkpoint(a.READ_CMD_ADDRESS);
            case READ_CMD_ADDRESS:
                switch (this.f) {
                    case IPv4:
                        this.h = a.a(byteBuf.readInt());
                        this.i = byteBuf.readUnsignedShort();
                        this.j = new SocksCmdResponse(this.e, this.f, this.h, this.i);
                        break;
                    case DOMAIN:
                        this.d = byteBuf.readByte();
                        this.h = a.a(byteBuf, this.d);
                        this.i = byteBuf.readUnsignedShort();
                        this.j = new SocksCmdResponse(this.e, this.f, this.h, this.i);
                        break;
                    case IPv6:
                        byte[] bArr = new byte[16];
                        byteBuf.readBytes(bArr);
                        this.h = a.a(bArr);
                        this.i = byteBuf.readUnsignedShort();
                        this.j = new SocksCmdResponse(this.e, this.f, this.h, this.i);
                        break;
                }
        }
        channelHandlerContext.pipeline().remove(this);
        list.add(this.j);
    }
}
