package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;

/* loaded from: classes4.dex */
public abstract class SocksMessage {
    private final SocksMessageType a;
    private final SocksProtocolVersion b = SocksProtocolVersion.SOCKS5;

    @Deprecated
    public abstract void encodeAsByteBuf(ByteBuf byteBuf);

    /* JADX INFO: Access modifiers changed from: protected */
    public SocksMessage(SocksMessageType socksMessageType) {
        if (socksMessageType != null) {
            this.a = socksMessageType;
            return;
        }
        throw new NullPointerException("type");
    }

    public SocksMessageType type() {
        return this.a;
    }

    public SocksProtocolVersion protocolVersion() {
        return this.b;
    }
}
