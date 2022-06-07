package io.netty.handler.codec.socks;

/* loaded from: classes4.dex */
public abstract class SocksRequest extends SocksMessage {
    private final SocksRequestType a;

    public SocksRequest(SocksRequestType socksRequestType) {
        super(SocksMessageType.REQUEST);
        if (socksRequestType != null) {
            this.a = socksRequestType;
            return;
        }
        throw new NullPointerException("requestType");
    }

    public SocksRequestType requestType() {
        return this.a;
    }
}
