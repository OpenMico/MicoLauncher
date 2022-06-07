package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public abstract class SocksResponse extends SocksMessage {
    private final SocksResponseType a;

    public SocksResponse(SocksResponseType socksResponseType) {
        super(SocksMessageType.RESPONSE);
        if (socksResponseType != null) {
            this.a = socksResponseType;
            return;
        }
        throw new NullPointerException("responseType");
    }

    public SocksResponseType responseType() {
        return this.a;
    }
}
