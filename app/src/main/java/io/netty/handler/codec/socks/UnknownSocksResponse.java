package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public final class UnknownSocksResponse extends SocksResponse {
    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
    }

    public UnknownSocksResponse() {
        super(SocksResponseType.UNKNOWN);
    }
}
