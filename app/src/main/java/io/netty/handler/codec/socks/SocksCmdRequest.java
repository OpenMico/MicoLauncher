package io.netty.handler.codec.socks;

import com.xiaomi.onetrack.api.b;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.IDN;

/* loaded from: classes4.dex */
public final class SocksCmdRequest extends SocksRequest {
    private final SocksCmdType a;
    private final SocksAddressType b;
    private final String c;
    private final int d;

    public SocksCmdRequest(SocksCmdType socksCmdType, SocksAddressType socksAddressType, String str, int i) {
        super(SocksRequestType.CMD);
        if (socksCmdType == null) {
            throw new NullPointerException("cmdType");
        } else if (socksAddressType == null) {
            throw new NullPointerException("addressType");
        } else if (str != null) {
            switch (socksAddressType) {
                case IPv4:
                    if (!NetUtil.isValidIpV4Address(str)) {
                        throw new IllegalArgumentException(str + " is not a valid IPv4 address");
                    }
                    break;
                case DOMAIN:
                    if (IDN.toASCII(str).length() > 255) {
                        throw new IllegalArgumentException(str + " IDN: " + IDN.toASCII(str) + " exceeds 255 char limit");
                    }
                    break;
                case IPv6:
                    if (!NetUtil.isValidIpV6Address(str)) {
                        throw new IllegalArgumentException(str + " is not a valid IPv6 address");
                    }
                    break;
            }
            if (i <= 0 || i >= 65536) {
                throw new IllegalArgumentException(i + " is not in bounds 0 < x < 65536");
            }
            this.a = socksCmdType;
            this.b = socksAddressType;
            this.c = IDN.toASCII(str);
            this.d = i;
        } else {
            throw new NullPointerException(b.E);
        }
    }

    public SocksCmdType cmdType() {
        return this.a;
    }

    public SocksAddressType addressType() {
        return this.b;
    }

    public String host() {
        return IDN.toUnicode(this.c);
    }

    public int port() {
        return this.d;
    }

    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(protocolVersion().byteValue());
        byteBuf.writeByte(this.a.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.b.byteValue());
        switch (this.b) {
            case IPv4:
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.c));
                byteBuf.writeShort(this.d);
                return;
            case DOMAIN:
                byteBuf.writeByte(this.c.length());
                byteBuf.writeBytes(this.c.getBytes(CharsetUtil.US_ASCII));
                byteBuf.writeShort(this.d);
                return;
            case IPv6:
                byteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.c));
                byteBuf.writeShort(this.d);
                return;
            default:
                return;
        }
    }
}
