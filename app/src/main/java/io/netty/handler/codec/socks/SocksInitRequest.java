package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class SocksInitRequest extends SocksRequest {
    private final List<SocksAuthScheme> a;

    public SocksInitRequest(List<SocksAuthScheme> list) {
        super(SocksRequestType.INIT);
        if (list != null) {
            this.a = list;
            return;
        }
        throw new NullPointerException("authSchemes");
    }

    public List<SocksAuthScheme> authSchemes() {
        return Collections.unmodifiableList(this.a);
    }

    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(protocolVersion().byteValue());
        byteBuf.writeByte(this.a.size());
        for (SocksAuthScheme socksAuthScheme : this.a) {
            byteBuf.writeByte(socksAuthScheme.byteValue());
        }
    }
}
