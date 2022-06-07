package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public final class SocksAuthResponse extends SocksResponse {
    private static final SocksSubnegotiationVersion a = SocksSubnegotiationVersion.AUTH_PASSWORD;
    private final SocksAuthStatus b;

    public SocksAuthResponse(SocksAuthStatus socksAuthStatus) {
        super(SocksResponseType.AUTH);
        if (socksAuthStatus != null) {
            this.b = socksAuthStatus;
            return;
        }
        throw new NullPointerException("authStatus");
    }

    public SocksAuthStatus authStatus() {
        return this.b;
    }

    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(a.byteValue());
        byteBuf.writeByte(this.b.byteValue());
    }
}
