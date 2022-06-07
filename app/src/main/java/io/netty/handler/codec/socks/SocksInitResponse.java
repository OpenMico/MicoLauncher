package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public final class SocksInitResponse extends SocksResponse {
    private final SocksAuthScheme a;

    public SocksInitResponse(SocksAuthScheme socksAuthScheme) {
        super(SocksResponseType.INIT);
        if (socksAuthScheme != null) {
            this.a = socksAuthScheme;
            return;
        }
        throw new NullPointerException("authScheme");
    }

    public SocksAuthScheme authScheme() {
        return this.a;
    }

    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(protocolVersion().byteValue());
        byteBuf.writeByte(this.a.byteValue());
    }
}
