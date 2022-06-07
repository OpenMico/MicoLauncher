package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import java.nio.charset.CharsetEncoder;

/* loaded from: classes4.dex */
public final class SocksAuthRequest extends SocksRequest {
    private static final CharsetEncoder a = CharsetUtil.encoder(CharsetUtil.US_ASCII);
    private static final SocksSubnegotiationVersion b = SocksSubnegotiationVersion.AUTH_PASSWORD;
    private final String c;
    private final String d;

    public SocksAuthRequest(String str, String str2) {
        super(SocksRequestType.AUTH);
        if (str == null) {
            throw new NullPointerException("username");
        } else if (str2 == null) {
            throw new NullPointerException("username");
        } else if (!a.canEncode(str) || !a.canEncode(str2)) {
            throw new IllegalArgumentException("username: " + str + " or password: **** values should be in pure ascii");
        } else if (str.length() > 255) {
            throw new IllegalArgumentException("username: " + str + " exceeds 255 char limit");
        } else if (str2.length() <= 255) {
            this.c = str;
            this.d = str2;
        } else {
            throw new IllegalArgumentException("password: **** exceeds 255 char limit");
        }
    }

    public String username() {
        return this.c;
    }

    public String password() {
        return this.d;
    }

    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(b.byteValue());
        byteBuf.writeByte(this.c.length());
        byteBuf.writeBytes(this.c.getBytes(CharsetUtil.US_ASCII));
        byteBuf.writeByte(this.d.length());
        byteBuf.writeBytes(this.d.getBytes(CharsetUtil.US_ASCII));
    }
}
